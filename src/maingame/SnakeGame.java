package maingame;

import additionalpanel.ScoreBoard;
import entity.Constants;
import entity.Food;
import entity.Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;


public class SnakeGame extends JComponent {

    //Instantiate the entity in the component
    private Snake snake = new Snake();
    private Food food = new Food();

    //Control snake animation
    private Timer timer;

    //variable that is used to determine the movement and direction of the snake
    private static int xSpeed = Constants.SCALE;
    private static int ySpeed = 0;
    private static String direction = "RIGHT";

    public SnakeGame(){

        //create a canvas to draw entities
        setPreferredSize(new Dimension(Constants.DEFAULT_WIDTH,
                                       Constants.DEFAULT_HEIGHT));
        setBackground(Color.BLACK);

        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                repaint();
            }
        };
        timer = new Timer(200,taskPerformer);
        timer.setCoalesce(true);
        timer.setInitialDelay(200);

        //Use as a test to check whether the snake works or not
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Snake.addTail();
            }
        });
    }


    //Drawing all the entites
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        //Drawing Snake
        g2.setPaint(Color.white);
        g2.fill(Snake.getHead());
        for(Rectangle2D tail: Snake.getTails()){
            g2.setPaint(Color.blue);
            g2.fill(tail);
        }

        //Drawing Food
        g2.setPaint(Color.red);
        g2.fill(Food.getFoodEllips());


        //Constrain the boundary that snake can move
        //if the snake goes out of boundary, the player lose
        checkBound();

        //Check whether the snake eat the food or not
        eat();

        //Makes the snake automatically move
        Snake.getHead().setFrame(Snake.getHead().getX() + xSpeed,
                                Snake.getHead().getY() + ySpeed,
                                Constants.SCALE,
                                Constants.SCALE);

        //Check whether it collide with itself or not
        eatItSelf();
    }

    //Getters & Setters
    public static int getxSpeed() { return xSpeed; }
    public static void setxSpeed(int v){ xSpeed = v; }

    public static int getySpeed() { return ySpeed; }
    public static void setySpeed(int v){ ySpeed = v;}

    public static String getDirection(){ return direction; }
    public static void setDirection(String d){ direction = d; }

    public void gameStart(boolean cond){
        if (cond){ timer.start(); }
        else timer.stop();
    }

    //Logic that makes the tail followed the head
    //By keep tracking the previous position of the head and then push in to a linkedlist
    //then the frame will be repainted and draw these previous positions of the head
    private void eat(){
        if(Food.isEaten()){
            //add to make the snake longer
            Snake.getTails().addFirst(Snake.tailFactory(Snake.getHead()));
            Food.randomFood();
            ScoreBoard.addScore();
        }

        //Since the snake didn't eat the food this maintain the size of snake
        Snake.getTails().addFirst(Snake.tailFactory(Snake.getHead()));
        Rectangle2D last = Snake.getTails().pollLast();

    }

    private void eatItSelf(){
        if(Snake.isSelfEaten()){
            reset();
        }
    }

    //Method that is called to check the boundary
    private void checkBound(){
        if (Snake.getHead().getX() + xSpeed == Constants.DEFAULT_WIDTH || Snake.getHead().getX() + xSpeed < 0){
            reset();
            xSpeed = Constants.SCALE;
        }
        else if(Snake.getHead().getY() + ySpeed == Constants.DEFAULT_HEIGHT || Snake.getHead().getY() + ySpeed < 0){
            reset();
            xSpeed = Constants.SCALE;
            ySpeed = 0;
        }
    }

    //Used to reset the game when the snake hits the end game conditions
    public static void reset(){
        GameDisplay.gameReset();
        Snake.clearTail();
        Snake.setHead(Constants.DEFAULT_WIDTH/2- Constants.SCALE,
                Constants.DEFAULT_HEIGHT/2- Constants.SCALE,
                Constants.SCALE,
                Constants.SCALE);
        Food.randomFood();
        ScoreBoard.resetScore();
    }

}
