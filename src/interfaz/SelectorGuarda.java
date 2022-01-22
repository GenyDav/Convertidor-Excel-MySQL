/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Geny
 */
public class SelectorGuarda extends JFileChooser {
    String rutaArch;
    String nomArch;
    String extension;
    
    public SelectorGuarda(){
        setDialogTitle("Guardar archivo Excel");
        setAcceptAllFileFilterUsed(false);
        setMultiSelectionEnabled(false);
        setFileFilter(new FileNameExtensionFilter("Libro de Excel 97-2003 (*.xls)","xls"));
        setFileFilter(new FileNameExtensionFilter("Libro de Excel (*.xlsx)","xlsx"));
        rutaArch = null;
        nomArch = null;
        extension = null;
    }
    
    @Override
    public void approveSelection(){
        
        nomArch = getSelectedFile().getName();
        rutaArch = getSelectedFile().getAbsolutePath();
        FileNameExtensionFilter filtro = (FileNameExtensionFilter)getFileFilter();
        String nomFiltros[] = filtro.getExtensions();
        extension = nomFiltros[0];
        
        if(!rutaArch.endsWith(extension)){
            rutaArch += "." + extension;
            nomArch += "." + extension;
        }
        
        File archivo = new File(rutaArch);
        
        if(archivo.exists()){
            int resp = JOptionPane.showConfirmDialog(
                this, 
                "El archivo "+nomArch+" ya existe. \n¿Desea reemplazarlo?",
                "Confirmar sobreescritura", 
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            if(resp==JOptionPane.OK_OPTION){
                System.out.println("Código para escribir el archivo");
            }else{
                return;
            }
        }
        super.approveSelection();
    }
}
