package controllers.interfaces;

import models.Cell;
import java.util.*;

public class MazeSolverDFS implements MazeSolver {
    @Override
    public List<Cell> getPath(boolean[][] grid, Cell start, Cell end) {
        List<Cell> path = new ArrayList<>();
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        return dfs(grid, start.row, start.col, end, visited, path) ? path : new ArrayList<>();
    }

    private boolean dfs(boolean[][] grid, int row, int col, Cell end, boolean[][] visited, List<Cell> path) {
        if (row < 0 || col < 0 || row >= grid.length || col >= grid[0].length || !grid[row][col] || visited[row][col]) {
            return false;
        }
        if (row == end.row && col == end.col) {
            path.add(new Cell(row, col));
            return true;
        }
        visited[row][col] = true;
        path.add(new Cell(row, col));

        for (int[] dir : new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}}) {
            if (dfs(grid, row + dir[0], col + dir[1], end, visited, path)) return true;
        }

        path.remove(path.size() - 1);
        return false;
    }
}
