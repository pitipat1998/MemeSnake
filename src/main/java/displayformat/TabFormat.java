package displayformat;

import javax.swing.*;
import java.awt.*;

public abstract class TabFormat extends JPanel{

    protected TabFormat(){
        setPreferredSize(new Dimension(Constants.SCOREBOARD_WIDTH, Constants.SCOREBOARD_HEIGHT));
        setLayout(new BorderLayout());
        setBackground(Color.white);
    }
}
