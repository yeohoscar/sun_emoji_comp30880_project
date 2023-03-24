import poker.Card;

public class BlackjackHand {
    public static final int MAX_NUM_CARDS = 5;
    public static final int NUM_CARDS_DEALT = 2;
    private static final int MAX_HAND_VALUE = 21;
    private Card[] hand;
    private DeckOfCards deck;

    private int stake = 0;
    private int numCardsInHand = NUM_CARDS_DEALT;

    private boolean surrender = false;

    // Constructors

    public BlackjackHand(DeckOfCards deck) {
        this.deck = deck;
        hand = new Card[MAX_NUM_CARDS];

        for (int i = 0; i < NUM_CARDS_DEALT; i++) {
            setCard(i, deck.dealNext());
        }
    }

    public BlackjackHand(BlackjackHand hand) {
        this.deck = hand.deck;
        this.hand = new Card[MAX_NUM_CARDS];
        this.stake = hand.stake;
        this.numCardsInHand = hand.numCardsInHand - 1;
        this.surrender = hand.surrender;
        setCard(0, hand.getCard(0));
        hand.setNumCardsInHand(numCardsInHand - 1);
    }

    // Display hand

    public String toString() {
        String desc = "\n      Stake: " + stake;

        for (int i = 0; i < numCardsInHand; i++)
            desc = desc + "\n      " + i + ":  " + getCard(i).toString();

        return desc + "\n";
    }

    // Utility method to deal a card to hand

    public void addCard() {
        if (numCardsInHand == 5) return;
        setCard(numCardsInHand, deck.dealNext());
        numCardsInHand++;
    }

    // Modifier

    public void setCard(int num, Card card) {
        if (num >= 0 && num <= numCardsInHand)
            hand[num] = card;
    }

    public void setStake(int stake) {
        this.stake = stake;
    }

    public void setNumCardsInHand(int numCards) {
        numCardsInHand = numCards;
    }


    // Accessors

    public Card getCard(int num) {
        if (num >= 0 && num < numCardsInHand)
            return hand[num];
        else
            return null;
    }

    public int getStake() {
        return stake;
    }

    public int getNumCardsInHand() {
        return numCardsInHand;
    }

    // Calculates value of hand

    public int getValue() {
        int i = 0, total = 0, aces = 0;
        while (i < 5 && hand[i] != null) {
            total += hand[i].getValue();
            if (hand[i].isAce()) {
                aces++;
            }
            i++;
        }

        while (total > 21 && aces > 0) {
            total -= 10;
            aces--;
        }

        return total;
    }

    // Check if hand exceeds 21

    public boolean isBusted() {
        return getValue() > MAX_HAND_VALUE;
    }


    public Card[] getHand(){
        return hand;
    }

    public boolean hasAce() {
        for (int i = 0; i < numCardsInHand; i++) {
            if (hand[i].isAce()) {
                return true;
            }
        }
        return false;
    }

    public void surrender() {
        surrender = true;
    }

    public boolean hasSurrendered() {
        return surrender;
    }
}
