package edu.neu.coe;

import org.json.simple.JSONArray;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class RankingSystem {

    final static String DATASETPATH = "src/dataset/";
    final static String CURRENTRANKINGFILE = DATASETPATH+"CurrentRankings.json";
    final static String REMAININGGAMESFILE = DATASETPATH+ "RemainingGames.json";
    final static String[] fileNames = new String[]{"season0910.json","season1011.json","season1112.json",
            "season1213.json","season1314.json","season1415.json","season1516.json",
            "season1617.json","season1718.json","season1819.json"};
    final static String OUTPUTPATH = "src/out/final.json";

    /**
     * Method to initialise current rankings, remaining games
     * and prepare the dataset
     *
     * @param fetchData edu.neu.coe.FetchData
     */
    private static void initializeData(FetchData fetchData) {
        fetchData.getDataFromFile(CURRENTRANKINGFILE);
        fetchData.getDataFromFile(REMAININGGAMESFILE);

        for (String file :fileNames)
            fetchData.getDataFromFile(DATASETPATH+file);
    }

    /**
     * Method iterates over the remaining games in the season,
     * predicts the winner and updates the current standings with the result
     *
     * @param predictor edu.neu.coe.GamePredictor
     * @param fetchData edu.neu.coe.FetchData
     */
    private static void predictMatches(GamePredictor predictor, FetchData fetchData) {
        for (ArrayList<String> teamsPlaying : fetchData.getRemainingGames()){
            String TeamA = teamsPlaying.get(0);
            String TeamB = teamsPlaying.get(1);
            System.out.println(TeamA + " vs " + TeamB);
            int score = predictor.getPrediction(TeamA,TeamB,fetchData.getHistoryData()); //replace by enum
            System.out.println("Score will be : "+score);
            if (score == 3){
                fetchData.getCurrentStanding().get(TeamA).setScore(score);
            }else if (score == 1) {
                fetchData.getCurrentStanding().get(TeamA).setScore(score);
                fetchData.getCurrentStanding().get(TeamB).setScore(score);
            } else {
                fetchData.getCurrentStanding().get(TeamB).setScore(score+3);
            }
            fetchData.getCurrentStanding().get(TeamA).incrementGamesPlayed();
            fetchData.getCurrentStanding().get(TeamB).incrementGamesPlayed();
            System.out.println();
        }
    }

    /**
     * Method that sorts the current standings according the score
     * @param fetchData edu.neu.coe.FetchData
     * @return List<edu.neu.coe.RankingInfo>
     */
    private static List<RankingInfo> sortBasedOnScore(FetchData fetchData) {
        List<RankingInfo> teamRankings = new ArrayList<>(fetchData.getCurrentStanding().values()); //rethink data structure
        teamRankings.sort(Collections.reverseOrder());
        for (RankingInfo team : teamRankings){
            System.out.println(team.toString());
        }
        return teamRankings;
    }

    /**
     * Method to store the List into a file
     *
     * @param teamRankings List<edu.neu.coe.RankingInfo>
     */
    private static void storeResult(List<RankingInfo> teamRankings) {
        JSONArray finalRanking = new JSONArray();
        finalRanking.addAll(teamRankings);

        try (FileWriter file = new FileWriter(OUTPUTPATH)) {
            file.write(finalRanking.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        GamePredictor predictor = new GamePredictor();
        FetchData fetchData = new FetchData();

        initializeData(fetchData);

        predictMatches(predictor, fetchData);

        List<RankingInfo> teamRankings = sortBasedOnScore(fetchData);

        storeResult(teamRankings);
    }

}
