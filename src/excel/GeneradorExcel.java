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
 * @version 1.0
 */
public class GeneradorExcel extends SwingWorker<Void,Integer>{
    private String nombreBase;                  
    private ArrayList<ElementoLista> tablas;    // lista de tablas que se van a exportar
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
    private OutputStream flujoSalida;
    
    /**
     * Crea un nuevo objeto que inicializa los atributos al iniciar el prograna 
     * a su valor por defecto.
     */
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
        flujoSalida = null;
    }
    
    /**
     * Crea un nuevo objeto con la información dada por el usuario para crear el
     * archivo de Excel.
     * @param c Conexión con el servidor de bases de datos
     * @param base Nombre de la base de datos a exportar
     * @param tablas Lista de tablas que se van a exportar
     * @param label Etiqueta en la que se muestra información del proceso
     * @param barra Barra de progreso de la interfaz
     * @param ventana Ventana sobre la que se muestra el programa
     * @param btn Botón que inicia/cancela el proceso de exportación
     */
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
        flujoSalida = null;
    }
    
    /**
     * Establece la ruta y la extensión del archivo de Excel que va a ser creado.
     * @param rutaArch Dirección del archivo.
     * @param extension Extensión del archivo. 
     */
    public void defInfoArchivo(String rutaArch, String extension){
        this.rutaArch = rutaArch;
        tipoArch = extension;
    }
    
    /**
     * Cierra el flujo de salida y el archivo que están siendo utilizados. 
     * Termina la conexión con el servidor.
     */
    private void terminarEscritura(){
        try {
            libro.write(flujoSalida);
            libro.close();
            libro = null;
            flujoSalida.flush();
            flujoSalida.close();
            conn.terminarConexion();
            conn = null;
        } catch (IOException ex) {
            ex.printStackTrace();
            labelProgreso.setText("Error: "+ex.getMessage());
        }
    }
    
    /**
     * 
     */
    private void crearLibro(){    
        labelProgreso.setText("Iniciando exportación de la base '" + nombreBase + "'...");
        try {
            flujoSalida = new FileOutputStream(rutaArch);
            if(tipoArch.equals("xls"))
                libro = new HSSFWorkbook();
            else
                libro = new XSSFWorkbook();
            for(int i=0;i<numTablas;i++){
                if(isCancelled()){  // Verificar si el usuario no ha cancelado la exportación
                    labelProgreso.setText("Cancelando la exportación de la base de datos...");
                    terminarEscritura();
                    labelProgreso.setText("Exportación de la base " + nombreBase + "' cancelada.");
                    return;
                }
                indiceTablaAct = i;
                crearHoja(tablas.get(i).getNombre());
            }
            terminarEscritura();
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
            // a exportar la base de datos.
            JOptionPane.showMessageDialog(
                ventana, 
                "El archivo está siendo utilizado por otro programa.  "
                    + "\nCiérrelo e inténtelo de nuevo.",
                "No se puede escribir el archivo", 
                JOptionPane.INFORMATION_MESSAGE
            );
            // ex.printStackTrace();
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
        for(int i=0;i<numColumnas;i++){ // obtener el nombre de cada columna
            if(isCancelled()){
                return -1;
            }
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
    
    @Override
    protected Void doInBackground() throws Exception {
        boton.setText("Cancelar exportación");
        crearLibro();
        boton.setEnabled(true);
        boton.setText("Exportar");
        return null;
    }
    
    @Override
    protected void process(List<Integer> chunks){
        barra.setValue(chunks.get(0));
    }
}
