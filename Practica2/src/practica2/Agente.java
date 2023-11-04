package practica2;

import jade.core.Agent;
import java.awt.Point;
import java.awt.*;

public class Agente extends Agent {
    private Point posicionActual;
    private Point objetivo;
    private Entorno entorno;
    
    // Constructor
    public Agente(Entorno entorno, int startX, int startY, Point objetivo) {
        this.entorno = entorno;
        this.posicionActual = new Point(startX, startY);
        this.objetivo = objetivo; // Suponemos que la posición del objetivo es conocida por el agente
    }
    
    // Este método se llamará para mover el agente hacia el objetivo
    public void moveToObjetivo() {
        // Moverse horizontalmente hacia el objetivo si es posible
        if (posicionActual.x < objetivo.x && entorno.isFree(posicionActual.x + 1, posicionActual.y)) {
            posicionActual.x++;
        } else if (posicionActual.x > objetivo.x && entorno.isFree(posicionActual.x - 1, posicionActual.y)) {
            posicionActual.x--;
        }
        // Moverse verticalmente hacia el objetivo si es posible
        if (posicionActual.y < objetivo.y && entorno.isFree(posicionActual.x, posicionActual.y + 1)) {
            posicionActual.y++;
        } else if (posicionActual.y > objetivo.y && entorno.isFree(posicionActual.x, posicionActual.y - 1)) {
            posicionActual.y--;
        }
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
