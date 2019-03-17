package sample;

import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    Deck deck;

    @BeforeEach
    void setup(){
        JFXPanel fxPanel = new JFXPanel();
        deck = new Deck();
    }

    @Test
    void draw1() {
        for (int i=0; i<52; i++){
            Card card = deck.draw();
            assertTrue(card.rank > 1, "Rank of card less than 2");
        }
    }
    @Test
    void draw2() {
        for (int i=0; i<52; i++){
            Card card = deck.draw();
            assertTrue(card.rank < 15, "Rank of card higher than 14");
        }
    }
    @Test
    void draw3() {
        for (int i=0; i<52; i++){
            Card card = deck.draw();
            assertTrue(
            (card.suit == 'C') ||
                    (card.suit == 'D') ||
                    (card.suit == 'H') ||
                    (card.suit == 'S'),
            "Unknown suit in the deck");
        }
    }
    @Test
    void cardsLeft1() {
        for (int i=0; i<52; i++){
            Card card = deck.draw();
        }
        assertEquals(deck.cardsLeft(), 0, "Deck failed to be empty");
    }
    @Test
    void cardsLeft2() {
        assertEquals(deck.cardsLeft(), 52, "Deck does not have 52 cards");
        }
    }