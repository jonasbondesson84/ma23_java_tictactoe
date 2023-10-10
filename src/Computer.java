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

    public void setDifficulty(Player computer) {
        String choice;
        do {
            System.out.println("Vilken svårighetsgrad vill du ha?");
            System.out.println("1. Enkel");
            System.out.println("2. Svår");
            choice = sc.nextLine();
            switch (choice.toLowerCase()) {
                case "1", "enkel" -> {
                    System.out.println("Du valde svårighetsgrad enkel.");
                    computer.setName("Marvin");
                    this.difficulty = 1;
                }
                case "2", "svår" -> {
                    System.out.println("Du valde svårighetsgrad svår.");
                    computer.setName("Djupa tanken");
                    this.difficulty = 2;
                }
                default -> {
                    System.out.println("Fel input, försök igen.");
                }
            }
        } while (!choice.equalsIgnoreCase("1") && !choice.equalsIgnoreCase("enkel") && !choice.equalsIgnoreCase("2") && !choice.equalsIgnoreCase("svår"));

    }

    public boolean computerTurn(Board playerBoard, Player computer, ArrayList<Player> players) {

        System.out.println("\n" + computer.getName() + ", din tur!");
        System.out.println("Du spelar med " + computer.getMark());

        if (difficulty == 1) {
            computerEasyAction(playerBoard, computer);
        } else if (difficulty == 2) {
            System.out.println("...");
            computerHardAction(playerBoard, computer, players);
        }
        //System.out.println(playerBoard.checkIfWin(player));
        return playerBoard.checkIfWin(computer); //Checks if someone has won.
    }

    public void computerEasyAction(Board playerBoard, Player computer) {
        int rowIndex;
        int colIndex;
        do {
            rowIndex = rand.nextInt(0, playerBoard.getNumbersOfRows());
            colIndex = rand.nextInt(0, playerBoard.getNumbersOfRows());
        } while (!playerBoard.checkIfTileIsFree(rowIndex, colIndex));
        playerBoard.setBoardArrayElement(rowIndex, colIndex, computer.getMark());
        computer.setLastMarkedRow(rowIndex);
        computer.setLastMarkedCol(colIndex);
        playerBoard.printBoard();

    }

    public void computerHardAction(Board playerBoard, Player computer, ArrayList<Player> players) {
        int rowIndex;
        int colIndex;
        if (!checkBestMove(computer, playerBoard, players)) {
            do {
                rowIndex = rand.nextInt(0, playerBoard.getNumbersOfRows());
                colIndex = rand.nextInt(0, playerBoard.getNumbersOfRows());
            } while (!playerBoard.checkIfTileIsFree(rowIndex, colIndex));
            playerBoard.setBoardArrayElement(rowIndex, colIndex, computer.getMark());
            computer.setLastMarkedRow(rowIndex);
            computer.setLastMarkedCol(colIndex);
            playerBoard.printBoard();
        }

    }

    public boolean checkBestMove(Player computer, Board playerBoard, ArrayList<Player> players) {
        for (int i = 1; i <= playerBoard.getNumbersOfRows() - 2; i++) {
            if (checkBestMoveCol(computer, players.get(0), playerBoard, i) || checkBestMoveRow(computer, players.get(0), playerBoard, i) || checkBestMoveDiagonally(computer, players.get(0), playerBoard, i)) {
                return true;
            } else if(checkBestMoveToStopRow(players.get(0), computer, playerBoard, i) || checkBestMoveToStopCol(players.get(0), computer, playerBoard, i) || checkBestMoveToStopDiagonally(players.get(0), computer,playerBoard,i)) {
                return true;
            }
        }
       // System.out.println("Didnt find best move");
        return false;
    }

    public boolean checkBestMoveToStopRow(Player player, Player computer, Board playerBoard, int numberToCheck) {

        int addMarkRow = -1;
        int addMarkCol = -1;
        int numbersOfFree = 0;
        int numbersInRow = 1;

        int markCol = player.getLastMarkedCol() - 1;
        while (markCol >= 0 && (playerBoard.getBoardArrayElement(player.getLastMarkedRow(), markCol) != computer.getMark())) {
            if (playerBoard.getBoardArrayElement(player.getLastMarkedRow(), markCol) == player.getMark()) {
                numbersInRow++;
            } else {
                numbersOfFree++;
                if (numbersOfFree == 1) { //sets the first free spot to set mark on
                    addMarkRow = player.getLastMarkedRow();
                    addMarkCol = markCol;
                }
            }
            markCol--;
        }

        markCol = player.getLastMarkedCol() + 1;
        while (markCol < playerBoard.getNumbersOfRows() && (playerBoard.getBoardArrayElement(player.getLastMarkedRow(), markCol) != computer.getMark())) {

            if (playerBoard.getBoardArrayElement(player.getLastMarkedRow(), markCol) == player.getMark()) {
                numbersInRow++;
            } else {
                numbersOfFree++;
                if (numbersOfFree == 1) { //sets the first free spot to set mark on
                    addMarkRow = player.getLastMarkedRow();
                    addMarkCol = markCol;
                }
            }
            markCol++;
        }
        if(playerBoard.getNumbersOfRows()==3) {
            if (numbersInRow >= 2 && ((numbersInRow + numbersOfFree) >= playerBoard.getNumberToWin())) {
                //  System.out.println("found mark col [" + addMarkRow + ";" + addMarkCol + "]");
                playerBoard.setBoardArrayElement(addMarkRow, addMarkCol, computer.getMark());
                playerBoard.printBoard();
                return true;
            }
        } else {
            if (numbersInRow >= Math.max(playerBoard.getNumberToWin() - numberToCheck, 3) && ((numbersInRow + numbersOfFree) >= playerBoard.getNumberToWin())) {
                //  System.out.println("found mark col [" + addMarkRow + ";" + addMarkCol + "]");
                playerBoard.setBoardArrayElement(addMarkRow, addMarkCol, computer.getMark());
                playerBoard.printBoard();
                return true;
            }
        }
        return false;
    }

    public boolean checkBestMoveToStopCol(Player player, Player computer, Board playerBoard, int numberToCheck) {
        int addMarkRow = -1;
        int addMarkCol = -1;
        int numbersOfFree = 0;
        int numbersInRow = 1;
        int markRow = player.getLastMarkedRow() - 1;

        while (markRow >= 0 && (playerBoard.getBoardArrayElement(markRow, player.getLastMarkedCol()) != computer.getMark())) {
            if (playerBoard.getBoardArrayElement(markRow, player.getLastMarkedCol()) == player.getMark()) {
                numbersInRow++;
            } else {
                numbersOfFree++;
                if (numbersOfFree == 1) { //sets the first free spot to set mark on
                    addMarkRow = markRow;
                    addMarkCol = player.getLastMarkedCol();
                }
            }
            markRow--;
        }

        markRow = player.getLastMarkedRow() + 1;
        while (markRow < playerBoard.getNumbersOfRows() && (playerBoard.getBoardArrayElement(markRow, player.getLastMarkedCol()) != computer.getMark())) {

            if (playerBoard.getBoardArrayElement(markRow, player.getLastMarkedCol()) == player.getMark()) {
                numbersInRow++;
            } else {
                numbersOfFree++;
                if (numbersOfFree == 1) { //sets the first free spot to set mark on
                    addMarkRow = markRow;
                    addMarkCol = player.getLastMarkedCol();
                }
            }
            markRow++;
        }
        if(playerBoard.getNumbersOfRows() == 3) {
            if (numbersInRow >= 2  && ((numbersInRow + numbersOfFree) >= playerBoard.getNumberToWin())) {
                //    System.out.println("found mark col [" + addMarkRow + ";" + addMarkCol + "]");
                playerBoard.setBoardArrayElement(addMarkRow, addMarkCol, computer.getMark());
                playerBoard.printBoard();
                return true;
            }
        } else {
            if (numbersInRow >= Math.max(playerBoard.getNumberToWin() - numberToCheck, 3) && ((numbersInRow + numbersOfFree) >= playerBoard.getNumberToWin())) {
                //    System.out.println("found mark col [" + addMarkRow + ";" + addMarkCol + "]");
                playerBoard.setBoardArrayElement(addMarkRow, addMarkCol, computer.getMark());
                playerBoard.printBoard();
                return true;
            }
        }
        return false;
    }
    public boolean checkBestMoveRow(Player computer, Player player, Board playerBoard, int numberToCheck) {

        int addMarkRow = -1;
        int addMarkCol = -1;
        int numbersOfFree = 0;
        int numbersInRow = 1;

        int markCol = computer.getLastMarkedCol() - 1;
        while (markCol >= 0 && (playerBoard.getBoardArrayElement(computer.getLastMarkedRow(), markCol) != player.getMark())) {
            if (playerBoard.getBoardArrayElement(computer.getLastMarkedRow(), markCol) == computer.getMark()) {
                numbersInRow++;
            } else {
                numbersOfFree++;
                if (numbersOfFree == 1) { //sets the first free spot to set mark on
                    addMarkRow = computer.getLastMarkedRow();
                    addMarkCol = markCol;
                }
            }
            markCol--;
        }

        markCol = computer.getLastMarkedCol() + 1;
        while (markCol < playerBoard.getNumbersOfRows() && (playerBoard.getBoardArrayElement(computer.getLastMarkedRow(), markCol) != player.getMark())) {

            if (playerBoard.getBoardArrayElement(computer.getLastMarkedRow(), markCol) == computer.getMark()) {
                numbersInRow++;
            } else {
                numbersOfFree++;
                if (numbersOfFree == 1) { //sets the first free spot to set mark on
                    addMarkRow = computer.getLastMarkedRow();
                    addMarkCol = markCol;
                }
            }
            markCol++;
        }
        if (numbersInRow >= Math.max(playerBoard.getNumberToWin() - numberToCheck, 2) && ((numbersInRow + numbersOfFree) >= playerBoard.getNumberToWin()) ){
          //  System.out.println("found mark col [" + addMarkRow + ";" + addMarkCol + "]");
            playerBoard.setBoardArrayElement(addMarkRow, addMarkCol, computer.getMark());
            playerBoard.printBoard();
            return true;
        }
        return false;
    }

    public boolean checkBestMoveCol(Player computer,Player player, Board playerBoard, int numberToCheck) {
        int addMarkRow = -1;
        int addMarkCol = -1;
        int numbersOfFree = 0;
        int numbersInRow = 1;
        int markRow = computer.getLastMarkedRow() - 1;

        while (markRow >= 0 && (playerBoard.getBoardArrayElement(markRow, computer.getLastMarkedCol()) != player.getMark())) {
            if (playerBoard.getBoardArrayElement(markRow, computer.getLastMarkedCol()) == computer.getMark()) {
                numbersInRow++;
            } else {
                numbersOfFree++;
                if (numbersOfFree == 1) { //sets the first free spot to set mark on
                    addMarkRow = markRow;
                    addMarkCol = computer.getLastMarkedCol();
                }
            }
            markRow--;
        }

        markRow = computer.getLastMarkedRow() + 1;
        while (markRow < playerBoard.getNumbersOfRows() && (playerBoard.getBoardArrayElement(markRow, computer.getLastMarkedCol()) != player.getMark())) {

            if (playerBoard.getBoardArrayElement(markRow, computer.getLastMarkedCol()) == computer.getMark()) {
                numbersInRow++;
            } else {
                numbersOfFree++;
                if (numbersOfFree == 1) { //sets the first free spot to set mark on
                    addMarkRow = markRow;
                    addMarkCol = computer.getLastMarkedCol();
                }
            }
            markRow++;
        }
        if (numbersInRow >= Math.max(playerBoard.getNumberToWin() - numberToCheck, 2) && ((numbersInRow + numbersOfFree) >= playerBoard.getNumberToWin())){
        //    System.out.println("found mark col [" + addMarkRow + ";" + addMarkCol + "]");
            playerBoard.setBoardArrayElement(addMarkRow, addMarkCol, computer.getMark());
            playerBoard.printBoard();
            return true;
        }
        return false;
    }

    public boolean checkBestMoveDiagonally(Player computer, Player player, Board playerBoard, int numberToCheck) {

        int addMarkRow = -1;
        int addMarkCol = -1;
        int numbersOfFree = 0;
        int numbersInDiagonally = 1;
        int markRow = computer.getLastMarkedRow() - 1;
        int markCol = computer.getLastMarkedCol() -1;

        while (markRow >= 0 && markCol >= 0 && (playerBoard.getBoardArrayElement(markRow, markCol) != player.getMark())) {
            if (playerBoard.getBoardArrayElement(markRow, markCol) == computer.getMark()) {
                numbersInDiagonally++;
            } else {
                numbersOfFree++;
                if (numbersOfFree == 1) { //sets the first free spot to set mark on
                    addMarkRow = markRow;
                    addMarkCol = markCol;
                }
            }
            markRow--;
            markCol--;
        }
        markRow = computer.getLastMarkedRow() + 1;
        markCol = computer.getLastMarkedCol() + 1;


        while (markRow < playerBoard.getNumbersOfRows() && markCol < playerBoard.getNumbersOfRows() && (playerBoard.getBoardArrayElement(markRow, markCol) != player.getMark())) {

            if (playerBoard.getBoardArrayElement(markRow, markCol) == computer.getMark()) {
                numbersInDiagonally++;
            } else {
                numbersOfFree++;
                if (numbersOfFree == 1) { //sets the first free spot to set mark on
                    addMarkRow = markRow;
                    addMarkCol = markCol;
                }
            }
            markRow++;
            markCol++;
        }
        if (numbersInDiagonally >= Math.max(playerBoard.getNumberToWin() - numberToCheck, 2) && ((numbersInDiagonally + numbersOfFree) >= playerBoard.getNumberToWin())){
            //    System.out.println("found mark col [" + addMarkRow + ";" + addMarkCol + "]");
            playerBoard.setBoardArrayElement(addMarkRow, addMarkCol, computer.getMark());
            playerBoard.printBoard();
            return true;
        } else {
             addMarkRow = -1;
            addMarkCol = -1;
             numbersOfFree = 0;
             numbersInDiagonally = 1;
             markRow = computer.getLastMarkedRow() - 1;
             markCol = computer.getLastMarkedCol() +1;

            while (markRow >= 0 && markCol < playerBoard.getNumbersOfRows() && (playerBoard.getBoardArrayElement(markRow, markCol) != player.getMark())) {
                if (playerBoard.getBoardArrayElement(markRow, markCol) == computer.getMark()) {
                    numbersInDiagonally++;
                } else {
                    numbersOfFree++;
                    if (numbersOfFree == 1) { //sets the first free spot to set mark on
                        addMarkRow = markRow;
                        addMarkCol = markCol;
                    }
                }
                markRow--;
                markCol++;
            }
            markRow = computer.getLastMarkedRow() + 1;
            markCol = computer.getLastMarkedCol() - 1;


            while ( markRow < playerBoard.getNumbersOfRows() && markCol >= 0 && (playerBoard.getBoardArrayElement(markRow, markCol) != player.getMark())) {

                if (playerBoard.getBoardArrayElement(markRow, markCol) == computer.getMark()) {
                    numbersInDiagonally++;
                } else {
                    numbersOfFree++;
                    if (numbersOfFree == 1) { //sets the first free spot to set mark on
                        addMarkRow = markRow;
                        addMarkCol = markCol;
                    }
                }
                markRow++;
                markCol--;
            }
            if (numbersInDiagonally >= Math.max(playerBoard.getNumberToWin() - numberToCheck, 2) && ((numbersInDiagonally + numbersOfFree) >= playerBoard.getNumberToWin())) {
                //    System.out.println("found mark col [" + addMarkRow + ";" + addMarkCol + "]");
                playerBoard.setBoardArrayElement(addMarkRow, addMarkCol, computer.getMark());
                playerBoard.printBoard();
                return true;
            }
        }
        return false;
    }

    public boolean checkBestMoveToStopDiagonally(Player player, Player computer, Board playerBoard, int numberToCheck) {

        int addMarkRow = -1;
        int addMarkCol = -1;
        int numbersOfFree = 0;
        int numbersInDiagonally = 1;
        int markRow = player.getLastMarkedRow() - 1;
        int markCol = player.getLastMarkedCol() -1;

        while (markRow >= 0 && markCol >= 0 && (playerBoard.getBoardArrayElement(markRow, markCol) != computer.getMark())) {
            if (playerBoard.getBoardArrayElement(markRow, markCol) == player.getMark()) {
                numbersInDiagonally++;
            } else {
                numbersOfFree++;
                if (numbersOfFree == 1) { //sets the first free spot to set mark on
                    addMarkRow = markRow;
                    addMarkCol = markCol;
                }
            }
            markRow--;
            markCol--;
        }
        markRow = player.getLastMarkedRow() + 1;
        markCol = player.getLastMarkedCol() + 1;


        while (markRow < playerBoard.getNumbersOfRows() && markCol < playerBoard.getNumbersOfRows() && (playerBoard.getBoardArrayElement(markRow, markCol) != computer.getMark())) {

            if (playerBoard.getBoardArrayElement(markRow, markCol) == player.getMark()) {
                numbersInDiagonally++;
            } else {
                numbersOfFree++;
                if (numbersOfFree == 1) { //sets the first free spot to set mark on
                    addMarkRow = markRow;
                    addMarkCol = markCol;
                }
            }
            markRow++;
            markCol++;
        }
        if (numbersInDiagonally >= Math.max(playerBoard.getNumberToWin() - numberToCheck, 2) && ((numbersInDiagonally + numbersOfFree) >= playerBoard.getNumberToWin())){
            //    System.out.println("found mark col [" + addMarkRow + ";" + addMarkCol + "]");
            playerBoard.setBoardArrayElement(addMarkRow, addMarkCol, computer.getMark());
            playerBoard.printBoard();
            return true;
        } else {
            addMarkRow = -1;
            addMarkCol = -1;
            numbersOfFree = 0;
            numbersInDiagonally = 1;
            markRow = player.getLastMarkedRow() - 1;
            markCol = player.getLastMarkedCol() +1;

            while (markRow >= 0 && markCol < playerBoard.getNumbersOfRows() && (playerBoard.getBoardArrayElement(markRow, markCol) != computer.getMark())) {
                if (playerBoard.getBoardArrayElement(markRow, markCol) == player.getMark()) {
                    numbersInDiagonally++;
                } else {
                    numbersOfFree++;
                    if (numbersOfFree == 1) { //sets the first free spot to set mark on
                        addMarkRow = markRow;
                        addMarkCol = markCol;
                    }
                }
                markRow--;
                markCol++;
            }
            markRow = player.getLastMarkedRow() + 1;
            markCol = player.getLastMarkedCol() - 1;


            while ( markRow < playerBoard.getNumbersOfRows() && markCol >= 0 && (playerBoard.getBoardArrayElement(markRow, markCol) != computer.getMark())) {

                if (playerBoard.getBoardArrayElement(markRow, markCol) == player.getMark()) {
                    numbersInDiagonally++;
                } else {
                    numbersOfFree++;
                    if (numbersOfFree == 1) { //sets the first free spot to set mark on
                        addMarkRow = markRow;
                        addMarkCol = markCol;
                    }
                }
                markRow++;
                markCol--;
            }
            if (numbersInDiagonally >= Math.max(playerBoard.getNumberToWin() - numberToCheck, 2) && ((numbersInDiagonally + numbersOfFree) >= playerBoard.getNumberToWin())) {
                //    System.out.println("found mark col [" + addMarkRow + ";" + addMarkCol + "]");
                playerBoard.setBoardArrayElement(addMarkRow, addMarkCol, computer.getMark());
                playerBoard.printBoard();
                return true;
            }
        }
        return false;
    }
}
