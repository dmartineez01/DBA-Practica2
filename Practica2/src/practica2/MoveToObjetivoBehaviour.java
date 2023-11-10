/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica2;

import jade.core.behaviours.OneShotBehaviour;

/**
 *
 * @author Miguel
 */
public class MoveToObjetivoBehaviour extends OneShotBehaviour {

    Agente ag;

    public MoveToObjetivoBehaviour(Agente a) {
        ag = a;
    }

    public void action() {
        ag.getEntorno().verificarCasillasAdyacentes(ag.getPosicionActual());

        if (ag.getPasos().isEmpty() || !ag.getEntorno().isFree(ag.getPasos().get(0).x, ag.getPasos().get(0).y) ||
                ag.pasoPorRendija(ag.getPosicionActual(), ag.getPasos().get(0).x, ag.getPasos().get(0).y)) {
            System.out.print("Recalculo ruta\n");
            ag.setPasos(ag.obtenerCaminoHasta(ag.getPosicionObjetivo()));
            ag.getPasos().remove(0); //Porque el primer paso que te da es la posición actual
        }

        if (!ag.getPasos().isEmpty()) { //Se establece esta condición porque la unica forma de que esté
            // vacío el array pasos es que haya llegado al objetivo
            System.out.print("Me voy a " + ag.getPasos().get(0) + "\n");
            ag.setPosicionActual(ag.getPasos().get(0));
            ag.getPasos().remove(0);
        }

    }
}
