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
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
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
    private int numTablas;
    private int indiceTablaAct;
    private Conexion conn;
    private Workbook libro;
    private ResultSet resultados;
    private ResultSetMetaData metaDatos;
    private String rutaArch;
    private String tipoArch;
    private JLabel labelProgreso;
    private JProgressBar barra;
    private int numRegistros;
    
    public GeneradorExcel(Conexion c, String base, ArrayList<String>tablas, JLabel label, JProgressBar barra){
        conn = c;
        nombreBase = base;
        libro = null;
        indiceTablaAct = 0;
        this.tablas = tablas;
        numTablas = tablas.size();
        rutaArch = null;
        tipoArch = null;
        labelProgreso = label;
        this.barra = barra;
        numRegistros = 0;
    }
    
    public void defInfoArchivo(String rutaArch, String extension){
        this.rutaArch = rutaArch;
        tipoArch = extension;
    }
    
    public void crearLibro(){
        labelProgreso.setText("Iniciando exportación de la base '" + nombreBase + "'...");
        if(tipoArch.equals("xls")){
            libro = new HSSFWorkbook();
        }else{
            libro = new XSSFWorkbook();
        }
        for(int i=0;i<numTablas;i++){
            indiceTablaAct = i;
            crearHoja(tablas.get(i));
        }
        try(OutputStream flujoSalida = new FileOutputStream(rutaArch)){
            libro.write(flujoSalida);
        }catch (FileNotFoundException ex){
            // El archivo esta abierto cuando se intentar sobreescribir
            // pedirle al usuario que cierre el archivo antes de guardar la base
            // o pedirle que lo cierre justo cuando se carguen todas las tablas
            // e intentar escribir el archivo hasta que el usuario lo cierre
            ex.printStackTrace();
        }catch (IOException ex) {
            ex.printStackTrace();
        }finally{
            labelProgreso.setText("Exportación de la base '" + nombreBase + "' terminada");
        }
    }
    
    private void crearHoja(String t){
        int columnas;   // número de columnas en la hoja
        Sheet hoja = libro.createSheet(t);
        try {
            publish(0); // reiniciar la barra de progreso
            labelProgreso.setText("Exportando la base '" + nombreBase + "': "
                + "creando hoja '" + t + "', consultando la base de datos...");
            resultados = conn.obtenerRegistros(nombreBase,t);
            metaDatos = resultados.getMetaData();
            labelProgreso.setText("Exportando la base '" + nombreBase + "': "
                + "creando hoja '"+ t + "'"
                + " (tabla " + (indiceTablaAct+1) + " de " + numTablas + ")");
            columnas = escribirEncabezados(hoja);
            escribirDatos(hoja, columnas);
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
        float incremento;
        float progreso = 0F;
        int renglon = 1;
        
        resultados.last();
        numRegistros = resultados.getRow();
        //System.out.println("numero de registros: "+numRegistros);
        incremento = 100F/numRegistros;
        //System.out.println("incremento: "+incremento);
        resultados.beforeFirst();
        
        while(resultados.next()){
            Row reg = hoja.createRow(renglon);
            for(int i=0;i<numColumnas;i++){
                Cell celda = reg.createCell(i);
                Object res = resultados.getObject(i+1);
                if(res!=null){
                    celda.setCellValue(res.toString());
                }
            }
            progreso = renglon*incremento;
            //System.out.println("progreso: "+progreso);
            try{
                Thread.sleep(1);
            }catch(Exception e){}
            publish(Math.round(progreso));
            renglon++;
        }
        // Ajustar el ancho de las columnas a su contenido
        for(int j=0;j<numColumnas;j++){
            hoja.autoSizeColumn(j);
        }
        publish(100);
        // cerrar
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
    
    @Override
    protected void process(List<Integer> chunks){
        barra.setValue(chunks.get(0));
    }
}
