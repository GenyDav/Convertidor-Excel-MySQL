/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excel;

import interfaz.FormatoTablaExcel;
import interfaz.InfoColumna;
import interfaz.TablaLista;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
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
    private String ruta;
    //private FileInputStream flujoEntrada;
    private Workbook libro;
    private JComboBox comboHojas;
    private JTable tabla;
    private JLabel etiqueta;
    private JButton botonAbrir;
    private JButton btnTipos;
    private JFrame ventana;
    private ArrayList<TablaLista> hojas;
    
    public LectorExcel(JFrame ventana,JComboBox combo,JTable tabla,JLabel label,JButton btn,String ruta,JButton btnTipos,ArrayList<TablaLista>hojas){
        this.ruta = ruta;
        comboHojas = combo;
        this.ventana = ventana;
        this.tabla = tabla;
        etiqueta = label;
        botonAbrir = btn;
        this.btnTipos = btnTipos;
        this.hojas = hojas;
    }

    public void setRuta(String ruta){
        this.ruta = ruta;
    }
    
    public JTable getTabla(){
        return tabla;
    }
    
    public JLabel getLabel(){
        return etiqueta;
    }
    
    public void leerArchivo() throws IOException{
        try{
            //flujoEntrada = new FileInputStream(new File(ruta));
            etiqueta.setText("Leyendo archivo...");
            libro = WorkbookFactory.create(new File(ruta));
            //libro = new XSSFWorkbook(flujoEntrada);
            System.out.println("Archivo creado...");
            
            Sheet hojaActual;
            Row encabezado; 

            for(int i=0;i<libro.getNumberOfSheets();i++){
                System.out.println("Llenando hoja...");
                int numCol = 0;
                hojaActual = libro.getSheetAt(i);
                hojas.add(new TablaLista(hojaActual.getSheetName(),i));
                //System.out.println("encabezado: "+hoja.getFirstRowNum());
                encabezado = hojaActual.getRow(hojaActual.getFirstRowNum());

                Iterator<Cell> iterador = encabezado.cellIterator();
                while(iterador.hasNext()){
                    //System.out.println(iterador.next().toString());
                    //hojas.get(i).agregarColumna(new InfoColumna(iterador.next().toString()));
                    // identificar la primer columna y preasignarle el tipo de dato int
                    if(numCol==0){
                       hojas.get(i).agregarColumna(new InfoColumna(iterador.next().toString(),3));
                    }else{
                        hojas.get(i).agregarColumna(new InfoColumna(iterador.next().toString()));
                    }
                    numCol++;
                }
                numCol = 0;
            }      
            new FormatoTablaExcel(0,this).execute();
        }catch(FileNotFoundException e){
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
    
    public int obtenerNumHojas(){
        System.out.println("Obteniendo número de hojas..."+libro.getNumberOfSheets());
        return libro.getNumberOfSheets();
    }
    
    public void obtenerNombresHojas(){
        System.out.println("Obteniendo nombres de hojas...");
        try{
            for(int i=0;i<libro.getNumberOfSheets();i++){
                //System.out.println("i: "+i+ " "+libro.getSheetName(i));
                comboHojas.addItem(libro.getSheetName(i));
            }
        }catch(Exception e){
            System.out.println("Error:");
            e.printStackTrace();
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
    
    @Override
    public Void doInBackground() throws IOException{
        System.out.println("Iniciando lectura...");
        leerArchivo();
        obtenerNombresHojas();
        comboHojas.setEnabled(true);
        botonAbrir.setEnabled(true);
        btnTipos.setEnabled(true);
        /*TablaLista tabla;
        System.out.println("Información");
        for(int i=0;i<hojas.size();i++){
            tabla = hojas.get(i);
            tabla.mostrarColumnas();
            System.out.println();
        }  */
        return null;
    }
}
