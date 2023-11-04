/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica2;

/**
 *
 * @author Usuario
 */
import java.awt.Point;
import java.io.*;
import java.awt.*;


public class Mapa {
    private int[][] data;
    private int filas;
    private int columnas;
    private Point posicionObjetivo;
    
    // Constructor modificado para llamar al método loadMap.
    public Mapa(String fichero) {
        loadMap(fichero);
    }

    // Método para cargar el mapa desde un fichero.
    public void loadMap(String fichero) {
        try {
            
            InputStream is = this.getClass().getClassLoader().getResourceAsStream(fichero);
            if (is == null) {
                throw new FileNotFoundException("No se pudo encontrar el archivo: " + fichero);
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            
            filas = Integer.parseInt(reader.readLine().trim());
            columnas = Integer.parseInt(reader.readLine().trim());
            
            data = new int[filas][columnas];
            for(int i = 0; i < filas; i++) {
                String[] line = reader.readLine().split("\t");
                for(int j = 0; j < columnas; j++) {
                    data[i][j] = Integer.parseInt(line[j]);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isObstacle(int x, int y) {
        return data[x][y] == -1;
    }

    public boolean isFree(int x, int y) {
        return data[x][y] == 0;
    }
    
    public boolean isObjetivo(int x, int y) {
        return data[x][y] == 2;
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }
    
    // Modificamos el método setObjetivo
    public void setObjetivo(int x, int y) {
        if (!isObstacle(x, y)) {
            data[x][y] = 2; // Suponiendo que el número 2 indica el objetivo en tu mapa
            posicionObjetivo = new Point(x, y);
        } else {
            System.err.println("No se puede establecer el objetivo en una posición ocupada por un obstáculo.");
        }
    }

    // Implementamos getObjetivo
    public Point getObjetivo() {
        return posicionObjetivo;
    }
    
    public void dibujar(Graphics g) {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (data[i][j] == -1) {
                    g.setColor(Color.BLACK); // Color para los obstáculos
                } else if (data[i][j] == 2) {
                    g.setColor(Color.RED); // Color para el objetivo
                } else {
                    g.setColor(Color.WHITE); // Color para los caminos libres
                }
                g.fillRect(j * 20, i * 20, 20, 20); // Dibujar cada celda del mapa (asumiendo que cada celda es de 20x20 píxeles)
                g.setColor(Color.GRAY); // Color para las líneas de la cuadrícula
                g.drawRect(j * 20, i * 20, 20, 20); // Dibujar el borde de cada celda
            }
        }
    }
}

