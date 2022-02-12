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
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

/**
 *
 * @author Geny
 */
public class FormatoTablaExcel {
    private DefaultTableModel modelo; 
    private JTable tabla;
    private LectorExcel lector;
    private XSSFSheet hoja;
    private int indiceRengInicio, indiceRengFinal;
    private int indiceColInicio, indiceColFinal;
    
    private int indice; // indice de la hoja que se muestra en pantalla
    private int numColumnas; // de la hoja actual
    
    public FormatoTablaExcel(JTable tabla, int indiceHoja, LectorExcel lector){
        modelo = new DefaultTableModel();
        this.tabla = tabla;
        tabla.setModel(modelo);
        this.lector = lector;
        this.indice = indiceHoja;
        hoja = lector.getLibro().getSheetAt(indice);
        numColumnas = 0;
        indiceRengInicio = 0;
        indiceRengFinal = 0;
        indiceColInicio = 0;
        indiceColFinal = 0;
    }
    
    public final void asignarNombresColumnas(){
        Row encabezado;
        ArrayList<Object> encabezadosTabla = new ArrayList<>();

        if(hoja.getPhysicalNumberOfRows()==1){ // si solo hay un renglón en la hoja
            encabezado = hoja.getRow(hoja.getLastRowNum());
        }else{
            System.out.println("encabezado: "+hoja.getFirstRowNum());
            encabezado = hoja.getRow(hoja.getFirstRowNum());
            indiceRengInicio = hoja.getFirstRowNum();
        }
        indiceColInicio = encabezado.getFirstCellNum();
        System.out.println("Primer columna: "+encabezado.getFirstCellNum());
        indiceColFinal = encabezado.getLastCellNum();
        System.out.println("Ultima columna: "+encabezado.getLastCellNum());
       
        Iterator<Cell> iterador = encabezado.cellIterator();
        while(iterador.hasNext()){
            encabezadosTabla.add(iterador.next());
        }
        numColumnas = encabezado.getPhysicalNumberOfCells();
        System.out.println("Columnas: "+encabezado.getPhysicalNumberOfCells());
        modelo.setColumnIdentifiers(encabezadosTabla.toArray());
    }
    
    public final void escribirCeldas(){
        indiceRengFinal = hoja.getLastRowNum();
        System.out.println("Ultima linea: "+indiceRengFinal);
        
        
        Object []renglon = new Object[numColumnas];
        Row renglonArch;

        Iterator<Row> iteradorRenglon = hoja.rowIterator();
        renglonArch = iteradorRenglon.next();
        while(iteradorRenglon.hasNext()){
            renglonArch = iteradorRenglon.next();
            for(int i=0;i<numColumnas;i++){
                System.out.println("Columna: "+(i+indiceColInicio));
                renglon[i] = renglonArch.getCell(i+indiceColInicio);
            }
            modelo.addRow(renglon);
        }
    }
    
    public final void llenarTabla(){
        System.out.println("numero de renglones: "+hoja.getPhysicalNumberOfRows());
        if(hoja.getPhysicalNumberOfRows()>0){
            asignarNombresColumnas();
            escribirCeldas();
        }
    }
}
