package sample;

import javafx.application.Application;
import javafx.stage.Stage;

public class Game extends Application {

    public static Stage myStage;
    public static WelcomeMenu mainMenu;
    public static GameScreen gameScreen;
    public static Pitch pitchTable;


    public static void main(String[] args) {launch(args);}

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Game of Pitch");
        myStage = primaryStage;

        mainMenu = new WelcomeMenu();
        mainMenu.showScene();
        primaryStage.show();
    }
}
