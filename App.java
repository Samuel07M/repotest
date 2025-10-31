import java.util.ArrayList;
import java.util.Scanner;

public class App {
    public static void main(String[] args){
        App app = new App();
        Menu menu = new Menu(app); 

        menu.mostrarMenu();
    }

    // Declaracion de entrada de datos 
    Scanner teclado = new Scanner(System.in);
    ArrayList<Lugar> arr_lugares = new ArrayList<>();
    ArrayList<Turista> arr_turistas = new ArrayList<>();

    // Metodo para registrar nuevas ciudades - Opcion 1 del menu
    public void registrarLugar(){
        System.out.println("\n== Registrar Lugar ==");
        System.out.print("Nombre: "); 
            String nombreLugar = teclado.nextLine(); 
           
        // Verificacion de existencia para evitar duplicados 
        Lugar verificarLugar = null; // Objeto temporal 
        for(int i=0; i<arr_lugares.size(); i++){
            if (arr_lugares.get(i).getNombreLugar().trim().equalsIgnoreCase(nombreLugar)){
                verificarLugar = arr_lugares.get(i); 
            }
        }

        if(verificarLugar == null){
            System.out.print("Intereses turisticos (separados por ,): "); 
                String interesesTexto = teclado.nextLine(); 
                String[] interesesLugar_tmp = interesesTexto.split(","); // Separa por comas
                String[] interesesLugar = new String[interesesLugar_tmp.length];
                for(int i=0; i<interesesLugar_tmp.length; i++) {
                    interesesLugar[i] = interesesLugar_tmp[i].trim().toLowerCase(); // Limpiar espacios en blanco
                }
            System.out.print("Descripcion: ");
                String descripcionLugar = teclado.nextLine();
            System.out.print("Estado (abierto/cerrado): ");
                String estado = teclado.nextLine();
            
            arr_lugares.add(new Lugar(nombreLugar, descripcionLugar, interesesLugar, estado));
            System.out.println("\nLugar registrado exitosamente.\n"); 
        }
        else{
            System.out.println("\nEl Lugar ya esta registrado.\n");
        }
    }

    // Metodo para registrar turistas - Opcion 3 del menu
    public void registrarTurista(){
        System.out.println("\n== Registrar Turista ==");
        System.out.print("Nombre: ");
            String nombreTurista = teclado.nextLine();
        
        // Verificacion de existencia para evitar duplicados
        Turista verificarTurista = null; 
        for(int i=0; i<arr_turistas.size(); i++){
            if (arr_turistas.get(i).getNombreTurista().trim().equalsIgnoreCase(nombreTurista)){
                verificarTurista = arr_turistas.get(i); 
            }
        }

        if(verificarTurista == null){
            System.out.print("Preferencias (separadas por ,): "); 
                String preferenciasTexto = teclado.nextLine(); 
                String[] preferenciasTurista_tmp = preferenciasTexto.split(","); // Separa por comas
                String[] preferenciasTurista = new String[preferenciasTurista_tmp.length];
                for(int i=0; i<preferenciasTurista_tmp.length; i++) {
                    preferenciasTurista[i] = preferenciasTurista_tmp[i].trim().toLowerCase(); // Limpiar espacios en blanco
                }
            
            arr_turistas.add(new Turista(nombreTurista, preferenciasTurista));
            System.err.println("\nTurista registrado exitosamente.\n");
        }
        else{
            System.out.println("\nEl Turista ya esta registrado.\n");
        }

    }

}