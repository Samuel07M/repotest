#ifndef SYSINFO_H
#define SYSINFO_H
#include "types.h" // Asegura la definicion de uint64

struct sysinfo {
  uint64 freemem; // Memoria fisica 
  uint64 usedpages; //Cantidad de paginas fisicas en uso
  uint64 freepages; // Cantidad de paginas fisicas disponibles
  uint64 runnableprocs; // Cantidad de procesos en estado RUNNABLE
};

#endif
