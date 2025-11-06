import java.util.ArrayList;
import java.util.Scanner;

public class App {
    public static void main(String[] args){
        App app = new App();
        Menu menu = new Menu(app); 
        GestorArchivos gestorArchivos = new GestorArchivos();

        menu.mostrarMenu();

        gestorArchivos.guardarLugares(app.arr_lugares);
        gestorArchivos.guardarConexiones(app.arr_conexiones);
        gestorArchivos.guardarRutas(app.arr_planificadorRutas);
        System.out.println("\nSaliendo...\n");
    }

    // Declaracion de entrada de datos 
    Scanner teclado = new Scanner(System.in);
    ArrayList<Lugar> arr_lugares = new ArrayList<>();
    ArrayList<Turista> arr_turistas = new ArrayList<>();
    ArrayList<Conexion> arr_conexiones = new ArrayList<>();
    ArrayList<PlanificadorRutas> arr_planificadorRutas = new ArrayList<>();

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
            System.out.print("Intereses turisticos (separados por comas): "); 
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

        if(origen.equals(destino)){
            System.out.println("\nNo se puede registrar la conexion de un lugar consigo mismo.\n");
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

        System.out.print("Tiempo de viaje base (hrs): ");
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
            if(arr_turistas.get(i).getNombreTurista().trim().equalsIgnoreCase(nombreTurista)){
                verificarTurista = arr_turistas.get(i); 
            }
        }

        if(verificarTurista == null){
            System.out.print("Preferencias (separadas por comas): "); 
                String preferenciasTexto = teclado.nextLine(); 
                String[] preferenciasTurista_tmp = preferenciasTexto.split(","); // Separa por comas
                String[] preferenciasTurista = new String[preferenciasTurista_tmp.length];
                for(int i=0; i<preferenciasTurista_tmp.length; i++) {
                    preferenciasTurista[i] = preferenciasTurista_tmp[i].trim().toLowerCase(); // Limpiar espacios en blanco
                }
            
            arr_turistas.add(new Turista(nombreTurista, preferenciasTurista));
            System.out.println("\nTurista registrado exitosamente.\n");
        }
        else{
            System.out.println("\nEl Turista ya esta registrado.\n");
        }
    }

    // Metodo para registrar rutas - Opcion 4 del menu
    public void registrarRutas(){
        System.out.println("\n== Registrar Ruta ==");
        
        if(arr_lugares.isEmpty()){
            System.out.println("\nNo hay lugares registrados.\n");
            return;
        }

        System.out.print("Lugares (separados por comas): ");
            String rutaTexto = teclado.nextLine();
            String[] rutaTexto_tmp = rutaTexto.split(","); // Separa por comas
            String[] ruta = new String[rutaTexto_tmp.length];
            for(int i=0; i<rutaTexto_tmp.length; i++){
                ruta[i] = rutaTexto_tmp[i].trim().toLowerCase(); // Limpiar espacios en blanco
            }
        
        String origen = ruta[0];
        String destino = ruta[ruta.length-1];

        boolean origenExistencia = false; 
        for(int i=0; i<arr_lugares.size(); i++){
            if(arr_lugares.get(i).getNombreLugar().equalsIgnoreCase(origen)){
                origenExistencia = true; 
                break;
            }
        }

        boolean destinoExistencia = false; 
        for(int i=0; i<arr_lugares.size(); i++){
            if(arr_lugares.get(i).getNombreLugar().equalsIgnoreCase(destino)){
                destinoExistencia = true; 
                break;
            }
        }

        if(origenExistencia == false){
            System.out.println("\nEl origen " + origen + " no esta registrado.\n");
            return;
        }

        if(destinoExistencia == false){
            System.out.println("\nEl destino " + destino + " no esta registrado.\n");
            return;
        }

        boolean verificarConexion = false;
        float tiempoBase = 0;
        for(int i=0; i<arr_conexiones.size(); i++){
            Conexion conexion = arr_conexiones.get(i);
            if(conexion.getOrigen().trim().equalsIgnoreCase(origen) && conexion.getDestino().trim().equalsIgnoreCase(destino)){
                verificarConexion = true;
                tiempoBase = conexion.getTiempoBase();
            }
            if(conexion.getOrigen().trim().equalsIgnoreCase(destino) && conexion.getDestino().trim().equalsIgnoreCase(origen)){
                verificarConexion = true;
                tiempoBase = conexion.getTiempoBase();
            }
        }

        if(verificarConexion == true){
            System.out.println("Tiempo base (hrs): " + tiempoBase);
            System.out.print("Tiempo adicional de la ruta (hrs): ");
                float tiempoRuta = teclado.nextFloat();
                teclado.nextLine();
            arr_planificadorRutas.add(new PlanificadorRutas(origen, destino, ruta, tiempoRuta));
            System.out.println("\nRuta registrada exitosamente.\n");
        }
        else{
            System.out.println("\nNo hay conexion entre " + origen + " y " + destino + ".\n");
        }
    }

    // Metodo para mostrar todos los lugares registrados - Opcion 5 del menu
    public void mostrarLugares(){
        System.out.println("\n== Lugares Registrados ==");

        if(arr_lugares.isEmpty()){
            System.out.println("\nNo hay lugares registrados.\n");
            return;
        }

        for(int i=0; i<arr_lugares.size(); i++){
            EntidadTuristica entidadTuristica = arr_lugares.get(i); // tipo general
            System.out.print((i+1));
            entidadTuristica.imprimirLugares(); // polimorfismo en acción
        }
    }

    // Metodo para mostrar todas las conexiones registradas - Opcion 6 del menu
    public void mostrarConexiones(){
        System.out.println("\n== Conexiones Registradas ==");

        if(arr_conexiones.isEmpty()){
            System.out.println("\nNo hay lugares registrados.\n");
            return;
        }

        for(int i=0; i<arr_conexiones.size(); i++){
            Conexion conexion = arr_conexiones.get(i);

            System.out.println(conexion.getOrigen() + " <-> " + conexion.getDestino() + 
                               " || Tiempo Base (hrs): " + conexion.getTiempoBase());
        }

        System.out.println("");
    }

    // Metodo para mostrar solo los lugares abiertos - Opcion 7 del menu
    public void mostrarLugaresAbiertos(){
        System.out.println("\n== Lugares Abiertos ==");

        if(arr_lugares.isEmpty()){
            System.out.println("\nNo hay lugares registrados.\n");
            return;
        }

        Lugar.imprimirLugaresAbiertos(arr_lugares);
        System.out.println("");
    }



    // Metodo para recomendar destinos - Opcion 8 del menu
    public void recomendarDestinos(){
        System.out.println("\n== Recomendaciones ==");

        if(arr_lugares.isEmpty()){
            System.out.println("\nNo hay lugares registrados.\n");
            return;
        }

        if(arr_turistas.isEmpty()){
            System.out.println("\nNo hay turistas registrados.\n");
            return;
        }

        System.out.print("Nombre del Turista: ");
            String nombreTurista = teclado.nextLine();
            Turista verificarTurista = null; 
            for(int i=0; i<arr_turistas.size(); i++){
                if(arr_turistas.get(i).getNombreTurista().trim().equalsIgnoreCase(nombreTurista)){
                    verificarTurista = arr_turistas.get(i); 
                }
            }
        
        if(verificarTurista != null){
            System.out.println("");
            PlanificadorRutas.afinidad(arr_lugares, verificarTurista);
        }
        else{
            System.out.println("\nEl Turista no esta registrado.\n");
        }
    }


    // Metodo para ver si dos lugares estan conectados - Opcion 9 del menu
    public void lugaresConectados(){
        System.out.println("\n== Lugares Conectados ==");

        if(arr_conexiones.isEmpty()){
            System.out.println("\nNo hay lugares registrados.\n");
            return;
        }

        System.out.print("Lugar A: ");
            String lugarA = teclado.nextLine();
        Lugar verificarLugarA = null; // Objeto temporal 
        for(int i=0; i<arr_lugares.size(); i++){
            if (arr_lugares.get(i).getNombreLugar().trim().equalsIgnoreCase(lugarA)){
                verificarLugarA = arr_lugares.get(i); 
            }
        }

        if(verificarLugarA == null){
            System.out.println("\nEl lugar no esta registrado.\n");
            return;
        }

        System.out.print("Lugar B: ");
            String lugarB = teclado.nextLine();
        Lugar verificarLugarB = null; // Objeto temporal 
        for(int i=0; i<arr_lugares.size(); i++){
            if (arr_lugares.get(i).getNombreLugar().trim().equalsIgnoreCase(lugarB)){
                verificarLugarB = arr_lugares.get(i); 
            }
        }

        if(verificarLugarB == null){
            System.out.println("\nEl lugar no esta registrado.\n");
            return;
        }

        boolean estanConectadas = false;
        for(int i=0; i<arr_conexiones.size(); i++){
            Conexion conexion = arr_conexiones.get(i);

            if(conexion.getOrigen().equalsIgnoreCase(lugarA) && conexion.getDestino().equalsIgnoreCase(lugarB)){
                System.out.println("\n" + lugarA + " y " + lugarB + " estan conectadas.\n");
                estanConectadas = true;
            }
        }

        if(estanConectadas == false){
            System.out.println("\nLos lugares no estan conectados.\n");
        }
    }

    // Metodos para mostrar las rutas - Opcion 10 del menu
    public void mostrarRutas(){
        System.out.println("\n== Rutas Registradas ==");

        if(arr_planificadorRutas.isEmpty()){
            System.out.println("\nNo hay rutas registradas.\n");
            return;
        }

        PlanificadorRutas.imprimirRutas(arr_planificadorRutas, arr_conexiones);
        System.out.println("");
    }

    public void mostrarRutasOrigen(){
        System.out.println("\n== Filtrar Rutas por Origen ==");

        if(arr_planificadorRutas.isEmpty()){
            System.out.println("\nNo hay rutas registradas.\n");
            return;
        }

        System.out.print("Origen: ");
            String origen = teclado.nextLine();
        Lugar verificarLugar = null; // Objeto temporal 
        for(int i=0; i<arr_lugares.size(); i++){
            if(arr_lugares.get(i).getNombreLugar().trim().equalsIgnoreCase(origen)){
                verificarLugar = arr_lugares.get(i); 
            }
        }

        if(verificarLugar == null){
            System.out.println("\nEl lugar no esta registrado.\n");
        }
        else{
            PlanificadorRutas.filtrarRutasOrigen(arr_planificadorRutas, arr_conexiones, origen);
        }
    }

    public void mostrarRutasDestino(){
        System.out.println("\n== Filtrar Rutas por Destino ==");

        if(arr_planificadorRutas.isEmpty()){
            System.out.println("\nNo hay rutas registradas.\n");
            return;
        }

        System.out.print("Destino: ");
            String destino = teclado.nextLine();
        Lugar verificarLugar = null; // Objeto temporal 
        for(int i=0; i<arr_lugares.size(); i++){
            if(arr_lugares.get(i).getNombreLugar().trim().equalsIgnoreCase(destino)){
                verificarLugar = arr_lugares.get(i); 
            }
        }

        if(verificarLugar == null){
            System.out.println("\nEl lugar no esta registrado.\n");
        }
        else{
            PlanificadorRutas.filtrarRutasDestino(arr_planificadorRutas, arr_conexiones, destino);
        }

    }

    // Metodo para cargar datos - Opcion 11 del menu
    public void cargarDatos(){
        GestorArchivos gestorArchivos = new GestorArchivos();
        arr_lugares = gestorArchivos.cargarLugares();
        arr_conexiones = gestorArchivos.cargarConexiones();
        arr_planificadorRutas = gestorArchivos.cargarRutas();
    }
}