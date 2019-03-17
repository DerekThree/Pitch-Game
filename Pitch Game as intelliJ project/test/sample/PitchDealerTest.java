package sample;

import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PitchDealerTest {

    PitchDealer dealer;

    @BeforeEach
    void setup(){
        JFXPanel fxPanel = new JFXPanel();
        dealer = new PitchDealer();
        dealer.shuffle();
    }
    @Test
    void dealHand1() {
        ArrayList<Card> hand = dealer.dealHand();
        assertEquals(hand.size(), 6, "dealer deals wrong number of cards");
    }
    @Test
    void dealHand2() {
        ArrayList<Card> hand1 = dealer.dealHand();
        ArrayList<Card> hand2 = dealer.dealHand();
        assertNotSame(hand1, hand2, "dealer deals the same hand twice");
    }
    @Test
    void shuffle() {
        ArrayList<Card> hand = dealer.dealHand();
        dealer.shuffle();
        assertEquals(dealer.cardsLeft(), 52, "Dealer did not shuffle");
    }
    @Test
    void cardsLeft1() {
        ArrayList<Card> hand = dealer.dealHand();
        assertEquals(dealer.cardsLeft(), 46, "cardsLeft1 error");
    }
    @Test
    void cardsLeft2() {
        assertEquals(dealer.cardsLeft(), 52, "cardsLeft2 error");
    }
}