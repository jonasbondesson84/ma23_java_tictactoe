import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Computer {
    private int difficulty;
    Random rand = new Random();
    Scanner sc = new Scanner(System.in);

    public Computer() {
        this.difficulty = 0;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty() {
        String choice;
        do {
            System.out.println("Vilken svårighetsgrad vill du ha?");
            System.out.println("1. Enkel");
            System.out.println("2. Svår");
            choice = sc.nextLine();
            switch (choice.toLowerCase()) {
                case "1", "enkel" -> {
                    System.out.println("Du valde svårighetsgrad enkel.");
                    this.difficulty = 1;
                }
                case "2", "svår" -> {
                    System.out.println("Du valde svårighetsgrad svår.");
                    this.difficulty = 2;
                }
                default -> {
                    System.out.println("Fel input, försök igen.");
                }
            }
        } while (!choice.equalsIgnoreCase("1") && !choice.equalsIgnoreCase("enkel") && !choice.equalsIgnoreCase("2") && !choice.equalsIgnoreCase("svår"));

    }

    public boolean computerTurn(Board playerBoard, int difficulty, Player player, ArrayList<Player> players) {

        System.out.println("\n" + player.getName() + ", din tur!");
        System.out.println("Du spelar med " + player.getMark());
        if (difficulty == 1) {
            computerEasyAction(playerBoard, player);
        } else if (difficulty == 2) {
            computerHardAction(playerBoard, player, players);
        }
        return playerBoard.checkIfWin(player); //Checks if someone has won.
    }

    public void computerEasyAction(Board playerBoard, Player player) {
        int rowIndex;
        int colIndex;
        do {
            rowIndex = rand.nextInt(0, playerBoard.getNumbersOfRows());
            colIndex = rand.nextInt(0, playerBoard.getNumbersOfRows());
        } while (!playerBoard.checkIfTileIsFree(rowIndex, colIndex));
        playerBoard.setBoardArrayElement(rowIndex, colIndex, player.getMark());
        player.setLastMarkedRow(rowIndex);
        player.setLastMarkedCol(colIndex);
        playerBoard.printBoard();

    }

    public void computerHardAction(Board playerBoard, Player player, ArrayList<Player> players) {
        int rowIndex = 0;
        int colIndex = 0;

    }

    public boolean checkBestMove() {
        return false;
    }

    public boolean checkBestMoveRow() {
        return false;
    }

    public boolean checkBestMoveCol(Player player, Board playerBoard) {
        int numbersInCol = 1;
        int nextFreeCol = 0;
        int nextFreeRow = 0;
        int markRow = player.getLastMarkedRow() - 1;
        while (markRow >= 0) {
            if (playerBoard.getBoardArrayElement(markRow, player.getLastMarkedCol()) == (player.getMark())) {
                numbersInCol++;
                markRow--;
            } else if (playerBoard.getBoardArrayElement(markRow, player.getLastMarkedCol()) == ' ') {
                nextFreeRow = markRow;
                nextFreeCol = player.getLastMarkedCol();
            }
        }
        markRow = player.getLastMarkedRow() + 1;
        while (markRow < playerBoard.getNumbersOfRows()) {
            if(playerBoard.getBoardArrayElement(markRow, player.getLastMarkedCol()) == player.getMark()) {
                numbersInCol++;
                markRow++;
            } else if(playerBoard.getBoardArrayElement(markRow, player.getLastMarkedCol()) == ' ') {
                nextFreeRow = markRow;
                nextFreeCol = player.getLastMarkedCol();
            }
        }
        if ((numbersInCol >= playerBoard.getNumberToWin() - 1)) {
            System.out.println("Jag hittade en row");
            playerBoard.setBoardArrayElement(nextFreeRow, nextFreeCol, player.getMark());
            return true;

        }
        return false;

    }

    public boolean checkBestMoveDiagonally() {
        return false;
    }

}
