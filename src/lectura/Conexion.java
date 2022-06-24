package lectura;

import datos.ElementoLista;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Clase que permite establecer la comunicación entre el programa y el servidor 
 * de bases de datos para realizar las operaciones de lectura/escritura.
 * @author Geny
 * @version 1.0
 */
public class Conexion {
    private String servidor;
    private String usr;
    private String passwd;
    private Connection conn;        // conexión con el servidor de BD
    private DatabaseMetaData info;  // información sobre la conexión
    private ResultSet rs;           // almacena los datos obtenidos en una consulta
    private Statement s;            // objeto con el que se realizan las consultar a la BD
    
    /**
     * Constructor. Encargado de establecer la conexión con el servidor.
     * @param servidor dirección IP del servidor de bases de datos
     * @param usr nombre del usuario
     * @param passwd contraseña del usuario
     * @throws ClassNotFoundException error que ocurre si no se encuentra la librería mysql-conector-java
     * @throws SQLException Error al intentar conectar con el servidor.
     */
    public Conexion(String servidor, String usr, String passwd) throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver");
        this.servidor = servidor;
        this.usr = usr;
        this.passwd = passwd;
        conn = DriverManager.getConnection("jdbc:mysql://"+servidor+"/",usr,passwd);
        info = conn.getMetaData();  
        s = conn.createStatement();
        rs = null;
    }
    
    public Conexion crearNuevaConexion() throws ClassNotFoundException, SQLException{
        return new Conexion(servidor,usr,passwd);
    }
    
    /**
     * Método que devuelve una lista con los nombres de las bases de datos del 
     * servidor.
     * @return arrayList que contiene los nombres de las bases de datos encontradas
     * en el servidor
     * @throws SQLException Error al realizar la consulta en el servidor.
     */
    public ArrayList<String> obtenerBasesDeDatos() throws SQLException{
        ArrayList<String> bases = new ArrayList<>();
        rs = info.getCatalogs();
        while(rs.next()) {
           bases.add(rs.getString(1));   // añadir la columna que tiene el nombre de la BD
        }
        return bases;
    }
     
    /**
     * Permite obtener una lista de objetos que representan a las tablas de una
     * base de datos.
     * @param nomBase nombre de la base de datos de la que se quieren conocer
     * sus tablas
     * @return arrayList de objetos ElementoLista que representan las tablas de
     * la base de datos seleccionada
     * @throws SQLException Error al realizar la consulta en el servidor.
     */
    public ArrayList<ElementoLista> obtenerTablas(String nomBase) throws SQLException{
        ArrayList<ElementoLista> tablas = new ArrayList<>();
        rs = info.getTables(nomBase,null,null,null);
        int cont = 0; // posición de la tabla en la base de datos
        while(rs.next()) {
            tablas.add(new ElementoLista(rs.getString(3),cont)); // la tercer columna contiene el nombre de la tabla
            cont++;
        }
        return tablas;
    }

    /**
     * Método que permite obtener los registros de una tabla.
     * @param base nombre de la base de datos en donde se encuentra la tabla de
     * la que se quieren obtener los registros.
     * @param tabla nombre de la tabla a consultar.
     * @return objeto ResultSet con los registros la tabla especificada.
     * @throws SQLException Error al realizar la consulta en el servidor.
     */
    public ResultSet obtenerRegistros(String base,String tabla) throws SQLException{
        rs = s.executeQuery("Select * from " + base + "." + tabla + ";");
        return rs;
    }
    
    /**
     * Método que obtiene el número de registros de una tabla.
     * @param base nombre de la base en donde se encuentra la tabla a consultar.
     * @param tabla nombre de la tabla a consultar.
     * @return número de registros en la tabla especificada.
     * @throws SQLException Error al realizar la consulta en el servidor.
     */
    public int obtenerNumRegistros(String base,String tabla) throws SQLException{
        rs = s.executeQuery("Select count(*) from " + base + "." + tabla + ";");
        rs.first();
        return Integer.valueOf(rs.getString(1));
    }
    
    /**
     * Permite crear una base de datos nueva.
     * @param nombre Nombre de la nueva base de datos.
     * @throws SQLException Error al realizar la operación en el servidor.
     */
    public void crearBase(String nombre) throws SQLException{
        s.executeUpdate("create database "+nombre+";");
    }
    
    /**
     * Método que permite realizar modificaciones en una base de datos.
     * @param script Instrucción que se quiere ejecutar.
     * @throws SQLException Error al realizar la operación en el servidor.
     */
    public void modificarBase(String script) throws SQLException{
        int i = s.executeUpdate(script);
    }
    
    /**
     * Método que permite cerrar la conexión con el servidor MySQL.
     */
    public void terminarConexion(){
        if(rs!=null){
            try{
                rs.close();
                rs = null;
            }
            catch(SQLException sqlEx){}
        }
        if(s!=null){
            try{
                s.close();
                s = null;
            }
            catch(SQLException sqlEx){}
        }
        if(conn!=null){
            try{
                conn.close();
                conn = null;
                info = null;
            }
            catch(SQLException ex){}
        }
    }    
}
