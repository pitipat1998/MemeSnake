package displayformat;

import javax.swing.*;
import java.awt.*;

public abstract class BigPanelFormat extends JPanel{

    protected BigPanelFormat(){
        setPreferredSize(new Dimension(Constants.DEFAULT_WIDTH,
                                Constants.DEFAULT_HEIGHT + Constants.SCOREBOARD_HEIGHT));
        setLayout(new BorderLayout());
        setOpaque(true);
        setBackground(Color.BLACK);
    }

}
