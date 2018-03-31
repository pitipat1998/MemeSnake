package additionalpanel;

import displayformat.SmallPanelFormat;
import highscore.Score;
import maingame.GameDisplay;
import utilities.Ranking;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EnterScorePanel extends SmallPanelFormat{

    private static JTextField inpName;
    private static JButton submitBtn;


    public EnterScorePanel(){
        super();
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.black);
        //centerPanel.setMaximumSize(new Dimension(100, 100));

        JLabel labelTxt = new JLabel("Enter your name:");
        labelTxt.setForeground(Color.white);
        labelTxt.setHorizontalAlignment(JLabel.CENTER);
        labelTxt.setFont(new Font("Serif", Font.BOLD, 40));

        inpName = new JTextField(10);
        inpName.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (inpName.getText().length() >= 6 ) // limit textfield to 3 characters
                    e.consume();
            }
        });
        inpName.setHorizontalAlignment(JTextField.CENTER);
        inpName.setFont(new Font("Serif", Font.BOLD, 25));

        submitBtn = new JButton("Submit");
        submitBtn.addActionListener(new SubmitAction());

        centerPanel.add(labelTxt, BorderLayout.PAGE_START);
        centerPanel.add(inpName, BorderLayout.CENTER);
        centerPanel.add(submitBtn, BorderLayout.PAGE_END);

        add(centerPanel, c);
    }

    public static JTextField getInpName() {
        return inpName;
    }

    public static void setInpName(String s){ inpName.setText(s); }

    private class SubmitAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Score score = new Score(inpName.getText(), ScoreBoard.getScoreDisplay());
            Ranking.addWinner(score);
            Ranking.updateHighScore();
            GameDisplay.gameReset();
            inpName.setText(null);
        }
    }
}
