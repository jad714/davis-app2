/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Joshua Davis
 */


package app;

public class ParseTyping {

    public boolean enforceSerialNumber(String serialNumber){
        // Other methods will call this to verify input can be accepted.
        // Method will enforce the format requirements of "A-XXX-XXX-XXX".
        // Also disallows repeat serial numbers.
    }

    public boolean enforceName(String name){
        // Other methods will call this to verify input can be accepted.
        // Method wil enforce the "2-256" character requirement for an item's name.
    }

    public boolean enforceMonetaryValue(String monetaryValue){
        // Other methods will call this to verify input can be accepted.
        // Method will enforce the currency input can be parsed as double using a try/catch.
        // If the try/catch passes, it will round to two decimal places and convert the number to a String.
        // If the try/catch fails, will return "false," disallowing the input.
    }

    public boolean searchSerial(String search){
        // Handles the case (using string comparison) where the user presses "search"
        // with input text in the search field, and presses "search". Returns false if no match.
    }

    public boolean searchName(String search){
        // Handles the case (using string comparison) where the user presses "search"
        // with input text in the search field, and presses "search". Returns false if no match found.
    }
}
