/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Joshua Davis
 */

package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class InventoryManagementApplicationController {
    /* Initialize all buttons and controls for the Application and define associated behaviors, as well as sorting behaviors. */

    @FXML
    public void radioButtons(ActionEvent event){
        // Dictate the behavior of the radio buttons.
        // The radio buttons will specify the search field's controlling method.
    }

    @FXML
    public void removeSelectedPressed(ActionEvent event){
        // Handles the associated actions.
        // Clears the list of the selected item, thereby removing it from the table.
    }

    @FXML
    public void removeAllButtonPressed(ActionEvent event){
        // Handles the associated actions.
        // Clears the list of ALL items, thereby emptying the TableView.
    }

    @FXML
    public void saveAsButtonPressed(ActionEvent event){
        // Calls up the appropriate save method according to the specified file type specified by the user
        // in the FileChooser (which is also created by this method).
    }

    @FXML
    public void editSelectedButtonPressed(ActionEvent event){
        // Applies the edits in the text boxes to the selected item in the TableView.
        // Ignores blank inputs (to allow individual edits, and also because no field has a valid blank input).
        // If all fields are blank, will display an error.
    }

    @FXML
    public void searchButtonPressed(ActionEvent event){
        // Changes focus to the item matching the specified search result. If not, display error message.
    }


    @FXML
    public void loadButtonPressed(ActionEvent event){
        // This method will allow the load button to function.
        // Will launch a file chooser to accomplish this.
    }

    @FXML
    public void addButtonPressed(ActionEvent event){
        // Adds fields entered as a new item to the list, and updates the TableView.
        // If all fields are not filled, or any field does not pass tests in ParseTyping,
        // an error will be displayed.
    }


    public void initialize(){
        // Initialize values required for the application (this will become more apparent as I work).
        // Will definitely want to change some of the values for the TableView, including the splash message.
    }
}