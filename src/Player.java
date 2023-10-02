public class Player {
    private String name;
    private int numberOfWins;

    public Player(String name) {
        this.name = name;
        this.numberOfWins = 0;
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

    public void playerTurn() {

    }

    public boolean checkIfWin() {
        return false;
    }
}
