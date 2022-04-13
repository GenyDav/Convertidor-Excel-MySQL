/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import java.util.Calendar;

/**
 *
 * @author Geny
 */
public class Tiempo {
    Calendar c;      
    int hora;
    int min;
    int seg;
    
    public Tiempo(){
        hora = 0;
        min = 0;
        seg = 0;
    }
    
    public String obtenerTiempo(){
        c = Calendar.getInstance();
        hora = c.get(Calendar.HOUR_OF_DAY);
        min = c.get(Calendar.MINUTE);
        seg = c.get(Calendar.SECOND);
        return formatear(hora) + ":" + formatear(min) + ":" + formatear(seg);
    }
    
    public String formatear(int info){
        String dato = (info<10)?"0"+String.valueOf(info):String.valueOf(info);
        return dato;
    }
}
