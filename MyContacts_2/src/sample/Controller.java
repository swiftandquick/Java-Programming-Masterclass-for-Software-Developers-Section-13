package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import sample.datamodel.Contact;
import sample.datamodel.ContactData;

import java.io.IOException;
import java.util.Optional;

public class Controller {

    @FXML
    private BorderPane mainPanel;
    /* TableView is like ListView, it contains a list of Contact objects.  */
    @FXML
    private TableView<Contact> contactsTable;

    private ContactData data;


    public void initialize() {
        /* Create a ContactData instance.  */
        data = new ContactData();
        /* Use data to invoke the loadContacts() method.  */
        data.loadContacts();
        /* Set items of contactsTable equal to contacts from ContactData class, which is an ObservableList.  */
        contactsTable.setItems(data.getContacts());
    }


    @FXML
    public void showAddContactDialog() {
        /* Create a Dialog object.  */
        Dialog<ButtonType> dialog = new Dialog<ButtonType>();

        /* initOwner():  Specifies the owner Window for this dialog, or null for a top-level, unowned dialog.
        getScene():  Gets the Scene object, the Scene class is the container for all content in a scene graph.
        getWindow():  Gets the Window object, it’s a top level window within which a scene is hosted, and with which
        the user interacts.  */
        dialog.initOwner(mainPanel.getScene().getWindow());

        /* Set the title of the Dialog to "Add New Contact".  */
        dialog.setTitle("Add New Contact");

        FXMLLoader fxmlLoader = new FXMLLoader();

        /* Sets the location used to resolve relative path attribute values.  Set location to the URL
        “contactdialog.fxml”.  */
        fxmlLoader.setLocation(getClass().getResource("contactdialog.fxml"));
        try {
            /* Set content to contactdialog.fxml.  */
            dialog.getDialogPane().setContent(fxmlLoader.load());
        }
        /* Catch an IOException if I can't load contactdialog.fxml.  */
        catch (IOException e) {
            System.out.println("Couldn't load the dialog.  ");
            e.printStackTrace();
            return;
        }

        /* Add an OK button and a CANCEL button.  */
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        /* result is whichever button I perform action (click) on.  */
        Optional<ButtonType> result = dialog.showAndWait();

        /* If I click on the OK button...  */
        if(result.isPresent() && result.get() == ButtonType.OK) {
            /* Create a ContactController object, contactController.  Gets the controller that is associate with the
            * contactdialog.fxml file.  */
            ContactController contactController = fxmlLoader.getController();
            /* Invoke the getNewContact() method from ContactController class.  */
            Contact newContact = contactController.getNewContact();
            /* Use data (ContactData) to invoke the addContact method, pass newContact as parameter.  */
            data.addContact(newContact);
            /* Invoke the saveContacts method to save the updated contacts.  */
            data.saveContacts();
        }
    }


    @FXML
    public void showEditContactDialog() {
        /* selectedContact is the currently selected item on contactsTable.  */
        Contact selectedContact = contactsTable.getSelectionModel().getSelectedItem();
        /* If I select no items, an alert message will display, telling me that I didn't select an item.  */
        if(selectedContact == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Contact Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select the contact you want to edit.  ");
            alert.showAndWait();
            return;
        }

        /* Create an "Edit Contact" dialog.  Reuse UI from contactdialog.fxml.  */
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPanel.getScene().getWindow());
        dialog.setTitle("Edit Contact");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("contactdialog.fxml"));
        /* Try to load contactdialog.fxml, if it doesn't load, print out a message.  */
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        }
        catch(IOException e) {
            System.out.println("Couldn't load the dialog.  ");
            e.printStackTrace();
            return;
        }

        /* Add OK and CANCEL buttons to dialog.  */
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        ContactController contactController = fxmlLoader.getController();
        /* Invoke editContact() method from ContactController class.  */
        contactController.editContact(selectedContact);

        /* Result is whichever button I clicked.  */
        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            /* Invoke updateContact() method from ContactController class to update the contact.  */
            contactController.updateContact(selectedContact);
            data.saveContacts();
        }
    }


    @FXML
    public void deleteContact() {
        /* selectedContact is the currently selected item.  */
        Contact selectedContact = contactsTable.getSelectionModel().getSelectedItem();

        /* If no item is selected, display an alert message, says that no contact is selected.  */
        if(selectedContact == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Contact Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select the contact you want to delete.  ");
            alert.showAndWait();
            /* Exit out of the method.  */
            return;
        }

        /* Confirmation type alert, use to confirm if the item should be deleted.  */
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        /* An alert that asks if the user wants to delete the contact.  */
        alert.setTitle("Delete Contact");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you sure you want to delete the selected contact:  " +
                selectedContact.getFirstName() + " " + selectedContact.getLastName() + "?  ");

        /* result is whichever button I clicked on.  */
        Optional<ButtonType> result = alert.showAndWait();

        /* There are two buttons in the confirmation window, OK and CANCEL.  if I choose the OK button...  */
        if(result.isPresent() && result.get() == ButtonType.OK) {
            /* Invoke the deleteContact() method from ContactData class to remove the item from contacts.  */
            data.deleteContact(selectedContact);
            data.saveContacts();
        }
    }

}