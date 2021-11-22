/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Joshua Davis
 */

package app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class ItemList {
    private ObservableList<Item> mainList = FXCollections.observableArrayList();
    private int size;
    public ItemList(){
        // Initialize the size to zero.
        this.size = 0;
    }

    public void addItem(Item item){
        // Adds an item to the end of the list.
        mainList.add(item);
        this.size++;
    }

    public void removeItem(int index){
        // Removes the item at the specified index.
        mainList.remove(index);
        this.size--;
    }

    public Item getItem(int index){
        // Returns the item at a given index.
        return mainList.get(index);
    }

    public void setItem(int index, Item item){
        // Sets the item to the item created.
        mainList.set(index, item);
    }

    public int getSize(){
        // Return the size of the list.
        return this.size;
    }

    public boolean isListEmpty(){
        return (this.size==0);
    }

    public void removeAll(){
        // Empties the entire list.
        mainList.clear();
        this.size=0;
    }

    public ObservableList<Item> getList(){
        // Returns the list.
        return mainList;
    }
}
