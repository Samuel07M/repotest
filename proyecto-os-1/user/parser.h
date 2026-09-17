#ifndef PARSER_H
#define PARSER_H
#include "utils.h"

// comando + argumentos + archivos de redireccion (si aplica).
struct stage {
  char *argv[MAXARGS + 1]; // argv[0..argc-1] + NULL final
  int argc;
  char *infile;             // != 0 si hay '<'
  char *outfile;            // != 0 si hay '>'
};

// secuencia de etapas conectadas por '|'.
struct pipeline {
  struct stage stages[MAXSTAGES];
  int nstages;
};

int parse_line(char *line, struct pipeline *pl);

#endif