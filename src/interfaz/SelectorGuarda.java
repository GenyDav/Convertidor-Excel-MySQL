package interfaz;

import lectura_escritura.GeneradorExcel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicFileChooserUI;

/**
 * Crea una nueva ventana de selección de archivos modificada para los 
 * propósitos del programa.
 * @author Geny
 * @version 1.0
 */
public class SelectorGuarda extends JFileChooser {
    private String rutaArch;
    private String nomArch;
    private String extension;
    private BasicFileChooserUI interfaz;
    private GeneradorExcel gen;
    
    /**
     * Constructor. Crea una nueva ventana de selección de archivos personalizada
     * que funciona de forma similar al selector de archivos del programa Excel.
     * @param archivo objeto File con el nombre de la base de datos que se va a 
     * exportar.
     * @param gen Objeto encargado de crear el archivo de Excel en segundo plano.
     */
    public SelectorGuarda(File archivo, GeneradorExcel gen){
        setSelectedFile(archivo);
        setDialogTitle("Guardar archivo Excel");
        setAcceptAllFileFilterUsed(false);
        setMultiSelectionEnabled(false);
        setFileFilter(new FileNameExtensionFilter("Libro de Excel 97-2003 (*.xls)","xls"));
        setFileFilter(new FileNameExtensionFilter("Libro de Excel (*.xlsx)","xlsx"));
        rutaArch = archivo.getPath();
        nomArch = archivo.getName();
        extension = "xlsx";
        interfaz = (BasicFileChooserUI)getUI();
        this.gen = gen;
        
        // Detectar cuando el usuario cambia el fitro del archivo seleccionado 
        // y actualizar la extensión en el nombre del archivo
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
                interfaz.setFileName(nomArch);
            }
        });
        
        // Detectar cuando el usuario cambia el archivo seleccionado 
        addPropertyChangeListener(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY, new PropertyChangeListener(){
            // El evento se lanza al seleccionar un archivo de la ventana, 
            // tambien al cambiar el filtro de tipo porque el archivo se convierte en null
            // y al hacer clic en el boton 'Aceptar' del jFileChooser.
            @Override
            public void propertyChange(PropertyChangeEvent evt){
                File f = getSelectedFile();
                if(f!=null){                   
                    nomArch = getSelectedFile().getName();
                }
            }
        });
    }
    
    /**
     * Método que se llama cuando el usuario presiona el boton 'Aceptar' después 
     * de seleccionar un archivo de la ventana o despues de escribir un nuevo 
     * nombre para el archivo.
     */
    @Override
    public void approveSelection(){
        // System.out.println(nomArch);
        rutaArch = getSelectedFile().getPath();    
        // Verificar la extensión del archivo antes de guardarlo
        if(!rutaArch.endsWith(extension)){
            rutaArch += "." + extension;
            nomArch += "." + extension;
        }
        File archivo = new File(rutaArch);
        // Verificar si el archivo ya existe, si es así, se muestra un mensaje
        // de confirmación para sobreescribirlo.
        if(archivo.exists()){
            int resp = JOptionPane.showConfirmDialog(
                this, 
                "El archivo " + nomArch + " ya existe. \n¿Desea reemplazarlo?",
                "Confirmar sobreescritura", 
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            if(resp == JOptionPane.OK_OPTION){
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
