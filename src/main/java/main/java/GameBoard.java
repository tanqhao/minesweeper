package main.java;

import java.util.Random;
import java.util.Scanner;


public class GameBoard {

    private static final int[][] ADJACENT_SQUARES = {
            {-1, -1}, {-1, 0}, {-1, 1},
            { 0, -1}, { 0, 1},
            { 1, -1}, { 1, 0}, { 1, 1}
    };

    private final int rows;
    private final int cols;
    private final int totalMines;
    private final int totalSafeSquare;
    private int squareRevealed;
    private final Square[][] gameGrid;


    // non randomize mine for testing
    public GameBoard(int rows, int cols, String[] mineSquares) {
        this.rows = rows;
        this.cols = cols;
        gameGrid = new Square[rows][cols];
        totalMines = mineSquares.length;
        totalSafeSquare = rows * cols - mineSquares.length;
        squareRevealed = 0;

        createBoard();
        for (String input : mineSquares) {
            int row = input.charAt(0) - 'A';
            int col = Integer.parseInt(input.substring(1)) - 1;
            gameGrid[row][col].hasMine = true;
        }

        computeAdjacentMines();
    }


    public GameBoard(int rows, int cols, int totalMines) {
        this.rows = rows;
        this.cols = cols;
        this.totalMines = totalMines;
        gameGrid = new Square[rows][cols];
        totalSafeSquare = rows * cols - totalMines;
        squareRevealed = 0;

        createBoard();
        placeMines();
        computeAdjacentMines();
    }

    private void createBoard() {
        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
                gameGrid[row][col] = new Square();
            }
        }
    }

    private void placeMines() {
        int noOfMines = totalMines;
        while(noOfMines > 0) {

            Random rand = new Random();
            int row = rand.nextInt(rows);
            int col = rand.nextInt(cols);

            if(!gameGrid[row][col].hasMine) {
                gameGrid[row][col].hasMine = true;
                noOfMines--;
            }
        }
    }

    public boolean selectSquare(String input) {

        int row = input.charAt(0) - 'A';
        int col = Integer.parseInt(input.substring(1)) - 1;

        if(gameGrid[row][col].hasMine) {
            System.out.println("Oh no, you detonated a mine! Game over.");
            return true;
        }

        if(gameGrid[row][col].revealed) {
            System.out.println("Square has already been reveal.");
            return false;
        }

        revealSquares(row, col);

        if(squareRevealed == totalSafeSquare) {
            System.out.println("Congratulations, you have won the game!");
            return true;
        }

        return false;
    }

    private void computeAdjacentMines() {
        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {

                int noOfAdjacentMines = 0;

                if(gameGrid[row][col].hasMine) {
                    continue;
                }

                for(int[] sq: ADJACENT_SQUARES) {
                    int adjacentRow = row + sq[0];
                    int adjacentCol = col + sq[1];

                    if(adjacentRow >= 0 && adjacentRow < rows && adjacentCol >= 0 && adjacentCol < cols) {
                        if(gameGrid[adjacentRow][adjacentCol].hasMine) {
                            noOfAdjacentMines++;
                        }
                    }
                }
                gameGrid[row][col].adjacentMines = noOfAdjacentMines;
            }
        }
    }

    private void revealSquares(int row, int col) {

        if(row < 0 || row >= rows || col < 0 || col >= cols) {
            return;
        }

        Square sq = gameGrid[row][col];

        if(sq.revealed || sq.hasMine) {
            return;
        }

        sq.revealed = true;
        squareRevealed++;

        //  Adjacent square has mines
        if(sq.adjacentMines > 0) {
            return;
        }

        for(int[] d : ADJACENT_SQUARES) {
            revealSquares(row + d[0], col + d[1]);
        }
    }

    public void printBoard(boolean revealAll) {

        System.out.print("  ");
        for(int col = 1; col <= cols; col++) {
            System.out.print(col + " ");
        }
        System.out.println();

        for(int row = 0; row < rows; row++) {
            char rowLabel = (char) ('A' + row);
            System.out.print(rowLabel + " ");

            for(int col = 0; col < cols; col++) {
                Square sq = gameGrid[row][col];
                if(revealAll) {
                    sq.revealed = true;
                }

                // align columns
                if(col >= 10) {
                    System.out.print(" " + sq + " ");
                }
                else {
                    System.out.print(sq + " ");
                }
            }
            System.out.println();
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getTotalMines() {
        return totalMines;
    }

    public Square[][] getGameGrid() {
        return gameGrid;
    }

}
