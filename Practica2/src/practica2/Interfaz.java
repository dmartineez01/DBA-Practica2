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
import java.util.Observable;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.FloatControl;


/**
 *
 * @author Usuario
 */
public class Interfaz extends javax.swing.JFrame {

    private ConfigPanel configPanel;
    private InfoPanel infoPanel;
    private ControlPanel controlPanel;
    private MapaPanel panelMapa;
    private JPanel background;

    private Controlador controlador; // Supongamos que tienes una clase Controlador
    private Timer autoIterarTimer; // Timer para el modo automático
    private int iteracion = 0;
    
    private Clip clipBackground;
    
    public Interfaz(Controlador controlador) {
        initComponents();
        this.controlador = controlador;

        setTitle("Simulación de Agente");
        setSize(1750, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2)); // Un GridLayout de 1 fila y 2 columnas
        
        // Panel para agrupar configPanel, infoPanel y controlPanel
        JPanel leftPanel = new JPanel(new GridLayout(3, 1));
        
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
        leftPanel.add(configPanel);

        // InfoPanel
        infoPanel = new InfoPanel();
        leftPanel.add(infoPanel);

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
        leftPanel.add(controlPanel);
        add(leftPanel); // Añadir el panel izquierdo
        
        // MapaPanel
        Mapa mapaInicial = controlador.getMapaActual();
        Point posicionInicialAgente = controlador.getPosicionAgente();
        panelMapa = new MapaPanel(mapaInicial, posicionInicialAgente.x, posicionInicialAgente.y);
        panelMapa.setBounds(10, 320, 750, 500); // Establecer posición y tamaño
        add(panelMapa);

        // Configuramos el timer para la iteración automática
        autoIterarTimer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iterar();
            }
        });
        
        playBackgroundMusic("/sounds/background.wav");
    }

    public ControlPanel getControlPanel() {
        return (controlPanel);
    }

    public void actualizarMapaYAgente() {
        panelMapa.repaint(); // Si MapaPanel maneja su propio dibujo, un repaint será suficiente para actualizar la vista
    }



    public void iterar() {
        // Incrementar la iteración
        iteracion++;
        controlPanel.notificarAll();
        //controlador.iterarAgente();
        actualizarInfo();

        // Actualiza el mapa y verifica si el agente ha alcanzado el objetivo
        actualizarMapaYAgente();
        if (controlador.agenteAlcanzoObjetivo()) {
            autoIterarTimer.stop();
            if (clipBackground != null && clipBackground.isRunning()) {
            clipBackground.stop(); // Detener la música de fondo
        }
        playSound("/sounds/victory.wav", true);
            // Escalar la imagen para el diálogo
            
            ImageIcon icono = new ImageIcon(getClass().getResource("/images/link_zelda.gif"));

            // Crear un JLabel para el mensaje con un tamaño de letra grande
            JLabel messageLabel = new JLabel("<html><h1>¡Has conseguido salvar a la princesa!</h1></html>");
            messageLabel.setHorizontalAlignment(JLabel.CENTER);

            // Crear un panel para contener tanto el mensaje como la imagen
            JPanel panel = new JPanel(new BorderLayout());
            panel.add(messageLabel, BorderLayout.NORTH); // Añadir el mensaje en la parte superior
            panel.add(new JLabel(icono), BorderLayout.CENTER); // Añadir la imagen en el centro

            // Mostrar el diálogo
            JOptionPane.showMessageDialog(
                this,
                panel,
                "Objetivo Alcanzado",
                JOptionPane.INFORMATION_MESSAGE
            );
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
    
    private void playBackgroundMusic(String filePath) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource(filePath));
            clipBackground = AudioSystem.getClip();
            clipBackground.open(audioInputStream);
            // Ajustar el volumen
            FloatControl gainControl = (FloatControl) clipBackground.getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range * 0.6f) + gainControl.getMinimum(); // 0.2f = 20% del volumen máximo
            gainControl.setValue(gain);

            clipBackground.start();
            clipBackground.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al reproducir la música de fondo.");
        }
    }


    private void playSound(String filePath, boolean resumeBackgroundMusic) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource(filePath));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            // Ajustar el volumen del sonido de la victoria
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range * 0.7f) + gainControl.getMinimum(); // 0.2f = 20% del volumen máximo
            gainControl.setValue(gain);

            clip.start();

            // Esperar 5 segundos, detener el sonido de la victoria y reanudar la música de fondo
            if (resumeBackgroundMusic) {
                Timer timer = new Timer(3000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        clip.stop();
                        clip.close();
                        if (clipBackground != null) {
                            clipBackground.start();
                            clipBackground.loop(Clip.LOOP_CONTINUOUSLY);
                        }
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al reproducir el sonido del objetivo.");
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
