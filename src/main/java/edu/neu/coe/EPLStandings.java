package edu.neu.coe;

public class EPLStandings implements Comparable<EPLStandings> {

    private final String team;
    private Integer gamesPlayed;
    private Integer score;

    /**
     * Method to update the score variable
     * @param score int
     */
    public void setScore( int score){
        this.score += score;
    }

    /**
     * Method to update the gamesPlayed variable by 1
     */
    public void incrementGamesPlayed(){
        this.gamesPlayed += 1;
    }

    /**
     * Constructor for class
     * @param team String
     * @param gamesPlayed int
     * @param score int
     */
    EPLStandings(String team, int gamesPlayed, int score){
        this.team = team;
        this.gamesPlayed = gamesPlayed;
        this.score = score;
    }

    @Override
    public String toString() {
        return " {" +
                "\"team\" : \"" + team + "\"," +
                "\"gamesPlayed\" : \"" + gamesPlayed + "\"," +
                "\"score\" : \"" + score + "\"" +
                '}';
    }

    @Override
    public int compareTo(EPLStandings o) {
        return this.score.compareTo(o.score);
    }
}
