package edu.neu.coe;

import java.util.Objects;

public class RankTable {

    String teamName;
    Double homeRank;
    Double awayRank;

    /**
     * Constructor for class
     * @param teamName String
     */
    public RankTable(String teamName) {
        this.teamName = teamName;
        this.homeRank = (double) 0;
        this.awayRank = (double) 0;
    }

    // Getter and setter
    public String getTeamName() {
        return teamName;
    }

    public Double getHomeRank() {
        return homeRank;
    }

    public void setHomeRank(Double homeRank) {
        this.homeRank += homeRank;
    }

    public Double getAwayRank() {
        return awayRank;
    }

    public void setAwayRank(Double awayRank) {
        this.awayRank += awayRank;
    }

    @Override
    public String toString() {
        return "RankTable {" +
                "teamName='" + teamName + '\'' +
                ", homeRank=" + homeRank +
                ", awayRank=" + awayRank +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RankTable)) return false;
        RankTable rankTable = (RankTable) o;
        return Objects.equals(getTeamName(), rankTable.getTeamName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTeamName(), getHomeRank(), getAwayRank());
    }
}
