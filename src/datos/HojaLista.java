package datos;

import java.util.ArrayList;

/**
 * Clase que representa la información de la estructura de una hoja (nombre de
 * sus columnas y sus tipos de datos junto con sus modificadores) que va a ser
 * utilizada para crear una tabla en la base de datos.
 * @author Geny
 * @version 1.0
 */
public class HojaLista extends ElementoLista{
    private ArrayList<InfoColumna> columnas; // Lista de columnas en la hoja
    
    /**
     * Crea un nuevo objeto que representa la estructura de una hoja del archivo 
     * de Excel abierto. El objeto se crea con una lista de columnas vacía.
     * @param nombre Nombre de la hoja
     * @param posicion Posición de la hoja 
     */
    public HojaLista(String nombre, int posicion){
        super(nombre,posicion);
        columnas = new ArrayList<>();
    }
    
    /**
     * Agrega una nueva columna a la lista de columnas.
     * @param c Objeto que representa la información de la columna.
     */
    public void agregarColumna(InfoColumna c){
        columnas.add(c);
    }
    
    /**
     * Método que devuelve la lista con la información de todas las columnas en
     * la hoja.
     * @return Lista con información de las columnas.
     */
    public ArrayList<InfoColumna> obtenerColumnas(){
        return columnas;
    }
    
    /**
     * Método que permite acceder a la información de la columna en la posición
     * indicada.
     * @param posicion Índice de la columna que se quiere consultar.
     * @return Objeto que representa la información de la columna solicitada.
     */
    public InfoColumna obtenerColumna(int posicion){
        return columnas.get(posicion);
    }
    
    /**
     * Muestra una lista con la descripción de todas las columnas.
     */
    public void mostrarColumnas(){
        System.out.println(getNombre());
        for (InfoColumna columna : columnas) {
            System.out.println("   " + columna.getNombre() + ": " + columna.getPK() +","+ Tipo.NOMBRES[columna.getTipo()]);
        }
        System.out.println();
    }
}
