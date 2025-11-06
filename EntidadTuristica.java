public abstract class EntidadTuristica {    // Clase abstracta, no se puede instanciar directamente
    
    // Atributos
    private String nombreLugar; 
    private String descripcionLugar; 

    // Constructor
    public EntidadTuristica(String nombreLugar, String descripcionLugar){
        setNombreLugar(nombreLugar); 
        setDescripcionLugar(descripcionLugar);
    }

    // Metodo abstracto para mostrar los lugares (polimorfismo) 
    public abstract void imprimirLugares();

    // Getters y Setters
    public String getNombreLugar(){
        return nombreLugar;
    }

    public String getDescripcionLugar(){
        return descripcionLugar;
    }

    public void setNombreLugar(String nombreLugar){
        this.nombreLugar = nombreLugar;
    }

    public void setDescripcionLugar(String descripcionLugar){
        this.descripcionLugar = descripcionLugar;
    }
}
