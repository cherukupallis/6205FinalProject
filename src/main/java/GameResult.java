public class GameResult {

    String teamName;
    Long goalDifference;
    GameResult( String teamName, Long goalDifference){
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
