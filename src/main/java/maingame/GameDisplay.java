package maingame;

import additionalpanel.EnterScorePanel;
import additionalpanel.GameOver;
import additionalpanel.ScoreBoard;
import additionalpanel.StartGamePanel;
import displayformat.BigPanelFormat;
import displayformat.Constants;
import entity.Food;
import entity.Snake;
import utilities.Ranking;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameDisplay extends BigPanelFormat {

    //Snake Component
    private static SnakeGame snakeGame;

    private static StartGamePanel startGamePanel;
    private static EnterScorePanel enterScorePanel;

    //GameOver
    private static GameOver gameOver;
    private static boolean isGameOver = false;
    private static boolean inEnterScore = false;

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
        enterScorePanel = new EnterScorePanel();

        snakeGame.setFocusable(true);

        //fixed panel that doesn't change
        scoreBoard = new ScoreBoard();

        //add panel to the container
        container.add("StartGamePanel",startGamePanel);
        container.add("SnakeGame",snakeGame);
        container.add("GameOver", gameOver);
        container.add("EnterScorePanel", enterScorePanel);

        //add component to this panel
        add(scoreBoard, BorderLayout.PAGE_END);
        add(container, BorderLayout.CENTER);

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
    }

    private static void toStartGamePanel(){
        cardLayout.show(container, "StartGamePanel");
        ScoreBoard.resetScore();
    }

    private static void toEnterScorePanel(){
        cardLayout.show(container, "EnterScorePanel");
        enterScorePanel.requestFocusInWindow();
    }

    public static void gameReset(){
        Snake.clearTail();
        Snake.setHead(Constants.DEFAULT_WIDTH/2- Constants.SCALE,
                Constants.DEFAULT_HEIGHT/2- Constants.SCALE,
                Constants.SCALE,
                Constants.SCALE);
        Food.randomFood();
        Food.refillMemeFood();
        Food.setCurrentScore(0);
        SnakeGame.resetAllScore();
        snakeGame.setRunningNyanCat(false);
        snakeGame.setFocusable(false);
        running = false;
        snakeGame.gameStart(false);
        if(isGameOver){
            toGameOver();
            isGameOver = false;
            inEnterScore = true;
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
//                    gameReset();
                    if(inEnterScore){
                        if(GameOver.getLoopSound().getClip() != null){ GameOver.stopMusic(); }
                        toEnterScorePanel();
                    }
                    else {
                        Ranking ranking = new Ranking();
                        ScoreBoard.resetScore();
                        toSnakeGame();
                    }


                }
            }
        }
    }


}
