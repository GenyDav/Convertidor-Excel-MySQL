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
import javax.swing.plaf.basic.BasicFileChooserUI;

/**
 *
 * @author Geny
 */
public class SelectorGuarda extends JFileChooser {
    String rutaArch;
    String nomArch;
    String extension;
    
    public SelectorGuarda(File archivo){
        setSelectedFile(archivo);
        setDialogTitle("Guardar archivo Excel");
        setAcceptAllFileFilterUsed(false);
        setMultiSelectionEnabled(false);
        setFileFilter(new FileNameExtensionFilter("Libro de Excel 97-2003 (*.xls)","xls"));
        setFileFilter(new FileNameExtensionFilter("Libro de Excel (*.xlsx)","xlsx"));
        rutaArch = archivo.getPath();
        System.out.println("path: "+rutaArch);
        nomArch = archivo.getName();
        System.out.println("archivo: "+nomArch);
        extension = null;
        
        addPropertyChangeListener(JFileChooser.FILE_FILTER_CHANGED_PROPERTY, new PropertyChangeListener(){
            @Override
            public void propertyChange(PropertyChangeEvent evt){
                System.out.println("===== Se cambió el filtro =====");
                nomArch = ((BasicFileChooserUI)getUI()).getFileName();   // obtener el nombre del archivo del jtextField
                System.out.println("Nombre en el campo: " + nomArch);
                if(getSelectedFile()!=null){
                    System.out.println("archivo seleccionado: " + getSelectedFile());
                }else{
                    System.out.println("no hay archivo");
                }
                // Obtener la extensión de archivo seleccionada
                FileNameExtensionFilter filtro = (FileNameExtensionFilter)getFileFilter();
                String nomFiltros[] = filtro.getExtensions();
                extension = nomFiltros[0];
                
                if(nomArch.endsWith("xlsx")){
                    nomArch = nomArch.substring(0, nomArch.length()-5); 
                }else if(nomArch.endsWith("xls")){
                    nomArch = nomArch.substring(0, nomArch.length()-4);
                }  
                nomArch += "." + extension;
                System.out.println("Nombre nuevo: "+nomArch);
                
                rutaArch = getCurrentDirectory().toString();
                //setSelectedFile(new File(rutaArch + "\\" + nomArch));
                ((BasicFileChooserUI)getUI()).setFileName(nomArch);
                System.out.println("ARCHIVO ACTUAL: "+ getSelectedFile());
            }
        });
        
        addPropertyChangeListener(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY, new PropertyChangeListener(){
            @Override
            public void propertyChange(PropertyChangeEvent evt){
                System.out.println("===== Se cambió el archivo =====");
                File f = getSelectedFile();
                if(f!=null){                   
                    System.out.println("NOMBRE: "+getSelectedFile().getName());
                    nomArch = getSelectedFile().getName();
                }else{
                    System.out.println("No hay archivo seleccionado");
                }
            }
        });
    }
    
    @Override
    public void approveSelection(){
        nomArch = getSelectedFile().getName();
        rutaArch = getSelectedFile().getAbsolutePath();
        System.out.println("archivo definitivo: "+rutaArch);
        /*FileNameExtensionFilter filtro = (FileNameExtensionFilter)getFileFilter();
        String nomFiltros[] = filtro.getExtensions();
        extension = nomFiltros[0];
        
        if(!rutaArch.endsWith(extension)){
            rutaArch += "." + extension;
            nomArch += "." + extension;
        }*/
        System.out.println("UBICACION: "+getCurrentDirectory());
        File archivo = new File(rutaArch);
        //System.out.println(rutaArch);
        
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
