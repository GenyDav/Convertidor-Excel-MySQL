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

/**
 *
 * @author Geny
 */
public class Conexion {
    private Connection conn;
    private DatabaseMetaData info;
    
    public Conexion(String servidor, String usr, String passwd) throws ClassNotFoundException, SQLException{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://"+servidor+"/",usr,passwd);
            info = conn.getMetaData();
            ResultSet rs = info.getCatalogs();
            System.out.println("List of the catalogs in the database");
            while(rs.next()) {
               System.out.println(rs.getString(1));
            }
    }
    /*public static void main(String []args){
        Conexion c = new Conexion();
    }*/
}
