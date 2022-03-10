/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import java.util.ArrayList;

/**
 *
 * @author Geny
 */
public class TablaLista extends ElementoLista{
    private ArrayList<InfoColumna> columnas;
    
    public TablaLista(String nombre, int posicion){
        super(nombre,posicion);
        columnas = new ArrayList<>();
    }
    
    public void agregarColumna(InfoColumna c){
        columnas.add(c);
    }
    
    public void modificarColumna(InfoColumna aux, int indice){
        columnas.set(indice, aux);
    }
    
    @Override
    public String toString(){
        return super.getNombre()+columnas.toString();
    }
}
