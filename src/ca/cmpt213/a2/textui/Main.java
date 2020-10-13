package ca.cmpt213.a2.textui;

import ca.cmpt213.a2.model.*;

/**
 * Main class handles the options of the menu options
 * It holds the objects in the TextUI and the main game object
 */
public class Main {

    public static void main(String[] args) {
        Game game = new Game();
        TextMenu textMenu = new TextMenu();
        TextMaze textMaze = new TextMaze();
        UserInput userInput = new UserInput();

        char move;

        game.startGame();
        Maze maze = game.getMaze();

        textMenu.printHelp();
        textMaze.printMaze(maze, true);
        printGameState(game, textMenu);

        while (!game.isGameOver()) {
            userInput.receiveInput();
            move = userInput.getMove();
            switch (move) {
                case '?' ->  {
                    textMenu.printHelp();
                }
                case 'c' -> {
                    game.setMonstersNeeded(1);
                }
                case 'm' -> {
                    textMaze.printMaze(maze, false);
                    printGameState(game, textMenu);
                }
                default -> {
                    // When move is one of four directions
                    GameElements.Move moveElement = userInput.convertInputToMove(move);
                    game.nextRound(moveElement);

                    if (!game.isGameOver()) {
                        if (game.isValidMove()) {
                            textMaze.printMaze(maze, true);
                            printGameState(game, textMenu);
                        } else {
                            userInput.invalidMove();
                        }
                    }
                }
            }
        }

        textMenu.printFinalMessage(game.hasWon());
        textMaze.printMaze(maze, false);
    }

    private static void printGameState(Game game, TextMenu textMenu) {
        int monstersNeeded = game.getMonstersNeeded();
        int powerHeld = game.getPowerHeld();
        int monstersAlive = game.getMonstersAlive();
        textMenu.printState(monstersNeeded, powerHeld, monstersAlive);
    }

}
