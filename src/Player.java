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
        int answer = 0;
        int answer1 = 0;
        boolean addedMark = false;
        System.out.println(getName() + ", din tur!");
        System.out.println("Du spelar med " + mark);
        while(!addedMark) {
            System.out.println("Vilken ruta vill du markera?");
            answer = sc.nextInt();
            sc.nextLine();
            System.out.println("Vilken ruta vill du markera?");
            answer1 = sc.nextInt();
            sc.nextLine();
            if(answer-1 < playerBoard.getNumbersOfRows() || answer1-1 < playerBoard.getNumbersOfRows() ) { //Kollar om rutan finns.
                if (playerBoard.getBoardArray()[answer - 1][answer1-1].equalsIgnoreCase(" ")) { //Kollar så att rutan är ledig.
                    playerBoard.setBoardArrayElement(answer - 1, answer1-1, mark);
                    addedMark = true;
                } else {
                    System.out.println("Rutan är tyvärr redan upptagen, försök igen.");
                }
            } else {
                System.out.println("Rutan finns inte, försök igen.");
            }
        }
        playerBoard.printBoard(); //Skriver ut brädet
        if(checkIfWin(answer-1, playerBoard)) { //Kollar om någon har vunnit
            return true;
        }
        return false;


    }

    public boolean checkIfWin(int lastMark, Board playerBoard) {


        return false;
    }
}
