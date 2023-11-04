package practica2;

public class Main {
    public static void main(String[] args) {
        Controlador controlador = new Controlador();
        
        // Puedes agregar comandos aquí para cargar el mapa, colocar al agente y al objetivo, etc.
        // Ejemplo:
        controlador.cargarMapa("practica2/Mapas/mapa1.txt"); // Asumiendo que tienes un método cargarMapa implementado
        controlador.colocarObjetivo(5, 5); // Asume que (5, 5) es una posición válida para el objetivo
        controlador.colocarAgente(0, 0); // Asume que (0, 0) es una posición válida para el agente
        
        // Iniciar un bucle de juego o iteraciones si es necesario
        // Ejemplo de un bucle simple:
    
    }
}
