package practica2;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class Main {

    public static void main(String[] args) {
        jade.core.Runtime runtime = jade.core.Runtime.instance();
        Profile profile = new ProfileImpl();
        AgentContainer container = runtime.createMainContainer(profile);

        try {

            // Creo y ejecuto los agentes
            // Crear y lanzar el tercer agente: AgenteBasico
            AgentController ac1;
            ac1 = container.createNewAgent("agente", Agente.class.getName(), null);
            ac1.start();

        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
        // Puedes agregar comandos aquí para cargar el mapa, colocar al agente y al objetivo, etc.
        // Ejemplo:
//        controlador.cargarMapa("practica2/Mapas/mapa1.txt"); // Asumiendo que tienes un método cargarMapa implementado
//        controlador.colocarObjetivo(18, 18); // Asume que (5, 5) es una posición válida para el objetivo
//        controlador.colocarAgente(0, 0); // Asume que (0, 0) es una posición válida para el agente
//        
        // Iniciar un bucle de juego o iteraciones si es necesario
        // Ejemplo de un bucle simple:
    }
}
