---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# FastCard User Guide

FastCard is a desktop contact manager controlled largely by keyboard commands, letting you add, find, and update contacts in seconds; No fumbling with menus, Pure Speed!

<!-- * Table of Contents -->
## **Table of Contents**

- [Getting Started with FastCard](#getting-started-with-fastcard)
- [Features](#features)
  - [AddBasic Command](#adding-a-person-basic-information-only--addbasic)
  - [Add Command](#adding-a-person-with-detailed-information--add)
  - [Edit Command](#editing-a-person--edit)
  - [Find Command](#locating-persons-by-name-find)
  - [Filter Command](#filtering-contacts-filter)
  - [Sort Command](#sorting-contacts-sort)
  - [Delete Command](#deleting-a-person--delete)
  - [Clear Command](#clearing-all-entries--clear)
  - [Exit Command](#exiting-the-program--exit)
  - [Command Recall](#repeat-commands)
- [Saving Data](#saving-the-data)
- [Editing Data File](#editing-the-data-file)
- [Frequently Asked Questions](#faq)
- [Known Issues](#known-issues)
- [Command Summary](#command-summary)

<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Getting Started with FastCard

**Step 1: Install Java (if you haven't already)** <br>
FastCard requires **Java 17 or newer** to run. Java is free software that lets your computer run applications like FastCard.

**Check if you already have Java:**
1. Open your command terminal (Command Prompt on Windows, Terminal on Mac/Linux).
2. Type `java -version` and press Enter.
3. If you see "java version 17" or higher, you're ready. skip to Step 2.
4. If you see an error or a lower version number, install Java:
    * **Windows users:** Download Java 17 from [here](https://se-education.org/guides/tutorials/javaInstallationWindows.html#switching-between-java-versions).
    * **Linux users:** Download Java 17 from [here](https://se-education.org/guides/tutorials/javaInstallationLinux.html#switching-between-java-versions).
    * **Mac users:** Download Java 17 from [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

**Step 2: Download FastCard**
1. Go to the FastCard releases page [here](https://github.com/AY2526S1-CS2103T-F11-4/tp/releases).
2. Download the latest `fastcard.jar` file (look for the newest version at the top).
3. Save it somewhere easy to find (like your Desktop or Documents folder).

**Step 3: Set up your FastCard folder**
1. Create a new folder where you want to keep FastCard and all your contact data.
    * Example: Create a folder called "FastCard" in your Documents.
2. Move the `fastcard.jar` file into this folder.
3. Remember this location. This is where all your data will be saved.

**Step 4: Launch FastCard**
**Using the command terminal:**
1. Open your command terminal:
    * **Windows:** Search for "Command Prompt" or "PowerShell"
    * **Mac:** Search for "Terminal" in Spotlight
    * **Linux:** Open your terminal application
2. Navigate to your FastCard folder by typing: `cd path/to/your/FastCard/folder` <br>
   Example: `cd Documents/FastCard`
3. Run FastCard by typing: `java -jar fastcard.jar`
4. Press Enter

**What you should see:** FastCard's window should appear within a few seconds, showing some sample contacts to help you get started.

**Troubleshooting:**
  * If you see "java is not recognized" &rarr; Java isn't installed correctly. Return to Step 1
  * If you see "file not found" &rarr; You're not in the right folder. Check Step 4.2

**Step 5: Try your first commands**
The command box is at the top of the window. Type a command and press **Enter** to execute it.

**Try these example commands:**
1. See all contacts: `list`
2. Add a new contact: `add n/John Doe p/98765432 e/johnd@example.com a/123 John Street, #01-01`
3. Search for a contact: `find John`
4. Delete a contact: (First, note the phone number of a contact you want to remove): `delete John Doe`
5. Get help: `help`

**Step 6: Learn more commands**
Once you're comfortable with the basics, check out the [Features](#features) section below for the complete list of what FastCard can do.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

**WORDS IN CAPITALS = Replace with your information**
* Commands show placeholders in `UPPER_CASE` that you need to replace with actual information.<br>
  Example: in `add n/NAME`, `NAME` can be replaced with `John Doe`, resulting in `add n/John Doe`.

**[Square Brackets] = Optional**
* Anything in square brackets is optional. You can include it or skip it.<br>
  Example: `n/NAME [t/TAG]` means:
    * `n/John Doe t/friend` (with optional tag)
    * `n/John Doe` (without tag, still works!)

**Three Dots… = Repeat as needed**
* Items with `…` can be used multiple times or not at all.<br>
  Example: `[t/TAG]…​` means:
    * ` ` (no tags)
    * `t/friend` (one tag)
    * `t/friend t/family t/colleague` (multiple tags, add as many as you want!)

**Order Doesn't Matter**
* You can type the information in any order that makes sense to you.<br>
  Example: These commands do the exact same thing:
    * `addbasic n/John Doe p/91234567`
    * `addbasic p/91234567`

**Extra Words Get Ignored**
* For simple commands like `help`, `list`, `exit`, and `clear`, anything extra you type will be ignored.<br>
  Example: Typing `help 123` or `help please` both just run `help`.

If you're reading this as a PDF and copying commands, be aware that spaces may get removed when you paste them into FastCard. This can cause commands to fail.

**Solution:** After pasting, check that spaces are still there, especially around line breaks.
</box>

### Viewing help : `help`

Opens a help window with a link to the full user guide.

**Format:** `help`

![help message](images/helpMessage.png)

**What it does:**
  * Displays a popup window with instructions for accessing detailed help.
  * Provides the URL to the complete guide.
  * Can be closed to return to FastCard.

**When to use this:**
  * You forgot how a command works.
  * You want to see all the available commands.
  * You need detailed examples.

### Listing all persons : `list`

Shows a list of all persons in the FastCard.

**What it does:**
  * Shows all contacts in your address book (removes any filters).
  * Resets the view to display everyone.
  * Useful after using `find` or `filter` commands.

**When to use this:**
  * After searching or filtering, to see everyone again.
  * To check your total number of contacts.
  * To start fresh before a new search.

### **Adding a person (basic information only)** : `addbasic`

Adds a person with only basic information (name and phone number) to FastCard.

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

Adds a person with detailed information such as name, phone, email, company, and optional tags.

**Format:**

```
add n/NAME p/PHONE e/EMAIL c/COMPANY [t/TAG]…
```

**What you need to provide:**
  * **Name** (`n/`) - Full name (letters, numbers, and spaces only), must be unique
  * **Phone** (`p/`) - At least 3 digits, must be unique
  * **Email** (`e/`) - Valid email address
  * **Company** (`c/`) - Any company format
  * **Tags** (`t/`) - Optional labels like "friend" or "colleague" (add as many as you want)

**What you need to know:**
  * All fields except tags are required.
  * Name and phone number must be unique, you can't add two people with the same name and phone number.
  * Add multiple tags by repeating `t/` (e.g., t/friend t/colleague)

**When to use this:**
  * You have complete contact information ready.
  * You want to organize contacts with tags immediately.
  * You're adding professional contacts that need full details.

**Use `addbasic` instead if:** You only have a name and phone number.

**Examples:**

* `add n/John Doe p/91234567 e/john@example.com c/Shopee t/friend t/colleague`
  Adds John Doe with email, company, and two tags.
* `add n/Betsy Crower p/93456789 e/betsy@example.com c/Shopee`
  Adds Betsy Crower with full details but no tags (tags are optional).

**Common Mistakes:**
  * Forgetting to include all required fields (name, phone, email, company)
  * Using an existing name and phone number (name and phone number pair must be unique)
  *  Writing `e/john@example` instead of `e/john@example.com` (incomplete email)

### Editing a person : `edit`

Edits an existing person in FastCard.

Format:
- `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [c/COMPANY] [t/TAG]…​`
- `edit NAME  [n/NAME] [p/PHONE] [e/EMAIL] [c/COMPANY] [t/TAG]…​`

* Edit by index: the `INDEX` refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* Edit by name: the `NAME` is matched case-insensitively; leading/trailing spaces are ignored and multiple spaces are treated as one (e.g., `Jane         Smith` = `Jane Smith`).
* If multiple contacts share the same name, FastCard will display the matched contacts and prompt: “There are multiple contacts’ names matched with the reference. Please use the edit /index {index} to specify the contact you want to edit.”
  * Continue by running `edit {index} ...` with the index shown in that filtered list.
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.

Examples:
* `edit 1 p/91234567 e/johndoe@example.com` — Edits the phone number and email address of the 1st person.
* `edit 2 n/Betsy Crower t/` — Edits the name of the 2nd person to `Betsy Crower` and clears all existing tags.
* `edit John Doe e/john.doe@company.com` — Edits the email of the contact named `John Doe` (case-insensitive name match).
* If multiple `John Doe` exist: run `edit 3 p/88888888` to specify the intended person from the filtered list.

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Filtering contacts: `filter`

Shows only contacts that have specific tags.

Format `filter t/TAG [t/TAG]…`

**What you need to know:**

* You must specify at least one tag.
* You can specify more than one tag.
    * If you specify more than one tag, contacts with **any** of those tags will appear (not all tags required).
* Tags are not case-sensitive (`t/friends` = `t/FRIENDS` = `t/Friends`)
* You may list the tags in any order.
* The exact tag name must match (e.g., `friend` won't find contacts tagged with `friends`)

### Sorting contacts: `sort`

Arranges your contacts in alphabetical order based on the information you choose.

Format: `sort f/FIELD o/ORDER`

**Available fields:**

* `name` - Sort by contact name
* `tag` - Sort by the first tag alphabetically

**Available orders:**

* `asc` or `ascending` - sorts in alphabetical order
* `desc` or `descending` - sorts in reverse alphabetical order

**What you need to know:**

* You must specify both a field and an order.
* Field and order are not case-sensitive. (`NAME = name`, `ASC` = `asc`)
* You can write field and order in any sequence.
* Contacts without the sorted field appear first/last if sorted in ascending/descending order respectively (e.g., contacts without tags when sorting by tag)

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)
[Configuration.md](Configuration.md)
Example: 
* `sort f/name o/asc` sorts contacts by name in alphabetical order
* `sort f/tag o/desc` sorts contacts by the first alphabetically ordered tag in descending order.
    * Note that if a contact has no tags, it is treated as though it has a tag that precedes all others alphabetically.

### Deleting a person : `delete`

Deletes the specified person from FastCard.

Format: `delete NAME` OR `delete INDEX`

* When a name is provided, FastCard deletes the contact whose name matches the input (case-insensitive).
* If multiple contacts share the same name, FastCard automatically lists the matching contacts. Delete the intended person by running `delete INDEX` using the index shown in that list.
* When an index is provided, the command deletes the contact at that index in the currently displayed list.
* The index must be a valid positive integer within the displayed list.


Examples:
* `delete Alice Pauline` deletes the contact named `Alice Pauline` when she is the only contact with that name.
* `find Jadon` followed by `delete 2` deletes the second person named `Jadon` shown in the results of the `find` command after multiple Jadons were listed.

### Clearing all entries : `clear`

**⚠️ WARNING: This permanently deletes ALL contacts from FastCard. This cannot be undone.**

**Format:** `clear`

**When to use:** Only when you want to completely start over with an empty contact list.

### Exiting the program : `exit`

Closes the FastCard application.

**Format:** `exit`

**Note:** All your data is automatically saved when you exit, so you don't need to save manually.

### Repeat commands

FastCard allows you to quickly repeat commands that you entered previously. Simply:

- Press &uarr; (Up Arrow) - Go back in  command history

- Press &darr; (Down Arrow) - Go forward in  command history

**Note:** When the command history limit is reached, the feedback box will display "End of Command History reached" along with a blank input field. You may enter any command as per usual there.

<box type="info" seamless>

### Saving the data

**Your data is automatically saved**
FastCard saves your contacts automatically every time you make a change.

**What this means for you:**
  * Add, edit, or delete a contact &rarr; automatically saved.
  * Close FastCard anytime &rarr; your data is safe.
  * No risk of losing work if you forget to save.

**Where your data is stored:** All contacts are saved to your computer's hard drive where the FastCard application is located.

### Editing the data file

Your contact data is stored in a file called `addressbook.json` located in `[JAR file location]/data/`.
Advanced users are welcome to update data directly by editing that data file.

**⚠️ WARNING: Manual editing is risky**

**If you edit this file incorrectly:**
  * FastCard will delete **ALL** your data and start with an empty file
  * The app may behave unpredictably
  * You could lose all your contacts permanently

**Before editing:**
  * Make a backup copy of the file
  * Only proceed if you understand JSON file format
  * Test with a small change first

**Recommendation:** Use FastCard's commands instead of editing the file directly. It's much safer.

</box>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q: How do I transfer my data to another Computer?** <br>
**A**:
  1. On your old computer, locate the `addressbook.json` file in `[JAR file location]/data/`.
  2. Copy this file to the other computer.
  3. Install FastCard on your other computer.
  4. Replace the new `addressbook.json` file with your copied file.
  5. Restart FastCard. All your contacts should appear.

**Q: Can I undo a command I just entered?**
**A**: No, FastCard doesn't have an undo feature. Commands take effect immediately and are saved automatically. Be especially careful with `delete` and `clear` commands.

**Q: How many contacts can FastCard store?**
**A:** FastCard can handle thousands of contacts. Performance depends on your computer's specifications, but typical users won't encounter any limits.

**Q: What happens if FastCard crashes?**
**A:** Your data is safe. Since FastCard saves automatically after every change, you'll only lose any command you were typing when it crashed (not the data itself).

**Q: I accidentally deleted a contact. Can I recover it?**
**A:** Unfortunately, no. FastCard doesn't have a recycle bin or recovery feature. The contact is permanently deleted. Consider backing up your `addressbook.json`F file regularly.

**Q: Why isn't my command working?**
**A:** Common reasons include:
  * Spelling mistakes (check the exact command format)
  * Missing required information (like phone number for add)
  * Duplicate phone numbers (FastCard won't add someone with an existing number)
  * Typos in the command
--------------------------------------------------------------------------------------------------------------------

## Known issues

**Issues you might encounter**

**App opens off-screen when using multiple screens**

**Problem:** If you used FastCard on a secondary monitor, then disconnected it, the app may open in an invisible location.
**Solution:**
  1. Close FastCard completely.
  2. Find and delete the `preferences.json` file in your FastCard folder.
  3. Restart FastCard. It should open on your main screen.

**Help window stays minimized**

**Problem:** If you minimize the Help window and press `F1` (or type `help`) again, nothing appears to happen.
**What's actually happening:** The Help window is already open but minimized.
**Solution:** Look for the minimized Help window in your taskbar and click it to restore it.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**    | `add n/NAME p/PHONE_NUMBER e/EMAIL c/COMPANY [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**AddBasic** | `addbasic n/NAME p/PHONE_NUMBER` <br> e.g., `addbasic n/James Ho p/22224444
**Delete** | `delete PHONE`<br> e.g., `delete 83556666`
**Edit**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [c/COMPANY] [t/TAG]…​` or `edit NAME [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [c/COMPANY] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Sort** | `sort f/FIELD o/ORDER` <br> e.g., `sort f/name o/asc`
**Filter** | `filter t/TAG [t/TAG]…` <br> e.g., `filter t/friend t/colleague`
**Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**Clear**  | `clear`
**List**   | `list`
**Help**   | `help`
**Repeat Commands** | &uarr; (Up Arrow Key) &darr; (Down Arrow Key)
