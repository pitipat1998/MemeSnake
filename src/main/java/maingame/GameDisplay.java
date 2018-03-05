package maingame;

import additionalpanel.GameOver;
import additionalpanel.ScoreBoard;
import additionalpanel.StartGamePanel;
import displayformat.BigPanelFormat;
import displayformat.Constants;
import entity.Food;
import entity.Snake;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameDisplay extends BigPanelFormat {

    //Snake Component
    private static SnakeGame snakeGame;

    private static StartGamePanel startGamePanel;
    //GameOver
    private static GameOver gameOver;
    private static boolean isGameOver = false;

    //Used to switch between panels
    private static Container container;
    private static CardLayout cardLayout;

    //Scoreboard
    private static ScoreBoard scoreBoard;

    private static boolean running = false;

    public GameDisplay(){
        super();

        //Container part that switches between panelS
        container = new Container();
        cardLayout = new CardLayout();
        container.setLayout(cardLayout);

        //panels that will be switched
        snakeGame = new SnakeGame();
        startGamePanel = new StartGamePanel();
        gameOver = new GameOver();

        //fixed panel that doesn't change
        scoreBoard = new ScoreBoard();

        //add panel to the container
        container.add("StartGamePanel",startGamePanel);
        container.add("SnakeGame",snakeGame);
        container.add("GameOver", gameOver);

        //add component to this panel
        add(container, BorderLayout.CENTER);
        add(scoreBoard, BorderLayout.PAGE_END);

        addKeyListener(new StartGame());
    }

    public static void setIsGameOver(boolean cond){ isGameOver = cond; }

    public static void toGameOver(){
        cardLayout.show(container, "GameOver");
        GameOver.startMusic();
    }

    public static void toSnakeGame(){
        running = true;
        snakeGame.gameStart(true);
        cardLayout.show(GameDisplay.container, "SnakeGame");
        snakeGame.requestFocusInWindow();
    }

    private static void toStartGamePanel(){
        cardLayout.show(container, "StartGamePanel");
        ScoreBoard.resetScore();
    }

    public static void gameReset(){
        Snake.clearTail();
        Snake.setHead(Constants.DEFAULT_WIDTH/2- Constants.SCALE,
                Constants.DEFAULT_HEIGHT/2- Constants.SCALE,
                Constants.SCALE,
                Constants.SCALE);
        Food.randomFood();
        snakeGame.setFocusable(false);
        running = false;
        snakeGame.gameStart(false);
        if(isGameOver){
            toGameOver();
            isGameOver = false;
        }
        else{
            toStartGamePanel();
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
                    ScoreBoard.resetScore();
                    toSnakeGame();
                    if(GameOver.getLoopSound().getClip() != null){ GameOver.stopMusic(); }
                }
            }
        }
    }


}
