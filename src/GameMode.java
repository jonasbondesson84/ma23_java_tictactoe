import java.util.ArrayList;
import java.util.Scanner;

public class GameMode {
    private String GameMode;
    private ArrayList<Player> players = new ArrayList<>();
    private Computer computer = new Computer();

    Scanner sc = new Scanner(System.in);

    public void setGameMode(String gameMode) {
        GameMode = gameMode;
    }

    public String getGameMode() {
        return GameMode;
    }

    public void singlePlayer() {

        players.clear();
        boolean winnerOrDraw;
        Board.setNumbersOfDraw(0);
        boolean playAgain = true;
        Board playerBoard = new Board();

        System.out.println("Välkommen till single player läget!");
        System.out.println("Ange namnet på spelaren:");

        players.add(new Player(sc.nextLine(), 'x'));

        players.add(new Player("Marvin", '0'));

        while (playAgain) {
            computer.setDifficulty(players.get(1));
            System.out.println("Välkommen " + players.get(0).getName() + "! Du kommer spela mot " + players.get(1).getName() + ".");
            playerBoard.setBoardSize();  //Sets the board size with method

            winnerOrDraw = false; //resets before new game
            playerBoard.resetBoard();

            playerBoard.printBoard();

            while (!winnerOrDraw) { //Players keeps playing until one has won or it is a draw
                winnerOrDraw = gameTurn(playerBoard, computer, players);
            }

            printScores(players);
            playAgain = askToPlayAgain();
        }
    }


    public void multiPlayer() {

        players.clear();
        Board.setNumbersOfDraw(0);
        boolean winnerOrDraw;
        boolean playAgain = true;
        Board playerBoard = new Board();

        System.out.println("Ange namnet på den första spelaren:");
        players.add(new Player(sc.nextLine(), 'x'));
        System.out.println("Ange namnet på den andra spelaren:");
        players.add(new Player(sc.nextLine(), 'o'));
        while (playAgain) {
            playerBoard.setBoardSize();  //Sets the board size with method

            winnerOrDraw = false; //resets all before new game
            playerBoard.resetBoard();

            playerBoard.printBoard();

            while (!winnerOrDraw) {  //Players keeps playing until one has won or it is a draw
                winnerOrDraw = gameTurn(playerBoard, computer, players); //only uses difficulty in single player mode.
            }

            printScores(players);
            playAgain = askToPlayAgain();
        }
    }
    public boolean gameTurn(Board playerBoard, Computer computer, ArrayList<Player> players) {

        boolean winnerOrDraw = false;
        //Players keeps playing until one has won or it is a draw
        for (Player player : players) {
            if (getGameMode().equalsIgnoreCase("single-player") && players.get(1) == player) { //If it is computers turn in single-player mode
                winnerOrDraw = computer.computerTurn(playerBoard,  player, players);
            } else {
                winnerOrDraw = player.playerTurn(playerBoard); //multiplayer mode
            }

            if (winnerOrDraw) {
                player.setNumberOfWins(player.getNumberOfWins() + 1);
                System.out.println("Vinnare är " + player.getName());
                break;
            } else if (playerBoard.checkIfBoardIsFull()) {
                System.out.println("Det blev oavgjort.");
                Board.setNumbersOfDraw(Board.getNumbersOfDraw() + 1);
                winnerOrDraw = true;
                break;
            }
        }
        return winnerOrDraw;

    }

    public boolean askToPlayAgain() {
        String answer;
        while (true) { //option to play again
            System.out.println("Vill du spela igen (j/n)?");
            answer = sc.nextLine();
            if (answer.equalsIgnoreCase("n") || answer.equalsIgnoreCase("nej")) {
                return false;
            } else if (answer.equalsIgnoreCase("j") || answer.equalsIgnoreCase("ja")) {
                return true;
            }
        }
    }

    public void printScores(ArrayList<Player> players) {
        for (Player player : players) {
            player.printPlayerScore();
        }
        System.out.println(Board.getNumbersOfDraw() + (Board.getNumbersOfDraw() == 1 ? " omgång blev oavgjord" : " omgångar blev oavgjorda"));
    }



}
