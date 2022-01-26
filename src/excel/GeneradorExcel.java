/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excel;

import conexion.Conexion;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Geny
 */
public class GeneradorExcel {
    private String nombreBase;
    private ArrayList<String> tablas;
    private String rutaArch;
    private Conexion conn;
    private Workbook libro;
    private ResultSet resultados;
    private ResultSetMetaData metaDatos;
    
    public GeneradorExcel(Conexion c, String base, ArrayList<String>tablas){
        conn = c;
        nombreBase = base;
        this.tablas = tablas;
        rutaArch = null;
    }
    
    public void crearLibro(String rutaArch){
        libro = new XSSFWorkbook();
        for(String tablaActual:tablas){
            crearHoja(tablaActual);
        }
        try  (OutputStream flujoSalida = new FileOutputStream(rutaArch)) {
            libro.write(flujoSalida);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private void crearHoja(String t){
        System.out.println(t);
        Sheet hoja = libro.createSheet(t);
        try {
            resultados = conn.obtenerRegistros(nombreBase,t);
            metaDatos = resultados.getMetaData();
            int columnas = escribirEncabezados(hoja);
            escribirDatos(hoja,columnas);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    private int escribirEncabezados(Sheet hoja) throws SQLException{
        Row renglon = hoja.createRow(0);
        int numColumnas = metaDatos.getColumnCount();
        for(int i=0;i<numColumnas;i++){             // obtener el nombre de cada columna
            Cell celda = renglon.createCell(i);
            celda.setCellValue(metaDatos.getColumnName(i+1));
        }
        System.out.println("Encabezados listos");
        return numColumnas;
    }
    
    private void escribirDatos(Sheet hoja,int numColumnas) throws SQLException{
        int renglon = 1;
        while(resultados.next()){
            Row reg = hoja.createRow(renglon);
            for(int i=0;i<numColumnas;i++){
                Cell celda = reg.createCell(i);
                celda.setCellValue(resultados.getObject(i+1).toString());
            }
            renglon++;
        }
    }
}
