package match;

import player.Player;

import java.util.ArrayList;

public class Team {
    private int id;
    private String name;
    private String symbol;
    private boolean ballPos;
    public ArrayList<Player> lineup = new ArrayList<Player>();

    public boolean isBallPos() {
        return ballPos;
    }
    public void setBallPos(boolean ballPos) {
        this.ballPos = ballPos;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public  Team(int id, String name){
        this.id=id;
        this.name =name;
        if(id==1) this.symbol="1";
        else this.symbol="2";
        ballPos=false;
    }
}
