/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excel;

import conexion.Conexion;
import interfaz.ElementoLista;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
    private ArrayList<ElementoLista> tablas;
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
    private JFrame ventana;
    private JButton boton;
    private int numRegistros;
    private boolean generandoArchivo;
    
    public GeneradorExcel(){
        conn = null;
        nombreBase = null;
        libro = null;
        indiceTablaAct = 0;
        tablas = null;
        numTablas = 0;
        rutaArch = null;
        tipoArch = null;
        labelProgreso = null;
        barra = null;
        ventana = null;
        numRegistros = 0;
        generandoArchivo = false;
    }
    
    public GeneradorExcel(Conexion c, String base, List<ElementoLista>tablas, JLabel label, JProgressBar barra, JFrame ventana, JButton btn){
        conn = c;
        nombreBase = base;
        libro = null;
        indiceTablaAct = 0;
        this.tablas = (ArrayList<ElementoLista>) tablas;
        numTablas = tablas.size();
        rutaArch = null;
        tipoArch = null;
        labelProgreso = label;
        this.barra = barra;
        this.ventana = ventana;
        boton = btn;
        numRegistros = 0;
        generandoArchivo = false;
    }
    
    public void defInfoArchivo(String rutaArch, String extension){
        this.rutaArch = rutaArch;
        tipoArch = extension;
    }
    
    public void crearLibro(){    
        try (OutputStream flujoSalida = new FileOutputStream(rutaArch)) {
            labelProgreso.setText("Iniciando exportación de la base '" + nombreBase + "'...");
            if(tipoArch.equals("xls")){
                libro = new HSSFWorkbook();
            }else{
                libro = new XSSFWorkbook();
            }

            for(int i=0;i<numTablas;i++){
                if(isCancelled()){
                    labelProgreso.setText("Exportación de la base "+nombreBase+"' cancelada.");
                    return;
                }
                indiceTablaAct = i;
                crearHoja(tablas.get(i).getNombre());
            }

            libro.write(flujoSalida);
            libro.close();
            libro = null;
            flujoSalida.flush();
            flujoSalida.close();
            
            //conn.terminarConexion();
            //conn = null;
            labelProgreso.setText("Exportación de la base '" + nombreBase + "' terminada");
            publish(100);
            JOptionPane.showMessageDialog(
                ventana, 
                "La exportación finalizó con éxito",
                "Proceso terminado", 
                JOptionPane.INFORMATION_MESSAGE
            );
        }catch (FileNotFoundException ex){
            // si el archivo está abierto cuando se intenta sobreescribir
            // se le pide al usuario que cierre el archivo antes de comenzar
            // a exportar la base de datos
            JOptionPane.showMessageDialog(
                ventana, 
                "El archivo está siendo utilizado por otro programa.  "
                    + "\nCiérrelo e inténtelo de nuevo.",
                "No se puede escribir el archivo", 
                JOptionPane.INFORMATION_MESSAGE
            );
            // ex.printStackTrace();
        }catch (IOException ex) {
            ex.printStackTrace();
            labelProgreso.setText("Error: "+ex.getMessage());
        }
    }
    
    private void crearHoja(String t){
        int columnas;   // número de columnas en la hoja
        Sheet hoja = libro.createSheet(t);
        try{
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
        }catch(SQLException ex){
            labelProgreso.setText("No se pudo cargar la información del servidor "
            + "(Error MySQL " + ex.getErrorCode() + ": " + ex.getMessage() + ".");
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
            if(isCancelled()){
                labelProgreso.setText("Exportación de la base "+nombreBase+"' cancelada.");
                return;
            }
            Row reg = hoja.createRow(renglon);
            for(int i=0;i<numColumnas;i++){
                //Cell celda = reg.createCell(i);
                Object res = resultados.getObject(i+1);
                if(res!=null && !res.toString().equals("")){
                    Cell celda = reg.createCell(i);
                    if(res.getClass().getSuperclass().getSimpleName().equals("Number")){
                        Number n = (Number)res;
                        celda.setCellValue(n.doubleValue());
                    }else{
                        celda.setCellValue(res.toString());
                    }
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
        //publish(100);
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

    public boolean estaActivo(){
        return generandoArchivo;
    }
    
    @Override
    protected Void doInBackground() throws Exception {
        //boton.setEnabled(false);
        boton.setText("Cancelar exportación");
        generandoArchivo = true;
        crearLibro();
        //boton.setEnabled(true);
        boton.setText("Exportar");
        generandoArchivo = false;
        return null;
    }
    
    @Override
    protected void process(List<Integer> chunks){
        barra.setValue(chunks.get(0));
    }
}
