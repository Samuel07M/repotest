import java.util.Scanner;

public class Menu {

    // Atributos 
    private App app; 
    private Scanner teclado = new Scanner(System.in);

    //Constructor, recibe un objeto App
    public Menu(App app){
        this.app = app;
    }

    // Metodo que muestra el menu
    public void mostrarMenu(){
        int opcion = 1; 

        while(opcion != 0){
            System.out.println("\n===================================");
            System.out.println("   SISTEMA DE RUTAS TURISTICAS");
            System.out.println("===================================\n");
            System.out.println("Opciones:");
            System.out.println(" 0 - Guardar y salir");
            System.out.println(" 1 - Registrar lugares"); 
            System.out.println(" 2 - Registrar conexiones");
            System.out.println(" 3 - Registrar turistas");
            System.out.println(" 4 - Registrar rutas");
            System.out.println(" 5 - Mostrar lugares");
            System.out.println(" 6 - Mostrar conexiones");
            System.out.println(" 7 - Mostrar lugares abiertos");
            System.out.println(" 8 - Recomendar destinos");
            System.out.println(" 9 - Lugares conectados");
            System.out.println("10 - Mostrar rutas");
            System.out.println("11 - Leer datos desde archivo");
            System.out.print("Elige una opcion: "); 
            
            // Verifica que el usuario escriba un numero entero
            if(teclado.hasNextInt()){ 
                opcion = teclado.nextInt();
                teclado.nextLine();

                if(opcion < 0 || opcion > 11){
                    System.out.println("\nOpcion invalida.");
                    continue;
                }
            }
            else{
                System.out.println("\nOpcion Invalida.");
                teclado.next(); // Limpira el buffer y evita un bucle infinito
                continue;
            }
            
            // Opcion 1: registrar nuevas ciudades
            if(opcion ==1){
                app.registrarLugar();
            }

            // Opcion 2: registrar nuevas conexiones
            if(opcion == 2){
                app.registrarConexion();
            }
            // Opcion 3: registrar nuevos turistas
            if(opcion == 3){
                app.registrarTurista();
            }

            // Opcion 4: registrar nuevas rutas
            if(opcion == 4){
                app.registrarRutas();
            }

            // Opcion 5: mostrar lugares
            if(opcion == 5){
                app.mostrarLugares();
            }

            // Opcion 6: mostrar conexiones
            if(opcion == 6){
                app.mostrarConexiones();
            }

            // Opcion 7: mostrar solo lugares abiertos
            if(opcion == 7){
                app.mostrarLugaresAbiertos();
            }

            // Opcion 10: mostrar menu rutas
            if(opcion == 10){
                mostrarMenuRutas();
            }

            // Opcion 11: cargar datos desde archivos
            if(opcion == 11){
                app.cargarDatos();
            }
        }
    }

    public void mostrarMenuRutas(){
        int opcion = 1;
        
        while(opcion != 0){
            System.out.println("\n== ¿Como le gustaria ver las rutas? ==");
            System.out.println("Opciones:");
            System.out.println(" 0 - Volver al menu principal");
            System.out.println(" 1 - Mostrar todas las rutas");
            System.out.println(" 2 - Filtrar por lugares");
            System.out.print("Elige una opcion: "); 
            
            // Verifica que el usuario escriba un numero entero
            if(teclado.hasNextInt()){ 
                opcion = teclado.nextInt();
                teclado.nextLine();

                if(opcion < 0 || opcion > 11){
                    System.out.println("\nOpcion invalida.");
                    continue;
                }
            }
            else{
                System.out.println("\nOpcion Invalida.");
                teclado.next(); // Limpira el buffer y evita un bucle infinito
                continue;
            }

            // Opcion 1: mostrar todas las rutas
            if(opcion == 1){
                app.mostrarRutas();
            }

            //opcion 2: filtrar por lugares
            if(opcion == 2){
                app.filtrarRutas();
            }
        }
    }
}