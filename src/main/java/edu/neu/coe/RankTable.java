package edu.neu.coe;

import java.util.Objects;

public class RankTable {

    String teamName;
    Double homeRank;
    Double awayRank;

    Double mean, variance, sigma;

    public Double getMean() {
        return mean;
    }

    public void setMean(Double mean) {
        if (this.mean ==0 )
            this.mean = mean;
        else
            this.mean = (this.mean+mean)/2;
    }

    public Double getVariance() {
        return variance;
    }

    public void setVariance(Double variance) {
        if (this.variance == 0)
            this.variance = variance;
    }

    public Double getSigma() {
        return sigma;
    }

    public void setSigma(Double sigma) {
        if (this.sigma == 0){
            this.sigma = sigma;
        }else{
            this.sigma = Math.sqrt(this.variance*this.variance + sigma*sigma);
            this.variance= sigma;
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
        this.mean = (double)0;
        this.variance = (double)0;
        this.sigma = (double)0;
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
        return "RankTable{" +
                "teamName='" + teamName + '\'' +
                ", homeRank=" + homeRank +
                ", awayRank=" + awayRank +
                ", mean=" + mean +
                ", variance=" + variance +
                ", sigma=" + sigma +
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
