package controllers.interfaces;

import models.Cell;
import java.util.*;

public class MazeSolverCache implements MazeSolver {
    @Override
    public List<Cell> getPath(boolean[][] grid, Cell start, Cell end) {
        Map<Cell, Integer> cache = new HashMap<>();
        List<Cell> shortestPath = new ArrayList<>();
        int result = findShortestPath(grid, start, end, new HashSet<>(), cache, shortestPath);
        return result == Integer.MAX_VALUE ? new ArrayList<>() : shortestPath;
    }

    private int findShortestPath(boolean[][] grid, Cell current, Cell end, Set<Cell> visited,
                                 Map<Cell, Integer> cache, List<Cell> path) {
        if (!isValid(grid, current) || visited.contains(current)) {
            return Integer.MAX_VALUE;
        }
        if (current.equals(end)) {
            path.add(current);
            return 0;
        }
        if (cache.containsKey(current)) {
            // Reconstruir el camino desde la caché si ya fue calculado
            path.addAll(reconstructPathFromCache(cache, current, end));
            return cache.get(current);
        }

        visited.add(current);
        path.add(current);

        int minSteps = Integer.MAX_VALUE;
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        for (int[] dir : directions) {
            Cell next = new Cell(current.row + dir[0], current.col + dir[1]);
            if (isValid(grid, next)) {
                List<Cell> tempPath = new ArrayList<>(path);
                int steps = findShortestPath(grid, next, end, new HashSet<>(visited), cache, tempPath);

                if (steps != Integer.MAX_VALUE && steps + 1 < minSteps) {
                    minSteps = steps + 1;
                    path.clear();
                    path.addAll(tempPath);
                }
            }
        }

        visited.remove(current);

        if (minSteps != Integer.MAX_VALUE) {
            cache.put(current, minSteps); // Almacenar el resultado en caché
        }

        return minSteps;
    }

    private List<Cell> reconstructPathFromCache(Map<Cell, Integer> cache, Cell current, Cell end) {
        List<Cell> path = new ArrayList<>();
        Cell step = current;
    
        // Reconstruir el camino desde la caché
        while (!step.equals(end)) {
            path.add(step);
    
            // Buscar la siguiente celda en el camino basándose en la caché
            int minSteps = Integer.MAX_VALUE;
            Cell nextStep = null;
    
            for (int[] dir : new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}}) {
                Cell neighbor = new Cell(step.row + dir[0], step.col + dir[1]);
    
                if (cache.containsKey(neighbor) && cache.get(neighbor) < minSteps) {
                    minSteps = cache.get(neighbor);
                    nextStep = neighbor;
                }
            }
    
            // Si no se encuentra un siguiente paso válido, terminar la reconstrucción
            if (nextStep == null) break;
    
            step = nextStep; // Avanzar al siguiente paso
        }
    
        path.add(end); // Asegurarse de que el destino esté incluido
        return path;
    }

    private boolean isValid(boolean[][] grid, Cell cell) {
        return cell.row >= 0 && cell.row < grid.length &&
               cell.col >= 0 && cell.col < grid[0].length &&
               grid[cell.row][cell.col];
    }
}
