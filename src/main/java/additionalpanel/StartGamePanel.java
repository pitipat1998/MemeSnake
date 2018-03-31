package additionalpanel;

import displayformat.SmallPanelFormat;

import javax.swing.*;
import java.awt.*;

public class StartGamePanel extends SmallPanelFormat {

    private JLabel startGameTxt;

    public StartGamePanel(){
        super();
        startGameTxt = new JLabel("Press Spacebar to start");
        startGameTxt.setFont(new Font("Serif", Font.PLAIN, 20));
        startGameTxt.setForeground(Color.white);
        startGameTxt.setHorizontalAlignment(JLabel.CENTER);

        add(startGameTxt, BorderLayout.CENTER);

    }
}
