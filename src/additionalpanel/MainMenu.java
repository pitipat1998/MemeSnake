package additionalpanel;

import entity.Constants;
import maindisplay.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class MainMenu extends JPanel {

    private FontMetrics fm;

    private String gameName = "Meme Snake";

    private final String startTxt = "Start";
    private final String highScoreTxt = "High Score";
    private final String exitTxt = "Exit";

    private Rectangle2D startBtn;
    private Rectangle2D highScoreBtn;
    private Rectangle2D exitBtn;

    private int startingX = Constants.DEFAULT_WIDTH/4;
    private int startingY = Constants.DEFAULT_HEIGHT/4 + 20;

    //For music
    private static LoopSound loopSound;

    public MainMenu() {
        setPreferredSize(new Dimension(Constants.DEFAULT_WIDTH,
                Constants.DEFAULT_HEIGHT + Constants.SCOREBOARD_HEIGHT));
        setOpaque(true);
        setBackground(Color.BLACK);


        loopSound = new LoopSound();

        startMusic();
        addMouseListener(new MouseHandler());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        Font font = new Font("Serif", Font.BOLD, 30);
        g2.setColor(Color.white);
        g2.setFont(font);
        g2.drawString(gameName, startingX, startingY);

        //g2.setColor(Color.white);
        g2.setFont(font);
        fm = g2.getFontMetrics();
        startBtn = new Rectangle2D.Double(startingX + 65,
                                            startingY + 40,
                                            fm.getStringBounds(startTxt, g2).getWidth(),
                                            fm.getStringBounds(startTxt, g2).getHeight());
        g2.draw(startBtn);
        g2.drawString(startTxt, startingX + 65, startingY + 70);

        highScoreBtn = new Rectangle2D.Double(startingX + 10,
                startingY + 120,
                fm.getStringBounds(highScoreTxt, g2).getWidth(),
                fm.getStringBounds(highScoreTxt, g2).getHeight());
        g2.draw(highScoreBtn);
        g2.drawString(highScoreTxt, startingX + 10, startingY + 150);

        exitBtn = new Rectangle2D.Double(startingX + 65,
                startingY + 200,
                fm.getStringBounds(exitTxt, g2).getWidth(),
                fm.getStringBounds(exitTxt, g2).getHeight());
        g2.draw(exitBtn);
        g2.drawString(exitTxt, startingX + 65, startingY + 230);

    }

    public static void startMusic(){
        try{
            loopSound.startMusic("/resource/music/titanicflute.wav");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void stopMusic(){
        loopSound.stopMusic();
    }

    private class MouseHandler extends MouseAdapter{
        @Override
        public void mouseReleased(MouseEvent e) {
            if (startBtn.contains(e.getPoint())){
                MainFrame.toDisplay();
                stopMusic();
            }
            else if (highScoreBtn.contains(e.getPoint())){
            }
            else if (exitBtn.contains(e.getPoint())){
                System.exit(0);
            }
        }
    }

}

