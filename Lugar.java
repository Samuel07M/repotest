import java.util.ArrayList;

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
    public void imprimirLugares(){
        String intereses = String.join(", ", getInteresesLugar());
        System.out.println(". " + getNombreLugar() + " || " + 
                            getDescripcionLugar() + " || " + "Intereses: " +
                            intereses + " || " + "Estado: " + getEstado());
    }

    // Metodo para mostrar solo lugares abiertos
    public static void imprimirLugaresAbiertos(ArrayList<Lugar> arr_lugares){
        boolean lugaresAbiertos = false;
        for(int i=0; i<arr_lugares.size(); i++){
            Lugar lugar = arr_lugares.get(i);

            if(lugar.getEstado().equalsIgnoreCase("abierta")){
                lugaresAbiertos = true;

                lugar = arr_lugares.get(i);
                String intereses = String.join(", ", lugar.getInteresesLugar());
            
                System.out.println((i+1) + ". " + lugar.getNombreLugar() + " || " + 
                                    lugar.getDescripcionLugar() + " || " + "Intereses: " +
                                    intereses);
            }
        }

        if(lugaresAbiertos == false){
            System.out.println("\nNo hay lugares abiertos.\n");
        }
    }

    // Getters y Setters 
    public String[] getInteresesLugar(){
        return interesesLugar;
    }

    public String getEstado(){
        return estado;
    }

    public boolean isAbierto(){
        return estado.equalsIgnoreCase("abierta");
    }
}