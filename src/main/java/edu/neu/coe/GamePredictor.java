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
                if(away){
                    ea = 1/(1+Math.pow(10, (teamA.getAwayRank()-teamB.getHomeRank())/400));
                    newrank = teamA.getAwayRank() + 2*(games.getGoalDifference()-ea);
                }else{
                    ea = 1/(1+Math.pow(10, (teamA.getHomeRank()-teamB.getAwayRank())/400));
                    newrank = teamA.getHomeRank() + 2*(games.getGoalDifference()-ea);
                }
                teamA.setHomeRank(newrank);
                sum += games.getGoalDifference();
                noOfGames++;
            }
        }
        teamA.setMean((double) (sum / noOfGames));
        sum = 0L;
        for (int i = 0; i < result.size(); i++) {
            double diff = teamA.getMean() - result.get(i);
            result.set(i, (long) (diff * diff));
            sum += result.get(i);
        }
        teamA.setVariance((double)sum);
        teamA.setSigma((double)sum);
        if(away)
            teamA.setAwayRank((teamA.getSigma() - 3*teamA.getMean()));
        else
            teamA.setHomeRank((teamA.getSigma() - 3*teamA.getMean()));

    }
}
