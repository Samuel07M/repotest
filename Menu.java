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

            // Opcion 0: guarda los datos en un archivo y finaliza el programa
            if(opcion == 0){
                System.out.println("\nNombre del archivo (lugares): ");
                System.out.println("\nNombre del archivo (conexiones): ");
                System.out.println("\nNombre del archivo (rutas): ");

                System.out.println("\nGuardado exitosamente. Saliendo...");
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
        }
    }
}