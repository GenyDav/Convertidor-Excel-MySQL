/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import conexion.Conexion;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Geny
 */
public class FormatoTabla extends SwingWorker<String,Integer>{
    private DefaultTableModel modelo;
    private JTable tabla;
    private Conexion conn;
    private ResultSet resultados;
    private ResultSetMetaData metaDatos;
    private int numColumnas;
    private int numRegistros;
    private String nomBase, nomTabla;
    private JProgressBar barra;
    private float progreso;
    
    public FormatoTabla(JTable t,Conexion c,String nomBase,String nomTabla,JProgressBar b) throws SQLException{
        modelo = new DefaultTableModel();
        tabla = t;
        tabla.setModel(modelo);
        //resultados = c.obtenerRegistros(nomBase, nomTabla);
        conn = c;
        metaDatos = null;
        numColumnas = 0;
        numRegistros = 0;
        this.nomBase = nomBase;
        this.nomTabla = nomTabla;
        barra = b;
        barra.setValue(0);
        progreso = 0.0F;
    } 
    
    public void asignarNombresColumnas() throws SQLException{
        numColumnas = metaDatos.getColumnCount();   // obtener el número de columnas
        String []columnas = new String[numColumnas];
        for(int i=0;i<numColumnas;i++){             // obtener el nombre de cada columna
            columnas[i] = metaDatos.getColumnName(i+1);
        }
        modelo.setColumnIdentifiers(columnas);
    }
    
    public void mostrarInformacion() throws SQLException{
        float incremento;
        int numReg = 0;
        
        resultados.last();
        numRegistros = resultados.getRow();
        System.out.println("numero de registros: "+numRegistros);
        incremento = 100F/numRegistros;
        System.out.println("incremento: "+incremento);
        resultados.beforeFirst();
        Object []renglon = new Object[numColumnas];
        while(resultados.next()){           
            for(int i=0;i<numColumnas;i++){
                renglon[i] = resultados.getObject(i+1);
            }
            modelo.addRow(renglon);
            
            numReg++;
            progreso = numReg*incremento;
            //progreso+=incremento;
            System.out.println("progreso: "+progreso);
            try{
                Thread.sleep(500);
            }catch(Exception e){
                
            }
            publish(Math.round(progreso));
        }      
    }
    
    @Override
    public String doInBackground() throws SQLException{
        resultados = conn.obtenerRegistros(nomBase, nomTabla);
        metaDatos = resultados.getMetaData();
        asignarNombresColumnas();
        mostrarInformacion();
        return numRegistros + " registros cargados";
    }
    
    @Override
    protected void done(){
        
    }
    
    @Override
    protected void process(List<Integer> chunks){
        barra.setValue(chunks.get(0));
        System.out.println("chunk: "+chunks.get(0));
        barra.setString(Integer.toString(chunks.get(0))+"%");
    }
}
