package ca.cmpt213.a2.model;

/**
 * Monster class handles the Monster element
 * It holds the position, it's backTrack and if it's dead
 */
public class Monster {
    private boolean isDead = false;
    private int[] position;
    private GameElements.Move backTrack;

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public int[] getPosition() {
        return position;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }

    public GameElements.Move getBackTrack() {
        return backTrack;
    }

    public void movePosition(GameElements.Move move) {
        switch (move) {
            case UP -> {
                position[1]--;
                backTrack = GameElements.Move.DOWN;
            }
            case DOWN -> {
                position[1]++;
                backTrack = GameElements.Move.UP;
            }
            case LEFT -> {
                position[0]--;
                backTrack = GameElements.Move.RIGHT;
            }
            case RIGHT -> {
                position[0]++;
                backTrack = GameElements.Move.LEFT;
            }
            default -> {
                throw new AssertionError();
            }
        }
    }
}
