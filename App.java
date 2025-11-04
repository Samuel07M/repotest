import java.util.ArrayList;
import java.util.Scanner;

public class App {
    public static void main(String[] args){
        App app = new App();
        Menu menu = new Menu(app); 

        menu.mostrarMenu();
    }

    // Declaracion de entrada de datos 
    Scanner teclado = new Scanner(System.in);
    ArrayList<Lugar> arr_lugares = new ArrayList<>();
    ArrayList<Turista> arr_turistas = new ArrayList<>();
    ArrayList<Conexion> arr_conexiones = new ArrayList<>();

    // Metodo para registrar nuevas ciudades - Opcion 1 del menu
    public void registrarLugar(){
        System.out.println("\n== Registrar Lugar ==");
        System.out.print("Nombre: "); 
            String nombreLugar = teclado.nextLine(); 
           
        // Verificacion de existencia para evitar duplicados 
        Lugar verificarLugar = null; // Objeto temporal 
        for(int i=0; i<arr_lugares.size(); i++){
            if (arr_lugares.get(i).getNombreLugar().trim().equalsIgnoreCase(nombreLugar)){
                verificarLugar = arr_lugares.get(i); 
            }
        }

        if(verificarLugar == null){
            System.out.print("Intereses turisticos (separados por ,): "); 
                String interesesTexto = teclado.nextLine(); 
                String[] interesesLugar_tmp = interesesTexto.split(","); // Separa por comas
                String[] interesesLugar = new String[interesesLugar_tmp.length];
                for(int i=0; i<interesesLugar_tmp.length; i++) {
                    interesesLugar[i] = interesesLugar_tmp[i].trim().toLowerCase(); // Limpiar espacios en blanco
                }
            System.out.print("Descripcion: ");
                String descripcionLugar = teclado.nextLine();
                String estado = "";
            do{
                System.out.print("Estado (abierta/cerrada): ");
                estado = teclado.nextLine().trim().toLowerCase();

                if(!estado.equals("abierta") && !estado.equals("cerrada")){
                    System.out.println("Opcion Invalida.");
                }

            }while(!estado.equals("abierta") && !estado.equals("cerrada"));
            
            arr_lugares.add(new Lugar(nombreLugar, descripcionLugar, interesesLugar, estado));
            System.out.println("\nLugar registrado exitosamente.\n"); 
        }
        else{
            System.out.println("\nEl Lugar ya esta registrado.\n");
        }
    }

    // Metodo para registrar conexiones - Opcion 2 del menu
    public void registrarConexion(){
        System.out.println("\n== Registrar Conexion ==");
        System.out.print("Origen: "); 
            String origen = teclado.nextLine(); 

        Lugar verificarLugar = null; // Objeto temporal 
        for(int i=0; i<arr_lugares.size(); i++){
            if (arr_lugares.get(i).getNombreLugar().trim().equalsIgnoreCase(origen)){
                verificarLugar = arr_lugares.get(i); 
            }
        }

        if(verificarLugar == null){
            System.out.println("\nEl lugar no esta registrado.\n");
            return;
        }

        System.out.print("Destino: ");
            String destino = teclado.nextLine(); 

        Lugar verificarLugar_2 = null; // Objeto temporal 
        for(int i=0; i<arr_lugares.size(); i++){
            if (arr_lugares.get(i).getNombreLugar().trim().equalsIgnoreCase(destino)){
                verificarLugar_2 = arr_lugares.get(i); 
            }
        }

        if(verificarLugar_2 == null){
            System.out.println("\nEl lugar no esta registrado.\n");
            return;
        }

        boolean existencia = false; 
        for(int i=0; i<arr_conexiones.size(); i++){
            Conexion tmp = arr_conexiones.get(i);
            if(tmp.getOrigen().equalsIgnoreCase(origen)){
                existencia = true; 
                break;
            }
        }

        if(existencia == true){
            System.out.println("\nLa conexion ya existe.\n");
            return;
        }

        System.out.print("Tiempo de viaje (min): ");
            float tiempo = teclado.nextFloat();
            teclado.nextLine();
        arr_conexiones.add(new Conexion(origen, destino, tiempo));

        // Bidirreccionalidad de la conexion 
        arr_conexiones.add(new Conexion(destino, origen, tiempo));
        System.out.println("\nConexion registrada exitosamente.\n"); 
    }

    // Metodo para registrar turistas - Opcion 3 del menu
    public void registrarTurista(){
        System.out.println("\n== Registrar Turista ==");
        System.out.print("Nombre: ");
            String nombreTurista = teclado.nextLine();
        
        // Verificacion de existencia para evitar duplicados
        Turista verificarTurista = null; 
        for(int i=0; i<arr_turistas.size(); i++){
            if (arr_turistas.get(i).getNombreTurista().trim().equalsIgnoreCase(nombreTurista)){
                verificarTurista = arr_turistas.get(i); 
            }
        }

        if(verificarTurista == null){
            System.out.print("Preferencias (separadas por ,): "); 
                String preferenciasTexto = teclado.nextLine(); 
                String[] preferenciasTurista_tmp = preferenciasTexto.split(","); // Separa por comas
                String[] preferenciasTurista = new String[preferenciasTurista_tmp.length];
                for(int i=0; i<preferenciasTurista_tmp.length; i++) {
                    preferenciasTurista[i] = preferenciasTurista_tmp[i].trim().toLowerCase(); // Limpiar espacios en blanco
                }
            
            arr_turistas.add(new Turista(nombreTurista, preferenciasTurista));
            System.err.println("\nTurista registrado exitosamente.\n");
        }
        else{
            System.out.println("\nEl Turista ya esta registrado.\n");
        }
    }

    // Metodo para mostrar todos los lugares registrados - Opcion 5 del menu
    public void mostrarLugares(){
        System.out.println("\n== Ciudades Registradas ==");

        if(arr_lugares.isEmpty()){
            System.out.println("No hay lugares registrados.\n");
            return;
        }

        for(int i=0; i<arr_lugares.size(); i++){
            Lugar lugar = arr_lugares.get(i);
            String intereses = String.join(", ", lugar.getInteresesLugar());
            
            System.out.println((i+1) + ". " + lugar.getNombreLugar() + " || " + 
                               lugar.getDescripcionLugar() + " || " + "Intereses: " +
                               intereses + " || " + "Estado: " + lugar.getEstado());
        }
    }

    // Metodo para mostrar todas las conexiones registradas - Opcion 6 del menu
    public void mostrarConexiones(){
        System.out.println("\n== Conexiones Registradas ==");

        if(arr_conexiones.isEmpty()){
            System.out.println("No hay lugares registrados.\n");
            return;
        }

        for(int i=0; i<arr_conexiones.size(); i++){
            Conexion conexion = arr_conexiones.get(i);

            System.out.println(conexion.getOrigen() + " <-> " + conexion.getDestino() + 
                               " || Tiempo: " + conexion.getTiempo());
        }
    }

    // Metodo para mostrar solo los lugares abiertos - Opcion 7 del menu
    public void mostrarLugaresAbiertos(){
        System.out.println("\n== Ciudades Abiertas ==");

        if(arr_lugares.isEmpty()){
            System.out.println("No hay lugares registrados.\n");
            return;
        }

        boolean lugaresAbiertos = false;
        for(int i=0; i<arr_lugares.size(); i++){
            Lugar lugar = arr_lugares.get(i);

            if(lugar.getEstado().equalsIgnoreCase("abierta")){
                lugaresAbiertos = true;

                lugar = arr_lugares.get(i);
                String intereses = String.join(", ", lugar.getInteresesLugar());
            
                System.out.println((i+1) + ". " + lugar.getNombreLugar() + " || " + 
                                    lugar.getDescripcionLugar() + " || " + "Intereses: " +
                                    intereses + " || " + "Estado: " + lugar.getEstado());
            }
        }
    }
}