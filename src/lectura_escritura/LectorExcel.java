package lectura_escritura;

import datos.InfoColumna;
import datos.Tipo;
import interfaz.PanelImport;
import datos.HojaLista;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.SwingWorker;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * Clase encargada de leer el contenido del archivo Excel y crear una lista que
 * contiene la información de cada hoja necesaria para crear una tabla MySQL.
 * @author Geny
 * @version 1.0
 */
public class LectorExcel extends SwingWorker<Void,Void>{  
    private String ruta;                    // ruta del archivo que va a cargar
    private String nombre;                  // nombre del archivo
    private Workbook libro;                 // archivo Excel
    private ArrayList<HojaLista> hojas;     // lista de hojas encontradas en el archivo
    
    private JComboBox comboHojas;   // comboBox en donde se añaden los nombres de las hojas del archivo
    private JLabel etiquetaReg;     // etiqueta donde se muestra el número de renglones de la hoja 
    private JLabel labelArchivo;    // etiqueta para el nombre del archivo
    private JButton btnAbrir;
    private JButton btnTipos;       // botón para configurar los tipos de datos de las hojas
    private JButton btnImportar;
    private JRadioButton opcTablasComp,opcTablasSel;
    private JFrame ventana;         // ventana sobre la que se muestra el programa
   
    /**
     * Crea el objeto capaz de obtener la información de un archivo de Excel.
     * @param ventana Frame sobre el que se ejecuta el programa
     * @param panelImport Panel que contiene todos los elementos para importar un
     * archivo.
     */
    public LectorExcel(JFrame ventana, PanelImport panelImport){
        ruta = panelImport.getRutaArch();
        comboHojas = panelImport.getComboHojas();
        this.ventana = ventana;
        etiquetaReg = panelImport.getLabelRegExcel();
        labelArchivo = panelImport.getLabelArchivo();
        nombre = panelImport.getNomArch();
        btnAbrir = panelImport.getBtnAbrir();
        btnImportar = panelImport.getBtnImportar();
        this.opcTablasComp = panelImport.getOpcHojasCompletas();
        this.opcTablasSel = panelImport.getOpcHojasSel();
        btnTipos = panelImport.getBtnTipos();
        hojas = panelImport.getListaHojas();
    }
    
    /**
     * Método que devuelve el objeto que representa al archivo de Excel.
     * @return objeto de tipo Workbook que contiene la información del archivo abierto.
     */
    public Workbook getLibro(){
        return libro;
    }
    
    /**
     * Devuelve la hoja del archivo cuyo índice se indica en el parámetro.
     * @param indice Índice de la hoja que se quiere obtener
     * @return Hoja del archivo Excel.
     */
    public Sheet getHoja(int indice){
        return libro.getSheetAt(indice);
    }
    
    /**
     * Abre el archivo seleccionado por el usuario y crea la estructura de datos
     * que representa a las hojas del archivo y la lista de columnas que pertenecen
     * a cada hoja.
     * @throws IOException Error lanzado si no se tiene acceso al archivo. 
     */
    public void leerArchivo() throws IOException{
        Sheet hojaActual;
        Row encabezado;
        int indiceColumna;
        try{
            // Abrir el archivo
            libro = WorkbookFactory.create(new File(ruta));
            // Recorrer cada hoja para crear la estructura de datos
            for(int i=0;i<libro.getNumberOfSheets();i++){
                hojaActual = libro.getSheetAt(i);
                // Crear un nuevo elemento 'hoja' y añadirlo a la estructura
                hojas.add(new HojaLista(hojaActual.getSheetName(),i));
                // Verificar si la hoja no está vacía para agregar las columnas a la estructura
                if(hojaActual.getPhysicalNumberOfRows()>0){ 
                    indiceColumna = 0;
                    // Leer el primer renglón de la hoja (que será el encabezado de la tabla)
                    encabezado = hojaActual.getRow(hojaActual.getFirstRowNum());
                    Iterator<Cell> iterador = encabezado.cellIterator();
                    while(iterador.hasNext()){
                        // Identificar la primer columna y preasignarle el tipo de dato int
                        // a las demás columnas se les asigna el tipo varchar
                        if(indiceColumna==0){
                           hojas.get(i).agregarColumna(new InfoColumna(iterador.next().toString(),Tipo.INT));
                        }else{
                            hojas.get(i).agregarColumna(new InfoColumna(iterador.next().toString(),Tipo.VARCHAR));
                        }
                        indiceColumna++;
                    }
                }
            }      
        }catch(FileNotFoundException e){
            etiquetaReg.setIcon(null);
            JOptionPane.showMessageDialog(
                ventana, 
                "El archivo está siendo utilizado por otro programa.  "
                    + "\nCiérrelo e inténtelo de nuevo.",
                "No se puede abrir el archivo", 
                JOptionPane.INFORMATION_MESSAGE
            );
            labelArchivo.setText("");
            //e.printStackTrace();
        }
    }

    /**
     * Lee los nombres de las hojas del archivo y los añade al comboBox
     * correspondiente.
     */
    public void obtenerNombresHojas(){
        for(int i=0;i<libro.getNumberOfSheets();i++){
            comboHojas.addItem(libro.getSheetName(i)); 
        }
    }
    
    /**
     * Habilita todos los elementos de la interfaz despues de que finaliza el
     * proceso de apertura del archivo.
     */
    private void habilitarControles(){
        comboHojas.setEnabled(true);
        btnAbrir.setEnabled(true);
        btnTipos.setEnabled(true);
        opcTablasComp.setEnabled(true);
        opcTablasSel.setEnabled(true);
        btnImportar.setEnabled(true);
    }
    
    /**
     * Cierra el flujo de datos del archivo.
     */
    public void cerrarArchivo(){
        try{
            libro.close();
        }catch (IOException ex) {
            //ex.printStackTrace();
        }
    }
    
    /**
     * Ejecuta en segundo plano la lectura del archivo, añade los nombres de las
     * hojas al combo correspondiente y actualiza los demás elementos de la interfaz
     * para que puedan ser utilizados.
     * @return null
     * @throws IOException Error en la entrada/salida de datos
     */
    @Override
    public Void doInBackground() throws IOException{
        etiquetaReg.setText("Leyendo archivo...");
        etiquetaReg.setIcon(new ImageIcon(getClass().getResource("/imagenes/loading2.gif")));
        leerArchivo();
        labelArchivo.setText("  " + nombre);
        obtenerNombresHojas();
        habilitarControles();       
        return null;
    }
}
