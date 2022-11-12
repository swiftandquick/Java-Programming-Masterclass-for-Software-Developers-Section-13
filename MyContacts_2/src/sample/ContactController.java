package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import sample.datamodel.Contact;

public class ContactController {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField notesField;


    public Contact getNewContact() {
        /* Retrieve String values from text fields and save it into local variables.  */
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String phoneNumber = phoneNumberField.getText();
        String notes = notesField.getText();

        /* Use local variables to create a new Contact object, newContact.  */
        Contact newContact = new Contact(firstName, lastName, phoneNumber, notes);
        return newContact;
    }


    public void editContact(Contact contact) {
        /* Set the text fields initially equal to the pre-edit values.  */
        firstNameField.setText(contact.getFirstName());
        lastNameField.setText(contact.getLastName());
        phoneNumberField.setText(contact.getPhoneNumber());
        notesField.setText(contact.getNotes());
    }


    public void updateContact(Contact contact) {
        /* Set the contact's attributes to the contents that I place inside the text fields.  */
        contact.setFirstName(firstNameField.getText());
        contact.setLastName(lastNameField.getText());
        contact.setPhoneNumber(phoneNumberField.getText());
        contact.setNotes(notesField.getText());
    }

}
