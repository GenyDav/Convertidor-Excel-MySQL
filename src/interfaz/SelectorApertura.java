package interfaz;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 * Crea una ventana que permite seleccionar archivos para su apertura.
 * @author Geny
 * @version 1.0
 */
public class SelectorApertura extends JFileChooser{
    private String nomArch;
    private String rutaArch;    
    private String extension;
    
    /**
     * Crea una nueva ventana para seleccionar el archivo que se va a cargar.
     * Permite seleccionar solo archivos con la extensión .xls o .xlsx
     */
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
    
    /**
     * Cuando el usuario presiona el botón 'Aceptar' de la ventana, verifica si 
     * el archivo que se quiere abrir existe en el directorio actual. Si no existe, 
     * muestra un mensaje de notificación.
     */
    @Override
    public void approveSelection(){
        nomArch = getSelectedFile().getName();
        rutaArch = getSelectedFile().getPath();  
        // comprobar si el nombre del archivo tiene la extensión correspondiente
        if(!nomArch.endsWith(extension)){
            rutaArch += "." + extension;
            nomArch += "." + extension;
        }
        File archivo = new File(rutaArch);
        if(!archivo.exists()){
            JOptionPane.showMessageDialog(
                this, 
                nomArch + "\nNo se encuentra el archivo.  "
                + "\nCompruebe el nombre del archivo e intentelo de nuevo.  ",
                "No se puede abrir el archivo",
                JOptionPane.INFORMATION_MESSAGE
            );
        }else{
            super.approveSelection();
        }
    }
}
