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

    public boolean computerTurn(Board playerBoard, Player player, ArrayList<Player> players) {

        System.out.println("\n" + player.getName() + ", din tur!");
        System.out.println("Du spelar med " + player.getMark());

        if (difficulty == 1) {
            computerEasyAction(playerBoard, player);
        } else if (difficulty == 2) {
            System.out.println("...");
            computerHardAction(playerBoard, player, players);
        }
        System.out.println(playerBoard.checkIfWin(player));
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
        int rowIndex;
        int colIndex;
        if(!checkBestMoveCol(player, playerBoard) && !checkBestMoveRow(player, playerBoard)) {
            do {
                rowIndex = rand.nextInt(0, playerBoard.getNumbersOfRows());
                colIndex = rand.nextInt(0, playerBoard.getNumbersOfRows());
            } while (!playerBoard.checkIfTileIsFree(rowIndex, colIndex));
            playerBoard.setBoardArrayElement(rowIndex, colIndex, player.getMark());
            player.setLastMarkedRow(rowIndex);
            player.setLastMarkedCol(colIndex);
            playerBoard.printBoard();
        }

    }

    public boolean checkBestMove() {
        return false;
    }

    public boolean checkBestMoveRow(Player player, Board playerBoard) {
        int addMarkRow = 0;
        int addMarkCol = 0;
        for(int i = 0; i < playerBoard.getNumbersOfRows(); i++) {
            int countMark=0;
            for(int j = 0; j < playerBoard.getNumbersOfRows(); j++) {
                if (playerBoard.getBoardArrayElement(i, j) == player.getMark()) {
                    countMark++;
                } else if (playerBoard.getBoardArrayElement(i, j) == ' ') {
                    addMarkRow = i;
                    addMarkCol = j;
                }
              //  System.out.println("contmark " + countMark +i+j);
                if (countMark >= playerBoard.getNumberToWin() - 1) {
                    System.out.println("fount mark row");
                    playerBoard.setBoardArrayElement(addMarkRow, addMarkCol, player.getMark());
                    playerBoard.printBoard();
                    return true;
                }
            }
        }
//        int numbersInCol = 1;
//        int nextFreeCol = 0;
//        int nextFreeRow = 0;
//        int markCol = player.getLastMarkedCol() - 1;
//
//        while (markCol >= 0) {
//            if (playerBoard.getBoardArrayElement(markCol, player.getLastMarkedRow()) == (player.getMark())) {
//                numbersInCol++;
//                markCol--;
//                System.out.println("if1");
//                break;
//            } else if (playerBoard.getBoardArrayElement(markCol, player.getLastMarkedRow()) == ' ') {
//                nextFreeRow = player.getLastMarkedRow();
//                nextFreeCol = markCol;
//                System.out.println("elseif1");
//                break;
//            }
//        }
//        markCol = player.getLastMarkedCol() + 1;
//        while (markCol < playerBoard.getNumbersOfRows()) {
//            if(playerBoard.getBoardArrayElement(markCol, player.getLastMarkedRow()) == player.getMark()) {
//                numbersInCol++;
//                markCol++;
//                System.out.println("if2");
//                break;
//            } else if(playerBoard.getBoardArrayElement(markCol, player.getLastMarkedRow()) == ' ') {
//                nextFreeRow = player.getLastMarkedRow();
//                nextFreeCol = markCol;
//                System.out.println("elseif2");
//                break;
//            }
//        }
//        if ((numbersInCol >= playerBoard.getNumberToWin() - 1)) {
//            System.out.println("Jag hittade en row");
//            System.out.println(nextFreeRow + " " + nextFreeCol);
//            playerBoard.setBoardArrayElement(nextFreeRow, nextFreeCol, player.getMark());
//            playerBoard.printBoard();
//            return true;
//
//        }
//        return false;
            return false;
    }

    public boolean checkBestMoveCol(Player player, Board playerBoard) {
        int addMarkRow = 0;
        int addMarkCol = 0;
        for (int i = 0; i < playerBoard.getNumbersOfRows(); i++) {
            int countMark = 0;
            for (int j = 0; j < playerBoard.getNumbersOfRows(); j++) {
                if (playerBoard.getBoardArrayElement(j, i) == 'o') {
                    countMark++;
                } else if (playerBoard.getBoardArrayElement(j, i) == ' ') {
                    addMarkRow = j;
                    addMarkCol = i;
                }

            if (countMark >= playerBoard.getNumberToWin() - 1) {
                System.out.println("found mark col");
                playerBoard.setBoardArrayElement(addMarkRow, addMarkCol, player.getMark());
                playerBoard.printBoard();
                return true;
            }
            }
        }
return false;
    }

    public boolean checkBestMoveDiagonally() {
        return false;
    }

}
