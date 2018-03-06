package displayformat;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Constants {
    //Constants that are used by the whole game
    //Make the snake game become like a grid
    public static final int SCALE = 20;
    public static final int DEFAULT_WIDTH = 400;
    public static final int DEFAULT_HEIGHT = 400;
    public static final int SCOREBOARD_WIDTH = DEFAULT_WIDTH;
    public static final int SCOREBOARD_HEIGHT = 80;
    public static final int ROWS = DEFAULT_HEIGHT/SCALE;
    public static final int COLS = DEFAULT_WIDTH/SCALE;

    private static Color[] colours = {new Color(0, 255, 204),
                                            new Color(  0, 255, 153),
                                            new Color(  0, 255, 102),
                                            new Color(  0, 255,  51),
                                            new Color(  0, 255,   0),
                                            new Color( 51, 255,   0),
                                            new Color(102, 255,   0),
                                            new Color(153, 255,   0),
                                            new Color(204, 255,   0),
                                            new Color(255, 255,   0),
                                            new Color(255, 204,   0),
                                            new Color(255, 153,   0),
                                            new Color(255, 102,   0),
                                            new Color(255,  51,   0),
                                            new Color(255,   0,   0),
                                            new Color(255,   0,  51),
                                            new Color(255,   0, 102),
                                            new Color(255,   0, 153),
                                            new Color(255,   0, 204),
                                            new Color(255,   0, 255),
                                            new Color(204,   0, 255),
                                            new Color(153,   0, 255),
                                            new Color(102,   0, 255),
                                            new Color( 51,   0, 255),
                                            new Color(  0,   0, 255),
                                            new Color(  0,  51, 255),
                                            new Color(  0, 102, 255),
                                            new Color(  0, 153, 255),
                                            new Color(  0, 204, 255),
                                            new Color(  0, 255, 255)};

    public static List<Color> rainbow = new ArrayList<Color>(Arrays.asList(colours));
}
