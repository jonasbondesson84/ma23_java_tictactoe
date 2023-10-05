import java.util.Scanner;

public class Board {
    static int numbersOfDraw = 0;
    private String[][] boardArray;
    private int numbersOfRows;
    private int numberToWin;
    Scanner sc = new Scanner(System.in);

    public Board() {
    }

    public static int getNumbersOfDraw() {
        return numbersOfDraw;
    }

    public static void setNumbersOfDraw(int numbersOfDraw) {
        Board.numbersOfDraw = numbersOfDraw;
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
                if (numberOfRowsTiles >= 3 && numberOfRowsTiles <= 10) {
                    break;
                }
                System.out.println("Spelplanen måste ha minst 3x3 rutor och max 10x10.");
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
        System.out.println("Brädet kommer nu vara " + getNumbersOfRows() + "x" + getNumbersOfRows() + " rutor.");
        System.out.println("Det krävs " + getNumberToWin() + " i rad för att vinna.");
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

    public void printBoard() {
        System.out.println(printColumnNumbers());
        System.out.println(printBorder());
        for (int i = 1; i <= numbersOfRows; i++) {
            System.out.print((char) (i+96) + " |");
            for (int j = 1; j <= numbersOfRows; j++) {
                System.out.print(" " + boardArray[i - 1][j - 1] + " " + "|" + (j == numbersOfRows  ? "\n" : "")); //Prints players marks + vertical borders
            }
            if (i < numbersOfRows) {
                System.out.print("  |");
                for (int k = 1; k <= numbersOfRows; k++) { // prints horizontal borders
                    System.out.print("---" + (k < numbersOfRows ? "+" : "|\n"));
                }
            }
        }
        System.out.println(printBorder());
    }

    public String printBorder() {
        String border = "   ";
        for(int i = 1; i <= numbersOfRows; i++) {
            border = border.concat((i != numbersOfRows ? "----" : "---"));
        }
        return border;
    }
    public String printColumnNumbers() {
        String columnNumbers= "   ";
        for(int i = 1; i <= numbersOfRows; i++) {
            columnNumbers = columnNumbers.concat(" " + i + "  ");
        }
        return columnNumbers;
    }

}
