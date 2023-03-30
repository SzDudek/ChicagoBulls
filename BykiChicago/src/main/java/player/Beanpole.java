package player;

import match.Match;

public class Beanpole extends Player {
    private int dunk;

    public Beanpole(int speed, int accuracy, int passing, int defence, int teamId, int playerCount) {
        super(speed, accuracy, passing, defence, teamId, playerCount);
        this.dunk = 10;
    }

    public Beanpole(int teamId, int playerCount) {
        super(teamId, playerCount);
        setDunk(10);
        setSpeed(50);
        setAccuracy(80);
        setPassing(70);
        setDefence(70);
        Match.getBeanpoles().add(this);

    }

    public void specialSkill() {
        if (Match.getRoundPart() == 4) {
            if (dunk == 10) {
                if (this.getTeamId() == 1) {
                    if (getDestX() >= 48 && getDestY() >= 4 && getDestY() <= 6 && this.isBallPos() && getDestX() == this.getX() && getDestY() == this.getDestY()) {
                        System.out.println("playerClasses.Player " + getPlayerCount() + " of the team " + Match.getTeams().get(this.getTeamId() - 1).getName() + " uses special skill - dunk");
                        int tempCelnosc = getAccuracy();
                        setAccuracy(100);
                        shoot();
                        setAccuracy(tempCelnosc);
                        dunk = 0;
                    }
                } else {
                    if (getDestX() <= 2 && getDestY() >= 4 && getDestY() <= 6 && this.isBallPos() && getDestX() == this.getX() && getDestY() == this.getDestY()) {
                        System.out.println("playerClasses.Player " + getPlayerCount() + " of the team " + Match.getTeams().get(this.getTeamId() - 1).getName() + " uses special skill - dunk");
                        int tempCelnosc = getAccuracy();
                        setAccuracy(100);
                        shoot();
                        setAccuracy(tempCelnosc);
                        dunk = 0;
                    }
                }
            } else if (dunk < 10) {
                addDunk();
            }
        }
    }

    public int getDunk() {
        return dunk;
    }

    public void addDunk() {
        this.dunk += 1;
    }

    public void setDunk(int dunk) {
        this.dunk = dunk;
    }
}
