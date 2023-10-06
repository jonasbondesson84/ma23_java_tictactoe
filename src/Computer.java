import java.util.Random;

public class Computer {
    Random rand = new Random();

    public boolean computerTurn(Board playerBoard, int difficulty, Player player) {

        System.out.println("\n" + player.getName() + ", din tur!");
        System.out.println("Du spelar med " + player.getMark());
        if (difficulty == 1) {
            computerEasyAction(playerBoard, player);
        } else if (difficulty == 2) {
            computerHardAction(playerBoard);
        }
        return playerBoard.checkIfWin(player); //Checks if someone has won.
    }
    public void computerEasyAction(Board playerBoard, Player player) {
        int rowIndex;
        int colIndex;
        do {
            rowIndex = rand.nextInt(0, playerBoard.getNumbersOfRows());
            colIndex = rand.nextInt(0, playerBoard.getNumbersOfRows());
        } while (!playerBoard.checkIfTileIsFree(rowIndex, colIndex));
        playerBoard.setBoardArrayElement(rowIndex, colIndex, player.getMark());
        player.setLastMarkedRow(rowIndex);
        player.setLastMarkedCol(colIndex);
        playerBoard.printBoard();

    }
    public void computerHardAction(Board playerBoard) {
        int rowIndex = 0;
        int colIndex = 0;
    }
}
