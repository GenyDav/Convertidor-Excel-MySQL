/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import interfaz.ElementoLista;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        rs = null;
    }
    
    public ArrayList<String> obtenerBasesDeDatos() throws SQLException{
        ArrayList<String> b = new ArrayList<>();
        rs = info.getCatalogs();
        while(rs.next()) {
           b.add(rs.getString(1));   // añadir la columna que tiene el nombre de la BD
        }
        return b;
    }
     
    public ArrayList<ElementoLista> obtenerTablas(String nomBase) throws SQLException{
        ArrayList<ElementoLista> t = new ArrayList<>();
        rs = info.getTables(nomBase,null,null,null);
        int cont = 0;
        while(rs.next()) {
            t.add(new ElementoLista(rs.getString(3),cont));
            // la tercer columna contiene el nombre de la tabla
            cont++;
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
    
    public void crearBase(String nombre) throws SQLException{
        int i = s.executeUpdate("create database "+nombre+";");    
    }
    
    public void modificarBase(String script) throws SQLException{
        int i = s.executeUpdate(script);
    }
    
    public void terminarConexion(){
        if(rs!=null){
            try{
                rs.close();
                rs = null;
                info = null;
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
            }
            catch(SQLException ex){}
        }
    }    
}
