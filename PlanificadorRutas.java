public class PlanificadorRutas {

    // Atributos 
    private String origen;
    private String destino; 
    private String[] ruta;

    // Constructor 
    public PlanificadorRutas(String origen, String destino, String[] ruta){
        this.origen = origen; 
        this.destino = destino; 
        this.ruta = ruta;
    }

    // Getters y Setters
    public String[] getRuta(){
        return ruta;
    }
}
