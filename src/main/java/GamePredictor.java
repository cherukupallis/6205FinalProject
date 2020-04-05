import java.util.ArrayList;
import java.util.HashMap;

public class GamePredictor {

    int win =0;
    int draw = 0;
    int total = 0;

    HashMap<String, ArrayList<GameResult>> dataSet ;

    public void getAwayPrediction(String awayTeam, String homeTeam){
        try {
            for (GameResult game : dataSet.get(awayTeam)) {
                if (game.teamName.equals(homeTeam)) {
                    if (game.goalDifference < 0)
                        win++;
                    else if (game.goalDifference == 0)
                        draw++;
                    total++;
                }
            }
        }catch (Exception e){
            System.out.println("No Data");
        }
    }

    public void getHomePrediction(String homeTeam,String awayTeam){
        try {
            for (GameResult game : dataSet.get(homeTeam)) {
                if (game.teamName.equals(awayTeam)) {
                    if (game.goalDifference > 0)
                        win++;
                    else if (game.goalDifference == 0)
                        draw++;
                    total++;
                }
            }
        }catch (Exception e){
            System.out.println("No Data");
        }
    }

    public int getPrediction(String homeTeam, String awayTeam, HashMap<String, ArrayList<GameResult>> dataSet){
        this.dataSet = dataSet;
        win = 0;
        draw = 0 ;
        total = 0 ;
//        getAwayPrediction(homeTeam, awayTeam);
        getHomePrediction(homeTeam, awayTeam);
        double winper = (double) win/total;
        double drawper = (double) draw/total;
        double lossper = (double)(total-draw-win)/total;
        System.out.println(winper + " draw : "+ drawper + " loss :"+ lossper);
        if ( winper > drawper && winper > lossper)
            return 3;
        if ( drawper > winper && drawper > lossper)
            return 1;
        return 0;
    }
}
