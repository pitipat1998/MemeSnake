package entity;

import displayformat.Constants;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Food {

    //variables for food
    private static final int RADIUS = Constants.SCALE;
    private static Rectangle2D foodEllipse;

    private static boolean runningNyanCat = false;
    private static List<Rectangle2D> memeFoods;
    private static int currentScore = 0;
    private static final int maxFood = 40;

    public Food(){

        //initailize the position of the food
        foodEllipse = randomPos();
        memeFoods = new ArrayList<Rectangle2D>();
        refillMemeFood();

    }

    //Getter
    public static Rectangle2D getFoodEllips() { return foodEllipse; }

    public static List<Rectangle2D> getMemeFoods(){ return memeFoods; }

    public static void setFoodEllipse(Rectangle2D food) {foodEllipse = food;}

    public static void setCurrentScore(int score){ currentScore = currentScore; }

    public static void refillMemeFood(){
        for (int i=0; i<maxFood; i++){
            memeFoods.add(randomMemePos());
        }
    }

    private boolean checkCollision(int x, int y){
        return ((Math.abs(x-foodEllipse.getX()) < Constants.DEFAULT_WIDTH ) &&
                (Math.abs(y-foodEllipse.getY()) < Constants.DEFAULT_HEIGHT));
    }
    //Random the position of the food with out intersecting with the snake
    public static void randomFood(){
        foodEllipse = randomPos();
    }

    //Method that is used to random the point and then used in randomPos method
    private static Rectangle2D randomPos() {
        double x = Constants.SCALE * ThreadLocalRandom.current().nextInt(0, Constants.COLS);
        double y = Constants.SCALE * ThreadLocalRandom.current().nextInt(0, Constants.ROWS);
        double[] point = {x,y};

        if(Snake.getHead().contains(point[0], point[1])){
            return randomPos();
        }
        else{
            for(Rectangle2D tail:Snake.getTails()){
                if (tail.contains(point[0], point[1])){
                    return randomPos();
                }
            }
        }

        return new Rectangle2D.Double(point[0], point[1], Constants.SCALE, Constants.SCALE);
    }

    private static Rectangle2D randomMemePos(){
        double x = Constants.SCALE * ThreadLocalRandom.current().nextInt(0, Constants.COLS);
        double y = Constants.SCALE * ThreadLocalRandom.current().nextInt(0, Constants.ROWS);
        double[] point = {x,y};

        if(Snake.getHead().contains(point[0], point[1])){
            return randomMemePos();
        }
        else{
            for(Rectangle2D tail:Snake.getTails()){
                if (tail.contains(point[0], point[1])){
                    return randomMemePos();
                }
            }
            for (Rectangle2D food: memeFoods){
                if (food.contains(point[0], point[1])){
                    return randomMemePos();
                }
            }
        }

        return new Rectangle2D.Double(point[0], point[1], Constants.SCALE, Constants.SCALE);
    }



    //Check whether the food got eaten by snake or not
    public static boolean isEaten(Rectangle2D food){
        double x = Snake.getHead().getX();
        double y = Snake.getHead().getY();
        if(Snake.getHead().contains(food.getX(), food.getY())) return true;
        else return false;
//        if (food.contains(x,y)){ return true; }
//        else return false;
    }


}
