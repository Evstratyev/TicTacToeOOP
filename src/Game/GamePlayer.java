package Game;

public class GamePlayer {
    private char playerSign;
    private boolean realPlayer = true;

    public GamePlayer(char playerSign, boolean realPlayer) {
        this.playerSign = playerSign;
        this.realPlayer = realPlayer;
    }

    public boolean isRealPlayer(){ return realPlayer; }

    public char getPlayerSign(){
        return playerSign;
    }
}
