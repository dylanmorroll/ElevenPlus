import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class QuestionReader {
  
  // Generate Question objects from default question file
  public static void generateDefaultQuestions() {
    
    // Creating file references
    File questionFile = new File("resources/questions.txt");
    
    // Try creating a scan reference to read from file
    try {
      Scanner fileScanner = new Scanner(questionFile);
      
      // Whilst there are still lines of text (there must be another question)
      while (fileScanner.hasNextLine()) {        
        
        // Create variables, from file, to be passed to Question class
        // Default string to hold current line
        String currentLine;
        
        // multipleChoice
        boolean multipleChoice;
        currentLine = fileScanner.nextLine();
        
        if (currentLine.equals("Single Choice")) {
          multipleChoice = false;
          
        } else {
          multipleChoice = true;
        }
        
        // questionText
        currentLine = fileScanner.nextLine();
        String questionText = currentLine;
        
        // numOfChocies
        currentLine = fileScanner.nextLine();
        Integer numOfChoices = Integer.parseInt(currentLine);
        
        // choices
        String[] choices = new String[numOfChoices];
        for (int i = 0; i < numOfChoices; i++) {
          // Append each choice to the list
          currentLine = fileScanner.nextLine();
          choices[i] = currentLine;
        }
        
        // correctAnswers
        currentLine = fileScanner.nextLine();
        
        // Split line up on ',' to get a string array of integers
        String[] parts = currentLine.split(",");
        
        // Create an int array from string array
        Set<Integer> correctAnswers = new HashSet<Integer>(parts.length);
        for (int i = 0; i < parts.length; i++) {
          correctAnswers.add(Integer.parseInt(parts[i]) - 1);
        }
        
        // moveForward
        currentLine = fileScanner.nextLine();
        int moveForward = Integer.parseInt(currentLine);
        
        // moveBackward
        currentLine = fileScanner.nextLine();
        int moveBackward = Integer.parseInt(currentLine);
        
        // Create Question object which will be automatically added to static list
        new Question(
            multipleChoice,
            questionText,
            numOfChoices,
            choices,
            correctAnswers,
            moveForward,
            moveBackward);
      }
      
      // Close fileScanner after loop
      fileScanner.close();
      
    }  catch (FileNotFoundException e) {
      System.out.println("QuestionReader.java: Question file not found.");
    }
  }
}