package practica2;

import java.awt.*;

public class Controlador {
    private Mapa mapa;
    private Agente agente;
    private Interfaz interfaz;
    private Entorno entorno;
    
    private Point objetivo;
    
    public Controlador() {
        cargarMapa("practica2/Mapas/mapa1.txt");
        entorno = new Entorno(mapa);
        
        objetivo = new Point(18, 18);
        agente = new Agente(entorno, 0, 0, objetivo); // Asumiendo que la posición inicial del agente es (0, 0) y el objetivo es (10, 10).
        mapa.setObjetivo(objetivo.x, objetivo.y);  // Establece el objetivo en el mapa.
        
        interfaz = new Interfaz(this); // Ahora la interfaz puede asumir que agente y objetivo están disponibles.
        
        // No necesitas establecer la posición del agente y del objetivo aquí, se hará en la interfaz.
        interfaz.actualizarInfo();
        interfaz.setVisible(true);
        
    } 
    
    
    public void cargarMapa(String rutaArchivo) {
        
        mapa = new Mapa(rutaArchivo);
        
    }
    
    public void actualizarMapa(String rutaArchivo){
        
        mapa = new Mapa(rutaArchivo);
        entorno.setMapa(mapa);
    }
    
    public void colocarObjetivo(int x, int y) {
        
        if ((mapa.isFree(x, y) || mapa.isObjetivo(x, y)) && x >= 0 && x < mapa.getColumnas() && y >= 0 && y < mapa.getFilas()) {
            objetivo = new Point(x,y);
            mapa.setObjetivo(x, y);
            // Ya no es necesario llamar a un método específico para actualizar la posición del objetivo en la interfaz.
            actualizarInterfaz();
        }
        else {
            System.out.print("No se puede colocar al objetivo en esas coordenadas");
        }
        
    }

    public void colocarAgente(int x, int y) {
        // Asumiendo que el objetivo ya está establecido en el mapa
        Point objetivo = mapa.getObjetivo();
        System.out.print("CONTROLADOR COLOCA OBJETIVO EN: ");
        System.out.print(objetivo);
        
        if ((mapa.isFree(x, y) || mapa.isObjetivo(x, y)) && x >= 0 && x < mapa.getColumnas() && y >= 0 && y < mapa.getFilas()) {
            agente = new Agente(entorno, x, y, objetivo);
            // Ya no es necesario llamar a un método específico para actualizar la posición del agente en la interfaz.
            actualizarInterfaz();
        } else {
            System.out.print("No se puede colocar al agente en esas coordenadas");
        }
    }

    public void iterarAgente() {
        // Mover el agente
        agente.moveToObjetivo();
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
        new Controlador();
    }
}
