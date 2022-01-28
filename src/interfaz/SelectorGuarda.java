/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import excel.GeneradorExcel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicFileChooserUI;

/**
 *
 * @author Geny
 */
public class SelectorGuarda extends JFileChooser {
    private String rutaArch;
    private String nomArch;
    private String extension;
    private BasicFileChooserUI interfaz;
    private GeneradorExcel gen;
    
    public SelectorGuarda(File archivo, GeneradorExcel gen){
        setSelectedFile(archivo);
        setDialogTitle("Guardar archivo Excel");
        setAcceptAllFileFilterUsed(false);
        setMultiSelectionEnabled(false);
        setFileFilter(new FileNameExtensionFilter("Libro de Excel 97-2003 (*.xls)","xls"));
        setFileFilter(new FileNameExtensionFilter("Libro de Excel (*.xlsx)","xlsx"));
        rutaArch = archivo.getPath();
        //System.out.println("path: "+rutaArch);
        nomArch = archivo.getName();
        extension = "xlsx";
        interfaz = (BasicFileChooserUI)getUI();
        this.gen = gen;
        
        addPropertyChangeListener(JFileChooser.FILE_FILTER_CHANGED_PROPERTY, new PropertyChangeListener(){
            // NOTA: cuando se selecciona un tipo de archivo distinto al actual
            // el archivo seleccionado se convierte en null
            @Override
            public void propertyChange(PropertyChangeEvent evt){              
                // obtener el nombre del archivo en el jTextField del jFileChooser 
                nomArch = interfaz.getFileName();   
                // Obtener la extensión de archivo seleccionada
                FileNameExtensionFilter filtro = (FileNameExtensionFilter)getFileFilter();
                String nomFiltros[] = filtro.getExtensions();
                extension = nomFiltros[0];
                // Cambiar la extensión según el tipo seleccionado
                if(nomArch.endsWith("xlsx")){
                    nomArch = nomArch.substring(0, nomArch.length()-5); 
                }else if(nomArch.endsWith("xls")){
                    nomArch = nomArch.substring(0, nomArch.length()-4);
                }  
                nomArch += "." + extension;
                //System.out.println("Nombre nuevo: "+nomArch);
                interfaz.setFileName(nomArch);
            }
        });
        
        addPropertyChangeListener(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY, new PropertyChangeListener(){
            // El evento se lanza al seleccionar un archivo 
            // tambien al cambiar el filtro de tipo y el archivo se convierte en null
            @Override
            public void propertyChange(PropertyChangeEvent evt){
                File f = getSelectedFile();
                if(f!=null){                   
                    nomArch = getSelectedFile().getName(); // si el usuario selecciona un archivo del jfileChooser
                }
            }
        });
    }
    
    @Override
    public void approveSelection(){
        nomArch = getSelectedFile().getName();
        rutaArch = getSelectedFile().getPath();    
        // Verificar la extensión del archivo antes de guardarlo
        if(!rutaArch.endsWith(extension)){
            rutaArch += "." + extension;
            nomArch += "." + extension;
        }
        System.out.println("ruta nueva: "+rutaArch);
        //System.out.println("Ubicacion: "+getCurrentDirectory());
        File archivo = new File(rutaArch);
        if(archivo.exists()){
            int resp = JOptionPane.showConfirmDialog(
                this, 
                "El archivo " + nomArch + " ya existe. \n¿Desea reemplazarlo?",
                "Confirmar sobreescritura", 
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            if(resp==JOptionPane.OK_OPTION){
                gen.defInfoArchivo(rutaArch, extension);
                gen.execute();
            }else{
                return; // regresar al jFileChooser
            }
        }else{
            gen.defInfoArchivo(rutaArch, extension);
            gen.execute();
        }
        super.approveSelection();
    }
}
