package match;

import player.Beanpole;
import player.Custom;
import player.Speedy;
import player.BigGuy;
import player.Player;
import statsOutput.BoxScore;
import statsOutput.ToFile;

import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Match {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    static ArrayList<Team> teams = new ArrayList<>();
    static ArrayList<Player> allPlayers = new ArrayList<>();
    static int teamCount = 1;
    static ArrayList<Player> speedies = new ArrayList<>();
    static ArrayList<Player> beanpoles = new ArrayList<>();
    static ArrayList<Player> bigGuys = new ArrayList<>();
    static int roundPart = 1;

    public void whoseBall() {                   //notify about the possesion of the ball
        System.out.println("Match.Team 1: " + Match.teams.get(0).isBallPos() + " Match.Team 2: " + Match.teams.get(1).isBallPos());
        System.out.println("1: " + Match.teams.get(0).lineup.get(0).isBallPos() + " 2: " + Match.teams.get(0).lineup.get(1).isBallPos() + " 3: " + Match.teams.get(0).lineup.get(2).isBallPos());
        System.out.println("1: " + Match.teams.get(1).lineup.get(0).isBallPos() + " 2: " + Match.teams.get(1).lineup.get(1).isBallPos() + " 3: " + Match.teams.get(1).lineup.get(2).isBallPos());
    }

    public static void sort(ArrayList<Player> players) {            //sort the players by their speed
        int len = players.size();
        for (int i = 0; i < len - 1; i++) {
            for (int l = 0; l < len - i - 1; l++) {
                if (players.get(l).getspeed() < players.get(l + 1).getspeed()) {
                    Player temp = players.get(l);
                    players.set(l, players.get(l + 1));
                    players.set(l + 1, temp);
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String teamName;
        int accuracy, speed, defence, passing, playerChoice;
        Court.initField();
        Scanner scaner = new Scanner(System.in);                //Initial setup for the game
        System.out.println("Enter the name of 1st team: ");
        teamName = scaner.next();
        Team team1 = new Team(teamCount, teamName);
        teams.add(team1);
        teamCount++;
        System.out.println("Enter the name of 2nd team: ");
        teamName = scaner.next();
        Team team2 = new Team(teamCount, teamName);
        teams.add(team2);
        Ball ball = new Ball();
        BoxScore boxScore = new BoxScore();
        Court.displayField();
        for (int i = 1; i < 3; i++) {                   //choosing and creating the players
            for (int k = 1; k < 4; k++) {
                System.out.println("Select the player " + k + " of the team " + Match.teams.get(i - 1).getName() + " by typing the number :");
                System.out.println("1. playerClasses.Custom player - no special skill");
                System.out.println("2. playerClasses.Beanpole - special skill: dunk (100% accuracy shot from close range)");
                System.out.println("3. Speedie - special skill: turbo (double move in the round)");
                System.out.println("4. Big Guy - special skill: block (100% retake)");
                System.out.println("Your choice: ");
                choice: while (true){
                    playerChoice = scaner.nextInt();
                    switch (playerChoice) {
                        case 1:
                            if (i == 1) {
                                System.out.println("Enter skillrate of the player " + k + " of the team " + Match.teams.get(0).getName());
                            } else {
                                System.out.println("Enter skillrate of the player " + k + " of the team " + Match.teams.get(1).getName());
                            }
                            System.out.println("Enter shooting accuracy 0-100: ");
                            accuracy = scaner.nextInt();
                            System.out.println("Enter speed 0-100: ");
                            speed = scaner.nextInt();
                            System.out.println("Enter defense 0-100: ");
                            defence = scaner.nextInt();
                            System.out.println("Enter passing 0-100: ");
                            passing = scaner.nextInt();
                            new Custom(speed, accuracy, passing, defence, i, k);
                            System.out.println("playerClasses.Custom player has been successfully created");
                            sleep(1500);
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            break choice;
                        case 2:
                            System.out.println("playerClasses.Beanpole class player has been successfully created");
                            new Beanpole(i, k);
                            sleep(1500);
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            break choice;
                        case 3:
                            System.out.println("Speedie class player has been successfully created");
                            new Speedy(i, k);
                            sleep(1500);
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            break choice;
                        case 4:
                            System.out.println("Big Guy class player has been successfully created");
                            new BigGuy(i, k);
                            sleep(1500);
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            break choice;
                        default:
                            System.out.println("---Wrong number chosen, please enter your choice again---");
                    }
                }
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        }

        sort(allPlayers);

        Court.displayField();
        for (int z = 0; z < 6; z++) {               //activity log
            Match.allPlayers.get(z).moveDirection(Ball.getX(), Ball.getY());
        }
        while (boxScore.getTeam1Score() < 5 && boxScore.getTeam2Score() < 5) {
            //for(int i=0;i<50;i++){
            sleep(500);
            System.out.println("%%%%%%%%%%%%%%%%    Movement    %%%%%%%%%%%%%%%%%");
            roundPart = 1;
            for (int z = 0; z < 6; z++) {
                Match.allPlayers.get(z).move();
            }

            for (int z = 0; z < 6; z++) {
                Match.allPlayers.get(z).specialSkill();
            }
            roundPart = 2;

            System.out.println("%%%%%%%%%%%%%%%%   Pickup   %%%%%%%%%%%%%%%%%");
            for (int z = 0; z < 6; z++) {
                Match.allPlayers.get(z).pick(Ball.getX(), Ball.getY(), Match.teams.get(0).isBallPos(), Match.teams.get(1).isBallPos());
            }
            roundPart = 3;

            System.out.println("%%%%%%%%%%%%%%%%   Retake   %%%%%%%%%%%%%%%%%");
            for (int z = 0; z < 6; z++) {
                Match.allPlayers.get(z).retake(Ball.getX(), Ball.getY(), Court.courtState[Ball.getX()][Ball.getY()]);
            }
            for (int z = 0; z < 6; z++) {
                Match.allPlayers.get(z).specialSkill();
            }
            roundPart = 4;

            System.out.println("%%%%%%%%%%%%%%%%   Shoot    %%%%%%%%%%%%%%%%%");
            for (int z = 0; z < 6; z++) {
                Match.allPlayers.get(z).specialSkill();
            }
            for (int z = 0; z < 6; z++) {
                Match.allPlayers.get(z).shoot();
            }

            Court.displayField();
            sleep(100);
            System.out.println();
        }                           //saving the stats to the text file
        ToFile save = new ToFile();
        save.Write();
    }

    public static ArrayList<Team> getTeams() {
        return teams;
    }

    public static void setTeams(ArrayList<Team> teams) {
        Match.teams = teams;
    }

    public static ArrayList<Player> getAllPlayers() {
        return allPlayers;
    }

    public static void setAllPlayers(ArrayList<Player> allPlayers) {
        Match.allPlayers = allPlayers;
    }

    public static int getTeamCount() {
        return teamCount;
    }

    public static void setTeamCount(int teamCount) {
        Match.teamCount = teamCount;
    }

    public static ArrayList<Player> getSpeedies() {
        return speedies;
    }

    public static void setSpeedies(ArrayList<Player> speedies) {
        Match.speedies = speedies;
    }

    public static ArrayList<Player> getBeanpoles() {
        return beanpoles;
    }

    public static void setBeanpoles(ArrayList<Player> beanpoles) {
        Match.beanpoles = beanpoles;
    }

    public static ArrayList<Player> getBigGuys() {
        return bigGuys;
    }

    public static void setBigGuys(ArrayList<Player> bigGuys) {
        Match.bigGuys = bigGuys;
    }

    public static int getRoundPart() {
        return roundPart;
    }

    public static void setRoundPart(int roundPart) {
        Match.roundPart = roundPart;
    }
}

