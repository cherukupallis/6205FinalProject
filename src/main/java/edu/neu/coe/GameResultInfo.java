package edu.neu.coe;

public class GameResultInfo {

    String teamName;
    Long goalDifference;

    /**
     * Constructor for class
     * @param teamName String
     * @param goalDifference Long
     */
    GameResultInfo(String teamName, Long goalDifference){
        this.goalDifference = goalDifference;
        this.teamName = teamName;
    }

    @Override
    public String toString() {
        return "GameResult{" +
                "teamName='" + teamName + '\'' +
                ", goalDifference=" + goalDifference +
                '}';
    }
}
