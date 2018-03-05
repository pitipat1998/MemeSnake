package maingame;

import additionalpanel.ScoreBoard;
import displayformat.Constants;
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

    //Instantiate the io.muic.compro2.memesnake.entity in the component
    private Snake snake = new Snake();
    private Food food = new Food();

    //Control snake animation
    private Timer animationThread;

    //variable that is used to determine the movement and direction of the snake
    private int xSpeed = Constants.SCALE;
    private int ySpeed = 0;
    private static String direction = "RIGHT";

    //variables for Actions
    private final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;
    private final String MOVE_UP = "move up";
    private final String MOVE_DOWN = "move down";
    private final String MOVE_LEFT = "move left";
    private final String MOVE_RIGHT = "move right";

    public SnakeGame(){

        //create a canvas to draw entities
        setPreferredSize(new Dimension(Constants.DEFAULT_WIDTH,
                                       Constants.DEFAULT_HEIGHT));
        setBackground(Color.BLACK);

        ActionListener animation = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                repaint();
            }
        };
        animationThread = new Timer(200,animation);
        animationThread.setCoalesce(true);
        animationThread.setInitialDelay(200);


        //assign input map for each arrow key
        getInputMap(IFW).put(KeyStroke.getKeyStroke("UP"), MOVE_UP);
        getInputMap(IFW).put(KeyStroke.getKeyStroke("DOWN"), MOVE_DOWN);
        getInputMap(IFW).put(KeyStroke.getKeyStroke("LEFT"), MOVE_LEFT);
        getInputMap(IFW).put(KeyStroke.getKeyStroke("RIGHT"), MOVE_RIGHT);

        //bind action with keys
        getActionMap().put(MOVE_UP, new MoveAction(0, -Constants.SCALE, "UP"));
        getActionMap().put(MOVE_DOWN, new MoveAction(0, Constants.SCALE, "DOWN"));
        getActionMap().put(MOVE_LEFT, new MoveAction(-Constants.SCALE, 0, "LEFT"));
        getActionMap().put(MOVE_RIGHT, new MoveAction(Constants.SCALE, 0, "RIGHT"));
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

    public void gameStart(boolean cond){
        if (cond){
            animationThread.start();
        }
        else {
            animationThread.stop();
        }
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
        direction = "RIGHT";
        GameDisplay.setIsGameOver(true);
        GameDisplay.gameReset();
    }

    //Action class use for the movement of snakeGame
    private class MoveAction extends AbstractAction {

        private int xV;
        private int yV;
        private String d;

        /*
         * Constructor that assign value depends on direction
         * if snakeGame goes right xSpeed = Constant.SCALE    ySpeed = 0
         * if snakeGame goes left  xSpeed = -Constant.SCALE   ySpeed = 0
         * if snakeGame goes up    ySpeed = -Constant.SCALE   xSpeed = 0
         * if snakeGame goes down  ySpeed = Constant.SCALE    xSpeed = 0
         * */
        public MoveAction(int xV, int yV, String d) {
            this.xV = xV;
            this.yV = yV;
            this.d = d;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //get snakeGame's current direction, in order to compare with the input direction
            String dNow = direction;

            //Conditions that prevent the snakeGame from going backward and collide with its body
            //Also, prevents player from boosting the snakeGame's speed by continually press the key
            if (d.equals("LEFT") && (!dNow.equals("LEFT") && !dNow.equals("RIGHT"))) {
                xSpeed = this.xV;
                ySpeed = this.yV;
                direction = this.d;
                repaint();
            }
            else if (d.equals("RIGHT") && (!dNow.equals("LEFT") && !dNow.equals("RIGHT"))) {
                xSpeed = this.xV;
                ySpeed = this.yV;
                direction = this.d;
                repaint();
            }
            else if (d.equals("UP") && (!dNow.equals("UP") && !dNow.equals("DOWN"))) {
                xSpeed = this.xV;
                ySpeed = this.yV;
                direction = this.d;
                repaint();
            }
            else if (d.equals("DOWN") && (!dNow.equals("UP") && !dNow.equals("DOWN"))) {
                xSpeed = this.xV;
                ySpeed = this.yV;
                direction = this.d;
                repaint();
            }
            //After the snakeGame turned, update the direction of the snakeGame
            dNow = direction;

        }
    }

}
