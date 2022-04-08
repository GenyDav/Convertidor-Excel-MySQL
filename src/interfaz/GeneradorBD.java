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
    
    public GeneradorBD(Conexion conn,LectorExcel lector,String nomBase,List<TablaLista> listaHojas,JLabel etiqueta,JProgressBar barra){
        this.conn = conn;
        nombreBase = nomBase;
        this.lector = lector;
        this.listaHojas = listaHojas;
    }
    
    private String crearScriptTabla(String nombreBase,TablaLista hoja){
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
    
    private void insertarRegistros(String nomBase,TablaLista hoja){
        Sheet hojaActual = lector.getLibro().getSheetAt(hoja.getPosicion());
        Row encabezado = hojaActual.getRow(hojaActual.getFirstRowNum());
        int numColumnas = encabezado.getPhysicalNumberOfCells();
        int numRenglones = hojaActual.getPhysicalNumberOfRows();
        int indiceColInicio = encabezado.getFirstCellNum();
        
        System.out.println("Número de registros: "+hojaActual.getPhysicalNumberOfRows()); // contando el encabezado
        String scriptInsertar = "";
        
        if(numRenglones>1){
            Row renglonArch;
            Iterator<Row> iteradorRenglon = hojaActual.rowIterator();
            
            renglonArch = iteradorRenglon.next(); // saltar el encabezado
            while(iteradorRenglon.hasNext()){
                renglonArch = iteradorRenglon.next();
                scriptInsertar = crearScriptRegistro(nomBase,hoja.getNombre(),renglonArch,numColumnas,indiceColInicio);
                System.out.println(scriptInsertar);
                try {
                    conn.modificarBase(scriptInsertar);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    //Error 1406
                }
            }
        }
    }
    
    private String crearScriptRegistro(String nomBase,String nomHoja,Row renglonAct,int numColumnas, int indiceColInicio){
        String scriptInsertar = "INSERT INTO " + nomBase + "." + nomHoja + " VALUES (";
        Object []renglon = new Object[numColumnas];
    
        for(int i=0;i<numColumnas;i++){
            renglon[i] = renglonAct.getCell(i+indiceColInicio);
            scriptInsertar += "'"+renglon[i]+"'";
            if(i!=numColumnas-1){
                scriptInsertar += ",";
            }
        }  
        scriptInsertar += ");";
        return scriptInsertar;
    }
    
    @Override
    protected Void doInBackground(){
        String scriptTabla;
        try{
            conn.crearBase(nombreBase);
            for(int i=0;i<listaHojas.size();i++){
                try{
                    scriptTabla = crearScriptTabla(nombreBase,listaHojas.get(i)); 
                    conn.modificarBase(scriptTabla); // crear la tabla
                    System.out.println(scriptTabla);
                    insertarRegistros(nombreBase,listaHojas.get(i));
                    System.out.println("=========================================");
                }catch(SQLException ex){
                    //Errores al crear tablas
                    System.err.println(ex.getErrorCode());
                    System.out.println(listaHojas.get(i).getNombre());
                    ex.printStackTrace();
                }
            }
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
}
