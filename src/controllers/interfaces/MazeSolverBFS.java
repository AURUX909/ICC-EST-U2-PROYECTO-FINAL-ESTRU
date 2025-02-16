package controllers.interfaces;

import models.Cell;
import java.util.*;

public class MazeSolverBFS implements MazeSolver {
    @Override
    public List<Cell> getPath(boolean[][] grid, Cell start, Cell end) {
        if (!isValid(grid, start) || !isValid(grid, end)) return new ArrayList<>();

        Queue<Cell> queue = new ArrayDeque<>();
        Map<Cell, Cell> parents = new HashMap<>();
        boolean[][] visited = new boolean[grid.length][grid[0].length];

        queue.offer(start);
        visited[start.row][start.col] = true;

        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        while (!queue.isEmpty()) {
            Cell current = queue.poll();
            if (current.equals(end)) return reconstructPath(parents, current);

            for (int[] dir : directions) {
                Cell neighbor = new Cell(current.row + dir[0], current.col + dir[1]);
                if (isValid(grid, neighbor) && !visited[neighbor.row][neighbor.col]) {
                    visited[neighbor.row][neighbor.col] = true;
                    parents.put(neighbor, current);
                    queue.offer(neighbor);
                }
            }
        }
        return new ArrayList<>();
    }

    private List<Cell> reconstructPath(Map<Cell, Cell> parents, Cell end) {
        LinkedList<Cell> path = new LinkedList<>();
        for (Cell current = end; current != null; current = parents.get(current)) {
            path.addFirst(current);
        }
        return path;
    }

    private boolean isValid(boolean[][] grid, Cell cell) {
        return cell.row >= 0 && cell.row < grid.length &&
               cell.col >= 0 && cell.col < grid[0].length &&
               grid[cell.row][cell.col];
    }
}
