public class Lugar extends EntidadTuristica {      // Herencia entre clases
    
    // Atributos adicionales a los de la superclase
    private String[] interesesLugar;
    private String estado; 
    
    // Constructor
    public Lugar(String nombreLugar, String descripcionLugar, String[] interesesLugar, String estado){
        super(nombreLugar, descripcionLugar);  
        this.interesesLugar = interesesLugar;
        this.estado = estado;
    }

    // Implementacion para el metodo polimorfico de la superclase
    @Override
    public void mostrarLugares(){
        String intereses = String.join(", ", getInteresesLugar());
        System.out.println(". " + getNombreLugar() + " || " + 
                            getDescripcionLugar() + " || " + "Intereses: " +
                            intereses + " || " + "Estado: " + getEstado());
    }

    // Getters y Setters 
    public String[] getInteresesLugar(){
        return interesesLugar;
    }

    public String getEstado(){
        return estado;
    }
}
