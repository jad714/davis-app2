/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Joshua Davis
 */

package app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class ItemList {
    private ObservableList<Item> mainList = FXCollections.observableArrayList();
    private int size;
    public ItemList(){
        // Initialize the size to zero.
        this.size = 0;
    }

    public void addItem(Item item){
        // Adds an item to the end of the list.
    }

    public void removeItem(int index){
        // Removes the item at the specified index.
    }

    public ObservableList<Item> getList(){
        // Returns the list.
        return mainList;
    }
}
