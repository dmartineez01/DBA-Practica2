package practica2;

import jade.core.Agent;
import java.awt.Point;
import java.util.List;
import jade.core.behaviours.*;
import java.util.ArrayList;
import java.util.Random;

public class Agente extends Agent {
    
    private Mapa mapa;
    private Point posicionActual;
    private Point objetivo;
    private MapaVisual visual;
    
    protected void setup() {
        // Inicializar el mapa.
        mapa = new Mapa("practica2/Mapas/mapa1.txt");

        // Configurar agente y visualización.
        initAgente();
    }
    
    // Método que inicializa el agente y la visualización.
    private void initAgente() {
        setPositionRandomly();
        setObjetivoRandomly();

        visual = new MapaVisual(mapa, this);
        visual.doIteration = e -> {
            startMovement();
            visual.setAgentePosicion(posicionActual.x, posicionActual.y);
            visual.repaint(); // Repintar solo después de un movimiento
        };

        visual.setVisible(true);
    }
    
    // Cambiar mapa durante la ejecución.
    // Cambiar mapa durante la ejecución.
    public void changeMap(String newMapPath) {
        mapa.loadMap(newMapPath); // Cargar nuevo mapa
        setPositionRandomly(); // O cualquier lógica para establecer la nueva posición del agente
        setObjetivoRandomly(); // O cualquier lógica para establecer el nuevo objetivo

        // Ahora, en lugar de crear una nueva visual, simplemente actualiza la actual.
        if (visual != null) {
            visual.setMap(mapa); // Actualizar el mapa en la visualización existente.
            visual.setAgentePosicion(posicionActual.x, posicionActual.y);
            visual.repaint(); // Repintar solo después de un cambio en el mapa
        } else {
            visual = new MapaVisual(mapa, this);
            visual.setVisible(true);
        }
    }

    
    public void startMovement() {
        addBehaviour(new ComportamientoMoverse());
    }

    // Método para establecer la posición del agente de forma aleatoria
    public void setPositionRandomly() {
        Random rand = new Random();
        int x, y;

        do {
            x = rand.nextInt(mapa.getFilas());
            y = rand.nextInt(mapa.getColumnas());
        } while (!mapa.isFree(x, y));

        posicionActual = new Point(x, y);
    }

    // Método para establecer la posición actual del agente
    public void setPosicionActual(Point nuevaPosicion) {
        if (mapa.isFree(nuevaPosicion.x, nuevaPosicion.y)) {
            posicionActual = nuevaPosicion;
        } else {
            // Aquí podrías lanzar una excepción o manejar el error como prefieras
            System.err.println("La nueva posición no es válida en el mapa.");
        }
    }

    // Método para obtener la posición actual del agente
    public Point getPosicionActual() {
        return posicionActual;
    }
    
    // Método para obtener el objetivo del agente
    public Point getObjetivo() {
        return objetivo;
    }
    
    // Método para establecer el objetivo del agente
    private void setObjetivo(Point objetivo) {
        this.objetivo = objetivo;
        mapa.setObjetivo(objetivo.x, objetivo.y);
    }
    
    private void setObjetivoRandomly() {
        Random rand = new Random();
        int x, y;

        do {
            x = rand.nextInt(mapa.getFilas());
            y = rand.nextInt(mapa.getColumnas());
        } while (!mapa.isFree(x, y) || (x == posicionActual.x && y == posicionActual.y));

        setObjetivo(new Point(x, y)); // Establecer el objetivo usando el método setter.
    }

    private void moverAgente() {
        List<Point> moves = getPossibleMoves(posicionActual);
        Point bestMove = getBestMove(moves);

        if (bestMove != null) {
            setPosicionActual(bestMove);
        }
    }
    
    private class ComportamientoMoverse extends OneShotBehaviour {
    
        public void action() {
            moverAgente();
        }
    }

    // Devuelve los posibles movimientos desde una posición dada.
    public List<Point> getMoves() {
        return getPossibleMoves(posicionActual);
    }
    
    // Devuelve los posibles movimientos desde una posición dada.
    private List<Point> getPossibleMoves(Point point) {
        List<Point> moves = new ArrayList<>();
        int x = (int) point.getX();
        int y = (int) point.getY();

        if (x > 0 && mapa.isFree(x-1, y)) moves.add(new Point(x-1, y));
        if (x < mapa.getFilas() - 1 && mapa.isFree(x+1, y)) moves.add(new Point(x+1, y));
        if (y > 0 && mapa.isFree(x, y-1)) moves.add(new Point(x, y-1));
        if (y < mapa.getColumnas() - 1 && mapa.isFree(x, y+1)) moves.add(new Point(x, y+1));

        return moves;
    }

    // Devuelve la dirección que acerca más al objetivo desde una lista de movimientos posibles.
    private Point getBestMove(List<Point> moves) {
        Point bestMove = null;
        double bestDistance = Double.MAX_VALUE;

        for (Point move : moves) {
            double distance = move.distance(getObjetivo());
            if (distance < bestDistance) {
                bestDistance = distance;
                bestMove = move;
            }
        }

        return bestMove;
    }
}
