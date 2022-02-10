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
    private int indice; // indice de la hoja que se muestra en pantalla
    
    public FormatoTablaExcel(JTable tabla, int indiceHoja, LectorExcel lector){
        modelo = new DefaultTableModel();
        this.tabla = tabla;
        tabla.setModel(modelo);
        this.lector = lector;
        this.indice = indiceHoja;
    }
    
    public final void asignarNombresColumnas(){
        ArrayList<String>encabezadosTabla = new ArrayList<>();
        XSSFSheet hoja = lector.getLibro().getSheetAt(indice);
        Row encabezado = hoja.getRow(0);
        Iterator<Cell> iterador = encabezado.cellIterator();
        while(iterador.hasNext()){
            encabezadosTabla.add(iterador.next().getStringCellValue());
        }
        modelo.setColumnIdentifiers(encabezadosTabla.toArray());
    }
}
