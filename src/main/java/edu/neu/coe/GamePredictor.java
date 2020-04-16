package edu.neu.coe;

import java.util.ArrayList;
import java.util.HashMap;

public class GamePredictor {

    public void calculateMeanSigma(RankTable homeTeam, RankTable awayTeam, HashMap<String, ArrayList<GameResultInfo>> dataSet){
        try{
            updateRankingTable(homeTeam, awayTeam, dataSet,false);
            updateRankingTable(awayTeam, homeTeam, dataSet,true);
        }catch (Exception ignored){

        }
    }

    private void updateRankingTable(RankTable teamA, RankTable teamB, HashMap<String, ArrayList<GameResultInfo>> dataSet,boolean away) {

        ArrayList<Long> result = new ArrayList<>();
        Long sum = 0L;
        int noOfGames=0;
        double ea;
        double newrank;
        for (GameResultInfo games : dataSet.get(teamA.getTeamName())) {
            if (games.getTeamName().equals(teamB.getTeamName())) {
                result.add(games.getGoalDifference());
                sum += games.getGoalDifference();
                noOfGames++;
            }
        }
        teamA.setMean((sum / noOfGames));
        sum = 0L;
        for (int i = 0; i < result.size(); i++) {
            double diff = teamA.getMean() - result.get(i);
            result.set(i, (long) (diff * diff));
            sum += result.get(i);
        }
        teamA.setVariance(sum);
        teamA.setSigma((long) Math.sqrt(sum));
        if(away)
            teamA.setAwayRank((double) (teamA.getSigma() - 2*teamA.getMean()));
        else
            teamA.setHomeRank((double) (teamA.getSigma() - 3*teamA.getMean()));

    }
}
