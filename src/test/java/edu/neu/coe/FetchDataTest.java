package edu.neu.coe;

import org.json.simple.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

public class FetchDataTest {

    FetchData fetchData ;
    @Test
    public void getDataFromUnknowFileTest(){
        fetchData = new FetchData();
        fetchData.getDataFromFile("randompath");
    }

    @Test
    public void storeGameHistoryDataTest(){
        fetchData = new FetchData();
        JSONObject game = new JSONObject();
        game.put("HomeTeam","home");
        game.put("AwayTeam","away");
        game.put("FTHG",(long)1);
        game.put("FTAG",(long)2);
        fetchData.storeGameHistoryData(game);
        assertNotNull(fetchData.getHistoryData().get("home"));
    }

    @Test
    public void storeCurrentDataTest(){
        fetchData = new FetchData();
        JSONObject team = new JSONObject();
        team.put("team","team1");
        team.put("games","2");
        team.put("points","6");
        fetchData.storeCurrentData(team);
        assertNotNull(fetchData.getCurrentStanding().get("team1"));
    }

    @Test
    public void storeRemainingGamesDataTest(){
        fetchData = new FetchData();
        JSONObject team = new JSONObject();
        team.put("HomeTeam","team1");
        team.put("AwayTeam","team2");
        fetchData.storeRemainingGamesData(team);
        assertEquals(fetchData.getRemainingGames().size(),1);
    }

}
