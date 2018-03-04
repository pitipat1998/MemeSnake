package maingame;

import entity.Constants;
import additionalpanel.ScoreBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameDisplay extends JPanel {

    //Snake Component
    private static SnakeGame snakeGame;

    //Used to switch between panels
    private static Container container;
    private static CardLayout cardLayout;

    //Scoreboard
    private static ScoreBoard scoreBoard;

    private JLabel startGameTxt;
    private static boolean running = false;

    //variables for Actions
    private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;
    private static final String MOVE_UP = "move up";
    private static final String MOVE_DOWN = "move down";
    private static final String MOVE_LEFT = "move left";
    private static final String MOVE_RIGHT = "move right";

    public GameDisplay(){
        setPreferredSize(new Dimension(Constants.DEFAULT_WIDTH,
                                Constants.DEFAULT_HEIGHT+Constants.SCOREBOARD_HEIGHT));
        setLayout(new BorderLayout(0,0));
        setBackground(Color.BLACK);

        container = new Container();
        cardLayout = new CardLayout();
        container.setLayout(cardLayout);

        snakeGame = new SnakeGame();
        scoreBoard = new ScoreBoard();
        startGameTxt = new JLabel("Press Spacebar to start");

        snakeGame.setFocusable(true);

        startGameTxt.setHorizontalAlignment(JLabel.CENTER);
        startGameTxt.setFont(new Font("Serif", Font.PLAIN, 20));
        startGameTxt.setForeground(Color.white);

        //assign input map for each arrow key
        snakeGame.getInputMap(IFW).put(KeyStroke.getKeyStroke("UP"), MOVE_UP);
        snakeGame.getInputMap(IFW).put(KeyStroke.getKeyStroke("DOWN"), MOVE_DOWN);
        snakeGame.getInputMap(IFW).put(KeyStroke.getKeyStroke("LEFT"), MOVE_LEFT);
        snakeGame.getInputMap(IFW).put(KeyStroke.getKeyStroke("RIGHT"), MOVE_RIGHT);

        //bind action with keys
        snakeGame.getActionMap().put(MOVE_UP, new MoveAction(0, -Constants.SCALE, "UP"));
        snakeGame.getActionMap().put(MOVE_DOWN, new MoveAction(0, Constants.SCALE, "DOWN"));
        snakeGame.getActionMap().put(MOVE_LEFT, new MoveAction(-Constants.SCALE, 0, "LEFT"));
        snakeGame.getActionMap().put(MOVE_RIGHT, new MoveAction(Constants.SCALE, 0, "RIGHT"));

        //add component to layer
        //add(snakeGame, BorderLayout.CENTER);
        add(scoreBoard, BorderLayout.PAGE_END);
        container.add("StartGameTxt",startGameTxt);
        container.add("SnakeGame",snakeGame);
        add(container);

        addKeyListener(new StartGame());
    }

    public static void gameReset(){
        snakeGame.setFocusable(false);
        //setFocusable(true);
        running = false;
        snakeGame.gameStart(false);
        cardLayout.show(container, "StartGameTxt");
    }
    //Action class use for the movement of snakeGame
    private class MoveAction extends AbstractAction {

        private int xSpeed;
        private int ySpeed;
        private String d;

        /*
        * Constructor that assign value depends on direction
        * if snakeGame goes right xSpeed = Constant.SCALE    ySpeed = 0
        * if snakeGame goes left  xSpeed = -Constant.SCALE   ySpeed = 0
        * if snakeGame goes up    ySpeed = -Constant.SCALE   xSpeed = 0
        * if snakeGame goes down  ySpeed = Constant.SCALE    xSpeed = 0
        * */
        public MoveAction(int xV, int yV, String d) {
            this.xSpeed = xV;
            this.ySpeed = yV;
            this.d = d;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            //get snakeGame's current direction, in order to compare with the input direction
            String dNow = SnakeGame.getDirection();

            //Conditions that prevent the snakeGame from going backward and collide with its body
            //Also, prevents player from boosting the snakeGame's speed by continually press the key
            if (d.equals("LEFT") && (!dNow.equals("LEFT") && !dNow.equals("RIGHT"))) {
                SnakeGame.setxSpeed(this.xSpeed);
                SnakeGame.setySpeed(this.ySpeed);
                SnakeGame.setDirection(this.d);
                snakeGame.repaint();
            }
            else if (d.equals("RIGHT") && (!dNow.equals("LEFT") && !dNow.equals("RIGHT"))) {
                SnakeGame.setxSpeed(this.xSpeed);
                SnakeGame.setySpeed(this.ySpeed);
                SnakeGame.setDirection(this.d);
                snakeGame.repaint();
            }
            else if (d.equals("UP") && (!dNow.equals("UP") && !dNow.equals("DOWN"))) {
                SnakeGame.setxSpeed(this.xSpeed);
                SnakeGame.setySpeed(this.ySpeed);
                SnakeGame.setDirection(this.d);
                snakeGame.repaint();
            }
            else if (d.equals("DOWN") && (!dNow.equals("UP") && !dNow.equals("DOWN"))) {
                SnakeGame.setxSpeed(this.xSpeed);
                SnakeGame.setySpeed(this.ySpeed);
                SnakeGame.setDirection(this.d);
                snakeGame.repaint();
            }
            //After the snakeGame turned, update the direction of the snakeGame
            dNow = SnakeGame.getDirection();

        }
    }

    private class StartGame implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) { }

        @Override
        public void keyPressed(KeyEvent e) { }

        @Override
        public void keyReleased(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_SPACE){
                if (!running){
                    running = true;
                    snakeGame.gameStart(true);
                    cardLayout.show(GameDisplay.container, "SnakeGame");
                }
            }
        }
    }


}
