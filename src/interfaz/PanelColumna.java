/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author Geny
 */
public class PanelColumna extends javax.swing.JPanel {
    //ArrayList<InfoColumna> columnas;
    InfoColumna info;
    String expDecimal;
    Pattern pat;
    String expEnumSet;
    Pattern patronEnumSet;
    Pattern coma;
    JDialog ventana;
    
    /**
     * Creates new form PanelColumna
     * @param col
     * @param ventana
     * @param grupo
     */
    public PanelColumna(InfoColumna col, ConfigTipos ventana, ButtonGroup grupo) {
        initComponents();
        this.ventana = ventana;
        info = col;
        //this.posicion = posicion;
        //info = columnas.get(posicion);
        
        expDecimal = "(\\d+)\\s*,\\s*(\\d+)";
        pat = Pattern.compile(expDecimal);
        expEnumSet = "('(\\w+)'\\s*,\\s*)*'(\\w*)'";
        patronEnumSet = Pattern.compile(expEnumSet);
        coma = Pattern.compile(",");
        grupo.add(checkAI);
        
        cargarDatos();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        nombreCol = new javax.swing.JLabel();
        tipoCol = new javax.swing.JComboBox();
        parametros = new javax.swing.JTextField();
        checkPK = new javax.swing.JCheckBox();
        checkNN = new javax.swing.JCheckBox();
        checkUQ = new javax.swing.JCheckBox();
        jSeparator1 = new javax.swing.JSeparator();
        checkUN = new javax.swing.JCheckBox();
        jSeparator4 = new javax.swing.JSeparator();
        checkAI = new javax.swing.JCheckBox();

        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));

        nombreCol.setBackground(new java.awt.Color(255, 255, 255));
        nombreCol.setText("Columna1");
        nombreCol.setToolTipText("Nombre de la columna");

        tipoCol.setBackground(new java.awt.Color(255, 255, 255));
        tipoCol.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TINYINT", "SMALLINT", "MEDIUMINT", "INT", "BIGINT", "FLOAT", "DOUBLE", "DECIMAL", "CHAR", "VARCHAR", "BINARY", "VARBINARY", "TEXT", "SET", "ENUM", "DATE", "DATETIME", "TIME", "TIMESTAMP", "YEAR" }));
        tipoCol.setToolTipText("Tipo de dato de la columna");
        tipoCol.setPreferredSize(new java.awt.Dimension(84, 20));
        tipoCol.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboTipoStateChanged(evt);
            }
        });

        parametros.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        parametros.setText("0");
        parametros.setToolTipText("Tamaño");
        parametros.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                parametrosFocusLost(evt);
            }
        });
        parametros.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                parametrosKeyReleased(evt);
            }
        });

        checkPK.setBackground(new java.awt.Color(102, 102, 102));
        checkPK.setForeground(new java.awt.Color(255, 255, 255));
        checkPK.setText("PK");
        checkPK.setToolTipText("Llave primaria");
        checkPK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkPKActionPerformed(evt);
            }
        });

        checkNN.setBackground(new java.awt.Color(153, 153, 153));
        checkNN.setForeground(new java.awt.Color(255, 255, 255));
        checkNN.setText("NN");
        checkNN.setToolTipText("No nulo");
        checkNN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkNNActionPerformed(evt);
            }
        });

        checkUQ.setBackground(new java.awt.Color(153, 153, 153));
        checkUQ.setForeground(new java.awt.Color(255, 255, 255));
        checkUQ.setText("UQ");
        checkUQ.setToolTipText("Índice único");
        checkUQ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkUQActionPerformed(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        checkUN.setBackground(new java.awt.Color(153, 153, 153));
        checkUN.setForeground(new java.awt.Color(255, 255, 255));
        checkUN.setText("UN");
        checkUN.setToolTipText("Sin signo");
        checkUN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkUNActionPerformed(evt);
            }
        });

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);

        checkAI.setBackground(new java.awt.Color(153, 153, 153));
        checkAI.setForeground(new java.awt.Color(255, 255, 255));
        checkAI.setText("AI");
        checkAI.setToolTipText("Auto Incremento");
        checkAI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkAIActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nombreCol, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tipoCol, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(parametros, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(checkPK)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(checkNN)
                .addGap(4, 4, 4)
                .addComponent(checkUQ)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(checkUN)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(checkAI)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(nombreCol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tipoCol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(parametros)
                        .addComponent(checkPK)
                        .addComponent(checkNN)
                        .addComponent(checkUQ)
                        .addComponent(checkUN)
                        .addComponent(checkAI)))
                .addGap(3, 3, 3))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cargarDatos(){
        nombreCol.setText(info.getNombre());
        tipoCol.setSelectedIndex(info.getTipo());
        //System.err.println(info.getTipo());
        parametros.setText(info.getParametros());
        checkPK.setSelected(info.getPK());
        checkNN.setSelected(info.getNN());
        checkUQ.setSelected(info.getUQ());
        checkUN.setSelected(info.getUN());
        checkAI.setSelected(info.getAI());
    }
    
    private void checkPKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkPKActionPerformed
        if(checkPK.isSelected()){
            info.setPK(true);
            info.setNN(true);
            checkNN.setSelected(true);
        }else{
            info.setPK(false);
        }
    }//GEN-LAST:event_checkPKActionPerformed

    private void checkNNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkNNActionPerformed
        info.setNN(checkNN.isSelected());
    }//GEN-LAST:event_checkNNActionPerformed

    private void checkUQActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkUQActionPerformed
        info.setUQ(checkUQ.isSelected());
    }//GEN-LAST:event_checkUQActionPerformed

    private void checkUNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkUNActionPerformed
        info.setUN(checkUN.isSelected());
    }//GEN-LAST:event_checkUNActionPerformed

    private void checkAIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkAIActionPerformed
        info.setAI(checkAI.isSelected());
    }//GEN-LAST:event_checkAIActionPerformed

    private void comboTipoStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboTipoStateChanged
        info.setTipo(tipoCol.getSelectedIndex());
        switch(tipoCol.getSelectedIndex()){
            case Tipo.TINYINT:
            case Tipo.SMALLINT:
            case Tipo.MEDIUMINT:
            case Tipo.INT:
            case Tipo.BIGINT:
                configCampoParametros("",true,"Ancho de visualización");
                break;              
            case Tipo.FLOAT:
            case Tipo.CHAR:
            case Tipo.BINARY:
            case Tipo.TEXT:
                configCampoParametros("",true,"Tamaño");
                break;
            case Tipo.VARCHAR:
            case Tipo.VARBINARY:
                configCampoParametros("45",true,"Tamaño");
                break;
            case Tipo.DECIMAL:
                configCampoParametros("",true,"Número total de dígitos, número de dígitos decimales");
                break;
            case Tipo.SET:
            case Tipo.ENUM:
                configCampoParametros("''",true,"Lista de elementos");
                break;
            case Tipo.DOUBLE: 
            case Tipo.DATE: 
            case Tipo.DATETIME:
            case Tipo.TIME:
            case Tipo.TIMESTAMP:
            case Tipo.YEAR:   
                configCampoParametros("",false,"");
                break;
        }
    }//GEN-LAST:event_comboTipoStateChanged

    private void configCampoParametros(String texto,boolean enabled,String toolTip){
        parametros.setText(texto);
        parametros.setEnabled(enabled);
        parametros.setToolTipText(toolTip);
    }
    
    private void parametrosFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_parametrosFocusLost
        String entrada = parametros.getText();
        switch(tipoCol.getSelectedIndex()){
            case Tipo.TINYINT:
            case Tipo.SMALLINT:
            case Tipo.MEDIUMINT:
            case Tipo.INT:
            case Tipo.BIGINT:
                try{
                    if(!entrada.equals("")){
                        int tam = Integer.parseInt(entrada);
                        if(tam<1||tam>255){
                            throw new Exception();
                        }else{
                            info.setParametros(entrada);
                            System.out.println("PARAMETROS: "+info.getParametros());
                        }
                    }   
                }catch(Exception e){
                    mostrarMsgError(
                        "\nPuede dejar el campo vacío o incluir"
                        + "\nun valor numérico ubicado entre 1 y 255.  "
                    );
                    parametros.setText("");
                }
                break;
            case Tipo.DECIMAL:               
                //String expDecimal = "(\\d+)\\s*,\\s*(\\d+)";
                //Pattern pat = Pattern.compile(expDecimal);
                //String input = parametros.getText();
                try{
                    if(!entrada.equals("")){
                        Matcher mat = pat.matcher(entrada);
                        int n1,n2;
                        if (mat.matches()) {
                            System.out.println("Regexp encontrada");
                            n1 = Integer.parseInt(mat.group(1));
                            n2 = Integer.parseInt(mat.group(2));
                            //System.out.println("Sujeto:"+mat.group(1));
                            //System.out.println("Sujeto:"+mat.group(2));
                            if(n1>65||n2>30||n1<n2){
                                throw new Exception(); 
                            }else{
                                info.setParametros(entrada);
                            }
                        } else {
                            System.err.println("Regexp NO encontrada");
                            throw new Exception(); 
                        }
                    }
                }catch(Exception e){
                    mostrarMsgError(
                        " Puede dejar el campo vacío o incluir dos valores  "
                        + "\nnuméricos separados por coma (M,D).  "
                        + "\nEl valor máximo aceptado para M es 65, el valor máximo aceptado para D es 30. "
                        + "\nM debe ser mayor o igual a D."
                    );
                    parametros.setText("");
                }
                break;
            case Tipo.FLOAT:
                try{
                    if(!entrada.equals("")){
                        int tam = Integer.parseInt(entrada);
                        if(tam<0||tam>53){
                            throw new Exception();
                        }else{
                            info.setParametros(entrada);
                        }
                    }
                }catch(Exception e){
                    mostrarMsgError(
                        "\nPuede dejar el campo vacío o incluir"
                        + "\nun valor numérico ubicado entre 0 y 53.  "
                    );
                    parametros.setText("");
                }
                break;
            case Tipo.TEXT:
                try{
                    if(!entrada.equals("")){
                        int tam = Integer.parseInt(entrada);
                        if(tam<0){
                            throw new Exception();
                        }else{
                            info.setParametros(entrada);
                        }
                    }
                }catch(Exception e){
                    mostrarMsgError(
                        "\nPuede dejar el campo vacío o incluir"
                        + "\nun valor numérico igual o mayor a cero.  "
                    );
                    parametros.setText("");
                }
                break;
            case Tipo.ENUM:
            case Tipo.SET:
                Matcher mat = patronEnumSet.matcher(entrada);
                try{
                    if(mat.matches()){
                        System.out.println("Regexp encontrada"); 
                        info.setParametros(entrada);
                        // Verificar el número de elementos en la lista
                        if((tipoCol.getSelectedIndex()==Tipo.SET && obtenerNumElementos(entrada)>64)
                                ||(tipoCol.getSelectedIndex()==Tipo.ENUM && obtenerNumElementos(entrada)>65535)){             
                            throw new Exception();                 
                        } 
                    }else{
                        System.err.println("Regexp NO encontrada");
                        throw new Exception();
                    }
                }catch(Exception e){
                    mostrarMsgError(
                        "\nDebe incluir valores alfanuméricos entre comillas  "
                        + "\nsimples ('') y separarlos con coma ( , ). "
                        + "\nPara el tipo SET se admiten como máximo 64 valores distintos.  "
                        + "\nPara el tipo ENUM se admiten como máximo 65,535 valores distintos.  "
                    );
                    parametros.setText("''");
                }
                break;
            case Tipo.CHAR:
            case Tipo.BINARY:
                try{
                    if(!entrada.equals("")){
                        int tam = Integer.parseInt(entrada);
                        if(tam<0||tam>255){
                            throw new Exception();
                        }else{
                            info.setParametros(entrada);
                        }
                    }
                }catch(Exception e){
                    mostrarMsgError(
                        "\nPuede dejar el campo vacío o incluir"
                        + "\nun valor numérico ubicado entre 0 y 255.  "
                    );
                    parametros.setText("");
                }
                break;
            case Tipo.VARCHAR:
            case Tipo.VARBINARY:
                try{
                    int tam = Integer.parseInt(entrada);
                    if(tam<0||tam>65535){
                        throw new Exception();
                    }else{
                        info.setParametros(entrada);
                    }
                }catch(Exception e){
                    mostrarMsgError(
                        "\nPuede incluir un valor numérico ubicado entre  "
                        + "\n0 y 65,535."
                );
                    parametros.setText("45");
                }
                break;
        }
    }//GEN-LAST:event_parametrosFocusLost

    private void mostrarMsgError(String mensaje){
        JOptionPane.showMessageDialog(
            ventana, 
            "El valor dado contiene errores."
            + mensaje,
            "No se puede aceptar el valor", 
            JOptionPane.ERROR_MESSAGE
        );
    }
    
    private int obtenerNumElementos(String parametros){
        int numElementos = 0;
        Matcher mat = coma.matcher(parametros);
        while(mat.find()){
            numElementos++;
        }
        System.out.println("numero de elementos: "+(numElementos+1));
        return numElementos+1;
    }
    
    private void parametrosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_parametrosKeyReleased
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            parametros.transferFocus();
        }
    }//GEN-LAST:event_parametrosKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox checkAI;
    private javax.swing.JCheckBox checkNN;
    private javax.swing.JCheckBox checkPK;
    private javax.swing.JCheckBox checkUN;
    private javax.swing.JCheckBox checkUQ;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel nombreCol;
    private javax.swing.JTextField parametros;
    private javax.swing.JComboBox tipoCol;
    // End of variables declaration//GEN-END:variables
}