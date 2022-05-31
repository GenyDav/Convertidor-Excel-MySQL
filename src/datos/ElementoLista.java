package datos;

/**
 * Crea nuevos objetos que representan a un elemento, ya sea de una base de 
 * datos (representando una tabla) o de un archivo de Excel (representando una 
 * hoja).
 * @author Geny
 * @version 1.0
 */
public class ElementoLista {
    private String nombre;
    private int posicion;
    private boolean seleccionado;

    /**
     * Crea un nuevo objeto al que se le asgina un nombre, su posición dentro de 
     * la lista completa de elementos y se inicializa como no seleccionado.
     * @param nombre Nombre del elemento.
     * @param posicion Posición del elemento.
     */
    public ElementoLista(String nombre, int posicion) {
        this.nombre = nombre;
        this.posicion = posicion;
        seleccionado = false;
    }

    /**
     * Método que se utiliza para obtener el nombre del elemento.
     * @return Nombre del elemento.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Método utilizado para obtener la posición que ocupa un elemento en la lista
     * completa de elementos a la que pertenece al cargar una base de datos o un 
     * archivo de Excel.
     * @return Posición del elemento. 
     */
    public int getPosicion() {
        return posicion;
    }
    
    /**
     * Método que devuelve un valor booleano que indica si el elemeto está 
     * seleccionado para su exportación o importación.
     * @return Booleano que indica si el elemento está seleccionado.
     */
    public boolean estaSeleccionado(){
        return seleccionado;
    }
    
    /**
     * Método utilizado para cambiar el estado del elemento (seleccionado o no
     * seleccionado) en una lista.
     * @param sel Vslor booleano que indica el estado del elemento.
     */
    public void setSeleccionado(boolean sel){
        seleccionado = sel;
    }
    
    /**
     * Método utilizado por la lista donde se muestran los elementos seleccionados
     * para obtener el nombre de los elementos.
     * @return Nombre del elemento seleccionado.
     */
    @Override
    public String toString(){
        //return nombre+" "+posicion;
        return nombre;
    }
}
