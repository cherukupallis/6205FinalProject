import org.json.simple.JSONArray;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class RankingSystem {

    final static String CURRENTRANKINGFILE = "src/data/CurrentRankings.json";
    final static String REMAININGGAMESFILE = "src/data/RemainingGames.json";
    final static String[] fileNames = new String[]{"src/data/season0910.json","src/data/season1011.json","src/data/season1112.json",
            "src/data/season1213.json","src/data/season1314.json","src/data/season1415.json","src/data/season1516.json",
            "src/data/season1617.json","src/data/season1718.json","src/data/season1819.json"};
    final static String OUTPUTPATH = "src/out/final.json";

    /**
     * Method to initialise current rankings, remaining games
     * and prepare the dataset
     *
     * @param fetchData FetchData
     */
    private static void initializeData(FetchData fetchData) {
        fetchData.getDataFromFile(CURRENTRANKINGFILE);
        fetchData.getDataFromFile(REMAININGGAMESFILE);

        for (String file :fileNames)
            fetchData.getDataFromFile(file);
    }

    /**
     * Method iterates over the remaining games in the season,
     * predicts the winner and updates the current standings with the result
     *
     * @param predictor GamePredictor
     * @param fetchData FetchData
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
     * @param fetchData FetchData
     * @return List<RankingInfo>
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
     * @param teamRankings List<RankingInfo>
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
