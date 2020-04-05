import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class FetchData {

    JSONParser jsonParser = new JSONParser();
    FileReader reader ;

    private  HashMap<String, ArrayList<GameResult>> dataSet = new HashMap<>();
    private  ArrayList<ArrayList<String>> remainingGames = new ArrayList<>();
    private  HashMap<String, ArrayList<Integer>>currentStanding =  new HashMap<>();

    public HashMap<String, ArrayList<GameResult>> getDataSet() {
        return dataSet;
    }

    public ArrayList<ArrayList<String>> getRemainingGames() {
        return remainingGames;
    }

    public  HashMap<String, ArrayList<Integer>> getCurrentStanding() {
        return currentStanding;
    }

    public void getDataFromFile(String fileName){
        try {
            reader = new FileReader(fileName);
            Object obj = jsonParser.parse(reader);
            JSONArray data = (JSONArray) obj;
            if (fileName.contains("CurrentRankings"))
                data.forEach( standing -> storeCurrentData( (JSONObject) standing ) );
            else if (fileName.contains("RemainingGames"))
                data.forEach( matches -> storeRemainingGamesData( (JSONObject) matches ) );
            else
                data.forEach( game -> storeGameHistoryData( (JSONObject) game ) );
        } catch (IOException | ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    public  void storeGameHistoryData(JSONObject game){
        String hometeam = (String) game.get("HomeTeam");
        String awayteam = (String) game.get("AwayTeam");
        Long homescore = (Long) game.get("FTHG");
        Long awayscore = (Long) game.get("FTAG") ;
        GameResult gameResult = new GameResult(awayteam, (homescore- awayscore));
        if (dataSet.get(hometeam)!= null){
            dataSet.get(hometeam).add(gameResult);
        }else{
            ArrayList<GameResult> games = new ArrayList<>();
            games.add(gameResult);
            dataSet.put(hometeam,games);
        }
    }

    public void storeCurrentData(JSONObject standing){
        String team = (String) standing.get("team");
        int games = Integer.parseInt((String)standing.get("games"));
        int score = Integer.parseInt((String)standing.get("points"));
        ArrayList<Integer> teamRanking= new ArrayList<>();
        teamRanking.add(score);
        teamRanking.add(games);
        currentStanding.put(team,teamRanking);
    }

    public void storeRemainingGamesData(JSONObject matches){
        String homeTeam = (String) matches.get("HomeTeam");
        String awayTeam = (String) matches.get("AwayTeam");
        ArrayList<String> row = new ArrayList<>();
        row.add(homeTeam);
        row.add(awayTeam);
        getRemainingGames().add(row);
    }

}
