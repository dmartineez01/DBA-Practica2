package practica2;

import java.awt.Graphics;
import java.awt.Color;

public class MapaPanel extends javax.swing.JPanel {

    private Mapa mapa;
    private int agenteX, agenteY;
    private final int CELL_SIZE = 20; // Tamaño de cada celda
    
    public MapaPanel() {
        initComponents();
    }
    
    public MapaPanel(Mapa mapa, int agenteX, int agenteY) {
        this.mapa = mapa;
        this.agenteX = agenteX;
        this.agenteY = agenteY;
        initComponents();
    }

    public void setAgentePosicion(int x, int y) {
        this.agenteX = x;
        this.agenteY = y;
        repaint(); // Repinta el mapa cada vez que el agente cambia de posición
    }

    public void setMap(Mapa nuevoMapa) {
        this.mapa = nuevoMapa;
        repaint(); // Repinta el panel para mostrar el nuevo mapa
    }

    @Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    // Asegúrate de que el mapa no sea nulo antes de intentar pintarlo
    if (mapa != null) {
        for (int i = 0; i < mapa.getFilas(); i++) {
            for (int j = 0; j < mapa.getColumnas(); j++) {
                if (i == agenteX && j == agenteY) { // Asumiendo que agenteX es fila y agenteY es columna
                    g.setColor(Color.BLUE);
                } else if (mapa.isObstacle(i, j)) {
                    g.setColor(Color.BLACK);
                } else if (mapa.isObjetivo(i, j)) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                g.setColor(Color.GRAY);
                g.drawRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
    }
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 880, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 397, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
