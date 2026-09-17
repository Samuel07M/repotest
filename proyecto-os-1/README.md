# Proyecto de Sistemas Operativos
## Implementación de un Shell para xv6

### Integrantes

- [Nombre completo integrante 1]
- [Nombre completo integrante 2]
- [Nombre completo integrante 3]

### Curso

Sistemas Operativos

### Semestre

2026-2

---

# Descripción

Este proyecto implementa un shell para el sistema operativo xv6. El programa
permite interpretar comandos ingresados por el usuario desde la entrada
estándar y ejecutar los programas existentes en xv6 mediante la creación de
nuevos procesos.

La implementación soporta:

- Ejecución de comandos simples.
- Ejecución de comandos con argumentos.
- Redirección de entrada (`<`).
- Redirección de salida (`>`).
- Tuberías simples y múltiples encadenadas (`|`).
- Comando interno `exit` para finalizar el shell.

La solución fue desarrollada utilizando lenguaje C y se integra al proceso de
compilación estándar de xv6 ejecutado sobre QEMU.

---

# Estructura de archivos

```text
user/
├── sh.c
├── parser.c
├── parser.h
├── commands.c
├── commands.h
└── utils.h
```

## Descripción de los archivos

### sh.c

Archivo principal del shell. Contiene el ciclo `main`: imprime el prompt,
lee la línea con `gets`, elimina el salto de línea final, la analiza con
`parse_line` y ejecuta el resultado. Reconoce el comando interno `exit`
antes de crear cualquier proceso.

### parser.c / parser.h

Analizador léxico y sintáctico de la línea de comandos. Reconoce palabras
y los símbolos `|`, `<` y `>`, y construye una estructura `struct pipeline`
compuesta por una o más `struct stage` (una por cada segmento separado por
`|`), cada una con su `argv`, y sus archivos de redirección de entrada y
salida si fueron indicados. Reporta errores de sintaxis simples (comando
faltante antes/después de `|`, nombre de archivo faltante tras `<`/`>`,
demasiados argumentos o etapas).

### commands.c / commands.h

Ejecución de la tubería construida por el parser. Cada etapa se ejecuta en
un proceso independiente creado con `fork`. Cuando hay más de una etapa se
crean los pipes necesarios y se conectan la entrada/salida estándar de cada
proceso al pipe correspondiente mediante `close`/`dup`; las redirecciones
explícitas (`<`, `>`) se aplican después, por lo que tienen prioridad sobre
la conexión de la tubería. El proceso que orquesta la tubería espera a que
todos sus hijos terminen antes de continuar.

### utils.h

Constantes compartidas por el proyecto: longitud máxima de línea, máximo de
argumentos por comando y máximo de etapas encadenadas por `|`.

---

# Integración con xv6

## Paso 1. Obtener xv6

```bash
git clone https://github.com/mit-pdos/xv6-riscv.git
```

## Paso 2. Copiar archivos

Copiar todos los archivos entregados (`sh.c`, `parser.c`, `parser.h`,
`commands.c`, `commands.h`, `utils.h`) dentro del directorio `user/` de
xv6.

## Paso 3. Modificar el Makefile

Agregar la siguiente regla específica para enlazar `sh` junto con los
módulos adicionales (`parser.o` y `commands.o`), antes de la regla genérica
`_%: %.o ...`:

```makefile
$U/_sh: $U/sh.o $U/parser.o $U/commands.o $(ULIB) $U/user.ld
	$(LD) $(LDFLAGS) -T $U/user.ld -o $@ $U/sh.o $U/parser.o $U/commands.o $(ULIB)
	$(OBJDUMP) -S $@ > $U/sh.asm
	$(OBJDUMP) -t $@ | sed '1,/SYMBOL TABLE/d; s/ .* / /; /^$$/d' > $U/sh.sym
```

`_sh` ya está incluido en la lista `UPROGS` por defecto en xv6-riscv, no es
necesario modificarla.

## Paso 4. Compilar xv6

```bash
make qemu
```

## Paso 5. Ejecutar el shell

El shell se ejecuta automáticamente como shell de usuario al iniciar xv6
(es el programa `init` el que lo invoca). También puede ejecutarse
manualmente escribiendo:

```bash
sh
```

---

# Casos de prueba

- Ejecución simple: `ls`, `echo hola`.
- Argumentos: `echo hola mundo`, `grep texto archivo.txt`.
- Redirección de entrada: `cat < datos.txt`.
- Redirección de salida: `echo hola > salida.txt`.
- Tuberías simples: `echo hola | wc`.
- Tuberías múltiples: `cat archivo.txt | grep error | wc`, `ls | grep txt | sort | wc`.
- Comando `exit`.
- Manejo de errores: comando inexistente, `|` sin comando antes/después,
  `<`/`>` sin nombre de archivo.

Todos los casos anteriores fueron verificados ejecutando el kernel
compilado bajo QEMU con entrada controlada.

---

# Decisiones de diseño

- Separación clara entre análisis sintáctico (`parser.c`) y ejecución
  (`commands.c`), siguiendo el principio de responsabilidad única.
- Representación plana de la tubería (arreglo de etapas) en lugar de un
  árbol de sintaxis, ya que la gramática soportada (una secuencia de
  comandos unidos por `|`, cada uno con a lo sumo una redirección de
  entrada y una de salida) no requiere una estructura recursiva.
- Cada etapa de una tubería se ejecuta en un proceso independiente; se usa
  un único descriptor "pendiente" (`prevfd`) para encadenar el extremo de
  lectura de un pipe con la entrada estándar de la siguiente etapa, y se
  cierran explícitamente los descriptores ya usados para evitar fugas de
  descriptores de archivo y bloqueos por extremos de pipe abiertos de más.
- Las redirecciones explícitas (`<`, `>`) se aplican después de conectar
  los pipes, de modo que tienen prioridad sobre estos si ambos coincidieran
  en el mismo descriptor.
- El comando `exit` se reconoce en el propio proceso del shell (sin crear
  un proceso hijo) para garantizar que termine el shell y no un
  subproceso. Se acepta con o sin argumentos adicionales (se ignoran),
  siempre que no sea parte de una tubería.
- `run_pipeline` es la única responsable de crear procesos: crea
  exactamente un `fork` por cada etapa de la tubería (ni más ni menos),
  y el propio proceso del shell la invoca directamente y espera a que
  todas las etapas terminen, sin un `fork` intermedio redundante.
- La memoria reservada por el parser (`argv`, nombres de archivo) se libera
  explícitamente (`free_pipeline`) tras cada ejecución.

---

# Limitaciones conocidas

- No soporta comillas simples ni dobles.
- No soporta variables de entorno ni sustitución de variables.
- No soporta expansión de comodines.
- No soporta ejecución en segundo plano ni control de trabajos.
- No implementa `cd`, `history`, `jobs`, `fg`, `bg`, `kill` (explícitamente
  excluidos del alcance del proyecto).

---

# Declaración de uso de IA

Durante el desarrollo del proyecto se utilizaron herramientas de
inteligencia artificial generativa como apoyo para comprensión de
conceptos, revisión de código, depuración (incluyendo la identificación de
un error de conteo del salto de línea final entregado por `gets`) y
generación de la documentación.

Los integrantes asumen plena responsabilidad académica sobre el contenido
entregado.
