/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formatoTablas;

import excel.LectorExcel;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 *
 * @author Geny
 */
public class FormatoTablaExcel extends SwingWorker<Void,Void>{
    private DefaultTableModel modelo; 
    private LectorExcel lector;
    private JTable tabla;
    private Sheet hoja;
    private int indiceColInicio;    // primer columna de la tabla en la hoja
    private int indice;             // indice de la hoja que se muestra en pantalla
    private int numColumnas;        // columnas de la hoja actual
    
    /**
     * Constructor. Condfigura y asigna el modelo a la tabla en donde se van a 
     * mostrar los datos y extrae la hoja del archivo de Excel según el índice 
     * que se pasó por parámetros.
     * @param indiceHoja Índice de la hoja que se va a mostrar en la tabla.
     * @param lector Obejto encargado de leer el archivo Excel.
     * @param tabla Tabla sobre la que se van a mostrar los datos.
     */
    public FormatoTablaExcel(int indiceHoja, LectorExcel lector, JTable tabla){
        modelo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable (int row, int column){
                return false;
            }
        };
        this.tabla = tabla;
        this.tabla.setModel(modelo);
        this.lector = lector;
        this.indice = indiceHoja;
        hoja = lector.getLibro().getSheetAt(indice);
        numColumnas = 0;
        indiceColInicio = 0;
    }
    
    /**
     * Método que lee los nombres de las columnas de una hoja y los asigna a la
     * tabla.
     */
    public final void asignarNombresColumnas(){
        Row encabezado;
        ArrayList<Object> encabezadosTabla = new ArrayList<>();
        // Obtener el primer renglón con datos de la hoja, el conteo inicia en 0
        encabezado = hoja.getRow(hoja.getFirstRowNum());
        System.out.println("renglon: "+hoja.getFirstRowNum());
        // Obtener el índice de la primer celda en el renglón, el conteo inicia en 0
        indiceColInicio = encabezado.getFirstCellNum();
        // Recorrer el renglón
        Iterator<Cell> iterador = encabezado.cellIterator();
        while(iterador.hasNext()){
            encabezadosTabla.add(iterador.next());
        }
        // Determinar el número de columnas según el número de celdas del encabezado
        numColumnas = encabezado.getPhysicalNumberOfCells();
        // Asignar las columnas a la tabla
        modelo.setColumnIdentifiers(encabezadosTabla.toArray());
    }
    
    /**
     * Método que lee los datos de la hoja y los transcribe a la tabla.
     */
    public final void escribirCeldas(){
        Object []renglonTabla = new Object[numColumnas];
        Object aux; // variable auxiliar que guarda el valor de cada celda en un renglón
        Row renglonArch;
        if(hoja.getPhysicalNumberOfRows()>1){          
            Iterator<Row> iteradorRenglon = hoja.rowIterator();
            renglonArch = iteradorRenglon.next(); // Saltar el renglón del encabezado
            // Recorrer cada renglón del archivo
            while(iteradorRenglon.hasNext()){
                renglonArch = iteradorRenglon.next();
                // Obtener el valor de cada columna
                for(int i=0;i<numColumnas;i++){
                    aux = renglonArch.getCell(i+indiceColInicio);
                    if(aux!=null)
                        renglonTabla[i] = "  "+aux + "  ";
                    else
                        renglonTabla[i] = "";
                }           
                // Añadir el renglón a la tabla
                modelo.addRow(renglonTabla);             
            }
        }   
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
    }
    
    /**
     * Método ejecutado en segundo plano. Verifica que la hoja que se quiere
     * mostrar no esté vacía antes de llamar a los métodos encargados de leer y
     * agregar los datos en la tabla.
     * @return void. 
     */
    @Override
    public Void doInBackground(){
        if(hoja.getPhysicalNumberOfRows()>0){
            asignarNombresColumnas();
            escribirCeldas();
            ajustarAnchoColumna();
            lector.getLabel().setText(hoja.getPhysicalNumberOfRows()-1+ " renglones cargados");
        }else{
            lector.getLabel().setText("La hoja está vacía");
        }
        lector.getLabel().setIcon(null);
        return null;
    }
}