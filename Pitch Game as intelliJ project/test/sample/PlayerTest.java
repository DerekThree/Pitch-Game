package sample;

import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    PitchDealer dealer;
    Player player;

    @BeforeEach
    void setup(){
        JFXPanel fxPanel = new JFXPanel();
        player = new Player();
        dealer = new PitchDealer();
    }

    @Test
    void setHand1() {
        ArrayList<Card> hand = dealer.dealHand();
        player.setHand(hand);
        assertSame(player.getHand(), hand, "Player.setHand1 error");
    }

    @Test
    void setHand2() {
        ArrayList<Card> hand = dealer.dealHand();
        player.setHand(hand);
        player.setHand(dealer.dealHand());
        assertNotSame(player.getHand(), hand, "Player.setHand2 error");
    }

    @Test
    void getHand1() {
        assertNull(player.getHand(), "Player.getHand1 error");
    }

    @Test
    void getHand2() {
        player.setHand(null);
        assertNull(player.getHand(), "Player.getHand1 error");
    }

    @Test
    void getHand3() {
        player.setHand(dealer.dealHand());
        player.setHand(dealer.dealHand());
        assertNotNull(player.getHand(), "Player.getHand1 error");
    }


}