package match;

import statsOutput.BoxScore;

import java.util.Arrays;

public class Court {
    public static int[][] courtState = new int[11][51];
    public static String[][] visual = new String[11][51];

    public static void initField() {            //Initialize the visuals

        for (int i = 0; i < 11; i++) {
            visual[i][0] = "|";
            visual[i][50] = "|";
            for (int k = 1; k < 50; k++) {
                visual[i][k] = " ";
            }
            visual[i][25] = "|";

        }
        visual[5][24] = "(";
        visual[5][26] = ")";

        visual[5][1] = "O";
        visual[5][49] = "O";

        Arrays.fill(visual[0], "-");
        Arrays.fill(visual[10], "-");
    }

    public static void displayField() {         //display the visual of the court
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 51; j++) {
                if (courtState[i][j] == 0) {
                    System.out.print(visual[i][j]);
                } else if (courtState[i][j] == 3) {
                    System.out.print(Match.ANSI_RED + "3" + Match.ANSI_RESET);
                } else if (courtState[i][j] == 1) {
                    if (Ball.getX() == i && Ball.getY() == j) {
                        System.out.print(Match.ANSI_RED + Match.teams.get(0).getSymbol() + Match.ANSI_RESET);
                    } else {
                        System.out.print(Match.teams.get(0).getSymbol());
                    }
                } else {
                    if (Ball.getX() == i && Ball.getY() == j) {
                        System.out.print(Match.ANSI_RED + Match.teams.get(1).getSymbol() + Match.ANSI_RESET);
                    } else {
                        System.out.print(Match.teams.get(1).getSymbol());
                    }
                }
            }
            System.out.print("   ");

            if (i == 0) System.out.print("BOX SCORE");
            if (i == 1)
                System.out.print("| " + Match.teams.get(0).getName() + " | " + Match.teams.get(1).getName() + " |");
            if (i == 2) {
                System.out.print("| ");
                if (BoxScore.getTeam1Score() < 10) {
                    System.out.print("0");
                    System.out.print(BoxScore.getTeam1Score());
                } else System.out.print(BoxScore.getTeam1Score());
                System.out.print(" | ");
                if (BoxScore.getTeam2Score() < 10) {
                    System.out.print("0");
                    System.out.print(BoxScore.getTeam2Score());
                } else System.out.print(BoxScore.getTeam2Score());
                System.out.print(" |");
            }
            System.out.println();
        }
    }
}

