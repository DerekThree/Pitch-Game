package sample;

import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AIPlayerTest {

    AIPlayer player = new AIPlayer();
    ArrayList<Card> hand = new ArrayList<Card>();
//    PitchDealer dealer = new PitchDealer();

    @BeforeEach
    void setup(){
        JFXPanel fxPanel = new JFXPanel();
        player = new AIPlayer();
//        dealer = new PitchDealer();
        hand.add(new Card(14, 'H'));
        hand.add(new Card(11, 'H'));
        hand.add(new Card(3, 'H'));
        hand.add(new Card(14, 'S'));
        hand.add(new Card(5, 'H'));
        hand.add(new Card(14, 'C'));
        player.setHand(hand);
    }

    @Test
    void haveHigh() {
        assertTrue(player.haveHigh('C'));
    }

    @Test
    void haveJ() {
        assertTrue(player.haveJ('H'));
    }

    @Test
    void haveLow() {
        assertTrue(player.haveLow('H'));
    }

    @Test
    void haveGame() {
        assertTrue(player.haveGame('H'));
    }

    @Test
    void haveSuit() {
        assertFalse(player.haveGame('H'));
    }
}