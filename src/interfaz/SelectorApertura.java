/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import excel.LectorExcel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Geny
 */
public class SelectorApertura extends JFileChooser{
    private String nomArch;
    private String rutaArch;    
    private String extension;
    
    private JLabel label;
    private JTable tabla;
    
    private ArrayList<String> hojas;
    private LectorExcel lector;
    
    public SelectorApertura(JLabel label, JTable tabla, LectorExcel lector){
        setDialogTitle("Abrir archivo Excel");
        setAcceptAllFileFilterUsed(false);
        setMultiSelectionEnabled(false);
        setFileFilter(new FileNameExtensionFilter("Libro de Excel 97-2003 (*.xls)","xls"));
        setFileFilter(new FileNameExtensionFilter("Libro de Excel (*.xlsx)","xlsx"));
        
        extension = "xlsx";
        this.label = label;
        this.tabla = tabla;
        this.lector = lector;    
        
        addPropertyChangeListener(JFileChooser.FILE_FILTER_CHANGED_PROPERTY, new PropertyChangeListener(){
            @Override
            public void propertyChange(PropertyChangeEvent evt){
                FileNameExtensionFilter filtro = (FileNameExtensionFilter)getFileFilter();
                String nomFiltros[] = filtro.getExtensions();
                extension = nomFiltros[0];
                System.out.println("Extensión: "+extension);
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
                nomArch
                + "\nNo se encuentra el archivo.  "
                + "\nCompruebe el nombre de archivo e intente de nuevo.",
                "No se puede abrir el archivo", 
                JOptionPane.INFORMATION_MESSAGE
            );
        }else{
            super.approveSelection();
            System.out.println("Tamaño: "+archivo.length());
            if(archivo.length()==0){
                JOptionPane.showMessageDialog(
                    this, 
                    nomArch
                    + "\nEl archivo no tiene información.  ",
                    "No se puede leer el archivo", 
                    JOptionPane.INFORMATION_MESSAGE
                );
                tabla.setModel(new DefaultTableModel());
                label.setText("Selecciona un archivo  "); 
            }else{
                lector.setRuta(rutaArch);
                lector.obtenerNombresHojas();
                label.setText(nomArch + "  "); 
                new FormatoTablaExcel(tabla,0,lector).asignarNombresColumnas(); 
            }
        }
    }
}
