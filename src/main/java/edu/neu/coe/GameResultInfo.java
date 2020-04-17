package edu.neu.coe;

public class GameResultInfo {

    private final String teamName;
    private final Long goalDifference;

    // Getters
    public String getTeamName() {
        return teamName;
    }

    public Long getGoalDifference() {
        return goalDifference;
    }

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
