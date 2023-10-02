import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String nextMark;
        String checkInput="0";
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

        System.out.println("Ange namnet på den första spelaren:");
        players.add(new Player(sc.nextLine()));
        System.out.println("Ange namnet på den andra spelaren:");
        players.add(new Player(sc.nextLine()));
        playerBoard.printBoard();

//        while(!win) {
//            for (Player player : players) {
//                player.playerTurn();
//                playerBoard.printBoard();
//                win = player.checkIfWin();
//            }
//
//
//        }

//        for(Player player : players) {
//            System.out.println(player.getName());
//            System.out.println(player.getNumberOfWins());
//        }


    }
}