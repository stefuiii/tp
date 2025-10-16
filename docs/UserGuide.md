---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# FastCard User Guide

FastCard is a **desktop app for managing contacts, optimized for use via a  Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). 
If you can type fast, FastCard can get your contact management tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from [here](https://github.com/AY2526S1-CS2103T-F11-4/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for FastCard.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar fastcard.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to FastCard.

   * `delete 83556666` : Deletes the contact with the phone number `83556666` from the current list.
   * 
   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### **Adding a person (basic information only)** : `addbasic`

Adds a person with only basic information (name and phone number) to the address book.

**Format:**

```
addbasic n/NAME p/PHONE
```

* Adds a person with the specified name and phone number.
* The `NAME` must only contain alphanumeric characters and spaces.
* The `PHONE` must be a valid 3–digit or longer number.
* Duplicate persons (same phone number) will not be added.

    * Duplicate detection is **case-insensitive** and **whitespace-insensitive** (e.g. `John Doe` and `  john   doe  ` are treated as the same).

**Examples:**

* `addbasic n/Alice Tan p/91234567`
  Adds a person named *Alice Tan* with phone number *91234567*.


### **Adding a person (with detailed information)** : `add`

Adds a person with detailed information such as name, phone, email, address, and tags.

**Format:**

```
add n/NAME p/PHONE e/EMAIL a/ADDRESS [t/TAG]…
```

* Adds a person with all specified details to the address book.
* The `NAME`, `PHONE`, `ADDRESS`, and `EMAIL` fields must be provided.
* The person’s phone number must be unique — duplicates are not allowed.
* When multiple tags are provided, they will all be added to the person.

**Examples:**

* `add n/John Doe p/91234567 e/john@example.com a/Kent Ridge t/friend t/colleague`
  Adds a detailed contact with tags.
* `add n/Betsy Crower p/93456789 e/betsy@example.com a/Clementi`
  Adds a new person without tags.


### Editing a person : `edit`

Edits an existing person in FastCard.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

### Filtering contacts: `filter`

Filters contacts whose information contains any of the given tags.

Format `filter t/[TAG]...`

* Filter is case-insensitive. e.g `t/FRIENDS will be treated as t/friends`
* The order of tags does not matter.
* Only full tags will be matched
* Contacts containing at least one tag will be shown (i.e. `OR` search).
### Sorting contacts: `sort`

Orders contacts in a chosen sequence.

Format: `sort f/[FIELD] o/[ORDER]`

* Sorting is case-insensitive. e.g `sort f/NAME o/ASC` will be treated as `sort f/name o/asc`
* The order of keywords does not matter. e.g `sort f/name o/asc` and `sort o/asc f/name` will be treated the name.
* Only one field and one order can be used in a sort command.
* Abbreviated order formats will be treated as their full formats. e.g `o/asc` and `o/desc` will be treated as `o/ascending` and `o/descending`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)
[Configuration.md](Configuration.md)
### Deleting a person : `delete`

Deletes the specified person from FastCard.

Format: `delete NAME` OR 'delete INDEX'

* When a name is provided, FastCard deletes the contact whose name matches the input (case-insensitive).
* If multiple contacts share the same name, FastCard automatically lists the matching contacts. Delete the intended person by running `delete INDEX` using the index shown in that list.
* When an index is provided, the command deletes the contact at that index in the currently displayed list.
* The index must be a valid positive integer within the displayed list.


Examples:
* `delete Alice Pauline` deletes the contact named `Alice Pauline` when she is the only contact with that name.
* `find Jadon` followed by `delete 2` deletes the second person named `Jadon` shown in the results of the `find` command after multiple Jadons were listed.

### Clearing all entries : `clear`

Clears all entries from FastCard.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

FastCard data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

FastCard data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, FastCard will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the FastCard to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

### Repeat commands

FastCard allows you to quickly repeat commands that you entered previously. Simply:

- Press &uarr; (Up Arrow) - Go back in  command history

- Press &darr; (Down Arrow) - Go forward in  command history

<box type="info" seamless>

**Note:** When the command history limit is reached, the feedback box will display "End of Command History reached" along with a blank input field. You may enter any command as per usual there.

</box>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous FastCard home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**    | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Clear**  | `clear`
***Delete** | `delete PHONE`<br> e.g., `delete 83556666`
**Edit**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List**   | `list`
**Help**   | `help`
