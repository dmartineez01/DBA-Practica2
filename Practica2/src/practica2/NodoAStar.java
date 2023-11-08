/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica2;

import java.awt.Point;

/**
 *
 * @author Miguel
 */
public class NodoAStar {
    Point posicion;
    double g; // Costo real desde el inicio hasta este nodo
    double f; // Costo estimado total (g + heurística)
    NodoAStar padre;

    NodoAStar(Point posicion, double g, double f, NodoAStar padre) {
        this.posicion = posicion;
        this.g = g;
        this.f = f;
        this.padre = padre;
    }}
