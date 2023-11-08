package practica2;

import java.awt.Point;
import java.util.NavigableMap;
import java.util.TreeMap;

public class Entorno {

    private Mapa mapa;
    private NavigableMap<Point, Boolean> posicionesNoPosibles = new TreeMap<>((p1, p2) -> {
        int compareY = Integer.compare(p1.y, p2.y); // Primero comparamos las filas
        if (compareY != 0) {
            return compareY;
        }
        return Integer.compare(p1.x, p2.x); // Luego las columnas
    });

    public Entorno(Mapa mapa) {
        this.mapa = mapa;
    }

    public boolean[] verificarCasillasAdyacentes(Point posicion) {
        boolean[] infoAdyacente = new boolean[8];

        int[][] directions = {
            {-1, 0}, {-1, 1}, {0, 1}, {1, 1},
            {1, 0}, {1, -1}, {0, -1}, {-1, -1}
        };

        for (int i = 0; i < directions.length; i++) {
            int newX = posicion.x + directions[i][0]; // x para columna
        int newY = posicion.y + directions[i][1]; // y para fila

        infoAdyacente[i] = newX >= 0 && newX < mapa.getColumnas() && newY >= 0 && newY < mapa.getFilas()
                           && !mapa.isObstacle(newX, newY);

            if (!infoAdyacente[i]) {
                posicionesNoPosibles.put(new Point(newX, newY), Boolean.FALSE);
            }
        }
        
        System.out.print("Posicion (4,8): " + mapa.isFree(4, 8));
        System.out.print("Posicion (8,4): " + mapa.isFree(8, 4));

        return infoAdyacente;
    }

    public boolean isFree(int x, int y) {
        
        Point punto = new Point(x, y);
        if (posicionesNoPosibles.containsKey(punto)) {
            return false;
        }
        
        return true;
    }
    
    public boolean isObjetivo(int x, int y) {
        return mapa.isObjetivo(x, y);
    }
    
    public void setMapa(Mapa mapaNuevo){
        this.mapa = mapaNuevo;
        // Restablecer posicionesNoPosibles para el nuevo mapa
        this.posicionesNoPosibles = new TreeMap<>((p1, p2) -> {
            int compareY = Integer.compare(p1.y, p2.y);
            if (compareY != 0) {
                return compareY;
            }
            return Integer.compare(p1.x, p2.x);
        });
    }
    
    public NavigableMap<Point, Boolean> getPosicionesNoPosibles() {
        return posicionesNoPosibles;
    }
}
