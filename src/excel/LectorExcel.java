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
import javax.swing.JLabel;
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
    private FormatoTablaExcel formato;
    private JButton boton;
    
    public LectorExcel(JComboBox combo,JTable tabla,JLabel label,JButton btn,String ruta){
        this.ruta = ruta;
        comboHojas = combo;
        this.tabla = tabla;
        etiqueta = label;
        boton = btn;
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
        System.out.println("Método leer archivo...");
        try{
            //flujoEntrada = new FileInputStream(new File(ruta));
            etiqueta.setText("Leyendo archivo...");
            //boton.setEnabled(false);
            System.out.println("Creando archivo...");
            libro = WorkbookFactory.create(new File(ruta));
            //libro = new XSSFWorkbook(flujoEntrada);
            System.out.println("Archivo creado...");
            new FormatoTablaExcel(0,this).execute();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException ex){
            ex.printStackTrace();
        }catch(Exception ex2){
            ex2.printStackTrace();
        }
    }
    
    public int obtenerNumHojas(){
        System.out.println("Obteniendo número de hojas...");
        return libro.getNumberOfSheets();
    }
    
    public void obtenerNombresHojas(){
        System.out.println("Obteniendo nombres de hojas...");
        comboHojas.removeAllItems();
        System.out.println(libro.getNumberOfSheets());
        try{
            for(int i=0;i<libro.getNumberOfSheets();i++){
                System.out.println("i: "+i+ " "+libro.getSheetName(i));
                comboHojas.addItem(libro.getSheetName(i));
            }
            comboHojas.setEnabled(true);
            boton.setEnabled(true);
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
    public Void doInBackground(){
        System.out.println("Iniciando lectura...");
        leerArchivo();
        //obtenerNumHojas();
        obtenerNombresHojas();
        return null;
    }
}
