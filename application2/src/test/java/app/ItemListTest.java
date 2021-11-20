/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Joshua Davis
 */

package app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemListTest {

    ItemList itemListTest = new ItemList();
    Item test1 = new Item("A-000-000-000", "test1", "$0.00");
    Item test2 = new Item("B-000-000-000", "test2", "$0.00");
    Item test3 = new Item("C-000-000-000", "test3", "$0.00");


    @Test
    void addItemTest() {
        // Assert that calling the addItem method actually adds an item to the list.
        itemListTest.addItem(test1);
        // Stress test to the requirements!
        for(int i=0;i<1024;i++){
            // Items do not need to meet uniqueness requirements for this test.
            Item newItem = new Item("A-000-000-000", "test", "7.25");
            itemListTest.addItem(newItem);
        }
        // Set the item added matches for index zero. Also make sure the list can handle 1024 additions.
        assertEquals(test1, itemListTest.getItem(0));
        assertEquals(1025, itemListTest.getSize());
    }

    @Test
    void removeItemTest() {
        // Assert that calling the removeItem method actually removes an item from the list.
        itemListTest.addItem(test1);
        itemListTest.addItem(test2);
        itemListTest.removeItem(0);
        assertEquals(1, itemListTest.getList().size());
        assertEquals(test2, itemListTest.getItem(0));
    }

    @Test
    void getSizeTest(){
        // Assert that the returned value is the same as the actual size of the list.
        itemListTest.addItem(test1);
        itemListTest.addItem(test2);
        itemListTest.addItem(test3);
        assertEquals(3, itemListTest.getSize());
    }

    @Test
    void isListEmptyTest(){
        // Assert that the method returns "true" if the list is empty, and false otherwise.
        assertTrue(itemListTest.isListEmpty());
        itemListTest.addItem(test1);
        assertFalse(itemListTest.isListEmpty());
    }

    @Test
    void removeAllTest(){
        // Add a bunch of items to the list, then verify they're all removed by the method.
        itemListTest.addItem(test1);
        itemListTest.addItem(test2);
        itemListTest.addItem(test3);
        itemListTest.removeAll();
        assertEquals(0, itemListTest.getList().size());
    }

    @Test
    void getListTest() {
        // Assert that the same list is returned when a test list is "gotten" using "getListTest()"
        itemListTest.addItem(test1);
        itemListTest.addItem(test2);
        itemListTest.addItem(test3);
        assertEquals(test2, itemListTest.getItem(1));
    }
}