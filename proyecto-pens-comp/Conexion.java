public class Conexion {

    // Atributos 
    private String origen; 
    private String destino; 
    private float tiempoBase; 
    
    // Constructor 
    public Conexion(String origen, String destino, float tiempo){
        this.origen = origen; 
        this.destino = destino; 
        this.tiempoBase = tiempo; 
    }

    // Getters 
    public String getOrigen(){
        return origen;
    }

    public String getDestino(){
        return destino;
    }

    public float getTiempoBase(){
        return tiempoBase;
    }
}