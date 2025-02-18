import controllers.interfaces.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import models.*;

public class App {
    public static void main(String[] args) {
        boolean[][] laberinto = {
            {true, true, true, true},
            {false, true, true, true},
            {true, true, false, false},
            {true, true, true, true},
        };
        Maze maze = new Maze(laberinto);
        System.out.println("Laberinto cargado:");
        maze.printMaze();

        Cell start = new Cell(0, 3);
        Cell end = new Cell(3, 3);

        List<MazeSolver> soluciones = Arrays.asList(
            new MazeSolverRecursivo(),
            new MazeSolverBFS(),
            new MazeSolverDFS(),
            new MazeSolverCache()
        );

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("\nSelecciona el algoritmo:");
            for (int i = 0; i < soluciones.size(); i++) {
                System.out.println((i + 1) + ": " + soluciones.get(i).getClass().getSimpleName());
            }
            int opcion = scanner.nextInt();
            if (opcion < 1 || opcion > soluciones.size()) {
                System.out.println("Opción no válida");
                return;
            }

            MazeSolver solver = soluciones.get(opcion - 1);
            List<Cell> path = solver.getPath(laberinto, start, end);

            if (path.isEmpty()) {
                System.out.println("No se encontró camino.");
            } else {
                System.out.println("Camino encontrado:");
                path.forEach(System.out::println);
                maze.printMazeWithPath(path);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}