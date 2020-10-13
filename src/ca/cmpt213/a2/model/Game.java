package ca.cmpt213.a2.model;

import java.util.Arrays;

/**
 * Game handles the main game logic
 * It holds the Maze and GameElement objects and key information of the game
 * It handles the start and each round of the game and the collision of the objects
 */
public class Game {
    private final Maze maze = new Maze();
    private final GameElements gameElements = new GameElements();
    private boolean isGameOver = false;
    private boolean hasWon = false;
    private int monstersNeeded;
    private int powerHeld;
    private int monstersAlive;
    private boolean isValidMove;

    public Maze getMaze() {
        return maze;
    }

    public boolean isGameOver(){
        return isGameOver;
    }

    public boolean hasWon() {
        return hasWon;
    }

    public int getMonstersNeeded() {
        return monstersNeeded;
    }

    public void setMonstersNeeded(int monstersNeeded) {
        this.monstersNeeded = monstersNeeded;
    }

    public int getPowerHeld() {
        return powerHeld;
    }

    public int getMonstersAlive() {
        return monstersAlive;
    }

    public boolean isValidMove() {
        return isValidMove;
    }

    public void startGame() {
        gameElements.initializeHero(maze);
        gameElements.initializePower(maze);
        gameElements.initializeMonsters(maze);
        monstersNeeded = 3;
        powerHeld = 0;
        monstersAlive = 3;
    }

    public void nextRound(GameElements.Move move) {
        gameElements.moveHero(maze, move);
        Hero hero = gameElements.getHero();

        // Hero hit Wall
        if (hero.getPreviousPosition() == hero.getPosition()) {
            isValidMove = false;
            return;
        }

        checkHeroMonster();

        // Powerless Hero step on Monster
        if (isGameOver) {
            return;
        }

        gameElements.moveMonsters(maze);
        checkHeroMonster();
        checkHeroPower();
        powerHeld = hero.getPower();

        // Win Game
        if (monstersNeeded <= 3 - monstersAlive) {
            hasWon = true;
            isGameOver = true;
        }

        gameElements.refreshPower(maze);
        isValidMove = true;
    }

    private void checkHeroMonster() {
        Hero hero = gameElements.getHero();
        Monster[] monsters = gameElements.getMonsters();
        Cell[][] mazeArray = maze.getMazeArray();
        int[] heroPosition = hero.getPosition();
        int xHero = heroPosition[0];
        int yHero = heroPosition[1];

        for (Monster monster : monsters) {
            if (!monster.isDead() && Arrays.equals(heroPosition, monster.getPosition())) {
                if (hero.getPower() > 0) {
                    hero.losePower();
                    monstersAlive--;
                    monster.setDead(true);
                    mazeArray[yHero][xHero].setElement(Cell.Element.HERO);
                } else {
                    mazeArray[yHero][xHero].setElement(Cell.Element.DEAD);
                    isGameOver = true;
                }
            }
        }
    }

    private void checkHeroPower() {
        Hero hero = gameElements.getHero();
        Power power = gameElements.getPower();

        if (Arrays.equals(hero.getPosition(), power.getPosition())) {
            hero.gainPower();
            gameElements.initializePower(maze);
        }

    }
}
