package lectura_escritura;

import datos.HojaLista;
import datos.InfoColumna;
import interfaz.PanelImport;
import interfaz.Reporte;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * Crea un proceso ejecutado en segundo plano que extrae los datos contenidos en
 * un archivo de Excel y los utiliza para crear una base de datos MySQL.
 * @author Geny
 * @version 1.0
 */
public class GeneradorBD extends Generador{
    private Conexion conn;
    private String nombreBase;
    private LectorExcel lector;         // objeto encargado de leer el archivo
    private Sheet hojaActual;           // hoja de la que se están leyendo los datos
    private List<HojaLista> listaHojas; // Información de la estructura de cada hoja en el archivo
    private JLabel etiquetaProgreso;
    private JButton btnImportar;
    private JProgressBar barra;
    private Reporte reporte;
    private String evento;              // Descripción de una actividad ocurrida en el proceso
    
    /**
     * Crea un nuevo objeto con todos sus atributos inicializados en null.
     */
    public GeneradorBD(){
        conn = null;
        nombreBase = null;
        lector = null;
        hojaActual = null;
        listaHojas = null;
        etiquetaProgreso = null;
        barra = null;
        reporte = null;
    }

    /**
     * Crea un nuevo objeto para generar una base de datos que ya tiene definido 
     * un nombre para esta y tambien define la estructura que van a tener las tablas.
     * @param conn Conexión con la base de datos.
     * @param nomBase Nombre de la base de datos nueva.
     * @param listaHojas Información de la estructura de las hojas en el archivo.
     * @param panel JPanel sobre el que se realiza la importación de bases.
     */
    public GeneradorBD(Conexion conn,String nomBase,List<HojaLista> listaHojas,PanelImport panel){
        this.conn = conn;
        nombreBase = nomBase;
        lector = panel.getLector();
        hojaActual = null;
        this.listaHojas = listaHojas;
        etiquetaProgreso = panel.getInfoImport();
        btnImportar = panel.getBtnImportar();
        barra = panel.getBarraProgreso();
        reporte = panel.getRep();
    }
    
    /**
     * Método que utiliza un objeto de tipo HojaLista para crear una nueva tabla
     * en la base de datos.
     * @param hoja Información de la hoja que se va a utilizar para crear la tabla.
     * @throws SQLException Error al ejecutar scripts en el servidor de bases de datos.
     */
    private void crearTabla(HojaLista hoja) throws SQLException{
        if(isCancelled()){
            return;
        }
        String scriptTabla = crearScriptTabla(hoja); 
        conn.modificarBase(scriptTabla); // crear la tabla
    }
    
    /**
     * Elabora el script para crear una tabla nueva utilizando la información de
     * la hoja pasada como parámetro.
     * @param hoja Información de la hoja que se va a utilizar para crear la tabla.
     * @return Instrucción MySQL para crear la nueva tabla.
     * @throws SQLException Error al ejecutar scripts en el servidor de bases de datos.
     */
    private String crearScriptTabla(HojaLista hoja) throws SQLException{
        String script;
        ArrayList<String> camposLlave = new ArrayList<>();
        int numCol = hoja.obtenerColumnas().size();
        
        script = "CREATE TABLE " + nombreBase + "." + hoja.getNombre() + "(\n";
        // Definir el nombre de las columnas, sus tipos de datos y sus modificadores
        for(int j=0;j<numCol;j++){
            InfoColumna columna = hoja.obtenerColumna(j);
            script += columna.getNombre() + " " + columna.getNombreTipo();
            if(!columna.getParametros().equals(""))
                script += "(" + columna.getParametros() + ")";
            if(columna.getPK())
                camposLlave.add(columna.getNombre());
            if(columna.getUN())
                script += " UNSIGNED";
            if(columna.getNN()){
                script += " NOT NULL";
            }else{
                script += " NULL";
            }
            if(columna.getUQ())
                script += " UNIQUE";
            if(columna.getAI())
                script += " AUTO_INCREMENT"; 
            // Añadir una coma a cada columna, excepto a la última
            if(j!=numCol-1)
                script += ",\n";
        }
        // Incluir en el script las columnas que forman la llave primaria
        if(!camposLlave.isEmpty()){
            script += ",\nPRIMARY KEY (";
            for(int k=0;k<camposLlave.size();k++){
                script += camposLlave.get(k);
                if(k!=camposLlave.size()-1){
                    script += ", ";
                }
            }           
            script += ")";
        }    
        script += ");";
        //System.out.println(script);
        return script;
    }
    
    /**
     * Método que permite insertar nuevos registros en una tabla.
     * @param hoja Hoja de la que se va a extraer la información.
     * @throws SQLException Error al intentar insertar el nuevo registro.
     */
    private void insertarRegistros(HojaLista hoja) throws SQLException{
        float incremento;    // porcentaje de progreso que le corresponde a cada renglón
        float progreso;      // porcentaje de importación de la hoja 
        int renglon = 1;     // número de renglón desde el que se empiezan a insertar los registros
        String scriptInsertar;
        
        Row encabezado = hojaActual.getRow(hojaActual.getFirstRowNum());
        int numColumnas = encabezado.getPhysicalNumberOfCells();
        int numRenglones = hojaActual.getPhysicalNumberOfRows();
        int indiceColInicio = encabezado.getFirstCellNum();
        
        incremento = 100F/(hojaActual.getPhysicalNumberOfRows()-1);
        
        if(numRenglones>1){
            Row renglonActual;
            Iterator<Row> iteradorRenglon = hojaActual.rowIterator();
            iteradorRenglon.next(); // saltar el encabezado
            while(iteradorRenglon.hasNext()){
                if(isCancelled()){
                    return;
                }
                renglonActual = iteradorRenglon.next();
                scriptInsertar = crearScriptRegistro(hoja.getNombre(),renglonActual,numColumnas,indiceColInicio);
                try{
                    conn.modificarBase(scriptInsertar);
                }catch(SQLException ex){
                    identificarFallo(ex);
                    evento = "Error en el renglón "+(renglon)+", código "+ex.getErrorCode() 
                        +" \n\t("+ex.getMessage()+")";
                    reporte.agregarEvento(evento);
                    //ex.printStackTrace();
                }finally{
                    progreso = renglon*incremento;
                    renglon++;
                    try{
                        Thread.sleep(1);
                        publish(Math.round(progreso)); // Mostrar el porcentaje en la barra de progreso
                    }catch(Exception e){}
                }
            }      
            evento = "Inserción de registros terminada.\n";
            reporte.agregarEvento(evento);
        }
    }
    
    /**
     * Crea una instrucción para insertar un registro en la tabla especificada.
     * @param nomTabla Nombre de la tabla nueva.
     * @param renglonAct Renglón (del archivo de Excel) del que se van a leer 
     * los datos.
     * @param numColumnas Número de campos en el renglón.
     * @param indiceColInicio Índice de la primer columna con datos de la hoja.
     * @return Instrucción MySQL para insertar un nuevo registro.
     */
    private String crearScriptRegistro(String nomTabla, Row renglonAct, int numColumnas, int indiceColInicio){
        String scriptInsertar = "INSERT INTO " + nombreBase + "." + nomTabla + " VALUES (";
        Object []celdas = new Object[numColumnas];
        for(int i=0;i<numColumnas;i++){   
            celdas[i] = renglonAct.getCell(i+indiceColInicio);
            if(celdas[i]==null) // Comprobar si la celda existe en el archivo              
                scriptInsertar += "null";
            else
                scriptInsertar += "'"+celdas[i]+"'"; // EL campo contiene texto o ''
            
            if(i!=numColumnas-1)
                scriptInsertar += ",";
        }  
        scriptInsertar += ");";
        //System.out.println(scriptInsertar);
        return scriptInsertar;
    }
    
    /**
     * Método que muestra un cuadro de diálogo indicando que ha ocurrido una
     * excepción que causó que el proceso de importación se detuviera.
     * @param codigo Número de identificación de la excepción MySQL.
     */
    @Override
    public void mostrarMsgError(int codigo){
        JOptionPane.showMessageDialog(
            null, 
            "No se puede continuar con la creación de la base de \n"
                + "datos debido al error MySQL de código " + codigo + ".  ",
            "Error", 
            JOptionPane.ERROR_MESSAGE
        );
    }
    
    /**
     * Ejecuta en segundo plano la creación de la base de datos.
     * @return void.
     */
    @Override 
    protected Void doInBackground(){
        String nomTabla;
        int numTablas = listaHojas.size();
        
        try{
            // Crear la base de datos
            btnImportar.setText("Cancelar importación");
            reporte.restablecer();
            etiquetaProgreso.setText("Iniciando la creación de la base de datos...");
            conn.crearBase(nombreBase);
            evento = "Esquema '"+nombreBase+"' creado.\n";
            reporte.agregarEvento(evento);
            
            // Crear las tablas e insertar los registros en cada una.
            for(int i=0;i<listaHojas.size();i++){
                // Comprobar si el proceso sigue ejecutándose o tuvo que detenerse
                if(isCancelled()){               
                    etiquetaProgreso.setText("Importación de la base '" + nombreBase + "' cancelada.");                  
                    evento = "Se canceló la importación del esquema '" + nombreBase + "'.";
                    conn.terminarConexion();
                    reporte.agregarEvento(evento);
                    btnImportar.setText("Crear base de datos");
                    return null;
                }
                try{
                    hojaActual = lector.getLibro().getSheetAt(i);
                    // Comprobar que la hoja que va a ser convertida en tabla tiene información
                    if(hojaActual.getPhysicalNumberOfRows()>0){
                        // Crear la tabla
                        nomTabla = listaHojas.get(i).getNombre();
                        etiquetaProgreso.setText("Creando la base '"+nombreBase+"': Definiendo la estructura de la tabla '"+nomTabla+"'");              
                        crearTabla(listaHojas.get(i));
                        evento = "Estructura de la tabla '"+nomTabla+"' creada.";
                        reporte.agregarEvento(evento);
                        
                        // Insertar los datos en la tabla
                        etiquetaProgreso.setText("Creando la base '"+nombreBase+"': Insertando datos en la tabla '"+nomTabla+"' (Tabla "+(i+1)+" de "+numTablas+")");
                        evento = "Iniciando la inserción de registros en la tabla '"+nomTabla+"'.";
                        reporte.agregarEvento(evento);
                        insertarRegistros(listaHojas.get(i));
                    }
                }catch(SQLException ex){ // Errores al crear tablas
                    identificarFallo(ex);
                    evento = "Error al crear la tabla '" 
                            + listaHojas.get(i).getNombre() 
                            + "', código " + ex.getErrorCode() 
                            + " \n\t("+ ex.getMessage()+ ")\n";                  
                    reporte.agregarEvento(evento);
                    //ex.printStackTrace();
                }                     
            }
            etiquetaProgreso.setText("Creación de la base de datos '"+nombreBase+"' terminada.");
            evento = "Importación de datos terminada.\n";
            reporte.agregarEvento(evento);
            publish(100);
            conn.terminarConexion();
        }catch(SQLException ex){ // Errores al crear la base de datos                
            evento = "[No se pudo continuar con la creación del "
                    + "esquema '" + nombreBase + "'.\n"
                    + "\tError MySQL " + ex.getErrorCode() + "\n"
                    + "\t" + ex.getMessage() + "\n";
            reporte.agregarEvento(evento);
            //ex.printStackTrace();
            etiquetaProgreso.setText("Falló el intento para importar los datos en '"+nombreBase+"'");
            publish(0);
        }
        btnImportar.setText("Crear base de datos");
        return null;
    }
    
    /**
     * Actualiza el valor mostrado en la barra de progreso.
     * @param chunks Lista de valores que se van a mostrar en la barra. 
     */
    @Override
    protected void process(List<Integer> chunks){
        //barra.setValue(chunks.get(0));
        chunks.stream().forEach((valor) -> {
            barra.setValue(valor);
        });
    }
}