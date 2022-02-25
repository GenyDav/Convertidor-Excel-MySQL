/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excel;

import interfaz.FormatoTablaExcel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
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
    
    public LectorExcel(JFrame ventana,JComboBox combo,JTable tabla,JLabel label,JButton btn,String ruta,JButton btnTipos){
        this.ruta = ruta;
        comboHojas = combo;
        this.ventana = ventana;
        this.tabla = tabla;
        etiqueta = label;
        botonAbrir = btn;
        this.btnTipos = btnTipos;
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
    
    @Override
    public Void doInBackground() throws IOException{
        System.out.println("Iniciando lectura...");
        leerArchivo();
        obtenerNombresHojas();
        comboHojas.setEnabled(true);
        botonAbrir.setEnabled(true);
        btnTipos.setEnabled(true);
        return null;
    }
}
