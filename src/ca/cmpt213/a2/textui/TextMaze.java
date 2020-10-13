package ca.cmpt213.a2.textui;

import ca.cmpt213.a2.model.Cell;
import ca.cmpt213.a2.model.Maze;

/**
 * TextMaze handles the output of the Maze
 * It holds the many char elements for the Maze
 */
public class TextMaze {
    private String mazeTitle = "Maze:";
    private char heroElement = '@';
    private char monsterElement = '!';
    private char powerElement = '$';
    private char wallElement = '#';
    private char openElement = ' ';
    private char hiddenElement = '.';
    private char deadElement = 'X';

    public void printMaze(Maze maze, boolean isHidden) {
        Cell[][] mazeArray = maze.getMazeArray();
        int height = maze.getHeight();
        int width = maze.getWidth();

        System.out.println("\n" + mazeTitle);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Cell cell = mazeArray[y][x];
                printElement(cell, isHidden);
            }
            System.out.println();
        }
    }

    private void printElement(Cell cell, boolean isHidden) {
        Cell.Element element = cell.getElement();
        switch (element) {
            case HERO -> {
                System.out.print(heroElement);
            }
            case MONSTER -> {
                System.out.print(monsterElement);
            }
            case POWER -> {
                System.out.print(powerElement);
            }
            case DEAD -> {
                System.out.print(deadElement);
            }
            default -> {
                if (!isHidden || cell.isVisible()) {
                    if (element == Cell.Element.WALL) {
                        System.out.print(wallElement);
                    } else if (element == Cell.Element.OPEN) {
                        System.out.print(openElement);
                    }
                } else {
                    System.out.print(hiddenElement);
                }
            }
        }
    }
}
