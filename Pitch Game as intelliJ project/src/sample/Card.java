package sample;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Card extends Button{

    final int rank;
    final char suit;
    final ImageView view;

    public Card(int theRank, char theSuit){

        rank = theRank;
        suit = theSuit;
 //       this.setDisable(true);

        view = new ImageView(new Image(rank + Character.toString(suit) + ".png"));
        view.setFitHeight(200);
        view.setFitWidth(80);
        view.setPreserveRatio(true);

        setGraphic(view);
    }
}
