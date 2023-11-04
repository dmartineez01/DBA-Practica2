package practica2;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {


    
    public InfoPanel() {
        
        initComponents();
    }

    public void setIteracion(int iteracion) {
        lblIteracion.setText("Iteración: " + iteracion);
    }

    public void setPosicionAgente(Point posicion) {
        lblPosicionAgente.setText("Agente: " + posicion.toString());
    }

    public void setPosicionObjetivo(Point posicion) {
        lblPosicionObjetivo.setText("Objetivo: " + posicion.toString());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        SubPanelInfo = new javax.swing.JPanel();
        TituloProyecto = new javax.swing.JLabel();
        lblIteracion = new javax.swing.JLabel();
        lblPosicionAgente = new javax.swing.JLabel();
        lblPosicionObjetivo = new javax.swing.JLabel();

        setBackground(new java.awt.Color(204, 204, 255));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        TituloProyecto.setFont(new java.awt.Font("Lucida Fax", 0, 14)); // NOI18N
        TituloProyecto.setText("Información Simulación");

        lblIteracion.setFont(new java.awt.Font("Rockwell Extra Bold", 0, 12)); // NOI18N
        lblIteracion.setText("Iteración: 0");

        lblPosicionAgente.setFont(new java.awt.Font("Rockwell Extra Bold", 0, 12)); // NOI18N
        lblPosicionAgente.setText("Agente: [0,0]");

        lblPosicionObjetivo.setFont(new java.awt.Font("Rockwell Extra Bold", 0, 12)); // NOI18N
        lblPosicionObjetivo.setText("Objetivo: [0,0]");

        javax.swing.GroupLayout SubPanelInfoLayout = new javax.swing.GroupLayout(SubPanelInfo);
        SubPanelInfo.setLayout(SubPanelInfoLayout);
        SubPanelInfoLayout.setHorizontalGroup(
            SubPanelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SubPanelInfoLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(SubPanelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPosicionObjetivo)
                    .addComponent(lblIteracion)
                    .addComponent(TituloProyecto)
                    .addComponent(lblPosicionAgente))
                .addContainerGap(213, Short.MAX_VALUE))
        );
        SubPanelInfoLayout.setVerticalGroup(
            SubPanelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SubPanelInfoLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(TituloProyecto, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblIteracion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblPosicionAgente)
                .addGap(18, 18, 18)
                .addComponent(lblPosicionObjetivo)
                .addContainerGap(73, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SubPanelInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SubPanelInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel SubPanelInfo;
    private javax.swing.JLabel TituloProyecto;
    private javax.swing.JLabel lblIteracion;
    private javax.swing.JLabel lblPosicionAgente;
    private javax.swing.JLabel lblPosicionObjetivo;
    // End of variables declaration//GEN-END:variables
}
