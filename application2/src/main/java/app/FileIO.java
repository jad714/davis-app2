/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Joshua Davis
 */

package app;

import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileIO {

    public String prepHTML(ItemList list) {
        String output = "<table>\n\t<th>Serial Number</th><th>Name</th><th>Monetary Value</th>\n\t";
        // Modifies the String to be written in HTML format.
        for(int i=0;i<list.getSize();i++){
            output = output.concat("\t<tr>\n\t\t\t<td>" + list.getItem(i).getSerialNumber() + "</td><td>" + list.getItem(i).getName() + "</td><td>" + list.getItem(i).getMonetaryValue() + "</td>\n\t\t</tr>");
        }
        output = output.concat("\n</table>");
        return output;
    }

    public void readHTML(File file, ItemList list) {
        // Handles the input for the HTML file case.
        // Open the HTML file for reading.
        List<String> serials = new ArrayList<>();
        List<String> names = new ArrayList<>();
        List<String> values = new ArrayList<>();
        List<List<String>> lists = new ArrayList<>();
        lists.add(serials);
        lists.add(names);
        lists.add(values);
        String wholeFile = "";
        try{
                Scanner htmlIO = new Scanner(file);
                while(htmlIO.hasNextLine()){
                    wholeFile = wholeFile.concat(htmlIO.nextLine());
                }
                htmlIO.close();
        }
        catch(FileNotFoundException e){
            System.err.println("Fatal Error! FileChooser found the file, but application couldn't load it!");
            e.printStackTrace();
            System.exit(0);
        }
        // Find the data being looked for in the passed String, and toss the rest.
        Pattern cellPattern = Pattern.compile("<td>(.*?)</td>");
        Matcher patternMatcher = cellPattern.matcher(wholeFile);
        int tracker = 0;
        int numItems = 0;
        while(patternMatcher.find()){
            lists.get(tracker).add(patternMatcher.group(1));
            tracker++;
            if(tracker==3){
                tracker = 0;
                numItems++;
            }
        }
        // Create the items.
        for(int i=0;i<numItems;i++)
        {
            Item newItem = new Item(serials.get(i), names.get(i), values.get(i));
            // Change the passed list so it reflects the information loaded.
            list.addItem(newItem);
        }
    }

    public void writeJson(File file, ItemList list) {
        // Open and write the Json file using Gson
        Gson gson = new Gson();
        try{
            FileWriter writer = new FileWriter(file);
            gson.toJson(list.getList(), writer);
            writer.flush();
            writer.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
            System.err.println("Problem with the FileChooser!");
            System.exit(0);
        }
    }

    public void readJson(File file, ItemList list) {
        // Instantiate new Gson object.
        Gson gson = new Gson();
        // Instantiate new buffered reader (for file IO).
        // Try to read the file.  Catch the IO exception.
        String filePath = file.getPath();
        try{
            Reader fileReader = Files.newBufferedReader(Paths.get(filePath));
            // Create a list of objects to store json information in, scan the information using gson library.
            // The list of objects will be contained in the "inventory" instance field due to the structure of the json file.
            Item[] items = gson.fromJson(fileReader, Item[].class);
            list.removeAll();
            for (Item item : items) {
                list.addItem(item);
            }
            fileReader.close();
        }
        catch(IOException ex){
            ex.printStackTrace();
            System.err.println("There is something wrong with the FileChooser!");
            System.exit(0);
        }
    }

    public void writeTSV(File file, ItemList list) {
        // Writes a TSV file to save the Application's information.
        // Open (create) the TSV file.
        try{
            PrintWriter writer = new PrintWriter(file);
            writer.printf("Serial Number\tName\tMonetary Value%n");
            for(int i=0;i<list.getSize();i++){
                String serial = list.getItem(i).getSerialNumber();
                String name = list.getItem(i).getName();
                String monetary = list.getItem(i).getMonetaryValue();
                // Modify the output so that it fits the format requirements for a TSV.
                writer.printf("%s\t%s\t%s%n", serial, name, monetary);
            }
            writer.close();
        }
        catch(IOException e){
            System.err.println("The FileChooser has failed! This isn't supposed to happen. Abandon ship!");
            e.printStackTrace();
            System.exit(0);
        }
        // Close the output file.
    }

    public void readTSV(File file, ItemList list){
        // Converts the data in the TSV file to one useable by the program.
        boolean isFirstRun = true;
        String skip;
        String data;
        String[] objectInfo = new String[3];
        try{
            Scanner fileScanner = new Scanner(file);
            // Skip the first line of information (Table Headers)
            while(fileScanner.hasNextLine()){
                if(isFirstRun){
                    skip = fileScanner.nextLine();
                    isFirstRun = false;
                }
                // Removes the tabs from the input and returns the information for further processing.
                data = fileScanner.nextLine();
                objectInfo = data.split("\\t");
                objectInfo[2] = objectInfo[2].replace("\n", "");
                Item newItem = new Item(objectInfo[0], objectInfo[1], objectInfo[2]);
                list.addItem(newItem);
                fileScanner.close();
            }
        }
        catch(IOException e){
            System.err.println("Something's wrong with the FileChooser!");
            e.printStackTrace();
            System.exit(0);
        }

    }

    public void writeFile(String output, File file){
        try{
            // Create a new instance of the PrintWriter Class to write the output file.
            PrintWriter fileWriter = new PrintWriter(file);
            // Print the content of the to-do list to said file.
            fileWriter.println(output);
            // Close the PrintWriter.
            fileWriter.close();
        }
        catch(IOException e)
        {
            // Catch the IOException (if anything went wrong with the FileChooser).
            System.err.println("FATAL ERROR: Unable to write file.");
            System.exit(0);
        }
    }
}