package controllers.interfaces;

import models.Cell;
import java.util.List;

/**
 * Interfaz para resolver laberintos.
 */
public interface MazeSolver {
    /**
     * Encuentra un camino desde la celda de inicio hasta la celda de destino.
     *
     * @param grid  Matriz booleana que representa el laberinto.
     * @param start Celda de inicio.
     * @param end   Celda de destino.
     * @return Lista de celdas que representan el camino encontrado.
     */
    List<Cell> getPath(boolean[][] grid, Cell start, Cell end);
}