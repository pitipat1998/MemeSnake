package entity;

import displayformat.Constants;

import java.awt.geom.Rectangle2D;
import java.util.Deque;
import java.util.LinkedList;

public class Snake {

    //varibles of the snake's head and body
    private static Rectangle2D head;
    private static Deque<Rectangle2D> tails = new LinkedList<>();

    public Snake(){

        //Set the snake initailly in the middle of the screen
        head = new Rectangle2D.Double(Constants.DEFAULT_WIDTH/2- Constants.SCALE,
                                      Constants.DEFAULT_HEIGHT/2- Constants.SCALE,
                                         Constants.SCALE,
                                         Constants.SCALE);
    }

    //Getters
    public static Rectangle2D getHead(){ return head; }
    public static LinkedList<Rectangle2D> getTails() { return (LinkedList<Rectangle2D>) tails; }

    //Setters
    public static void setHead(double x, double y, double w, double h){
        head = new Rectangle2D.Double(Constants.DEFAULT_WIDTH/2- Constants.SCALE,
                Constants.DEFAULT_HEIGHT/2- Constants.SCALE,
                Constants.SCALE,
                Constants.SCALE);
    }
    public static void clearTail(){ tails.clear();}

    //Method the track the history of the snake's head
    public static Rectangle2D tailFactory(Rectangle2D rect){
        double x = rect.getX();
        double y = rect.getY();
        double w = Constants.SCALE;
        double h = Constants.SCALE;
        return new Rectangle2D.Double(x,y,w,h);
    }

    //Check whether the snake's head collide with its body or not
    //if so, game's over. The eatItSelf method will run
    public static boolean isSelfEaten(){
        for (Rectangle2D tail:tails){
            if (head.contains(tail.getX(), tail.getY())) return true;
        }
        return false;
    }

    //Used for testing
    public static void addTail(){
        tails.addFirst(tailFactory(head));
    }
}
