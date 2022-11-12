package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class Controller {

    @FXML
    private Label label;
    @FXML
    private Button button4;
    @FXML
    private GridPane gridPane;

    public void initialize() {
        /* Create a drop shadow effect for button 4.  */
        button4.setEffect(new DropShadow());
    }


    /* When the mouse hover over the element...  */
    @FXML
    public void handleMouseEnter() {
        /* Make the node (label) twice as big.  */
        label.setScaleX(2.0);
        label.setScaleY(2.0);
    }


    /* When the mouse stop hover over the element.  */
    @FXML
    public void handleMouseExit() {
        /* Make the node (label) to normal side.  */
        label.setScaleX(1.0);
        label.setScaleY(1.0);
    }


    @FXML
    public void handleClick() {
        /* Create a FileChooser, which allows user to navigate the file system to choose files.  */
        FileChooser chooser = new FileChooser();

        /* Set title of the file chooser.  */
        chooser.setTitle("Save Application File");

        /* If it's showOpenDialog():  Allow me filter files by type so I can only view and open a certain type of files.
        * If it's showSaveDialog():  Allow me to save file by different types.  */
        chooser.getExtensionFilters().addAll(
                /* If it's showOpenDialog():  Allow me to filter files so I can only view and open .zip type files.
                * If it's showSaveDialog():  Allow me to save file as .zip type.  */
                new FileChooser.ExtensionFilter("Zip", "*.zip"),
                new FileChooser.ExtensionFilter("Text", "*.txt"),
                new FileChooser.ExtensionFilter("PDF", "*.pdf"),
                /* If it's showOpenDialog():  Allow mje to view and open .jpg, .png, and .gif type files.  */
                new FileChooser.ExtensionFilter("Imagve Files", "*.jpg", "*.png", "*.gif"),
                /* If it's showOpenDialog():  Allow me to view and open all types of files.  */
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        /* Shows a new file open dialog.  Make it so that I have to hit the cancel button or choose a file to exit
        * out of file chooser window.  showOpenDialog makes me choose pre-existing file.  showSaveDialog() let me
        * create a new file and save it or save it onto an existing file.    */
        // File file = chooser.showSaveDialog(gridPane.getScene().getWindow());
        // File file = chooser.showOpenDialog(gridPane.getScene().getWindow());

        /* showOpenMultipleDialog():  Opens a list of files.  */
        List<File> file = chooser.showOpenMultipleDialog(gridPane.getScene().getWindow());

        /* Create a DirectoryChooser, which allows user to select a folder.  */
        // DirectoryChooser chooser = new DirectoryChooser();
        /* Like showOpenDialog() for FileChooser, this method shows a dialog for DirectoryChooser.  */
        // chooser.showDialog(gridPane.getScene().getWindow());

        /* If a choose a file, print out the path to the file.  */
        if(file != null) {
            for(int i = 0; i < file.size(); i++) {
                /* Print out the path of each file.  */
                System.out.println(file.get(i));
            }
            // System.out.println(file.getPath());
        }
        /* Otherwise, print a message indicate that I cancel out of the file chooser window.  */
        else {
            System.out.println("Chooser was cancelled.  ");
        }
    }


    @FXML
    public void handleLinkClick() {
        /*
        try {
            Desktop.getDesktop().browse(new URI("http://www.javafx.com"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        catch(URISyntaxException e) {
            e.printStackTrace();
        }
         */
    }

}
