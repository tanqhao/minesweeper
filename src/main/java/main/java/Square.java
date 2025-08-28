package main.java;

public class Square {
    boolean hasMine;
    boolean revealed;
    int adjacentMines;

    @Override
    public String toString() {
        if(!revealed) {
            return "_";
        }

        if(hasMine) {
            return "*";
        }

        return String.valueOf(adjacentMines);
    }

    public boolean isHasMine() {
        return hasMine;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public int getAdjacentMines() {
        return adjacentMines;
    }
}
