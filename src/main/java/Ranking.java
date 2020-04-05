public class Ranking implements Comparable<Ranking> {
    String team;
    Integer gamesPlayed;
    Integer score;

    public void setScore( int score){
        this.score += score;
    }

    public void incrementGamesPlayed(){
        this.gamesPlayed += 1;
    }

    Ranking(String team, int gamesPlayed, int score){
        this.team = team;
        this.gamesPlayed = gamesPlayed;
        this.score = score;
    }

    @Override
    public String toString() {
        return "Ranking{" +
                "team='" + team + '\'' +
                ", gamesPlayed=" + gamesPlayed +
                ", score=" + score +
                '}';
    }

    @Override
    public int compareTo(Ranking o) {
        return this.score.compareTo(o.score);
    }
}
