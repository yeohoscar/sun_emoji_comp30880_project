package texas_hold_em;

import poker.Card;
import texas_scramble.Hand.HandElement;

// Deck interface for use with polymorphism

public interface Deck {
    void reset();
    void shuffle();
    HandElement dealNext();
    Hand dealHand();
}
