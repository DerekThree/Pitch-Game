package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.util.ArrayList;

import static sample.Game.gameScreen;
import static sample.Game.pitchTable;


public class Player {

    ArrayList<Card> hand;
    ArrayList<Card> tricks;

    // picks up the cards and resets tricks and numBids
    public void setHand(ArrayList<Card> newHand){
        hand = newHand;
        tricks = new ArrayList<Card>();
    }

    // change to return a copy
    ArrayList<Card> getHand(){
        return hand;
    }

    public void bid(){
    }

    void pickCard(){

        boolean suitFound = false; // True if player has the leading suit

        // activate setOnAction on cards in hand
        // and disable them if they don't follow
        // leading suit or trump

        for (int i=0; i<hand.size();i++) {
            Card card = hand.get(i);
            // Disable the card unless it follows trump or suit
            if ((card.suit != pitchTable.getTrump()) && (card.suit != pitchTable.getLeadingSuit()))
                card.setDisable(true);
            else
                card.setDisable(false);

            // if player has the suit he will have to play this or trump
            if (card.suit == pitchTable.getLeadingSuit())
                suitFound = true;

            card.setOnAction(e -> {
                    // Move card from hand to pile
                    pitchTable.putOnPile(card);
                    hand.remove(card);
                    gameScreen.updateHand(hand);

                    // Tell AI players whose turn is after the user
                    // to play their cards
                    pitchTable.userPlayedACard();
                }
            );
        }

        // If player has no leading suit then he can play anything
        if (!suitFound) hand.forEach(card -> card.setDisable(false));

    }
}
