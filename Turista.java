public class Turista {

    // Atributos 
    private String nombreTurista;
    private String[] preferenciasTurista; 

    // Constructor 
    public Turista(String nombreTurista, String[] preferenciasTurista){
        this.nombreTurista = nombreTurista; 
        this.preferenciasTurista = preferenciasTurista;
    }

    // Getters y Setters 
    public String getNombreTurista(){
        return nombreTurista;
    }

    public String[] getPreferenciasTurista(){
        return preferenciasTurista;
    }
}
