package maindisplay;

import additionalpanel.HighScorePanel;
import additionalpanel.MainMenu;
import highscore.HighScoreRepo;
import highscore.Score;
import maingame.GameDisplay;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {

    //panels that are used interchangeably
    private static GameDisplay gameDisplay;
    private static MainMenu mainMenu;
    private static HighScorePanel highScorePanel;

    private static List<Score> highScores;

    private static boolean callByHighScore = false;

    //To switch between panels
    private static Container container;
    private static CardLayout cardLayout;

    public MainFrame(){
        setTitle("Meme Snake");
        getContentPane().setBackground(Color.black);
        setResizable(false);
        //setFocusable(true);

        container = getContentPane();
        cardLayout = new CardLayout();
        container.setLayout(cardLayout);

        gameDisplay = new GameDisplay();
        mainMenu = new MainMenu();
        highScorePanel = new HighScorePanel();

        highScores = HighScoreRepo.getHighScoreValue();

        container.add("MainMenu", mainMenu);
        container.add("GameDisplay", gameDisplay);
        container.add("HighScorePanel", highScorePanel);
        //set frame to the size of component
        pack();
    }

    public static void setCallByHighScore(boolean cond){ callByHighScore = cond; }
    public static List<Score> getHighScores() { return highScores; }

    private static void updateHighScores(){
        highScores = HighScoreRepo.getHighScoreValue();
    }

    public static void toMainMenu(){
        cardLayout.show(container, "MainMenu");
        updateHighScores();
        if (!callByHighScore) mainMenu.startMusic("music/titanicflute2.wav");
        else callByHighScore = false;
        mainMenu.requestFocusInWindow();
    }
    public static void toDisplay(){
        cardLayout.show(container, "GameDisplay");
        gameDisplay.requestFocusInWindow();
    }

    public static void toHighScorePanel(){
        cardLayout.show(container, "HighScorePanel");
        HighScorePanel.getHighScore();
    }
}
