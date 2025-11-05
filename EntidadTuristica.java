public abstract class EntidadTuristica {    // Clase abstracta, no se puede instanciar directamente
    
    // Atributos
    private String nombreLugar; 
    private String descripcionLugar; 

    // Constructor
    public EntidadTuristica(String nombreLugar, String descripcionLugar){
        this.nombreLugar = nombreLugar; 
        this.descripcionLugar = descripcionLugar; 
    }

    // Metodo abstracto para mostrar los lugares (polimorfismo) 
    public abstract void mostrarLugares();

    // Getters y setters 
    public String getNombreLugar(){
        return nombreLugar;
    }

    public String getDescripcionLugar(){
        return descripcionLugar;
    }
}
