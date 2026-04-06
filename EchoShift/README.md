# Echo Shift

Echo Shift is a horror typing game built in Java using JavaFX for Western's CS2212.
In order to survive the night, players must quickly type words to activate abilities and prevent the Entity from reaching their office.

## Description

Echo Shift is a survival horror typing game. Players take on the role of a night-shift worker being hunted by a terrifying Entity. By typing words correctly, players can scan the factory map, lure the Entity away, and use power-ups to stay alive through three increasingly difficult nights.

---

## Required Libraries and Tools

- IntelliJ IDEA 2024.x or later - IDE used to build and run the project
- Java JDK 23 - Language version
- JavaFX SDK 25.0.2 - UI framework
- Gson 2.13.2 - JSON parsing for save data
- JUnit Jupiter 5.14.0 - Unit testing
- JUnit Platform 1.14.0 - Test runner
- Hamcrest Core 1.3 - Test assertions
- OpenTest4J 1.3.0 - Test exception support
- APIGuardian API 1.1.2 - JUnit annotation support

### Where to Download
- **JDK 23:** https://www.oracle.com/java/technologies/downloads/#java23
- **JavaFX 25.0.2 SDK (Windows x64):** https://gluonhq.com/products/javafx/
- **IntelliJ IDEA:** https://www.jetbrains.com/idea/download/

---

## Building from Source

### Step 1: Extract the ZIP

Download and extract the project ZIP to a folder on your computer, for example:
```
C:\Users\YourName\group55\
```

### Step 2: Open the Project in IntelliJ

1. Open IntelliJ IDEA
2. Click Open
3. Navigate to and select the extracted EchoShift folder (the one containing the `src` folder)
4. Click **OK** and wait for IntelliJ to finish indexing


### Step 3: Set the Project SDK to Java 23

1. Go to File then Project Structure
2. Under Project, set the SDK to Java 23
3. Set Language Level to 23
4. Click Apply


### Step 4: Add the Required Libraries

1. Still in File then Project Structure, click Libraries on the left
2. Click + then Java
3. Navigate to your JavaFX SDK folder and select the lib folder, e.g.:
   ```
   C:\Users\YourName\javafx-sdk-25.0.2\lib
   ```
4. Click OK — IntelliJ will ask which modules to add it to, select the project module and click OK
5. Repeat the process to add any other required JARs (Gson, JUnit, etc.) from the project's `lib` or `files` folder
6. Click Apply then OK


### Step 5: Configure VM Options for JavaFX

1. Go to Run then Edit Configurations
2. Select the App configuration (or click + then Application and set the Main class to `echoshift.App`)
3. In the VM Options field, add:
   --module-path "C:\Users\YourName\javafx-sdk-25.0.2\lib" --add-modules javafx.controls,javafx.fxml,javafx.media
   Replace the path with the actual location of your JavaFX `lib` folder.
4. Click Apply then OK


### Step 6: Build the Project

Go to Build then Build Project
The project should compile with no errors. If you see errors about missing classes, double-check that all libraries were added correctly in Step 4.

---

## Running the Game

Once the project is built:

1. In the Project panel on the left, navigate to:
   src/echoshift/App.java
2. Right-click App.java and select Run 'App.main()'
3. The game window will launch maximized

---

## User Guide

### Main Menu

On launch you will see the main menu. From here you can:
- Player Login — log in to an existing player account
- Create Account — register a new player account
- Admin Login — access the admin panel (parental controls)


### Gameplay

Survive your shift by typing words correctly to trigger actions. If the Entity reaches your office and steals your last life, it is GAME OVER.


#### Available Actions
- Scan — Reveals the Entity's current location on the map
- Lure — Lures the Entity to a selected node if it is in an adjacent room


#### How to Play
- Type a word with no node selected to activate the scanner and reveal the Entity's location
- Click a node on the map, then type a word to lure the Entity to that node (only works if the Entity is in an adjacent node)
- Click on a power-up icon to activate its effect


#### Power-ups

Extra Life - Regain one life
Easier Word - Access an easier word bank for your next action
Instant Lure - Send the Entity back to its starting room immediately


### Levels

There are three nights, each increasing in difficulty:

- Night 1 – The Breakout: The Entity has just escaped. It moves slowly and is still learning the factory layout.
- Night 2 – The Hunt: The Entity is adapting. It moves faster and begins to track you more effectively.
- Night 3 – No Escape: The creature hunts with terrifying speed and precision. Surviving this night requires perfect typing and quick decisions.


### Shop

Between actions, players can spend currency earned through correct typing to purchase power-ups from the shop.


### Statistics

Player statistics (words typed, accuracy, games played, etc.) are tracked per account and can be viewed from the Player Home screen after logging in.


### Settings

From the settings menu, players can adjust music and sound effect volume.

---

## Accounts and Passwords

The following test accounts are pre-loaded with the project:

User Name - Password - Type
admin - test - Admin (Parental Controls)
test - test - Player
test1 - test - Player

New player accounts can be created from the Create Account screen on the main menu.

---

## Parental Controls / Admin Panel

The Admin Panel serves as the parental controls for Echo Shift. It allows an administrator to manage all player accounts, view statistics, and remove accounts.


### Accessing the Admin Panel

1. From the Main Page, click Admin Login
2. Enter the admin credentials:
    - Username: admin
    - Password: test
3. Click Login


### Admin Panel Features

- View all player accounts — see a list of all registered players
- View player statistics — inspect any player's game history and performance
- Delete accounts — remove a player account from the system

---

## Authors

- Bob Zhang
- Ho Long Adrian Lee
- Matthew Michael Taylor
- Tudor-Mihai Pristav
- Yasmine Suojhayer
