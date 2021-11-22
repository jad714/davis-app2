/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Joshua Davis
 */


package app;

import java.text.NumberFormat;
import java.util.HashSet;
import java.util.Locale;

public class ParseTyping {
    HashSet<String> usedNumbers = new HashSet<>();

    public boolean isUsed(String serial){
        // Returns a simple boolean value to signify used or not used values.
        return usedNumbers.contains(serial);
    }

    public void removeForEdit(String serial, int index, ItemList itemList){
        // Removes an item from the usedNumbers list for the sake of edits that replace the same value.
        // This method mostly exists to reduce the cognitive complexity of editSelected for SonarLint's sake.
        if(serial.equals(itemList.getList().get(index).getSerialNumber())){
            usedNumbers.remove(serial);
        }
    }

    public boolean enforceSerialNumber(String serialNumber){
        // Other methods will call this to verify input can be accepted.
        // Method will enforce the format requirements of "A-XXX-XXX-XXX" (X's can be any letter or digit).
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
            if(Double.parseDouble(monetaryValue)>=0){
                return true;
            }
        }
        catch(NumberFormatException e){
            return false;
        }
        return false;
    }

    public String convertValue(String preValue){
        // Convert the value to a double (it is already cleared for NumberFormatException, so that error will not be possible).
        Double doubleValue = Double.parseDouble(preValue);
        doubleValue = Math.floor(doubleValue*100)/100;
        // Convert it to a currency instance for display in the table.
        return NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(doubleValue);
    }
}
