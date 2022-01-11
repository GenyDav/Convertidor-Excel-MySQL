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
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Geny
 */
public class FormatoTabla extends SwingWorker<Void,Void>{
    private DefaultTableModel modelo;
    private JTable tabla;
    private Conexion conn;
    private ResultSet resultados;
    private ResultSetMetaData metaDatos;
    private int numColumnas;
    private int numRegistros;
    private String nomBase, nomTabla;
    private JLabel reg;
    
    public FormatoTabla(JTable t,Conexion c,String nomBase,String nomTabla,JLabel reg) throws SQLException{
        modelo = new DefaultTableModel();
        tabla = t;
        tabla.setModel(modelo);
        conn = c;
        numColumnas = 0;
        numRegistros = 0;
        resultados = null;
        metaDatos = null;
        this.nomBase = nomBase;
        this.nomTabla = nomTabla;
        this.reg = reg;
        this.reg.setText("");
    } 
    
    public final void asignarNombresColumnas() throws SQLException{
        numColumnas = metaDatos.getColumnCount();   // obtener el número de columnas
        String []columnas = new String[numColumnas];
        for(int i=0;i<numColumnas;i++){             // obtener el nombre de cada columna
            columnas[i] = metaDatos.getColumnName(i+1);
        }
        modelo.setColumnIdentifiers(columnas);
    }
    
    public void mostrarInformacion() throws SQLException{
        Object []renglon = new Object[numColumnas];
        while(resultados.next()){           
            for(int i=0;i<numColumnas;i++){
                renglon[i] = resultados.getObject(i+1);
            }
            modelo.addRow(renglon);    
        } 
        reg.setText(numRegistros + " registros cargados");
    }
    
    @Override
    public Void doInBackground() throws SQLException{
        reg.setText("Buscando registros...");
        numRegistros = conn.obtenerNumRegistros(nomBase, nomTabla);
        resultados = conn.obtenerRegistros(nomBase, nomTabla);
        metaDatos = resultados.getMetaData();
        asignarNombresColumnas();
        reg.setText("Formateando registros...");
        mostrarInformacion();
        return null;
    }
}
