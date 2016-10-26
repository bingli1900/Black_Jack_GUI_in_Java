package BlackJack;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Bing on 11/15/2014 0015.
 */
public class BlackJackFrame extends JFrame{

    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JTextField textPlayerScore;
    private JTextField textCurrentMoney;
    private JTextField textRoundNum;
    private JTextField textBet;

    private int nBet;
    private int nMoney;
    private int nRound;
    private ArrayList<Card> playerCards;
    private ArrayList<Card> dealerCards;

    private int dealerScore;
    private int playerScore;
    private int runningFlag;
    private JLabel text4;
    private DrawCards drawDealer;
    private DrawCards drawPlayer;

    private JPanel imPanel = new JPanel();

    public BlackJackFrame(){
        playerCards = new ArrayList<Card>();
        dealerCards = new ArrayList<Card>();
        createComponents();
    }

    class ClickListener1 implements ActionListener{
        public void actionPerformed(ActionEvent event){
            if(runningFlag == 1) {
                Card c = generateCard();
                playerCards.add(c);
                drawPlayer.repaint();

                int up = scoreUp(playerCards);
                int down = scoreDown(playerCards);

                if (up == down) {
                    textPlayerScore.setText(Integer.toString(up));
                } else {
                    textPlayerScore.setText(Integer.toString(down) + '/' + Integer.toString(up));
                }

                if (down > 21) {
                    dealerRun();
                    gameCheckout();
                }
            }
        }
    }

    class ClickListener2 implements ActionListener{
        public void actionPerformed(ActionEvent event){
            if(runningFlag == 1) {
                dealerRun();
                gameCheckout();
            }
        }
    }

    class ClickListener3 implements ActionListener{
        public void actionPerformed(ActionEvent event){
            if(runningFlag == 1){
                Card c = generateCard();
                playerCards.add(c);
                drawPlayer.repaint();
                textBet.setText(Integer.toString(nBet*2));
                dealerRun();
                gameCheckout();
            }
        }
    }

    class ClickListener4 implements ActionListener{
        public void actionPerformed(ActionEvent event){
            playerCards.clear();
            dealerCards.clear();
            drawDealer.repaint();
            drawPlayer.repaint();

            text4.setText("Game running");
            textPlayerScore.setText("0");
            nBet = 5;
            textBet.setText(Integer.toString(5));
            nRound++;
            textRoundNum.setText(Integer.toString(nRound));
            runningFlag = 1;
        }
    }

    public void createComponents(){
        //set up the frame
        button1 = new JButton("Hit");
        //hit means asking for more cards
        button2 = new JButton("Stand");
        //stop asking for cards
        button3 = new JButton("Double Down");
        //double down means ask for one last card and finish this round
        //this will double the bet.
        button4 = new JButton("Next Round");
        //begin the next round

        //money left in player's account
        nMoney = 1000;
        //number of rounds
        nRound = 1;
        //flag of state, whether the current round has finished
        //if not, the player can still "hit", ask for more cards
        runningFlag = 1;

        textCurrentMoney = new JTextField(10);
        textPlayerScore = new JTextField(10);
        textRoundNum = new JTextField(10);
        textBet = new JTextField(10);

        JLabel text1 = new JLabel("\n\nYour Current Money:");
        JLabel text3 = new JLabel("\n\nYour Accumulated Points of This Round:");
        JLabel text2 = new JLabel("\n\nRound Number:");
        JLabel text5 = new JLabel("\n\nBet to Put");
        text4 = new JLabel("");

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        //layout of the button panel
        //which controls the progress of the program
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 4));
        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button3);
        buttonPanel.add(button4);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        //layout of the score panel, which shows the current state
        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new GridLayout(9,1));
        textCurrentMoney.setEditable(false);
        textPlayerScore.setEditable(false);
        textRoundNum.setEditable(false);

        scorePanel.add(text1);
        textCurrentMoney.setText(Integer.toString(nMoney));
        scorePanel.add(textCurrentMoney);
        scorePanel.add(text2);
        textRoundNum.setText(Integer.toString(nRound));
        scorePanel.add(textRoundNum);
        scorePanel.add(text3);
        scorePanel.add(textPlayerScore);
        scorePanel.add(text5);
        nBet = 5;
        textBet.setText(Integer.toString(nBet));
        scorePanel.add(textBet);
        scorePanel.add(text4);
        panel.add(scorePanel, BorderLayout.EAST);

        //layout of the image Panel
        //the upper is the dealer's panel, which shows the card list of the dealer
        //it only shows up when player stands or finish operating
        drawPlayer = new DrawCards(playerCards,"PLAYER",50,50);
        drawDealer = new DrawCards(dealerCards,"DEALER",50,50);
        imPanel = new JPanel();
        imPanel.setLayout(new GridLayout(2,1));
        imPanel.add(drawDealer);
        imPanel.add(drawPlayer);
        panel.add(imPanel,BorderLayout.CENTER);
        add(panel);

        ActionListener listener1 = new ClickListener1();
        button1.addActionListener(listener1);
        ActionListener listener2 = new ClickListener2();
        button2.addActionListener(listener2);
        ActionListener listener3 = new ClickListener3();
        button3.addActionListener(listener3);
        ActionListener listener4 = new ClickListener4();
        button4.addActionListener(listener4);

    }

    public void dealerRun(){
        //This is the dealer's turn
        //According to the dealer's strategy
        // it keeps asking for new card until it hits a "hard 17", (I use hard 17 instead of soft 17)

        Card c;
        int down = scoreDown(dealerCards);

        while(down <= 16) {
            c = generateCard();
            dealerCards.add(c);
            down = scoreDown(dealerCards);
        }
        //if the player exceeds 21 points, then there's no need for dealer to continue.
        if(scoreDown(playerCards)<=21)
            drawDealer.repaint();

    }

    public void gameCheckout(){

        //game checkout is used to judge who wins
        //if player exceeds 21, he loses
        //if player doesn't exceed, and dealer exceeds, dealer loses
        //in other cases, have to compare the final scores of both
        // scoreUp is the "points" with soft Ace, scoreDown is the "points" with hard Ace
        if(scoreDown(playerCards) > 21) {
            text4.setText("Dealer Wins!");
            nBet = Integer.parseInt(textBet.getText());
            nMoney -= nBet;
            textCurrentMoney.setText(Integer.toString(nMoney));
            playerScore = scoreDown(playerCards);
            System.out.println("you are out of 21");
            textPlayerScore.setText(Integer.toString(playerScore));
        }
        else if (scoreDown(dealerCards) > 21) {
            text4.setText("You Win!");
            nBet = Integer.parseInt(textBet.getText());
            nMoney += 2 * nBet;
            textCurrentMoney.setText(Integer.toString(nMoney));
            System.out.println("dealer out of 21");
            if (scoreUp(playerCards) > 21)
                playerScore = scoreDown(playerCards);
            else playerScore = scoreUp(playerCards);
            textPlayerScore.setText(Integer.toString(playerScore));
        }
        else {
            if (scoreUp(playerCards) > 21)
                playerScore = scoreDown(playerCards);
            else playerScore = scoreUp(playerCards);

            if (scoreUp(dealerCards) > 21)
                dealerScore = scoreDown(dealerCards);
            else dealerScore = scoreUp(dealerCards);

            if (playerScore >= dealerScore) {
                text4.setText("You Win!");
                nBet = Integer.parseInt(textBet.getText());
                nMoney += 2 * nBet;
                textCurrentMoney.setText(Integer.toString(nMoney));
                System.out.println("Your score:" + playerScore);
                System.out.println("Dealer Score:" + dealerScore);
            } else {
                text4.setText("Dealer Wins!");
                nBet = Integer.parseInt(textBet.getText());
                nMoney -= nBet;
                textCurrentMoney.setText(Integer.toString(nMoney));
                System.out.println("Your score:" + playerScore);
                System.out.println("Dealer Score:" + dealerScore);
            }
            textPlayerScore.setText(Integer.toString(playerScore));
        }
        runningFlag = 0;
    }

    public Card generateCard(){
        //generateCard will return a new random card
        //It checked whether this new card has ever appeared
        // in either dealer's cardList or player's cardList, if so, find a new card
        //since there is only one copy for each different card in one round
        int flag;
        Card c;

        do {
            flag = 0;
            cardType tempCardType = cardType.values()[(int) (4 * Math.random())];
            int tempInt = (int) (13 * Math.random() + 1);
            c = new Card(tempCardType, tempInt);
            for (Card playerCard : playerCards) {
                if (playerCard.equals(c)) flag = 1;
            }
            for (Card dealerCard : dealerCards) {
                if (dealerCard.equals(c)) flag = 1;
            }
        } while (flag == 1);
        return c;
    }

    public int scoreUp(ArrayList<Card> cards){
        //points with soft Ace
        int sum = 0;
        int num_of_A = 0;
        for (Card card : cards) {
            if (card.getNumber()==1)
                num_of_A++;
            sum += card.getPoint();
        }
        if(num_of_A == 0)
            return sum;
        else
            return (sum+10);
    }

    public int scoreDown(ArrayList<Card> cards){
        //points with hard Ace
        int sum = 0;
        for (Card card : cards) {
            sum += card.getPoint();
        }
        return sum;
    }

}
