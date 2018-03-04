package maindisplay;

import additionalpanel.MainMenu;
import entity.Constants;
import maingame.GameDisplay;
import sharedactions.StartButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    //panels that are used interchangeably
    private static GameDisplay gameDisplay;
    private static MainMenu mainMenu;

    //To switch between panels
    private static Container container;
    private static CardLayout cardLayout;

    public MainFrame(){
        setTitle("Meme Snake");
        getContentPane().setBackground(Color.black);
        setResizable(false);
        setFocusable(true);

        container = getContentPane();
        cardLayout = new CardLayout();
        container.setLayout(cardLayout);

        gameDisplay = new GameDisplay();
        mainMenu = new MainMenu();

        container.add("MainMenu", mainMenu);
        container.add("GameDisplay", gameDisplay);
        //set frame to the size of component
        pack();
    }

    public static void toMainMenu(){
        cardLayout.show(container, "MainMenu");
    }
    public static void toDisplay(){
        cardLayout.show(container, "GameDisplay");
        gameDisplay.requestFocusInWindow();
    }

}
