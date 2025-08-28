package test.java;

import main.java.GameBoard;
import main.java.Square;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameBoardTest {

    private GameBoard gameBoard;

    @BeforeEach
    void setup() {
        gameBoard = new GameBoard(5, 5, new String[]{"A4", "B4", "C4"});
    }

    @Test
    @DisplayName("Board size should matched input")
    void testBoardSize() {
        int boardSize = gameBoard.getRows() * gameBoard.getCols();
        assertEquals(25, boardSize, "Board size should matched");
    }


    @Test
    @DisplayName("Total number of mines should matched input")
    void testTotalMines() {
        int count = 0;

        for (int row = 0; row < gameBoard.getRows(); row++) {
            for (int col = 0; col < gameBoard.getCols(); col++) {

                Square sq = gameBoard.getGameGrid()[row][col];
                if (sq.isHasMine()) {
                    count++;
                }
            }
        }

        assertEquals(3, count, "Total mines should matched.");
    }


    @Test
    @DisplayName("Game should end if mine square selected")
    void testGameOver() {
        boolean gameOver = gameBoard.selectSquare("A4");
        assertTrue(gameOver, "Square should contain mine");
    }

    @Test
    @DisplayName("Game should continue if non mine square selected")
    void testSafeSquare() {
        boolean gameOver = gameBoard.selectSquare("A1");
        assertFalse(gameOver, "Square should not contain mine");
    }

    @Test
    @DisplayName("Test number of adjacent mines for selected squares")
    void testAdjacentSquareMine() {
        assertEquals(2, gameBoard.getGameGrid()[0][2].getAdjacentMines(), "Adjacent mines should matched for A2");
        assertEquals(3, gameBoard.getGameGrid()[1][2].getAdjacentMines(), "Adjacent mines should matched for B2");
        assertEquals(1, gameBoard.getGameGrid()[3][2].getAdjacentMines(), "Adjacent mines should matched for D2");
    }

    @Test
    @DisplayName("Test adjacent square that are revealed if there are 0 adjacent mines")
    void testAdjacentSquareReveal() {

        boolean gameOver = gameBoard.selectSquare("E5");
        assertTrue(gameBoard.getGameGrid()[3][4].isRevealed(), "D5 should be revealed");
        assertFalse(gameBoard.getGameGrid()[0][4].isRevealed(), "A5 should not be revealed");
    }

    @Test
    @DisplayName("Player should win when all non-mine squares are revealed")
    void testGameWin() {
        boolean gameOver = gameBoard.selectSquare("E5");
        assertFalse(gameOver, "Selecting E5 should not end the game");
        gameOver = gameBoard.selectSquare("A5");

        gameOver = gameBoard.selectSquare("B5");
        assertFalse(gameOver, "Selecting B5 should not end the game");

        gameOver = gameBoard.selectSquare("C5");
        assertTrue(gameOver, "Game should be won");
    }
}
