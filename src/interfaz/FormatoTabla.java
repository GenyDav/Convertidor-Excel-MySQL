/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import conexion.Conexion;
import java.awt.Component;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

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
        modelo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable (int row, int column){
                // Aquí devolvemos true o false según queramos que una celda
                // identificada por fila,columna (row,column), sea o no editable
                //if (column == 3)
                //   return true;
                return false;
            }
        };
        
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
    
    /**
    * Metodo que ajusta el ancho de la columna de una tabla
    * @param table 
    */
    private void resizeColumnWidth(JTable table){
        //Se obtiene el modelo de la columna
        TableColumnModel modeloColumna = table.getColumnModel();
        //Se obtiene el total de las columnas
        for(int columna=0;columna<table.getColumnCount();columna++){
            //Establecemos un valor minimo para el ancho de la columna
            int ancho = 50;
            //Obtenemos el numero de filas de la tabla
            for(int renglon=0;renglon<table.getRowCount();renglon++){
                //Obtenemos el renderizador de la tabla
                TableCellRenderer renderer = table.getCellRenderer(renglon,columna);
                //Creamos un objeto para preparar el renderer
                Component comp = table.prepareRenderer(renderer,renglon,columna);
                //Establecemos el ancho según el valor maximo del ancho de la columna
                ancho = Math.max(comp.getPreferredSize().width + 1, ancho);
            }
            //Se establece una condicion para no sobrepasar el valor de 300
            if (ancho>300)
                ancho = 300;
            //Se establece el ancho de la columna
            modeloColumna.getColumn(columna).setPreferredWidth(ancho);
        }
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
        //Se llama al metodo que ajusta las columnas
        resizeColumnWidth(tabla);
        //Se desactiva el Auto Resize de la tabla
        //Es importante que vaya despues de el metodo que ajusta el ancho de la columna
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            return null;
        }
}
