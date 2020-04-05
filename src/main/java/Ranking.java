public class Ranking {
    String team;
    int gamesPlayed;
    int score;

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
}
