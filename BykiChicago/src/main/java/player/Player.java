package player;

import java.util.Objects;
import java.util.Random;

import match.Match;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.lang.Thread.sleep;
import match.Court;
import statsOutput.BoxScore;
import match.Ball;

public abstract class Player implements Actions {
    private int teamId;
    private int speed;
    private int accuracy;
    private int passing;
    private int defence;
    private int x;
    private int y;
    private boolean ballPos;
    private int playerCount;
    private int destX;
    private int destY;

    
    public boolean isFree(int xR, int yR) {                 //checking if the field is free
        if (xR > 0 && xR < 10 && yR > 0 && yR < 50) {
            if (Court.courtState[xR][yR] == 0) return true;
            else return false;
        }
        return false;
    }

    public int closestPlayer(String sit) {                  //finding the closest player
        int nr = 0;                    
        double minR = 100000.0;
        double temp;
        if (Objects.equals(sit, "p")) {
            for (int i = 0; i < 2; i++) {
                if (Match.getTeams().get(this.teamId - 1).lineup.get(i).getPlayerCount() != this.playerCount) {
                    temp = sqrt(pow((this.getX() - Match.getTeams().get(this.teamId - 1).lineup.get(i).getX()), 2) + pow((this.getY() - Match.getTeams().get(this.teamId - 1).lineup.get(i).getY()), 2));
                    if (temp < minR) {
                        minR = temp;
                        nr = Match.getTeams().get(this.teamId - 1).lineup.get(i).getPlayerCount();
                    }
                }
            }
            return nr;
        } else {
            for (int i = 0; i < 2; i++) {
                temp = sqrt(pow((Ball.getX() - Match.getTeams().get(this.teamId - 1).lineup.get(i).getX()), 2) + pow((Ball.getY() - Match.getTeams().get(this.teamId - 1).lineup.get(i).getY()), 2));
                if (temp < minR) {
                    minR = temp;
                    nr = Match.getTeams().get(this.teamId - 1).lineup.get(i).getPlayerCount();
                }

            }
            return nr;
        }
    }

                                                                            //picking the ball from the ground/air
    public void pick(int xBall, int yBall, boolean ballPos1, boolean ballPos2) {
        if (!ballPos1 && !ballPos2) {
            if (Math.abs(this.x - xBall) <= 1 && Math.abs(this.y - yBall) <= 1) {
                System.out.println(this.teamId + " picks up the ball");
                this.setBallPos(true);
                Ball.setX(this.x);
                Ball.setY(this.y);
                moveDirection(Ball.getX(), Ball.getY());
                Match.getTeams().get(this.teamId - 1).setBallPos(true);
                Court.courtState[xBall][yBall] = 0;
                for (int i = 0; i < 3; i++) {
                    Match.getTeams().get(this.teamId - 1).lineup.get(i).moveDirection(Ball.getX(), Ball.getY());
                }
            }
        }
    }

                                                                                //take the ball from the opponent
    public void retake(int xBall, int yBall, int ballField) {
        if (Math.abs(this.x - xBall) <= 1 && Math.abs(this.y - yBall) <= 1) {
            if (ballField != 3 && ballField != this.teamId) {
                Random szansa = new Random();
                int a = szansa.nextInt(101);
                if (a <= this.defence) {
                    System.out.println(this.teamId + " retakes the ball");
                    this.setBallPos(true);
                    for (int i = 0; i < 2; i++) {
                        Match.getTeams().get(i).setBallPos(Match.getTeams().get(i).getId() == this.teamId);
                    }
                    for (int i = 0; i < 6; i++) {
                        if (Match.getAllPlayers().get(i).getY() == Ball.getY() && Match.getAllPlayers().get(i).getX() == Ball.getX()) {
                            Match.getAllPlayers().get(i).setBallPos(false);
                            break;
                        }
                    }
                    if (this.teamId == 1) BoxScore.setSteal1();
                    else BoxScore.setSteal2();
                    Ball.setX(this.x);
                    Ball.setY(this.y);
                    for (int i = 0; i < 3; i++) {
                        Match.getTeams().get(this.teamId - 1).lineup.get(i).moveDirection(Ball.getX(), Ball.getY());
                    }
                }
            }
        }
    }

                                                                            //determine the direction of movement
    public void moveDirection(int xP, int yP) {
        if (Match.getTeams().get(this.teamId - 1).isBallPos()) {
            Random decision = new Random();
            Random posx = new Random();
            Random posy = new Random();
            int tempDirX = -1;
            int tempDirY = -1;
            boolean lasts = true;
            while (lasts) {
                int b = decision.nextInt(3);
                switch (b) {
                    case 0://layup
                        Random near = new Random();
                        if (this.teamId == 1) {
                            int c = near.nextInt(2);
                            if (c == 0) { //near kosza
                                c = near.nextInt(2);
                                if (c == 0) {
                                    tempDirX = 4;
                                    tempDirY = 49;
                                } else {
                                    tempDirX = 6;
                                    tempDirY = 49;
                                }
                            } else {
                                c = near.nextInt(3);
                                switch (c) {
                                    case 0 -> {
                                        tempDirX = 4;
                                        tempDirY = 48;
                                    }
                                    case 1 -> {
                                        tempDirX = 5;
                                        tempDirY = 48;
                                    }
                                    case 2 -> {
                                        tempDirX = 6;
                                        tempDirY = 48;
                                    }
                                }
                            }
                        } else {
                            int c = near.nextInt(2);
                            if (c == 0) {
                                c = near.nextInt(2);
                                if (c == 0) {
                                    tempDirX = 4;
                                    tempDirY = 1;
                                } else {
                                    tempDirX = 6;
                                    tempDirY = 1;
                                }
                            } else {
                                c = near.nextInt(3);
                                switch (c) {
                                    case 0 -> {
                                        tempDirX = 4;
                                        tempDirY = 2;
                                    }
                                    case 1 -> {
                                        tempDirX = 5;
                                        tempDirY = 2;
                                    }
                                    case 2 -> {
                                        tempDirX = 6;
                                        tempDirY = 2;
                                    }
                                }
                            }
                        }
                        break;
                    case 1:
                        if (this.teamId == 1) {
                            tempDirX = posx.nextInt(9) + 1;
                            tempDirY = posy.nextInt(8) + 40;
                        } else {
                            tempDirX = posx.nextInt(9) + 1;
                            tempDirY = posy.nextInt(8) + 3;
                        }
                        break;
                    case 2:
                        if (this.teamId == 1) {
                            tempDirX = posx.nextInt(9) + 1;
                            tempDirY = posy.nextInt(3) + 39;
                        } else {
                            tempDirX = posx.nextInt(9) + 1;
                            tempDirY = posy.nextInt(3) + 11;
                        }
                        break;
                }
                boolean check = true;
                for (int i = 0; i < 3; i++) {
                    if (Match.getTeams().get(this.teamId - 1).lineup.get(i).getX() == tempDirX && Match.getTeams().get(this.teamId - 1).lineup.get(i).getY() == tempDirY) {
                        System.out.println("Teammate already has this target");
                        check = false;
                        break;
                    }
                }
                if (check) {
                    this.destX = tempDirX;
                    this.destY = tempDirY;
                    lasts = false;
                }
            }
        } else {
            int targetPlayerNumber = closestPlayer("r");
            if (targetPlayerNumber == this.playerCount) {
                this.destX = Ball.getX();
                this.destY = Ball.getY();
            } else {
                int opponentTeam;
                if (this.teamId == 1) opponentTeam = 2;
                else opponentTeam = 1;
                for (int i = 0; i < 3; i++) {
                    if (Match.getTeams().get(opponentTeam - 1).lineup.get(i).getPlayerCount() == this.playerCount) {
                        this.destX = Match.getTeams().get(opponentTeam - 1).lineup.get(i).getX();
                        this.destY = Match.getTeams().get(opponentTeam - 1).lineup.get(i).getY();
                    }
                }
            }
        }
        if (Match.getRoundPart() == 1) {
            System.out.println("Targeted direction of player " + this.playerCount + " of the team " + Match.getTeams().get(this.teamId - 1).getName() + ": " + this.destX + " " + this.destY);
        }
    }
                                                                        //actual movement
    public void move() {
        if (!Match.getTeams().get(this.teamId - 1).isBallPos()) {
            moveDirection(Ball.getX(), Ball.getY());
        }
        int freeFields[][] = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (isFree(this.x + i - 1, this.y + j - 1)) freeFields[i][j] = 1;
                else freeFields[i][j] = 0;
            }
        }

        int chosex;
        int chosey;
        if ((this.destX - this.x) > 0) {
            chosex = 2;
        } else if ((this.destX - this.x) < 0) {
            chosex = 0;
        } else chosex = 1;
        if ((this.destY - this.y) > 0) {
            chosey = 2;
        } else if ((this.destY - this.y) < 0) {
            chosey = 0;
        } else chosey = 1;

        if (freeFields[chosex][chosey] == 1) {
            Court.courtState[this.x][this.y] = 0;
            this.x = this.x + (chosex - 1);
            this.y = this.y + (chosey - 1);
            Court.courtState[this.x][this.y] = this.teamId;
            if (this.ballPos) {
                Ball.setX(this.x);
                Ball.setY(this.y);
            }
        }
        else {
            int chosen;
            Random decision = new Random();
            chosen = decision.nextInt(2);
            if ((chosex == 0 && chosey == 0) || (chosex == 2 && chosey == 0) || (chosex == 0 && chosey == 2) || (chosex == 2 && chosey == 2)) {
                if (chosen == 0) {
                    chosen = decision.nextInt(2);
                    if (chosen == 0) { //+x
                        if (chosex + 1 < 3) {
                            if (freeFields[chosex + 1][chosey] == 1) {
                                chosex += 1;
                                Court.courtState[this.x][this.y] = 0;
                                this.x = this.x + (chosex - 1);
                                this.y = this.y + (chosey - 1);
                                Court.courtState[this.x][this.y] = this.teamId;
                                if (this.ballPos) {
                                    Ball.setX(this.x);
                                    Ball.setY(this.y);
                                }
                            }
                        } else if (chosex - 1 > -1) {
                            if (freeFields[chosex - 1][chosey] == 1) {
                                chosex -= 1;
                                Court.courtState[this.x][this.y] = 0;
                                this.x = this.x + (chosex - 1);
                                this.y = this.y + (chosey - 1);
                                Court.courtState[this.x][this.y] = this.teamId;
                                if (this.ballPos) {
                                    Ball.setX(this.x);
                                    Ball.setY(this.y);
                                }
                            }
                        }
                    } else { //-x
                        if (chosex - 1 > -1) {
                            if (freeFields[chosex - 1][chosey] == 1) {
                                chosex -= 1;
                                Court.courtState[this.x][this.y] = 0;
                                this.x = this.x + (chosex - 1);
                                this.y = this.y + (chosey - 1);
                                Court.courtState[this.x][this.y] = this.teamId;
                                if (this.ballPos) {
                                    Ball.setX(this.x);
                                    Ball.setY(this.y);
                                }
                            }
                        } else if (chosex + 1 < 3) {
                            if (freeFields[chosex + 1][chosey] == 1) {
                                chosex += 1;
                                Court.courtState[this.x][this.y] = 0;
                                this.x = this.x + (chosex - 1);
                                this.y = this.y + (chosey - 1);
                                Court.courtState[this.x][this.y] = this.teamId;
                                if (this.ballPos) {
                                    Ball.setX(this.x);
                                    Ball.setY(this.y);
                                }
                            }
                        }
                    }
                } else {
                    chosen = decision.nextInt(2);
                    if (chosen == 0) {
                        if (chosey + 1 < 3) {
                            if (freeFields[chosex][chosey + 1] == 1) {
                                chosey += 1;
                                Court.courtState[this.x][this.y] = 0;
                                this.x = this.x + (chosex - 1);
                                this.y = this.y + (chosey - 1);
                                Court.courtState[this.x][this.y] = this.teamId;
                                if (this.ballPos) {
                                    Ball.setX(this.x);
                                    Ball.setY(this.y);
                                }
                            }
                        } else if (chosey - 1 > -1) {
                            if (freeFields[chosex][chosey - 1] == 1) {
                                chosex -= 1;
                                Court.courtState[this.x][this.y] = 0;
                                this.x = this.x + (chosex - 1);
                                this.y = this.y + (chosey - 1);
                                Court.courtState[this.x][this.y] = this.teamId;
                                if (this.ballPos) {
                                    Ball.setX(this.x);
                                    Ball.setY(this.y);
                                }
                            }
                        }
                    } else { //-y
                        if (chosey - 1 > -1) {
                            if (freeFields[chosex][chosey - 1] == 1) {
                                chosey -= 1;
                                Court.courtState[this.x][this.y] = 0;
                                this.x = this.x + chosex - 1;
                                this.y = this.y + chosey - 1;
                                Court.courtState[this.x][this.y] = this.teamId;
                                if (this.ballPos) {
                                    Ball.setX(this.x);
                                    Ball.setY(this.y);
                                }
                            }
                        } else if (chosey + 1 < 3) {
                            if (freeFields[chosex][chosey + 1] == 1) {
                                chosey += 1;
                                Court.courtState[this.x][this.y] = 0;
                                this.x = this.x + (chosex - 1);
                                this.y = this.y + (chosey - 1);
                                Court.courtState[this.x][this.y] = this.teamId;
                                if (this.ballPos) {
                                    Ball.setX(this.x);
                                    Ball.setY(this.y);
                                }
                            }
                        }
                    }
                }
            } else {
                if (chosen == 0) {
                    if (freeFields[0][chosey] == 1) {
                        chosex = 0;
                        Court.courtState[this.x][this.y] = 0;
                        this.x = this.x + (chosex - 1);
                        this.y = this.y + (chosey - 1);
                        Court.courtState[this.x][this.y] = this.teamId;
                        if (this.ballPos) {
                            Ball.setX(this.x);
                            Ball.setY(this.y);
                        }
                    } else if (freeFields[chosex][0] == 1) {
                        chosey = 0;
                        Court.courtState[this.x][this.y] = 0;
                        this.x = this.x + (chosex - 1);
                        this.y = this.y + (chosey - 1);
                        Court.courtState[this.x][this.y] = this.teamId;
                        if (this.ballPos) {
                            Ball.setX(this.x);
                            Ball.setY(this.y);
                        }
                    }
                } else {
                    if (freeFields[chosex][0] == 1) {
                        chosey = 0;
                        Court.courtState[this.x][this.y] = 0;
                        this.x = this.x + (chosex - 1);
                        this.y = this.y + (chosey - 1);
                        Court.courtState[this.x][this.y] = this.teamId;
                        if (this.ballPos) {
                            Ball.setX(this.x);
                            Ball.setY(this.y);
                        }
                    } else if (freeFields[0][chosey] == 1) {
                        chosex = 0;
                        Court.courtState[this.x][this.y] = 0;
                        this.x = this.x + (chosex - 1);
                        this.y = this.y + (chosey - 1);
                        Court.courtState[this.x][this.y] = this.teamId;
                        if (this.ballPos) {
                            Ball.setX(this.x);
                            Ball.setY(this.y);
                        }
                    }
                }
            }
        }
    }
                                                    //change the values which influence the visuals of the court
    public void visual(String type) {
        int xPcel = this.getX();
        int yPcel = this.getY();
        int xPast = Ball.getX();
        int yPast = Ball.getY();
        int vPast = Court.courtState[Ball.getX()][Ball.getY()];
        if (Objects.equals(type, "r")) {
            if (this.teamId == 1) {
                xPcel = 5;
                yPcel = 49;
            } else {
                xPcel = 5;
                yPcel = 1;
            }
        } else {
            int targeted = closestPlayer("p");
            for (int i = 0; i < 3; i++) {
                if (Match.getTeams().get(this.teamId - 1).lineup.get(i).playerCount == targeted) {
                    xPcel = Match.getTeams().get(this.teamId - 1).lineup.get(i).getX();
                    yPcel = Match.getTeams().get(this.teamId - 1).lineup.get(i).getY();
                }
            }
        }
        while (xPcel != Ball.getX() || yPcel != Ball.getY()) {
            Court.courtState[xPast][yPast] = vPast;
            if ((xPcel - Ball.getX()) > 0) {
                xPast = Ball.getX() + 1;
                Ball.setX(Ball.getX() + 1);
            } else if ((xPcel - Ball.getX()) < 0) {
                xPast = Ball.getX() - 1;
                Ball.setX(Ball.getX() - 1);
            }
            if ((yPcel - Ball.getY()) > 0) {
                yPast = Ball.getY() + 1;
                Ball.setY(Ball.getY() + 1);
            } else if ((yPcel - Ball.getY()) < 0) {
                yPast = Ball.getY() - 1;
                Ball.setY(Ball.getY() - 1);
            }
            vPast = Court.courtState[xPast][yPast];
            Court.courtState[Ball.getX()][Ball.getY()] = 3;
            Court.displayField();
            try {
                sleep(100);
                System.out.print("\033[H\033[2J");
                System.out.flush();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

                                                                //shooting
    public void shoot() {
        if (this.x == destX && this.y == destY && this.ballPos) {
            System.out.println("Player " + this.playerCount + " of the team " + Match.getTeams().get(this.teamId - 1).getName() + " shoots and... ");
            Random scoring = new Random();
            int t = scoring.nextInt(101);
            this.ballPos = false;
            Match.getTeams().get(this.teamId - 1).setBallPos(false);
            visual("r");
            if (t <= this.accuracy) {
                System.out.print("scores\n");
                if (this.teamId == 1) {
                    BoxScore.setScored1();
                    if (this.y > 39) {
                        BoxScore.setTeam1Score(2);
                    } else {
                        BoxScore.setTeam1Score(3);
                        BoxScore.setFor3_1();
                    }
                } else {
                    BoxScore.setScored2();
                    if (this.y < 11) {
                        BoxScore.setTeam2Score(2);
                    } else {
                        BoxScore.setTeam2Score(3);
                        BoxScore.setFor3_2();
                    }
                }
                for (int i = 0; i < 6; i++) {
                    if (Match.getAllPlayers().get(i).getTeamId() != this.teamId) {
                        if (this.teamId == 1) {
                            Court.courtState[Match.getAllPlayers().get(i).getX()][Match.getAllPlayers().get(i).getY()] = 0;
                            Match.getAllPlayers().get(i).setX(5);
                            Match.getAllPlayers().get(i).setY(49);
                            Ball.setX(5);
                            Ball.setY(49);
                            Court.courtState[Match.getAllPlayers().get(i).getX()][Match.getAllPlayers().get(i).getY()] = Match.getAllPlayers().get(i).getTeamId();
                            Match.getAllPlayers().get(i).setBallPos(true);
                            Match.getTeams().get(1).setBallPos(true);
                            for (int o = 0; o < 3; o++) {
                                Match.getTeams().get(1).lineup.get(o).moveDirection(Ball.getX(), Ball.getY());
                            }
                        } else {
                            Court.courtState[Match.getAllPlayers().get(i).getX()][Match.getAllPlayers().get(i).getY()] = 0;
                            Match.getAllPlayers().get(i).setX(5);
                            Match.getAllPlayers().get(i).setY(1);
                            Ball.setX(5);
                            Ball.setY(1);
                            Court.courtState[Match.getAllPlayers().get(i).getX()][Match.getAllPlayers().get(i).getY()] = Match.getAllPlayers().get(i).getTeamId();
                            Match.getAllPlayers().get(i).setBallPos(true);
                            Match.getTeams().get(0).setBallPos(true);
                            for (int o = 0; o < 3; o++) {
                                Match.getTeams().get(0).lineup.get(o).moveDirection(Ball.getX(), Ball.getY());
                            }
                        }
                        break;
                    }
                }
            } else {
                System.out.print("misses");
                System.out.println();
                visual("r");
                Random threePointField = new Random();
                int pole = threePointField.nextInt(5);
                if (this.teamId == 1) {
                    BoxScore.setMissed1();
                    switch (pole) {
                        case 0 -> {
                            Ball.setX(4);
                            Ball.setY(48);
                        }
                        case 1 -> {
                            Ball.setX(5);
                            Ball.setY(48);
                        }
                        case 2 -> {
                            Ball.setX(6);
                            Ball.setY(48);
                        }
                        case 3 -> {
                            Ball.setX(6);
                            Ball.setY(49);
                        }
                        case 4 -> {
                            Ball.setX(4);
                            Ball.setY(49);
                        }
                    }
                } else {
                    BoxScore.setMissed2();
                    switch (pole) {
                        case 0 -> {
                            Ball.setX(4);
                            Ball.setY(2);
                        }
                        case 1 -> {
                            Ball.setX(5);
                            Ball.setY(2);
                        }
                        case 2 -> {
                            Ball.setX(6);
                            Ball.setY(2);
                        }
                        case 3 -> {
                            Ball.setX(6);
                            Ball.setY(1);
                        }
                        case 4 -> {
                            Ball.setX(4);
                            Ball.setY(1);
                        }
                    }
                }
                if (Court.courtState[5][1] == 3) Court.courtState[5][1] = 0;
                if (Court.courtState[5][49] == 3) Court.courtState[5][1] = 0;

                if (Court.courtState[Ball.getX()][Ball.getY()] == 0) {
                    Court.courtState[Ball.getX()][Ball.getY()] = 3;
                } else {
                    for (int i = 0; i < 2; i++) {
                        if (Match.getAllPlayers().get(i).getX() == Ball.getX() && Match.getAllPlayers().get(i).getY() == Ball.getY()) {
                            Match.getAllPlayers().get(i).setBallPos(true);
                            Match.getAllPlayers().get(i).moveDirection(Ball.getX(), Ball.getY());
                            for (int j = 0; j < 2; j++) {
                                if (Match.getTeams().get(j).getId() == Match.getAllPlayers().get(i).teamId) {
                                    System.out.println("After the rebound the ball possesion is " + Match.getAllPlayers().get(i).teamId);
                                    System.out.println();
                                    Match.getTeams().get(j).setBallPos(true);
                                    break;
                                }
                            }
                            break;
                        }
                    }
                }

                if (Court.courtState[5][1] == 3) Court.courtState[5][1] = 0;
                if (Court.courtState[5][49] == 3) Court.courtState[5][49] = 0;

            }
        }
    }
                                                                        //Constructors
    public Player(int speed, int accuracy, int passing, int defence, int teamId, int playerCount) {
        this.ballPos = false;
        this.speed = speed;
        this.accuracy = accuracy;
        this.passing = passing;
        this.defence = defence;
        this.teamId = teamId;
        this.playerCount = playerCount;
        if (teamId == 1) {
            if (playerCount == 1) {
                this.x = 5;
                this.y = 15;
                Court.courtState[5][15] = 1;
            } else if (playerCount == 2) {
                this.x = 3;
                this.y = 17;
                Court.courtState[3][17] = 1;
            } else {
                this.x = 7;
                this.y = 17;
                Court.courtState[7][17] = 1;
            }
        } else {
            if (playerCount == 1) {
                this.x = 5;
                this.y = 35;
                Court.courtState[5][35] = 2;
            } else if (playerCount == 2) {
                this.x = 3;
                this.y = 33;
                Court.courtState[3][33] = 2;
            } else {
                this.x = 7;
                this.y = 33;
                Court.courtState[7][33] = 2;
            }
        }
        if (teamId == 1) {
            Match.getTeams().get(0).lineup.add(this);
        } else {
            Match.getTeams().get(1).lineup.add(this);
        }
        Match.getAllPlayers().add(this);
    }

    public Player(int teamId, int playerCount) {
        this.ballPos = false;
        this.teamId = teamId;
        this.playerCount = playerCount;
        if (teamId == 1) {
            if (playerCount == 1) {
                this.x = 5;
                this.y = 15;
                Court.courtState[5][15] = 1;
            } else if (playerCount == 2) {
                this.x = 3;
                this.y = 17;
                Court.courtState[3][17] = 1;
            } else {
                this.x = 7;
                this.y = 17;
                Court.courtState[7][17] = 1;
            }
        } else {
            if (playerCount == 1) {
                this.x = 5;
                this.y = 35;
                Court.courtState[5][35] = 2;
            } else if (playerCount == 2) {
                this.x = 3;
                this.y = 33;
                Court.courtState[3][33] = 2;
            } else {
                this.x = 7;
                this.y = 33;
                Court.courtState[7][33] = 2;
            }
        }
        if (teamId == 1) {
            Match.getTeams().get(0).lineup.add(this);
        } else {
            Match.getTeams().get(1).lineup.add(this);
        }
        Match.getAllPlayers().add(this);

    }

    public int getDestX() {
        return destX;
    }

    public void setDestX(int destX) {
        this.destX = destX;
    }

    public int getDestY() {
        return destY;
    }

    public void setDestY(int destY) {
        this.destY = destY;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public boolean isBallPos() {
        return ballPos;
    }

    public void setBallPos(boolean ballPos) {
        this.ballPos = ballPos;
    }

    public int getspeed() {
        return speed;
    }

    public void setSpeed(int szybkosc) {
        this.speed = szybkosc;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public int getPassing() {
        return passing;
    }

    public void setPassing(int passing) {
        this.passing = passing;
    }

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }
}

