package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.util.ArrayList;
import static sample.Game.*;

// The screen seen during play
// Has a scene, top buttons, score board, trump info, hand with user's cards,
// center switches between bidding menu and pile to throw cards on
public class GameScreen {

    private BorderPane pane = new BorderPane();
    private Scene gameScene = new Scene(pane, 800, 800);
    private ArrayList<String> names = new ArrayList(); // names of players
    private int numPlayers; // number of players
    private int roundNumber = 1; // game has 6 rounds starting at round 1

    // Top buttons and boxes
    private Button nextHandBtn = new Button("Next Hand");
    private Button exitBtn = new Button("Exit");
    private Button newGameBtn = new Button("New Game");
    private HBox topHBox = new HBox(10, newGameBtn, exitBtn, nextHandBtn);

    // Bidding buttons and boxes
    private Button passBtn = new Button("Pass");
    private Button bid1Btn = new Button("1");
    private Button bid2Btn = new Button("2");
    private Button bid3Btn = new Button("3");
    private Button bid4Btn = new Button("4");
    private Button bid5Btn = new Button("5");
    private HBox bidHBox = new HBox(10, passBtn, bid1Btn, bid2Btn, bid3Btn, bid4Btn, bid5Btn);
    private VBox bidVBox = new VBox(10, new Text("Your bid:"), bidHBox);

    // Score board
    private VBox namesVBox = new VBox(10);
    private VBox scoreVBox = new VBox(10);
    private VBox bidTextVBox = new VBox(10);
    private VBox bidValVBox = new VBox(10);
    private HBox scoreHBox = new HBox(10, namesVBox, scoreVBox, bidTextVBox, bidValVBox);

    // trump info
    private ImageView trumpView;
    private VBox trumpVBox = new VBox(10, new Text("Trump"));

    // Row of cards in hand
    private HBox handHBox = new HBox(10);

    // Pile to throw cards on
    private HBox pile = new HBox(10);
    private Button pileOKButton = new Button("OK");
    private HBox pileOKButtonHBox = new HBox(pileOKButton);
    private VBox pileBox = new VBox(20, pile, pileOKButtonHBox);

    GameScreen(int numberOfPlayers) {

        pane.setPadding(new Insets(70));
        numPlayers = numberOfPlayers;

        // populate score board
        names.add("You");
        names.add("Opponent 1");
        names.add("Opponent 2");
        names.add("Opponent 3");
        for (int i=0; i<numPlayers; i++) {
            namesVBox.getChildren().add(new Text(names.get(i) + ":"));
            scoreVBox.getChildren().add(new Text("0"));
            bidTextVBox.getChildren().add(new Text("Bid:"));
        }
        pane.setLeft(scoreHBox);

        // Display trump info board
        trumpVBox.setMinWidth(100);
        pane.setRight(trumpVBox);

        // Event handlers for top buttons
        nextHandBtn.setOnAction(e -> {
            pitchTable.dealHand();
            nextHandBtn.setDisable(true);
        });
        exitBtn.setOnAction(e -> mainMenu.showScene());
        newGameBtn.setOnAction(e -> {
            mainMenu.playPitch();
        });
        // Layout for top buttons
        nextHandBtn.setDisable(true);
        topHBox.setAlignment(Pos.TOP_CENTER);
        pane.setTop(topHBox);

        // Event handlers for bidding menu buttons
        passBtn.setOnAction(e -> {
            pitchTable.makeBid(0);
            pane.setCenter(pileBox);
            pitchTable.findWinningBidder();
        });
        bid1Btn.setOnAction(e -> {
            pitchTable.makeBid(1);
            pane.setCenter(pileBox);
            pitchTable.findWinningBidder();
        });
        bid2Btn.setOnAction(e -> {
            pitchTable.makeBid(2);
            pane.setCenter(pileBox);
            pitchTable.findWinningBidder();
        });
        bid3Btn.setOnAction(e -> {
            pitchTable.makeBid(3);
            pane.setCenter(pileBox);
            pitchTable.findWinningBidder();
        });
        bid4Btn.setOnAction(e -> {
            pitchTable.makeBid(4);
            pane.setCenter(pileBox);
            pitchTable.findWinningBidder();
        });
        bid5Btn.setOnAction(e -> {
            pitchTable.makeBid(5);
            pane.setCenter(pileBox);
            pitchTable.findWinningBidder();
        });
        // Layout for bidding menu buttons
        bidHBox.setAlignment(Pos.CENTER);
        bidVBox.setAlignment(Pos.CENTER);

        // layout for cards in hand
        handHBox.setAlignment(Pos.CENTER);
        pane.setBottom(handHBox);

        // Layout for pile to throw cards on
        // with an invisible OK button
        pile.setAlignment(Pos.CENTER);
        pileBox.setAlignment(Pos.CENTER);
        pileOKButtonHBox.setAlignment(Pos.BASELINE_RIGHT);
        // This button will show up to clean the pile
        pileOKButton.setVisible(false);
        pileOKButton.setOnAction(e -> {
            pile.getChildren().clear();
            updatePile(new ArrayList<Card>());
            pileOKButton.setVisible(false);
            // decide player's order for the next round
            // or count score if 6 rounds played
            roundNumber++;
            if (roundNumber < 7)
                pitchTable.makeOrderedList();
            else {
                roundNumber = 1;
                pitchTable.countScore();
            }
        });

    }

    // Displays trump based on the argument
    void updateTrump(char newTrump){
        ImageView trumpView = new ImageView(new Image(newTrump + ".png"));
        trumpVBox.getChildren().add(trumpView);
        trumpView.setFitHeight(50);
        trumpView.setFitWidth(50);
        trumpView.setPreserveRatio(true);
    }

    // Displays newHand as a row of cards
    public void updateHand(ArrayList<Card> newHand){
        handHBox.getChildren().clear();
        handHBox.getChildren().addAll(newHand);
    }

    // displays scores of players based on the argument
    public void updateScore(ArrayList<Integer> scores){
        bidValVBox.getChildren().clear();
        trumpVBox.getChildren().clear();
        trumpVBox.getChildren().add(new Text("Trump"));
        nextHandBtn.setDisable(false);
        scoreVBox.getChildren().clear();
        scores.forEach(i -> scoreVBox.getChildren().add(new Text(Integer.toString(i))));
    }

    void displayWinner(int winner){
        if (winner == 0){
            ImageView youWin = new ImageView(new Image("YouWin.jpg"));
            pane.setCenter(youWin);
            youWin.setFitHeight(200);
            youWin.setPreserveRatio(true);
        }
        else{
            Text youLose = new Text(names.get(winner) + " wins...");
            pane.setCenter(youLose);
        }
        nextHandBtn.setDisable(true);
    }

    void updatePile(ArrayList<Card> newPile){
        pile.getChildren().clear();
        newPile.forEach(card -> pile.getChildren().add(card.view));
    }

    void showPileOKButton(){
        pileOKButton.setVisible(true);
        handHBox.getChildren().forEach(card -> card.setDisable(true));
    }

    // Returns the game play scene
    public Scene getScene(){
        return gameScene;
    }

    // Displays the game scene
    public void showScene(){
        myStage.setScene(gameScene);
    }

    // Prompts for a user's bid and calls makeAIBids
    public void makeUserBid(){
        pane.setCenter(bidVBox);
    }

    // Displays bids on the screen
    void updateBids(ArrayList<Integer> bids){
        bidValVBox.getChildren().clear();
        bids.forEach(i -> {
            if (i == 0) bidValVBox.getChildren().add(new Text("Pass"));
            else bidValVBox.getChildren().add(new Text(Integer.toString(i)));
        });
    }

}
