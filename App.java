import java.util.Scanner;

public class App {
    public static void main(String[] args){
        App app = new App();
        Menu menu = new Menu(app); 

        menu.mostrarMenu();
    }

    Scanner teclado = new Scanner(System.in);

    // Metodo para registrar nuevas ciudades - Opcion 1 del menu
    public void registrarLugar(){
        System.out.println("\n== Registrar Lugar ==");
        System.out.print("Nombre: "); 
            String nombreCiudad = teclado.nextLine(); 
        System.out.print("Intereses turisticos (separados por ,): "); 
            String interesesTexto = teclado.nextLine(); 
            String[] interesesLugar = interesesTexto.split(","); // Separa por comas
        System.out.print("Estado (abierto/cerrado): ");
            String estado = teclado.nextLine();

        System.out.println("\nLugar creado exitosamente."); 
    }

}