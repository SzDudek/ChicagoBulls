package statsOutput;

import match.Match;

import java.io.FileWriter;
import java.io.IOException;

public class ToFile {                   //class for saving the data to file
    public static void Write() {
        try {
            FileWriter writer = new FileWriter("MatchStatistics.txt");
            writer.write("STATS\n");
            writer.write(Match.getTeams().get(0).getName() + " | " + Match.getTeams().get(1).getName() + "\n");
            writer.write("SCORE: " + BoxScore.getTeam1Score() + " : " + BoxScore.getTeam2Score() + "\n");
            writer.write("HIT: " + BoxScore.getScored1() + " : " + BoxScore.getScored2() + "\n");
            writer.write("MISSED: " + BoxScore.getMissed1() + " : " + BoxScore.getMissed2() + "\n");
            writer.write("TRIPLE: " + BoxScore.getFor3_1() + " : " + BoxScore.getFor3_2() + "\n");
            writer.write("STEALS: " + BoxScore.getSteal1() + " : " + BoxScore.getSteal2() + "\n");
            System.out.println("Stats were successfully saved to txt file");
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occured during saving the stats to the txt file");
            e.printStackTrace();
        }
    }
}
