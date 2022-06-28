package lectura_escritura;

import java.sql.SQLException;
import javax.swing.SwingWorker;

/**
 * Clase abstracta de la que se pueden definir nuevos procesos ejecutados en 
 * segundo plano para exportar o importar una base de datos. Define el método 
 * que permite identificar las excepciones que pueden hacer que un proceso se 
 * interrumpa.
 * @author Geny
 * @version 1.0
 */
public abstract class Generador extends SwingWorker<Void,Integer> {
    /**
     * Método que identifica las excepciones ocurridas que pueden hacer que el 
     * proceso se detenga completamente. Si ocurre una excepción de esa clase, 
     * se llama al método cancel() del proceso en segundo plano para terminalo. 
     * Si la excepción no es de tipo crítico, el proceso continúa.
     * @param ex Error ocurrido al intentar realizar consultas o modificar la 
     * base de datos.
     */
    protected void identificarFallo(SQLException ex){
        switch(ex.getErrorCode()){
            case 0:    // Comminication link failure
            case 22:   // No se puede abrir el archivo (demasiados archivos abiertos)
            case 24:   // No se puede abrir el archivo (demasiados archivos abiertos)
            case 126:  // Index file is crashed
            case 127:  // Record-file is crashed
            case 134:  // Record was already deleted (or record file crashed)
            case 144:  // Table is crashed and last repair failed
            case 145:  // Table was marked as crashed and should be repaired
            case 1021: // SQLSTATE: HY000 (ER_DISK_FULL) Disco lleno (%s). Esperando para que se libere algo de espacio...
            case 1037: // SQLSTATE: HY001 (ER_OUTOFMEMORY) Mensaje: Memoria insuficiente. Reinicie el demonio e intentelo otra vez (necesita %d bytes)
            case 1038: // SQLSTATE: HY001 (ER_OUT_OF_SORTMEMORY)Mensaje: Memoria de ordenacion insuficiente. Incremente el tamano del buffer de ordenacion
            case 1040: // SQLSTATE: 08004 (ER_CON_COUNT_ERROR) Mensaje: Demasiadas conexiones
            case 1041: // SQLSTATE: HY000 (ER_OUT_OF_RESOURCES) Mensaje: Memoria/espacio de tranpaso insuficiente
            case 1053: // SQLSTATE: 08S01 (ER_SERVER_SHUTDOWN) Mensaje: Desconexion de servidor en proceso
            case 1077: // SQLSTATE: HY000 (ER_NORMAL_SHUTDOWN) Mensaje: %s: Apagado normal
            case 1078: // SQLSTATE: HY000 (ER_GOT_SIGNAL) Mensaje: %s: Recibiendo signal %d. Abortando!
            case 1079: // SQLSTATE: HY000 (ER_SHUTDOWN_COMPLETE) Mensaje: %s: Apagado completado
            case 1080: // SQLSTATE: 08S01 (ER_FORCING_CLOSE) Mensaje: %s: Forzando a cerrar el thread %ld usuario: '%s'
            case 1105: // SQLSTATE: HY000 (ER_UNKNOWN_ERROR) Mensaje: Error desconocido
            case 1146: // La actualizacion de MySQL no tuvo exito           
            case 1152: // SQLSTATE: 08S01 (ER_ABORTING_CONNECTION) Mensaje: Conexión abortada %ld para db: '%s' usuario: '%s' (%s)
            case 1184: // SQLSTATE: 08S01 (ER_NEW_ABORTING_CONNECTION) Mensaje: Abortada conexión %ld para db: '%s' usuario: '%s' servidor: '%s' (%s)
            case 1236: // MySQL Replication
            case 2000: // (CR_UNKNOWN_ERROR) Mensaje: Unknown MySQL error
            case 2001: // (CR_SOCKET_CREATE_ERROR) Mensaje: Can't create UNIX socket (%d) 
            case 2002: // (CR_CONNECTION_ERROR) Mensaje: Can't connect to local MySQL server through socket '%s' (%d)
            case 2003: // (CR_CONN_HOST_ERROR) Mensaje: Can't connect to MySQL server on '%s' (%d)
            case 2004: // (CR_IPSOCK_ERROR) Mensaje: Can't create TCP/IP socket (%d)
            case 2005: // (CR_UNKNOWN_HOST) Mensaje: Unknown MySQL server host '%s' (%d)
            case 2006: // (CR_SERVER_GONE_ERROR) Mensaje: MySQL server has gone away
            case 2008: // (CR_OUT_OF_MEMORY) Mensaje: MySQL client ran out of memory
            case 2013: // (CR_SERVER_LOST) Mensaje: Lost connection to MySQL server during query
            case 2055: // (CR_SERVER_LOST_EXTENDED) Mensaje: Lost connection to MySQL server at '%s', system error: %d
                cancel(true);
                publish(0);
                mostrarMsgError(ex.getErrorCode());   
        }  
    }
    
    /**
     * Muestra un mensaje cuando ocurre una excepción que hace que el proceso
     * se detenga completamente.
     * @param codigo Código de la excepción MySQL.
     */
    public abstract void mostrarMsgError(int codigo);
}
