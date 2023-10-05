import java.util.Scanner;

public class Player {
    private String name;
    private int numberOfWins;
    private String mark;
    Scanner sc = new Scanner(System.in);

    public Player(String name, String mark) {
        this.name = name;
        this.numberOfWins = 0;
        this.mark = mark;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfWins() {
        return numberOfWins;
    }

    public void setNumberOfWins(int numberOfWins) {
        this.numberOfWins = numberOfWins;
    }

    public boolean playerTurn(Board playerBoard) {
        String answer;
        int rowIndex = 0;
        int colIndex = 0;
        boolean addedMark = false;

        System.out.println("\n" + getName() + ", din tur!");
        System.out.println("Du spelar med " + mark);
        while (!addedMark) {
            do {
                System.out.println("Vilken ruta vill du markera (ex a1)?");
                answer = sc.nextLine();
            } while (answer.length() != 2);
            rowIndex = playerBoard.getRow(answer.charAt(0)) - 1;
            colIndex = Character.getNumericValue(answer.charAt(1)) - 1;
            if (playerBoard.checkIfTileExists(answer)) { //Checks if tile is within board
                if (playerBoard.checkIfTileIsFree(rowIndex, colIndex)) { //checks if tile is free
                    playerBoard.setBoardArrayElement(rowIndex, colIndex, mark);
                    addedMark = true;
                } else {
                    System.out.println("Rutan är tyvärr redan upptagen, försök igen.");
                }
            } else {
                System.out.println("Rutan finns inte, försök igen.");
            }
        }
        playerBoard.printBoard(); //Prints the board
        return checkIfWin(rowIndex, colIndex, playerBoard, mark); //Checks if someone has won.
    }

    public boolean checkIfWin(int lastMarkRow, int lastMarkCol, Board playerBoard, String mark) {

        if (checkIfWinRow(playerBoard, lastMarkRow, lastMarkCol) || checkIfWinCol(playerBoard, lastMarkRow, lastMarkCol)) {
            return true;
        } else {
            return checkIfWinDiagonally(playerBoard, lastMarkRow,lastMarkCol);
        }
    }

    public boolean checkIfWinCol(Board playerBoard, int lastMarkRow, int lastMarkCol) {
        int numbersInCol = 1;
        int markRow = lastMarkRow - 1;
        while(markRow >= 0 && playerBoard.getBoardArrayElement(markRow, lastMarkCol).equalsIgnoreCase(mark)) {
            numbersInCol++;
            markRow--;
        }
        markRow = lastMarkRow + 1;
        while(markRow < playerBoard.getNumbersOfRows() && playerBoard.getBoardArrayElement(markRow, lastMarkCol).equalsIgnoreCase(mark)) {
            numbersInCol++;
            markRow++;
        }
        return (numbersInCol >= playerBoard.getNumberToWin());
    }

    public boolean checkIfWinRow(Board playerBoard, int lastMarkRow, int lastMarkCol) {
        int numbersInRow = 1;
        int markCol = lastMarkCol - 1;
        while(markCol >= 0 && playerBoard.getBoardArrayElement(lastMarkRow, markCol).equalsIgnoreCase(mark)) {
            numbersInRow++;
            markCol--;
        }
        markCol = lastMarkCol + 1;
        while(markCol < playerBoard.getNumbersOfRows() && playerBoard.getBoardArrayElement(lastMarkRow, markCol).equalsIgnoreCase(mark)) {
            numbersInRow++;
            markCol++;
        }
        return (numbersInRow >= playerBoard.getNumberToWin());
    }

    public boolean checkIfWinDiagonally(Board playerBoard, int lastMarkRow, int lastMarkCol) {
        int numbersInDiagonally = 1;
        int markRow = lastMarkRow-1;
        int markCol = lastMarkCol-1;
        while ((markRow >= 0 && markCol >= 0) && playerBoard.getBoardArrayElement(markRow, markCol).equalsIgnoreCase(mark)){
            numbersInDiagonally++;
            markRow--;
            markCol--;
        }
        markRow = lastMarkRow+1;
        markCol = lastMarkCol+1;
        while ((markRow < playerBoard.getNumbersOfRows() && markCol < playerBoard.getNumbersOfRows()) && playerBoard.getBoardArrayElement(markRow, markCol).equalsIgnoreCase(mark)) {
            numbersInDiagonally++;
            markRow++;
            markCol++;
        }
        if(numbersInDiagonally >= playerBoard.getNumberToWin()) {
            return true;
        } else {
            numbersInDiagonally = 1;
            markRow = lastMarkRow-1;
            markCol = lastMarkCol+1;
            while ((markRow >= 0 && markCol < playerBoard.getNumbersOfRows()) && playerBoard.getBoardArrayElement(markRow, markCol).equalsIgnoreCase(mark)){
                numbersInDiagonally++;
                markRow--;
                markCol++;
            }
            markRow = lastMarkRow+1;
            markCol = lastMarkCol-1;
            while ((markRow < playerBoard.getNumbersOfRows() && markCol >= 0) && playerBoard.getBoardArrayElement(markRow, markCol).equalsIgnoreCase(mark)) {
                numbersInDiagonally++;
                markRow++;
                markCol--;
            }
        }
        return (numbersInDiagonally >= playerBoard.getNumberToWin());
    }

    public void printScore() {
        System.out.println(name + " har " + numberOfWins +(numberOfWins == 1 ? " vinst" : " vinster"));
    }
}
