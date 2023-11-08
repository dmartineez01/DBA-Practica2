package practica2;

import java.awt.Point;
import java.util.NavigableMap;
import java.util.TreeMap;

public class Entorno {

    private Mapa mapa;
    private NavigableMap<Point, Boolean> posicionesNoPosibles = new TreeMap<>((p1, p2) -> {
        int compareX = Integer.compare(p1.x, p2.x);
        if (compareX != 0) {
            return compareX;
        }
        return Integer.compare(p1.y, p2.y);
    });

    public Entorno(Mapa mapa) {
        this.mapa = mapa;
    }

    // Método para verificar las casillas adyacentes y diagonales
    public boolean[] verificarCasillasAdyacentes(Point posicion) {
        boolean[] infoAdyacente = new boolean[8]; // top, top-right, right, bottom-right, bottom, bottom-left, left, top-left

        // Verificar las casillas adyacentes y diagonales si están libres o no
        int[][] directions = {
            {-1, 0}, {-1, 1}, {0, 1}, {1, 1},
            {1, 0}, {1, -1}, {0, -1}, {-1, -1}
        };

        for (int i = 0; i < directions.length; i++) {
            int newX = posicion.x + directions[i][0];
            int newY = posicion.y + directions[i][1];

            // Verifica si la posición está dentro de los límites del mapa y si no es un obstáculo
            infoAdyacente[i] = newX >= 0 && newX < mapa.getFilas()
                    && newY >= 0 && newY < mapa.getColumnas()
                    && !mapa.isObstacle(newX, newY);

            if (!infoAdyacente[i]) {
                posicionesNoPosibles.put(new Point(newX, newY), Boolean.FALSE);
            }
        }

        return infoAdyacente;
    }

    // Método para verificar si una casilla específica está libre
    public boolean isFree(int x, int y) {
        return x >= 0 && x < mapa.getFilas() && y >= 0 && y < mapa.getColumnas() && mapa.isFree(x, y);
    }

    public NavigableMap<Point, Boolean> getPosicionesNoPosibles() {
        return posicionesNoPosibles;
    }
}
