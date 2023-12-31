
import java.util.Scanner;

public class Player {
    private String name;
    private int numberOfWins;
    private char mark;
    private int lastMarkedRow; //Used to track which tile was last marked.
    private int lastMarkedCol; //Used to track which tile was last marked
    Scanner sc = new Scanner(System.in);

    public Player(String name, char mark) {
        this.name = name;
        this.numberOfWins = 0;
        this.mark = mark;
    }

    public char getMark() {
        return mark;
    }

    public int getLastMarkedRow() {
        return lastMarkedRow;
    }

    public void setLastMarkedRow(int lastMarkedRow) {
        this.lastMarkedRow = lastMarkedRow;
    }

    public int getLastMarkedCol() {
        return lastMarkedCol;
    }

    public void setLastMarkedCol(int lastMarkedCol) {
        this.lastMarkedCol = lastMarkedCol;
    }

    public void setName(String name) {
        this.name = name;
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
        int markRowIndex;
        int markColIndex;
        boolean addedMark = false;

        System.out.println("\n" + getName() + ", din tur!");
        System.out.println("Du spelar med " + mark);
        while (!addedMark) {
            do {
                System.out.println("Vilken ruta vill du markera (ex a1)?");
                answer = sc.nextLine();
            } while (answer.length() != 2);
            markRowIndex = playerBoard.getRowNumberFromChar(answer.charAt(0)) - 1;
            markColIndex = Character.getNumericValue(answer.charAt(1)) - 1;
            if (playerBoard.checkIfTileExists(answer)) { //Checks if tile is within board
                if (playerBoard.checkIfTileIsFree(markRowIndex, markColIndex)) { //checks if tile is free
                    playerBoard.setBoardArrayElement(markRowIndex, markColIndex, mark);
                    this.lastMarkedRow = markRowIndex;
                    this.lastMarkedCol = markColIndex;
                    addedMark = true;
                } else {
                    System.out.println("Rutan är tyvärr redan upptagen, försök igen.");
                }
            } else {
                System.out.println("Rutan finns inte, försök igen.");
            }
        }
        playerBoard.printBoard(); //Prints the board
        return playerBoard.checkIfWin(this); //Checks if this player has won.
    }

    public void printPlayerScore() {
        System.out.println(name + " har " + numberOfWins + (numberOfWins == 1 ? " vinst" : " vinster"));
    }
}
