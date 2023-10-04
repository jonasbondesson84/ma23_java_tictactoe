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

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfWins() {
        return numberOfWins;
    }

    public void setNumberOfWins(int numberOfWins) {
        this.numberOfWins = numberOfWins;
    }

    public boolean playerTurn(Board playerBoard) {
     //   markBoard(playerBoard);
        String answerFull;

        int rowIndex = 0;
        int colIndex = 0;
        boolean addedMark = false;
        System.out.println(getName() + ", din tur!");
        System.out.println("Du spelar med " + mark);
        while (!addedMark) {
            do {
                System.out.println("Vilken ruta vill du markera (ex a1)?");
                answerFull = sc.nextLine();
            } while (answerFull.length() != 2);

            rowIndex = playerBoard.getRow(answerFull.charAt(0)) - 1;
            colIndex = Character.getNumericValue(answerFull.charAt(1)) - 1;
            if (rowIndex < playerBoard.getNumbersOfRows() && colIndex < playerBoard.getNumbersOfRows()) { //Kollar om rutan är inom spelplanen.
                if (playerBoard.getBoardArray()[rowIndex][colIndex].equalsIgnoreCase(" ")) { //Kollar så att rutan är ledig.
                    playerBoard.setBoardArrayElement(rowIndex, colIndex, mark);
                    addedMark = true;
                } else {
                    System.out.println("Rutan är tyvärr redan upptagen, försök igen.");
                }
            } else {
                System.out.println("Rutan finns inte, försök igen.");
            }
        }
        playerBoard.printBoard(); //Skriver ut brädet

        return checkIfWin(rowIndex, colIndex, playerBoard, mark); //Kollar om någon har vunnit


    }

    public boolean checkIfWin(int lastMarkRow, int lastMarkCol, Board playerBoard, String mark) {

        if (checkIfWinRow(playerBoard, lastMarkRow) || checkIfWinCol(playerBoard, lastMarkCol)) {
            return true;
        } else {
            return checkIfWinDiagonally(playerBoard, lastMarkRow,lastMarkCol);
        }
    }

    public boolean checkIfWinRow(Board playerBoard, int lastMarkRow) {
        int numbersInRow = 0;
        for (int i = 0; i < playerBoard.getNumbersOfRows(); i++) {  //Kollar ifall man har tillräckligt för att vinna i den raden man senast spelade.
            if (playerBoard.getBoardArrayElement(lastMarkRow, i).equalsIgnoreCase(mark)) {
                numbersInRow++;
                if (numbersInRow >= playerBoard.getNumberToWin()) {
                    return true;
                }
            } else {
                numbersInRow = 0;
            }
        }
        return false;
    }

    public boolean checkIfWinCol(Board playerBoard, int lastMarkCol) {
        int numbersInCol = 0;
        for (int i = 0; i < playerBoard.getNumbersOfRows(); i++) {  //Kollar ifall man har tillräckligt för att vinna i den kolumnen man senast spelade.
            if (playerBoard.getBoardArrayElement(i, lastMarkCol).equalsIgnoreCase(mark)) {
                numbersInCol++;
                if (numbersInCol >= playerBoard.getNumberToWin()) {
                    return true;
                }
            } else { //om nästa
                numbersInCol = 0;
            }
        }
        return false;
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

}
