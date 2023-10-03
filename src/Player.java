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
            if (rowIndex < playerBoard.getNumbersOfRows() && colIndex < playerBoard.getNumbersOfRows()) { //Kollar om rutan finns.
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
        boolean check = checkIfWin(rowIndex, colIndex, playerBoard, mark);
        System.out.println(check);
        return check; //Kollar om någon har vunnit



    }

    public boolean checkIfWin( int lastMarkRow, int lastMarkCol, Board playerBoard, String mark) {
        int numbersInRow = 0;
        int numbersInCol = 0;
        System.out.println(mark);
        for(int i = 0; i < playerBoard.getNumbersOfRows(); i++) {
            if(playerBoard.getBoardArrayElement(lastMarkRow, i).equalsIgnoreCase(mark)) {
                numbersInRow++;
            }
            if(playerBoard.getBoardArrayElement(i, lastMarkCol).equalsIgnoreCase(mark)) {
                numbersInCol++;
            }


        }

        return (numbersInCol == 3 || numbersInRow == 3);


    }
}
