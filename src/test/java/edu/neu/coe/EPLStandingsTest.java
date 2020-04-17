package edu.neu.coe;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class EPLStandingsTest {
    String team = "team1";
    int gamesPlayed = 1;
    int score = 3;

    @Test
    public void EPLStandingsTest(){
        EPLStandings eplStandings = new EPLStandings(team,gamesPlayed,score);
        String result =" {" +
                        "\"team\" : \"" + team + "\"," +
                        "\"gamesPlayed\" : \"" + gamesPlayed + "\"," +
                        "\"score\" : \"" + score + "\"" +
                        '}';
        assertEquals(eplStandings.toString(),result);
    }

    @Test
    public void incrementGamesPlayedTest(){
        EPLStandings eplStandings = new EPLStandings(team,gamesPlayed,score);
        eplStandings.incrementGamesPlayed();
        String result =" {" +
                "\"team\" : \"" + team + "\"," +
                "\"gamesPlayed\" : \"" + (gamesPlayed+1) + "\"," +
                "\"score\" : \"" + score + "\"" +
                '}';
        assertEquals(eplStandings.toString(),result);
    }

    @Test
    public void checkSetScoreTest(){
        EPLStandings eplStandings = new EPLStandings(team,gamesPlayed,score);
        eplStandings.setScore(1);
        String result =" {" +
                "\"team\" : \"" + team + "\"," +
                "\"gamesPlayed\" : \"" + gamesPlayed + "\"," +
                "\"score\" : \"" + (score+1)+ "\"" +
                '}';
        assertEquals(eplStandings.toString(),result);
    }

    @Test
    public void checkSetScoreIncorrectTest(){
        EPLStandings eplStandings = new EPLStandings(team,gamesPlayed,score);
        eplStandings.setScore(1);
        String result =" {" +
                "\"team\" : \"" + team + "\"," +
                "\"gamesPlayed\" : \"" + gamesPlayed + "\"," +
                "\"score\" : \"" + (score+3)+ "\"" +
                '}';
        assertNotEquals(eplStandings.toString(),result);
    }

    @Test
    public void compareScoresTest(){
        EPLStandings eplStandings = new EPLStandings(team,gamesPlayed,score);
        eplStandings.setScore(3);
        EPLStandings eplStandings1 = new EPLStandings(team+1,gamesPlayed,score);
        assertEquals(eplStandings.compareTo(eplStandings1),1);
    }

}
