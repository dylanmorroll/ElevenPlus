import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Question {
  
  //CLASS VARIABLES
  // Question details  
  private boolean multipleChoice;
  private String questionText;
  private int numOfChoices;
  private String[] choices;
  private Set<Integer> correctAnswers;
  private int moveForward;
  private int moveBackward;
  
  // Static list of all questions
  private static List<Question> allQuestions = new ArrayList<Question>();
  
  //CONSTRUCTOR
  // Creating question 
  public Question(
      boolean multipleChoice,
      String questionText,
      int numOfChoices,
      String[] choices,
      Set<Integer> correctAnswers,
      int moveForward,
      int moveBackward) {
    
    // Start of constructor
    // Assigning variables
    this.multipleChoice = multipleChoice;
    this.questionText = questionText;
    this.numOfChoices = numOfChoices;
    this.choices = choices;
    this.correctAnswers = correctAnswers;
    this.moveForward = moveForward;
    this.moveBackward = moveBackward;
    
    // Adding question to static list
    Question.allQuestions.add(this);
  }
  
  //METHODS
  // Static list getters and setters
  public static List<Question> getAllQuestions() {
    return allQuestions;
  }
  
  //GETTERS & SETTERS
  // singleChoice
  public boolean getMultipleChoice() {
    return multipleChoice;
  }
  
  // questionText
  public String getQuestionText() {
    return questionText;
  }
  
  // numOfChoices
  public int getNumOfChoices() {
    return numOfChoices;
  }
  
  // choices
  public String[] getChoices() {
    return choices;
  }
  
  // correctAnswers
  public Set<Integer> getCorrectAnswers() {
    return correctAnswers;
  }
  
  // moveForward
  public int getMoveForward() {
    return moveForward;
  }
  
  // moveBackward
  public int getMoveBackward() {
    return moveBackward;
  }
}
