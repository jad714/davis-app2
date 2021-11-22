/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Joshua Davis
 */

package app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParseTypingTest {

    @Test
    void isUsedTest(){
        // Will test the method's ability to return true if HashSet contains value, and false otherwise.
        ParseTyping parseTest = new ParseTyping();
        // Assert that the method returns "false" if the value is not within the HashSet.
        assertFalse(parseTest.isUsed("test"));
        // Add the value to the HashSet.
        parseTest.usedNumbers.add("test");
        // Assert that the value returns "true" once the value is contained within the HashSet.
        assertTrue(parseTest.isUsed("test"));
    }

    @Test
    void removeForEditTest(){
        // Will test the method's ability to remove a value for the calling method if it already appears within the HashSet.
        ParseTyping parseTest = new ParseTyping();
        ItemList itemListTest = new ItemList();
        // Add a test item to a new list, and the used numbers HashSet.
        itemListTest.addItem(new Item("A-000-000-000", "test", "$7.25"));
        parseTest.usedNumbers.add("A-000-000-000");
        // The test index will be 0 (there is only one item).
        int testIndex = 0;
        // Remove the value in "serial" from the HashSet if it is the selected item and the user is replacing it with the same value.
        parseTest.removeForEdit("A-000-000-000", testIndex, itemListTest);
        // Assert that the method successfully removed the item to be removed.
        assertEquals(0, parseTest.usedNumbers.size());
    }

    @Test
    void enforceSerialNumberTest() {
        ParseTyping parseTest = new ParseTyping();
        // Pass a failing String into the method.
        // Assert that it returns "false".
        assertFalse(parseTest.enforceSerialNumber("1-AAA-aaa-BBB"));
        // Pass a passing String into the method.
        // Assert that it returns "true".
        assertTrue(parseTest.enforceSerialNumber("A-2c2-111-1as"));
    }

    @Test
    void enforceNameTest() {
        ParseTyping parseTest = new ParseTyping();
        // Pass a failing String into the method.
        // Assert that it returns "false".
        assertFalse(parseTest.enforceName("a"));
        String testString = "";
        for(int i=0;i<258;i++){
            testString = testString.concat("a");
        }
        assertFalse(parseTest.enforceName(testString));
        // Pass a passing String into the method.
        // Assert that it returns "true".
        assertTrue(parseTest.enforceName("Dr. Hollander"));
    }

    @Test
    void enforceMonetaryValueTest() {
        ParseTyping parseTest = new ParseTyping();
        // Pass a failing String into the method.
        // Assert that it returns "false".
        assertFalse(parseTest.enforceMonetaryValue("abc"));
        // Pass a passing String into the method.
        // Assert that it returns "true".
        assertTrue(parseTest.enforceMonetaryValue("7.25"));
    }

    @Test
    void convertValueTest(){
        ParseTyping parseTest = new ParseTyping();
        // Pass in a value to convert.
        // Assert that the converted value matches the expected monetary format.
        assertEquals("$7.25", parseTest.convertValue("7.25"));
        // Pass in a value that is of correct format for the method, but not the final String.
        // Assert the chop was carried out successfully.
        assertEquals("$7.25", parseTest.convertValue("7.2568"));
    }
}