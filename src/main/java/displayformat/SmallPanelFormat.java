package displayformat;

import javax.swing.*;
import java.awt.*;

public abstract class SmallPanelFormat extends JPanel{

    protected SmallPanelFormat(){
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(Constants.DEFAULT_WIDTH,
                                        Constants.DEFAULT_HEIGHT));
        setBackground(Color.BLACK);
    }

}
