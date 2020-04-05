import java.util.*;

public class RankingSystem {

    final static String CURRENTRANKINGFILE = "src/data/CurrentRankings.json";
    final static String REMAININGGAMESFILE = "src/data/RemainingGames.json";
    final static String[] fileNames = new String[]{"src/data/season0910.json","src/data/season1011.json","src/data/season1112.json",
            "src/data/season1213.json","src/data/season1314.json","src/data/season1415.json","src/data/season1516.json",
            "src/data/season1617.json","src/data/season1718.json","src/data/season1819.json"};

    public static void main(String[] args) {

        GamePredictor predictor = new GamePredictor();
        FetchData fetchData = new FetchData();

        fetchData.getDataFromFile(CURRENTRANKINGFILE);
        fetchData.getDataFromFile(REMAININGGAMESFILE);

        for (String file :fileNames)
            fetchData.getDataFromFile(file);

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
                fetchData.getCurrentStanding().get(TeamB).setScore(score);
            }
            fetchData.getCurrentStanding().get(TeamA).incrementGamesPlayed();
            fetchData.getCurrentStanding().get(TeamB).incrementGamesPlayed();
            System.out.println();
        }

        List<Ranking> teamRankings = new ArrayList<>(fetchData.getCurrentStanding().values()); //rethink data structure
        teamRankings.sort(Collections.reverseOrder());
        for (Ranking team : teamRankings){
            System.out.println(team.toString());
        }
    }
}
