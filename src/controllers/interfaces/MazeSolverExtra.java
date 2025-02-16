package controllers.interfaces;

import models.Cell;
import java.util.*;

public class MazeSolverExtra {
    public List<List<Cell>> getAllPaths(boolean[][] grid, Cell start, Cell end) {
        List<List<Cell>> allPaths = new ArrayList<>();
        findAllPaths(grid, start, end, new boolean[grid.length][grid[0].length], new ArrayList<>(), allPaths);
        return allPaths;
    }

    private void findAllPaths(boolean[][] grid, Cell current, Cell end, boolean[][] visited,
                              List<Cell> currentPath, List<List<Cell>> allPaths) {
        if (!isValid(grid, current) || visited[current.row][current.col]) return;

        visited[current.row][current.col] = true;
        currentPath.add(current);

        if (current.equals(end)) {
            allPaths.add(new ArrayList<>(currentPath));
        } else {
            Arrays.stream(new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}})
                  .map(dir -> new Cell(current.row + dir[0], current.col + dir[1]))
                  .forEach(next -> findAllPaths(grid, next, end, visited, currentPath, allPaths));
        }

        visited[current.row][current.col] = false;
        currentPath.remove(currentPath.size() - 1);
    }

    private boolean isValid(boolean[][] grid, Cell cell) {
        return cell.row >= 0 && cell.row < grid.length &&
               cell.col >= 0 && cell.col < grid[0].length &&
               grid[cell.row][cell.col];
    }
}
