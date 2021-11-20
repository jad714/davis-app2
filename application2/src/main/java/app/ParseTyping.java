/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Joshua Davis
 */


package app;

public class ParseTyping {

    public boolean enforceSerialNumber(String serialNumber){
        // Other methods will call this to verify input can be accepted.
        // Method will enforce the format requirements of "A-XXX-XXX-XXX" (X's can be any letter or digit).
        // Repeat serial numbers are prevented by the calling method.
        return serialNumber.matches("[a-zA-Z]-[a-zA-Z_0-9]{3}-[a-zA-Z_0-9]{3}-[a-zA-Z_0-9]{3}");
    }

    public boolean enforceName(String name){
        // Other methods will call this to verify input can be accepted.
        // Method wil enforce the "2-256" character requirement for an item's name.
        return name.length()>=2 && name.length()<=256;
    }

    public boolean enforceMonetaryValue(String monetaryValue){
        // Other methods will call this to verify input can be accepted.
        // Method will enforce the currency input can be parsed as double using a try/catch.
        // If the try/catch passes, it will round to two decimal places and convert the number to a String.
        // If the try/catch fails, will return "false," disallowing the input.
        try{
            Double.parseDouble(monetaryValue);
            return true;
        }
        catch(NumberFormatException e){
            return false;
        }
    }
}
