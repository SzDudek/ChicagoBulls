package statsOutput;

public class BoxScore {                             //class for the stats
    private static int team1Score;
    private static int team2Score;
    private static int scored1;
    private static int scored2;
    private static int missed1;
    private static int missed2;
    private static int for3_1;
    private static int for3_2;
    private static int steal1;
    private static int steal2;

    public BoxScore() {
        team1Score = 0;
        team2Score = 0;
        scored1 = 0;
        scored2 = 0;
        missed1 = 0;
        missed2 = 0;
        for3_1 = 0;
        for3_2 = 0;
        steal1 = 0;
        steal2 = 0;
    }

    public static int getTeam1Score() {
        return team1Score;
    }

    public static void setTeam1Score(int pluspts) {
        team1Score += pluspts;
    }

    public static int getTeam2Score() {
        return team2Score;
    }

    public static void setTeam2Score(int pluspts) {
        team2Score += pluspts;
    }

    public static int getScored1() {
        return scored1;
    }

    public static void setScored1() {
        scored1 += 1;
    }

    public static int getScored2() {
        return scored2;
    }

    public static void setScored2() { scored2 += 1; }

    public static int getSteal1() {
        return steal1;
    }

    public static void setSteal1() {
        steal1 += 1;
    }

    public static int getSteal2() {
        return steal2;
    }

    public static void setSteal2() { steal2 += 1;}

    public static int getMissed1() { return missed1;}

    public static void setMissed1() { BoxScore.missed1 += 1;}

    public static int getMissed2() { return missed2;}

    public static void setMissed2() { BoxScore.missed2 += 1;}

    public static int getFor3_1() { return for3_1;}

    public static void setFor3_1() { BoxScore.for3_1 += 1;}

    public static int getFor3_2() { return for3_2;}

    public static void setFor3_2() { BoxScore.for3_2 += 1;}
}
