package BlackJack;

/**
 * Created by Bing on 11/15/2014 0015.
 */

public class Card {

    private cardType type;
    private int number;

    public boolean equals(Card card) {

        return (card.type == this.type && card.number == this.number);
    }

    public Card() {

    }

    public Card(cardType type, int number) {
        this.type = type;
        this.number = number;
    }

    public cardType getType() {
        return type;
    }

    public int getNumber() {
        return number;
    }

    public int getPoint(){
        return Math.min(number, 10);
    }

}
