package BlackJack;

/**
 * Created by Bing on 11/15/2014 0015.
 */
public enum cardType {

    SPADE,
    DIAMOND,
    CLUB,
    HEART;

    public String toString() {
        switch(this) {
            case SPADE: return "spade";
            case DIAMOND: return "diamond";
            case CLUB: return "club";
            case HEART: return "heart";
            default: throw new IllegalArgumentException();
        }
    }
}
