package practica2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ControlPanel extends JPanel {

    private ActionListener iterarListener;
    private ActionListener automaticoListener;
    
    private ArrayList<Controlador> observadores = new ArrayList<Controlador>();
    
    public ControlPanel(){
        initComponents();
    }
    
    public void suscribir(Controlador c){
        observadores.add(c);
    }
    
    public void notificarAll(){
        for(int i=0; i < observadores.size(); i++){
            observadores.get(i).notificar();
        }
    }
    
    public ControlPanel(ActionListener iterarListener, ActionListener automaticoListener) {
        
        initComponents();
        
        this.iterarListener = iterarListener;
        this.automaticoListener = automaticoListener;

        btnIterar.addActionListener(iterarListener);
        btnAutomatico.addActionListener(automaticoListener);
    }

    public void setAutomaticoButtonText(String text) {
        btnAutomatico.setText(text);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnIterar = new javax.swing.JButton();
        btnAutomatico = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(204, 204, 255));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnIterar.setBackground(new java.awt.Color(255, 255, 204));
        btnIterar.setText("Iterar");
        btnIterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIterarActionPerformed(evt);
            }
        });

        btnAutomatico.setBackground(new java.awt.Color(255, 255, 204));
        btnAutomatico.setText("Automatico");

        jLabel1.setFont(new java.awt.Font("Serif", 0, 36)); // NOI18N
        jLabel1.setText("Ejecución");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnAutomatico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnIterar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addComponent(jLabel1)))
                .addContainerGap(73, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(btnIterar, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnAutomatico, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnIterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIterarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnIterarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAutomatico;
    private javax.swing.JButton btnIterar;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
