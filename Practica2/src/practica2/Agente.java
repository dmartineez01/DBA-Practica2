package practica2;

import jade.core.Agent;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Agente extends Agent {

    private Point posicionActual;
    private Point objetivo;
    private Entorno entorno;
    private ArrayList<Point> pasos = new ArrayList<>();

    public Agente() {
        // El constructor vacío es necesario para JADE
    }

    public Mapa cargarMapa(String rutaArchivo) {

        return new Mapa(rutaArchivo);

    }

    public void setup() {
        int startX = 0;
        int startY = 0;
        this.objetivo = new Point(18, 18);
        this.posicionActual = new Point(startX, startY);
        this.pasos = new ArrayList<>();        
        new Controlador(this);
    }

    public Agente getAgente() {
        return this;
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
            int newX = posicion.x + directions[i][0]; // x para columna
            int newY = posicion.y + directions[i][1]; // y para fila

            if (entorno.isFree(newX, newY) && !pasoPorRendija(posicion, newX, newY)) {
                sucesores.add(new Point(newX, newY));
            }
        }
        return sucesores;
    }

    public boolean pasoPorRendija(Point pos, int newX, int newY) {
        boolean pasoPorRendija = false;
        Point ob = new Point(newX, newY);
        
        Point direccion = new Point(ob.x - pos.x, ob.y - pos.y);
        Point obst1 = new Point(pos.x + direccion.x, pos.y);
        Point obst2 = new Point(pos.x, pos.y + direccion.y);

        return (entorno.getPosicionesNoPosibles().containsKey(obst1) && entorno.getPosicionesNoPosibles().containsKey(obst2));
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

    public void setEntorno(Entorno e) {
        this.entorno = e;
    }

    public void setObjetivo(Point o) {
        this.objetivo = o;
        this.pasos = new ArrayList<>();

    }

    public Point getPosicionActual() {
        return posicionActual;
    }

    public void setPosicionActual(Point p) {
        posicionActual = p;
    }

    public Point getPosicionObjetivo() {
        return objetivo;
    }

    public Entorno getEntorno() {
        return entorno;
    }

    public ArrayList<Point> getPasos() {
        return pasos;
    }

    public void setPasos(ArrayList<Point> p) {
        pasos = p;
    }

}
