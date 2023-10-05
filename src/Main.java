import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String choice;
        GameMode game = new GameMode();

        System.out.println("Välkommen till TRE-I-RAD!");
        System.out.println("Detta är ett spel för två spelare, man vinner om man får tre likadana tecken i rad.");
        System.out.println("Raderna kan vara horisontella, vertikala samt diagonala.\n");
        do {
            System.out.println("Hur vill du spela?");
            System.out.println("1. Spelare mot spelare");
            System.out.println("2. Spelare mot dator");
            System.out.println("3. Avsluta");
            choice = sc.nextLine();
            switch (choice.toLowerCase()) {
                case "1", "spelare mot spelare" -> {
                    game.setGameMode("Multiplayer");
                    game.multiPlayer();
                }
                case "2", "spelare mot dator" -> {
                    game.setGameMode("Single-player");
                    game.singlePlayer();
                }
                case "3", "avsluta" -> {
                    System.out.println("Tack för att du spelat.");
                }
                default -> {
                    System.out.println("Fel input, försök igen.");
                }
            }
        } while (!choice.equalsIgnoreCase("3") && !choice.equalsIgnoreCase("Avsluta"));
    }
}