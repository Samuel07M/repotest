// commands.h
// Interfaz para la ejecucion de comandos y tuberias.

#ifndef COMMANDS_H
#define COMMANDS_H

#include "parser.h"

// Ejecuta la tuberia completa (una o mas etapas conectadas por '|'),
// aplicando las redirecciones indicadas en cada etapa, y espera a que
// todos los procesos hijos terminen antes de retornar.
void run_pipeline(struct pipeline *pl);

// Libera la memoria reservada por el parser para 'pl'.
void free_pipeline(struct pipeline *pl);

#endif // COMMANDS_H
