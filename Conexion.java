public class Conexion {

    // Atributos 
    private String origen; 
    private String destino; 
    private float tiempo; 
    
    // Constructor 
    public Conexion(String origen, String destino, float tiempo){
        this.origen = origen; 
        this.destino = destino; 
        this.tiempo = tiempo; 
    }

    // Getters y Setters 
    public String getOrigen(){
        return origen;
    }

    public String getDestino(){
        return destino;
    }

    public float getTiempo(){
        return tiempo;
    }
}
