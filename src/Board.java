import java.util.Scanner;

public class Board {
    static int numbersOfDraw = 0;
    private char[][] boardArray;
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

    public char[][] getBoardArray() {
        return boardArray;
    }

    public char getBoardArrayElement(int rowIndex, int colIndex) {
        return this.boardArray[rowIndex][colIndex];
    }

    public void setBoardArrayElement(int rowIndex, int colIndex, char newValue) {
        this.boardArray[rowIndex][colIndex] = newValue;
    }

    public void resetBoard() {
        for (int i = 0; i < numbersOfRows; i++) {
            for (int j = 0; j < numbersOfRows; j++) {
                boardArray[i][j] = ' ';
            }
        }
    }

    public int getRowNumberFromChar(char row) {
        return row - 96;
    }

    public void setBoardSize() {
        String checkString;
        int numberOfRows;
        while (true) {
            System.out.println("Hur många rutor (vertikalt) ska spelplanen ha");
            try {
                checkString = sc.nextLine();
                numberOfRows = Integer.parseInt(checkString);
                if (numberOfRows >= 3 && numberOfRows <= 10) {
                    break;
                }
                System.out.println("Spelplanen måste ha minst 3x3 rutor och max 10x10.");
            } catch (Exception e) {
                System.out.println("Fel input.");
            }
        }
        this.numbersOfRows = numberOfRows;
        this.boardArray = new char[numbersOfRows][numbersOfRows];
        if (numbersOfRows <= 5) {
            this.numberToWin = numbersOfRows;
        } else {
            this.numberToWin = 5;
        }
        System.out.println("Brädet kommer nu vara " + getNumbersOfRows() + "x" + getNumbersOfRows() + " rutor.");
        System.out.println("Det krävs " + getNumberToWin() + " i rad för att vinna. \n");
    }

    public void printBoard() {
        System.out.println(printColumnNumbers());
        System.out.println(printBorder());
        for (int i = 1; i <= numbersOfRows; i++) {
            System.out.print((char) (i + 96) + " |");
            for (int j = 1; j <= numbersOfRows; j++) {
                System.out.print(" " + boardArray[i - 1][j - 1] + " " + "|" + (j == numbersOfRows ? "\n" : "")); //Prints players marks + vertical borders
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
        for (int i = 1; i <= numbersOfRows; i++) {
            border = border.concat((i != numbersOfRows ? "----" : "---"));
        }
        return border;
    }

    public String printColumnNumbers() {
        String columnNumbers = "   ";
        for (int i = 1; i <= numbersOfRows; i++) {
            columnNumbers = columnNumbers.concat(" " + i + "  ");
        }
        return columnNumbers;
    }

    public boolean checkIfTileExists(String answer) {
        int rowIndex;
        int colIndex;
        while (true) {
            try {
                rowIndex = getRowNumberFromChar(answer.charAt(0)) - 1;
                colIndex = Character.getNumericValue(answer.charAt(1)) - 1;
                return (rowIndex < getNumbersOfRows() && colIndex < getNumbersOfRows() && rowIndex >= 0 && colIndex >= 0);
            } catch (Exception e) {
                System.out.println("Fel input, försök igen.");
                answer = sc.nextLine();
            }
        }
    }

    public boolean checkIfTileIsFree(int rowIndex, int colIndex) {
        return (getBoardArray()[rowIndex][colIndex] == ' ');
    }

    public boolean checkIfBoardIsFull() {
        for (int i = 0; i < numbersOfRows; i++) {
            for (int j = 0; j < numbersOfRows; j++) {
                if (getBoardArrayElement(i, j) == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkIfWin(Player player) {
        if (checkIfWinRow(player) || checkIfWinCol(player) || checkIfWinDiagonally(player)) {
            return true;
        }
        return false;
    }

    public boolean checkIfWinCol(Player player) {
        int numbersInCol = 1;
        int markRow = player.getLastMarkedRow() - 1;
        while (markRow >= 0 && getBoardArrayElement(markRow, player.getLastMarkedCol()) == player.getMark()) {
            numbersInCol++;
            markRow--;
        }
        markRow = player.getLastMarkedRow() + 1;
        while (markRow < getNumbersOfRows() && getBoardArrayElement(markRow, player.getLastMarkedCol()) == player.getMark()) {
            numbersInCol++;
            markRow++;
        }
        return (numbersInCol >= getNumberToWin());
    }

    public boolean checkIfWinRow(Player player) {
        int numbersInRow = 1;
        int markCol = player.getLastMarkedCol() - 1;
        while (markCol >= 0 && getBoardArrayElement(player.getLastMarkedRow(), markCol) == player.getMark()) {
            numbersInRow++;
            markCol--;
        }
        markCol = player.getLastMarkedCol() + 1;
        while (markCol < getNumbersOfRows() && getBoardArrayElement(player.getLastMarkedRow(), markCol) == player.getMark()) {
            numbersInRow++;
            markCol++;
        }
        return (numbersInRow >= getNumberToWin());
    }

    public boolean checkIfWinDiagonally(Player player) {
        int numbersInDiagonally = 1;
        int markRow = player.getLastMarkedRow() - 1;
        int markCol = player.getLastMarkedCol() - 1;
        //Checks how many in a row you have to North West
        while ((markRow >= 0 && markCol >= 0) && getBoardArrayElement(markRow, markCol) == player.getMark()) {
            numbersInDiagonally++;
            markRow--;
            markCol--;
        }
        markRow = player.getLastMarkedRow() + 1;
        markCol = player.getLastMarkedCol() + 1;
        //checks how many in a row you have to South East
        while ((markRow < getNumbersOfRows() && markCol < getNumbersOfRows()) && getBoardArrayElement(markRow, markCol) == player.getMark()) {
            numbersInDiagonally++;
            markRow++;
            markCol++;
        }
        if (numbersInDiagonally >= getNumberToWin()) {
            return true;
        } else {
            numbersInDiagonally = 1;
            markRow = player.getLastMarkedRow() - 1;
            markCol = player.getLastMarkedCol() + 1;
            //checks how man in a row you have to North East
            while ((markRow >= 0 && markCol < getNumbersOfRows()) && getBoardArrayElement(markRow, markCol) == player.getMark()) {
                numbersInDiagonally++;
                markRow--;
                markCol++;
            }
            markRow = player.getLastMarkedRow() + 1;
            markCol = player.getLastMarkedCol() - 1;
            //checks how many in a row you have to South West
            while ((markRow < getNumbersOfRows() && markCol >= 0) && getBoardArrayElement(markRow, markCol) == player.getMark()) {
                numbersInDiagonally++;
                markRow++;
                markCol--;
            }
        }
        return (numbersInDiagonally >= getNumberToWin());
    }
}
