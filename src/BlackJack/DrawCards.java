package BlackJack;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Bing on 11/15/2014 0015.
 */

public class DrawCards extends JComponent{

    private ArrayList<Card> cards;
    private int x;
    private int y;
    private String string;
    private static final int SIZE_X = 90;
    private static final int SIZE_Y = 135;

    public DrawCards(ArrayList<Card> cards, String string, int x, int y) {
        this.cards = cards;
        this.x = x;
        this.y = y;
        this.string = string;
    }

    public void paintComponent(Graphics g){
        //g.dispose();
        int i = 0;
        g.drawString(string, 5,65);
        for (Card card : cards) {
            i++;
            String filename = "200px-Playing_card_"+card.getType().toString()+"_"+Integer.toString(card.getNumber())+".svg.png";
            g.drawImage(new ImageIcon("C:/Users/Bing/java_project/proBlackJack/src/BlackJack/pokercards/"+filename).getImage(), x+i*15, y, SIZE_X, SIZE_Y, null);
        }
    }

}
