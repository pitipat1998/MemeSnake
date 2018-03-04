package additionalpanel;

import entity.Constants;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class GameOver extends JPanel {

    private Font header;
    private Font header2;
    private Icon image;

    private URL displayImage;
    private static LoopSound loopSound;

    public GameOver(){
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(Constants.DEFAULT_WIDTH,
                                        Constants.DEFAULT_HEIGHT));
        setBackground(Color.BLACK);


        loopSound = new LoopSound();

        displayImage = this.getClass().getResource("/resource/image/wtfboom.gif");
        image = new ImageIcon(displayImage);
        header = new Font("Serif", Font.BOLD, 30);
        header2 = new Font("Serif", Font.BOLD, 20);

        JLabel label1 = new JLabel(image);

        JLabel label2 = new JLabel("Game Over");
        label2.setHorizontalAlignment(JLabel.CENTER);
        label2.setFont(header);
        label2.setForeground(Color.RED);

        JLabel label3 = new JLabel("Press Space Bar to Play Again");
        label3.setHorizontalAlignment(JLabel.CENTER);
        label3.setFont(header2);
        label3.setForeground(Color.WHITE);

        add(label1, BorderLayout.CENTER);
        add(label2,BorderLayout.PAGE_START);
        add(label3, BorderLayout.PAGE_END);

    }

    public static LoopSound getLoopSound() { return loopSound; }

    public static void startMusic(){
        try{
            loopSound.startMusic("/resource/music/wtfboom.wav");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void stopMusic(){
        loopSound.stopMusic();
    }

}
