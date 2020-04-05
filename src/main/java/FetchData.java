import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class FetchData {

    final String HOMETEAM = "HomeTeam";
    final String AWAYTEAM = "AwayTeam";
    final String HOMETEAMGOALS = "FTHG";
    final String AWAYTEAMGOALS = "FTAG";
    final String CURRENTRANKINGS = "CurrentRankings";
    final String REMAININGGAMES = "RemainingGames";
    final String TEAM = "team";
    final String GAMES = "games";
    final String POINTS ="points";

    JSONParser jsonParser = new JSONParser();
    FileReader reader ;

    private  HashMap<String, ArrayList<GameResult>> historyData = new HashMap<>();
    private  ArrayList<ArrayList<String>> remainingGames = new ArrayList<>();
    private  HashMap<String,Ranking > currentStanding =  new HashMap<>();

    public HashMap<String, ArrayList<GameResult>> getHistoryData() {
        return historyData;
    }

    public ArrayList<ArrayList<String>> getRemainingGames() {
        return remainingGames;
    }

    public  HashMap<String, Ranking > getCurrentStanding() {
        return currentStanding;
    }

    public void getDataFromFile(String fileName){
        try {
            reader = new FileReader(fileName);
            Object obj = jsonParser.parse(reader);
            JSONArray data = (JSONArray) obj;
            if (fileName.contains(CURRENTRANKINGS))
                data.forEach( standing -> storeCurrentData( (JSONObject) standing ) );
            else if (fileName.contains(REMAININGGAMES))
                data.forEach( matches -> storeRemainingGamesData( (JSONObject) matches ) );
            else
                data.forEach( game -> storeGameHistoryData( (JSONObject) game ) );
        } catch (IOException | ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    public  void storeGameHistoryData(JSONObject game){
        String hometeam = (String) game.get(HOMETEAM);
        String awayteam = (String) game.get(AWAYTEAM);
        Long homescore = (Long) game.get(HOMETEAMGOALS);
        Long awayscore = (Long) game.get(AWAYTEAMGOALS) ;
        GameResult gameResult = new GameResult(awayteam, (homescore- awayscore));
        if (historyData.get(hometeam)!= null){
            historyData.get(hometeam).add(gameResult);
        }else{
            ArrayList<GameResult> games = new ArrayList<>();
            games.add(gameResult);
            historyData.put(hometeam,games);
        }
    }

    public void storeCurrentData(JSONObject standing){
        String team = (String) standing.get(TEAM);
        int games = Integer.parseInt((String)standing.get(GAMES));
        int score = Integer.parseInt((String)standing.get(POINTS));
        Ranking teamRanking = new Ranking(team,games,score);
        currentStanding.put(team,teamRanking);
    }

    public void storeRemainingGamesData(JSONObject matches){
        String homeTeam = (String) matches.get(HOMETEAM);
        String awayTeam = (String) matches.get(AWAYTEAM);
        ArrayList<String> row = new ArrayList<>();
        row.add(homeTeam);
        row.add(awayTeam);
        getRemainingGames().add(row);
    }

}
