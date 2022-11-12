package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/* Main is a subclass for Application.  Sets up an entry point for the application.  The Application class manages the
lifecycle of an application.  */
public class Main extends Application {

    /* Stage is a top level JavaFX container that extends the Window class, so essentially, it’s a main window.
    The JavaFX runtime constructs runtime constructs the initial stage and passes it into the start method.  */
    @Override
    public void start(Stage primaryStage) throws Exception{
        /* Loads the UI from the sample.fxml file.  sample.fxml is a file name, a file can have many names, if the name
         * isn’t sample.fxml, then I have to change it.  */
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        /*
        // I can also set GridPane as root inside the Main class rather than in FXML file.
        GridPane root = new GridPane();
        // Components inside the root layout will be aligned to the middle.
        root.setAlignment(Pos.CENTER);
        // Sets the horizontal gap between components to 10.
        root.setVgap(10);
        // Sets the vertical gap between components to 10.
        root.setHgap(10);
        // Set the title of the application.

        // Create a Label object greeting.
        Label greeting = new Label("Welcome to JavaFX!  ");
        // Sets the coolr of greeting (Label) to green.
        greeting.setTextFill(Color.GREEN);
        // Set the font to Times New Roman, bold, and size 70.
        greeting.setFont(Font.font("Times New Roman", FontWeight.BOLD, 70));
        // I add the greeting object (Label) to the UI.
        root.getChildren().add(greeting);
        */

        primaryStage.setTitle("Hello JavaFX!");
        // GridPane node will be set as the root of the scene, with 700 as width and 275 as height.
        primaryStage.setScene(new Scene(root, 700, 275));
        // Returns immediately regardless of the modality of the stage.
        primaryStage.show();
    }


    public static void main(String[] args) {
        /* Launch the application.  */
        launch(args);
    }
}
