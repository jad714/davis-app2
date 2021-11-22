Inventory Management Application User Guide
===========================================

I. Introduction:
----------------

The purpose of this guide is to familiarize the user with the controls and behaviors of the Inventory Management
application. The application's features and controls will be introduced and defined by functionality. It is important
that close attention is paid to the instructions for each functionality so that maximum productivity can be extracted
from the application.

II. Adding the First Item:
--------------------------

Upon launching the application, the user will be greeted with a blank table, and the message "Nothing to display...add something!"
This cue provides the user with the understanding of what the table will display upon entering a new value. To add a value to the Inventory
List, the user should direct their attention to the three text fields above the "Add" and "Edit Selected" buttons.

These fields are, from highest to lowest:

Serial Number field - This field will allow the user to enter the serial number of the item to be added to the inventory.
This field cannot be blank, and must contain a serial number that matches the format "A-XXX-XXX-XXX", where the "A" represents
any letter, the dashes are REQUIRED to be included by the user, and the "X" characters represent any numeric or alphabetical character.
Failure to follow these guidelines will result in an error message being displayed when the "Add" button is pressed (see "Errors / Messages" in Section VIII).

Item Name field - This field will allow the user to enter the name of the item. Any character can be used in this field, however, the name
must be between 2-256 characters long to be accepted, and the field must not be blank. Failure to follow this guideline will result in an error message
being displayed when the "Add" button is pressed (See "Errors / Messages" in Section VIII).

Monetary Value field - This field will allow the user to enter the monetary value (in U.S. dollars) for each item. This field will only
accept valid numeric decimal input (no special characters or letters will be permitted). Failure to follow this guideline will result in an
error being displayed when the "Add" button is pressed (See "Errors / Messages" in Section VIII). Values entered in this field should be in dollars and cents.
If more than two decimal places are included, the value will be chopped to two decimal places (Example: If the user entered 7.111, a value of
$7.11 would be added to the list.). It is the user's responsibility to know and understand this functionality

Once each field has been filled, and the user is satisfied with the item, the "Add" button will be pressed. If no error has been generated,
the item will appear in the large table above. The user should note that a "$" sign has been added before the monetary value. This is for the user's
convenience and a normal function of the program.

III. Editing an Existing Item:
------------------------------

It is possible that upon adding an item to the list, the user realizes that the item was entered incorrectly. The user, in this case, can fill
in the fields with the correct values for the item in this case, click on the item they would like to edit, verify that item is highlighted, and
click the "Edit Selected" button. Providing no errors have been generated, the value should reflect any changes.

There are two things that should be noted when it comes to editing an item:

a) Every field must be filled, even if the user does not wish to change the information in that field. This is for data safety purposes, and
to ensure no unintended consequences arise from editing an item.

b) An existing serial number cannot be entered while editing an item, unless that serial number is the serial number of the specific item the user
is editing (for instance, if the user entered only the name wrong, and wanted to keep the serial number the same, the user would simply type the same
serial number into the "Serial Number" field). Failure to follow this guideline will result in an error being displayed when the "Edit Selected" button
is pressed (See "Errors / Messages" in Section VIII).

IV. Searching the List:
-----------------------

In the middle of the lower interface (below the table) the user can find a field beneath two radio buttons. The radio buttons allow for the user
to specify whether they would like to search the list by serial number, or to search the list by name.

The use of this feature is simple: After selecting the search criteria, the user simply types the value they are looking for into the search field,
and click the "Search" button. If a match is found, the item will be highlighted and the table feature will automatically scroll to the item. If not,
a message that no matching item was found will be displayed (See "Errors / Messages" in Section VIII). The user should note that all searches MUST
be exact matches. For user convenience, the search field is focused and the text automatically selected for ease of revision, should the user decide
they wish to search for a different match.

It should also be noted that the search will return the FIRST MATCH for the item. It is recommended that if a unique item is being sought, the user utilize
the "Search by Serial Number" function.

V. Removing Items From the List:
--------------------------------

a) Removing a Single Item: If the user wishes to remove a single item from the list, the user can simply click the item they wish to remove
and then click the "Remove Selected" button. Note that no warning or verification will be given, so the user should ensure this is what the user wants to do.

b) Removing all Items: If the user wishes to remove all items from the list, the user can click the "Remove All" Button to delete the entire list. 
WARNING: IT IS STRONGLY RECOMMENDED THE USER BACK UP THE LIST BEFORE CARRYING OUT THIS ACTION. SEE "Saving / Loading a List" FOR MORE DETAILS.

If either button is clicked with nothing selected, or an empty table, the click will be ignored.

VI. Saving / Loading a List:
---------------------------

a) Saving a List: If the user wishes for a list to be saved beyond the active running time of the program, the user must click the "Save As..." button.
This will generate a window native to the user's operating system that will allow the user to specify a file path and name. 

There are three filetypes the user can choose from:

1. Tab-Separated Value file (.txt) - This file will be formatted to be read easily by spreadsheet software (like Microsoft Excel).
WARNING: IT IS NOT RECOMMENDED THAT THE USER CREATE OR MODIFY TSV FILES OUTSIDE OF THE APPLICATION'S SAVE FUNCTIONALITY UNLESS THE USER
IS THOUROUGHLY FAMILIAR WITH BOTH THE TSV FORMAT, AND THE FULL FUNCTIONALITY OF THIS APPLICATION.

2. .json file (.json) - This file will be formatted to the .json file standard. WARNING: IT IS NOT RECOMMMENDED THAT THE USER CREATE OR MODIFY .JSON FILES
OUTSIDE OF THE APPLICATION'S SAVE FUNCTIONALITY AT ALL!

3. HTML file (.html) - This file will be formatted as an HTML table, and can be viewed by opening with a web browser. WARNING: IT IS NOT RECOMMENDED THAT THE USER
CRETAE OR MODIFY HTML FILES OUTSIDE OF THE APPLICATION'S SAVE FUNCTIONALITY AT ALL!

Upon selecting the file path, selecting the file type, and proceeding in the manner specified by the user's operating system (the user should consult their native
operating system's documentation for details), the file will be created and formatted by the application for later use.

b) Loading a List: If the user wishes for a list to be loaded that was previously created to be viewed or edited in the application, the user
must click the "Load Inventory" button. A dialog native to the user's operating system will be generated (the user should consult their native operating
system's documentation for details), and the user can select the file they wish to load. Files of the three formats listed above (TSV, .json, HTML) can be
loaded into the application, and the items viewed or edited. WARNING: DO NOT ATTEMPT TO LOAD FILES NOT CREATED BY THE APPLICATION INTO THE APPLICATION! THIS CAN
RESULT IN UNSTABLE BEHAVIOR FROM THE APPLICATION, UP TO AND INCLUDING A FATAL ERROR. IT IS RECOMMENDED THAT THE USER SAVE ALL FILES FOR THIS APPLICATION
IN A FOLDER DEDICATED TO THIS APPLICATION.

VII. Sorting the List / Table Operations:
-----------------------------------------

By default, the list appears in order of the items added. However, clicking the headers of each column "Serial Number", "Item Name", and "Monetary Value" allows for
ascending or descending sort order of each column (the direction of the carrot that appears indicates which). For the user's convenience, each column can also be resized
for viewing. It is, however, recommended that the user not over or under-size any columns.

VIII. Errors / Messages:
------------------------

In the lower left corner of the application, a text label reading "Errors / Messages:" can be found. Below this, any errors or feedback
messages generated by the application can be found. They come in a few categories:

a) Add/Edit Errors - These errors are generated when something is wrong with things typed into the text fields by the user. The messages
are self-explanatory. Consult each Sections I and II for details regarding what constitutes acceptable input.

b) Edit Error - There is only one error in this category, and it results from trying to edit an item while nothing is selected.

c) Search Error - There is only one error in this category, and it results from no value being found when searching for an item.

In general, it is recommended that should anything not perform as expected upon engaging with the application in any way, the user should
check the "Errors / Messages" corner to see if the application has provided any feedback that might shed light on the reason for this.

============ END USER GUIDE =============== 



