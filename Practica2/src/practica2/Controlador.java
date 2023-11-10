package practica2;

import java.awt.*;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class Controlador {

    private Mapa mapa;
    private Agente agente;
    private Interfaz interfaz;
    private Entorno entorno;
    private AgentController agenteController;
    private Point objetivo;
    private AgentContainer container;

//    public Controlador() {
//        
//        cargarMapa("practica2/Mapas/mapa1.txt");
//        entorno = new Entorno(mapa);
//        
//        objetivo = new Point(18, 18);
//        agente = new Agente(entorno, 0, 0, objetivo); // Asumiendo que la posición inicial del agente es (0, 0) y el objetivo es (10, 10).
//        mapa.setObjetivo(objetivo.x, objetivo.y);  // Establece el objetivo en el mapa.
//        
//        interfaz = new Interfaz(this); // Ahora la interfaz puede asumir que agente y objetivo están disponibles.
//        
//        // No necesitas establecer la posición del agente y del objetivo aquí, se hará en la interfaz.
//        interfaz.actualizarInfo();
//        interfaz.setVisible(true);
//        
//    } 
    public Controlador(Agente a) {
        // Primero, inicializar el entorno de JADE y el contenedor
        //iniciarJADE();
        agente = a;
        // Luego, cargar el mapa y configurar el entorno
        cargarMapa("practica2/Mapas/mapa1.txt");
        entorno = new Entorno(mapa);
        agente.setEntorno(entorno);
        objetivo = new Point(18, 18);
        mapa.setObjetivo(objetivo.x, objetivo.y);

        // Crear y configurar el agente
        //crearYConfigurarAgente(0, 0, objetivo); // Asumiendo posiciones iniciales

        // Finalmente, inicializar la interfaz
        inicializarInterfaz();

    }

    private void inicializarInterfaz() {
        interfaz = new Interfaz(this);
        interfaz.actualizarInfo();
        interfaz.setVisible(true);
    }

    public void cargarMapa(String rutaArchivo) {

        mapa = new Mapa(rutaArchivo);

    }

    public void actualizarMapa(String rutaArchivo) {

        mapa = new Mapa(rutaArchivo);
        entorno.setMapa(mapa);
    }

    public void colocarObjetivo(int x, int y) {

        if ((mapa.isFree(x, y) || mapa.isObjetivo(x, y)) && x >= 0 && x < mapa.getColumnas() && y >= 0 && y < mapa.getFilas()) {
            objetivo = new Point(x, y);
            mapa.setObjetivo(x, y);
            // Ya no es necesario llamar a un método específico para actualizar la posición del objetivo en la interfaz.
            actualizarInterfaz();
        } else {
            System.out.print("No se puede colocar al objetivo en esas coordenadas");
        }

    }

    public void colocarAgente(int x, int y) {
        // Asumiendo que el objetivo ya está establecido en el mapa
        Point objetivo = mapa.getObjetivo();
        System.out.print(objetivo);

        if ((mapa.isFree(x, y) || mapa.isObjetivo(x, y)) && x >= 0 && x < mapa.getColumnas() && y >= 0 && y < mapa.getFilas()) {
            //agente = new Agente(entorno, x, y, objetivo);
            agente.setPosicionActual(new Point(x,y));
            agente.setObjetivo(objetivo);
            // Ya no es necesario llamar a un método específico para actualizar la posición del agente en la interfaz.
            actualizarInterfaz();
        } else {
            System.out.print("No se puede colocar al agente en esas coordenadas");
        }
    }
    
    public void resetEntorno(){
        entorno = new Entorno(mapa);
        agente.setEntorno(entorno);
    }

    public void iterarAgente() {
        // Mover el agente
        //agente.moveToObjetivo();
        agente.addBehaviour(new MoveToObjetivoBehaviour(agente));
        // Ya no es necesario llamar a un método específico para actualizar la posición del agente en la interfaz.
        actualizarInterfaz();
    }

    public Point getPosicionObjetivo() {
        return objetivo;
    }

    public Point getPosicionAgente() {
        return agente.getPosicionActual();
    }

    public boolean agenteAlcanzoObjetivo() {
        return agente.getPosicionActual().equals(objetivo);
    }

    public Mapa getMapaActual() {
        return mapa;
    }

    // Método auxiliar para actualizar la interfaz.
    private void actualizarInterfaz() {
        interfaz.actualizarInfo(); // Asume que este método actualizará el mapa y la información del agente y del objetivo.
    }

    public static void main(String[] args) {
        //new Controlador();
    }
}
