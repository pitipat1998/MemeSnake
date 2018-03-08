package maingame;

import additionalpanel.ScoreBoard;
import displayformat.Constants;
import entity.Food;
import entity.Snake;
import sharedactions.MusicPlayer;
import utilities.LoopSound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

//1. import nyancat image
//2. import nyancat music
//3. timer in nyan cat mode
//4. make invincible in nyancat mode
public class SnakeGame extends JComponent implements MusicPlayer{

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

    //for nyan cat
    private static Image nyanCatImage;
    private URL nyanCatURL;
    private static int targetScore;
    private static int currentScore;
    private Timer nyanTimer;
    private ActionListener countTime;
    private static boolean inNyanCat = false;
    private static boolean runningNyanCat = false;
    private Random randomizer;
    private List<Color> rainbow = Constants.rainbow;
    //nyan cat Music
    private LoopSound loopSound;

    public SnakeGame(){

        //create a canvas to draw entities
        setPreferredSize(new Dimension(Constants.DEFAULT_WIDTH,
                                       Constants.DEFAULT_HEIGHT));
        setBackground(Color.BLACK);


        ActionListener animation = new ActionListener() {
            public synchronized void actionPerformed(ActionEvent evt) {
                repaint();
            }
        };
        animationThread = new Timer(200,animation);
        animationThread.setCoalesce(true);
        animationThread.setInitialDelay(200);

        //instantiate Nyan Cat image
        nyanCatURL = getClass().getClassLoader().getResource("image/Nyancat.png");
        nyanCatImage = new ImageIcon(nyanCatURL).getImage().getScaledInstance(100,50,Image.SCALE_DEFAULT);
        randomizer = new Random();
        targetScore = randomTargetScore();
        loopSound = new LoopSound();
        countTime = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Food.refillMemeFood();
                currentScore = ScoreBoard.getScoreDisplay();
                nyanTimer.stop();
                runningNyanCat = false;
                targetScore = randomTargetScore();
                stopMusic();
                Snake.getHead().setFrame(Snake.getHead().getX(),
                        Snake.getHead().getY(),
                        Constants.SCALE,
                        Constants.SCALE);
            }
        };
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


    //Drawing all the entities
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        //System.out.println(animationThread.getDelay());
        //Drawing Snake
        if (!runningNyanCat) {
            g2.setPaint(Color.white);
            g2.fill(Snake.getHead());
            for (Rectangle2D tail : Snake.getTails()) {
                g2.setPaint(Color.blue);
                g2.fill(tail);
            }

            //Drawing Food
            if (!inNyanCat){
                g2.setPaint(Color.red);
            }else{
                g2.setPaint(Color.MAGENTA);
            }
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
        }else{
            for(Rectangle2D food: Food.getMemeFoods()){
                g2.setPaint(rainbow.get(randomizer.nextInt(rainbow.size())));
                g2.fill(food);
            }

            g2.drawImage(nyanCatImage, (int) Snake.getHead().getX(), (int) Snake.getHead().getY(), this);

            checkBoundInNyan();

            eat();

            Snake.getHead().setFrame(Snake.getHead().getX()+xSpeed,
                    Snake.getHead().getY()+ySpeed,
                    nyanCatImage.getWidth(this),
                    nyanCatImage.getHeight(this));
        }

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
        if (!runningNyanCat){
            if(Food.isEaten(Food.getFoodEllips())) {
                if (inNyanCat) {
                    runningNyanCat = true;
                    inNyanCat = false;
                    Snake.getHead().setFrame(Snake.getHead().getX(),
                            Snake.getHead().getY(),
                            nyanCatImage.getWidth(this),
                            nyanCatImage.getHeight(this));
                    startMusic("music/nyancat.wav");
                    nyanTimer = new Timer(0, countTime);
                    nyanTimer.setInitialDelay(12000);
                    nyanTimer.setRepeats(false);
                    nyanTimer.restart();
                }
                //add to make the snake longer
                Snake.getTails().addFirst(Snake.tailFactory(Snake.getHead()));
                ScoreBoard.addScore();
                if (ScoreBoard.getScoreDisplay() == targetScore) {
                    inNyanCat = true;
                }
                Food.randomFood();
            }
            //Since the snake didn't eat the food this maintain the size of snake
            Snake.getTails().addFirst(Snake.tailFactory(Snake.getHead()));
            Rectangle2D last = Snake.getTails().pollLast();
        }
        else {
            nyanEat();
        }

    }

    private void eatItSelf(){
        if(Snake.isSelfEaten()){
            reset();
        }
    }

    //Method that is called to check the boundary
    private void checkBound(){
        double x = Snake.getHead().getX();
        double y = Snake.getHead().getY();
        if (x + xSpeed == Constants.DEFAULT_WIDTH || x + xSpeed < 0){
            reset();
            xSpeed = Constants.SCALE;
        }
        else if(y + ySpeed == Constants.DEFAULT_HEIGHT || y + ySpeed < 0){
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



    ///////////////////////////////////////////
    /////////////NYAN CAT PART/////////////////
    ///////////////////////////////////////////
    public void setRunningNyanCat(boolean runningNyanCat) { this.runningNyanCat = runningNyanCat;}

    private static int randomTargetScore(){
        return ThreadLocalRandom.current().nextInt(2,3) + currentScore;
    }

    private void checkBoundInNyan(){
        int w = nyanCatImage.getWidth(this);
        int h = nyanCatImage.getHeight(this);
        double x = Snake.getHead().getX();
        double y = Snake.getHead().getY();
        if ((x+w) + xSpeed > Constants.DEFAULT_WIDTH || x + xSpeed < 0){
            xSpeed = -(xSpeed);
            if(direction.equals("LEFT")) direction = "RIGHT";
            else if(direction.equals("RIGHT")) direction = "LEFT";
        }
        else if((y+h) + ySpeed > Constants.DEFAULT_HEIGHT || y + ySpeed < 0){
            ySpeed = -(ySpeed);
            if(direction.equals("UP")) direction = "DOWN";
            else if(direction.equals("DOWN")) direction = "UP";
        }
    }

    private void nyanEat(){
        int w = nyanCatImage.getWidth(this);
        int h = nyanCatImage.getHeight(this);
        ArrayList<Rectangle2D> tempLst = new ArrayList<>();
        for (Rectangle2D food: Food.getMemeFoods()){
            if (Food.isEaten(food)){
                Snake.getTails().addFirst(Snake.tailFactory(Snake.getHead()));
                ScoreBoard.addScore();
                tempLst.add(food);
            }
        }
        //to prevent list iterating and deleting its element at the same time
        Food.getMemeFoods().removeAll(tempLst);
        Snake.getTails().addFirst(Snake.tailFactory(Snake.getHead()));
        Rectangle2D last = Snake.getTails().pollLast();
    }

    public static void resetAllScore(){
        currentScore = 0;
        targetScore = randomTargetScore();
        runningNyanCat = false;
        inNyanCat = false;
    }

    public void startMusic(String path){
        try{
            loopSound.startMusic(path);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void stopMusic(){
        loopSound.stopMusic();
    }
}
