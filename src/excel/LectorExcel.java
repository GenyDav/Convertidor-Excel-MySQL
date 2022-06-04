/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excel;

import datos.InfoColumna;
import datos.Tipo;
import interfaz.PanelImport;
import interfaz.TablaLista;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.SwingWorker;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author Geny
 */
public class LectorExcel extends SwingWorker<Void,Void>{  
    private String ruta;            // ruta del archivo que va a cargar
    private String nombre;          // nombre del archivo
    private Workbook libro;         // archivo Excel
    private JComboBox comboHojas;   // comboBox en donde se añaden los nombres de las hojas del archivo
    private JLabel etiquetaReg;     // etiqueta donde se muestra el número de renglones de la hoja 
    private JLabel labelArchivo;    // etiqueta para el nombre del archivo
    private JButton btnAbrir;
    private JButton btnTipos;       
    private JButton btnImportar;
    private JRadioButton opcTablasComp,opcTablasSel;
    private JFrame ventana;
    private ArrayList<TablaLista> hojas; // lista de hojas encontradas en el archivo
   
    public LectorExcel(JFrame ventana, PanelImport panelImport){
        ruta = panelImport.getRutaArch();//
        comboHojas = panelImport.getComboHojas();
        this.ventana = ventana;
        etiquetaReg = panelImport.getLabelRegExcel();
        labelArchivo = panelImport.getLabelArchivo();
        nombre = panelImport.getNomArch();//
        btnAbrir = panelImport.getBtnAbrir();
        btnImportar = panelImport.getBtnImportar();
        this.opcTablasComp = panelImport.getOpcHojasCompletas();
        this.opcTablasSel = panelImport.getOpcHojasSel();
        btnTipos = panelImport.getBtnTipos();
        hojas = panelImport.getListaHojas();
    }
    
    public LectorExcel(JFrame ventana,JComboBox combo,JLabel label,JLabel nomArch,JButton btn,String ruta,String nombre,JButton btnTipos,ArrayList<TablaLista>hojas,JButton btnImport,JRadioButton opc1,JRadioButton opc2){
        this.ruta = ruta;//
        comboHojas = combo;
        this.ventana = ventana;
        etiquetaReg = label;
        labelArchivo = nomArch;
        this.nombre = nombre;//
        btnAbrir = btn;
        btnImportar = btnImport;
        this.opcTablasComp = opc1;
        this.opcTablasSel = opc2;
        this.btnTipos = btnTipos;
        this.hojas = hojas;
    }
    
    public JLabel getLabel(){
        return etiquetaReg;
    }
    
    public void leerArchivo() throws IOException{
        Sheet hojaActual;
        Row encabezado;
        int numeroColumna;
        try{
            libro = WorkbookFactory.create(new File(ruta));
            for(int i=0;i<libro.getNumberOfSheets();i++){
                hojaActual = libro.getSheetAt(i);
                // Crear un nuevo elemento 'hoja' y añadirlo a la estructura de datos
                hojas.add(new TablaLista(hojaActual.getSheetName(),i));
                // Verificar si la hoja no está vacía
                if(hojaActual.getPhysicalNumberOfRows()>0){ 
                    numeroColumna = 0;
                    // Leer el primer renglón de la hoja
                    encabezado = hojaActual.getRow(hojaActual.getFirstRowNum());
                    Iterator<Cell> iterador = encabezado.cellIterator();
                    while(iterador.hasNext()){
                        // Identificar la primer columna y preasignarle el tipo de dato int
                        // a las demás columnas se les asigna el tipo varchar
                        if(numeroColumna==0){
                           hojas.get(i).agregarColumna(new InfoColumna(iterador.next().toString(),Tipo.INT));
                        }else{
                            hojas.get(i).agregarColumna(new InfoColumna(iterador.next().toString(),Tipo.VARCHAR));
                        }
                        numeroColumna++;
                    }
                }
            }      
        }catch(FileNotFoundException e){
            etiquetaReg.setIcon(null);
            JOptionPane.showMessageDialog(
                ventana, 
                "El archivo está siendo utilizado por otro programa.  "
                    + "\nCiérrelo e inténtelo de nuevo.",
                "No se puede abrir el archivo", 
                JOptionPane.INFORMATION_MESSAGE
            );
            e.printStackTrace();
        }catch(Exception ex2){
            ex2.printStackTrace();
        }
    }
    
    /*public int obtenerNumHojas(){
        //System.out.println("Obteniendo número de hojas..."+libro.getNumberOfSheets());
        return libro.getNumberOfSheets();
    }*/
    
    /**
     * Lee los nombres de las hojas del archivo y los añade al comboBox
     * correspondiente.
     */
    public void obtenerNombresHojas(){
        for(int i=0;i<libro.getNumberOfSheets();i++){
            comboHojas.addItem(libro.getSheetName(i)); 
        }
    }
    
    public Workbook getLibro(){
        return libro;
    }
    
    public void cerrarArchivo(){
        try {
            libro.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public ArrayList<TablaLista> obtenerInfoArchivo(){
        return hojas;
    }
    
    /**
     * Habilita todos los elementos de la interfaz despues de que finaliza el
     * proceso de apertura del archivo.
     */
    private void habilitarControles(){
        comboHojas.setEnabled(true);
        btnAbrir.setEnabled(true);
        btnTipos.setEnabled(true);
        opcTablasComp.setEnabled(true);
        opcTablasSel.setEnabled(true);
        btnImportar.setEnabled(true);
    }
    
    @Override
    public Void doInBackground() throws IOException{
        etiquetaReg.setText("Leyendo archivo...");
        etiquetaReg.setIcon(new ImageIcon(getClass().getResource("/imagenes/loading2.gif")));
        leerArchivo();
        labelArchivo.setText("  " + nombre);
        obtenerNombresHojas();
        habilitarControles();
        
        TablaLista tabla;
        System.out.println("Información");
        for(int i=0;i<hojas.size();i++){
            tabla = hojas.get(i);
            tabla.mostrarColumnas();
            System.out.println();
        }  
        return null;
    }
}
