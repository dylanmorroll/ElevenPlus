import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

// Control class for GUI classes to process back-end functionality
public class RunGame {
  
  //ATTRIBUTES
  private Random random = new Random();
  private List<Question> questions;
  private int numOfQuestions;
  List<Integer> questionsLeftListIndex;
  Player[] players;
  int currentPlayerIndex = 0;
  
  //METHODS
  // QUESTION METHODS
  public void loadQuestions() {
    QuestionReader.generateDefaultQuestions();
    questions = Question.getAllQuestions();
    numOfQuestions = questions.size();
    
    // List of integers, that represent the index position that question is, in the questions list
    questionsLeftListIndex = new ArrayList<Integer>();
    for (int i = 0; i < numOfQuestions; i++) {
      questionsLeftListIndex.add(i);
    }
  }
  
  public Question getNextQuestion() {
    // If questionList hasn't been instantiated, instantiate it
    if (questionsLeftListIndex == null) {
      loadQuestions();
    }
    
    // If there are no more questions, re generate the list of question indexing
    if (questionsLeftListIndex.size() < 1) {
      questionsLeftListIndex = new ArrayList<Integer>();
      for (int i = 0; i < numOfQuestions; i++) {
        questionsLeftListIndex.add(i);
      }
    }

    // Return next question
    int nextPosition = random.nextInt(questionsLeftListIndex.size());
    int nextActual = questionsLeftListIndex.get(nextPosition);
    questionsLeftListIndex.remove(nextPosition);
    return questions.get(nextActual);
  }
  
  // PLAYER HANDLING METHODS
  public void setUpPlayers(List<Integer> charsPicked) {
    // Create array of players
    players = new Player[charsPicked.size()];
    
    // For each player, set their player number and the character they have chosen
    for (int i = 0; i < charsPicked.size(); i++) {
      players[i] = new Player(i, charsPicked.get(i));
    }
  }
  
  // Set up players and positions (for loading games)
  public void setUpPlayers(List<Integer> charsPicked, List<Integer> positions) {
    // Create array of players
    players = new Player[charsPicked.size()];
    
    // For each player, set their player number and the character they have chosen
    for (int i = 0; i < charsPicked.size(); i++) {
      players[i] = new Player(i, charsPicked.get(i), positions.get(i));
    }
  }
  
  // Move the current player to the next player
  public void nextPlayer() {
    // Move the index to point at the next player
    currentPlayerIndex++;
    
    // If there is no next player, go back to the first player
    if (currentPlayerIndex >= players.length) {
      currentPlayerIndex = 0;
    }
  }
  
  public void moveCurrentPlayerForward(int amount) {
    Player currentPlayer = getCurrentPlayer();
    int newPosition = currentPlayer.getPosition() + amount;
    
    // Player position can't be off the board
    if (newPosition > 30) {
      currentPlayer.setPosition(30);
    
    } else {
      currentPlayer.setPosition(newPosition);
    }
    
    nextPlayer();
  }
  
  public void moveCurrentPlayerBackward(int amount) {
    Player currentPlayer = getCurrentPlayer();
    int newPosition = currentPlayer.getPosition() - amount;
    
    // Player position can't be off the board
    if (newPosition < 0) {
      currentPlayer.setPosition(0);
    
    } else {
      currentPlayer.setPosition(newPosition);
    }
    
    nextPlayer();
  }
  
  // SAVING AND LOADING METHODS
  public void saveGame(File saveFile) {
    
    // Create FileOutputStream and PrintWriter for saving to file
    FileOutputStream fos;
    
    try {
      fos = new FileOutputStream(saveFile);
      PrintWriter writer = new PrintWriter(fos);
      
      // Save information in text file
      // Number of players
      writer.println(players.length);
      // Position of player  - for each
      // Character of player - for each
      for (Player player : players) {
        writer.println(player.getPosition());
        writer.println(player.getCharacter());
      }
      // Current player
      writer.println(currentPlayerIndex);
      
      // Close PrintWriter
      writer.close();
      
    } catch (FileNotFoundException e) {
      System.out.println("Invalid file for saving");
      e.printStackTrace();
    }
  }
  
  public void loadGame(String fileName) {
    
    File fileToLoad = new File(fileName);
    
    // Try creating a scan reference to read from file
    try {
      Scanner fileScanner = new Scanner(fileToLoad);
      
      // Lists of details to set up characters with
      List<Integer> playersPositions = new ArrayList<Integer>();
      List<Integer> charactersPicked = new ArrayList<Integer>();
      
      // First line is number of players
      int numberOfPlayers = Integer.parseInt(fileScanner.nextLine());
      
      // For each player there are 2 lines, position and character
      for (int i = 0; i < numberOfPlayers; i++) {
        
        // Add their position to list
        playersPositions.add(Integer.parseInt(fileScanner.nextLine()));
        
        // Add their character to list
        charactersPicked.add(Integer.parseInt(fileScanner.nextLine()));
      }
      
      // Final line is current player
      currentPlayerIndex = Integer.parseInt(fileScanner.nextLine());
     
      // Set up the game
      setUpPlayers(charactersPicked, playersPositions);
      
      fileScanner.close();
      
    }  catch (FileNotFoundException e) {
      System.out.println("RunGame.java:loadGame:File not found");
    }
  }
  
  public void deleteGame(String fileName) {
    
    // Create file object
    File fileToDelete = new File(fileName);
    
    // Delete file object
    fileToDelete.delete();
  }
  
  //GETTERS & SETTERS
  public Player[] getPlayers() {
    return players;
  }
  
  public Player getLastPlayer() {
    int lastPlayer = currentPlayerIndex--;
    
    if (lastPlayer < 0) {
      lastPlayer = players.length;
    }
    
    return players[lastPlayer];
  }
  
  public Player getCurrentPlayer() {
    return players[currentPlayerIndex];
  }
  
  public int getCurrentPlayerNumber() {
    return currentPlayerIndex + 1;
  }
}