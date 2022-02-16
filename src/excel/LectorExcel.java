/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excel;

import interfaz.FormatoTablaExcel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Geny
 */
public class LectorExcel extends SwingWorker<Void,Void>{
    private String ruta;
    private FileInputStream flujoEntrada;
    private XSSFWorkbook libro;
    private JComboBox comboHojas;
    private JTable tabla;
    private JLabel etiqueta;
    
    public LectorExcel(JComboBox combo, JTable tabla, JLabel label){
        libro = null;
        ruta = "";
        comboHojas = combo;
        this.tabla = tabla;
        etiqueta = label;
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
    
    public void leerArchivo(){
        try{
            flujoEntrada = new FileInputStream(new File(ruta));
            etiqueta.setText("Leyendo archivo...");
            libro = new XSSFWorkbook(flujoEntrada);
            new FormatoTablaExcel(0,this).execute(); 
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    public int obtenerNumHojas(){
        return libro.getNumberOfSheets();
    }
    
    public void obtenerNombresHojas(){
        comboHojas.removeAllItems();
        for(int i=0;i<libro.getNumberOfSheets();i++){
            comboHojas.addItem(libro.getSheetName(i));
        }
    }
    
    public XSSFWorkbook getLibro(){
        return libro;
    }
    
    public void cerrarArchivo(){
        try {
            flujoEntrada.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public Void doInBackground(){
        leerArchivo();
        obtenerNumHojas();
        obtenerNombresHojas();
        return null;
    }
}
