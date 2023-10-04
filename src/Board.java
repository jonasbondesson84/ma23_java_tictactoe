import java.util.ArrayList;

public class Board {
    private String[][] boardArray;
    private int numbersOfRows;
    private int numbersOfSquares;
    private int numberToWin;

    public Board(int numbersOfRows) {
        this.numbersOfRows = numbersOfRows;
        this.numbersOfSquares = numbersOfRows * numbersOfRows;
        this.boardArray = new String[numbersOfRows][numbersOfRows];
        if(numbersOfRows <= 5) {
            this.numberToWin = numbersOfRows;
        } else {
            this.numberToWin = 5;
        }
    }

    public int getNumberToWin() {
        return numberToWin;
    }

    public void setNumberToWin(int numberToWin) {
        this.numberToWin = numberToWin;
    }

    public int getNumbersOfRows() {
        return numbersOfRows;
    }

    public void setNumbersOfRows(int numbersOfRows) {
        this.numbersOfRows = numbersOfRows;
    }

    public int getNumbersOfSquares() {
        return numbersOfSquares;
    }

    public void setNumbersOfSquares(int numbersOfSquares) {
        this.numbersOfSquares = numbersOfSquares;
    }

    public String[][] getBoardArray() {
        return boardArray;
    }

    public void setBoardArray(String[][] boardArray) {
        this.boardArray = boardArray;
    }

    public String getBoardArrayElement(int rowIndex, int colIndex) {
        return this.boardArray[rowIndex][colIndex];
    }

    public void setBoardArrayElement(int rowIndex, int colIndex, String newValue) {
        this.boardArray[rowIndex][colIndex] = newValue;
    }

    public void fillList() {
        for (int i = 0; i < numbersOfRows; i++) {
            for (int j = 0; j < numbersOfRows; j++) {
                boardArray[i][j] = " ";
            }
        }
    }

    public int getRow(char row) {
     //   System.out.println(row-96);
        return row - 96;

    }

    public void createWinList() {

    }

    public void printBoard() {
        for (int i = 1; i <= numbersOfRows; i++) {
            for (int j = 1; j <= numbersOfRows; j++) {
                System.out.print(" " + boardArray[i - 1][j - 1] + " " + (j % numbersOfRows != 0 ? "|" : "\n")); //Prints players marks + vertical borders
            }
            if (i < numbersOfRows) {
                for (int k = 1; k <= numbersOfRows; k++) { // prints horizontal borders
                    System.out.print("---" + (k < numbersOfRows ? "+" : "\n"));
                }
            }
        }
    }

}
