package edu.neu.coe;

import org.json.simple.JSONArray;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class RankingSystem {

    private final static String DATASETPATH = "src/dataset/";
    private final static String CURRENTRANKINGFILE = DATASETPATH+"CurrentRankings.json";
    private final static String REMAININGGAMESFILE = DATASETPATH+ "RemainingGames.json";
    private final static String[] fileNames = new String[]{"season0910.json","season1011.json","season1112.json",
            "season1213.json","season1314.json","season1415.json","season1516.json",
            "season1617.json","season1718.json","season1819.json"};
    private final static String OUTPUTPATH = "src/output/final.json";


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
     * @param rankings HashMap<>
     * @param fetchData edu.neu.coe.FetchData
     */
    private static void predictMatches( HashMap<String,RankTable> rankings,FetchData fetchData) {
        for (ArrayList<String> teamsPlaying : fetchData.getRemainingGames()){
            String TeamA = teamsPlaying.get(0);
            String TeamB = teamsPlaying.get(1);
            if (rankings.get(TeamA).getHomeRank()>rankings.get(TeamB).getAwayRank()){
                fetchData.getCurrentStanding().get(TeamA).setScore(3);
            }else if (rankings.get(TeamA).getHomeRank()<rankings.get(TeamB).getAwayRank()) {
                fetchData.getCurrentStanding().get(TeamB).setScore(3);
            } else {
                fetchData.getCurrentStanding().get(TeamA).setScore(1);
                fetchData.getCurrentStanding().get(TeamB).setScore(1);
            }
            fetchData.getCurrentStanding().get(TeamA).incrementGamesPlayed();
            fetchData.getCurrentStanding().get(TeamB).incrementGamesPlayed();
        }
    }

    /**
     * Method that sorts the current standings according the score
     * @param fetchData edu.neu.coe.FetchData
     * @return List<RankingInfo>
     */
    private static List<EPLStandings> sortBasedOnScore(FetchData fetchData) {
        List<EPLStandings> teamRankings = new ArrayList<>(fetchData.getCurrentStanding().values()); //rethink data structure
        teamRankings.sort(Collections.reverseOrder());
        for (EPLStandings team : teamRankings){
            System.out.println(team.toString());
        }
        return teamRankings;
    }

    /**
     * Method to store the List into a file
     *
     * @param teamRankings List<RankingInfo>
     */
    private static void storeResult(List<EPLStandings> teamRankings) {
        JSONArray finalRanking = new JSONArray();
        finalRanking.addAll(teamRankings);

        try (FileWriter file = new FileWriter(OUTPUTPATH)) {
            file.write(finalRanking.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to Rank all the teams currently playing the season
     *
     * @param rankings HashMap<>
     * @param predictor GamePredictor
     * @param fetchData FetchData
     */
    private static void RankTeams(HashMap<String,RankTable> rankings,GamePredictor predictor,FetchData fetchData ){

        for (String teamA : fetchData.getCurrentStanding().keySet()){
            RankTable teamARanking;
            if (rankings.get(teamA)!=null)
                teamARanking = rankings.get(teamA);
            else
                teamARanking = new RankTable(teamA);
            for (String teamB :fetchData.getCurrentStanding().keySet()){
                if (!teamA.equals(teamB)){
                    RankTable teamBRanking;
                    if (rankings.get(teamB)!=null)
                        teamBRanking = rankings.get(teamB);
                    else
                        teamBRanking = new RankTable(teamB);
                    predictor.calculateMeanSigma(teamARanking,teamBRanking,fetchData.getHistoryData());
                    rankings.put(teamB,teamBRanking);
                }
            }
            rankings.put(teamA,teamARanking);
        }
    }

    public static void main(String[] args) throws IOException {

        GamePredictor predictor = new GamePredictor();
        FetchData fetchData = new FetchData();
        HashMap<String,RankTable> rankings = new HashMap<>();

        initializeData(fetchData);
        RankTeams(rankings,predictor, fetchData);
        predictMatches(rankings,fetchData);
        List<EPLStandings> teamRankings = sortBasedOnScore(fetchData);
        storeResult(teamRankings);
    }
}
