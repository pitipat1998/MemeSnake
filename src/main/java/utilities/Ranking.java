package utilities;

import highscore.HighScoreRepo;
import highscore.Score;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Ranking {

    private static List<Score> scores;

    public Ranking(){
        scores = HighScoreRepo.getHighScoreValue();
        sortList();
    }

    public static void addWinner(Score score){
        scores.add(score);
        sortList();
    }

    public static void updateHighScore(){
        HighScoreRepo.createNewTable();
        for(Score score : scores){
            HighScoreRepo.insertHighScoreValue(score.getName(), score.getScore());
        }
    }

    private static void sortList(){
        Collections.sort(scores, new Comparator<Score>() {
            @Override
            public int compare(Score o1, Score o2) {
                if (o1.getScore() > o2.getScore()) return 1;
                else if (o1.getScore() < o2.getScore()) return -1;
                else {
                    return o2.getName().compareTo(o1.getName());
                }
            }
        });
        Collections.reverse(scores);

        checkWinnersNumber();
    }

    private static void checkWinnersNumber(){
        if(scores.size() > 3){
//            HighScoreRepo.deleteHighScoreValue(scores.get(scores.size()).getName(), scores.get(scores.size()).getScore());
            scores.remove(scores.size()-1);
        }
    }





}
