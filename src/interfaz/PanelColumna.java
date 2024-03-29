package interfaz;

import datos.InfoColumna;
import datos.Tipo;
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
 * @author Geny D.
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
    private boolean esCargaInicial; // variable utilizada para evitar que el campo de los parámetros se restablezca al seleccionar 
                                    // el tipo de dato cuando se carga la ventana
    
    /**
     * Crea una nueva forma PanelColumna
     * @param col objeto que contiene la información de la columna a mostrarse en el panel
     * @param ventana contenedor sobre el que se muestra el panel
     * @param grupo grupo al que se añade el checkbox correspondiente al modificador AUTO_INCREMENT
     */
    public PanelColumna(InfoColumna col, ConfiguracionTipos ventana, ButtonGroup grupo) {
        initComponents();
        esCargaInicial = true;
        this.ventana = ventana;
        info = col;
        expDecimal = "(\\d+)\\s*,\\s*(\\d+)";        // dos números enteros separados por coma
        patronDecimal = Pattern.compile(expDecimal);
        expEnumSet = "('(\\w+)'(\\s*),(\\s*))*'(\\w*)'"; // una o más palabras (letras, números y el caracter '_') entre comillas y separadas por comas
        patronEnumSet = Pattern.compile(expEnumSet);
        coma = Pattern.compile("'(\\w+)'");
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

    /**
     * Método que muestra la configuración de la columna en el panel en pantalla.
     * Muestra el nombre de la columna, su tipo de dato, los parametros y los 
     * modificadores aplicados en esa columna.
     */
    private void cargarDatos(){
        nombreCol.setText(info.getNombre());
        checkPK.setSelected(info.getPK());
        checkNN.setSelected(info.getNN());
        checkUQ.setSelected(info.getUQ());
        checkUN.setSelected(info.getUN());
        checkAI.setSelected(info.getAI());
        estadoCheckBox = info.getAI();
        clic = false;
        tipoCol.setSelectedIndex(info.getTipo());
        esCargaInicial = false;
        parametros.setText(info.getParametros());     
    }
    
    /**
     * Método que cambia el valor del modificador PRIMARY KEY en la estructura de
     * datos al hacer clic sobre su checkbox correspondiente. Cuando se selecciona
     * el jCheckBox se selecciona tambien el check del modificador NOT NULL y se 
     * modifica la estructura de datos con la información de la columna. 
     * Si al seleccionar una columna como llave primaria su tipo de dato corresponde
     * con el de algún tipo de entero (TINYINT, SMALLINT, MEDIUMINT, INT o BIGINT),
     * se activará el checkbox del modificador AUTO_INCREMENT.
     * Cuando se deselecciona el jCheckBox de llave primaria se deselecciona tambien 
     * el checkbox de auto incremento y se deshabilita. La estructura de datos 
     * con la información de la columna se actualiza.
     * @param evt evento de selección sobre el ckeckBox para el atributo PRIMARY KEY
     */
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
                grupo.clearSelection(); // la información se actualiza de forma al cambiar el estado del jCheckBox
            }
        }
    }//GEN-LAST:event_checkPKActionPerformed

    /**
     * Método que cambia el valor del modificador NOT NULL en la estructura de
     * datos al hacer clic sobre su checkbox correspondiente.
     * @param evt evento de selección sobre el ckeckBox para el atributo NOT NULL
     */
    private void checkNNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkNNActionPerformed
        info.setNN(checkNN.isSelected());
    }//GEN-LAST:event_checkNNActionPerformed
    
    /**
     * Método que cambia el valor del modificador UNIQUE en la estructura de
     * datos al hacer clic sobre su checkbox correspondiente.
     * @param evt evento de selección sobre el ckeckBox para el atributo UNIQUE
     */
    private void checkUQActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkUQActionPerformed
        info.setUQ(checkUQ.isSelected());
    }//GEN-LAST:event_checkUQActionPerformed
    
    /**
     * Método que cambia el valor del modificador USIGNED en la estructura de
     * datos al hacer clic sobre su checkbox correspondiente.
     * @param evt evento de selección sobre el ckeckBox para el atributo UNSIGNED
     */
    private void checkUNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkUNActionPerformed
        info.setUN(checkUN.isSelected());
    }//GEN-LAST:event_checkUNActionPerformed

    /**
     * Establece los modificadores disponibles y los parámetros aceptados según 
     * el tipo de dato seleccionado.
     * @param evt evento de cambio de estado sobre el jComboBox que contiene los
     * tipos de datos que pueden ser utilizados
     */
    private void comboTipoStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboTipoStateChanged
        if(evt.getStateChange()==ItemEvent.SELECTED){
            info.setTipo(tipoCol.getSelectedIndex());
            configCampoParametros();
            configModificadores();
        }
    }//GEN-LAST:event_comboTipoStateChanged
 
    /**
     * Define las propiedades del campo dedicado a contener los parámetros
     * según el tipo de dato seleccionado.
     */
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
    
    /**
     * Modifica las propiedades del campo dedicado a contener los parámetros
     * de la columna según el tipo de dato seleccionado.
     * @param texto texto dentro del campo
     * @param enabled indica si el campo esta habilitado o deshabilitado
     * @param toolTip descripción emergente del tipo de datos que se pueden escribir
     */
    private void configValoresCampo(String texto,boolean enabled,String toolTip){
        parametros.setText(texto);
        if(!esCargaInicial)
            info.setParametros(parametros.getText());
        parametros.setEnabled(enabled);
        parametros.setToolTipText(toolTip);
    }
      
    /**
     * Estableces los modificadores disponibles para una columna según el tipo
     * de dato seleccionado.
     * Al seleccionar un tipo de dato no numérico se deshabilita el jcheckbox
     * que corresponde al modificador UNSIGNED.
     * Al seleccionar un tipo de dato distinto a TINYINT, SMALLINT, MEDIUMINT,
     * INT y BIGINT se deshabilita el modificador AUTO_INCREMENT.
     * AUTO_INCREMENT también se deshabilita si el tipo de dato seleccionado es
     * entero pero la columna no está marcada como llave primaria.
     */
    private void configModificadores(){
        switch(tipoCol.getSelectedIndex()){
            case Tipo.TINYINT:
            case Tipo.SMALLINT:
            case Tipo.MEDIUMINT:
            case Tipo.INT:
            case Tipo.BIGINT:
                checkUN.setEnabled(true);
                if(info.getPK())
                    checkAI.setEnabled(true);
                else
                    checkAI.setEnabled(false);
                break;
            case Tipo.FLOAT:
            case Tipo.DECIMAL:
            case Tipo.DOUBLE:
                checkUN.setEnabled(true);
                checkAI.setEnabled(false);
                // Si antes de seleccionar un tipo de sato FLOAT, DECIMAL o 
                // DOUBLE, la opción de auto_increment estaba seleccionada,
                // entonces se desmarca
                if(checkAI.isSelected())
                    grupo.clearSelection();               
                break;              
            default:
                checkUN.setEnabled(false);
                checkAI.setEnabled(false);
                if(checkAI.isSelected())
                    grupo.clearSelection();
                break;
        }
    }
    
    /**
     * Método que evalúa los parámetros ingresados por el usuario para una columna,
     * mostrando un mensaje de error si los parámetros no son válidos para el
     * tipo de dato de la columna. Si son válidos se actualiza la estructura de
     * datos que contiene la información de las columnas del archivo.
     * @param evt Evento lanzado cuando el campo que contiene los parámetros de
     * una columna pierde el foco.
     */
    private void parametrosFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_parametrosFocusLost
        String entrada = parametros.getText();
        switch(tipoCol.getSelectedIndex()){
            case Tipo.TINYINT:
            case Tipo.SMALLINT:
            case Tipo.MEDIUMINT:
            case Tipo.INT:
            case Tipo.BIGINT:
                /* Los tipos de datos enteros permiten que se deje vacío el campo o 
                permiten que el usuario ingrese como paramétro un valor entero 
                entre 1 y 255, correspondientes al ancho de visualización. */
                try{
                    if(!entrada.equals("")){
                        int tam = Integer.parseInt(entrada);
                        if(tam<1||tam>255)
                            throw new Exception();
                        else
                            info.setParametros(entrada);
                    }else{
                        info.setParametros("");
                    }
                }catch(Exception e){
                    mostrarMsgError(
                        "\nPuede dejar el campo vacío o incluir"
                        + "\nun valor numérico ubicado entre 1 y 255.  "
                    );
                    // El campo se reinicia al último valor aceptado
                    parametros.setText(info.getParametros());
                }
                break;
            case Tipo.DECIMAL:         
                /* El tipo de dato decimal acepta dos valores numéricos separados por
                una coma(,). El primer número indica el número de dígitos en total
                que pueden tener los valores de la columna (máximo 65). El segundo 
                número representa el número de dígitos en la parte decimal del valor
                (máximo 30). El valor del primer número debe ser mayor al valor del
                segundo número del parámetro. */
                try{
                    if(!entrada.equals("")){
                        Matcher mat = patronDecimal.matcher(entrada);
                        int n1,n2;
                        if(mat.matches()){
                            // Los grupos se determinan por el uso de paréntesis en la definición
                            // de la expresión regular
                            n1 = Integer.parseInt(mat.group(1)); // obtener la primer coincidencia del patrón
                            n2 = Integer.parseInt(mat.group(2)); // obtener la segunda coincidencia del patrón    
                            if(n1>65||n2>30||n1<n2)
                                throw new Exception(); 
                            else
                                info.setParametros(entrada);
                        }else{
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
                }
                break;
            case Tipo.FLOAT:
                /* El tipo de dato float permite que el campo para los parámetros esté vacio
                o contenga un número entero entre 0 y 53, indicando el tamaño de
                almacenamiento. Una precisión de 0 a 23 da como resultado una columna 
                FLOAT de precisión simple de 4 bytes. 
                Una precisión de 24 a 53 da como resultado una columna DOUBLE de 
                precisión doble de 8 bytes. */
                try{
                    if(!entrada.equals("")){
                        int tam = Integer.parseInt(entrada);
                        if(tam<0||tam>53)
                            throw new Exception();
                        else
                            info.setParametros(entrada);
                    }else{
                        info.setParametros("");
                    } 
                }catch(Exception e){
                    mostrarMsgError(
                        "\nPuede dejar el campo vacío o incluir"
                        + "\nun valor numérico ubicado entre 0 y 53.  "
                    );
                    parametros.setText(info.getParametros());
                }
                break;
            case Tipo.TEXT:
                /* El tipo de dato text puede aceptar un número entero positivo
                que indica la longitud máxima de los valores que pueden contener.*/
                try{
                    if(!entrada.equals("")){
                        int tam = Integer.parseInt(entrada);
                        if(tam<0)
                            throw new Exception();
                        else
                            info.setParametros(entrada);
                    }else{
                        info.setParametros("");
                    } 
                }catch(Exception e){
                    mostrarMsgError(
                        "\nPuede dejar el campo vacío o incluir"
                        + "\nun valor numérico igual o mayor a cero.  "
                    );
                    parametros.setText(info.getParametros());
                }
                break;
            case Tipo.ENUM:
            case Tipo.SET:
                /* Los tipos de datos set y enum aceptan como parámetros una lista
                de elementos separados por coma. Cada elemento debe ir entre 
                comillas simples (''). La diferencia entre ambos tipos de datos 
                es que el número máximo de elementos que se pueden definir para 
                el tipo set es 64 mientras que para enum el número máximo de 
                elementos en la lista puede ser hasta 65,535. */
                Matcher mat = patronEnumSet.matcher(entrada);
                try{
                    if(mat.matches()){
                        // Verificar el número de elementos en la lista
                        if((tipoCol.getSelectedIndex()==Tipo.SET && obtenerNumElementos(entrada)>64)
                            ||(tipoCol.getSelectedIndex()==Tipo.ENUM && obtenerNumElementos(entrada)>65535)){             
                            throw new Exception();                 
                        }else{
                            info.setParametros(entrada);
                        } 
                    }else{
                        throw new Exception();
                    }
                }catch(Exception e){
                    mostrarMsgError(
                        "\nDebe incluir valores alfanuméricos entre comillas  "
                        + "\nsimples ('') y separarlos con coma ( , ). "
                        + "\nPara el tipo SET se admiten como máximo 64 valores distintos.  "
                        + "\nPara el tipo ENUM se admiten como máximo 65,535 valores distintos.  "
                    );
                    parametros.setText(info.getParametros());
                }
                break;
            case Tipo.CHAR:
            case Tipo.BINARY:
                /* Los tipos de datos char y binary permiten que el usuario escriba
                como parámetro un valor entero ubicado entre 0 y 255, indicando el
                número de caracteres (para el tipo char) o bytes (binary) que se 
                pueden almacenar en un dato de la columna. */
                try{
                    if(!entrada.equals("")){
                        int tam = Integer.parseInt(entrada);
                        if(tam<0||tam>255)
                            throw new Exception();
                        else
                            info.setParametros(entrada);
                    }else{
                        info.setParametros("");
                    } 
                }catch(Exception e){
                    mostrarMsgError(
                        "\nPuede dejar el campo vacío o incluir"
                        + "\nun valor numérico ubicado entre 0 y 255.  "
                    );
                    parametros.setText(info.getParametros());
                }
                break;
            case Tipo.VARCHAR:
            case Tipo.VARBINARY:
                /* Los tipos de datos char y binary permiten que el usuario escriba
                como parámetro un valor entero ubicado entre 0 y 65,535, indicando 
                el número de caracteres (para el tipo char) o bytes (binary) que 
                se pueden almacenar como máximo en un dato de la columna.
                Estos tipos de datos no permiten que el campo de parámetros se
                encuentre vacío. */
                try{
                    int tam = Integer.parseInt(entrada);
                    if(tam<0||tam>65535)
                        throw new Exception();
                    else
                        info.setParametros(entrada);
                }catch(Exception e){
                    mostrarMsgError(
                        "\nPuede incluir un valor numérico ubicado entre  "
                        + "\n0 y 65,535."
                    );
                    parametros.setText(info.getParametros());
                }
                break;
        } 
    }//GEN-LAST:event_parametrosFocusLost

    /**
     * Método que muestra una cuadro de diálogo con una descripción del error 
     * ocurrido cuando el usuario intenta definir los parámetros de una columna.
     * @param mensaje Descripción del error que ocurre al intentar escribir los
     * parámetros de una columna según su tipo de dato.
     */
    private void mostrarMsgError(String mensaje){
        JOptionPane.showMessageDialog(
            ventana, 
            "El valor dado contiene errores."
            + mensaje,
            "No se puede aceptar el valor", 
            JOptionPane.ERROR_MESSAGE
        );
    }
    
    /**
     * Método que devuelve el número de elementos que el usuario escribió en el 
     * campo para parámetros cuando el tipo de dato seleccionado es SET o ENUM.
     * @param parametros lista de elementos que el usuario ingresó en el campo.
     * @return número de elementos en la lista
     */
    private int obtenerNumElementos(String parametros){
        int numElementos = 0;
        Matcher mat = coma.matcher(parametros);
        while(mat.find()){
            //System.out.println(mat.group()); // mostrar la coincidencia actual
            numElementos++;
        }
        return numElementos;
    }
    
    /**
     * Método utilizado para dar por terminada la inserción de parámetros en el
     * campo de texto cuando el usuario presiona la tecla ENTER. El campo pierde
     * el foco y se realizar la comprobación 
     * @param evt evento lanzado cuando se presiona una tecla mientras el campo
     * para los parámetros de la columna tiene el foco.
     */
    private void parametrosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_parametrosKeyReleased
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            parametros.transferFocus();
        }
    }//GEN-LAST:event_parametrosKeyReleased

    /**
     * Método que actualiza la estructura de datos con la información de la
     * columna cuando la propiedad auto_increment se modifica de forma indirecta
     * por un cambio en otra propiedad (y no debido a que el usuario ha hecho 
     * clic sobre su checkBox).
     * @param evt evento lanzado cuando el checkBox del modificador auto_increment
     * modifica su estado.
     */
    private void checkAutoIncStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_checkAutoIncStateChanged
        if(!checkAI.isSelected()&&!clic){
            estadoCheckBox = false;
            info.setAI(false);
        }
    }//GEN-LAST:event_checkAutoIncStateChanged

    /**
     * Método que permite actualizar la estructura de datos del archivo cuando 
     * se selecciona el modificador auto_increment.
     * @param evt evento lanzado al hacer clic en el checkBox del modificador auto_increment 
     */
    private void checkAutoIncActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkAutoIncActionPerformed
        // Como el checkbox se añadió a un grupo, al hacer clic por primera vez
        // en él queda seleccionado, pero al vover a hacer clic no se deselecciona.
        // El único modo de desmarcalo es selccionando otro elemento del grupo o 
        // limpiando la selección. Por eso hay que modificar el comportamiento de
        // ese checkbox:
        clic = true;                        // indicar que el usuario ha hecho clic en el checkbox
        if(estadoCheckBox)
            grupo.clearSelection();         // desmarcar el checkbox seleccionado
        estadoCheckBox = !estadoCheckBox;   // estadoCheckbox es sustituto del valor obtenido de .isSelected()
        info.setAI(estadoCheckBox);
        clic = false; 
    }//GEN-LAST:event_checkAutoIncActionPerformed

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