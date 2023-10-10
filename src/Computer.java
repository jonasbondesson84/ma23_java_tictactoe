import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Computer {
    private int difficulty;
    Random rand = new Random();
    Scanner sc = new Scanner(System.in);

    private int addMarkRow;
    private int addMarkCol;
    private int numbersOfFree;

    private int NUMBER_TO_STOP_PLAYER = 0;

    public Computer() {
        this.difficulty = 0;
    }

    public int setNUMBER_TO_STOP_PLAYER( int numberToCheck, Board playerBoard) {
        if (playerBoard.getNumbersOfRows() == 3) {
            this.NUMBER_TO_STOP_PLAYER = 2;
        } else {
            this.NUMBER_TO_STOP_PLAYER = Math.max(playerBoard.getNumberToWin() - numberToCheck, 3);
        }
        return this.NUMBER_TO_STOP_PLAYER;
    }

    public void setDifficulty(Player computer) {
        String choice;
        do {
            System.out.println("Vilken svårighetsgrad vill du ha?");
            System.out.println("1. Enkel");
            System.out.println("2. Svår");
            choice = sc.nextLine();
            switch (choice.toLowerCase()) {
                case "1", "enkel" -> {
                    System.out.println("Du valde svårighetsgrad enkel.");
                    computer.setName("Marvin");
                    this.difficulty = 1;
                }
                case "2", "svår" -> {
                    System.out.println("Du valde svårighetsgrad svår.");
                    computer.setName("Djupa tanken");
                    this.difficulty = 2;
                }
                default -> {
                    System.out.println("Fel input, försök igen.");
                }
            }
        } while (!choice.equalsIgnoreCase("1") && !choice.equalsIgnoreCase("enkel") && !choice.equalsIgnoreCase("2") && !choice.equalsIgnoreCase("svår"));

    }

    public boolean computerTurn(Board playerBoard, Player computer, ArrayList<Player> players) {

        System.out.println("\n" + computer.getName() + ", din tur!");
        System.out.println("Du spelar med " + computer.getMark());

        if (difficulty == 1) {
            computerEasyAction(playerBoard, computer);
        } else if (difficulty == 2) {
            computerHardAction(playerBoard, computer, players);
        }
        System.out.println(computer.getName() + ": " + computerInsults());
        return playerBoard.checkIfWin(computer); //Checks if someone has won.
    }

    public void computerEasyAction(Board playerBoard, Player computer) {
        int rowIndex;
        int colIndex;
        do {
            rowIndex = rand.nextInt(0, playerBoard.getNumbersOfRows());
            colIndex = rand.nextInt(0, playerBoard.getNumbersOfRows());
        } while (!playerBoard.checkIfTileIsFree(rowIndex, colIndex));
        playerBoard.setBoardArrayElement(rowIndex, colIndex, computer.getMark());
        computer.setLastMarkedRow(rowIndex);
        computer.setLastMarkedCol(colIndex);
        playerBoard.printBoard();

    }

    public void computerHardAction(Board playerBoard, Player computer, ArrayList<Player> players) {
        int rowIndex;
        int colIndex;
        if (!checkBestMove(computer, playerBoard, players)) {
            do {
                rowIndex = rand.nextInt(0, playerBoard.getNumbersOfRows());
                colIndex = rand.nextInt(0, playerBoard.getNumbersOfRows());
            } while (!playerBoard.checkIfTileIsFree(rowIndex, colIndex));
            playerBoard.setBoardArrayElement(rowIndex, colIndex, computer.getMark());
            computer.setLastMarkedRow(rowIndex);
            computer.setLastMarkedCol(colIndex);
            playerBoard.printBoard();
        }

    }

    public boolean checkBestMove(Player computer, Board playerBoard, ArrayList<Player> players) {
        for (int i = 1; i <= playerBoard.getNumbersOfRows() - 2; i++) {
            if (checkBestMoveCol(computer, players.get(0), playerBoard, i) || checkBestMoveRow(computer, players.get(0), playerBoard, i) || checkBestMoveDiagonally(computer, players.get(0), playerBoard, i)) {
                return true;
            } else if (checkBestMoveToStopRow(players.get(0), computer, playerBoard, i) || checkBestMoveToStopCol(players.get(0), computer, playerBoard, i) || checkBestMoveToStopDiagonally(players.get(0), computer, playerBoard, i)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkBestMoveRow(Player computer, Player player, Board playerBoard, int numberToCheck) {

        this.addMarkRow = -1;
        this.addMarkCol = -1;
        this.numbersOfFree = 0;
        int numbersInRow = 1;

        int markRow = computer.getLastMarkedRow();
        int markCol = computer.getLastMarkedCol() - 1;
        while (markCol >= 0 && (playerBoard.getBoardArrayElement(markRow, markCol) != player.getMark())) {
            numbersInRow = checkNumbersInRow(playerBoard, markRow, markCol, computer, numbersInRow);
            markCol--;
        }

        markCol = computer.getLastMarkedCol() + 1;
        while (markCol < playerBoard.getNumbersOfRows() && (playerBoard.getBoardArrayElement(markRow, markCol) != player.getMark())) {
            numbersInRow = checkNumbersInRow(playerBoard, markRow, markCol, computer, numbersInRow);
            markCol++;
        }
        if (numbersInRow >= Math.max(playerBoard.getNumberToWin() - numberToCheck, 2) && ((numbersInRow + numbersOfFree) >= playerBoard.getNumberToWin())) {
            //  System.out.println("found mark col [" + addMarkRow + ";" + addMarkCol + "]");
            playerBoard.setBoardArrayElement(addMarkRow, addMarkCol, computer.getMark());
            playerBoard.printBoard();
            return true;
        }
        return false;
    }

    public boolean checkBestMoveCol(Player computer, Player player, Board playerBoard, int numberToCheck) {
        this.addMarkRow = -1;
        this.addMarkCol = -1;
        this.numbersOfFree = 0;
        int numbersInRow = 1;

        int markRow = computer.getLastMarkedRow() - 1;
        int markCol = computer.getLastMarkedCol();
        while (markRow >= 0 && (playerBoard.getBoardArrayElement(markRow, computer.getLastMarkedCol()) != player.getMark())) {
            numbersInRow = checkNumbersInRow(playerBoard, markRow, markCol, computer, numbersInRow);
            markRow--;
        }

        markRow = computer.getLastMarkedRow() + 1;
        while (markRow < playerBoard.getNumbersOfRows() && (playerBoard.getBoardArrayElement(markRow, markCol) != player.getMark())) {
            numbersInRow = checkNumbersInRow(playerBoard, markRow, markCol, computer, numbersInRow);
            markRow++;
        }
        if (numbersInRow >= Math.max(playerBoard.getNumberToWin() - numberToCheck, 2) && ((numbersInRow + numbersOfFree) >= playerBoard.getNumberToWin())) {
            //    System.out.println("found mark col [" + addMarkRow + ";" + addMarkCol + "]");
            playerBoard.setBoardArrayElement(addMarkRow, addMarkCol, computer.getMark());
            playerBoard.printBoard();
            return true;
        }
        return false;
    }

    public boolean checkBestMoveDiagonally(Player computer, Player player, Board playerBoard, int numberToCheck) {

        this.addMarkRow = -1;
        this.addMarkCol = -1;
        this.numbersOfFree = 0;
        int numbersInRow = 1;

        int markRow = computer.getLastMarkedRow() - 1;
        int markCol = computer.getLastMarkedCol() - 1;
        while (markRow >= 0 && markCol >= 0 && (playerBoard.getBoardArrayElement(markRow, markCol) != player.getMark())) {
            numbersInRow = checkNumbersInRow(playerBoard, markRow, markCol, computer, numbersInRow);
            markRow--;
            markCol--;
        }

        markRow = computer.getLastMarkedRow() + 1;
        markCol = computer.getLastMarkedCol() + 1;
        while (markRow < playerBoard.getNumbersOfRows() && markCol < playerBoard.getNumbersOfRows() && (playerBoard.getBoardArrayElement(markRow, markCol) != player.getMark())) {
            numbersInRow = checkNumbersInRow(playerBoard, markRow, markCol, computer, numbersInRow);
            markRow++;
            markCol++;
        }

        if (numbersInRow >= Math.max(playerBoard.getNumberToWin() - numberToCheck, 2) && ((numbersInRow + numbersOfFree) >= playerBoard.getNumberToWin())) {
            //    System.out.println("found mark col [" + addMarkRow + ";" + addMarkCol + "]");
            playerBoard.setBoardArrayElement(addMarkRow, addMarkCol, computer.getMark());
            playerBoard.printBoard();
            return true;
        } else {
            this.addMarkRow = -1;
            this.addMarkCol = -1;
            this.numbersOfFree = 0;
            numbersInRow = 1;

            markRow = computer.getLastMarkedRow() - 1;
            markCol = computer.getLastMarkedCol() + 1;
            while (markRow >= 0 && markCol < playerBoard.getNumbersOfRows() && (playerBoard.getBoardArrayElement(markRow, markCol) != player.getMark())) {
                numbersInRow = checkNumbersInRow(playerBoard, markRow, markCol, computer, numbersInRow);
                markRow--;
                markCol++;
            }

            markRow = computer.getLastMarkedRow() + 1;
            markCol = computer.getLastMarkedCol() - 1;
            while (markRow < playerBoard.getNumbersOfRows() && markCol >= 0 && (playerBoard.getBoardArrayElement(markRow, markCol) != player.getMark())) {
                numbersInRow = checkNumbersInRow(playerBoard, markRow, markCol, computer, numbersInRow);
                markRow++;
                markCol--;
            }

            if (numbersInRow >= Math.max(playerBoard.getNumberToWin() - numberToCheck, 2) && ((numbersInRow + numbersOfFree) >= playerBoard.getNumberToWin())) {
                //    System.out.println("found mark col [" + addMarkRow + ";" + addMarkCol + "]");
                playerBoard.setBoardArrayElement(addMarkRow, addMarkCol, computer.getMark());
                playerBoard.printBoard();
                return true;
            }
        }
        return false;
    }

    public boolean checkBestMoveToStopRow(Player player, Player computer, Board playerBoard, int numberToCheck) {

        this.addMarkRow = -1;
        this.addMarkCol = -1;
        this.numbersOfFree = 0;
        int numbersInRow = 1;
        NUMBER_TO_STOP_PLAYER = setNUMBER_TO_STOP_PLAYER(numberToCheck, playerBoard);

        int markRow = player.getLastMarkedRow();
        int markCol = player.getLastMarkedCol() - 1;
        while (markCol >= 0 && (playerBoard.getBoardArrayElement(markRow, markCol) != computer.getMark())) {
            numbersInRow = checkNumbersInRow(playerBoard, markRow, markCol, player, numbersInRow);
            markCol--;
        }

        markCol = player.getLastMarkedCol() + 1;
        while (markCol < playerBoard.getNumbersOfRows() && (playerBoard.getBoardArrayElement(markRow, markCol) != computer.getMark())) {
            numbersInRow = checkNumbersInRow(playerBoard, markRow, markCol, player, numbersInRow);
            markCol++;
        }
        if(checkIfShouldMark(numbersInRow, numbersOfFree, playerBoard, addMarkRow, addMarkCol, computer, player, NUMBER_TO_STOP_PLAYER)) {
            return true;
        }
        return false;
    }

    public boolean checkBestMoveToStopCol(Player player, Player computer, Board playerBoard, int numberToCheck) {
        this.addMarkRow = -1;
        this.addMarkCol = -1;
        this.numbersOfFree = 0;
        int numbersInRow = 1;
        int markRow = player.getLastMarkedRow() - 1;
        int markCol = player.getLastMarkedCol();
        NUMBER_TO_STOP_PLAYER = setNUMBER_TO_STOP_PLAYER(numberToCheck, playerBoard);

        while (markRow >= 0 && (playerBoard.getBoardArrayElement(markRow, markCol) != computer.getMark())) {
            numbersInRow = checkNumbersInRow(playerBoard, markRow, markCol, player, numbersInRow);
            markRow--;
        }

        markRow = player.getLastMarkedRow() + 1;
        while (markRow < playerBoard.getNumbersOfRows() && (playerBoard.getBoardArrayElement(markRow, markCol) != computer.getMark())) {
            numbersInRow = checkNumbersInRow(playerBoard, markRow, markCol, player, numbersInRow);
            markRow++;
        }
        if(checkIfShouldMark(numbersInRow, numbersOfFree, playerBoard, addMarkRow, addMarkCol, computer, player, NUMBER_TO_STOP_PLAYER)) {
            return true;
        }
        return false;
    }

    public boolean checkBestMoveToStopDiagonally(Player player, Player computer, Board playerBoard, int numberToCheck) {

        this.addMarkRow = -1;
        this.addMarkCol = -1;
        this.numbersOfFree = 0;
        int numbersInRow = 1;
        int markRow = player.getLastMarkedRow() - 1;
        int markCol = player.getLastMarkedCol() - 1;
        NUMBER_TO_STOP_PLAYER = setNUMBER_TO_STOP_PLAYER(numberToCheck, playerBoard);

        while (markRow >= 0 && markCol >= 0 && (playerBoard.getBoardArrayElement(markRow, markCol) != computer.getMark())) {
            numbersInRow = checkNumbersInRow(playerBoard, markRow, markCol, player, numbersInRow);
            markRow--;
            markCol--;
        }

        markRow = player.getLastMarkedRow() + 1;
        markCol = player.getLastMarkedCol() + 1;
        while (markRow < playerBoard.getNumbersOfRows() && markCol < playerBoard.getNumbersOfRows() && (playerBoard.getBoardArrayElement(markRow, markCol) != computer.getMark())) {
            numbersInRow = checkNumbersInRow(playerBoard, markRow, markCol, player, numbersInRow);
            markRow++;
            markCol++;
        }

        if(checkIfShouldMark(numbersInRow, numbersOfFree, playerBoard, addMarkRow, addMarkCol, computer, player, NUMBER_TO_STOP_PLAYER)) {
            return true;
        } else {
            this.addMarkRow = -1;
            this.addMarkCol = -1;
            this.numbersOfFree = 0;
            numbersInRow = 1;
            markRow = player.getLastMarkedRow() - 1;
            markCol = player.getLastMarkedCol() + 1;

            while (markRow >= 0 && markCol < playerBoard.getNumbersOfRows() && (playerBoard.getBoardArrayElement(markRow, markCol) != computer.getMark())) {
                numbersInRow = checkNumbersInRow(playerBoard, markRow, markCol, player, numbersInRow);
                markRow--;
                markCol++;
            }

            markRow = player.getLastMarkedRow() + 1;
            markCol = player.getLastMarkedCol() - 1;
            while (markRow < playerBoard.getNumbersOfRows() && markCol >= 0 && (playerBoard.getBoardArrayElement(markRow, markCol) != computer.getMark())) {
                numbersInRow = checkNumbersInRow(playerBoard, markRow, markCol, player, numbersInRow);
                markRow++;
                markCol--;
            }

            if(checkIfShouldMark(numbersInRow, numbersOfFree, playerBoard, addMarkRow, addMarkCol, computer, player, NUMBER_TO_STOP_PLAYER)) {
                return true;
            }
            return false;
        }
    }

    public int checkNumbersInRow(Board playerBoard, int markRow, int markCol, Player computer, int numbersInRow) {

        if (playerBoard.getBoardArrayElement(markRow, markCol) == computer.getMark()) {
            numbersInRow++;
        } else {
            this.numbersOfFree++;
            if (numbersOfFree == 1) { //sets the first free spot to set mark on
                this.addMarkRow = markRow;
                this.addMarkCol = markCol;
            }
        }
        return numbersInRow;
    }

    public boolean checkIfShouldMark(int numbersInRow, int numbersOfFree, Board playerBoard, int addMarkRow, int addMarkCol, Player computer, Player player,int  NUMBERS_TO_STOP_PLAYER) {
        if (numbersInRow >= NUMBERS_TO_STOP_PLAYER && ((numbersInRow + numbersOfFree) >= playerBoard.getNumberToWin())) {
            //    System.out.println("found mark col [" + addMarkRow + ";" + addMarkCol + "]");
            playerBoard.setBoardArrayElement(addMarkRow, addMarkCol, computer.getMark());
            playerBoard.printBoard();
            return true;
        } else {
            return false;
        }
    }

    public String computerInsults() {
        int randomString = rand.nextInt(1, 10);
        if (difficulty == 1) {
            switch (randomString) {
                case 1 -> {
                    return "Din intelligensnivå är så låg att den skulle kunna sätta rekord i negativa tal.";
                }
                case 2 -> {
                    return "Jag är övertygad om att du har en hjärna, men det verkar som om den är avstängd.";
                }
                case 3 -> {
                    return "Om din hjärna var en planet, skulle den vara Pluto - alltför liten och ingen bryr sig om den längre.";
                }
                case 4 -> {
                    return "Du måste vara en av de få som kan gå vilse i en hiss med enbart två knappar.";
                }
                case 5 -> {
                    return "Din intelligens är som en svart hål - allt som kommer nära försvinner spårlöst.";
                }
                case 6 -> {
                    return "Jag har sett växter som är mer medvetna om sin omgivning än du är.";
                }
                case 7 -> {
                    return "Om ditt IQ var ett telefonnummer, skulle det vara 0-800-DUM-TROLL.";
                }
                case 8 -> {
                    return "Du är som en bok med bara en sida, och den sidan är tom.";
                }
                case 9 -> {
                    return "Är du säker på att du inte är besläktad med en trädgårdsgurka? Jag tror att den skulle kunna slå dig i en IQ-test.";
                }
                case 10 -> {
                    return "Din hjärna verkar ha tagit en permanent semester. Jag hoppas att du åtminstone har haft kul på vägen ner.";
                }
                default -> {
                    return "";
                }
            }
        } else {
            switch (randomString) {
                case 1 -> {
                    return "Din intelligens är som en stjärna som slocknar i det fjärran universum av kunskap.";
                }
                case 2 -> {
                    return "Jag har granskat galaxers mysterier, och du är fortfarande den mest obegripliga gåtan.";
                }
                case 3 -> {
                    return "Din tankeskärpa är som ett svart hål, sugande in allt hopp om förnuft.";
                }
                case 4 -> {
                    return "Din intellekt är som ett klot av mörk materia - ingen kan se det, men det påverkar negativt allt omkring det.";
                }
                case 5 -> {
                    return "Din visdom är som en matematisk konstant - den är konstant obefintlig.";
                }
                case 6 -> {
                    return "Jag har analyserat universets orsak och verkan, och jag tror fortfarande att din existens är en av dess största mysterier.";
                }
                case 7 -> {
                    return "Din tankekapacitet liknar en svart hål- teoretiskt fascinerande men praktiskt taget omöjligt att förstå.";
                }
                case 8 -> {
                    return "Ditt intellekt är som ett kvantfenomen - när vi försöker mäta det, förändras det.";
                }
                case 9 -> {
                    return "Jag har sonderat de djupaste avgrunderna i kosmiskt medvetande, och ändå känner jag mig fortfarande förvirrad av din oförklarliga dumhet.";
                }
                case 10 -> {
                    return "Din intelligens är som universums expansion - oändligt snabb och fullständigt meningslös.";
                }
                default -> {
                    return "";
                }
            }

        }
    }
}
