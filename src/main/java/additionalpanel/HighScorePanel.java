package additionalpanel;

import displayformat.BigPanelFormat;
import displayformat.Constants;
import highscore.Score;
import maindisplay.MainFrame;
import sharedactions.BackButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class HighScorePanel extends BigPanelFormat{

    private static List<Score> highScores;
    private static Score number1 = null;
    private static Score number2 = null;
    private static Score number3 = null;
    private JButton backBtn;
    private Rectangle2D baseRect;
    private Rectangle2D topRect;

    public HighScorePanel(){
        super();

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(Color.black);

        backBtn = new JButton("Back");
        backBtn.addActionListener(new BackButton(){
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.setCallByHighScore(true);
                MainFrame.toMainMenu();
            }
        });
        buttonPanel.add(backBtn, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.PAGE_END);

        baseRect = new Rectangle2D.Double(50,
                                            Constants.DEFAULT_HEIGHT/2+120,
                                            Constants.DEFAULT_WIDTH -100,
                                            3*Constants.SCALE);
        topRect = new Rectangle2D.Double(150,
                                        Constants.DEFAULT_HEIGHT/2 - 3*Constants.SCALE+120,
                                        100,
                                        3*Constants.SCALE);
    }

    public static void getHighScore(){
        highScores = MainFrame.getHighScores();
        try{
            number1 = highScores.get(0);
            number2 = highScores.get(1);
            number3 = highScores.get(2);
        }catch (IndexOutOfBoundsException e){
            if(number1 != null){
                if(number2 != null){
                    if(number3 != null){
                    }
                    else{
                        number3 = new Score("No one", 0);
                    }
                }
                else {
                    number2 = new Score("No one", 0);
                    number3 = new Score("No one", 0);
                }
            }
            else {
                number1 = new Score("No one", 0);
                number2 = new Score("No one", 0);
                number3 = new Score("No one", 0);
            }


        }
//
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        Font font = new Font("Serif", Font.BOLD, 30);
        g2.setColor(Color.white);
        g2.setFont(font);
        g2.drawString("High Score", Constants.DEFAULT_WIDTH/4 + 10, Constants.DEFAULT_HEIGHT/4);

        g2.setPaint(Color.WHITE);
        g2.fill(baseRect);
        g2.fill(topRect);

        g2.setColor(Color.black);
        g2.setFont(new Font("Serif", Font.BOLD, 40));
        g2.drawString("1", 185, Constants.DEFAULT_HEIGHT/2 - 3*Constants.SCALE+165);
        g2.drawString("2", 85, Constants.DEFAULT_HEIGHT/2+165);
        g2.drawString("3", 285, Constants.DEFAULT_HEIGHT/2+165);

        g2.setColor(Color.yellow);
        g2.setFont(new Font("Serif", Font.BOLD, 20));
        g2.drawString(number1.getName() + " : " + number1.getScore(),
                        150,
                        Constants.DEFAULT_HEIGHT/2 - 3*Constants.SCALE+100);

        g2.setColor(Color.white);
        g2.drawString(number2.getName() + " : " + number2.getScore(),
                25,
                Constants.DEFAULT_HEIGHT/2+100);

        g2.drawString(number3.getName() + " : " + number3.getScore(),
                260,
                Constants.DEFAULT_HEIGHT/2+100);


    }
}
