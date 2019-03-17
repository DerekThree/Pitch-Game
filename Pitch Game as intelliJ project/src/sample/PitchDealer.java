package sample;

import java.util.ArrayList;

public class PitchDealer implements Dealer{

    private Deck pitchDeck = new Deck();

    public ArrayList<Card> dealHand(){

        ArrayList<Card> hand = new ArrayList();

        for (int i=0; i<6; i++){
            hand.add(pitchDeck.draw());
        }
        return hand;
    }

    public void shuffle(){
        pitchDeck = new Deck();
    }

    public int cardsLeft(){
        return pitchDeck.cardsLeft();
    }
}

interface Dealer {
    public ArrayList<Card> dealHand();
}