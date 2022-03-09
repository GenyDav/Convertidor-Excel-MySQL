/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

/**
 *
 * @author Geny
 */
public class ElementoLista {
    private String nombre;
    private int posicion;
    private boolean seleccionado;

    public ElementoLista(String nombre, int posicion) {
        this.nombre = nombre;
        this.posicion = posicion;
        seleccionado = false;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPosicion() {
        return posicion;
    }
    
    public boolean getSeleccionado(){
        return seleccionado;
    }
    
    public void setSeleccionado(boolean sel){
        seleccionado = sel;
    }
    
    @Override
    public String toString(){
        //return nombre+" "+posicion;
        return nombre;
    }
}
