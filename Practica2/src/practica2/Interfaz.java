/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 *
 * @author Usuario
 */
public class Interfaz extends javax.swing.JFrame {

    private ConfigPanel configPanel;
    private InfoPanel infoPanel;
    private MapaPanel panelMapa;
    private ControlPanel controlPanel;
    
    private Controlador controlador; // Supongamos que tienes una clase Controlador
    private Timer autoIterarTimer; // Timer para el modo automático
    private int iteracion = 0;
   

    public Interfaz(Controlador controlador) {
    initComponents();
    this.controlador = controlador; // Inyectamos la dependencia del controlador

    setTitle("Simulación de Agente");
    setSize(1400, 800);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new GridLayout(2, 1)); // Establecer GridLayout para dos filas y una columna

    // Crear un panel para la primera fila con GridLayout para tres paneles
    JPanel fila1 = new JPanel(new GridLayout(1, 3));
    
    // ConfigPanel
    configPanel = new ConfigPanel(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            String mapaSeleccionado = configPanel.getMapaSeleccionado();
            mapaSeleccionado = "practica2/Mapas/" + mapaSeleccionado + ".txt";
            controlador.actualizarMapa(mapaSeleccionado); 
            
            
            Point nuevaPosicionObjetivo = configPanel.getPosicionObjetivo();
            if (nuevaPosicionObjetivo != null) {
                
                controlador.colocarObjetivo(nuevaPosicionObjetivo.x, nuevaPosicionObjetivo.y);
                actualizarInfo();
            }
            
            Point nuevaPosicion = configPanel.getPosicionAgente();
            if (nuevaPosicion != null) {
                
                controlador.colocarAgente(nuevaPosicion.x, nuevaPosicion.y);
                actualizarInfo();
            }
            
            controlador.resetEntorno();
            
            panelMapa.setMap(controlador.getMapaActual());
            panelMapa.setAgentePosicion(controlador.getPosicionAgente().x, controlador.getPosicionAgente().y);
            resetInfo();
            
            // Actualizar el mapa actual en el controlador y en la interfaz
        }
    });
    fila1.add(configPanel);

    // InfoPanel
    infoPanel = new InfoPanel();
    fila1.add(infoPanel);

    // ControlPanel
    controlPanel = new ControlPanel(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            iterar();
        }
    }, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (autoIterarTimer.isRunning()) {
                autoIterarTimer.stop();
                controlPanel.setAutomaticoButtonText("Automático");
            } else {
                autoIterarTimer.start();
                controlPanel.setAutomaticoButtonText("Detener");
            }
        }
    });
    fila1.add(controlPanel);

    // Agregar fila1 al JFrame
    add(fila1);

    // MapaPanel
    Mapa mapaInicial = controlador.getMapaActual();
    Point posicionInicialAgente = controlador.getPosicionAgente();
    panelMapa = new MapaPanel(mapaInicial, posicionInicialAgente.x, posicionInicialAgente.y);
    add(panelMapa); // Añadir el MapaPanel en la segunda fila

    // Configuramos el timer para la iteración automática
    autoIterarTimer = new Timer(200, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            iterar();
        }
    });
}


    public void actualizarMapaYAgente() {
        panelMapa.repaint(); // Si MapaPanel maneja su propio dibujo, un repaint será suficiente para actualizar la vista
    }

    public void iterar() {
        // Incrementar la iteración
        iteracion++;
        controlador.iterarAgente();
        actualizarInfo();
        
        // Actualiza el mapa y verifica si el agente ha alcanzado el objetivo
        actualizarMapaYAgente();
        if (controlador.agenteAlcanzoObjetivo()) {
            autoIterarTimer.stop();
            JOptionPane.showMessageDialog(this, "El agente ha alcanzado el objetivo!");
        }
    }

    // Dentro de tu clase Interfaz...
    public void actualizarInfo() {
        Point posicionAgente = controlador.getPosicionAgente();
        Point posicionObjetivo = controlador.getPosicionObjetivo();

        infoPanel.setIteracion(iteracion);
        infoPanel.setPosicionAgente(posicionAgente);
        infoPanel.setPosicionObjetivo(posicionObjetivo);

        panelMapa.setAgentePosicion(posicionAgente.x, posicionAgente.y);
        panelMapa.repaint(); // Podrías necesitar repintar el mapa después de establecer la nueva posición
    }
    
    public void resetInfo() {
        Point posicionAgente = controlador.getPosicionAgente();
        Point posicionObjetivo = controlador.getPosicionObjetivo();

        infoPanel.setIteracion(0);
        infoPanel.setPosicionAgente(posicionAgente);
        infoPanel.setPosicionObjetivo(posicionObjetivo);

        panelMapa.repaint(); // Podrías necesitar repintar el mapa después de establecer la nueva posición
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    
    public static void main(String args[]) {
        
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        /*
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CivitasView().setVisible(true);
            }
        });
        */
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
