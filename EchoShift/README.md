# Echo Shift

Echo Shift is a horror typing game built in Java using JavaFX for Western's CS2212.
In order to survive the night players must quickly type words allowing them to activate abilities.

## Gameplay
Survive your shift by typing words correctly to trigger actions.
If the Entity reaches the office and steals you last life it is GAME OVER.

### Available Actions
- Scan - Reveals the Entity's location
- Lure - Lures the monster to the selected node if it is in an adjacent node.

### How to play
- Type a word with no node selected to activate the scanner and see the Entity's location.
- Click a node then type a word to try and lure the Entity to that node (This only works if the entity is in an adjacent node).
- Click on power ups to activate their effects.

### Power-ups
- Extra Life: Click to regain one life.
- Easier Word: Get access to an easier bank of words.
- Instant Lure: Send entity back to its starting room.

### Levels
There are Three Nights each increasing in difficulty.
- Night 1, The Breakout: The Entity has just escaped. It moves slowly and is still learning the factory layout.
- Night 2, The Hunt: The Entity is adapting. It moves faster and begins to track you more effectively.
- Night 3, No Escape: The creature now hunts with terrifying speed and precision. Surviving this night will require perfect typing and quick decisions.

## Technologies Used
- Java 23
- JavaFX 21
- JSON data files
- IntelliJ IDEA
  Custom game logic and rendering systems

## Run Requirements
- Java 23
- JavaFX 21

### Running the Game

##### Step 1: Extract the ZIP

Download the project and extract the .zip file to a folder on your computer.

##### Step 2: Open the Project in IntelliJ
   Open IntelliJ IDEA
   Click Open
   Select the extracted project folder
   Wait for IntelliJ to finish indexing the files. 

##### Step 3: Set the Project SDK
   Go to File → Project Structure → Project
   Set the Project SDK to Java 23

##### Step 4: Configure JavaFX

Download JavaFX 21 from the official website if it is not already installed:

JavaFX

Then configure the run settings:

Open Run → Edit Configurations
Add the following to VM Options:
--module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml,javafx.media

Replace /path/to/javafx/lib with the path to your JavaFX lib folder. 

##### Step 5: Run the Game
Locate the main class:
App.java
Right-click App.java and select:
Run 'App.main()'
The game window should now launch.

## Authors
Bob Zhang
Ho Long Adrian Lee
Matthew Michael Taylor
Tudor-Mihai Pristav
Yasmine Suojhayer