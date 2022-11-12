package com.cheny16.todolist;

import com.cheny16.todolist.datamodel.TodoData;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainwindow.fxml"));
        primaryStage.setTitle("Todo List");
        primaryStage.setScene(new Scene(root, 900, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


    /* This method is called when the application should stop and provides a convenient place to prepare for application
    exit and destroy resources.  */
    @Override
    public void stop() throws Exception {
        try {
            /* Save data to the text file, that's when we close down the program.  storeTodoItems() create a text
            file every time the application is closed.  */
            TodoData.getInstance().storeTodoItems();
        }
        catch(IOException e) {
            /* Print out the exception message.  */
            System.out.println(e.getMessage());
        }
    }


    /* The initialization method, it's called on launcher thread.  */
    @Override
    public void init() throws Exception {
        try {
            /* loadTodoItems() is called when the application starts.  Every time the application starts, the
            * add items from the text file into the instance variable todoItems.  */
            TodoData.getInstance().loadTodoItems();
        }
        catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
