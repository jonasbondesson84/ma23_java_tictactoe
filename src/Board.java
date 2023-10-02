import java.util.ArrayList;

public class Board {
    private ArrayList<String> board = new ArrayList<>();

    private int numbersOfSquares=9;

    public Board( int numbersOfSquares) {
        this.numbersOfSquares = numbersOfSquares;
    }

    public ArrayList<String> getBoard() {
        return board;
    }

    public void setBoard(ArrayList<String> board) {
        this.board = board;
    }

    public int getNumbersOfSquares() {
        return numbersOfSquares;
    }

    public void setNumbersOfSquares(int numbersOfSquares) {
        this.numbersOfSquares = numbersOfSquares;
    }

    public void printBoard() {
        for (int i = 1; i <= numbersOfSquares; i++) {
            System.out.print(" x " + (i % Math.sqrt(numbersOfSquares) !=0 ? "|" : ""));
            if (i % Math.sqrt(numbersOfSquares) == 0 && i<numbersOfSquares) {
                System.out.println();
                for (int j = 1; j <= Math.sqrt(numbersOfSquares); j++) {
                    System.out.print("---" + (j < Math.sqrt(numbersOfSquares) ? "+" : ""));
                }
                System.out.println();

            }
        }
    }
}
