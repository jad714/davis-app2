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
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;

import static javafx.geometry.Pos.CENTER;
import static javafx.scene.control.TableColumn.SortType.DESCENDING;

public class InventoryManagementApplicationController {
    private static final String RED = "#ff0000";
    private static final String GREEN = "#00ff08";
    ItemList itemList = new ItemList();
    FilteredList<Item> filteredItems = new FilteredList<>(itemList.getList(), p->true);
    SortedList<Item> sortedList = new SortedList<>(filteredItems);
    // The "usedNumbers" list keeps track of any serial numbers added to the list. If any of those numbers match existing ones, input will be rejected.
    HashSet<String> usedNumbers = new HashSet<>();
    private int radioSelected = 1; // The top radio button is initially selected, so this will be 1 by default.

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
        // Cannot be very easily tested, but result is easily confirmed within the program's operation.
        return (String value1, String value2) -> {
            value1 = value1.replace("$", "");
            value2 = value2.replace("$", "");
            value1 = value1.replace(",", "");
            value2 = value2.replace(",", "");
            double doubleValue1 = Double.parseDouble(value1);
            double doubleValue2 = Double.parseDouble(value2);
            return Double.compare(doubleValue1, doubleValue2);
        };
    }
/*
    public boolean isUsed(){

    }
*/
    private void displayError(int type){
        switch (type) {
            case 1 -> {
                feedbackLabel.setTextFill(Paint.valueOf(RED));
                feedbackLabel.setText("Add/Edit Error: SN is of wrong format!");
                feedbackLabel.setWrapText(true);
                feedbackLabel.setVisible(true);
            }
            case 2 -> {
                feedbackLabel.setTextFill(Paint.valueOf(RED));
                feedbackLabel.setText("Add/Edit Error: SN already exists! Use a different one.");
                feedbackLabel.setWrapText(true);
                feedbackLabel.setVisible(true);
            }
            case 3 -> {
                feedbackLabel.setTextFill(Paint.valueOf(RED));
                feedbackLabel.setText("Add/Edit Error: Name must be 2-256 characters!");
                feedbackLabel.setWrapText(true);
                feedbackLabel.setVisible(true);
            }
            case 4 -> {
                feedbackLabel.setTextFill(Paint.valueOf(RED));
                feedbackLabel.setText("Add/Edit Error: Monetary value must be numeric!");
                feedbackLabel.setWrapText(true);
                feedbackLabel.setVisible(true);
            }
            case 5 -> {
                feedbackLabel.setTextFill(Paint.valueOf(RED));
                feedbackLabel.setText("Search Error: Value not found!");
                feedbackLabel.setWrapText(true);
                feedbackLabel.setVisible(true);
            }
            case 6 -> {
                feedbackLabel.setTextFill(Paint.valueOf(RED));
                feedbackLabel.setText("Add/Edit Error: All fields must be filled!");
                feedbackLabel.setWrapText(true);
                feedbackLabel.setVisible(true);
            }
            default -> feedbackLabel.setVisible(false);
        }
    }

    private void clearMessages(){
        feedbackLabel.setVisible(false);
    }

    @FXML
    public void radioButtons(ActionEvent event){
        // Dictate the behavior of the radio buttons.
        event.consume();
        // The radio buttons will specify the search field's controlling method.
        if(searchBySerial.isSelected()){
            radioSelected = 1;
        }
        else if(searchByName.isSelected()){
            radioSelected = 0;
        }
    }

    @FXML
    public void removeSelectedPressed(ActionEvent event){
        // Handles the associated actions.
        event.consume();
        // If no item selected, ignore button press.
        if(table.selectionModelProperty().getValue().isEmpty()){
            return;
        }
        // Checks for empty list.
        if(itemList.isListEmpty()){
            return;
        }
        // Gets the selected index from the table.
        int index = table.getSelectionModel().getSelectedIndex();
        // Clears the list of the selected item, thereby removing it from the table.
        itemList.removeItem(index);
    }

    @FXML
    public void removeAllButtonPressed(ActionEvent event){
        // Handles the associated actions.
        event.consume();
        // Can't delete an empty list!
        if(itemList.isListEmpty()){
            return;
        }
        // Clears the list of ALL items, thereby emptying the TableView.
        itemList.removeAll();
    }

    @FXML
    public void saveAsButtonPressed(ActionEvent event){
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
        File file = fileChooser.showSaveDialog(saveStage);
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
        event.consume();
        if(table.selectionModelProperty().getValue().isEmpty()){
            // Display an error.
            return;
        }
        ParseTyping parseTyping = new ParseTyping();
        // Applies the edits in the text boxes to the selected item in the TableView.
        boolean isSerialFilled = serialNumber.getText()!=null;
        boolean isNameFilled = itemName.getText()!=null;
        boolean isMonetaryValueFilled = monetaryValue.getText()!=null;
        String serial = "";
        String name = "";
        String monetary = "";
        // Ignores blank inputs (to allow individual edits, and also because no field has a valid blank input).
        if(isSerialFilled && isNameFilled && isMonetaryValueFilled){
            serial = serial.concat(serialNumber.getText());
            if(!parseTyping.enforceSerialNumber(serial) || usedNumbers.contains(serial)){
                // Display Error
                if(usedNumbers.contains(serial))
                    displayError(2);
                else
                    displayError(1);
                return;
            }
            name = name.concat(itemName.getText());
            if(!parseTyping.enforceName(name)){
                // display error
                displayError(3);
                return;
            }
            monetary = monetary.concat(monetaryValue.getText());
            if(!parseTyping.enforceMonetaryValue(monetary)){
                // display error
                displayError(4);
                return;
            }
            Double doubleValue = Double.parseDouble(monetary);
            monetary = NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(doubleValue);
        }
        else{
            displayError(6);
            return;
        }

        int index = table.getSelectionModel().getSelectedIndex();
        Item replacementItem = new Item("","","");
        replacementItem.setSerialNumber(serial);
        usedNumbers.add(serial); // This serial number should not be duplicated.
        replacementItem.setName(name);
        replacementItem.setMonetaryValue(monetary);
        itemList.setItem(index, replacementItem);
        // If all fields are blank, will display an error.
    }

    @FXML
    public void searchButtonPressed(ActionEvent event){
        // Changes focus to the item matching the specified search result. If not, display error message.
        event.consume();
        int index=-9999;
        boolean found = false;
        // If top button selected, search by serial number.
        if(radioSelected == 1){
            for(int i=0;i<itemList.getSize();i++) {
                if(itemList.getItem(i).getSerialNumber().equals(searchField.getText())){
                    index = i;
                    System.out.println("one");
                    found = true;
                    break;
                }
            }
        }
        // If bottom button selected, search by name.
        else if(radioSelected == 0){
            for(int j=0;j<itemList.getSize();j++){
                if(itemList.getItem(j).getName().equals(searchField.getText())){
                    index = j;
                    System.out.println("two");
                    found = true;
                    break;
                }
            }
        }
        if(!found){
            displayError(5);
            return;
        }
        table.scrollTo(itemList.getItem(index));
        table.getSelectionModel().select(itemList.getItem(index));
        // Highlight the text in the search field.
        searchField.requestFocus();
        searchField.selectAll();
    }


    @FXML
    public void loadButtonPressed(ActionEvent event){
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
        event.consume();
        ParseTyping typeCheck = new ParseTyping();
        // If all fields are not filled, or any field does not pass tests in ParseTyping,
        if(serialNumber.getText().equals("") || itemName.getText().equals("") || monetaryValue.getText().equals("")){
            displayError(6);
            return;
        }
        String serial;
        String name;
        String value;
        serial = serialNumber.getText();
        if(!typeCheck.enforceSerialNumber(serial) || usedNumbers.contains(serial)){
            // an error will be displayed.
            if(usedNumbers.contains(serial))
                displayError(2);
            else
                displayError(1);
            return;
        }
        name = itemName.getText();
        if(!typeCheck.enforceName(name)){
            // an error will be displayed.
            displayError(3);
            return;
        }
        String preValue = monetaryValue.getText();
        if(!typeCheck.enforceMonetaryValue(preValue)){
            // an error will be displayed.
            displayError(4);
            return;
        }
        // Convert the value to the appropriate format.
        Double doubleValue = Double.parseDouble(preValue);
        value = NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(doubleValue);
        // Otherwise the value will be added to the list.
        Item newItem = new Item(serial, name, value);
        itemList.addItem(newItem);
        usedNumbers.add(serial);
        // Clear the text in the fields to provide a form of user feedback.
        serialNumber.clear();
        itemName.clear();
        monetaryValue.clear();
    }


    public void initialize(){
        // Initialize values required for the application (this will become more apparent as I work).
        // Initialize all values and controls that the UI will need to launch properly.
        serialColumn.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("monetaryValue"));
        table.setPlaceholder(new Label("No items to display...add something!"));
        table.setItems(sortedList);
        serialColumn.setSortType(DESCENDING);
        table.setFixedCellSize(Region.USE_COMPUTED_SIZE);
        sortedList.comparatorProperty().bind(table.comparatorProperty());
        Comparator<String> monetaryValueComparator = this.getComparator();
        valueColumn.setComparator(monetaryValueComparator);
        // Will definitely want to change some of the values for the TableView, including the splash message.
    }
}