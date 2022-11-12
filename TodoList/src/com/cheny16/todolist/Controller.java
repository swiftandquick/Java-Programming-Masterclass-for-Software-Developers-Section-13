package com.cheny16.todolist;

import com.cheny16.todolist.datamodel.TodoData;
import com.cheny16.todolist.datamodel.TodoItem;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Controller {

    /* Import and use ToDoItem class from datamodel package.  */
    private List<TodoItem> todoItems;

    /* filteredList is the list of only today's TodoItem items.  */
    private FilteredList<TodoItem> filteredList;

    /* Set predicates.  A Predicate is functional interface that accepts an argument and returns a boolean.  */
    private Predicate<TodoItem> wantAllItems;
    private Predicate<TodoItem> wantTodaysItems;

    /* Make use of the items from mainwindow.fxml.  ListView displays TodoItem type object.  */
    @FXML
    private ListView<TodoItem> todoListView;
    @FXML
    private TextArea itemDetailsTextArea;
    @FXML
    private Label deadlineLabel;
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private ContextMenu listContextMenu;
    @FXML
    private ToggleButton filterToggleButton;


    public void initialize() {

        /* Initialize the ContextMenu.  */
        listContextMenu = new ContextMenu();

        /* Create a MenuItem object that deletes entries from todoItems.  */
        MenuItem deleteMenuItem = new MenuItem("Delete");

        /* Sets the property onAction, sets what would happen if I click on deleteMenuItem (MenuItem).   */
        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            /* Handles event once there based on action performed.  */
            @Override
            public void handle(ActionEvent actionEvent) {
                /* Set item equals currently selected item on the list.  */
                TodoItem item = todoListView.getSelectionModel().getSelectedItem();
                /* Delete the selected item.  */
                deleteItem(item);
            }
        });

        /* Add the deleteMenuItem ("Delete") option in listContextMenu (ContextMenu).  */
        listContextMenu.getItems().addAll(deleteMenuItem);

        todoListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TodoItem>() {
            /* Override the method changed inside an anonymous class to create a listener whenever I select a new item.  */
            @Override
            public void changed(ObservableValue<? extends TodoItem> observableValue, TodoItem oldValue,
                                TodoItem newValue) {
                /* If the newly selected item is not null, display the details (String) attribute in
                itemsDetailsTextArea element, display the deadline (Date) attribute in deadlineLabel element, in
                String format.  */
                if(newValue != null) {
                    TodoItem item = todoListView.getSelectionModel().getSelectedItem();
                    itemDetailsTextArea.setText(item.getDetails());
                    /* Set the format to <month in String> <day>, <year>.  Example:  April 25, 2016.  */
                    DateTimeFormatter df = DateTimeFormatter.ofPattern("MMMM d, yyyy");
                    deadlineLabel.setText(df.format(item.getDeadline()));
                }
            }
        });


        /* Return true because all items need to be on the filteredList if filterToggleButton is not toggled.  */
        wantAllItems = new Predicate<TodoItem>() {
            @Override
            public boolean test(TodoItem todoItem) {
                return true;
            }
        };

        /* Only keep the items that is due on today's date.  */
        wantTodaysItems = new Predicate<TodoItem>() {
            @Override
            public boolean test(TodoItem todoItem) {
                return todoItem.getDeadline().equals(LocalDate.now());
            }
        };

        /* Filter a list, with Predicate of wantAllItems, which means keep all items.  */
        filteredList = new FilteredList<TodoItem>(TodoData.getInstance().getTodoItems(), wantAllItems);

        /* sortedList (SortedList) is a list that sorts the filteredList.  */
        SortedList<TodoItem> sortedList = new SortedList<TodoItem>(filteredList, new Comparator<TodoItem>() {
            @Override
            public int compare(TodoItem o1, TodoItem o2) {
                /* Sort the list base on deadline from oldest to newest.  */
                return o1.getDeadline().compareTo(o2.getDeadline());
            }
        });

        /* Display all objects of ToDoData's current instance variable todoItems.  */
        // todoListView.setItems(TodoData.getInstance().getTodoItems());

        /* Display all objects of sortedList.  */
        todoListView.setItems(sortedList);

        /* Make it so that I can only select one item at the time.  */
        todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        /* Initially select the first item of the list.  */
        todoListView.getSelectionModel().selectFirst();

        /* Sets the cell factory.  The cell factory for all cells in this column.  The cell factory is responsible for
         rendering the data contained within each TableCell for a single table column.
         Callback:  An anonymous class class that implements the Callback interface, which is part of JavaFX API.
         ListView<TodoItem>:  Type of argument provided by the call method, in our case, we will be passing the
         ListView controller.
         ListCell<TodoItem>:  Type that is going be return from the call method, in our case, it's the instance of the
         class ListCell that is going to be returned.  */
        todoListView.setCellFactory(new Callback<ListView<TodoItem>, ListCell<TodoItem>>() {
           /* Callback method.  Return the cell (ListCell<TodoItem>) instance that we created and the ListView will
           * use that instance to paint the cell.  As a result, this allows us to paint every cell based on TodoItem
           * that it contained.  */
            @Override
           public ListCell<TodoItem> call(ListView<TodoItem> param) {
                /* Use anonymous class to create our ListCell instances.  .  */
               ListCell<TodoItem> cell = new ListCell<TodoItem>() {
                   /* Override the updateItem() method.  This method can run whenever the ListView wants to paint a
                   * single cell.  */
                   @Override
                   protected void updateItem(TodoItem item, boolean empty) {
                       super.updateItem(item, empty);
                       /* If cell itself is empty, set text to null.  */
                       if(empty) {
                           setText(null);
                       }
                       else {
                           /* Set the text to item's (TodoItem) shortDescription variable.  */
                           setText(item.getShortDescription());
                           /* If deadline equals to today's date or past due date.  Make the text color red.  */
                           if(item.getDeadline().isBefore(LocalDate.now().plusDays(1))) {
                                setTextFill(Color.RED);
                           }
                           /* If deadline equals to tomorrow's date.  Make the text color brown.  */
                           else if(item.getDeadline().equals(LocalDate.now().plusDays(1))) {
                                setTextFill(Color.BROWN);
                           }
                       }
                   }
               };
               /* Lambda expression.  isNowEmpty checks if cell is empty.  */
               cell.emptyProperty().addListener(
                       (obs, wasEmpty, isNowEmpty) -> {
                           /* If the cell hasn't get anything in it, set the ContextMenu to null, which means I can't
                           see the listContextMenu if I click on an empty cell.  */
                           if (isNowEmpty) {
                               cell.setContextMenu(null);
                           }
                           /* If I didn't click on an empty cell, listContextMenu will show up.  */
                           else {
                               cell.setContextMenu(listContextMenu);
                           }
                       }
               );
               return cell;
           }
        });
    }


    @FXML
    public void showNewItemDialog() {
        /* Create an instance of a Dialog.  */
        Dialog<ButtonType> dialog = new Dialog<ButtonType>();

        /* initOwner():  Specifies the owner Window for this dialog, or null for a top-level, unowned dialog.
        getScene():  Gets the Scene object, the Scene class is the container for all content in a scene graph.
        getWindow():  Gets the Window object, it’s a top level window within which a scene is hosted, and with which
        the user interacts.  */
        dialog.initOwner(mainBorderPane.getScene().getWindow());

        /* Set the title to dialog (DialogPane) to “Add New Todo Item”.  */
        dialog.setTitle("Add New Todo Item");

        /* Set the header text for dialog (DialogPane).  */
        dialog.setHeaderText("Use this dialog to create a new todo item.  ");

        /* Load the todoItemDialog.fxml page.  */
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("todoItemDialog.fxml"));
        try {
            /* Gets the DialogPane and set content to fxmlLoader.load() or todoItemDialog.fxml.  */
            dialog.getDialogPane().setContent(fxmlLoader.load());
        }
        catch(IOException e) {
            System.out.println("Couldn't load the dialog.  ");
            e.printStackTrace();
            return;
        }

        /* Add OK and CANCEL button types on Dialog.  Press OK and item will be added to todoItems.  */
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        /* result's value is base on what button I choose.  */
        Optional<ButtonType> result = dialog.showAndWait();
        /* If button I click on is the OK type button...  */
        if(result.isPresent() && result.get() == ButtonType.OK) {
            /* If I click on the OK button, I invoke the processResults() method, which adds an item into todoItem.  */
            DialogController controller = fxmlLoader.getController();

            /* Set newItem to the returned value from processResults method.  */
            TodoItem newItem = controller.processResults();

            /* Newly added item is immediately selected.  */
            todoListView.getSelectionModel().select(newItem);
        }
    }


    @FXML
    public void handleKeyPressed(KeyEvent keyEvent) {
        TodoItem selectedItem = todoListView.getSelectionModel().getSelectedItem();
        /* If select an item.  */
        if(selectedItem != null) {
            /* Get the key code of the key pressed it, and see if it’s equal to Delete key.  */
            if(keyEvent.getCode().equals(KeyCode.DELETE)) {
                /* Invoke the deleteItem method to delete the selectedItem.  */
                deleteItem(selectedItem);
            }
        }
    }


    @FXML
    public void handleClickListView() {
        TodoItem item = todoListView.getSelectionModel().getSelectedItem();
        /* Display the details.  */
        itemDetailsTextArea.setText(item.getDetails());
        /* Display the deadline in String format.  */
        deadlineLabel.setText(item.getDeadline().toString());
    }


    public void deleteItem(TodoItem item) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        /* Set the title, header text, and content text of the alert.  */
        alert.setTitle("Delete Todo Item");
        alert.setHeaderText("Delete item:  " + item.getShortDescription());
        alert.setContentText("Are you sure?  Press OK to confirm, or cancel to back out.  ");

        /* result equals to whatever button I click.  */
        Optional<ButtonType> result = alert.showAndWait();

        /* If I click on the OK button, TodoData's current instance invoke the deleteTodoItem() method to delete
        * the item.  */
        if(result.isPresent() && result.get() == ButtonType.OK) {
            TodoData.getInstance().deleteTodoItem(item);
        }
    }


    @FXML
    public void handleFilterButton() {
        TodoItem selectedItem = todoListView.getSelectionModel().getSelectedItem();

        /* If filterToggleButton (ToggleButton) is toggled or selected...  */
        if(filterToggleButton.isSelected()) {
            /* If button is toggled, only keep today's items.  */
            filteredList.setPredicate(wantTodaysItems);
            /* If filteredList is empty, clear everything.  */
            if(filteredList.isEmpty()) {
                itemDetailsTextArea.clear();
                deadlineLabel.setText("");
            }
            /* Select selectedItem if filteredList contains the currently selectedItem.  */
            else if (filteredList.contains(selectedItem)) {
                todoListView.getSelectionModel().select(selectedItem);
            }
            /* If selectedItem is not in filteredList and filteredList is not empty, select first item.  */
            else {
                todoListView.getSelectionModel().selectFirst();
            }
        }
        else {
            /* Or else, keep all items.   */
            filteredList.setPredicate(wantAllItems);
            /* Select the currently selected Item.  */
            todoListView.getSelectionModel().select(selectedItem);
        }
    }


    @FXML
    public void handleExit() {
        Platform.exit();
    }

}
