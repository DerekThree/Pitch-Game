package sample;

import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PitchTest {

    Pitch table = new Pitch(2);

    @BeforeEach
    void setup(){
        JFXPanel fxPanel = new JFXPanel();
    }

    @Test
    void createDealer1() {
        assertNotNull(table.createDealer(), "Error in Pitch.createDealer()");
    }

    @Test
    void createDealer2() {
        PitchDealer dealer = table.createDealer();
        table = new Pitch(4);
        assertNotSame(table.createDealer(),dealer, "Pitch.createDealer() creates wrong dealer");
    }


    @Test
    void getNumPlayers() {
        assertEquals(table.getNumPlayers(), 2, "Pitch.getNumPlayers error");
    }
    @Test
    void getNumPlayers2() {
        table = new Pitch(3);
        assertEquals(table.getNumPlayers(), 3, "Pitch.getNumPlayers error");
    }@Test
    void getNumPlayers3() {
        table = new Pitch(4);
        assertEquals(table.getNumPlayers(), 4, "Pitch.getNumPlayers error");
    }

}