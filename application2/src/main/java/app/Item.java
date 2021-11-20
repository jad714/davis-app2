/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Joshua Davis
 */

package app;

public class Item {
    private String serialNumber;
    private String name;
    private String monetaryValue;
    public Item(String serialNumber, String name, String monetaryValue){
        // Initialize the item with passed values.
        this.serialNumber = serialNumber;
        this.name = name;
        this.monetaryValue = monetaryValue;
    }

    public String getSerialNumber(){
        // Returns the requested String.
        return this.serialNumber;
    }

    public String getName(){
        // Returns the requested String.
        return this.name;
    }

    public String getMonetaryValue(){
        // Returns the requested String.
        return this.monetaryValue;
    }

    public void setSerialNumber(String newSerialNumber){
        // Changes the referenced String to passed value.
        this.serialNumber = newSerialNumber;
    }

    public void setName(String newName){
        // Changes the referenced String to passed value.
        this.name = newName;
    }

    public void setMonetaryValue(String newValue){
        // Changes the referenced String to passed value.
        this.monetaryValue = newValue;
    }
}
