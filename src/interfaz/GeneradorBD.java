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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
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
    private JProgressBar barra;
    
    public GeneradorBD(Conexion conn,LectorExcel lector,String nomBase,List<TablaLista> listaHojas,JLabel etiqueta,JProgressBar barra){
        this.conn = conn;
        nombreBase = nomBase;
        this.lector = lector;
        this.listaHojas = listaHojas;
        this.etiqueta = etiqueta;
        this.barra = barra;
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
        System.out.println(scriptTabla);
    }
    
    private String crearScriptRegistro(String nomBase,String nomHoja,Row renglonAct,int numColumnas, int indiceColInicio){
        String scriptInsertar = "INSERT INTO " + nomBase + "." + nomHoja + " VALUES (";
        Object []celdas = new Object[numColumnas];
        for(int i=0;i<numColumnas;i++){   
            celdas[i] = renglonAct.getCell(i+indiceColInicio);
            System.out.println(celdas[i]);
            if(celdas[i]==null){              
                scriptInsertar += "null";
            }else{
                scriptInsertar += "'"+celdas[i]+"'";
            }
            if(i!=numColumnas-1){
                scriptInsertar += ",";
            }
        }  
        scriptInsertar += ");";
        System.out.println(scriptInsertar);
        return scriptInsertar;
    }
    
    private void insertarRegistros(String nomBase,TablaLista hoja){
        Sheet hojaActual = lector.getLibro().getSheetAt(hoja.getPosicion());
        Row encabezado = hojaActual.getRow(hojaActual.getFirstRowNum());
        int numColumnas = encabezado.getPhysicalNumberOfCells();
        int numRenglones = hojaActual.getPhysicalNumberOfRows();
        int indiceColInicio = encabezado.getFirstCellNum();
        
        float incremento;
        float progreso = 0F;
        int renglon = 1;
        
        incremento = 100F/(hojaActual.getPhysicalNumberOfRows()-1);
        System.out.println("Incremento: "+incremento);
        System.out.println("Número de registros: "+hojaActual.getPhysicalNumberOfRows()); // contando el encabezado
        String scriptInsertar = "";
        
        // guardar en un array cada columna de la hoja indicando si su valor puede ser nulo o no
        /*boolean []esNulo = new boolean[hoja.obtenerColumnas().size()];
        for(int j=0;j<hoja.obtenerColumnas().size();j++){
            esNulo[j] = hoja.obtenerColumnas().get(j).getNN();
            System.out.println(esNulo[j]);
        }*/
        
        if(numRenglones>1){
            Row renglonArch;
            Iterator<Row> iteradorRenglon = hojaActual.rowIterator();
            
            renglonArch = iteradorRenglon.next(); // saltar el encabezado
            while(iteradorRenglon.hasNext()){
                renglonArch = iteradorRenglon.next();
                scriptInsertar = crearScriptRegistro(nomBase,hoja.getNombre(),renglonArch,numColumnas,indiceColInicio);
                try{
                    conn.modificarBase(scriptInsertar);
                }catch(SQLException ex){ // errores al insertar registros
                    System.out.println("Error en el renglón "+(renglon+1)+ ", error: "+ex.getErrorCode());
                    System.out.println(scriptInsertar);
                    ex.printStackTrace();
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
        }
    }
    
    @Override
    protected Void doInBackground(){
        String nomTabla;
        int numTablas = listaHojas.size();
        try{
            etiqueta.setText("Iniciando la creación de la base de datos...");
            conn.crearBase(nombreBase);
            for(int i=0;i<listaHojas.size();i++){
                try{
                    nomTabla = listaHojas.get(i).getNombre();
                    etiqueta.setText("Creando la base '"+nombreBase+"': Definiendo la estructura de la tabla '"+nomTabla+"'");              
                    crearTabla(nombreBase,listaHojas.get(i));
                    
                    etiqueta.setText("Creando la base '"+nombreBase+"': Insertando datos en la tabla '"+nomTabla+"' (Tabla "+(i+1)+" de "+numTablas+")");
                    insertarRegistros(nombreBase,listaHojas.get(i));
                    System.out.println("=========================================");
                }catch(SQLException ex){
                    //Errores al crear tablas
                    System.out.println("Error al crear la tabla "+listaHojas.get(i).getNombre()+" "+ex.getErrorCode());
                    System.out.println(listaHojas.get(i).getNombre());
                    ex.printStackTrace();
                }
            }
            etiqueta.setText("Creación de la base de datos terminada.");
        }catch(SQLException ex){
            ex.printStackTrace();
            System.out.println(ex.getErrorCode());
            if(ex.getErrorCode()==1007){ // ya existe una base de datos con ese nombre
                JOptionPane.showMessageDialog(
                    null, 
                    "Ya existe una base de datos con el nombre '" + nombreBase + "'.  ",
                    "No se puede crear la base de datos", 
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
        return null;
    }
    
    @Override
    protected void process(List<Integer> chunks){
        barra.setValue(chunks.get(0));
    }
}
