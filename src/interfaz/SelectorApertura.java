/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 *
 * @author Geny
 */
public class SelectorApertura extends JFileChooser{
    private String nomArch;
    private String rutaArch;    
    private String extension;
    
    public SelectorApertura(){
        setDialogTitle("Abrir archivo Excel");
        setAcceptAllFileFilterUsed(false);
        setMultiSelectionEnabled(false);
        setFileFilter(new FileNameExtensionFilter("Libro de Excel 97-2003 (*.xls)","xls"));
        setFileFilter(new FileNameExtensionFilter("Libro de Excel (*.xlsx)","xlsx"));
        extension = "xlsx";
        
        addPropertyChangeListener(JFileChooser.FILE_FILTER_CHANGED_PROPERTY, new PropertyChangeListener(){
            @Override
            public void propertyChange(PropertyChangeEvent evt){
                FileNameExtensionFilter filtro = (FileNameExtensionFilter)getFileFilter();
                String nomFiltros[] = filtro.getExtensions();
                extension = nomFiltros[0];
            }
        });
    }
    
    @Override
    public void approveSelection(){
        nomArch = getSelectedFile().getName();
        rutaArch = getSelectedFile().getPath();  
        //System.out.println(nomArch);
        //System.out.println(rutaArch);
        // comprobar si el nombre del archivo tiene la extensión correspondiente
        if(!nomArch.endsWith(extension)){
            rutaArch += "." + extension;
            nomArch += "." + extension;
        }
        File archivo = new File(rutaArch);
        if(!archivo.exists()){
            JOptionPane.showMessageDialog(
                this, 
                nomArch+"\nNo se encuentra el archivo.  \nCompruebe el nombre de archivo e intente de nuevo.  ",
                "No se puede abrir el archivo",
                JOptionPane.INFORMATION_MESSAGE
            );
        }else{
            super.approveSelection();
        }
    }
}
