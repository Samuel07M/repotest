// sh.c
// Shell para xv6.
//
// Ciclo principal: lee una linea de la entrada estandar, la analiza
// (parser.c) y ejecuta el comando o la tuberia resultante (commands.c).
// Soporta el comando interno "exit" para terminar de forma controlada.

#include "kernel/types.h"
#include "user/user.h"
#include "kernel/fcntl.h"
#include "utils.h"
#include "parser.h"
#include "commands.h"

// Garantiza que los descriptores 0, 1 y 2 esten abiertos sobre la
// consola antes de iniciar el ciclo del shell (necesario cuando el
// shell se ejecuta como primer proceso de usuario).
static void
ensure_std_fds(void)
{
  int fd;
  while ((fd = open("console", O_RDWR)) >= 0) {
    if (fd >= 3) {
      close(fd);
      break;
    }
  }
}

int
main(void)
{
  printf("\n>>> SOY LA NUEVA SHELL <<<\n\n");
  static char line[MAXLINE];

  ensure_std_fds();

  while (1) {
    printf("$ ");
    memset(line, 0, sizeof(line));
    gets(line, sizeof(line));

    if (line[0] == 0)
      break; // fin de entrada (EOF)

    // gets() conserva el '\n' (o "\r\n") final dentro del buffer; lo
    // quitamos para que no quede pegado como parte del ultimo argumento.
    int len = strlen(line);
    while (len > 0 && (line[len - 1] == '\n' || line[len - 1] == '\r')) {
      line[len - 1] = '\0';
      len--;
    }

    struct pipeline pl;
    int r = parse_line(line, &pl);
    if (r <= 0)
      continue; // linea vacia o error de sintaxis ya reportado

    // Comando interno "exit": termina el shell sin crear un proceso.
    // Se acepta con o sin argumentos adicionales (que son ignorados),
    // siempre que no forme parte de una tuberia.
    if (pl.nstages == 1 && pl.stages[0].argc >= 1 &&
        strcmp(pl.stages[0].argv[0], "exit") == 0) {
      free_pipeline(&pl);
      exit(0);
    }

    // run_pipeline crea un proceso hijo por cada etapa de la tuberia
    // (fork + exec) y espera a que todos terminen antes de retornar,
    // por lo que no es necesario un fork adicional aqui.
    run_pipeline(&pl);
    free_pipeline(&pl);
  }

  exit(0);
}
