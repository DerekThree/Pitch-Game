package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import static sample.Game.myStage;
import static sample.Game.pitchTable;

// The first screen the user sees.
// Has a scene and menu buttons
public class WelcomeMenu {

    private BorderPane pane = new BorderPane();
    private Scene welcomeScene = new Scene(pane, 800, 800);
    private int numPlayers = 2;

    // Welcome screen buttons and text
    private Text howManyText = new Text("How many opponents:");
    private Button p2btn = new Button("One");
    private Button p3btn = new Button("Two");
    private Button p4btn = new Button("Three");
    private Button startBtn = new Button("Start");
    private Button exitBtn = new Button("Exit");

    WelcomeMenu() {

        // Event handlers for welcome screen buttons
        p2btn.setOnAction(e -> {
                numPlayers = 2;
                p2btn.setDisable(true);
                p3btn.setDisable(false);
                p4btn.setDisable(false);
        });
        p3btn.setOnAction(e -> {
                numPlayers = 3;
                p3btn.setDisable(true);
                p2btn.setDisable(false);
                p4btn.setDisable(false);
        });
        p4btn.setOnAction(e -> {
                numPlayers = 4;
                p4btn.setDisable(true);
                p2btn.setDisable(false);
                p3btn.setDisable(false);
        });
        startBtn.setOnAction(e -> {
              playPitch();
        });
        exitBtn.setOnAction(e -> System.exit(0));

        // Create menu layout by lining up buttons on the pane
        HBox numPlayersBtns = new HBox(10, p2btn, p3btn, p4btn);
        HBox startExitBtns = new HBox(10, startBtn, exitBtn);
        VBox welcomeVBox = new VBox(10, howManyText, numPlayersBtns, startExitBtns);
        welcomeVBox.setAlignment(Pos.TOP_CENTER);
        numPlayersBtns.setAlignment(Pos.CENTER);
        startExitBtns.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(70));
        pane.setCenter(welcomeVBox);
    }

    // Creates and displays a new game of pitch
    public void playPitch(){
        pitchTable = new Pitch(numPlayers);
        pitchTable.showScene();
    }

    // Displays main menu
    public void showScene(){
        myStage.setScene(welcomeScene);
    }
}
