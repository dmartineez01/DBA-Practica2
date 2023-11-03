/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica2;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;


public class MapaVisual extends JFrame {
    
    private Mapa mapa;
    private int agenteX, agenteY;
    private final int CELL_SIZE = 20; // Define el tamaño de cada celda en el canvas

    private JButton btnIterar;
    private JButton btnAuto;
    private Timer autoTimer;
    public ActionListener doIteration;
    
    private Agente agente;
    
    private JLabel lblTitulo;
    private JLabel lblPosicionActual;
    private JLabel lblPosicionObjetivo;
    private JLabel lblNumeroIteracion;
    private int iteraciones = 0;

    private MapaPanel mapaPanel;
    private JComboBox<String> comboBoxMapas;
    private JComboBox<Point> comboBoxPosicionesAgente;
    private JButton botonInicio;
    
    public MapaVisual(Mapa mapa, Agente agente) {
        this.mapa = mapa;
        this.agente = agente;
        this.agenteX = agente.getPosicionActual().x;
        this.agenteY = agente.getPosicionActual().y;

        // Configuración básica de la ventana
        this.setTitle("Visualización del Mapa");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout()); // Usar BorderLayout para la disposición principal

        // Crear y configurar el panel del mapa
        mapaPanel = new MapaPanel(mapa, agenteX, agenteY);
        mapaPanel.setPreferredSize(new Dimension(600, 400));
        mapaPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Añadir márgenes alrededor del mapa

        // Panel superior para selección de mapas y posiciones
        JPanel comboBoxPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 10, 10)); // Usar FlowLayout con espacios
        comboBoxPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Añadir márgenes alrededor de los componentes

        String[] mapas = {"mapa1", "mapa2", "mapa3"};
        comboBoxMapas = new JComboBox<>(mapas);
        comboBoxMapas.addActionListener(e -> {
            String selectedMap = (String) comboBoxMapas.getSelectedItem();
            mapa.loadMap("practica2/Mapas/" + selectedMap + ".txt");
            agente.changeMap("practica2/Mapas/" + selectedMap + ".txt");
            mapaPanel.setMap(mapa); // Suponiendo que tienes un método setMap para actualizar el mapa.
            mapaPanel.repaint();
            mapaPanel.revalidate(); // Si es necesario, para revalidar el layout
            updateAgentPositions(); // Actualiza las posiciones del agente si es necesario
        });


        comboBoxPosicionesAgente = new JComboBox<>();
        comboBoxPosicionesAgente.addActionListener(e -> {
            Point selectedPosition = (Point) comboBoxPosicionesAgente.getSelectedItem();
            agente.setPosicionActual(selectedPosition);
            setAgentePosicion(selectedPosition.x, selectedPosition.y);
            mapaPanel.repaint();
        });
        
        botonInicio = new JButton("Iniciar");
        botonInicio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Inicia el comportamiento del agente o lo que sea necesario
                agente.startMovement();
            }
        });

        comboBoxPanel.add(new JLabel("Seleccionar Mapa:"));
        comboBoxPanel.add(comboBoxMapas);
        comboBoxPanel.add(new JLabel("Seleccionar Posición Inicial:"));
        comboBoxPanel.add(comboBoxPosicionesAgente);
        comboBoxPanel.add(botonInicio);
        
        // Añadir el panel del mapa y los botones a la ventana
        add(mapaPanel, BorderLayout.CENTER);
        add(comboBoxPanel, BorderLayout.NORTH);
        
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        btnIterar = new JButton("Iterar");
        btnIterar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agente.startMovement();
                doIteration();
            }
        });

        btnAuto = new JButton("Auto");
        btnAuto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agente.startMovement();
                if (autoTimer == null) {
                    autoTimer = new Timer(500, new ActionListener() { // Ejecuta cada 500 milisegundos
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            doIteration();
                        }
                    });
                    autoTimer.start();
                    btnAuto.setText("Detener");
                } else {
                    autoTimer.stop();
                    autoTimer = null;
                    btnAuto.setText("Auto");
                }
            }
        });

        btnPanel.add(btnIterar);
        btnPanel.add(btnAuto);
        
        // Panel informativo en el lado este
        JPanel panelInfo = new JPanel(new GridLayout(6, 1, 10, 10)); // GridLayout con espacios horizontales y verticales
        panelInfo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Información del Agente"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        lblPosicionActual = new JLabel("Posición Actual: (" + agenteX + "," + agenteY + ")");
        lblPosicionObjetivo = new JLabel("Posición Objetivo: (" + agente.getObjetivo().x + "," + agente.getObjetivo().y + ")");
        lblNumeroIteracion = new JLabel("Iteración: 0");
    
    
        // Añadir etiquetas informativas al panelInfo
    panelInfo.add(lblPosicionActual);
    panelInfo.add(lblPosicionObjetivo);
    panelInfo.add(lblNumeroIteracion);

    // Añadir paneles al JFrame
    this.add(comboBoxPanel, BorderLayout.NORTH);
    this.add(mapaPanel, BorderLayout.CENTER);
    this.add(btnPanel, BorderLayout.SOUTH);
    this.add(panelInfo, BorderLayout.EAST);
    
    // Inicializa la lista de posiciones disponibles para el agente
        updateAgentPositions();
        
    // Ajustes finales de la ventana
    this.pack(); // Ajustar el tamaño de la ventana para acomodar los componentes
    this.setLocationRelativeTo(null); // Centrar en la pantalla
    this.setVisible(true); // Mostrar la ventana

    }
    
    public void doIteration() {
        if(doIteration != null) {
            doIteration.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "doIteration"));
            }
    }

    // Método para actualizar la lista de posiciones disponibles para el agente
    private void updateAgentPositions() {
        comboBoxPosicionesAgente.removeAllItems();
        for (int i = 0; i < mapa.getFilas(); i++) {
            for (int j = 0; j < mapa.getColumnas(); j++) {
                if (mapa.isFree(i, j)) {
                    comboBoxPosicionesAgente.addItem(new Point(i, j));
                }
            }
        }
    }
    
    // En la clase MapaVisual
    public void setMap(Mapa newMap) {
        this.mapa = newMap; // Actualiza el mapa actual de esta visualización.

        // Asegúrate de actualizar también el mapa en el MapaPanel.
        mapaPanel.setMap(newMap);

        // Después de cambiar el mapa, probablemente necesites actualizar las posiciones del agente.
        updateAgentPositions();

        // Y actualiza la interfaz gráfica para reflejar los cambios del nuevo mapa.
        mapaPanel.repaint();
        mapaPanel.revalidate(); // Si es necesario, para revalidar el layout.
    }

    
    public void setAgentePosicion(int x, int y) {
        mapaPanel.setAgentePosicion(x, y); // Llamamos al método del MapaPanel
        lblPosicionActual.setText("Posición Actual: (" + x + "," + y + ")");
        iteraciones++;
        lblNumeroIteracion.setText("Iteración: " + iteraciones);
    }

}
