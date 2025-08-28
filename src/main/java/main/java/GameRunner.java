package main.java;

import java.util.Scanner;

public class GameRunner {

    public static void startGame(Scanner scanner) {
        boolean playGame = true;

        while(playGame) {
            System.out.println("\nWelcome to Minesweeper!");

            int size = getBoardSizeInput(scanner);
            int totalMines = getTotalMinesInput(scanner, size);

            playRound(scanner, size, totalMines);

            playGame = getRestartInput(scanner);
        }
    }

    private static int getBoardSizeInput(Scanner scanner) {
        int size;
        while(true) {
            try {
                System.out.print("Enter the size of the grid: ");
                size = scanner.nextInt();

                if(size < 2 || size > 26) {
                    System.out.println("Grid size must be between 2 to 26.");
                    continue;
                }

                scanner.nextLine();
                return size;
            } catch(Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
    }

    private static int getTotalMinesInput(Scanner scanner, int size) {
        int totalSquares = size * size;
        int maxMines = (int)Math.floor(totalSquares * 0.35);

        while(true) {
            try {
                System.out.print("Enter the number of mines to place on the grid (maximum is 35% of the total squares): ");
                int totalMines = scanner.nextInt();

                if (totalMines < 1 || totalMines > maxMines) {
                    System.out.println("Invalid number of mines. Please enter between 1 and " + maxMines + ".");
                    continue;
                }

                scanner.nextLine();
                return totalMines;
            } catch(Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
    }

    private static void playRound(Scanner scanner, int size, int totalMines) {
        GameBoard board = new GameBoard(size, size, totalMines);
        boolean gameOver = false;

        while(!gameOver) {
            try {
                board.printBoard(false);

                System.out.print("Select a square to reveal (e.g. A1): ");
                String inputSelectSquare = scanner.nextLine().trim().toUpperCase();

                if(inputSelectSquare.length() < 2) {
                    throw new IllegalArgumentException("Input too short.");
                }

                int row = inputSelectSquare.charAt(0) - 'A';
                int col = Integer.parseInt(inputSelectSquare.substring(1)) - 1;

                if(row < 0 || row >= size) {
                    System.out.println("Invalid row. Must be between A and " + (char) ('A' + size - 1));
                    continue;
                } else if (col < 0 || col >= size) {
                    System.out.println("Invalid column. Must be between 1 and " + size);
                    continue;
                }

                gameOver = board.selectSquare(inputSelectSquare);

                if(gameOver) {
                    board.printBoard(true);
                }

            } catch(NumberFormatException e) {
                System.out.println("Invalid column number. Please select (eg. A1, B1...)");
            } catch(IllegalArgumentException e) {
                System.out.println("Invalid input. Please select (eg. A1, B1...)");
            }
        }
    }

    private static boolean getRestartInput(Scanner scanner) {
        while (true) {
            System.out.print("Do you want to play again (Y / N): ");
            String choice = scanner.nextLine().trim().toUpperCase();

            if(choice.equals("Y")) {
                return true;
            } else if(choice.equals("N")) {
                return false;
            } else {
                System.out.println("Invalid input. Please enter only Y or N.");
            }
        }
    }
}
