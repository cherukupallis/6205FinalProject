public class Ranking implements Comparable<Ranking> {
    String team;
    Integer gamesPlayed;
    Integer score;

    public void setWonScore(){
        this.score += 3;
    }

    public void setDrawScore(){
        this.score += 1;
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
