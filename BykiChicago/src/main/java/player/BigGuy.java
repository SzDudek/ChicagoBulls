package player;

import match.Ball;
import match.Court;
import match.Match;

public class BigGuy extends Player {
    private int block;

    public BigGuy(int speed, int accuracy, int passing, int defence, int teamId, int playerCount) {
        super(speed, accuracy, passing, defence, teamId, playerCount);
        this.block = 10;
    }

    public BigGuy(int teamId, int ktoryzaw) {
        super(teamId, ktoryzaw);
        setBlock(10);
        setSpeed(20);
        setAccuracy(40);
        setPassing(70);
        setDefence(80);
        Match.getBigGuys().add(this);

    }

    public void specialSkill() {
        if (Match.getRoundPart() == 3) {
            if (block == 10 && Math.abs(this.getX() - Ball.getX()) <= 1 && Math.abs(this.getY() - Ball.getY()) <= 1) {
                System.out.println("playerClasses.Player " + getPlayerCount() + " of the team " + Match.getTeams().get(this.getTeamId() - 1).getName() + " uses special skill - block");
                int temp = getDefence();
                setDefence(100);
                retake(Ball.getX(), Ball.getY(), Court.courtState[Ball.getX()][Ball.getY()]);
                setDefence(temp);
                block = 0;
            } else if (block < 10) {
                addBlock();
            }
        }
    }

    public int getBlock() {
        return block;
    }

    public void addBlock() {
        this.block += 1;
    }

    public void setBlock(int block) {
        this.block = block;
    }
}
