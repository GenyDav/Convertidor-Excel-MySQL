/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import excel.LectorExcel;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
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
     * COnstructor. Condfigura y asigna el modelo a la tabla en donde se van a 
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
    
    public final void asignarNombresColumnas(){
        try{
            Row encabezado;
            ArrayList<Object> encabezadosTabla = new ArrayList<>();
            if(hoja.getPhysicalNumberOfRows()==1){ // si solo hay un renglón en la hoja
                encabezado = hoja.getRow(hoja.getLastRowNum());
            }else{
                //System.out.println("encabezado: "+hoja.getFirstRowNum());
                encabezado = hoja.getRow(hoja.getFirstRowNum());
            }
            indiceColInicio = encabezado.getFirstCellNum();
            //System.out.println("Primer columna: "+encabezado.getFirstCellNum());

            Iterator<Cell> iterador = encabezado.cellIterator();
            while(iterador.hasNext()){
                encabezadosTabla.add(iterador.next());
            }
            numColumnas = encabezado.getPhysicalNumberOfCells();
            //System.out.println("Columnas: "+encabezado.getPhysicalNumberOfCells());

            modelo.setColumnIdentifiers(encabezadosTabla.toArray());
        }catch(Exception e){System.out.println("Error 1");e.printStackTrace();}
    }
    
    public final void escribirCeldas(){
        try{
        if(hoja.getPhysicalNumberOfRows()>1){
            //System.out.println("Ultima linea: "+indiceRengFinal);
            
            Object []renglon = new Object[numColumnas];
            Row renglonArch;
            Iterator<Row> iteradorRenglon = hoja.rowIterator();
            renglonArch = iteradorRenglon.next(); // saltar el encabezado
            while(iteradorRenglon.hasNext()){
                renglonArch = iteradorRenglon.next();
                for(int i=0;i<numColumnas;i++){
                    //System.out.println("Columna: "+(i+indiceColInicio));
                    renglon[i] = renglonArch.getCell(i+indiceColInicio);
                }               
                modelo.addRow(renglon);             
            }
        }   
        }catch(Exception e){System.out.println("Error 1");e.printStackTrace();}
    }
    
    @Override
    public Void doInBackground(){
        //System.out.println("numero de renglones: "+hoja.getPhysicalNumberOfRows());
        if(hoja.getPhysicalNumberOfRows()>0){
            asignarNombresColumnas();
            escribirCeldas();
        }
        lector.getLabel().setIcon(null);
        lector.getLabel().setText(hoja.getPhysicalNumberOfRows()-1+ " renglones cargados");
        return null;
    }
}