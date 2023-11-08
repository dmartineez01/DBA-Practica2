package practica2;

import jade.core.Agent;
import java.awt.Point;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Agente extends Agent {

    private Point posicionActual;
    private Point objetivo;
    private Entorno entorno;
    private ArrayList<Point> pasos = new ArrayList<>();;

    // Constructor
    public Agente(Entorno entorno, int startX, int startY, Point objetivo) {
        this.entorno = entorno;
        this.posicionActual = new Point(startX, startY);
        this.objetivo = objetivo; // Suponemos que la posición del objetivo es conocida por el agente
        System.out.print("Mi objetivo es: ");
        System.out.print(objetivo);
    }

    private double distanciaHeuristica(Point a, Point b) {
        int dx = a.x - b.x;
        int dy = a.y - b.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    private ArrayList<Point> generarSucesores(Point posicion) {
        ArrayList<Point> sucesores = new ArrayList<>();
        int[][] directions = {
            {-1, 0}, {-1, 1}, {0, 1}, {1, 1},
            {1, 0}, {1, -1}, {0, -1}, {-1, -1}
        };

        for (int i = 0; i < directions.length; i++) {
            int newX = posicion.x + directions[i][0];
            int newY = posicion.y + directions[i][1];

            if (!entorno.getPosicionesNoPosibles().containsKey(new Point(newX, newY))) {
                sucesores.add(new Point(newX, newY));
            }
        }

        return sucesores;
    }

    public ArrayList<Point> obtenerCaminoHasta(Point objetivo) {
        // Crear estructuras de datos para la búsqueda
        PriorityQueue<NodoAStar> abiertos = new PriorityQueue<>((n1, n2) -> Double.compare(n1.f, n2.f));
        Map<Point, NodoAStar> nodosVisitados = new HashMap<>();

        // Nodo inicial
        NodoAStar nodoInicial = new NodoAStar(posicionActual, 0, distanciaHeuristica(posicionActual, objetivo), null);
        abiertos.add(nodoInicial);

        while (!abiertos.isEmpty()) {
            NodoAStar nodoActual = abiertos.poll();
            Point posicionActual = nodoActual.posicion;

            // Verificar si hemos alcanzado el objetivo
            if (posicionActual.equals(objetivo)) {
                // Reconstruir el camino desde el nodo objetivo al nodo inicial
                ArrayList<Point> camino = new ArrayList<>();
                NodoAStar nodo = nodoActual;
                while (nodo != null) {
                    camino.add(0, nodo.posicion);
                    nodo = nodo.padre;
                }
                return camino;
            }

            // Generar sucesores
            for (Point sucesor : generarSucesores(posicionActual)) {
                if (!nodosVisitados.containsKey(sucesor)) {
                    double g = nodoActual.g + 1; // Costo del movimiento (puede ser diferente si consideras costos diferentes)
                    double h = distanciaHeuristica(sucesor, objetivo);
                    NodoAStar nodoSucesor = new NodoAStar(sucesor, g, g + h, nodoActual);
                    abiertos.add(nodoSucesor);
                    nodosVisitados.put(sucesor, nodoSucesor);
                }
            }
        }

        // No se encontró un camino
        return null;
    }

    // Este método se llamará para mover el agente hacia el objetivo
    public void moveToObjetivo() {
        entorno.verificarCasillasAdyacentes(posicionActual);
        if(pasos.isEmpty() || entorno.getPosicionesNoPosibles().containsKey(pasos.get(0)) ){ //FALTA HACER QUE RECALCULE SI EL SIGUIENTE PASO ES ERRÓNEO
            pasos = obtenerCaminoHasta(objetivo);
        }
        
        posicionActual = pasos.get(0);
        pasos.remove(0);
//        // Moverse horizontalmente hacia el objetivo si es posible
//        if (posicionActual.x < objetivo.x && entorno.isFree(posicionActual.x + 1, posicionActual.y)) {
//            posicionActual.x++;
//        } else if (posicionActual.x > objetivo.x && entorno.isFree(posicionActual.x - 1, posicionActual.y)) {
//            posicionActual.x--;
//        }
//        // Moverse verticalmente hacia el objetivo si es posible
//        if (posicionActual.y < objetivo.y && entorno.isFree(posicionActual.x, posicionActual.y + 1)) {
//            posicionActual.y++;
//        } else if (posicionActual.y > objetivo.y && entorno.isFree(posicionActual.x, posicionActual.y - 1)) {
//            posicionActual.y--;
//        }
    }

    public Point getPosicionActual() {
        return posicionActual;
    }

    public Point getPosicionObjetivo() {
        return objetivo;
    }

    // Llama a este método para obtener información del entorno
    public void checkEntorno() {
        boolean[] infoAdyacente = entorno.verificarCasillasAdyacentes(posicionActual);
        // Procesa la información del entorno según sea necesario
    }

    public void dibujar(Graphics g) {
        g.setColor(Color.BLUE); // Color para el agente
        g.fillOval(posicionActual.x * 20, posicionActual.y * 20, 20, 20); // Dibujar al agente como un círculo
    }
}
