package com.cheny16.todolist;

import com.cheny16.todolist.datamodel.TodoData;
import com.cheny16.todolist.datamodel.TodoItem;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class DialogController {

    @FXML
    private TextField shortDescriptionField;
    @FXML
    private TextArea detailsArea;
    @FXML
    private DatePicker deadlinePicker;


    /* Takes inputs from the text field, the text area, and the date picker to create a new TodoItem, then add
    * TodoItem to the todoItems ArrayList.  */
    public TodoItem processResults() {
        String shortDescription = shortDescriptionField.getText().trim();
        String details = detailsArea.getText().trim();
        LocalDate deadlineValue = deadlinePicker.getValue();

        /* Create the object newItem, newItem retrieve inputs from three text field, text area, and date picker.  */
        TodoItem newItem = new TodoItem(shortDescription, details, deadlineValue);

        /* Add the newItem (TodoItem) to TodoData's current instance's todoItems List.  */
        TodoData.getInstance().addTodoItem(newItem);

        /* Return the newly created object.  */
        return newItem;
    }
    
}
