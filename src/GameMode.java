import java.util.ArrayList;
import java.util.Scanner;

public class GameMode {
    private String GameMode;
    private ArrayList<Player> players = new ArrayList<>();

    Scanner sc = new Scanner(System.in);

    public void setGameMode(String gameMode) {
        GameMode = gameMode;
    }
    public String getGameMode() {
        return GameMode;
    }

    public void singlePlayer() {

        int difficulty;
        players.clear();
        boolean winnerOrDraw;
        Board.setNumbersOfDraw(0);
        boolean playAgain = true;
        Board playerBoard = new Board();
        System.out.println("Välkommen till single player läget!");
        System.out.println("Ange namnet på spelaren:");

        players.add(new Player(sc.nextLine(), "x"));
        System.out.println("Välkommen " + players.get(0).getName() + "! Du kommer spela mot Marvin.");
        players.add(new Player("Marvin", "o"));
        while (playAgain) {
            difficulty = setDifficulty();
            playerBoard.setBoardSize();  //Sets the board size with method

            winnerOrDraw = false; //resets before new game
            playerBoard.resetBoard();
            int numbersOfTurns = 0;

            playerBoard.printBoard();

            while (!winnerOrDraw) { //Players keeps playing until one has won or it is a draw
                winnerOrDraw = gameTurn(numbersOfTurns, playerBoard, difficulty);
            }

            printScores(players);
            playAgain = askToPlayAgain();
        }


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
        Board.setNumbersOfDraw(0);
        boolean winnerOrDraw;
        boolean playAgain = true;
        Board playerBoard = new Board();

        System.out.println("Ange namnet på den första spelaren:");
        players.add(new Player(sc.nextLine(), "x"));
        System.out.println("Ange namnet på den andra spelaren:");
        players.add(new Player(sc.nextLine(), "o"));
        while (playAgain) {
            playerBoard.setBoardSize();  //Sets the board size with method

            winnerOrDraw = false; //resets all before new game
            playerBoard.resetBoard();
            int numbersOfTurns = 0;

            playerBoard.printBoard();

            while (!winnerOrDraw) {  //Players keeps playing until one has won or it is a draw
                winnerOrDraw = gameTurn(numbersOfTurns, playerBoard, 0); //only uses difficulty in single player mode.
            }

            printScores(players);
            playAgain = askToPlayAgain();
        }
    }

    public void printScores(ArrayList<Player> players) {
        for (Player player : players) {
            player.printScore();
        }
        System.out.println(Board.getNumbersOfDraw() + (Board.getNumbersOfDraw() == 1 ? " omgång blev oavgjord" : " omgångar blev oavgjorda"));
    }

    public boolean askToPlayAgain() {
        String answer;
        while(true) { //option to play again
            System.out.println("Vill du spela igen (j/n)?");
            answer = sc.nextLine();
            if (answer.equalsIgnoreCase("n") || answer.equalsIgnoreCase("nej")) {
                return false;
            } else if(answer.equalsIgnoreCase("j") || answer.equalsIgnoreCase("ja")) {
                return true;
            }
        }
    }

    public boolean gameTurn(int numbersOfTurns, Board playerBoard, int difficulty) {
        boolean winnerOrDraw = false;
         //Players keeps playing until one has won or it is a draw
            for (Player player : players) {
                numbersOfTurns++;
                if(getGameMode().equalsIgnoreCase("single-player") && players.get(1) == player) {
                    winnerOrDraw = player.computerTurn(playerBoard, difficulty);
                } else {
                    winnerOrDraw = player.playerTurn(playerBoard);
                }
                if (winnerOrDraw) {
                    player.setNumberOfWins(player.getNumberOfWins() + 1);
                    System.out.println("Vinnare är " + player.getName());
                    break;
                } else if (numbersOfTurns == (playerBoard.getNumbersOfRows() * playerBoard.getNumbersOfRows())) {
                    System.out.println("Det blev oavgjort.");
                    Board.setNumbersOfDraw(Board.getNumbersOfDraw()+1);
                    winnerOrDraw = true;
                    break;
                }
            }
            return winnerOrDraw;

    }

}
