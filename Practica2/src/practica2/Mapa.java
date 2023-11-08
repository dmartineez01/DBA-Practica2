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

    // En Mapa.java
    public boolean isObstacle(int x, int y) {
        return data[y][x] == -1; // x para columna, y para fila
    }

    public boolean isFree(int x, int y) {
        
        return data[y][x] != -1; // x para columna, y para fila
    }

    public boolean isObjetivo(int x, int y) {
        return data[y][x] == 2; // x para columna, y para fila
    }


    public void setObjetivo(int x, int y) throws IllegalArgumentException {
        if (x < 0 || x >= columnas || y < 0 || y >= filas) {
            throw new IllegalArgumentException("Las coordenadas están fuera del mapa.");
        }
        if (!isObstacle(x, y)) {
            data[y][x] = 2; // y para fila, x para columna
            posicionObjetivo = new Point(x, y);
        } else {
            System.err.println("No se puede establecer el objetivo en una posición ocupada por un obstáculo.");
        }
    }


    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    // Implementamos getObjetivo
    public Point getObjetivo() {
        return posicionObjetivo;
    }
    
    
}

