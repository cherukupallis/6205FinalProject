public class RankingInfo implements Comparable<RankingInfo> {
    String team;
    Integer gamesPlayed;
    Integer score;

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
    RankingInfo(String team, int gamesPlayed, int score){
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
    public int compareTo(RankingInfo o) {
        return this.score.compareTo(o.score);
    }
}
