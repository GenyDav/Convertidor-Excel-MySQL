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
public class InfoColumna {
    String nombre;
    int tipo;
    int tam;
    boolean pk, nn, uq, un, ai;
    
    public InfoColumna(String nombre){
        this.nombre = nombre;
        tam = 0;
        pk = false;
        nn = false;
        uq = false;
        un = false;
        ai = false;
    }
}
