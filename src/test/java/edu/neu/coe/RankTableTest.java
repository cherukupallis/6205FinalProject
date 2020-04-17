package edu.neu.coe;
import org.junit.Test;
import static org.junit.Assert.*;

public class RankTableTest {

    @Test
    public void setValues(){
        RankTable team1 = new RankTable("team1");
        team1.setMean(0.2d);
        team1.setVariance(2.56d);
        team1.setSigma(1.6d);

        assertEquals(0.2,team1.getMean(),0.2);
        assertEquals(2.56,team1.getVariance(),0.2);
        assertEquals(1.6,team1.getSigma(),0.2);
    }

    @Test
    public void setMultipleValues(){
        RankTable team1 = new RankTable("team1");
        team1.setMean(0.2d);
        team1.setVariance(2.56d);
        team1.setSigma(1.6d);

        assertEquals(0.2,team1.getMean(),0.2);
        assertEquals(2.56,team1.getVariance(),0.2);
        assertEquals(1.6,team1.getSigma(),0.2);

        team1.setMean(1d);
//        team1.setVariance();
        team1.setSigma(1.2d);

        assertEquals(0.6,team1.getMean(),0.2);
        assertEquals(2.78,team1.getSigma(),0.2);

    }
}
