import java.io.BufferedWriter;
import java.io.FileReader; 
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class GestorArchivos {
    
    // Atributos
    FileWriter fw; 
    BufferedWriter br; 
    PrintWriter pw;
    FileReader fr; 
    Scanner archivo; 

    // Constructor 
    public GestorArchivos(){
        fw = null;
        pw = null; 
        fr = null; 
        archivo = null; 
    }

    // Metodos para guardar los datos 
    public void guardarLugares(ArrayList<Lugar> arr_lugares){
        try{
            fw = new FileWriter("lugares.txt"); 
            br = new BufferedWriter(fw); 
            pw = new PrintWriter(br);

            for(int i=0; i<arr_lugares.size(); i++){
                Lugar lugar = arr_lugares.get(i);
                String intereses = String.join(",", lugar.getInteresesLugar());
                pw.println(lugar.getNombreLugar() + "|" + lugar.getDescripcionLugar() + "|" + intereses + "|" + lugar.getEstado());
                pw.flush();
            }

            System.out.println("\nLugares guardados exitosamente.\n");
            fw.close();
            br.close();
            pw.close();

        }catch(Exception e){
            System.err.println("\nError guardando lugares: " + e.getMessage() + "\n");
        }
    }

    public void guardarConexiones(ArrayList<Conexion> arr_conexiones){
        try{
            fw = new FileWriter("conexiones.txt"); 
            br = new BufferedWriter(fw); 
            pw = new PrintWriter(br);

            for(int i = 0; i < arr_conexiones.size(); i++){
                Conexion conexion = arr_conexiones.get(i);
                pw.println(conexion.getOrigen() + "|" + conexion.getDestino() + "|" + conexion.getTiempo());
                pw.flush();
            }

            System.out.println("\nConexiones guardadas exitosamente.\n");
            fw.close();
            br.close();
            pw.close();

        }catch(Exception e){
            System.err.println("\nError guardando conexiones: " + e.getMessage() + "\n");
        }
    }

    public void guardarRutas(ArrayList<PlanificadorRutas> arr_planificadorRutas){
        try {
            fw = new FileWriter("rutas.txt"); 
            br = new BufferedWriter(fw); 
            pw = new PrintWriter(br);

            for(int i=0; i<arr_planificadorRutas.size(); i++){
                PlanificadorRutas planificadorRutas = arr_planificadorRutas.get(i);
                String[] ruta_tmp = planificadorRutas.getRuta();
                String ruta = String.join(",", ruta_tmp);
                pw.println(planificadorRutas.getOrigen() + "|" + planificadorRutas.getDestino() + "|" + ruta + "|" + planificadorRutas.getTiempo());
                pw.flush();
            }

            System.out.println("\nRutas guardadas exitosamente.\n");
            fw.close();
            br.close();
            pw.close();

        }catch(Exception e){
            System.err.println("\nError guardando rutas: " + e.getMessage() + "\n");
        }
    }

    // Metodos para cargar los datos
    public ArrayList<Lugar> cargarLugares(){
        ArrayList<Lugar> arr_lugares = new ArrayList<>();

        try{
            fr = new FileReader("lugares.txt");
            archivo = new Scanner(fr);

            while(archivo.hasNextLine()){
                String linea = archivo.nextLine();
                String[] partes = linea.split("\\|"); // Separar por "|"
                if(partes.length == 4){
                    String nombreLugar = partes[0];
                    String descripcionLugar = partes[1];
                    String[] intereses = partes[2].split(","); // Separar intereses por ','
                    for(int i=0; i<intereses.length; i++){
                        intereses[i] = intereses[i].trim().toLowerCase(); // Limpiar espacios
                    }
                    String estado = partes[3];

                    Lugar lugar = new Lugar(nombreLugar, descripcionLugar, intereses, estado);
                    arr_lugares.add(lugar);
                }
            }

            System.out.println("\nLugares cargados exitosamente.\n");
            
            fr.close();
            archivo.close();

        }catch(Exception e){
            System.err.println("\nError leyendo lugares: " + e.getMessage() + "\n");
        }

        return arr_lugares;
    }

    public ArrayList<Conexion> cargarConexiones(){
        ArrayList<Conexion> arr_conexiones = new ArrayList<>();

        try{
            fr = new FileReader("conexiones.txt");
            archivo = new Scanner(fr);

            while(archivo.hasNextLine()){
                String linea = archivo.nextLine();
                String[] partes = linea.split("\\|"); // Separar por '|'
                if(partes.length == 3){
                    String origen = partes[0].trim();
                    String destino = partes[1].trim();
                    float tiempo = Float.parseFloat(partes[2].trim());
                    arr_conexiones.add(new Conexion(origen, destino, tiempo));
                }
            }

            System.out.println("\nConexiones cargadas exitosamente.\n");

            fr.close();
            archivo.close();

        }catch(Exception e){
            System.err.println("\nError leyendo conexiones: " + e.getMessage() + "\n");  
        }
        
        return arr_conexiones;
    }

    public ArrayList<PlanificadorRutas> cargarRutas(){
        ArrayList<PlanificadorRutas> arr_planificadorRutas = new ArrayList<>();

        try{
            fr = new FileReader("rutas.txt");
            archivo = new Scanner(fr);

            while(archivo.hasNextLine()){
                String linea = archivo.nextLine();
                String[] partes = linea.split("\\|"); // Separar por '|'
                if(partes.length == 4){
                    String origen = partes[0].trim();
                    String destino = partes[1].trim();
                    String[] lugares = partes[2].split(","); // Separar los lugares por ','
                    for(int i=0; i<lugares.length; i++){
                        lugares[i] = lugares[i].trim().toLowerCase();
                    }
                    float tiempo = Float.parseFloat(partes[3].trim());
                    arr_planificadorRutas.add(new PlanificadorRutas(origen, destino, lugares, tiempo));
                }
            }

            System.out.println("\nRutas cargadas exitosamente.\n");

            fr.close();
            archivo.close();
        
        }catch(Exception e){
            System.err.println("\nError leyendo rutas: " + e.getMessage() + "\n");  
        }

        return arr_planificadorRutas;
    }
}