// parser.c
// Implementacion del analizador de la linea de comandos.
//
// Gramatica soportada (sin comillas, sin variables, sin comodines):
//
//   linea    -> pipeline
//   pipeline -> etapa ( '|' etapa )*
//   etapa    -> palabra+ ( '<' palabra | '>' palabra )*
//
// El analisis se hace en dos partes:
//   1. Un tokenizador (gettoken) que reconoce palabras y los simbolos
//      especiales '|', '<', '>'.
//   2. Un ensamblador de tuberia que recorre los tokens y construye la
//      estructura struct pipeline definida en parser.h.
//
// Manejo de memoria: cada palabra reconocida (argumento, nombre de
// archivo de redireccion) se reserva con malloc (dupword). Si el
// analisis se aborta por cualquier motivo (token invalido, error de
// sintaxis, falta de memoria), TODO lo reservado hasta ese punto -
// incluida la etapa que se estaba construyendo en ese momento, aunque
// este incompleta - se libera antes de retornar, para no dejar fugas.

#include "kernel/types.h"
#include "user/user.h"
#include "parser.h"
#include "commands.h" // free_pipeline

#define TOK_WORD 1
#define TOK_PIPE 2
#define TOK_LT   3
#define TOK_GT   4
#define TOK_END  5
#define TOK_ERR  6

// Avanza 'p' saltando espacios en blanco.
static char*
skip_blanks(char *p, char *end)
{
  while (p < end && (*p == ' ' || *p == '\t'))
    p++;
  return p;
}

// Obtiene el siguiente token a partir de *pp (sin sobrepasar 'end').
// Si el token es una palabra, la copia (terminada en '\0') en buf.
// Actualiza *pp para que apunte despues del token consumido.
static int
gettoken(char **pp, char *end, char *buf, int bufsize)
{
  char *p = skip_blanks(*pp, end);

  if (p >= end) {
    *pp = p;
    return TOK_END;
  }

  if (*p == '|') {
    *pp = p + 1;
    return TOK_PIPE;
  }
  if (*p == '<') {
    *pp = p + 1;
    return TOK_LT;
  }
  if (*p == '>') {
    *pp = p + 1;
    return TOK_GT;
  }

  // Palabra: todo lo que no sea espacio ni simbolo especial.
  char *start = p;
  while (p < end && *p != ' ' && *p != '\t' &&
         *p != '|' && *p != '<' && *p != '>')
    p++;

  int len = p - start;
  if (len <= 0 || len >= bufsize) {
    *pp = p;
    return TOK_ERR;
  }

  memmove(buf, start, len);
  buf[len] = '\0';

  *pp = p;
  return TOK_WORD;
}

// Reserva espacio para una copia de 'word' dentro del heap del proceso.
static char*
dupword(char *word)
{
  int len = strlen(word);
  char *copy = malloc(len + 1);
  if (copy == 0) {
    printf("sh: error de memoria\n");
    return 0;
  }
  strcpy(copy, word);
  return copy;
}

// Aborta el analisis: libera toda la memoria reservada hasta el momento
// y retorna -1. 'pending' indica si la etapa actualmente en construccion
// (pl->stages[pl->nstages], todavia no contabilizada en pl->nstages) debe
// incluirse en la liberacion.
static int
parse_fail(struct pipeline *pl, int pending)
{
  if (pending)
    pl->nstages++;
  free_pipeline(pl);
  pl->nstages = 0;
  return -1;
}

int
parse_line(char *line, struct pipeline *pl)
{
  char *p = line;
  char *end = line + strlen(line);
  char tokbuf[MAXLINE];

  pl->nstages = 0;
  struct stage *st = &pl->stages[0];
  st->argc = 0;
  st->infile = 0;
  st->outfile = 0;

  int have_command = 0; // hay al menos una palabra en la etapa actual

  for (;;) {
    int tok = gettoken(&p, end, tokbuf, sizeof(tokbuf));

    if (tok == TOK_END)
      break;

    if (tok == TOK_ERR) {
      printf("sh: token invalido\n");
      return parse_fail(pl, 1);
    }

    if (tok == TOK_WORD) {
      if (!have_command && pl->nstages >= MAXSTAGES) {
        printf("sh: demasiados comandos encadenados con '|'\n");
        return parse_fail(pl, 1);
      }
      if (st->argc >= MAXARGS - 1) {
        printf("sh: demasiados argumentos\n");
        return parse_fail(pl, 1);
      }
      char *w = dupword(tokbuf);
      if (w == 0)
        return parse_fail(pl, 1);
      st->argv[st->argc++] = w;
      have_command = 1;
      continue;
    }

    if (tok == TOK_LT || tok == TOK_GT) {
      int t2 = gettoken(&p, end, tokbuf, sizeof(tokbuf));
      if (t2 != TOK_WORD) {
        printf("sh: se esperaba un nombre de archivo despues de '%s'\n",
               tok == TOK_LT ? "<" : ">");
        return parse_fail(pl, 1);
      }
      char *w = dupword(tokbuf);
      if (w == 0)
        return parse_fail(pl, 1);
      // Si el mismo tipo de redireccion se repite en la misma etapa
      // (p. ej. "echo hola > a.txt > b.txt"), la ultima gana; se libera
      // la cadena anterior para no dejarla huerfana en el heap.
      if (tok == TOK_LT) {
        if (st->infile)
          free(st->infile);
        st->infile = w;
      } else {
        if (st->outfile)
          free(st->outfile);
        st->outfile = w;
      }
      continue;
    }

    if (tok == TOK_PIPE) {
      if (!have_command) {
        printf("sh: se esperaba un comando antes de '|'\n");
        return parse_fail(pl, 1);
      }
      st->argv[st->argc] = 0;
      pl->nstages++;
      if (pl->nstages >= MAXSTAGES) {
        printf("sh: demasiados comandos encadenados con '|'\n");
        // La etapa que se acaba de cerrar ya quedo contabilizada en
        // pl->nstages; no hay una etapa adicional pendiente que sumar.
        return parse_fail(pl, 0);
      }
      st = &pl->stages[pl->nstages];
      st->argc = 0;
      st->infile = 0;
      st->outfile = 0;
      have_command = 0;
      continue;
    }
  }

  if (!have_command) {
    if (pl->nstages == 0)
      return 0; // linea vacia: nada que ejecutar
    printf("sh: se esperaba un comando despues de '|'\n");
    return parse_fail(pl, 1);
  }

  st->argv[st->argc] = 0;
  pl->nstages++;

  return 1;
}
