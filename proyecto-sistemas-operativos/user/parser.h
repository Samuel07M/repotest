// parser.h
// Estructuras y funciones para el analisis de la linea de comandos.
//
// El shell soporta una tuberia (pipeline) compuesta por una o mas
// "etapas" (stages) separadas por el operador '|'. Cada etapa es un
// comando simple con sus argumentos y, opcionalmente, redireccion de
// entrada ('<') y/o salida ('>').

#ifndef PARSER_H
#define PARSER_H

#include "utils.h"

// Una etapa: comando + argumentos + archivos de redireccion (si aplica).
struct stage {
  char *argv[MAXARGS + 1]; // argv[0..argc-1] + NULL final
  int argc;
  char *infile;             // != 0 si hay '<'
  char *outfile;            // != 0 si hay '>'
};

// Una tuberia completa: secuencia de etapas conectadas por '|'.
struct pipeline {
  struct stage stages[MAXSTAGES];
  int nstages;
};

// Analiza 'line' y llena 'pl'.
// Retorna 1 si la linea contiene un comando valido para ejecutar,
// 0 si la linea esta vacia (no hay nada que hacer),
// -1 si hay un error de sintaxis (ya fue reportado por stderr).
int parse_line(char *line, struct pipeline *pl);

#endif // PARSER_H
