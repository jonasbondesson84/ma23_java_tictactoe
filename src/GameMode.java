import java.util.ArrayList;
import java.util.Scanner;

public class GameMode {
    ArrayList<Player> players = new ArrayList<>();
    //    private ArrayList<Player> players = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    public void singlePlayer() {
        boolean keepPlaying = true;
        int difficulty;
        System.out.println("Välkommen till spelet mot dator. Vi börjar med att ställa in svårighetsgrad.");
        do {
            difficulty = setDifficulty();


        } while (keepPlaying);


    }

    public int setDifficulty() {
        String choice;
        while (true) {
            System.out.println("Vilken svårighetsgrad vill du ha?");
            System.out.println("1. Enkel");
            System.out.println("2. Svår");
            choice = sc.nextLine();
            switch (choice.toLowerCase()) {
                case "1", "enkel" -> {
                    System.out.println("Du valde svårighetsgrad enkel.");
                    return 1;
                }
                case "2", "svår" -> {
                    System.out.println("Du valde svårighetsgrad svår.");
                    return 2;
                }
                default -> {
                    System.out.println("Fel input, försök igen.");
                }
            }
        }

    }


    public void multiPlayer() {
        players.clear();
        boolean winnerOrDraw;
        String answer;
        boolean playAgain = true;
        Board playerBoard = new Board();

        System.out.println("Ange namnet på den första spelaren:");
        players.add(new Player(sc.nextLine(), "x"));
        System.out.println("Ange namnet på den andra spelaren:");
        players.add(new Player(sc.nextLine(), "o"));
        while (playAgain) {
            playerBoard.setBoardSize();  //Sets the board size with method

            System.out.println("Brädet kommer nu vara " + playerBoard.getNumbersOfRows() + "x" + playerBoard.getNumbersOfRows() + " rutor.");
            System.out.println("Det krävs " + playerBoard.getNumberToWin() + " i rad för att vinna.");

            winnerOrDraw = false;
            playerBoard.resetBoard();
            int numbersOfTurns = 0;

            playerBoard.printBoard();

            while (!winnerOrDraw) { //Players keeps playing until one has won or it is a draw
                for (Player player : players) {
                    numbersOfTurns++;
                    winnerOrDraw = player.playerTurn(playerBoard);
                    if (winnerOrDraw) {
                        player.setNumberOfWins(player.getNumberOfWins() + 1);
                        System.out.println("Vinnare är " + player.getName());
                        break;
                    } else if (numbersOfTurns == (playerBoard.getNumbersOfRows() * playerBoard.getNumbersOfRows())) {
                        System.out.println("Det blev oavgjort.");
                        winnerOrDraw = true;
                        break;
                    }
                }
            }

            for (Player player : players) { //Prints scores
                player.printScore();
            }

            while(true) { //option to play again
                System.out.println("Vill du spela igen (j/n)?");
                answer = sc.nextLine();
                if (answer.equalsIgnoreCase("n") || answer.equalsIgnoreCase("nej")) {
                    playAgain = false;
                    break;
                } else if(answer.equalsIgnoreCase("j") || answer.equalsIgnoreCase("ja")) {
                    break;
                }
            }
        }
    }

}
