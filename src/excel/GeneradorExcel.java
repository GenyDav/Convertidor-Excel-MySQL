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
import javax.swing.JLabel;
import javax.swing.SwingWorker;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Geny
 */
public class GeneradorExcel extends SwingWorker<Void,Integer>{
    private String nombreBase;
    private ArrayList<String> tablas;
    private Conexion conn;
    private Workbook libro;
    private ResultSet resultados;
    private ResultSetMetaData metaDatos;
    private String rutaArch;
    private String tipoArch;
    private JLabel labelProgreso;
    
    public GeneradorExcel(Conexion c, String base, ArrayList<String>tablas, JLabel label){
        conn = c;
        nombreBase = base;
        this.tablas = tablas;
        rutaArch = null;
        tipoArch = null;
        labelProgreso = label;
    }
    
    public void defInfoArchivo(String rutaArch, String extension){
        this.rutaArch = rutaArch;
        tipoArch = extension;
    }
    
    public void crearLibro(){
        if(tipoArch.equals("xls")){
            libro = new HSSFWorkbook();
        }else{
            libro = new XSSFWorkbook();
        }
        for(String tablaActual:tablas){
            crearHoja(tablaActual);
        }
        try(OutputStream flujoSalida = new FileOutputStream(rutaArch)){
            libro.write(flujoSalida);
        }catch (FileNotFoundException ex){
            // El archivo esta abierto cuando se intentar sobreescribir
            ex.printStackTrace();
        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private void crearHoja(String t){
        //System.out.println(t);
        labelProgreso.setText("Creando hoja "+t);
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

        CellStyle estiloCelda = defEstiloEnc();
        
        for(int i=0;i<numColumnas;i++){             // obtener el nombre de cada columna
            Cell celda = renglon.createCell(i);
            celda.setCellValue(metaDatos.getColumnName(i+1));
            celda.setCellStyle(estiloCelda);
        }
        return numColumnas;
    }
    
    private void escribirDatos(Sheet hoja,int numColumnas) throws SQLException{
        int renglon = 1;
        while(resultados.next()){
            Row reg = hoja.createRow(renglon);
            for(int i=0;i<numColumnas;i++){
                Cell celda = reg.createCell(i);
                Object res = resultados.getObject(i+1);
                if(res!=null){
                    celda.setCellValue(res.toString());
                }else{
                    //System.out.println("nulo");
                }
                /*try{
                    Thread.sleep(500);
                }catch(Exception e){}*/
            }
            renglon++;
        }
        for(int j=0;j<numColumnas;j++){
            hoja.autoSizeColumn(j);
        }
    }
    
    private CellStyle defEstiloEnc(){
        Font fuente = libro.createFont();
        fuente.setFontHeightInPoints((short)11);
        fuente.setFontName("Arial Unicode MS");
        fuente.setBold(true);
        fuente.setColor(IndexedColors.WHITE.getIndex());
        
        CellStyle estiloCelda = libro.createCellStyle();
        estiloCelda.setFont(fuente);
        estiloCelda.setAlignment(HorizontalAlignment.CENTER);
        estiloCelda.setFillForegroundColor(IndexedColors.SEA_GREEN.getIndex());
        estiloCelda.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estiloCelda.setBottomBorderColor(IndexedColors.WHITE.getIndex());
        estiloCelda.setBorderBottom(BorderStyle.DOUBLE);
        estiloCelda.setRightBorderColor(IndexedColors.WHITE.getIndex());
        estiloCelda.setBorderRight(BorderStyle.THIN);
        return estiloCelda;
    }

    @Override
    protected Void doInBackground() throws Exception {
        crearLibro();
        return null;
    }
}
