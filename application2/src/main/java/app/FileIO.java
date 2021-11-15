/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Joshua Davis
 */

package app;

public class FileIO {

    public void writeHTML(String output) {
        // Handles the output for the HTML file.
        // Open (create) the HTML file.
        // Write the HTML file according to the required format.
        // Close the writer.
    }

    public String readHTML() {
        // Handles the input for the HTML file case.
        // Open the HTML file for reading.
        // Return the whole file as a string to be parsed elsewhere.
        return "";
    }

    public void writeJson(String output) {
        // Open and write the Json file using Gson
    }

    public Item readJson() {
        Item item;
        // Handles the output for the Json file.
        // Open the Json file for reading.
        // Use a Gson parser to parse the file.
        // return the Item according to the parsed data.
        return item;
    }

    public void writeTSV(String output) {
        // Writes a TSV file to save the Application's information.
        // Open (create) the TSV file.
        // Modify the output so that it fits the format requirements for a TSV.
        // Close the output file.
    }

    public String readTSV(){
        // Converts the data in the TSV file to one useable by the program.
        // Removes the tabs from the input and returns the information for further processing.
        return "";
    }
}