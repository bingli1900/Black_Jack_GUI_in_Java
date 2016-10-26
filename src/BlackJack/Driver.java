package BlackJack;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Bing on 11/15/2014 0015.
 */
public class Driver {

    public static void main(String[] args) {

        BlackJackFrame frame = new BlackJackFrame();

        frame.setSize(700, 500);
        frame.setTitle("BlackJack");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}
