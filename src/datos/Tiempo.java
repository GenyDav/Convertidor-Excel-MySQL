package datos;

import java.util.Calendar;

/**
 * Clase que crea un objeto que permite obtener el tiempo del sistema con un 
 * formato adecuado para mostrarse en los reportes al crear una base de datos.
 * @author Geny
 * @version 1.0
 */
public class Tiempo {
    private Calendar c;      
    private int hora;
    private int min;
    private int seg;
    
    /**
     * Inicializa los valores de los campos de tiempo a cero.
     */
    public Tiempo(){
        hora = 0;
        min = 0;
        seg = 0;
    }
    
    /**
     * Método que obtiene la hora actual del sistema con el formato hh:mm:ss
     * @return tiempo con el formato hh:mm:ss
     */
    public String obtenerTiempo(){
        c = Calendar.getInstance();
        hora = c.get(Calendar.HOUR_OF_DAY);
        min = c.get(Calendar.MINUTE);
        seg = c.get(Calendar.SECOND);
        return formatear(hora) + ":" + formatear(min) + ":" + formatear(seg);
    }
    
    /**
     * Método que formatea los datos de tiempo que recibe para que su longitud
     * sea de dos dígitos.
     * @param info dato correspondiente a la hora, minuto o segundo.
     * @return dato entero con el formato a dos dígitos.
     */
    public String formatear(int info){
        String dato = (info<10)?"0"+String.valueOf(info):String.valueOf(info);
        return dato;
    }
}
