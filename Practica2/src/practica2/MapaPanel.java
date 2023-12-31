package practica2;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class MapaPanel extends javax.swing.JPanel {

    private Mapa mapa;
    private int agenteX, agenteY;
    private final int CELL_SIZE = 20; // Tamaño de cada celda
    
    // Cargar las imágenes
    private Image imgAgente;
    private Image imgObjetivo;
    private Image imgMuro;
    private Image imgSuelo; // Imagen para el suelo
    
    public MapaPanel() {
        initComponents();
    }
    
    public MapaPanel(Mapa mapa, int agenteX, int agenteY) {
        this.mapa = mapa;
        this.agenteX = agenteX;
        this.agenteY = agenteY;
        initComponents();
        
        // Inicializar las imágenes
        imgAgente = new ImageIcon(getClass().getResource("/images/link.gif")).getImage();
        imgObjetivo = new ImageIcon(getClass().getResource("/images/zelda.gif")).getImage();
        imgMuro = new ImageIcon(getClass().getResource("/images/muro.jpg")).getImage();
        imgSuelo = new ImageIcon(getClass().getResource("/images/suelo.png")).getImage(); // Asegúrate de tener una imagen suelo.png
    }

    public void setAgentePosicion(int x, int y) {
        
        if ((mapa.isFree(x, y) || mapa.isObjetivo(x, y)) && x >= 0 && x < mapa.getColumnas() && y >= 0 && y < mapa.getFilas()) {
            
            //  System.out.print("La casilla es la siguiente x: " + x + " y:" + y + " y segun el mapa estaria: " + mapa.isFree(x, y) + "\n" );
            this.agenteX = x;
            this.agenteY = y;
            repaint(); // Repinta el mapa cada vez que el agente cambia de posición
        } else {
            
            
            JOptionPane.showMessageDialog(this, "No se puede colocar al Agente en esta posición!");
        }

        
        
    }

    public void setMap(Mapa nuevoMapa) {
        this.mapa = nuevoMapa;
        repaint(); // Repinta el panel para mostrar el nuevo mapa
    }

@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (mapa != null) {
            int cellWidth = getWidth() / mapa.getColumnas();
            int cellHeight = getHeight() / mapa.getFilas();

            for (int i = 0; i < mapa.getFilas(); i++) {
                for (int j = 0; j < mapa.getColumnas(); j++) {
                    // Primero dibuja el suelo en todas las celdas
                    g.drawImage(imgSuelo, j * cellWidth, i * cellHeight, cellWidth, cellHeight, this);

                    // Luego dibuja el sprite correspondiente encima del suelo
                    if (j == agenteX && i == agenteY) {
                        g.drawImage(imgAgente, j * cellWidth, i * cellHeight, cellWidth, cellHeight, this);
                    } else if (mapa.isObstacle(j, i)) {
                        g.drawImage(imgMuro, j * cellWidth, i * cellHeight, cellWidth, cellHeight, this);
                    } else if (mapa.isObjetivo(j, i)) {
                        g.drawImage(imgObjetivo, j * cellWidth, i * cellHeight, cellWidth, cellHeight, this);
                    }

                    // Si deseas dibujar bordes alrededor de las celdas, descomenta la siguiente línea
                     g.setColor(Color.GRAY);
                     g.drawRect(j * cellWidth, i * cellHeight, cellWidth, cellHeight);
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
