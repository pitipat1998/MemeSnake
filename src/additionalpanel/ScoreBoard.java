package additionalpanel;

import entity.Constants;
import maindisplay.MainFrame;
import maingame.GameDisplay;
import sharedactions.BackButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ScoreBoard extends JPanel {

    private static JLabel score;
    private static String textDisplay = "Your Score: ";
    private static int scoreDisplay = 0;
    private static JButton backBtn;

    public ScoreBoard(){
        setPreferredSize(new Dimension(Constants.SCOREBOARD_WIDTH, Constants.SCOREBOARD_HEIGHT));
        setLayout(new BorderLayout());
        setBackground(Color.white);

        score = new JLabel(textDisplay+ scoreDisplay, JLabel.CENTER);
        score.setFont(new Font("Serif", Font.PLAIN, 20));

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(Color.white);

        backBtn = new JButton("Back");
        backBtn.addActionListener(new BackButton(){
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.toMainMenu();
                GameDisplay.gameReset();
                if (GameOver.getLoopSound().getClip() != null){
                    GameOver.stopMusic();
                }
                MainMenu.startMusic("/resource/music/titanicflute.wav");
            }
        });
        buttonPanel.add(backBtn, BorderLayout.EAST);

        add(score, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.PAGE_END);
    }

    public static void addScore(){
        scoreDisplay++;
        score.setText(textDisplay+scoreDisplay);
    }

    public static void resetScore(){
        scoreDisplay = 0;
        score.setText(textDisplay+scoreDisplay);
    }


}
