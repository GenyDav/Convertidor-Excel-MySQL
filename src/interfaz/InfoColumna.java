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
    String nombre;
    int tipo;
    int tam;
    boolean primaryKey, notNull, unique, unsigned, autoIncrement;
    
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

    /*public void setNombre(String nombre) {
        this.nombre = nombre;
    }*/
    
    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getTam() {
        return tam;
    }

    public void setTam(int tam) {
        this.tam = tam;
    }

    public boolean isPk() {
        return primaryKey;
    }

    public void setPk(boolean pk) {
        this.primaryKey = pk;
    }

    public boolean isNn() {
        return notNull;
    }

    public void setNn(boolean nn) {
        this.notNull = nn;
    }

    public boolean isUq() {
        return unique;
    }

    public void setUq(boolean uq) {
        this.unique = uq;
    }

    public boolean isUn() {
        return unsigned;
    }

    public void setUn(boolean un) {
        this.unsigned = un;
    }

    public boolean isAi() {
        return autoIncrement;
    }

    public void setAi(boolean ai) {
        this.autoIncrement = ai;
    }
}
