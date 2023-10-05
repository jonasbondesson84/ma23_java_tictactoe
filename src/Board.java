import java.util.Scanner;

public class Board {
    private String[][] boardArray;
    private int numbersOfRows;
    private int numberToWin;
    Scanner sc = new Scanner(System.in);

    public Board() {
    }
    public int getNumberToWin() {
        return numberToWin;
    }
    public int getNumbersOfRows() {
        return numbersOfRows;
    }
    public String[][] getBoardArray() {
        return boardArray;
    }
    public String getBoardArrayElement(int rowIndex, int colIndex) {
        return this.boardArray[rowIndex][colIndex];
    }

    public void setBoardArrayElement(int rowIndex, int colIndex, String newValue) {
        this.boardArray[rowIndex][colIndex] = newValue;
    }

    public void resetBoard() {
        for (int i = 0; i < numbersOfRows; i++) {
            for (int j = 0; j < numbersOfRows; j++) {
                boardArray[i][j] = " ";
            }
        }
    }

    public int getRow(char row) {
        return row - 96;
    }

    public void setBoardSize() {
        String checkString;
        int numberOfRowsTiles;
        while (true) {
            System.out.println("Hur många rutor (vertikalt) ska spelplanen ha");
            try {
                checkString = sc.nextLine();
                numberOfRowsTiles = Integer.parseInt(checkString);
                if (numberOfRowsTiles >= 3) {
                    break;
                }
                System.out.println("Spelplanen måste ha minst 3x3 rutor.");
            } catch (Exception e) {
                System.out.println("Fel input.");
            }
        }
        this.numbersOfRows = numberOfRowsTiles;
        this.boardArray = new String[numbersOfRows][numbersOfRows];
        if(numbersOfRows <= 5) {
            this.numberToWin = numbersOfRows;
        } else {
            this.numberToWin = 5;
        }
    }


    public boolean checkIfTileExists(String answer) {
        int rowIndex;
        int colIndex;
        while (true) {
            try {
                rowIndex = getRow(answer.charAt(0)) - 1;
                colIndex = Character.getNumericValue(answer.charAt(1)) - 1;
                return (rowIndex < getNumbersOfRows() && colIndex < getNumbersOfRows() && rowIndex >= 0 && colIndex >= 0);
            }
            catch (Exception e) {
                System.out.println("Fel input, försök igen.");
                answer = sc.nextLine();
            }

        }
    }

    public boolean checkIfTileIsFree(int rowIndex, int colIndex) {
        return (getBoardArray()[rowIndex][colIndex].equalsIgnoreCase(" "));
    }

    public boolean markTile (String answer, int rowIndex, int colIndex, Player player) {
        if(checkIfTileExists(answer)) {
            if(checkIfTileIsFree(rowIndex, colIndex)) {

            }
        }
        return false;
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
