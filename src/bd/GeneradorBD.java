/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

import datos.HojaLista;
import datos.InfoColumna;
import datos.Tiempo;
import datos.Tipo;
import excel.LectorExcel;
import interfaz.PanelImport;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 *
 * @author Geny
 */
public class GeneradorBD extends SwingWorker<Void,Integer>{
    private Conexion conn;
    private String nombreBase;
    private LectorExcel lector;
    private Sheet hojaActual;
    private List<HojaLista> listaHojas;
    
    private JLabel etiqueta;
    private JButton btnImportar;
    private JProgressBar barra;
    private JTextArea areaRep;
    
    private String evento;    
    private Tiempo temp;
    
    public GeneradorBD(){
        conn = null;
        nombreBase = null;
        lector = null;
        hojaActual = null;
        listaHojas = null;
        etiqueta = null;
        barra = null;
        temp = null;
        areaRep = null;
    }
    
    public GeneradorBD(Conexion conn,String nomBase,List<HojaLista> listaHojas,PanelImport panel){
        this.conn = conn;
        nombreBase = nomBase;
        lector = panel.getLector();
        hojaActual = null;
        this.listaHojas = listaHojas;
        etiqueta = panel.getInfoImport();
        btnImportar = panel.getBtnImportar();
        barra = panel.getBarraProgreso();
        temp = new Tiempo();
        areaRep = panel.getRep().getTextArea();
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
            script += columna.getNombre()+" "+Tipo.NOMBRES[columna.getTipo()];
            if(!columna.getParametros().equals(""))
                script += "("+columna.getParametros()+")";
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
            if(j!=numCol-1)
                script += ",\n";
        }
        // Definir que columnas forman la llave primaria
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
        System.out.println(script);
        return script;
    }
    
    /**
     * Método que utiliza un objeto de tipo HojaLista para crear una nueva tabla
     * en la base de datos.
     * @param hoja Información de la hoja que se va a utilizar para crear la tabla.
     * @throws SQLException Error al ejecutar scripts en el servidor de bases de datos.
     */
    private void crearTabla(HojaLista hoja) throws SQLException{
        if(isCancelled()){
            etiqueta.setText("Importación de la base '" + nombreBase + "' cancelada.");
            return;
        }
        String scriptTabla = crearScriptTabla(hoja); 
        conn.modificarBase(scriptTabla); // crear la tabla
    }
    
    private String crearScriptRegistro(String nomBase,String nomHoja,Row renglonAct,int numColumnas, int indiceColInicio){
        String scriptInsertar = "INSERT INTO " + nomBase + "." + nomHoja + " VALUES (";
        Object []celdas = new Object[numColumnas];
        for(int i=0;i<numColumnas;i++){   
            celdas[i] = renglonAct.getCell(i+indiceColInicio);
            //System.out.print("["+celdas[i]+"]");
            if(celdas[i]==null){              
                scriptInsertar += "null";
            }else{
                scriptInsertar += "'"+celdas[i]+"'"; // texto o ''
            }
            if(i!=numColumnas-1){
                scriptInsertar += ",";
            }
        }  
        scriptInsertar += ");";
        //System.out.println(scriptInsertar);
        return scriptInsertar;
    }
    
    private void insertarRegistros(String nomBase,HojaLista hoja) throws SQLException{
        //Sheet hojaActual = lector.getLibro().getSheetAt(hoja.getPosicion());
        Row encabezado = hojaActual.getRow(hojaActual.getFirstRowNum());
        int numColumnas = encabezado.getPhysicalNumberOfCells();
        int numRenglones = hojaActual.getPhysicalNumberOfRows();
        int indiceColInicio = encabezado.getFirstCellNum();
        
        float incremento;
        float progreso = 0F;
        int renglon = 1;
        
        incremento = 100F/(hojaActual.getPhysicalNumberOfRows()-1);
        //System.out.println("Incremento: "+incremento);
        //System.out.println("Número de registros: "+hojaActual.getPhysicalNumberOfRows()); // contando el encabezado
        String scriptInsertar = "";
        
        if(numRenglones>1){
            Row renglonArch;
            Iterator<Row> iteradorRenglon = hojaActual.rowIterator();
            
            renglonArch = iteradorRenglon.next(); // saltar el encabezado
            while(iteradorRenglon.hasNext()){
                if(isCancelled()){
                    etiqueta.setText("Importación de la base '"+nombreBase+"' cancelada.");
                    return;
                }
                renglonArch = iteradorRenglon.next();
                scriptInsertar = crearScriptRegistro(nomBase,hoja.getNombre(),renglonArch,numColumnas,indiceColInicio);
                System.out.println(scriptInsertar);
                try{
                    conn.modificarBase(scriptInsertar);
                }catch(SQLException ex){ // errores al insertar registros
                    System.out.println(ex.getMessage());
                    System.out.println(ex.getErrorCode());
                    System.out.println(ex.getSQLState());
                    identificarFallo(ex);
                    ex.printStackTrace();
                    evento = "[" + temp.obtenerTiempo() + "] Error en la línea " + (renglon+1) + ", código " + ex.getErrorCode() 
                        +" \n\t("+ ex.getMessage()+ ")\n";
                    //System.out.println(reporte);
                    areaRep.append(evento);
                    //Error 1406
                }finally{
                    progreso = renglon*incremento;
                    renglon++;
                    //System.out.println(progreso+"%");
                    try{
                        Thread.sleep(1);
                        publish(Math.round(progreso));
                    }catch(Exception e){}
                }
            }      
            evento = "[" + temp.obtenerTiempo() + "] Inserción de registros terminada.\n";
            //System.out.println(reporte);
            areaRep.append(evento);
        }
    }
    
    private void identificarFallo(SQLException ex) throws SQLException{
        switch(ex.getErrorCode()){//1366
            case 1236:
            case 2002:
            case 2003: 
            case 126:
            case 127:
            case 134:
            case 144:
            case 145:
            case 1146:
            case 22:
            case 24:
            case 0:
                throw new SQLException(ex.getMessage(),ex.getSQLState(),ex.getErrorCode());   
            default:
                /*ex.printStackTrace();
                reporte = "[" + temp.obtenerTiempo() + "] Error en la línea " + (renglon+1) + ", código " + ex.getErrorCode() 
                    +" \n\t("+ ex.getMessage()+ ")\n";
                //System.out.println(reporte);
                areaRep.append(reporte);
                //Error 1406
                break;*/
        }  
    }
    
    @Override 
    protected Void doInBackground(){
        String nomTabla;
        int numTablas = listaHojas.size();
        
        try{
            // Crear la base de datos
            btnImportar.setText("Cancelar importación");
            areaRep.setText("");
            etiqueta.setText("Iniciando la creación de la base de datos...");
            conn.crearBase(nombreBase);
            evento = "["+temp.obtenerTiempo()+"] Esquema '"+nombreBase+"' creado.\n";
            areaRep.append(evento);
            
            // Crear las tablas e insertar los registros en cada una
            for(int i=0;i<listaHojas.size();i++){
                // Comprobar si el usuario canceló el proceso
                if(isCancelled()){
                    etiqueta.setText("Importación de la base '" + nombreBase + "' cancelada.");
                    evento = "[" + temp.obtenerTiempo() + "] Se canceló la importación del esquema '" + nombreBase + "'.\n";
                    areaRep.append(evento);
                    btnImportar.setText("Crear base de datos");
                    return null;
                }
                try{
                    hojaActual = lector.getLibro().getSheetAt(i);
                    // Comprobar que la hoja que va a ser convertida en tabla tiene información
                    if(hojaActual.getPhysicalNumberOfRows()>0){
                        nomTabla = listaHojas.get(i).getNombre();
                        etiqueta.setText("Creando la base '"+nombreBase+"': Definiendo la estructura de la tabla '"+nomTabla+"'");              
                        crearTabla(listaHojas.get(i));
                        evento = "[" +temp.obtenerTiempo()+"] Estructura de la tabla '"+nomTabla+"' creada.\n";
                        areaRep.append(evento);

                        etiqueta.setText("Creando la base '"+nombreBase+"': Insertando datos en la tabla '"+nomTabla+"' (Tabla "+(i+1)+" de "+numTablas+")");
                        evento = "[" +temp.obtenerTiempo()+"] Iniciando la inserción de registros en la tabla '"+nomTabla+"'.\n";
                        //System.out.println(reporte);
                        areaRep.append(evento);

                        insertarRegistros(nombreBase,listaHojas.get(i));
                        System.out.println("=========================================");
                    }
                }catch(SQLException ex){ // las excepciones de insertarRegitros caen aqui
                    //Errores al crear tablas
                    System.out.println("Mensaje: "+ex.getMessage());
                    System.out.println("Código: "+ex.getErrorCode());
                    System.out.println("Estado:"+ex.getSQLState());
                   
                    identificarFallo(ex);
                    evento = "[" +temp.obtenerTiempo()+ "] Error al crear la tabla '" + listaHojas.get(i).getNombre() + "', código " + ex.getErrorCode() 
                        +" \n\t("+ ex.getMessage()+ ")\n";                  
                    //System.out.println(reporte);
                    areaRep.append(evento);
                    ex.printStackTrace();
                }                     
            }
            etiqueta.setText("Creación de la base de datos '"+nombreBase+"' terminada.");
            evento = "[" +temp.obtenerTiempo()+"] Importación de datos terminada.\n";
            //System.out.println(reporte);
            areaRep.append(evento);
            //publish(100);
            barra.setValue(0);
        }catch(SQLException ex){
            System.out.println("=============");                  
            /*System.out.println(ex.getMessage());
            System.out.println(ex.getErrorCode());
            System.out.println(ex.getSQLState());*/
            evento = "[" +temp.obtenerTiempo()+"] No se pudo continuar con la creación del "
                    + "esquema '"+nombreBase+"'.\n"
                    + "\tError "+ex.getErrorCode() +"\n"
                    + "\t"+ ex.getMessage()+ "\n";
            areaRep.append(evento);
            ex.printStackTrace();
            etiqueta.setText("Falló el intento para importar los datos en '"+nombreBase+"'");
            barra.setValue(0);
            System.out.println(ex.getErrorCode());
            
            JOptionPane.showMessageDialog(
                null, 
                "No se puede continuar con la creación de la base de datos \n"
                    + "debido al error MySQL con código "+ex.getErrorCode(),
                "Error", 
                JOptionPane.ERROR_MESSAGE
            );
            //1006 no se puede crear la base de datos
            /*if(ex.getErrorCode()==1007){ // ya existe una base de datos con ese nombre
                JOptionPane.showMessageDialog(
                    null, 
                    "Ya existe una base de datos con el nombre '" + nombreBase + "'.  ",
                    "No se puede crear la base de datos", 
                    JOptionPane.ERROR_MESSAGE
                );
            }
            if(ex.getErrorCode()==0){
                JOptionPane.showMessageDialog(
                    null, 
                    "La conexión con el servidor se perdió.",
                    "No se puede crear la base de datos", 
                    JOptionPane.ERROR_MESSAGE
                );
            }*/
        }
        //btnImportar.setText("Crear base de datos");
        return null;
    }
    
    @Override
    protected void process(List<Integer> chunks){
        barra.setValue(chunks.get(0));
    }
}