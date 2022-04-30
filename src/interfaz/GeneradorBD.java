/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import conexion.Conexion;
import excel.LectorExcel;
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
    private List<TablaLista> listaHojas;
    
    private JLabel etiqueta;
    private JButton btnImportar;
    private JProgressBar barra;
    private JTextArea areaRep;
    
    private String reporte;    
    private Tiempo temp;
    
    private boolean generandoBase;
    
    public GeneradorBD(){
        conn = null;
        nombreBase = null;
        lector = null;
        listaHojas = null;
        etiqueta = null;
        barra = null;
        temp = null;
        areaRep = null;
        generandoBase = false;
    }
    
    public GeneradorBD(Conexion conn,LectorExcel lector,String nomBase,List<TablaLista> listaHojas,JLabel etiqueta,JButton btn,JProgressBar barra,JTextArea areaTexto){
        this.conn = conn;
        nombreBase = nomBase;
        this.lector = lector;
        this.listaHojas = listaHojas;
        this.etiqueta = etiqueta;
        btnImportar = btn;
        this.barra = barra;
        temp = new Tiempo();
        areaRep = areaTexto;
        generandoBase = false;
    }
    
    private String crearScriptTabla(String nombreBase,TablaLista hoja) throws SQLException{
        System.out.println("Indice de la hoja: "+hoja.getPosicion());
        String script;
        ArrayList<String> llavePrimaria = new ArrayList<>();
        int numCol = hoja.obtenerColumnas().size();
        script = "CREATE TABLE " + nombreBase + "." + hoja.getNombre() + "(\n";
        for(int j=0;j<numCol;j++){
            InfoColumna col = hoja.obtenerColumna(j);
            script += col.getNombre()+" "+Tipo.TIPO[col.getTipo()];
            if(!col.getParametros().equals(""))
                script+="("+col.getParametros()+")";
            if(col.getPK())
                llavePrimaria.add(col.getNombre());
            if(col.getUN())
                script+=" UNSIGNED";
            if(col.getNN()){
                script+=" NOT NULL";
            }else{
                script+=" NULL";
            }
            if(col.getUQ())
                script+=" UNIQUE";
            if(col.getAI())
                script+=" AUTO_INCREMENT"; 
            if(j<numCol-1)
                script += ",\n";
        }
        if(!llavePrimaria.isEmpty()){
            script += ",\nPRIMARY KEY (";
            for(int k=0;k<llavePrimaria.size();k++){
                script += llavePrimaria.get(k);
                if(k!=llavePrimaria.size()-1){
                    script += ", ";
                }
            }           
            script += ")";
        }    
        script += ");";
        return script;
    }
    
    private void crearTabla(String nombreBase,TablaLista hoja) throws SQLException{
        String scriptTabla = crearScriptTabla(nombreBase,hoja); 
        conn.modificarBase(scriptTabla); // crear la tabla
        //System.out.println(scriptTabla);
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
    
    private void insertarRegistros(String nomBase,TablaLista hoja) throws SQLException{
        Sheet hojaActual = lector.getLibro().getSheetAt(hoja.getPosicion());
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
                    /*etiqueta.setText("Cerrando la conexión...");
                    barra.setValue(0);
                    try{
                        Thread.sleep(250);
                    }catch(Exception e){}
                    System.out.println("Cerrando la conexión...");*/
                    return;
                }
                renglonArch = iteradorRenglon.next();
                scriptInsertar = crearScriptRegistro(nomBase,hoja.getNombre(),renglonArch,numColumnas,indiceColInicio);
                try{
                    conn.modificarBase(scriptInsertar);
                }catch(SQLException ex){ // errores al insertar registros
                    System.out.println(ex.getMessage());
                    System.out.println(ex.getErrorCode());
                    System.out.println(ex.getSQLState());
                    identificarFallo(ex);
                    ex.printStackTrace();
                    reporte = "[" + temp.obtenerTiempo() + "] Error en la línea " + (renglon+1) + ", código " + ex.getErrorCode() 
                        +" \n\t("+ ex.getMessage()+ ")\n";
                    //System.out.println(reporte);
                    areaRep.append(reporte);
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
            reporte = "[" + temp.obtenerTiempo() + "] Inserción de registros terminada.\n";
            //System.out.println(reporte);
            areaRep.append(reporte);
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
    
    public boolean estaActivo(){
        return generandoBase;
    }
    
    @Override 
    protected Void doInBackground(){
        btnImportar.setEnabled(false);
        generandoBase = true;
        String nomTabla;
        int numTablas = listaHojas.size();
        try{
            areaRep.setText("");
            etiqueta.setText("Iniciando la creación de la base de datos...");
            conn.crearBase(nombreBase);
            reporte = "["+temp.obtenerTiempo()+"] Esquema '"+nombreBase+"' creado.\n";
            areaRep.append(reporte);
            
            for(int i=0;i<listaHojas.size();i++){
                if(isCancelled()){
                    //etiqueta.setText("Cerrando la conexión...");
                    //System.out.println("Cerrando la conexión...");
                    /*try{
                        Thread.sleep(100);
                    }catch(Exception e){
                        
                    }*/
                    return null;
                }
                try{
                    nomTabla = listaHojas.get(i).getNombre();
                    etiqueta.setText("Creando la base '"+nombreBase+"': Definiendo la estructura de la tabla '"+nomTabla+"'");              
                    crearTabla(nombreBase,listaHojas.get(i));
                    reporte = "[" +temp.obtenerTiempo()+"] Estructura de la tabla '"+nomTabla+"' creada.\n";
                    areaRep.append(reporte);
                    
                    etiqueta.setText("Creando la base '"+nombreBase+"': Insertando datos en la tabla '"+nomTabla+"' (Tabla "+(i+1)+" de "+numTablas+")");
                    reporte = "[" +temp.obtenerTiempo()+"] Iniciando la inserción de registros en la tabla '"+nomTabla+"'.\n";
                    //System.out.println(reporte);
                    areaRep.append(reporte);
                    
                    insertarRegistros(nombreBase,listaHojas.get(i));
                    System.out.println("=========================================");
                }catch(SQLException ex){ // las excepciones de insertarRegitros caen aqui
                    //Errores al crear tablas
                    System.out.println("Mensaje: "+ex.getMessage());
                    System.out.println("Código: "+ex.getErrorCode());
                    System.out.println("Estado:"+ex.getSQLState());
                   
                    identificarFallo(ex);
                    reporte = "[" +temp.obtenerTiempo()+ "] Error al crear la tabla '" + listaHojas.get(i).getNombre() + "', código " + ex.getErrorCode() 
                        +" \n\t("+ ex.getMessage()+ ")\n";                  
                    //System.out.println(reporte);
                    areaRep.append(reporte);
                    ex.printStackTrace();
                }                     
            }
            etiqueta.setText("Creación de la base de datos '"+nombreBase+"' terminada.");
            reporte = "[" +temp.obtenerTiempo()+"] Importación de datos terminada.\n";
            //System.out.println(reporte);
            areaRep.append(reporte);
            //publish(100);
            barra.setValue(0);
        }catch(SQLException ex){
            System.out.println("=============");                  
            /*System.out.println(ex.getMessage());
            System.out.println(ex.getErrorCode());
            System.out.println(ex.getSQLState());*/
            reporte = "[" +temp.obtenerTiempo()+"] No se pudo continuar con la creación del "
                    + "esquema '"+nombreBase+"'.\n"
                    + "\tError "+ex.getErrorCode() +"\n"
                    + "\t"+ ex.getMessage()+ "\n";
            areaRep.append(reporte);
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
        generandoBase = false;
        btnImportar.setEnabled(true);
        return null;
    }
    
    @Override
    protected void process(List<Integer> chunks){
        barra.setValue(chunks.get(0));
    }
}