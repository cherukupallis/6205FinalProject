package edu.neu.coe;

import org.junit.Test;
import static org.junit.Assert.*;

public class GameResultInfoTest {

    @Test
    public void GameResultTest(){
        GameResultInfo result = new GameResultInfo("team1",(long)4);
        assertEquals(result.getTeamName(),"team1");
        assertEquals(4, (long) result.getGoalDifference());
    }


}
