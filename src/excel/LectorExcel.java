/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JComboBox;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Geny
 */
public class LectorExcel {
    private String ruta;
    private XSSFWorkbook libro;
    private JComboBox comboHojas;
    
    public LectorExcel(JComboBox combo){
        libro = null;
        ruta = "";
        comboHojas = combo;
    }

    public void setRuta(String ruta){
        this.ruta = ruta;
        try(FileInputStream flujoEntrada = new FileInputStream(new File(ruta))){
            libro = new XSSFWorkbook(flujoEntrada);
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
        for(int i=0;i<libro.getNumberOfSheets();i++){
            comboHojas.addItem(libro.getSheetName(i));
        }
    }
}
