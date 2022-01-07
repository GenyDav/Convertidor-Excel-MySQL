/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import conexion.Conexion;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Geny
 */
public class FormatoTabla {
    private DefaultTableModel modelo;
    private JTable tabla;
    private ResultSet resultados;
    private ResultSetMetaData metaDatos;
    private int numColumnas;
    private int numRegistros;
    
    public FormatoTabla(JTable t, Conexion c, String nomBase, String nomTabla) throws SQLException{
        modelo = new DefaultTableModel();
        tabla = t;
        tabla.setModel(modelo);
        resultados = c.obtenerRegistros(nomBase, nomTabla);
        metaDatos = resultados.getMetaData();
        numColumnas = 0;
        numRegistros = 0;
    }
    
    public void asignarNombres() throws SQLException{
        numColumnas = metaDatos.getColumnCount();   // obtener el número de columnas
        String []columnas = new String[numColumnas];
        for(int i=0;i<numColumnas;i++){             // obtener el nombre de cada columna
            columnas[i] = metaDatos.getColumnName(i+1);
        }
        modelo.setColumnIdentifiers(columnas);
    }
    
    public void mostrarInformacion() throws SQLException{
        resultados.last();
        numRegistros = resultados.getRow();
        System.out.println("numero de registros: "+numRegistros);
        resultados.beforeFirst();
        Object []renglon = new Object[numColumnas];
        while(resultados.next()){           
            for(int i=0;i<numColumnas;i++){
                renglon[i] = resultados.getObject(i+1);
            }
            modelo.addRow(renglon);
        }      
    }
    
}
