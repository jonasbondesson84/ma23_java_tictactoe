import java.util.ArrayList;

public class Board {
    String[] boardArray;
    private int numbersOfRows;
    private int numbersOfSquares;

    public Board(int numbersOfRows) {
        this.numbersOfRows = numbersOfRows;
        this.numbersOfSquares = numbersOfRows * numbersOfRows;
        this.boardArray = new String[numbersOfRows*numbersOfRows];
    }

    public int getNumbersOfSquares() {
        return numbersOfSquares;
    }

    public void setNumbersOfSquares(int numbersOfSquares) {
        this.numbersOfSquares = numbersOfSquares;
    }

    public void fillList() {
        for(int i = 0; i<numbersOfSquares; i++) {
            boardArray[i] = " ";
        }

    }
    public void printBoard() {
        for (int i = 1; i <= numbersOfSquares; i++) {
            System.out.print(" " + boardArray[i-1] + " " + (i % numbersOfRows != 0 ? "|" : "\n")); //Prints players marks + vertical borders
            if (i % numbersOfRows == 0 && i < numbersOfSquares) {
                for (int j = 1; j <= numbersOfRows; j++) { // prints horizontal borders
                    System.out.print("---" + (j < numbersOfRows ? "+" : "\n"));
                }
            }
        }
    }

}
