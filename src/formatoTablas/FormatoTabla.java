package formatoTablas;

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
 * Permite crear los objetos que consultan la base de datos y muestran los 
 * resultados en pantalla.
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
    private void mostrarRenglones() throws SQLException{
        Object aux;
        while(resultados.next()){  
            Object []renglon = new Object[numColumnas];
            for(int i=0;i<numColumnas;i++){
                aux = resultados.getObject(i+1);
                if(aux!=null)
                    renglon[i] = "  " + aux + "  ";
            }
            modelo.addRow(renglon);    
        } 
        labeRegistros.setText(numRegistros + " registros cargados");
    }
    
    /**
    * Metodo que ajusta el ancho de cada columna de una tabla en base a su 
    * contenido. 
    */
    private void ajustarAnchoColumna(){
        TableColumnModel modeloColumna = tabla.getColumnModel(); // Se obtiene el modelo de la columna
        for(int col=0;col<tabla.getColumnCount();col++){
            int ancho = 50; // valor minimo para el ancho de la columna
            for(int renglon=0;renglon<tabla.getRowCount();renglon++){
                TableCellRenderer renderer = tabla.getCellRenderer(renglon,col); // Obtener el renderizador de la tabla
                Component comp = tabla.prepareRenderer(renderer,renglon,col);    // Crear un objeto para preparar el renderer
                // Comparar el valor actual de la variable ancho con el tamaño de la celda
                ancho = Math.max(comp.getPreferredSize().width+1,ancho);
            }
            if (ancho>300) // no sobrepasar el valor de 300
                ancho = 300;
            modeloColumna.getColumn(col).setPreferredWidth(ancho); // Establecer el ancho de la columna
        }
        //tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }
    
    /**
     * Método ejecutado en segundo plano que llama a los otros métodos necesarios 
     * para mostrar los datos de la tabla.
     * @return void.
     * @throws SQLException Error ocurrido al intentar obtener los datos desde 
     * el servidor.
     */
    @Override
    public Void doInBackground() throws SQLException{
        labeRegistros.setText("Buscando registros...");
        numRegistros = conn.obtenerNumRegistros(nomBase, nomTabla);
        resultados = conn.obtenerRegistros(nomBase, nomTabla);
        metaDatos = resultados.getMetaData();
        asignarNombresColumnas();
        labeRegistros.setText("Formateando registros...");
        mostrarRenglones();
        ajustarAnchoColumna();
        return null;
    }
}
