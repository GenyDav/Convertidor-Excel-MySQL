/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Geny
 */
public class Conexion {
    private Connection conn;
    private DatabaseMetaData info;
    private ResultSet rs;
    private Statement s;
    
    public Conexion(String servidor, String usr, String passwd) throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://"+servidor+"/",usr,passwd);
        info = conn.getMetaData();  
        s = conn.createStatement();
    }
    
    public ArrayList<String> obtenerBasesDeDatos() throws SQLException{
        ArrayList<String> b = new ArrayList<>();
        rs = info.getCatalogs();
        while(rs.next()) {
           b.add(rs.getString(1));   // añadir la columna que tiene el nombre de la BD
        }
        return b;
    }
     
    public ArrayList<String> obtenerTablas(String nomBase) throws SQLException{
        ArrayList<String> t = new ArrayList<>();
        rs = info.getTables(nomBase,null,null,null);
        while(rs.next()) {
           t.add(rs.getString(3));  // la tercer columna contiene el nombre de la tabla
        }
        return t;
    }
    
    public ResultSet obtenerRegistros(String base,String tabla) throws SQLException{
        rs = s.executeQuery("Select * from " + base + "." + tabla + ";");
        return rs;
    }
    
    public int obtenerNumRegistros(String base,String tabla) throws SQLException{
        rs = s.executeQuery("Select count(*) from " + base + "." + tabla + ";");
        rs.first();
        return Integer.valueOf(rs.getString(1));
    }
}
