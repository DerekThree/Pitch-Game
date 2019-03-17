package sample;

import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    @Test
    void cardTest1() {
        JFXPanel fxPanel = new JFXPanel();
        int rank = 14;
        char suit = 'H';
        Card card = new Card(rank, suit);
        assertEquals(rank, card.rank, "Rank failed in test1");
        assertEquals(suit, card.suit, "Suit failed in test1");
    }
    @Test
    void cardTest2() {
        JFXPanel fxPanel = new JFXPanel();
        int rank = 2;
        char suit = 'C';
        Card card = new Card(rank, suit);
        assertEquals(rank, card.rank, "Rank failed in test2");
        assertEquals(suit, card.suit, "Suit failed in test2");
    }
    @Test
    void cardTest3() {
        JFXPanel fxPanel = new JFXPanel();
        int rank = 11;
        char suit = 'D';
        Card card = new Card(rank, suit);
        assertEquals(rank, card.rank, "Rank failed in test3");
        assertEquals(suit, card.suit, "Suit failed in test3");
    }
    @Test
    void cardTest4() {
        JFXPanel fxPanel = new JFXPanel();
        int rank = 5;
        char suit = 'S';
        Card card = new Card(rank, suit);
        assertEquals(rank, card.rank, "Rank failed in test4");
        assertEquals(suit, card.suit, "Suit failed in test4");
    }
    @Test
    void cardTest5() {
        JFXPanel fxPanel = new JFXPanel();
        int rank = 3;
        char suit = 'H';
        Card card = new Card(rank, suit);
        assertEquals(rank, card.rank, "Rank failed in test5");
        assertEquals(suit, card.suit, "Suit failed in test5");
    }
}