package edu.neu.coe;

import java.util.Objects;

public class RankTable {

    String teamName;
    Double homeRank;
    Double awayRank;

    Long mean, variance, sigma;

    public Long getMean() {
        return mean;
    }

    public void setMean(Long mean) {
        if (this.mean ==0 )
            this.mean = mean;
        else
            this.mean = (this.mean+mean)/2;
    }

    public Long getVariance() {
        return variance;
    }

    public void setVariance(Long variance) {
        if (this.variance == 0)
            this.variance = variance;
        else
            this.variance += variance;
    }

    public Long getSigma() {
        return sigma;
    }

    public void setSigma(Long sigma) {
        if (this.sigma == 0){
            this.sigma = sigma;
        }else{
            this.sigma = (long) Math.sqrt(this.variance);
        }

    }

    /**
     * Constructor for class
     * @param teamName String
     */
    public RankTable(String teamName) {
        this.teamName = teamName;
        this.homeRank = (double) 0;
        this.awayRank = (double) 0;
        this.mean = (long)0;
        this.variance = (long)0;
        this.sigma = (long)0;
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
                ", homeRank=" + homeRank+
                ", awayRank=" + awayRank+
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
