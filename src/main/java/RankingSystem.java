import java.util.*;

public class RankingSystem {

    final static String CURRENTRANKINGFILE = "src/data/CurrentRankings.json";
    final static String RANKINGRANKINGFILE = "src/data/RemainingGames.json";
    final static String[] fileNames = new String[]{"src/data/season0910.json","src/data/season1011.json","src/data/season1112.json",
            "src/data/season1213.json","src/data/season1314.json","src/data/season1415.json","src/data/season1516.json",
            "src/data/season1617.json","src/data/season1718.json","src/data/season1819.json"};

    public static void main(String[] args) {

        GamePredictor predictor = new GamePredictor();
        FetchData data = new FetchData();

        data.getDataFromFile(CURRENTRANKINGFILE);
        data.getDataFromFile(RANKINGRANKINGFILE);

        for (String file :fileNames)
            data.getDataFromFile(file);

        for (ArrayList<String> row : data.getRemainingGames()){
            String TeamA = row.get(0);
            String TeamB = row.get(1);
            System.out.println(TeamA + " vs " + TeamB);
            int score = predictor.getPrediction(TeamA,TeamB,data.getDataSet());
            System.out.println("Score will be : "+score);
            if (score == 3){
                data.getCurrentStanding().get(TeamA).setWonScore();
            }else if (score ==1) {
                data.getCurrentStanding().get(TeamA).setDrawScore();
                data.getCurrentStanding().get(TeamB).setDrawScore();
            } else {
                data.getCurrentStanding().get(TeamB).setWonScore();
            }
            data.getCurrentStanding().get(TeamA).incrementGamesPlayed();
            data.getCurrentStanding().get(TeamB).incrementGamesPlayed();
            System.out.println();
        }

        List<Ranking> teamRankings = new ArrayList<>(data.getCurrentStanding().values());
        teamRankings.sort(Collections.reverseOrder());
        for (Ranking team : teamRankings){
            System.out.println(team.toString());
        }
    }
}
