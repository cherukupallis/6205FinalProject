import java.util.ArrayList;
import java.util.HashMap;

public class RankingSystem {

    public static void main(String[] args) {
        String[] fileNames = new String[]{"src/data/season0910.json","src/data/season1011.json","src/data/season1112.json",
                "src/data/season1213.json","src/data/season1314.json","src/data/season1415.json","src/data/season1516.json",
                "src/data/season1617.json","src/data/season1718.json","src/data/season1819.json"};
        GamePredictor predictor = new GamePredictor();
        FetchData data = new FetchData();

        data.getDataFromFile("src/data/CurrentRankings.json");
        data.getDataFromFile("src/data/RemainingGames.json");
        for (String file :fileNames)
            data.getDataFromFile(file);

//        HashMap<String,Integer> teams = new HashMap<>();
        for (ArrayList<String> row : data.getRemainingGames()){
//            teams.merge(row.get(0),1,Integer::sum);
//            teams.merge(row.get(1),1,Integer::sum);

            System.out.println(row.get(0) + " vs " + row.get(1));
            int score = predictor.getPrediction(row.get(0),row.get(1),data.getDataSet());
            System.out.println("Score will be : "+score);
            if (score == 3){
                int previousScore =  data.getCurrentStanding().get(row.get(0)).get(0);
                data.getCurrentStanding().get(row.get(0)).set(0,previousScore+3);
            }else if (score ==1) {
                int previousScoreHT =  data.getCurrentStanding().get(row.get(0)).get(0);
                int previousScoreAT =  data.getCurrentStanding().get(row.get(1)).get(0);
                data.getCurrentStanding().get(row.get(0)).set(0,previousScoreHT+1);
                data.getCurrentStanding().get(row.get(1)).set(0,previousScoreAT+1);
            } else {
                int previousScore =  data.getCurrentStanding().get(row.get(1)).get(0);
                data.getCurrentStanding().get(row.get(1)).set(0,previousScore+3);
            }
            int matchHT =  data.getCurrentStanding().get(row.get(0)).get(1);
            int matchAT =  data.getCurrentStanding().get(row.get(1)).get(1);

            data.getCurrentStanding().get(row.get(0)).set(1,matchHT+1);
            data.getCurrentStanding().get(row.get(1)).set(1,matchAT+1);
            System.out.println();
        }


        for (String team : data.getCurrentStanding().keySet()){
            System.out.println(team + " : points : " + data.getCurrentStanding().get(team).get(0)
                    + " matches played : "+ data.getCurrentStanding().get(team).get(1) );
        }
    }
}
