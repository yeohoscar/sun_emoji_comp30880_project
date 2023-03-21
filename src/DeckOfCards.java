import poker.Card;
import poker.FaceCard;
import poker.NumberCard;

public class DeckOfCards extends poker.DeckOfCards {
    private Card[] deck = new Card[NUMCARDS];
    private int next = 0;
    public DeckOfCards() {
        for (int i = 0; i < suits.length; i++) {
            deck[next++] = new NumberCard("Ace", suits[i], 1, 11);
            deck[next++] = new NumberCard("Deuce", suits[i], 2);
            deck[next++] = new NumberCard("Three", suits[i], 3);
            deck[next++] = new NumberCard("Four", suits[i], 4);
            deck[next++] = new NumberCard("Five", suits[i], 5);
            deck[next++] = new NumberCard("Six", suits[i], 6);
            deck[next++] = new NumberCard("Seven", suits[i], 7);
            deck[next++] = new NumberCard("Eight", suits[i], 8);
            deck[next++] = new NumberCard("Nine", suits[i], 9);
            deck[next++] = new NumberCard("Ten", suits[i], 10);
            deck[next++] = new FaceCard("Jack", suits[i], 10);
            deck[next++] = new FaceCard("Queen", suits[i], 10);
            deck[next++] = new FaceCard("King", suits[i], 10);
        }

        reset();
    }

    public BlackjackHand dealBlackJackHand() {
        return new BlackjackHand(this);
    }
}
