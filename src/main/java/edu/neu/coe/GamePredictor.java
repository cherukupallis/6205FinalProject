package edu.neu.coe;

import java.util.ArrayList;
import java.util.HashMap;

public class GamePredictor {

    private int win =0, draw = 0, total = 0;
    double winper, drawper, lossper;

    HashMap<String, ArrayList<GameResultInfo>> dataSet ;

    /**
     * Method to calculate the probability of team winning/losing when playing
     * an away game
     *
     * @param awayTeam String
     * @param homeTeam String
     * @param dataSet  HashMap<String, ArrayList<GameResultInfo>>
     * @return int
     */
    public int getAwayPrediction(String awayTeam, String homeTeam, HashMap<String, ArrayList<GameResultInfo>> dataSet){
        this.dataSet = dataSet;
        win =draw = total =0;
        try {
            for (GameResultInfo game : dataSet.get(awayTeam)) {
                if (game.getTeamName().equals(homeTeam)) {
                    if (game.getGoalDifference() < 0)
                        win++;
                    else if (game.getGoalDifference() == 0)
                        draw++;
                    total++;
                }
            }
        }catch (Exception e){
            System.out.println("No Data");
        }
        return scoreCalculator();
    }


    /**
     * Method to calculate the score for a team
     *
     * @return int
     */
    private int scoreCalculator(){
        winper = (double) win/total;
        drawper = (double) draw/total;
        lossper = (double)(total-draw-win)/total;
        if ( winper > drawper && winper > lossper)
            return 3;
        if ( drawper > winper && drawper > lossper)
            return 1;
        return 0;
    }

    /**
     * Method to calculate the probability of team winning/losing when playing
     * an home game
     *
     * @param awayTeam String
     * @param homeTeam String
     * @param dataSet  HashMap<String, ArrayList<GameResultInfo>>
     * @return int
     */
    public int getPrediction(String homeTeam, String awayTeam, HashMap<String, ArrayList<GameResultInfo>> dataSet){
        this.dataSet = dataSet;
        win =draw = total =0;
        try {
            for (GameResultInfo game : dataSet.get(homeTeam)) {
                if (game.getTeamName().equals(awayTeam)) {
                    if (game.getGoalDifference() > 0)
                        win++;
                    else if (game.getGoalDifference() == 0)
                        draw++;
                    total++;
                }
            }
        }catch (Exception e){
            System.out.println("No Data");
        }
        return scoreCalculator();
    }
}
