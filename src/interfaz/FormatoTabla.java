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
 * @version 1.0
 */
public class FormatoTabla extends SwingWorker<Void,Void>{
    private DefaultTableModel modelo;
    private JTable tabla;           
    private Conexion conn;        
    private String nomBase, nomTabla;
    private JLabel labeRegistros;       
    private ResultSet resultados;        // datos de la tabla
    private ResultSetMetaData metaDatos; // información de la tabla obtenida desde la base de datos
    private int numColumnas;
    private int numRegistros;
    
    /**
     * Constructor.
     * @param t Tabla sobre la que se mostrarán los datos.
     * @param c Conexión con la base de datos.
     * @param nomBase Nombre de la base de datos en donde está la tabla que se va a mostrar.
     * @param nomTabla Nombre de la tabla que se va a mostrar.
     * @param reg Etiqueta en donde se muestra el número de registros cargados. 
     * @throws java.sql.SQLException Error al consultar los datos en el servidor de BD.
     */
    public FormatoTabla(JTable t,Conexion c,String nomBase,String nomTabla,JLabel reg) throws SQLException{
        tabla = t;
        modelo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable (int row, int column){
                return false; // Deshabilitar la edición de todas las celdas
            }
        };
        tabla.setModel(modelo);
        conn = c;
        this.nomBase = nomBase;
        this.nomTabla = nomTabla;
        labeRegistros = reg;
        labeRegistros.setText("");
        numColumnas = 0;
        numRegistros = 0;
        resultados = null;
        metaDatos = null;
    } 
    
    /**
     * Método que obtiene los nombres de la columnas desde la base de datos y los
     * asigna a la tabla.
     * @throws SQLException Error ocurrido al consultar el nombre de las columnas
     * en la base de datos.
     */
    private void asignarNombresColumnas() throws SQLException{
        numColumnas = metaDatos.getColumnCount();       // obtener el número de columnas
        String []columnas = new String[numColumnas];
        for(int i=0;i<numColumnas;i++){             
            columnas[i] = metaDatos.getColumnName(i+1); // obtener el nombre de cada columna
        }
        modelo.setColumnIdentifiers(columnas);
    }
    
    /**
     * Método que permite añadir los registros de la base de datos en la tabla.
     * @throws SQLException Error ocurrido al consultar los registros en la 
     * base de datos.
     */
    public void mostrarRenglones() throws SQLException{
        Object []renglon = new Object[numColumnas];
        while(resultados.next()){           
            for(int i=0;i<numColumnas;i++){
                renglon[i] = "  " + resultados.getObject(i+1) + "  ";
            }
            modelo.addRow(renglon);    
        } 
        labeRegistros.setText(numRegistros + " registros cargados");
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
        labeRegistros.setText("Buscando registros...");
        numRegistros = conn.obtenerNumRegistros(nomBase, nomTabla);
        resultados = conn.obtenerRegistros(nomBase, nomTabla);
        metaDatos = resultados.getMetaData();
        asignarNombresColumnas();
        labeRegistros.setText("Formateando registros...");
        mostrarRenglones();
        //Se llama al metodo que ajusta las columnas
        resizeColumnWidth(tabla);
        //Se desactiva el Auto Resize de la tabla
        //Es importante que vaya despues de el metodo que ajusta el ancho de la columna
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            return null;
        }
}
