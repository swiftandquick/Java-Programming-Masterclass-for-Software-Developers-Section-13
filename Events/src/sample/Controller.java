package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller {

    /* Annotate the instance variable declaration so I can use the variable nameField.  */
    @FXML
    /* Add the variable, the variable has the same name as text field’s fx:id attribute, which is nameField.  */
    private TextField nameField;
    @FXML
    private Button helloButton;
    @FXML
    private Button byeButton;
    @FXML
    private CheckBox ourCheckBox;
    @FXML
    private Label ourLabel;


    @FXML
    public void initialize() {
        /* The buttons are initially disabled.  */
        helloButton.setDisable(true);
        byeButton.setDisable(true);
    }


    /* add the method, the method has to have the same name as the button’s onAction attribute’s value, which is
    onButtonClicked.  o	ActionEvent is an Event representing some type of action. This event type is widely used to
    represent a variety of things, such as when a Button has been fired, when a KeyFrame has finished, and other such
    usages.  */
    @FXML
    public void onButtonClicked(ActionEvent e) {
        /* getSource():  The object on which the Event initially occurred.  In this case, I retrieve the source of
         * the event, which is the button I clicked.  Use conditional statement to check which button I clicked,
         * the helloButton or the byeButton.  */
        if(e.getSource().equals(helloButton)) {
            /* After clicking on the button, the console will print “Hello, “ and whatever is currently in the text field.
            If I have “Tim” in the text field via getText() method, console will print “Hello, Tim”.  */
            System.out.println("Hello, " + nameField.getText());
        }
        /* If byeButton is clicked, console will print out "Bye, " and the content in the text field.  */
        else if(e.getSource().equals(byeButton)) {
            System.out.println("Bye, " + nameField.getText());
        }

        /* Runnable interface is implemented by any class whose instances are intended to be executed by a thread.
        This is an anonymous inner class where I can use to override methods like run().  */
        Runnable task = new Runnable() {
            /* Starting the thread causes the object's run method to be called in that separately executing thread.  */
            @Override
            public void run() {
                /* Put current thread to sleep for 10 seconds, I have to wait for 10 seconds for ourLabel's text to
                reset.  */
                try {
                    /* Returns true if the calling thread is the JavaFX Application Thread.  */
                    String s = Platform.isFxApplicationThread() ? "UI Thread" : "Background Thread";
                    System.out.println("I'm going to sleep on the:  " + s + ".  ");
                    /* Sleep on the background thread.  */
                    Thread.sleep(10000);
                    /* Run the specified Runnable on the JavaFX Application Thread at some unspecified time in the
                    future.  */
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            /* Update the JavaFX Application Thread (UI Thread).  */
                            String s = Platform.isFxApplicationThread() ? "UI Thread" : "Background Thread";
                            System.out.println("I'm updating the label on the:  " + s + ".  ");
                            /* Set new text to the label ourLabel.  */
                            ourLabel.setText("We did something!  ");
                        }
                    });
                }
                catch (InterruptedException event) {
                    /* No action. */
                }
            }
        };

        /* Start an anonymous thread, put Runnable interface task as argument, invoke the start() method.  Once
        * start() is invoked, run() will be called.  */
        new Thread(task).start();

        /* If the check box is checked, clear the content of the text field and disable the buttons.  */
        if(ourCheckBox.isSelected()) {
            nameField.clear();
            helloButton.setDisable(true);
            byeButton.setDisable(true);
        }
    }


    /* A handler for when key is released.  */
    @FXML
    public void handleKeyReleased() {
        /* Retrieve text from the nameField (text field).  */
        String text = nameField.getText();
        /* Set disableButtons to true true if the nameField is empty or is empty after trimming.  */
        boolean disableButtons = text.isEmpty() | text.trim().isEmpty();
        /* If text is empty, disable both buttons, otherwise enable them.  */
        helloButton.setDisable(disableButtons);
        byeButton.setDisable(disableButtons);
    }


    @FXML
    public void handleChange() {
        /* Checks to see if the check box is checked.  */
        System.out.println("The checkbox is " + (ourCheckBox.isSelected() ? "checked" : "not checked"));
    }

}
