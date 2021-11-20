/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Joshua Davis
 */

package app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    Item testItem = new Item("A-000-000-000", "test", "$0.00");

    @Test
    void getSerialNumberTest() {
        // Test the correct result is returned (simple getter).
        assertEquals("A-000-000-000", testItem.getSerialNumber());
    }

    @Test
    void getNameTest() {
        // Test the correct result is returned (simple getter).
        assertEquals("test", testItem.getName());
    }

    @Test
    void getMonetaryValueTest() {
        // Test the correct result is returned (simple getter).
        assertEquals("$0.00", testItem.getMonetaryValue());
    }

    @Test
    void setSerialNumberTest() {
        // Test the correct change has been made (simple setter).
        testItem.setSerialNumber("B-000-000-000");
        assertEquals("B-000-000-000", testItem.getSerialNumber());
    }

    @Test
    void setNameTest() {
        // Test the correct change has been made (simple setter).
        testItem.setName("Dr. Hollander");
        assertEquals("Dr. Hollander", testItem.getName());
    }

    @Test
    void setMonetaryValueTest() {
        // Test the correct change has been made (simple setter).
        testItem.setMonetaryValue("$1000000.00");
        assertEquals("$1000000.00", testItem.getMonetaryValue());
    }
}