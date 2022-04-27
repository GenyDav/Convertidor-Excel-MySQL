package interfaz;

import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 * Clase que crea un panel que contiene los elementos gráficos necesarios para
 * modificar los atributos de una columna
 * @author Geny
 * @version 1.0
 */
public class PanelColumna extends javax.swing.JPanel {
    private InfoColumna info;       // objeto que contiene el tipo de dato de la columna, sus parámetros y modificadores
    private String expDecimal;      // expresión regular para identificar números decimales separados por coma
    private Pattern patronDecimal;  // patrón para identificar los parámetros del tipo de dato DECIMAL
    private String expEnumSet;      // expresión regular para identificar los elementos separados por coma para los tipos de dato ENUM y SET
    private Pattern patronEnumSet;  // patrón para identificar los parámetros del tipo de dato ENUM y SET
    private Pattern coma;           // patrón para identificar el número de comas en una expresión 
    private JDialog ventana;        // contenedor sobre el que se muestra el Panel
    private ButtonGroup grupo;      // grupo al que se va a añadir el checkBox del modificador AUTO_INCREMENT
    private boolean estadoCheckBox; // variable de control que indica si el checkBox del modificador AUTO_INCREMENT está seleccionado o no
    private boolean clic;           // variable que indica si el usuario ha hecho clic o no cuando el estado de checkAI cambia
    
    /**
     * Creates new form PanelColumna
     * @param col
     * @param ventana
     * @param grupo
     */
    public PanelColumna(InfoColumna col, ConfiguracionTipos ventana, ButtonGroup grupo) {
        initComponents();
        this.ventana = ventana;
        info = col;
        expDecimal = "(\\d+)\\s*,\\s*(\\d+)";   // uno o más digitos seguido de cero o varios espacios  
        patronDecimal = Pattern.compile(expDecimal);
        expEnumSet = "('(\\w+)'\\s*,\\s*)*'(\\w*)'";
        patronEnumSet = Pattern.compile(expEnumSet);
        coma = Pattern.compile(",");
        this.grupo = grupo;
        this.grupo.add(checkAI);
        
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
        checkUN = new javax.swing.JCheckBox();
        checkAI = new javax.swing.JCheckBox();

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 51, 51), 2, true));

        nombreCol.setBackground(new java.awt.Color(255, 255, 255));
        nombreCol.setForeground(new java.awt.Color(255, 255, 255));
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
        tipoCol.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tipoColFocusLost(evt);
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

        checkPK.setBackground(new java.awt.Color(51, 51, 51));
        checkPK.setForeground(new java.awt.Color(255, 204, 0));
        checkPK.setText("PK");
        checkPK.setToolTipText("Llave primaria");
        checkPK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/unchecked_PK2.png"))); // NOI18N
        checkPK.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/checked_PK.png"))); // NOI18N
        checkPK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkPKActionPerformed(evt);
            }
        });

        checkNN.setBackground(new java.awt.Color(102, 102, 102));
        checkNN.setForeground(new java.awt.Color(255, 255, 255));
        checkNN.setText("NN");
        checkNN.setToolTipText("No nulo");
        checkNN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/unchecked.png"))); // NOI18N
        checkNN.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/checked.png"))); // NOI18N
        checkNN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkNNActionPerformed(evt);
            }
        });

        checkUQ.setBackground(new java.awt.Color(102, 102, 102));
        checkUQ.setForeground(new java.awt.Color(255, 255, 255));
        checkUQ.setText("UQ");
        checkUQ.setToolTipText("Índice único");
        checkUQ.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/unchecked.png"))); // NOI18N
        checkUQ.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/checked.png"))); // NOI18N
        checkUQ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkUQActionPerformed(evt);
            }
        });

        checkUN.setBackground(new java.awt.Color(102, 102, 102));
        checkUN.setForeground(new java.awt.Color(255, 255, 255));
        checkUN.setText("UN");
        checkUN.setToolTipText("Sin signo");
        checkUN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/unchecked.png"))); // NOI18N
        checkUN.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/checked.png"))); // NOI18N
        checkUN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkUNActionPerformed(evt);
            }
        });

        checkAI.setBackground(new java.awt.Color(102, 102, 102));
        checkAI.setForeground(new java.awt.Color(255, 255, 255));
        checkAI.setText("AI");
        checkAI.setToolTipText("Auto Incremento");
        checkAI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/unchecked.png"))); // NOI18N
        checkAI.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/checked.png"))); // NOI18N
        checkAI.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkAutoIncStateChanged(evt);
            }
        });
        checkAI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkAutoIncActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(nombreCol, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(tipoCol, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(parametros, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(checkPK)
                .addGap(2, 2, 2)
                .addComponent(checkNN)
                .addGap(2, 2, 2)
                .addComponent(checkUQ)
                .addGap(2, 2, 2)
                .addComponent(checkUN)
                .addGap(2, 2, 2)
                .addComponent(checkAI)
                .addGap(6, 6, 6))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombreCol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(parametros)
                    .addComponent(checkPK)
                    .addComponent(checkNN)
                    .addComponent(checkUQ)
                    .addComponent(checkUN)
                    .addComponent(checkAI))
                .addGap(3, 3, 3))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(tipoCol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cargarDatos(){
        //System.out.println(info.getNombre()+": "+Tipo.TIPO[info.getTipo()]+"("+info.getParametros()+")");
        nombreCol.setText(info.getNombre());
        checkPK.setSelected(info.getPK());
        checkNN.setSelected(info.getNN());
        checkUQ.setSelected(info.getUQ());
        checkUN.setSelected(info.getUN());
        checkAI.setSelected(info.getAI());
        estadoCheckBox = info.getAI();
        clic = false;
        tipoCol.setSelectedIndex(info.getTipo());
        parametros.setText(info.getParametros());     
    }
    
    /* Checkbox de llave primaria */
    private void checkPKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkPKActionPerformed
        if(checkPK.isSelected()){
            info.setPK(true);
            info.setNN(true);
            checkNN.setSelected(true);
            switch(tipoCol.getSelectedIndex()){
                case Tipo.TINYINT:
                case Tipo.SMALLINT:
                case Tipo.MEDIUMINT:
                case Tipo.INT:
                case Tipo.BIGINT:
                    checkAI.setEnabled(true);
                    break;
            }
        }else{
            info.setPK(false);
            checkAI.setEnabled(false);
            if(checkAI.isSelected()){
                grupo.clearSelection();
            }
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

    private void comboTipoStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboTipoStateChanged
        if(evt.getStateChange()==ItemEvent.SELECTED){
            info.setTipo(tipoCol.getSelectedIndex());
            configCampoParametros();
            configModificadores();
        }
    }//GEN-LAST:event_comboTipoStateChanged

    private void configValoresCampo(String texto,boolean enabled,String toolTip){
        parametros.setText(texto);
        parametros.setEnabled(enabled);
        parametros.setToolTipText(toolTip);
    }
    
    private void configCampoParametros(){
        switch(tipoCol.getSelectedIndex()){
            case Tipo.TINYINT:
            case Tipo.SMALLINT:
            case Tipo.MEDIUMINT:
            case Tipo.INT:
            case Tipo.BIGINT:
                configValoresCampo("",true,"Ancho de visualización");
                break;              
            case Tipo.FLOAT:
            case Tipo.CHAR:
            case Tipo.BINARY:
            case Tipo.TEXT:
                configValoresCampo("",true,"Tamaño");
                break;
            case Tipo.VARCHAR:
            case Tipo.VARBINARY:
                configValoresCampo("45",true,"Tamaño");
                break;
            case Tipo.DECIMAL:
                configValoresCampo("",true,"Número total de dígitos, número de dígitos decimales");
                break;
            case Tipo.SET:
            case Tipo.ENUM:
                configValoresCampo("''",true,"Lista de elementos");
                break;
            case Tipo.DOUBLE: 
            case Tipo.DATE: 
            case Tipo.DATETIME:
            case Tipo.TIME:
            case Tipo.TIMESTAMP:
            case Tipo.YEAR:   
                configValoresCampo("",false,"");
                break;
        }
    }
    
    /*  
    Deshabilitar la opción UNSIGNED si el tipo de dato seleccionado no es numérico
    Deshabilitar la opción AUTO_INCREMENT si el tipo de dato seleccionado es distinto de algún INTEGER y el campo no es llave primaria
    */  
    private void configModificadores(){
        //System.out.println("Cambiando modificadores...");
        switch(tipoCol.getSelectedIndex()){
            case Tipo.TINYINT:
            case Tipo.SMALLINT:
            case Tipo.MEDIUMINT:
            case Tipo.INT:
            case Tipo.BIGINT:
                checkUN.setEnabled(true);
                if(info.getPK()){
                    checkAI.setEnabled(true);
                }else{ //?
                    checkAI.setEnabled(false);
                }
                break;
            case Tipo.FLOAT:
            case Tipo.DECIMAL:
            case Tipo.DOUBLE:
                checkUN.setEnabled(true);
                checkAI.setEnabled(false);
                if(checkAI.isSelected()){
                    grupo.clearSelection();
                }
                break;              
            default:
                checkUN.setEnabled(false);
                checkAI.setEnabled(false);
                if(checkAI.isSelected()){
                    grupo.clearSelection();
                }
                break;
        }
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
                        }
                    }else{
                        info.setParametros("");
                    }
                }catch(Exception e){
                    mostrarMsgError(
                        "\nPuede dejar el campo vacío o incluir"
                        + "\nun valor numérico ubicado entre 1 y 255.  "
                    );
                    parametros.setText(info.getParametros());
                    //info.setParametros("");
                }
                break;
            case Tipo.DECIMAL:               
                try{
                    if(!entrada.equals("")){
                        Matcher mat = patronDecimal.matcher(entrada);
                        int n1,n2;
                        if (mat.matches()) {
                            //System.out.println("Regexp encontrada");
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
                            //System.err.println("Regexp NO encontrada");
                            throw new Exception(); 
                        }
                    }else{
                        info.setParametros("");
                    }
                }catch(Exception e){
                    mostrarMsgError(
                        " Puede dejar el campo vacío o incluir dos valores  "
                        + "\nnuméricos separados por coma (M,D).  "
                        + "\nEl valor máximo aceptado para M es 65, el valor máximo aceptado para D es 30. "
                        + "\nM debe ser mayor o igual a D."
                    );
                    parametros.setText(info.getParametros());
                    //info.setParametros("");
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
                    }else{
                        info.setParametros("");
                    } 
                }catch(Exception e){
                    mostrarMsgError(
                        "\nPuede dejar el campo vacío o incluir"
                        + "\nun valor numérico ubicado entre 0 y 53.  "
                    );
                    parametros.setText(info.getParametros());
                    //info.setParametros("");
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
                    }else{
                        info.setParametros("");
                    } 
                }catch(Exception e){
                    mostrarMsgError(
                        "\nPuede dejar el campo vacío o incluir"
                        + "\nun valor numérico igual o mayor a cero.  "
                    );
                    parametros.setText(info.getParametros());
                    //info.setParametros("");
                }
                break;
            case Tipo.ENUM:
            case Tipo.SET:
                Matcher mat = patronEnumSet.matcher(entrada);
                try{
                    if(mat.matches()){
                        //System.out.println("Regexp encontrada"); 
                        info.setParametros(entrada);
                        // Verificar el número de elementos en la lista
                        if((tipoCol.getSelectedIndex()==Tipo.SET && obtenerNumElementos(entrada)>64)
                                ||(tipoCol.getSelectedIndex()==Tipo.ENUM && obtenerNumElementos(entrada)>65535)){             
                            throw new Exception();                 
                        } 
                    }else{
                        //System.err.println("Regexp NO encontrada");
                        throw new Exception();
                    }
                }catch(Exception e){
                    mostrarMsgError(
                        "\nDebe incluir valores alfanuméricos entre comillas  "
                        + "\nsimples ('') y separarlos con coma ( , ). "
                        + "\nPara el tipo SET se admiten como máximo 64 valores distintos.  "
                        + "\nPara el tipo ENUM se admiten como máximo 65,535 valores distintos.  "
                    );
                    //parametros.setText("''");
                    parametros.setText(info.getParametros());
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
                    }else{
                        info.setParametros("");
                    } 
                }catch(Exception e){
                    mostrarMsgError(
                        "\nPuede dejar el campo vacío o incluir"
                        + "\nun valor numérico ubicado entre 0 y 255.  "
                    );
                    parametros.setText(info.getParametros());
                    //info.setParametros("");
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
                    parametros.setText(info.getParametros());
                    //info.setParametros("45");
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
        //System.out.println("numero de elementos: "+(numElementos+1));
        return numElementos+1;
    }
    
    private void parametrosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_parametrosKeyReleased
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            parametros.transferFocus();
        }
    }//GEN-LAST:event_parametrosKeyReleased

    private void checkAutoIncStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_checkAutoIncStateChanged
        //System.out.println("AI cambió su estado en la columna "+ info.getNombre());
        if(!checkAI.isSelected()&&!clic){
            //System.out.println("Sin hacer clic");
            estadoCheckBox = false;
            info.setAI(false);
        }
        //info.setAI(estadoCheckBox);
        //System.out.println("info.getAI: "+info.getAI());
    }//GEN-LAST:event_checkAutoIncStateChanged

    private void checkAutoIncActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkAutoIncActionPerformed
        clic = true;
        //System.out.println("Clic en AutoIncrement");
        //System.out.println(checkAI.isSelected());
        if(!estadoCheckBox){
            //estadoAnterior = true;
        }else{  
            grupo.clearSelection();
            //estadoAnterior = false;
        }
        estadoCheckBox = !estadoCheckBox;
        info.setAI(estadoCheckBox);
        //System.out.println("estado: " + estadoCheckBox);
        clic = false;
        //System.out.println("--------------------");
    }//GEN-LAST:event_checkAutoIncActionPerformed

    private void tipoColFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tipoColFocusLost
        info.setParametros(parametros.getText());  
    }//GEN-LAST:event_tipoColFocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox checkAI;
    private javax.swing.JCheckBox checkNN;
    private javax.swing.JCheckBox checkPK;
    private javax.swing.JCheckBox checkUN;
    private javax.swing.JCheckBox checkUQ;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel nombreCol;
    private javax.swing.JTextField parametros;
    private javax.swing.JComboBox tipoCol;
    // End of variables declaration//GEN-END:variables
}