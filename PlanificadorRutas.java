import java.util.ArrayList;

public class PlanificadorRutas {

    // Atributos 
    private String origen;
    private String destino; 
    private String[] ruta;
    private float tiempo;

    // Constructor 
    public PlanificadorRutas(String origen, String destino, String[] ruta, float tiempo){
        this.origen = origen; 
        this.destino = destino; 
        this.ruta = ruta;
        this.tiempo = tiempo;
    }

    // Getters y Setters
    public String[] getRuta(){
        return ruta;
    }

    public String getOrigen(){
        return origen;
    }

    public String getDestino(){
        return destino;
    }

    public float getTiempo(){
        return tiempo;
    }

    // Metodos para mostrar y filtrar las rutas 
    public static void mostrarRutas(ArrayList<PlanificadorRutas> arr_planificadorRutas){
        for(int i=0; i<arr_planificadorRutas.size(); i++){
            PlanificadorRutas ruta = arr_planificadorRutas.get(i);
            String lugares = String.join(" -> ", ruta.getRuta());
            System.out.println((i + 1) + ". Origen: " + ruta.getOrigen() +
                                " || Destino: " + ruta.getDestino() +
                                " || Ruta: " + lugares +
                                " || Tiempo: " + ruta.getTiempo() + " horas");
        }
    }    

    public static void filtrarRutas(ArrayList<PlanificadorRutas> arr_planificadorRutas, String origen){
        for(int i=0; i<arr_planificadorRutas.size(); i++){
            if(origen.equalsIgnoreCase(arr_planificadorRutas.get(i).getOrigen())){
                PlanificadorRutas ruta = arr_planificadorRutas.get(i);
                String lugares = String.join(" -> ", ruta.getRuta());
                System.out.println((i + 1) + ". Origen: " + ruta.getOrigen() +
                                    " || Destino: " + ruta.getDestino() +
                                    " || Ruta: " + lugares +
                                    " || Tiempo: " + ruta.getTiempo() + " horas");

            }
        }
    }
}
