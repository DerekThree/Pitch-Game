package sample;

import java.util.ArrayList;
import java.util.Random;

// A deck of cards with cards left in it
// Deck is shuffled by creating a new deck
// When drawing a card it gives a random card,
// this way it doesn't need to be randomized after creation
public class Deck {

    private ArrayList<Card> sortedCards; // sorted list of cards

    // Constructor populates sortedCards
    public Deck() {

        sortedCards = new ArrayList<Card>();

        // populate sortedCards
        for (int i = 0; i < 13; i++) {
            sortedCards.add(new Card(i + 2, 'C'));
            sortedCards.add(new Card(i + 2, 'D'));
            sortedCards.add(new Card(i + 2, 'H'));
            sortedCards.add(new Card(i + 2, 'S'));
        }
    }

    // draws and returns a random card from sortedCards
    public Card draw(){

        Random randomGenerator = new Random();
        Card drawn = null;

        int index = randomGenerator.nextInt(sortedCards.size());
        drawn = sortedCards.get(index);
        sortedCards.remove(index);
        return drawn;
    }

    // returns number of cards left in the deck
    public int cardsLeft(){ return sortedCards.size(); }
}
