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
public class InfoColumna{
    private String nombre;
    private int tipo;
    private int tam;
    private boolean primaryKey, notNull, unique, unsigned, autoIncrement;
    
    public InfoColumna(String nombre){
        this.nombre = nombre;
        tam = 0;
        primaryKey = false;
        notNull = false;
        unique = false;
        unsigned = false;
        autoIncrement = false;
    }

    public String getNombre() {
        return nombre;
    }
    
    public int getTipo() {
        return tipo;
    }

    public int getTam() {
        return tam;
    }

    public boolean getPK() {
        return primaryKey;
    }

    public boolean getNN() {
        return notNull;
    }

    public boolean getUQ() {
        return unique;
    }

    public boolean getUN() {
        return unsigned;
    }

    public boolean getAI() {
        return autoIncrement;
    }
    
    /*public void setNombre(String nombre) {
        this.nombre = nombre;
    }*/
    
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    
    public void setTam(int tam) {
        this.tam = tam;
    }
    
    public void setPK(boolean pk) {
        this.primaryKey = pk;
    }
    
    public void setNN(boolean nn) {
        this.notNull = nn;
    }
    
    public void setUQ(boolean uq) {
        this.unique = uq;
    }
    
    public void setUN(boolean un) {
        this.unsigned = un;
    }
    
    public void setAI(boolean ai) {
        this.autoIncrement = ai;
    }
}
