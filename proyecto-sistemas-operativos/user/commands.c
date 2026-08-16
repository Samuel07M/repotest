// commands.c
// Ejecucion de una tuberia (pipeline) de una o mas etapas.
//
// Cada etapa se ejecuta en un proceso hijo independiente (fork + exec).
// Cuando hay mas de una etapa, se encadenan mediante pipes: el extremo
// de lectura del pipe anterior se conecta a la entrada estandar de la
// siguiente etapa, y el extremo de escritura del pipe actual se conecta
// a la salida estandar de la etapa presente. Las redirecciones ('<' y
// '>') explicitas de cada etapa se aplican despues de conectar los
// pipes, de modo que tienen prioridad sobre estos.

#include "kernel/types.h"
#include "user/user.h"
#include "kernel/fcntl.h"
#include "commands.h"

// Aplica las redirecciones de archivo indicadas en la etapa, si existen.
// Debe llamarse en el proceso hijo, antes de exec().
static void
apply_redirections(struct stage *st)
{
  if (st->infile) {
    close(0);
    if (open(st->infile, O_RDONLY) < 0) {
      printf("sh: no se pudo abrir %s\n", st->infile);
      exit(1);
    }
  }

  if (st->outfile) {
    close(1);
    if (open(st->outfile, O_WRONLY | O_CREATE | O_TRUNC) < 0) {
      printf("sh: no se pudo crear %s\n", st->outfile);
      exit(1);
    }
  }
}

void
run_pipeline(struct pipeline *pl)
{
  int n = pl->nstages;
  int prevfd = -1; // extremo de lectura del pipe de la etapa anterior
  int created = 0; // procesos hijos efectivamente creados hasta el momento

  // NOTA: run_pipeline se ejecuta directamente en el proceso del shell
  // (no en un hijo intermedio), por lo que ante un error de pipe() o
  // fork() NUNCA debe llamar a exit(): eso terminaria el shell entero.
  // En su lugar, se reportan el error, se cierran los descriptores
  // pendientes, se espera a los hijos ya creados y se retorna
  // normalmente para que sh.c continue con la siguiente linea.

  for (int i = 0; i < n; i++) {
    struct stage *st = &pl->stages[i];
    int have_next = (i < n - 1);
    int fd[2];

    if (have_next && pipe(fd) < 0) {
      printf("sh: error creando pipe\n");
      if (prevfd != -1)
        close(prevfd);
      break;
    }

    int pid = fork();
    if (pid < 0) {
      printf("sh: error en fork\n");
      if (have_next) {
        close(fd[0]);
        close(fd[1]);
      }
      if (prevfd != -1)
        close(prevfd);
      break;
    }

    if (pid == 0) {
      // Proceso hijo: conecta la etapa dentro de la tuberia.
      if (prevfd != -1) {
        close(0);
        dup(prevfd);
        close(prevfd);
      }
      if (have_next) {
        close(fd[0]);
        close(1);
        dup(fd[1]);
        close(fd[1]);
      }

      // Las redirecciones explicitas tienen prioridad sobre el pipe.
      apply_redirections(st);

      if (st->argc == 0)
        exit(0);

      exec(st->argv[0], st->argv);
      printf("sh: no se pudo ejecutar %s\n", st->argv[0]);
      exit(1);
    }

    // Proceso padre (el shell): cierra los descriptores que ya no
    // necesita y avanza al siguiente eslabon de la tuberia.
    created++;
    if (prevfd != -1)
      close(prevfd);

    if (have_next) {
      close(fd[1]);
      prevfd = fd[0];
    } else {
      prevfd = -1;
    }
  }

  for (int i = 0; i < created; i++)
    wait(0);
}

void
free_pipeline(struct pipeline *pl)
{
  for (int i = 0; i < pl->nstages; i++) {
    struct stage *st = &pl->stages[i];
    for (int j = 0; j < st->argc; j++)
      free(st->argv[j]);
    if (st->infile)
      free(st->infile);
    if (st->outfile)
      free(st->outfile);
  }
}
