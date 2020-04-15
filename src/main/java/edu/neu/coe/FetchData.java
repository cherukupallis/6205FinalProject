package edu.neu.coe;

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

    private  HashMap<String, ArrayList<GameResultInfo>> historyData = new HashMap<>();
    private  ArrayList<ArrayList<String>> remainingGames = new ArrayList<>();
    private  HashMap<String, EPLStandings> currentStanding =  new HashMap<>();

    /**
     *  Returns the historyData object
     * @return historyData
     */
    public HashMap<String, ArrayList<GameResultInfo>> getHistoryData() {
        return historyData;
    }

    /**
     *  Returns the remainingGames object
     * @return remainingGames
     */
    public ArrayList<ArrayList<String>> getRemainingGames() {
        return remainingGames;
    }

    /**
     * Returns the currentStanding object
     * @return currentStanding
     */
    public  HashMap<String, EPLStandings> getCurrentStanding() {
        return currentStanding;
    }

    /**
     * Method to read data from a given filename and store
     *
     * @param fileName String
     */
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

    /**
     * Method to store the game object in the history data
     * map
     * @param game JSONObject
     */
    public  void storeGameHistoryData(JSONObject game){
        String hometeam = (String) game.get(HOMETEAM);
        String awayteam = (String) game.get(AWAYTEAM);
        Long homescore = (Long) game.get(HOMETEAMGOALS);
        Long awayscore = (Long) game.get(AWAYTEAMGOALS) ;
        GameResultInfo gameResult = new GameResultInfo(awayteam, (homescore- awayscore));
        if (historyData.get(hometeam)!= null){
            historyData.get(hometeam).add(gameResult);
        }else{
            ArrayList<GameResultInfo> games = new ArrayList<>();
            games.add(gameResult);
            historyData.put(hometeam,games);
        }
    }

    /**
     * Method to store the standings object in the current standing
     * list
     * @param standing JSONObject that contains team name, games played and score details
     */
    public void storeCurrentData(JSONObject standing){
        String team = (String) standing.get(TEAM);
        int games = Integer.parseInt((String)standing.get(GAMES));
        int score = Integer.parseInt((String)standing.get(POINTS));
        EPLStandings teamRanking = new EPLStandings(team,games,score);
        currentStanding.put(team,teamRanking);
    }

    /**
     * Method to store the matches object in the remaining games
     * map
     * @param matches JSONObject that contains home team and away team names
     */
    public void storeRemainingGamesData(JSONObject matches){
        String homeTeam = (String) matches.get(HOMETEAM);
        String awayTeam = (String) matches.get(AWAYTEAM);
        ArrayList<String> row = new ArrayList<>();
        row.add(homeTeam);
        row.add(awayTeam);
        getRemainingGames().add(row);
    }

}
