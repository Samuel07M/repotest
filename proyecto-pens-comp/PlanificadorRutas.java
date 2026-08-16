import java.util.ArrayList;

public class PlanificadorRutas {

    // Atributos 
    private String origen;
    private String destino; 
    private String[] ruta;
    private float tiempoRuta;

    // Constructor 
    public PlanificadorRutas(String origen, String destino, String[] ruta, float tiempoRuta){
        this.origen = origen; 
        this.destino = destino; 
        this.ruta = ruta;
        this.tiempoRuta = tiempoRuta;
    }

    // Metodos para mostrar y filtrar las rutas 
    public static void imprimirRutas(ArrayList<PlanificadorRutas> arr_planificadorRutas, ArrayList<Conexion> arr_conexiones){
        for(int i=0; i<arr_planificadorRutas.size(); i++){
            PlanificadorRutas ruta = arr_planificadorRutas.get(i);

            Conexion conexion = null;
            for(int j=0; j<arr_conexiones.size(); j++){
                if(arr_conexiones.get(j).getDestino().equalsIgnoreCase(ruta.getDestino())){
                    conexion = arr_conexiones.get(j);
                    break;
                }
            }

            String lugares = String.join(" -> ", ruta.getRuta());
            System.out.println((i+1) + ". Origen: " + ruta.getOrigen() + " || Destino: " + ruta.getDestino());
            System.out.println("   [ " + lugares + " ]");
            System.out.println("   Tiempo Base (hrs): " + conexion.getTiempoBase() + " || "
                               + "Tiempo Adicional de la Ruta: " + ruta.getTiempoRuta()
                               + " || Tiempo total: " + (conexion.getTiempoBase() + ruta.getTiempoRuta()));
        }
    }    

    public static void filtrarRutasOrigen(ArrayList<PlanificadorRutas> arr_planificadorRutas, ArrayList<Conexion> arr_conexiones, String origen){
        for(int i=0; i<arr_planificadorRutas.size(); i++){
            if(origen.equalsIgnoreCase(arr_planificadorRutas.get(i).getOrigen())){
                PlanificadorRutas ruta = arr_planificadorRutas.get(i);
                
                Conexion conexion = null;
                for(int j=0; j<arr_conexiones.size(); j++){
                    if(arr_conexiones.get(j).getOrigen().equalsIgnoreCase(ruta.getOrigen())){
                        conexion = arr_conexiones.get(j);
                        break;
                    }
                }
                
                String lugares = String.join(" -> ", ruta.getRuta());
                System.out.println((i+1) + ". Origen: " + ruta.getOrigen() + " || Destino: " + ruta.getDestino());
                System.out.println("   [ " + lugares + " ]");
                System.out.println("   Tiempo Base (hrs): " + conexion.getTiempoBase() + " || "
                               + "Tiempo Adicional de la Ruta: " + ruta.getTiempoRuta()
                               + " || Tiempo total: " + (conexion.getTiempoBase() + ruta.getTiempoRuta()));

            }
        }
    }

    public static void filtrarRutasDestino(ArrayList<PlanificadorRutas> arr_planificadorRutas, ArrayList<Conexion> arr_conexiones, String destino){
        for(int i=0; i<arr_planificadorRutas.size(); i++){
            if(destino.equalsIgnoreCase(arr_planificadorRutas.get(i).getDestino())){ 
                PlanificadorRutas ruta = arr_planificadorRutas.get(i);

                Conexion conexion = null;
                for(int j=0; j<arr_conexiones.size(); j++){
                    if(arr_conexiones.get(j).getDestino().equalsIgnoreCase(ruta.getDestino())){
                        conexion = arr_conexiones.get(j);
                        break;
                    }
                }

                String lugares = String.join(" -> ", ruta.getRuta());
                System.out.println((i+1) + ". Origen: " + ruta.getOrigen() + " || Destino: " + ruta.getDestino());
                System.out.println("   [ " + lugares + " ]");
                System.out.println("   Tiempo Base (hrs): " + conexion.getTiempoBase() + " || "
                               + "Tiempo Adicional de la Ruta: " + ruta.getTiempoRuta()
                               + " || Tiempo total: " + (conexion.getTiempoBase() + ruta.getTiempoRuta()));

            }
        }
    }

    // Metodo para recomendar destinos (Afinidad)
    public static void afinidad(ArrayList<Lugar> arr_lugares, Turista turista){
        String[] preferenciasTurista = turista.getPreferenciasTurista();
        double[] porcentajes = new double[arr_lugares.size()];
        int[] coincidencias = new int[arr_lugares.size()];
        String[][] coincidenciaIntereses = new String[arr_lugares.size()][]; // guardar intereses comunes

        // Calcular afinidad
        for(int i=0; i<arr_lugares.size(); i++){
            Lugar lugar = arr_lugares.get(i);

            if(lugar.isAbierto()){
                String[] interesesLugar = lugar.getInteresesLugar();
                int contador = 0;
                ArrayList<String> comunes = new ArrayList<>();

                for(int j=0; j<preferenciasTurista.length; j++){
                    for(int k=0; k<interesesLugar.length; k++){
                        if(preferenciasTurista[j].trim().equalsIgnoreCase(interesesLugar[k].trim())){
                            contador++;
                            comunes.add(interesesLugar[k]);
                        }
                    }
                }

                coincidencias[i] = contador;
                coincidenciaIntereses[i] = comunes.toArray(new String[0]);

                if(contador > 0){
                    porcentajes[i] = (contador * 100.0) / interesesLugar.length;
                }
            }
        }

        // Ordenar de forma descendente
        for(int i=0; i<arr_lugares.size()-1; i++){
            for(int j=i+1; j<arr_lugares.size(); j++){
                if(porcentajes[j] > porcentajes[i]){
                    // Intercambiar porcentajes
                    double temp_p = porcentajes[i];
                    porcentajes[i] = porcentajes[j];
                    porcentajes[j] = temp_p;

                    // Intercambiar coincidencias
                    int temp_c = coincidencias[i];
                    coincidencias[i] = coincidencias[j];
                    coincidencias[j] = temp_c;

                    // Intercambiar lugares
                    Lugar temp_l = arr_lugares.get(i);
                    arr_lugares.set(i, arr_lugares.get(j));
                    arr_lugares.set(j, temp_l);

                    // Intercambiar intereses comunes
                    String[] temp_inter = coincidenciaIntereses[i];
                    coincidenciaIntereses[i] = coincidenciaIntereses[j];
                    coincidenciaIntereses[j] = temp_inter;
                }
            }
        }

        // Imprimir resultados
        boolean hayCoincidencias = false;

        for(int i=0; i<arr_lugares.size(); i++){
            if(coincidencias[i] > 0){
                hayCoincidencias = true;

                System.out.println("- " + arr_lugares.get(i).getNombreLugar() + ": " + 
                                   coincidencias[i] + " intereses turisticos coincidentes (" +
                                   String.format("%.2f", porcentajes[i]) + "% de afinidad)");

                // Mostrar intereses en común
                System.out.print("  Intereses compartidos: ");
                String[] comunes = coincidenciaIntereses[i];
                for(int j=0; j<comunes.length; j++){
                    System.out.print(comunes[j]);
                    if(j<comunes.length - 1){
                        System.out.print(", ");
                    }
                }
                System.out.println("\n");
            }
        }

        if(hayCoincidencias == false){
            System.out.println("\nNo hay lugares con intereses compartidos.\n");
        }
    }

    // Getters
    public String[] getRuta(){
        return ruta;
    }

    public String getOrigen(){
        return origen;
    }

    public String getDestino(){
        return destino;
    }

    public float getTiempoRuta(){
        return tiempoRuta;
    }
}