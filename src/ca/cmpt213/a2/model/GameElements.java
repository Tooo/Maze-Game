package ca.cmpt213.a2.model;

import java.util.*;

/**
 * GameElements handles the connection of the Maze and Elements
 * It holds the Hero, Power and Monster and have a public enum Move
 * It handles the initializing and moving the Elements to reflect on Maze
 */
public class GameElements {
    private final Hero hero = new Hero();
    private final Monster[] monsters = {new Monster(), new Monster(), new Monster()};
    private final Power power = new Power();
    public enum Move {UP, DOWN, LEFT, RIGHT}

    public Hero getHero() {
        return hero;
    }

    public Monster[] getMonsters() {
        return monsters;
    }

    public Power getPower() {
        return power;
    }

    public void initializeHero(Maze maze) {
        Cell[][] mazeArray = maze.getMazeArray();
        int[] position = hero.getPosition();

        mazeArray[position[1]][position[0]].setElement(Cell.Element.HERO);
        heroSurroundView(maze);
    }

    public void moveHero(Maze maze, Move move) {
        Cell[][] mazeArray = maze.getMazeArray();
        int[] previousPosition = hero.getPosition().clone();
        hero.setPreviousPosition(previousPosition);
        hero.movePosition(move);
        int[] position = hero.getPosition();

        // Allow Hero movement when not on wall
        if (mazeArray[position[1]][position[0]].getElement() == Cell.Element.WALL) {
            hero.setPosition(previousPosition);
        } else {
            mazeArray[previousPosition[1]][previousPosition[0]].setElement(Cell.Element.OPEN);
            mazeArray[position[1]][position[0]].setElement(Cell.Element.HERO);
            heroSurroundView(maze);
        }
    }

    private void heroSurroundView(Maze maze) {
        int[] position = hero.getPosition();
        Cell[][] mazeArray = maze.getMazeArray();

        for (int y = position[1]-1; y < position[1]+2; y++) {
            for (int x = position[0]-1; x < position[0]+2; x++) {
                mazeArray[y][x].setVisible(true);
            }
        }
    }

    public void initializeMonsters(Maze maze) {
        Cell[][] mazeArray = maze.getMazeArray();
        int width = maze.getWidth();
        int height = maze.getHeight();
        int[][] positions = {{width-2, 1}, {1, height-2}, {width-2, height-2}};

        for (int i = 0; i < 3; i++) {
            monsters[i].setPosition(positions[i]);
            mazeArray[positions[i][1]][positions[i][0]].setElement(Cell.Element.MONSTER);
        }
    }

    public void moveMonsters(Maze maze) {
        Random random = new Random();
        Cell[][] mazeArray = maze.getMazeArray();
        boolean isAllowed = false;
        Move move;

        for (int i = 0; i < 3; i++) {
            Monster monster = monsters[i];
            if (!monster.isDead()) {
                ArrayList<Move> moves = new ArrayList<>();
                Collections.addAll(moves, Move.UP, Move.DOWN, Move.LEFT, Move.RIGHT);
                Move backTrack = monster.getBackTrack();
                moves.remove(backTrack);

                while (!isAllowed) {
                    if (moves.size() == 0) {
                        move = backTrack;
                    } else {
                        int randomNumber = random.nextInt(moves.size());
                        move = moves.get(randomNumber);
                        moves.remove(move);
                    }

                    int[] previousPosition = monster.getPosition().clone();
                    monster.movePosition(move);
                    int[] position = monster.getPosition();

                    // Allow Monster movement when not on wall
                    if (mazeArray[position[1]][position[0]].getElement() == Cell.Element.WALL) {
                        monster.setPosition(previousPosition);
                    } else {
                        mazeArray[previousPosition[1]][previousPosition[0]].setElement(Cell.Element.OPEN);
                        mazeArray[position[1]][position[0]].setElement(Cell.Element.MONSTER);
                        isAllowed = true;
                    }
                }
                isAllowed = false;
            }
        }

    }

    public void initializePower(Maze maze) {
        Random random = new Random();
        Cell[][] mazeArray = maze.getMazeArray();
        int width = maze.getWidth();
        int height = maze.getHeight();
        boolean isAllowed = false;

        // Randomly place Power that's not on Wall/Hero
        while (!isAllowed) {
            int x = random.nextInt(width - 1);
            int y = random.nextInt(height - 1);
            Cell cell = mazeArray[y][x];

            if (cell.getElement() != Cell.Element.WALL
                    && cell.getElement() != Cell.Element.HERO) {
                power.setPosition(new int[]{x, y});
                cell.setElement(Cell.Element.POWER);
                isAllowed = true;
            }
        }
    }

    public void refreshPower(Maze maze) {
        Cell[][] mazeArray = maze.getMazeArray();
        int[] position = power.getPosition();
        Cell cell = mazeArray[position[1]][position[0]];

        // Show Power when Hero/Monster not on it
        if (cell.getElement() != Cell.Element.HERO
                && cell.getElement() != Cell.Element.MONSTER) {
            cell.setElement(Cell.Element.POWER);
        }

    }
}
