/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Joshua Davis
 */

package app;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class FileIOTest {

    File testDir;

    public String readFile(File testFile){
        // Attempt to read the file, line by line.
        try{
            Scanner fileIO = new Scanner(testFile);
            String contents = "";
            while(fileIO.hasNextLine()){
                contents = fileIO.nextLine();
            }
            fileIO.close();
            return contents;
        }
        // Return "error" if unable to read. This should cause saveFileTest to fail.
        catch(FileNotFoundException e){
            System.err.println("Could not locate file!");
            return "error";
        }
    }

    @Test
    void prepHTMLTest() {
        FileIO ioTest = new FileIO();
        // Create a test HTML file based on a created list of test items using the method.
        ItemList itemListTest = new ItemList();
        Item test1 = new Item("A-000-000-000", "test", "$7.25");
        itemListTest.addItem(test1);
        String testOutput = "<table>\n\t<th>Serial Number</th><th>Name</th><th>Monetary Value</th>\n\t\t<tr>\n\t\t\t<td>A-000-000-000</td><td>test</td><td>$7.25</td>\n\t\t</tr>\n</table>";
        assertEquals(testOutput, ioTest.prepHTML(itemListTest));
        // Assert that the created item exists. If not, fail the test.
        // Read the created file and turn it back into an item.
        // Assert that the reconstructed item matches the original item.
        // Delete the created directory / file (keep the project clean).
    }

    @Test
    void readHTMLTest() {
        FileIO ioTest = new FileIO();
        ItemList testList = new ItemList();
        // Create a test HTML file.
        String testOutput = "<table>\n\t<th>Serial Number</th><th>Name</th><th>Monetary Value</th>\n\t\t<tr>\n\t\t\t<td>A-000-000-000</td><td>test</td><td>$7.25</td>\n\t\t</tr>\n</table>";
        testDir = new File("test_data/");
        testDir.mkdir();
        File testFile = new File("test_data/test_html.html");
        try{
            PrintWriter writer = new PrintWriter(testFile);
            writer.print(testOutput);
            writer.close();
        }
        catch(IOException e){
            e.printStackTrace();
            System.err.println("Test will fail due to file not found!");
            testFile.delete();
            testDir.delete();
        }
        // Create a test String that should create the same information as the HTML file.
        Item testItem = new Item("A-000-000-000", "test", "$7.25");
        ioTest.readHTML(testFile, testList);
        // Verify that the produced String matches the test item.
        assertEquals("A-000-000-000", testList.getItem(0).getSerialNumber());
        assertEquals("test", testList.getItem(0).getName());
        assertEquals("$7.25", testList.getItem(0).getMonetaryValue());
        testFile.delete();
        testDir.delete();
    }

    @Test
    void writeJsonTest() {
        // Create a test Json file based on a created list of test items using the method.
        // Assert that the created item exists. If not, fail the test.
        // Read the created file and turn it back into an item.
        // Assert that the reconstructed item matches the original item.
        // Delete the created directory / file (keep the project clean).
    }

    @Test
    void readJsonTest() {
        // Create a test Json file.
        // I try not to have tests in the same test class rely on other methods for that class. This is a long one.
        ItemList itemListTest = new ItemList();
        Item testItem = new Item("A-000-000-000", "test", "$7.25");
        itemListTest.addItem(testItem);
        Gson gson = new Gson();
        testDir = new File("test_data/");
        testDir.mkdir();
        File testFile = new File("test_data/test_json.json");
        try{
            FileWriter writer = new FileWriter(testFile);
            gson.toJson(itemListTest.getList(), writer);
            writer.flush();
            writer.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
            fail();
        }
        // Create a test item that should create the same information as the HTML file.
        try{
            Reader fileReader = Files.newBufferedReader(Paths.get("test_data/test_json.json"));
            // Create a list of objects to store json information in, scan the information using gson library.
            // The list of objects will be contained in the "inventory" instance field due to the structure of the json file.
            Item[] items = gson.fromJson(fileReader, Item[].class);
            ItemList testList2 = new ItemList();
            FileIO ioTest = new FileIO();
            ioTest.readJson(testFile, testList2);
            assertEquals(itemListTest.getItem(0).getSerialNumber(), testList2.getItem(0).getSerialNumber());
            assertEquals(itemListTest.getItem(0).getName(), testList2.getItem(0).getName());
            assertEquals(itemListTest.getItem(0).getMonetaryValue(), testList2.getItem(0).getMonetaryValue());
            fileReader.close();
            testFile.delete();
            testDir.delete();
        }
        catch(IOException ex){
            ex.printStackTrace();
            fail();
        }
        // Verify that the produced String matches the test item.
    }

    @Test
    void writeTSVTest() {
        // Create a test TSV file based on a created list of test items using the method.
        testDir = new File("test_data/");
        testDir.mkdir();
        ItemList testList = new ItemList();
        Item testItem = new Item("A-000-000-000", "test", "$7.25");
        testList.addItem(testItem);
        File testFile = new File("test_data/test_tsv.txt");
        FileIO ioTest = new FileIO();
        ioTest.writeTSV(testFile, testList);
        // Assert the file exists.
        assertTrue(testFile.exists());
        boolean isFirstRun = true;
        String skip;
        String data;
        String[] objectInfo = new String[3];
        ItemList testList2 = new ItemList();
        // Assert that the created item exists after deserializing. If not, fail the test.
        try{
            Scanner fileScanner = new Scanner(testFile);
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
                testList2.addItem(newItem);
            }
            /* Assert the item is equal to the original item */
            assertEquals(testList.getItem(0).getSerialNumber(), testList2.getItem(0).getSerialNumber());
            assertEquals(testList.getItem(0).getName(), testList2.getItem(0).getName());
            assertEquals(testList.getItem(0).getMonetaryValue(), testList2.getItem(0).getMonetaryValue());
            fileScanner.close();
        }
        catch(IOException e){
            // Test fails because no file was found.
            e.printStackTrace();
            fail();
        }

        // Delete the created directory / file (keep the project clean).
        testFile.delete();
        testDir.delete();
    }

    @Test
    void readTSVTest() {
        // Create a test TSV file.
        // Create a test item that should create the same information as the HTML file.
        // Verify that the produced String matches the test item.
    }

    @Test
    void writeFileTest(){
        FileIO fileIOTest = new FileIO();
        // Verify the existence of a file matching the name supplied.
        // Since the method is mostly self-testing this can be done according to its returned
        // boolean value, and separately by ensuring the data copied is correct.
        File testDir = new File("test_data/");
        testDir.mkdir();
        File testFile = new File("test_data/HelloWorld.txt");
        // Write the String to a file, creating a new one. Ensure method returns true with assertion.
        fileIOTest.writeFile("Hello World", testFile);
        assertTrue(testFile.exists());
        // Read the just created test file to ensure it copied the information correctly.
        String fileContent = this.readFile(testFile);
        // Use another assertion to ensure the read string and the example string are equal.
        assertEquals("Hello World", fileContent);
        // Delete these files (don't pollute the directory with unnecessary things).
        testFile.delete();
        testDir.delete();
    }
}