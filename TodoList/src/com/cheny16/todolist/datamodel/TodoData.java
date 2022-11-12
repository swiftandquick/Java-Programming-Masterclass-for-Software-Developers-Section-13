package com.cheny16.todolist.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

public class TodoData {

    private static TodoData instance = new TodoData();
    private static String filename = "TodoListItems.txt";

    /* ObservableList:  A list that allows listeners to track changes when they occur.  */
    private ObservableList<TodoItem> todoItems;
    private DateTimeFormatter formatter;


    /* Returns the current instance of TodoData.  */
    public static TodoData getInstance() {
        return instance;
    }


    /* Private constructor.  Set the format of the date.  */
    private TodoData() {
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }


    public ObservableList<TodoItem> getTodoItems() {
        return todoItems;
    }


    /* Add item (TodoItem object) to the todoItems List.  */
    public void addTodoItem(TodoItem item) {
        todoItems.add(item);
    }


    /* Load the list of items from TodoListItems.txt.  Throws IOException in case if the file contains information
    * that cannot be read.  */
    public void loadTodoItems() throws IOException {
        /* Create an observable array list that I can use to add items later on.  */
        todoItems = FXCollections.observableArrayList();
        /* Retrieve the path to filename, in this case, filename is "TodoListItems.txt".  */
        Path path = Paths.get(filename);
        /* Create a BufferedReader and point the reader to the path of the file (filename).  */
        BufferedReader br = Files.newBufferedReader(path);

        String input;

        try {
            /* Read the next line and set input equal to next line.  If next line isn't null...  */
            while((input = br.readLine()) != null) {
                /* Split input (next line) to different pieces via tab or "\t".  */
                String[] itemPieces = input.split("\t");

                String shortDescription = itemPieces[0];
                String details = itemPieces[1];
                String dateString = itemPieces[2];

                /* Convert String to LocalDate using formatter (dd-MM-yyyy) format.  */
                LocalDate date = LocalDate.parse(dateString, formatter);

                /* Create a new TodoItem object.  */
                TodoItem todoItem = new TodoItem(shortDescription, details, date);

                /* Add the new item to the list todoItems.  */
                todoItems.add(todoItem);
            }
        }
        finally {
            /* If br is not null, close br (BufferedReader).  */
            if (br != null) {
                br.close();
            }
        }
    }


    public void storeTodoItems() throws IOException {
        /* Retrieve the path to filename, in this case, filename is "TodoListItems.txt".  */
        Path path = Paths.get(filename);

        /* Create a BufferedWriter and point the writer to the path of the file (filename).  */
        BufferedWriter bw = Files.newBufferedWriter(path);

        try {
            /* Use iterator to iterate through the todoItems list.  */
            Iterator<TodoItem> iter = todoItems.iterator();
            /* While there is a next item in todoItems, set item equals to next item.  */
            while(iter.hasNext()) {
                TodoItem item = iter.next();
                /* Write each item, tab separates shortDescription, details, and deadline.  */
                bw.write(String.format("%s\t%s\t%s",
                        item.getShortDescription(),
                        item.getDetails(),
                        item.getDeadline().format(formatter)));
                /* Add a new line.  */
                bw.newLine();
            }
        }
        finally {
            /* Close bw if bw (BufferedWriter) isn't null.  */
            if(bw != null) {
                bw.close();
            }
        }
    }


    /* Remove item from todoItems List.  */
    public void deleteTodoItem(TodoItem item) {
        todoItems.remove(item);
    }

}
