package interfaz;

import datos.ElementoLista;
import lectura_escritura.Conexion;
import java.awt.CardLayout;
import java.awt.Color;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.SwingWorker.StateValue;
import javax.swing.UIManager;

/**
 * Clase principal que crea la ventana en donde se ejecuta el programa.
 * @author Geny
 * @version 2.0
 */
public class Principal extends javax.swing.JFrame {
    private CardLayout cardLayout;
    private String servidor;        // dirección o nombre del servidor de bases de datos
    private String usuario;         // nombre del usuario
    private String clave;           // contraseña para acceder al servidor
    private Conexion conn;          // encargado de realizar la conexión y consultas a la base de datos
    private String mensaje;         // mensaje de la pantalla de inicio
    private int modo;               // tipo de panel que se está mostrando (importación o exportación)
    private final int EXP;
    private final int IMP; 
    private PanelExport panelExp;   // interfaz para crear archivos de Excel
    private PanelImport panelImp;   // interfaz para crear bases de datos
    
    /**
     * Crea el panel y las distintas interfaces del programa.
     */
    public Principal() {
        // Configurar el color de la barra de progreso
        UIManager.put("ProgressBar.selectionForeground", Color.white);
        UIManager.put("ProgressBar.foreground", new Color(2,97,140));
        initComponents(); 
        setIconImage(new ImageIcon(getClass().getResource("/imagenes/icono_frame.png")).getImage());
        setResizable(false);
        setTitle("Convertidor MySQL/Excel");
        setSize(1000,600);
        setLocationRelativeTo(null);
        
        cardLayout = (CardLayout)contenedor.getLayout();
        servidor = "";
        usuario = "";
        clave = "";
        mensaje = "";
  
        EXP = 1;
        IMP = 2;
        modo = EXP;
        panelExp = new PanelExport(this,contenedor);
        panelImp = new PanelImport(this,contenedor);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        contenedor = new javax.swing.JPanel();
        panelLogIn = new javax.swing.JPanel();
        panelSesion = new javax.swing.JPanel();
        labelUsuario = new javax.swing.JLabel();
        labelServidor = new javax.swing.JLabel();
        labelClave = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        txtServidor = new javax.swing.JTextField();
        txtClave = new javax.swing.JPasswordField();
        btnConectar = new javax.swing.JButton();
        tituloLogIn = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        msj = new javax.swing.JTextArea();
        imagenInicio = new javax.swing.JLabel();
        panelSecundario = new javax.swing.JPanel();
        menuLateral = new javax.swing.JPanel();
        btnModoExport = new javax.swing.JButton();
        btnModoImport = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        panelContenido = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                cerrarVentana(evt);
            }
        });

        contenedor.setLayout(new java.awt.CardLayout());

        panelLogIn.setBackground(new java.awt.Color(255, 255, 255));
        panelLogIn.setForeground(new java.awt.Color(102, 102, 102));

        panelSesion.setBackground(new java.awt.Color(51, 51, 51));
        panelSesion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        panelSesion.setForeground(new java.awt.Color(255, 255, 255));
        panelSesion.setMaximumSize(new java.awt.Dimension(265, 364));
        panelSesion.setName(""); // NOI18N

        labelUsuario.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        labelUsuario.setForeground(new java.awt.Color(255, 255, 255));
        labelUsuario.setText("Nombre de usuario");

        labelServidor.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        labelServidor.setForeground(new java.awt.Color(255, 255, 255));
        labelServidor.setText("Dirección del servidor");

        labelClave.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        labelClave.setForeground(new java.awt.Color(255, 255, 255));
        labelClave.setText("Contraseña");

        txtUsuario.setAutoscrolls(false);
        txtUsuario.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        txtUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        txtServidor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));

        txtClave.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));

        btnConectar.setText("Conectar");
        btnConectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConectarActionPerformed(evt);
            }
        });

        tituloLogIn.setFont(new java.awt.Font("Dialog", 1, 17)); // NOI18N
        tituloLogIn.setForeground(new java.awt.Color(255, 255, 255));
        tituloLogIn.setText("Datos de conexión");

        javax.swing.GroupLayout panelSesionLayout = new javax.swing.GroupLayout(panelSesion);
        panelSesion.setLayout(panelSesionLayout);
        panelSesionLayout.setHorizontalGroup(
            panelSesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSesionLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(panelSesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(labelClave, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelServidor)
                    .addComponent(btnConectar, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                    .addComponent(txtClave)
                    .addComponent(labelUsuario)
                    .addComponent(txtServidor)
                    .addComponent(txtUsuario)
                    .addComponent(tituloLogIn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        panelSesionLayout.setVerticalGroup(
            panelSesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSesionLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(tituloLogIn)
                .addGap(18, 18, 18)
                .addComponent(labelServidor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtServidor, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelClave)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtClave, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 375, Short.MAX_VALUE)
                .addComponent(btnConectar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        jScrollPane3.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        msj.setEditable(false);
        msj.setColumns(20);
        msj.setLineWrap(true);
        msj.setRows(4);
        msj.setWrapStyleWord(true);
        msj.setFocusable(false);
        msj.setRequestFocusEnabled(false);
        jScrollPane3.setViewportView(msj);

        imagenInicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/panel.png"))); // NOI18N

        javax.swing.GroupLayout panelLogInLayout = new javax.swing.GroupLayout(panelLogIn);
        panelLogIn.setLayout(panelLogInLayout);
        panelLogInLayout.setHorizontalGroup(
            panelLogInLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLogInLayout.createSequentialGroup()
                .addComponent(panelSesion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(panelLogInLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLogInLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 732, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(panelLogInLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(imagenInicio))))
        );
        panelLogInLayout.setVerticalGroup(
            panelLogInLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelSesion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLogInLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(imagenInicio)
                .addGap(47, 47, 47)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        contenedor.add(panelLogIn, "panelInicio");

        panelSecundario.setBackground(new java.awt.Color(152, 152, 152));

        menuLateral.setBackground(new java.awt.Color(51, 51, 51));
        menuLateral.setForeground(new java.awt.Color(255, 255, 255));

        btnModoExport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btn1.png"))); // NOI18N
        btnModoExport.setToolTipText("Convertir una base de datos en un archivo de Excel");
        btnModoExport.setBorder(null);
        btnModoExport.setBorderPainted(false);
        btnModoExport.setContentAreaFilled(false);
        btnModoExport.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnModoExport.setDefaultCapable(false);
        btnModoExport.setFocusPainted(false);
        btnModoExport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnExportarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnExportarMouseExited(evt);
            }
        });
        btnModoExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModoExportActionPerformed(evt);
            }
        });

        btnModoImport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/btn2.png"))); // NOI18N
        btnModoImport.setToolTipText("Crear una base de datos desde un archivo de Excel");
        btnModoImport.setBorder(null);
        btnModoImport.setBorderPainted(false);
        btnModoImport.setContentAreaFilled(false);
        btnModoImport.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnModoImport.setDefaultCapable(false);
        btnModoImport.setFocusPainted(false);
        btnModoImport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnImportMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnImportMouseExited(evt);
            }
        });
        btnModoImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModoImportActionPerformed(evt);
            }
        });

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/salir1.png"))); // NOI18N
        btnSalir.setToolTipText("Cerrar la conexión");
        btnSalir.setBorderPainted(false);
        btnSalir.setContentAreaFilled(false);
        btnSalir.setDefaultCapable(false);
        btnSalir.setFocusPainted(false);
        btnSalir.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/salir3.png"))); // NOI18N
        btnSalir.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/salir2.png"))); // NOI18N
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout menuLateralLayout = new javax.swing.GroupLayout(menuLateral);
        menuLateral.setLayout(menuLateralLayout);
        menuLateralLayout.setHorizontalGroup(
            menuLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuLateralLayout.createSequentialGroup()
                .addGap(0, 6, Short.MAX_VALUE)
                .addGroup(menuLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnModoImport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnModoExport, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        menuLateralLayout.setVerticalGroup(
            menuLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuLateralLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(btnModoExport)
                .addGap(0, 0, 0)
                .addComponent(btnModoImport)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSalir)
                .addGap(5, 5, 5))
        );

        panelContenido.setBackground(new java.awt.Color(255, 255, 255));
        panelContenido.setMaximumSize(new java.awt.Dimension(944, 600));
        panelContenido.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout panelSecundarioLayout = new javax.swing.GroupLayout(panelSecundario);
        panelSecundario.setLayout(panelSecundarioLayout);
        panelSecundarioLayout.setHorizontalGroup(
            panelSecundarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSecundarioLayout.createSequentialGroup()
                .addComponent(menuLateral, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelContenido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelSecundarioLayout.setVerticalGroup(
            panelSecundarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelContenido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(menuLateral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        contenedor.add(panelSecundario, "pantallaSecundaria");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contenedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contenedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Método encargado de intentar realizar la conexión con el servidor de BD
     * con los datos proporcionados por el usuario. 
     * Si la conexión se realiza de forma exitosa, el programa muestra la 
     * pantalla que permite exportar una base de datos en un archivo Excel. 
     * Si la conexión no se puede realizar, se muestra un mensaje de error en la
     * pantalla inicial.
     * param evt Evento generado al presionar el botón 'Conectar'. 
     */
    private void btnConectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConectarActionPerformed
        mensaje = "Estableciendo la conexión con el servidor...";
        msj.setText(mensaje);
        btnConectar.setEnabled(false);
        servidor = txtServidor.getText();
        usuario = txtUsuario.getText();
        clave = txtClave.getText();
        new Thread(){
            @Override
            public void run(){
                try{
                    conn = new Conexion(servidor,usuario,clave); // Conectar con el servidor
                    panelImp.definirConexion(conn);
                    panelExp.cargarListaDeBases(conn);
                    panelContenido.removeAll();
                    panelContenido.add(panelExp);
                    panelContenido.revalidate();
                    panelContenido.repaint();
                    cardLayout.show(contenedor, "pantallaSecundaria"); // cambiar a la pantalla de exportación
                }catch(SQLException sqle){
                    mensaje += "\nFalló el intento de conexión. "
                    + "\nError MySQL " + sqle.getErrorCode() + ": " + sqle.getMessage() + ".";
                    if(sqle.getErrorCode()==1045){
                        mensaje += "\nDatos de conexión incorrectos, verifique e intente de nuevo.";
                    }
                }catch(ClassNotFoundException cnf){
                    //cnf.printStackTrace();
                    mensaje += "\nFalló el intento de conexión."
                    + "\nNo se pudo encontar la librería mysql-conector-java";
                }finally{
                    msj.setText(mensaje);
                    btnConectar.setEnabled(true);
                }
            }
        }.start();
    }//GEN-LAST:event_btnConectarActionPerformed
  
    /**
     * Metodo que permite terminar la ejecución del programa al presionar el 
     * botón para cerrar la ventana (X). Verifica si existen procesos de importación
     * o exportación ejecutándose antes de terminarlo.
     * @param evt Evento lanzado al presionar el botón que cierra la ventana del
     * programa.
     */
    private void cerrarVentana(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_cerrarVentana
        Object []opciones ={"Aceptar","Cancelar"};
        if((panelExp.consultarEstadoProceso()==StateValue.STARTED)||(panelImp.consultarEstadoProceso()==StateValue.STARTED)){    
            int eleccion = JOptionPane.showOptionDialog(
                this,
                "Hay procesos ejecutándose, ¿desea cerrar el programa?  ",
                "Confirmar cierre",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,null,opciones,"Aceptar"
            );
            if (eleccion == JOptionPane.YES_OPTION){
                conn.terminarConexion();
                panelExp.cancelarProceso();
                panelImp.cancelarProceso();
                System.exit(0);
            }
        }else{     
            conn.terminarConexion();
            System.exit(0);
        }
    }//GEN-LAST:event_cerrarVentana
    
    /**
     * Permite que el usuario cierre la conexión con el servidor despues de 
     * verificar que no existen procesos ejecutándose. Si los hay, se pide que
     * el usuario confirme que estos sean interrumpidos.
     * @param evt Evento lanzado al presionar el botón para terminar la sesión.
     */
    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        Object []opciones = {"Aceptar","Cancelar"};
        new SwingWorker<Void,Void>(){
            @Override
            protected Void doInBackground() throws Exception { 
                if((panelExp.consultarEstadoProceso()==StateValue.STARTED)||(panelImp.consultarEstadoProceso()==StateValue.STARTED)){
                    int eleccion = JOptionPane.showOptionDialog(
                        contenedor,
                        "Hay procesos ejecutándose, ¿desea cerrar la conexión?  ",
                        "Confirmar cierre",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,null,opciones,"Aceptar"
                    );
                    if (eleccion == JOptionPane.YES_OPTION){ 
                        panelExp.cancelarProceso();
                        panelImp.cancelarProceso();
                        terminarConexion();             
                    }
                }else{
                    terminarConexion();
                }
                return null;
            }
        }.execute();  
    }//GEN-LAST:event_btnSalirActionPerformed
    
    /**
     * Termina la sesión del usuario en el programa.
     */
    private void terminarConexion(){
        reiniciarCamposLogIn();
        conn.terminarConexion();
        modo = EXP;
        btnModoExport.setIcon(new ImageIcon(getClass().getResource("/imagenes/btn1.png")));
        btnModoImport.setIcon(new ImageIcon(getClass().getResource("/imagenes/btn2.png")));
        panelExp.reiniciarElementosExp();
        panelImp.reiniciarElementosImp(true);
        cardLayout.show(contenedor, "panelInicio"); // regresar a la pantalla de inicio de sesión  
    }

    /**
     * Limpia los valores de los campos de la pantalla de inicio de sesión.
     */
    public void reiniciarCamposLogIn(){
        txtServidor.setText("");
        txtUsuario.setText("");
        txtClave.setText("");
        mensaje = "Conexión terminada.";
        msj.setText(mensaje);
        btnConectar.setEnabled(true);
    }
    
    /**
     * Método que muestra la interfaz con los elementos para crear un archivo
     * Excel a partir de una base de datos.
     * @param evt Evento lanzado al presionar el botón que muestra la interfaz
     * para exportar bases de datos.
     */
    private void btnModoExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModoExportActionPerformed
        modo = EXP;
        panelSecundario.setBackground(new Color(153,153,153));
        panelContenido.removeAll();
        panelContenido.add(panelExp);
        panelContenido.revalidate();
        panelContenido.repaint();
    }//GEN-LAST:event_btnModoExportActionPerformed

    /**
     * Método que muestra la interfaz con los elementos para crear una base de
     * datos a partir de un archivo de Excel.
     * @param evt Evento lanzado al presionar el botón que muestra la interfaz
     * para importar bases de datos.
     */
    private void btnModoImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModoImportActionPerformed
        modo = IMP;
        panelSecundario.setBackground(new Color(104,104,104));
        panelContenido.removeAll();
        panelContenido.add(panelImp);
        panelContenido.revalidate();
        panelContenido.repaint();
    }//GEN-LAST:event_btnModoImportActionPerformed

    /**
     * Método que configura la apariencia de los botones que cambian la interfaz
     * cuando el usuario pasa el mouse sobre el botón del modo Importar.
     * @param evt Evento lanzado cuando el puntero del mouse entra al área del 
     * botón que cambia el modo de programa a Importar.
     */
    private void btnImportMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnImportMouseEntered
        btnModoImport.setIcon(new ImageIcon(getClass().getResource("/imagenes/btn2_1.png")));
        btnModoExport.setIcon(new ImageIcon(getClass().getResource("/imagenes/btn1_1.png")));
    }//GEN-LAST:event_btnImportMouseEntered

    /**
     * Método que configura la apariencia de los botones que cambian la interfaz
     * cuando el usuario hace que el mouse salga del área del botón del modo Importar.
     * @param evt Evento lanzado cuando el puntero del mouse sale del área del
     * botón que cambia el modo del programa a Importar
     */
    private void btnImportMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnImportMouseExited
        if(modo==EXP){
            btnModoExport.setIcon(new ImageIcon(getClass().getResource("/imagenes/btn1.png")));
            btnModoImport.setIcon(new ImageIcon(getClass().getResource("/imagenes/btn2.png")));
        }
    }//GEN-LAST:event_btnImportMouseExited

    /**
     * Método que configura la apariencia de los botones que cambian la interfaz
     * cuando el usuario pasa el mouse sobre el botón del modo Exportar.
     * @param evt Evento lanzado cuando el puntero del mouse entral al área del
     * botón que cambia el modo del programa a Exportar.
     */
    private void btnExportarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExportarMouseEntered
        btnModoExport.setIcon(new ImageIcon(getClass().getResource("/imagenes/btn1.png")));
        btnModoImport.setIcon(new ImageIcon(getClass().getResource("/imagenes/btn2.png")));
    }//GEN-LAST:event_btnExportarMouseEntered

    /**
     * Método que configura la apariencia de los botones que cambian la interfaz
     * cuando el usuario hace que el mouse salga del área del botón del modo Importar.
     * @param evt Evento lanzado cuando el puntero del mouse sale del área del 
     * botón que cambia el modo del programa a Importar.
     */
    private void btnExportarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExportarMouseExited
        if(modo==IMP){
            btnModoExport.setIcon(new ImageIcon(getClass().getResource("/imagenes/btn1_1.png")));
            btnModoImport.setIcon(new ImageIcon(getClass().getResource("/imagenes/btn2_1.png")));
        }
    }//GEN-LAST:event_btnExportarMouseExited
    
    /**
     * Muestra por consola el estado de selección de cada elemento en una lista
     * (de tablas o de hojas.
     * @param <T> Clases que heredan de ElementoLista.
     * @param lista Lista de elementos a mostrar.
     */
    public static <T extends ElementoLista> void mostrarListaElementos(ArrayList<T> lista){
        for(int i=0;i<lista.size();i++){
            System.out.print("["+lista.get(i).estaSeleccionado()+"]");
        }
        System.out.println();
    }
    
    /**
     * Método que elimina todos los elementos que están en la lista de exportación/importación.
     * @param <T> Objetos de la clase ElementoLista o que hereden de ella.
     * @param panel Panel sobre el que se va a mostrar la intefaz de exportación o de importación.
     * @param elemento Tipo de elemento que va a ser eliminado (tabla u hoja).
     * @param modeloLista Modelo del jList donde están las tablas seleccionadas.
     * @param listaAux Estructura de datos con la información de las tablas.
     * @return Valor entero que indica si el usuario ha confirmado o cancelado
     * la eliminación de los elementos de la lista.
     */
    public static <T extends ElementoLista> int limpiarSeleccion(JPanel panel,String elemento,DefaultListModel modeloLista,ArrayList<T> listaAux){
        int resp = JOptionPane.showConfirmDialog(
            panel, 
            "Todas las "+elemento+" en la lista se \nborrarán. ¿Continuar?",
            "¿Borrar todo?", 
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        if(resp == JOptionPane.OK_OPTION){                
            ElementoLista elem;
            for(int i=modeloLista.getSize()-1;i>=0;i--){
                elem = (ElementoLista)modeloLista.getElementAt(i);
                // desmarca como seleccionado el elemento en la estructura de datos
                listaAux.get(elem.getPosicion()).setSeleccionado(false);
            }
            //mostrarListaElementos(listaAux);
            modeloLista.clear();
        }
        return resp;
    }
    
    /**
     * Método que permite eliminar los elementos seleccionados en la lista de 
     * exportación.
     * @param <T> Objetos de la clase ElementoLista o que hereden de ella.
     * @param seleccion JList en donde están las tablas seleccionadas.
     * @param modeloLista Modelo del jList donde están las tablas seleccionadas.
     * @param lista Estructura de datos con la información de las tablas.
     */
    public static <T extends ElementoLista> void borrarElemento(JList seleccion,DefaultListModel modeloLista,ArrayList<T> lista){
        int []elemSeleccionados; // posicion de los elementos que se van a borrar
        T elemento; // elemento que se va a eliminar
        
        elemSeleccionados = seleccion.getSelectedIndices(); // Obtener las posiciones de los elementos a eliminar
        for(int i=elemSeleccionados.length-1;i>=0;i--){
            elemento = (T)modeloLista.getElementAt(elemSeleccionados[i]);      
            lista.get(elemento.getPosicion()).setSeleccionado(false); // Desmarcar el elemento de la estructura de datos 
            modeloLista.remove(elemSeleccionados[i]);
            //mostrarListaElementos();
        }
        if(elemSeleccionados.length>1){  // si se seleccionaron varios elementos para borrarlos
            if(!modeloLista.isEmpty())
                seleccion.setSelectedIndex(0);
        }else{
            if(elemSeleccionados[0]==modeloLista.getSize()){ // si el elemento que se borró es el último de la lista
                seleccion.setSelectedIndex(modeloLista.getSize()-1); // se selecciona el elemento anterior al eliminado
            }else{
                seleccion.setSelectedIndex(elemSeleccionados[0]); // seleccionar el elemento siguiente a eliminado
            }
        }
    }
    
    /**
     * Método que inicia la ejecución del programa.
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String args[]) {
        // LookAndFeel nativo
        try {    
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            //System.err.println(ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConectar;
    private javax.swing.JButton btnModoExport;
    private javax.swing.JButton btnModoImport;
    private javax.swing.JButton btnSalir;
    private javax.swing.JPanel contenedor;
    private javax.swing.JLabel imagenInicio;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel labelClave;
    private javax.swing.JLabel labelServidor;
    private javax.swing.JLabel labelUsuario;
    private javax.swing.JPanel menuLateral;
    private javax.swing.JTextArea msj;
    private javax.swing.JPanel panelContenido;
    private javax.swing.JPanel panelLogIn;
    private javax.swing.JPanel panelSecundario;
    private javax.swing.JPanel panelSesion;
    private javax.swing.JLabel tituloLogIn;
    private javax.swing.JPasswordField txtClave;
    private javax.swing.JTextField txtServidor;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
