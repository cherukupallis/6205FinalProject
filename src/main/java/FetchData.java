import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class FetchData {

    static class GameResult{
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

    private static HashMap<String, ArrayList<GameResult>> dataSet = new HashMap<>();

    public static void getData(String fileName){
        JSONParser jsonParser = new JSONParser();
        FileReader reader ;
        try {
            reader = new FileReader(fileName);
            Object obj = jsonParser.parse(reader);
            JSONArray gamesPlayed = (JSONArray) obj;
            gamesPlayed.forEach( game -> storeData( (JSONObject) game ) );
        } catch (IOException | ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void storeData(JSONObject game){
        String hometeam = (String) game.get("HomeTeam");
        String awayteam = (String) game.get("AwayTeam");
        Long homescore = (Long) game.get("FTHG");
        Long awayscore = (Long) game.get("FTAG") ;
        GameResult gameResult = new GameResult(awayteam, (homescore- awayscore));
        if (dataSet.get(hometeam)!= null){
            dataSet.get(hometeam).add(gameResult);
        }else{
            ArrayList<GameResult> games = new ArrayList<>();
            games.add(gameResult);
            dataSet.put(hometeam,games);
        }
    }

    public static void printDataSet(){
        for ( String key : dataSet.keySet()){
            System.out.println(key + " games played " + dataSet.get(key).size() );
            for ( GameResult game : dataSet.get(key)){
                System.out.println(game.toString());
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        String[] fileNames = new String[]{"src/data/season0910.json","src/data/season1011.json","src/data/season1112.json",
                "src/data/season1213.json","src/data/season1314.json","src/data/season1415.json","src/data/season1516.json",
                "src/data/season1617.json","src/data/season1718.json","src/data/season1819.json"};

        for (String file : fileNames){
            getData(file);
        }

//        Everton vs Chelsea
        int win =0;
        int draw = 0;
        int total = 0;
        // home
        for ( GameResult game : dataSet.get("Everton")){
            if (game.teamName.equals("Liverpool")){
                System.out.println(game.toString());
                if (game.goalDifference> 0)
                    win++;
                else if( game.goalDifference == 0 )
                    draw++;
                total++;
            }
        }
        //away
        for ( GameResult game : dataSet.get("Liverpool")){
            if (game.teamName.equals("Everton")){
                System.out.println(game.toString());
                if (game.goalDifference<  0)
                    win++;
                else if( game.goalDifference == 0 )
                    draw++;
                total ++;
            }
        }

        System.out.println("Everton vs Liverpool \n Win % : "+ (double)win/total + " Draw % : "+ (double)draw/total
                + " Loss % : "+ (double)(total-win-draw)/total);
    }
}
