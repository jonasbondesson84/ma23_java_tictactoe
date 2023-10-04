import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String nextMark;
        String checkInput="0";
        String winner = "";
        int numberOfRowsTiles;
        ArrayList<Player> players = new ArrayList<>();
        boolean win=false;

        System.out.println("Välkommen till TRE-I-RAD!");
        System.out.println("Detta är ett spel för två spelare, man vinner om man får tre likadana tecken i rad.");
        System.out.println("Raderna kan vara horisontella, vertikala samt diagonala.\n");

        System.out.println("Hur många rutor (vertikalt) ska spelplanen ha");
        numberOfRowsTiles = sc.nextInt();
        //numberOfBoardTiles= Integer.parseInt(checkInput);
        Board playerBoard = new Board(numberOfRowsTiles);
        sc.nextLine();
        playerBoard.fillList();
        System.out.println("Brädet kommer nu vara " + playerBoard.getNumbersOfRows() + "x" + playerBoard.getNumbersOfRows() + " rutor.");
        System.out.println("Det krävs " + playerBoard.getNumberToWin() + " i rad för att vinna.");

        System.out.println("Ange namnet på den första spelaren:");
        players.add(new Player(sc.nextLine(), "x"));
        System.out.println("Ange namnet på den andra spelaren:");
        players.add(new Player(sc.nextLine(), "o"));
        playerBoard.printBoard();
//        for(Player player :players) {
//            player.playerTurn(playerBoard);
//        }
        int numbersOfTurns=0;
        while(!win) {

            for (Player player : players) {

                numbersOfTurns++;
                System.out.println(numbersOfTurns);
                System.out.println(playerBoard.getNumbersOfRows()*playerBoard.getNumbersOfRows());
                win = player.playerTurn(playerBoard);
                if(win) {
                    player.setNumberOfWins(player.getNumberOfWins() + 1);
                    winner = player.getName();
                    break;
                } else if(numbersOfTurns == (playerBoard.getNumbersOfRows()* playerBoard.getNumbersOfRows())) {
                    System.out.println("Det blev oavgjort.");
                    win=true;
                    break;
                }


            }


        }
        System.out.println("Vinnare är " + winner);

//        for(Player player : players) {
//            System.out.println(player.getName());
//            System.out.println(player.getNumberOfWins());
//        }


    }
}