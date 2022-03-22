/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import java.util.ArrayList;

/**
 *
 * @author Geny
 */
public class PanelColumna extends javax.swing.JPanel {
    //ArrayList<InfoColumna> columnas;
    InfoColumna info;
    
    /**
     * Creates new form PanelColumna
     * @param col
     */
    public PanelColumna(InfoColumna col) {
        initComponents();
        info = col;
        //this.posicion = posicion;
        //info = columnas.get(posicion);

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
        tamCol = new javax.swing.JTextField();
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

        tamCol.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tamCol.setText("0");
        tamCol.setToolTipText("Tamaño");

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
                .addComponent(tamCol, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addComponent(tamCol)
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
        checkPK.setSelected(info.getPK());
        checkNN.setSelected(info.getNN());
        checkUQ.setSelected(info.getUQ());
        checkUN.setSelected(info.getUN());
        checkAI.setSelected(info.getAI());
        tipoCol.setSelectedIndex(info.getTipo());
    }
    
    private void checkPKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkPKActionPerformed
        if(checkPK.isSelected()){
            info.setPK(true);
            info.setNN(true);
            checkNN.setSelected(true);
        }else{
            //checkNN.setSelected(false);
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
                tamCol.setText("");
                tamCol.setEnabled(true);
                tamCol.setToolTipText("Ancho de visualización");
                break;
            case Tipo.DOUBLE: 
            case Tipo.DATE: 
            case Tipo.DATETIME:
            case Tipo.TIME:
            case Tipo.TIMESTAMP:
            case Tipo.YEAR:   
                tamCol.setText("");
                tamCol.setEnabled(false);
                tamCol.setToolTipText("");
                break;
            default:
                tamCol.setEnabled(true);
                break;
        }
        
    }//GEN-LAST:event_comboTipoStateChanged

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
    private javax.swing.JTextField tamCol;
    private javax.swing.JComboBox tipoCol;
    // End of variables declaration//GEN-END:variables
}
