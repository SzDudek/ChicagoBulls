package player;

import match.Match;

public class Speedy extends Player {

    private int speed;

    public Speedy(int speed, int accuracy, int passing, int defence, int teamId, int playerCount) {
        super(speed, accuracy, passing, defence, teamId, playerCount);
        this.speed = 10;
    }

    public Speedy(int teamId, int playerCount) {
        super(teamId, playerCount);
        setSpeed(10);
        setSpeed(90);
        setAccuracy(50);
        setPassing(70);
        setDefence(70);
        Match.getSpeedies().add(this);
    }

    public void specialSkill() {
        if (Match.getRoundPart() == 1) {
            if (speed == 10) {
                System.out.println("Player " + getPlayerCount() + " of the team " + Match.getTeams().get(this.getTeamId() - 1).getName() + " uses special skill - turbo");
                move();
                speed = 0;
            } else if (speed < 10) {
                addSpeed();
            }
        }
    }

    public int getSpeed() {
        return speed;
    }

    public void addSpeed() {
        this.speed += 1;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
