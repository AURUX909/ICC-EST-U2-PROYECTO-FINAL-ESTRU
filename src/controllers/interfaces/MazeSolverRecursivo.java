package controllers.interfaces;

import models.Cell;
import java.util.*;

public class MazeSolverRecursivo implements MazeSolver {
    @Override
    public List<Cell> getPath(boolean[][] grid, Cell start, Cell end) {
        List<Cell> path = new ArrayList<>();
        Set<Cell> visited = new HashSet<>();
        return findPath(grid, start.row, start.col, end, path, visited) ? path : new ArrayList<>();
    }

    private boolean findPath(boolean[][] grid, int row, int col, Cell end, List<Cell> path, Set<Cell> visited) {
        Cell current = new Cell(row, col);
        if (!isValid(grid, current) || visited.contains(current)) return false;

        visited.add(current);
        path.add(current);

        if (current.equals(end)) return true;

        boolean found = Arrays.stream(new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}})
                .anyMatch(dir -> findPath(grid, row + dir[0], col + dir[1], end, path, visited));

        if (!found) {
            path.remove(current);
            visited.remove(current);
        }
        return found;
    }

    private boolean isValid(boolean[][] grid, Cell cell) {
        return cell.row >= 0 && cell.row < grid.length &&
               cell.col >= 0 && cell.col < grid[0].length &&
               grid[cell.row][cell.col];
    }
}
