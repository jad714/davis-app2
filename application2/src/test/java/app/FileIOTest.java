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

    private File testDir;
    private String testHTMLOutput = "<table>\n\t<th>Serial Number</th><th>Name</th><th>Monetary Value</th>\n\t\t<tr>\n\t\t\t<td>A-000-000-000</td><td>test</td><td>$7.25</td>\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td>A-000-000-001</td><td>test2</td><td>$8.00</td>\n\t\t</tr>\n</table>";

    // This is a utility method used by one of the test methods.
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
            fail();
        }
        return "error";
    }

    @Test
    void prepHTMLTest() {
        // This test is part one of satisfying the requirement that users be able to save their list as HTML.
        FileIO ioTest = new FileIO();
        ItemList itemListTest = new ItemList();
        // Create two items to add to the list (this verifies that the method can prep a multi-item HTML file).
        Item test1 = new Item("A-000-000-000", "test", "$7.25");
        Item test2 = new Item("A-000-000-001", "test2", "$8.00");
        itemListTest.addItem(test1);
        itemListTest.addItem(test2);
        // Create a test HTML String based on a created list of test items using the method.

        assertEquals(testHTMLOutput, ioTest.prepHTML(itemListTest));
    }

    @Test
    void readHTMLTest() {
        // This test ensures the application satisfies the requirement that users be able to load saved inventory lists.
        FileIO ioTest = new FileIO();
        ItemList testList = new ItemList();
        // Create a test HTML file.
        testDir = new File("test_data/");
        testDir.mkdir();
        File testFile = new File("test_data/test_html.html");
        // Create a test file that should create the same information as the HTML file.
        try{
            PrintWriter writer = new PrintWriter(testFile);
            writer.print(testHTMLOutput);
            writer.close();
        }
        catch(IOException e){
            e.printStackTrace();
            System.err.println("Test will fail due to file not found!");
            testFile.delete();
            testDir.delete();
        }
        Item testItem = new Item("A-000-000-000", "test", "$7.25");
        Item testItem2 = new Item("A-000-000-001", "test2", "$8.00");
        // Read our created test file and puts the information in the test list.
        ioTest.readHTML(testFile, testList);
        // Verify that the produced String matches the test items.
        assertEquals("A-000-000-000", testList.getItem(0).getSerialNumber());
        assertEquals("test", testList.getItem(0).getName());
        assertEquals("$7.25", testList.getItem(0).getMonetaryValue());
        assertEquals("A-000-000-001", testList.getItem(1).getSerialNumber());
        assertEquals("test2", testList.getItem(1).getName());
        assertEquals("$8.00", testList.getItem(1).getMonetaryValue());
        // Clean up the directory by deleting the created files / directories.
        testFile.delete();
        testDir.delete();
    }

    @Test
    void writeJsonTest() {
        // This test ensures that the requirement that users be able to save their lists as .json files works.
        // Create a test Json file based on a created list of test items using the method.
        ItemList listTest = new ItemList();
        Item testItem = new Item("A-000-000-000", "test", "$7.25");
        listTest.addItem(testItem);
        Gson testGson = new Gson();
        testDir = new File("test_data/");
        testDir.mkdir();
        File testFile = new File("test_data/test_json.json");
        String filePath = "test_data/test_json.json";
        FileIO ioTest = new FileIO();
        ioTest.writeJson(testFile, listTest);
        assertTrue(testFile.exists());
        // Read the created file and turn it back into an item.
        try{
            Reader fileReader = Files.newBufferedReader(Paths.get(filePath));
            // Create a list of objects to store json information in, scan the information using gson library.
            // The list of objects will be contained in the "inventory" instance field due to the structure of the json file.
            Item[] items = testGson.fromJson(fileReader, Item[].class);
            listTest.removeAll();
            for (Item item : items) {
                listTest.addItem(item);
            }
            fileReader.close();
        }
        catch(IOException ex){
            ex.printStackTrace();
            System.err.println("Something's wrong with the test!");
            fail();
        }
        // Run assertions to ensure the simple file was read correctly.
        assertEquals(testItem.getSerialNumber(), listTest.getItem(0).getSerialNumber());
        assertEquals(testItem.getName(), listTest.getItem(0).getName());
        assertEquals(testItem.getMonetaryValue(), listTest.getItem(0).getMonetaryValue());
        // Delete the created directory / file (keep the project clean).
        testFile.delete();
        testDir.delete();
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
        testDir = new File("test_data/");
        testDir.mkdir();
        ItemList testList1 = new ItemList();
        ItemList testList2 = new ItemList();
        Item testItem = new Item("A-000-000-000", "test", "$7.25");
        testList1.addItem(testItem);
        File testFile = new File("test_data/test_tsv.txt");
        try{
            PrintWriter writer = new PrintWriter(testFile);
            writer.println("Serial Number\tName\tMonetary Value");
            for(int i=0;i<testList1.getSize();i++){
                writer.printf("%s\t%s\t%s%n", testList1.getItem(i).getSerialNumber(), testList1.getItem(i).getName(), testList1.getItem(i).getMonetaryValue());
            }
            writer.flush();
            writer.close();
        }
        catch(IOException e){
            e.printStackTrace();
            fail();
        }
        FileIO ioTest = new FileIO();
        ioTest.readTSV(testFile, testList2);
        assertEquals(testList1.getItem(0).getSerialNumber(), testList2.getItem(0).getSerialNumber());
        assertEquals(testList1.getItem(0).getName(), testList2.getItem(0).getName());
        assertEquals(testList1.getItem(0).getMonetaryValue(), testList2.getItem(0).getMonetaryValue());
        assertEquals(testList1.getSize(), testList2.getSize());
        // Create a test item that should create the same information as the HTML file.
        // Verify that the produced String matches the test item.
        testFile.delete();
        testDir.delete();
    }

    @Test
    void writeHTMLTest(){
        // This test is part two of satisfying the requirement of a user being able to save their lists to html.
        FileIO fileIOTest = new FileIO();
        // Verify the existence of a file matching the name supplied.
        // This test method does not actually have to write an HTML formatted file correctly, all it does is write for the HTML method
        // to reduce complexity.
        File testDir = new File("test_data/");
        testDir.mkdir();
        File testFile = new File("test_data/HelloWorld.txt");
        // Write the String to a file, creating a new one. Ensure method returns true with assertion.
        fileIOTest.writeHTML("Hello World", testFile);
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