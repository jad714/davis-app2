/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Joshua Davis
 */

package app;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.*;

import static javafx.scene.control.TableColumn.SortType.DESCENDING;

public class InventoryManagementApplicationController {
    private static final String RED = "#ff0000";
    private ItemList itemList = new ItemList();
    private FilteredList<Item> filteredItems = new FilteredList<>(itemList.getList(), p->true);
    private SortedList<Item> sortedList = new SortedList<>(filteredItems);
    // The "usedNumbers" list keeps track of any serial numbers added to the list. If any of those numbers match existing ones, input will be rejected.
    private int radioSelected = 1; // The top radio button is initially selected, so this will be 1 by default.
    private final ParseTyping typeCheck = new ParseTyping();

    @FXML
    private RadioButton searchBySerial;
    @FXML
    private RadioButton searchByName;
    @FXML
    private TextField serialNumber = new TextField();
    @FXML
    private TextField itemName = new TextField();
    @FXML
    private TextField monetaryValue = new TextField();
    @FXML
    private TextField searchField = new TextField();
    @FXML
    private TableView<Item> table = new TableView<>();
    @FXML
    private Label feedbackLabel;
    @FXML
    private TableColumn<Item, String> serialColumn;
    @FXML
    private TableColumn<Item, String> nameColumn;
    @FXML
    private TableColumn<Item, String> valueColumn;


    /* Initialize all buttons and controls for the Application and define associated behaviors, as well as sorting behaviors. */
    private Comparator<String> getComparator(){
        // This method returns the comparator that allows for the sorting of monetary values! (Proud of this one).
        // The method cannot be tested effectively, because the only way to test it would be to completely recreate it and
        // assert the two comparators equal. As such, there would be no value in running a test on this.
        return (String value1, String value2) -> {
            // Replace all the non-numeric characters for comparison.
            value1 = value1.replace("$", "");
            value2 = value2.replace("$", "");
            value1 = value1.replace(",", "");
            value2 = value2.replace(",", "");
            // Get the double value of the Strings.
            double doubleValue1 = Double.parseDouble(value1);
            double doubleValue2 = Double.parseDouble(value2);
            // Compare the strings as double values to determine their order on the list.
            return Double.compare(doubleValue1, doubleValue2);
        };
    }

    private void displayError(int type){
        // Will implement a switch statement to decide which error to display.
        switch (type) {
            // The first case handles incorrect Serial Number format.
            case 1 -> {
                feedbackLabel.setTextFill(Paint.valueOf(RED));
                feedbackLabel.setText("Add/Edit Error: SN is of wrong format! See readme.txt for details.");
                feedbackLabel.setWrapText(true);
                feedbackLabel.setVisible(true);
            }
            // The second case handles the case where a serial number the user is attempting to enter already exists.
            case 2 -> {
                feedbackLabel.setTextFill(Paint.valueOf(RED));
                feedbackLabel.setText("Add/Edit Error: SN already exists! Use a different one.");
                feedbackLabel.setWrapText(true);
                feedbackLabel.setVisible(true);
            }
            // The third case handles the case where a user enters too little or too many characters.
            case 3 -> {
                feedbackLabel.setTextFill(Paint.valueOf(RED));
                feedbackLabel.setText("Add/Edit Error: Name must be 2-256 characters!");
                feedbackLabel.setWrapText(true);
                feedbackLabel.setVisible(true);
            }
            // The fourth case handles the case where a user enters an incorrect monetary value (non-numeric characters are entered besides a decimal).
            case 4 -> {
                feedbackLabel.setTextFill(Paint.valueOf(RED));
                feedbackLabel.setText("Add/Edit Error: Monetary value must be positive numeric!");
                feedbackLabel.setWrapText(true);
                feedbackLabel.setVisible(true);
            }
            // The fifth case handles the case where a search result could not be found.
            case 5 -> {
                feedbackLabel.setTextFill(Paint.valueOf(RED));
                feedbackLabel.setText("Search Error: Value not found!");
                feedbackLabel.setWrapText(true);
                feedbackLabel.setVisible(true);
            }
            // The sixth case handles the case where one or more fields is not filled in an add / edit case.
            case 6 -> {
                feedbackLabel.setTextFill(Paint.valueOf(RED));
                feedbackLabel.setText("Add/Edit Error: All fields must be filled!");
                feedbackLabel.setWrapText(true);
                feedbackLabel.setVisible(true);
            }
            // The seventh case handles the case where the user is trying to edit a list that is empty.
            case 7 -> {
                feedbackLabel.setTextFill(Paint.valueOf(RED));
                feedbackLabel.setText("Edit Error: Nothing selected! Please click on an item in the table!");
                feedbackLabel.setWrapText(true);
                feedbackLabel.setVisible(true);
            }
            // The eighth case handles the case where the user attempts to save an empty list.
            case 8 -> {
                feedbackLabel.setTextFill(Paint.valueOf(RED));
                feedbackLabel.setText("Save/Load Error: Cannot save an empty list!");
                feedbackLabel.setWrapText(true);
                feedbackLabel.setVisible(true);
            }
            // This should never trigger, but in the event it does, it hides the error message label.
            default -> feedbackLabel.setVisible(false);
        }
    }

    private void clearMessages(){
        // Hides the error message label. Will be called multiple times to keep the corner clear after a new event is triggered.
        feedbackLabel.setVisible(false);
    }

    @FXML
    public void radioButtons(ActionEvent event){
        // Dictate the behavior of the radio buttons.
        event.consume();
        // The radio buttons will specify the search field's controlling method.
        if(searchBySerial.isSelected()){
            // Returns 1 to the calling method to signify the user would like to search by Serial Number.
            radioSelected = 1;
        }
        else if(searchByName.isSelected()){
            // Returns 0 to the calling method to signify the user would like to search by the name of the item.
            radioSelected = 0;
        }
    }

    @FXML
    public void removeSelectedPressed(ActionEvent event){
        // Handles the associated actions.
        this.clearMessages();
        event.consume();
        // If no item selected, ignore button press.
        if(table.selectionModelProperty().getValue().isEmpty()){
            return;
        }
        // Checks for empty list.
        if(itemList.isListEmpty()){
            // ignore the button press if list empty.
            return;
        }
        // Gets the selected index from the table.
        int index = table.getSelectionModel().getSelectedIndex();
        // Removes the serial number from the blacklist.
        typeCheck.usedNumbers.remove(itemList.getItem(index).getSerialNumber());
        // Deletes the selected item, thereby removing it from the table.
        itemList.removeItem(index);
    }


    @FXML
    public void removeAllButtonPressed(ActionEvent event){
        this.clearMessages();
        // Handles the associated actions.
        event.consume();
        // Can't delete an empty list!
        if(itemList.isListEmpty()){
            return;
        }
        // Empties the blacklist of all serial numbers.
        typeCheck.usedNumbers.clear();
        // Clears the list of ALL items, thereby emptying the TableView.
        itemList.removeAll();
    }

    @FXML
    public void saveAsButtonPressed(ActionEvent event){
        this.clearMessages();
        event.consume();
        if(itemList.isListEmpty()){
            // Shows an error.
            return;
        }
        FileIO save = new FileIO();
        // Calls up the appropriate save method according to the specified file type specified by the user
        Stage saveStage = new Stage();
        // Set up the file chooser and any and all extension filters.
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter txtFilter = new FileChooser.ExtensionFilter("(TSV File)", "*.txt");
        FileChooser.ExtensionFilter jsonFilter = new FileChooser.ExtensionFilter("(.json File)", "*.json");
        FileChooser.ExtensionFilter htmlFilter = new FileChooser.ExtensionFilter("(.html File)", "*.html");
        fileChooser.getExtensionFilters().add(txtFilter);
        fileChooser.getExtensionFilters().add(jsonFilter);
        fileChooser.getExtensionFilters().add(htmlFilter);
        // Stage the save dialog.
        File file = fileChooser.showSaveDialog(saveStage);
        // If the file isn't null (it shouldn't be if FileChooser works correctly), run the rest of the method.
        if(file!=null){
            if(file.getPath().contains(".html")){
                // If an html file is selected, call this method.
                String content = save.prepHTML(itemList);
                save.writeHTML(content, file);
            }
            else if(file.getPath().contains(".json")){
                // If a .json file is selected, call this method.
                save.writeJson(file, itemList);
            }
            else{
                // Process of elimination says TSV.
                save.writeTSV(file, itemList);
            }
        }
    }

    @FXML
    public void editSelectedButtonPressed(ActionEvent event){
        // Clear all messages from the message corner.
        this.clearMessages();
        event.consume();
        if(table.selectionModelProperty().getValue().isEmpty()){
            // Display the error associated with attempting to edit an empty list.
            this.displayError(9);
            return;
        }
        // Applies the edits in the text boxes to the selected item in the TableView.
        // First, initialize blank Strings for storing the input data, and boolean values to check for empty fields.
        boolean isSerialFilled = !serialNumber.getText().equals("");
        boolean isNameFilled = !itemName.getText().equals("");
        boolean isMonetaryValueFilled = !monetaryValue.getText().equals("");
        String serial = "";
        String name = "";
        String monetary = "";
        /* Blank inputs will not be allowed (for simplicity of design, and because it isn't a requirement). */
        // Retrieve input from the table using the selection model.
        int index = table.getSelectionModel().getSelectedIndex();
        if(isSerialFilled && isNameFilled && isMonetaryValueFilled){
            // Get the new serial number from the field, but check it first.
            serial = serial.concat(serialNumber.getText());
            if(!typeCheck.enforceSerialNumber(serial) || typeCheck.isUsed(serial)){
                // This method will do nothing if the removal is unnecessary.
                typeCheck.removeForEdit(serial, index, itemList);
                if(typeCheck.isUsed(serial)){
                    // Displays the error associated with a used serial number.
                    displayError(2);
                    return;
                }
                else if(!typeCheck.enforceSerialNumber(serial)){
                    // Displays the error associated with an invalid serial number format.
                    displayError(1);
                    return;
                }
            }
            // Get the new name from the field, but check it before adding.
            name = name.concat(itemName.getText());
            if(!typeCheck.enforceName(name)){
                // Display the error associated with a bad name.
                displayError(3);
                return;
            }
            // Get the new monetary value from the field, but check it for validity before applying it.
            monetary = monetary.concat(monetaryValue.getText());
            if(!typeCheck.enforceMonetaryValue(monetary)){
                // Displays the error associated with a bad monetary value.
                displayError(4);
                return;
            }
            monetary = typeCheck.convertValue(monetary);
        }
        else{
            // Displays the error associated with empty fields.
            displayError(6);
            return;
        }
        // Create the item to replace the item being edited.
        Item replacementItem = new Item("","","");
        replacementItem.setSerialNumber(serial);
        typeCheck.usedNumbers.add(serial); // This serial number should not be duplicated.
        replacementItem.setName(name);
        replacementItem.setMonetaryValue(monetary);
        itemList.setItem(index, replacementItem);
    }

    @FXML
    public void searchButtonPressed(ActionEvent event){
        // Changes focus to the item matching the specified search result. If not, display error message.
        this.clearMessages();
        event.consume();
        // Initialize index to an invalid index, so that we know for sure if no item is found.
        int index = -9999;
        // Initialize a boolean value to determine whether or not a value has been found.
        boolean found = false;
        // If top button selected, search by serial number. NOTE: this is governed by the get methods of ItemList and Item, which are tested.
        if(radioSelected == 1){
            for(int i=0;i<itemList.getSize();i++) {
                if(itemList.getItem(i).getSerialNumber().equals(searchField.getText())){
                    index = i;
                    found = true;
                    break;
                }
            }
        }
        // If bottom button selected, search by name. NOTE: This is governed by the get methods of ItemList and Item, which are tested.
        else if(radioSelected == 0){
            for(int j=0;j<itemList.getSize();j++){
                if(itemList.getItem(j).getName().equals(searchField.getText())){
                    index = j;
                    found = true;
                    break;
                }
            }
        }
        if(!found){
            // If "found" is still false by this point, display the error associated with no matched values.
            displayError(5);
            return;
        }
        // Scroll to and select the matching item in the tableview.
        table.scrollTo(itemList.getItem(index));
        table.getSelectionModel().select(itemList.getItem(index));
        // Highlight the text in the search field.
        searchField.requestFocus();
        searchField.selectAll();
    }


    @FXML
    public void loadButtonPressed(ActionEvent event){
        // Clear old messages from the message corner.
        this.clearMessages();
        event.consume();
        // This method will allow the load button to function.
        // Will launch a file chooser to accomplish this.
        FileIO load = new FileIO();
        // Create the stage for the FileChooser.
        Stage loadStage = new Stage();
        // Set up the extension filters.
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter txtFilter = new FileChooser.ExtensionFilter("(TSV File)", "*.txt");
        FileChooser.ExtensionFilter jsonFilter = new FileChooser.ExtensionFilter("(.json File)", "*.json");
        FileChooser.ExtensionFilter htmlFilter = new FileChooser.ExtensionFilter("(.html File)", "*.html");
        fileChooser.getExtensionFilters().add(txtFilter);
        fileChooser.getExtensionFilters().add(jsonFilter);
        fileChooser.getExtensionFilters().add(htmlFilter);
        File file = fileChooser.showOpenDialog(loadStage);
        // If the file is not null, load the file.
        if(file!=null){
            itemList.removeAll();
            if(file.getPath().contains(".html")){
                // If an html file is selected, call this method.
                load.readHTML(file, itemList);
            }
            else if(file.getPath().contains(".json")){
                // If a .json file is selected, call this method.
                load.readJson(file, itemList);
            }
            else{
                // Process of elimination says TSV.
                load.readTSV(file, itemList);
            }
        }
    }

    @FXML
    public void addButtonPressed(ActionEvent event){
        // Adds fields entered as a new item to the list, and updates the TableView.
        // Start by clearing old messages from the message corner.
        this.clearMessages();
        event.consume();
        // Instantiate ParseTyping instance for checking each input for validity.
        // If all fields are not filled, or any field does not pass tests in ParseTyping,
        if(serialNumber.getText().equals("") || itemName.getText().equals("") || monetaryValue.getText().equals("")){
            displayError(6);
            return;
        }
        String serial;
        String name;
        String value;
        // Check the serial number.
        serial = serialNumber.getText();
        if(!typeCheck.enforceSerialNumber(serial) || typeCheck.isUsed(serial)){
            if(typeCheck.isUsed(serial))
                // Displays the error associated with a non-unique serial number attempt.
                displayError(2);
            else
                // Displays the error associated with bad serial numbers.
                displayError(1);
            return;
        }
        // Check the name.
        name = itemName.getText();
        if(!typeCheck.enforceName(name)){
            // Displays the error associated with bad name in the message corner.
            displayError(3);
            return;
        }
        // Check the monetary value.
        String preValue = monetaryValue.getText();
        if(!typeCheck.enforceMonetaryValue(preValue)){
            // Displays the error associated with bad value in the message corner.
            displayError(4);
            return;
        }
        // Convert the value to the appropriate format (for display only, the input validity is checked in ParseTyping).
        value = typeCheck.convertValue(preValue);
        // Add the value to the list.
        Item newItem = new Item(serial, name, value);
        itemList.addItem(newItem);
        // Add the serial number to the blacklist of serial numbers.
        typeCheck.usedNumbers.add(serial);
        // Clear the text in the fields to provide a form of user feedback.
        serialNumber.clear();
        itemName.clear();
        monetaryValue.clear();
    }


    public void initialize(){
        // Initialize values required for the application (this will become more apparent as I work).
        // Initialize all values and controls that the UI will need to launch properly.
        // Initialize cell properties.
        serialColumn.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("monetaryValue"));
        // Initialize the placeholder, and seat the Observable List within the table.
        table.setPlaceholder(new Label("No items to display...add something!"));
        table.setItems(sortedList);
        // Set the default sort type for new additions to the list.
        serialColumn.setSortType(DESCENDING);
        // Set the size of the cell to be computed by default.
        table.setFixedCellSize(Region.USE_COMPUTED_SIZE);
        // Every column will use its default comparator for sorting EXCEPT the monetary value column which uses
        // a custom comparator.
        sortedList.comparatorProperty().bind(table.comparatorProperty());
        Comparator<String> monetaryValueComparator = this.getComparator();
        valueColumn.setComparator(monetaryValueComparator);
    }
}