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
import java.util.ArrayList;

/**
 *
 * @author Geny
 */
public class Conexion {
    private Connection conn;
    private DatabaseMetaData info;
    private ResultSet rs;
    
    public Conexion(String servidor, String usr, String passwd) throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://"+servidor+"/",usr,passwd);
        info = conn.getMetaData();        
    }
    
    public ArrayList<String> obtenerBasesDeDatos() throws SQLException{
        ArrayList<String> b = new ArrayList<>();
        rs = info.getCatalogs();
        while(rs.next()) {
           //System.out.println(bases.getString(1));
           b.add(rs.getString(1));   // añadir la primer columna del ResultSet de Bases de Datos
        }
        return b;
    }
     
    public ArrayList<String> obtenerTablas(String nomBase) throws SQLException{
        ArrayList<String> t = new ArrayList<>();
        rs = info.getTables(nomBase,null,null,null);
        while(rs.next()) {
           System.out.println(rs.getString(3));
           t.add(rs.getString(3));
        }
        return t;
    }
}
