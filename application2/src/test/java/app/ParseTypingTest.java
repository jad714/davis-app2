/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Joshua Davis
 */

package app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParseTypingTest {

    @Test
    void enforceSerialNumberTest() {
        ParseTyping parseTest = new ParseTyping();
        // Pass a failing String into the method.
        // Assert that it returns "false".
        assertFalse(parseTest.enforceSerialNumber("1-AAA-aaa-BBB"));
        // Pass a passing String into the method.
        // Assert that it returns "true".
        assertTrue(parseTest.enforceSerialNumber("A-222-111-111"));
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
}