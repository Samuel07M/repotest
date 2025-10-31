public abstract class EntidadTuristica {    // Clase abstracta, no se puede instanciar directamente
    
    // Atributos
    private String nombreLugar; 
    private String descripcionLugar; 

    // Constructor
    public EntidadTuristica(String nombreLugar, String descripcionLugar){
        this.nombreLugar = nombreLugar; 
        this.descripcionLugar = descripcionLugar; 
    }

    // Metodo que se podra sobreescribir 
    // public abstract void (){}

    // Getters y setters 
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
