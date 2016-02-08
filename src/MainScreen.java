import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class MainScreen extends JPanel implements ActionListener {

  //ATTRIBUTES
  // Default serialVersionUID
  private static final long serialVersionUID = 1L;
  
  // Controller for changing screens
  private Controller controller;
  
  // Default empty border for padding on panels
  private EmptyBorder defaultPadding = new EmptyBorder(5, 15, 5, 5);
  // Default line border for padding on panels
  private LineBorder defaultLinePadding = new LineBorder(Color.WHITE, 5);
  
  // Game instance to execute functions
  private RunGame game;
  
  // Current question
  Question currentQuestion;
  
  // For question panel:
  // String to tell the user if their answer was correct or not (if set to 2 text is left blank)
  boolean correctAnswer;
  
  // Centring text attribute set
  private SimpleAttributeSet centre;
  
  // Text areas
  JTextPane playerTurnTextPane;
  JTextPane questionTextTextPane;
  JTextPane correctAnswerTextPane;
  // Document reference to change colour of text
  StyledDocument doc3;
  Style fontColourStyle;
  
  // For answer panel:
  // Panel holding all buttons for answer submission
  private JPanel answerPanel;
  
  // Options buttons
  private Button quitGame;
  private Button saveGame;
  
  // Buttons for submitting answers
  private ChoiceButton[] choiceSelectButtons;
  private Button submit;
  
  // Set of Buttons keep track of number of currently selected choices
  private List<ChoiceButton> currentButtonsSelected;
  
  // Set of correct answers
  private Set<Integer> correctChoices;
  
  // Integer to hold the max number of choices that can be selected for the current question
  private int maxPickedChoices;
  
  // For game panel:
  // Custom panel with custom board
  private ImagePanel gamePanel;

  // List of all JPanels that are on the path of of the game
  private JPanel[] pathGridPanels;
  
  // JPanel for each character image
  private ImagePanel[] playerCharacterImagePanels;

  //CONSTRUCTOR
  public MainScreen(Controller controller, RunGame game) {
    
    // Assign variables
    this.controller = controller;
    this.game = game;
    
    // Set layout
    setBackground(Color.WHITE);
    setLayout(new BorderLayout());
    
    // Set up centre alignment attribute set
    centre = new SimpleAttributeSet();
    StyleConstants.setAlignment(centre, StyleConstants.ALIGN_CENTER);
    
    // Add question and answer JPanels from first Question
    setupQuestionAndAnswer();
    
    // Add game panel to screen
    setupGameBoard();
  }
  
  //METHODS
  // Set up the question and answer JPanels with all their respective components
  private void setupQuestionAndAnswer() {
    
    // Create question holder panel, holds question information and save game button
    JPanel questionHolderPanel = new JPanel();
    questionHolderPanel.setBackground(Color.WHITE);
    questionHolderPanel.setBorder(defaultPadding);
    questionHolderPanel.setLayout(new BorderLayout());
    
    // Create question panel
    JPanel questionPanel = new JPanel(new GridLayout(0, 1, 0, 0));
    questionPanel.setBackground(Color.WHITE);
    
    // Create text panes for displaying question information
    playerTurnTextPane = new JTextPane();
    questionTextTextPane = new JTextPane();
    
    // Disallow editing
    playerTurnTextPane.setEditable(false);
    questionTextTextPane.setEditable(false);
    
    // Set font of panes
    playerTurnTextPane.setFont(new Font("Century Gothic", Font.ITALIC, 18));
    questionTextTextPane.setFont(new Font("Century Gothic", Font.BOLD, 22));
    
    // Centre text
    StyledDocument doc = playerTurnTextPane.getStyledDocument();
    StyledDocument doc2 = questionTextTextPane.getStyledDocument();
    doc.setParagraphAttributes(0, doc.getLength(), centre, false);
    doc2.setParagraphAttributes(0, doc2.getLength(), centre, false);
    
    // Add text panes to panel
    questionPanel.add(questionTextTextPane);
    questionPanel.add(playerTurnTextPane);
    
    // Add question panel to centre of holder panel
    questionHolderPanel.add(questionPanel, BorderLayout.CENTER);
    
    // Panel to hold save and quit game button
    JPanel optionsPanel = new JPanel();
    optionsPanel.setBackground(Color.WHITE);
    optionsPanel.setLayout(new GridLayout(0, 1, 10, 10));
    optionsPanel.setPreferredSize(new Dimension(300, 0));
    
    // Quit game button
    quitGame = new Button("Quit Game");
    quitGame.addActionListener(this);
    
    // Save game button
    saveGame = new Button("Save Game");
    saveGame.addActionListener(this);
    
    // Add options buttons to the options panel
    optionsPanel.add(quitGame);
    optionsPanel.add(saveGame);
    
    // Add options panel to holder panel
    questionHolderPanel.add(optionsPanel, BorderLayout.EAST);
    
    // Add question holder panel to north of the game screen (this) panel
    add(questionHolderPanel, BorderLayout.NORTH);
    
    // Create answer panel
    answerPanel = new JPanel(new GridLayout(10, 1, 10, 10)); //new GridLayout(0, 1, 10, 10)
    answerPanel.setBackground(Color.WHITE);
    answerPanel.setBorder(defaultPadding);
    // Set width of answer panel to a fixed 300 pixels
    answerPanel.setPreferredSize(
        new Dimension(300, (int) controller.getPreferredSize().getHeight()));
    
    // Add new JPanel to main JPanel
    add(answerPanel, BorderLayout.EAST);

    // Load first question
    currentQuestion = game.getNextQuestion();
    loadNextQuestion();
  }
  
  private void loadNextQuestion() {
    // Load and set question information
    // Text to display information about question
    // Text is split and added in two parts, to create a different font for each part
    String questionText = "Player " + game.getCurrentPlayerNumber() + "'s Turn. This question has ";
    if (currentQuestion.getCorrectAnswers().size() == 1) {
      questionText += "a single correct answer.";
    } else {
      questionText += currentQuestion.getCorrectAnswers().size() + " correct answers.";
    }
    String questionText2 = currentQuestion.getQuestionText();
    
    // Add text to panes
    playerTurnTextPane.setText(questionText);
    questionTextTextPane.setText(questionText2);
    
    // If the pane has been setup
    if (correctAnswerTextPane != null) {
      
      // If the answer was correct, set the text to say so and the colour to green
      if (correctAnswer) {
        correctAnswerTextPane.setText("Correct answer! You move forward "
            + currentQuestion.getMoveForward() + " space(s).");
        StyleConstants.setForeground(fontColourStyle, new Color(0, 100, 0));
      
      // If the answer wasn't correct, set the text to say so and the colour to red
      } else {
        correctAnswerTextPane.setText("Sorry, wrong answer. You move backward "
            + currentQuestion.getMoveBackward() + " space(s).");
        StyleConstants.setForeground(fontColourStyle, Color.RED);
      
      }
      // Update the colour of the text
      doc3.setCharacterAttributes(0, doc3.getLength() + 1, fontColourStyle, false);
    }
    
    // Load and set answer information
    // Remove all the old elements
    answerPanel.removeAll();
    // Assign variable of the max number of choices that can be picked (number of answers)
    maxPickedChoices = currentQuestion.getCorrectAnswers().size();
    correctChoices = currentQuestion.getCorrectAnswers();
    int numberOfChoices = currentQuestion.getNumOfChoices();
    currentButtonsSelected = new ArrayList<ChoiceButton>(3);
    
    // Create array to hold all choice buttons
    choiceSelectButtons = new ChoiceButton[numberOfChoices];
    
    // For each choice generate a button to be selected,
    // add an action listener and add it to the array
    for (int i = 0; i < numberOfChoices; i++) {
      ChoiceButton choice = new ChoiceButton(currentQuestion.getChoices()[i], i);
      choice.addActionListener(this);
      choiceSelectButtons[i] = choice;
      answerPanel.add(choice);
    }
    
    // Fill the rest of the grid with blank spaces, so the submit button is always in the 10th row
    for (int i = numberOfChoices; i < 9; i++) {
      JPanel fillPanel = new JPanel();
      fillPanel.setBackground(Color.WHITE);
      answerPanel.add(fillPanel);
    }
    
    // Create submit button    
    submit = new Button("Submit");
    submit.setCustomBackground(Color.LIGHT_GRAY);
    submit.changeOuterBorder(Color.BLACK, 5);
    submit.addActionListener(this);
    submit.setEnabled(false);
    answerPanel.add(submit);
  }
  
  private void setupGameBoard() {
    
    // Panel to hold game panel
    JPanel gameHolderPanel = new JPanel();
    gameHolderPanel.setBackground(Color.WHITE);
    gameHolderPanel.setLayout(new BorderLayout());
    
    // Create JPanels for each player with image on
    int numberOfPlayers = game.getPlayers().length;
    playerCharacterImagePanels = new ImagePanel[numberOfPlayers];
    for (int i = 0; i < numberOfPlayers; i ++) {
      ImagePanel charImgPanel = new ImagePanel(controller.getCharImages()
          [game.getPlayers()[i].getCharacter() - 1]);
      setOpaque(false);
      playerCharacterImagePanels[i] = charImgPanel;
    }
    
    // Create game panel
    gamePanel = new ImagePanel(new File("resources/board_map.png"));
    gamePanel.setBorder(defaultLinePadding);
    gamePanel.setLayout(new GridLayout(9, 15, 0, 0));
    
    // List that holds all grid co-ordinate pairs for grid cells on the path
    // This list is used to add each JPanel in the appropriate place
    int[][] pathCoords = new int[31][2];
    pathCoords[0] = new int[]{0, 1};
    pathCoords[1] = new int[]{1, 1};
    pathCoords[2] = new int[]{2, 1};
    pathCoords[3] = new int[]{3, 1};
    
    pathCoords[4] = new int[]{3, 2};
    pathCoords[5] = new int[]{3, 3};
    
    pathCoords[6] = new int[]{4, 3};
    pathCoords[7] = new int[]{5, 3};
    pathCoords[8] = new int[]{6, 3};

    pathCoords[9] = new int[]{6, 2};

    pathCoords[10] = new int[]{7, 2};
    pathCoords[11] = new int[]{8, 2};

    pathCoords[12] = new int[]{8, 3};
    pathCoords[13] = new int[]{8, 4};
    pathCoords[14] = new int[]{8, 5};

    pathCoords[15] = new int[]{7, 5};
    pathCoords[16] = new int[]{6, 5};
    pathCoords[17] = new int[]{5, 5};
    pathCoords[18] = new int[]{4, 5};

    pathCoords[19] = new int[]{4, 6};
    pathCoords[20] = new int[]{4, 7};

    pathCoords[21] = new int[]{5, 7};
    pathCoords[22] = new int[]{6, 7};
    pathCoords[23] = new int[]{7, 7};
    pathCoords[24] = new int[]{8, 7};
    pathCoords[25] = new int[]{9, 7};
    pathCoords[26] = new int[]{10, 7};
    pathCoords[27] = new int[]{11, 7};
    pathCoords[28] = new int[]{12, 7};
    pathCoords[29] = new int[]{13, 7};
    pathCoords[30] = new int[]{14, 7};
    
    // Array to hold the JPanel for each cell, so a player can be drawn on that cell
    pathGridPanels = new JPanel[31];
    
    // Create a JPanel, with a GridLayout that's transparent, for each cell that's on the path
    for (int i = 0; i < 31; i++) {
      JPanel gridPanelForPathCell = new JPanel();
      gridPanelForPathCell.setLayout(new GridLayout(2, 2, 0, 0));
      gridPanelForPathCell.setOpaque(false);
      pathGridPanels[i] = gridPanelForPathCell;
    }
        
    // Loop through each row (Y) of grid
    for (int i = 0; i < 9; i++) {
    
      // Loop through each row in the column (X) of grid
      for (int j = 0; j < 15; j++) {
        
        // See if the current cell is on the path
        boolean currentCellOnPath = false;
        
        // The position the current cell is in the path
        int positionInPath = 0;
        
        // If current cell is on path, set boolean to true
        for (int[] currentCell : pathCoords) {
          if (currentCell[0] == j && currentCell[1] == i) {
            currentCellOnPath = true;
            break;
          }
          
          // Loop through each cell on the path, if it's not the current cell, add one to counter
          positionInPath++;
        }
          
        // If it is on the path, add the current JPanel to the grid
        if (currentCellOnPath) {
          gamePanel.add(pathGridPanels[positionInPath]);
          
        // If it isn't on the path, add an empty JLabel to fill the space on the grid
        } else {
          gamePanel.add(new JLabel(""));
        }
      }
    }
    
    // Setting up initial placement of characters
    for (Player player : game.getPlayers()) {
      redrawPathPanel(player.getPosition());
    }
    
    // Panel to hold text on whether the user got the correct answer
    JPanel answerInfoPanel = new JPanel();
    answerInfoPanel.setBackground(Color.WHITE);
    answerInfoPanel.setLayout(new BorderLayout());
    
    // Set up the text pane
    correctAnswerTextPane = new JTextPane();
    correctAnswerTextPane.setEditable(false);
    correctAnswerTextPane.setFont(new Font("Century Gothic", Font.BOLD, 18));
    
    doc3 = correctAnswerTextPane.getStyledDocument();
    doc3.setParagraphAttributes(0, doc3.getLength(), centre, false);
    fontColourStyle = correctAnswerTextPane.addStyle(null, null);
    
    // Add the correct answer information text pane to panel
    answerInfoPanel.add(correctAnswerTextPane);
    
    // Add the game panel to the holder panel
    gameHolderPanel.add(gamePanel, BorderLayout.CENTER);
    
    // Add the answer information to the game panel
    gameHolderPanel.add(answerInfoPanel, BorderLayout.SOUTH);
    
    // Add the holder panel to the main panel
    add(gameHolderPanel, BorderLayout.CENTER);
  }
  
  public void redrawCharacter(int oldPosition, int newPosition) {
    
    redrawPathPanel(oldPosition);
    redrawPathPanel(newPosition);
    
  }
  
  // Redraw a path panel to show/ hide characters
  public void redrawPathPanel(int pathPosition) {
    
    // Get panel reference from array
    JPanel pathPanel = pathGridPanels[pathPosition];
    
    // List of all players on the panel
    List<Integer> panelPlayerNumbers = new ArrayList<Integer>();
    
    // Add positions to list
    for (Player play : game.getPlayers()) {
      if (play.getPosition() == pathPosition) {
        panelPlayerNumbers.add(play.getPlayerNumber());
      }
    }
    
    // Clean panel
    pathPanel.removeAll();
    pathPanel.setLayout(new GridLayout(2, 2, 0, 0));
    pathPanel.setOpaque(false);
    
    // Draw the new characters on the grid, with positioning dependent on player number
    for (int i = 0; i < 4; i ++) {
      if (i < playerCharacterImagePanels.length) {
        if (panelPlayerNumbers.contains(i)) {
          pathPanel.add(playerCharacterImagePanels[i]);
        
        } else {
          pathPanel.add(new JLabel(""));
        }
      
      } else {
        pathPanel.add(new JLabel(""));
      }
    }
    
    // Update the panel
    pathPanel.validate();
    pathPanel.repaint();
  }
  
  // Button action handling
  public void actionPerformed(ActionEvent event) {
    
    // Loop through buttons representing each choice
    for (ChoiceButton button : choiceSelectButtons) {
      
      // Once we find the right button
      if (event.getSource().equals(button)) {
        
        // If the button has not been selected
        if (button.getSelected() == false) {
          
          // And we have not reached the max limit of choices
          if (currentButtonsSelected.size() < maxPickedChoices) {
            
            // Select the choice
            button.selectChoice();
            currentButtonsSelected.add(button);
          
          // If we have reached the max number of choices
          } else {
            
            // Deselect the first choice and select the new one
            currentButtonsSelected.get(0).deselectChoice();
            currentButtonsSelected.remove(0);
            button.selectChoice();
            currentButtonsSelected.add(button);
            
          }
        
        // Else if the button has been selected
        } else {
          
          // Deselect it
          button.deselectChoice();
          currentButtonsSelected.remove(button);
        }
      }
    }

    if (event.getSource() == saveGame) {
      controller.saveGame();
      
    } else if (event.getSource() == quitGame) {
      controller.quitGame();
    
    // If the correct amount of choices have been selected
    // Change the submit background colour to white to show it can be selected
    } else if (currentButtonsSelected.size() == maxPickedChoices) {
      submit.setCustomBackground(Color.WHITE);
      submit.setEnabled(true);
      
      // If submit is pressed and we have selected all choices
      if (event.getSource().equals(submit)) {
        
        // Reference to update the player that was moved
        Player movedPlayer = game.getCurrentPlayer();
        int oldPosition = movedPlayer.getPosition();
        
        // Create int array of choices
        Set<Integer> currentPickedChoices = new HashSet<Integer>();
        for (ChoiceButton button : currentButtonsSelected) {
          currentPickedChoices.add(button.getChoiceNum());
        }
        
        // Move the player forward and set boolean to update display with text
        // information on whether they got the answer correct and how far they moved
        if (currentPickedChoices.containsAll(correctChoices)) {
          int moveForward = currentQuestion.getMoveForward();
          game.moveCurrentPlayerForward(moveForward);
          correctAnswer = true;
          
        // Otherwise move the player backward
        } else {
          int moveBackward = currentQuestion.getMoveBackward();
          game.moveCurrentPlayerBackward(moveBackward);
          correctAnswer = false;
          
        }
        
        int newPosition = movedPlayer.getPosition();
        
        // Update the character on the board if they have moved
        // (they don't move if they go backwards from square 0)
        if (newPosition != oldPosition) {
          redrawCharacter(oldPosition, newPosition);
        }
        
        // If player reaches the last tile, they have won
        if (newPosition == 30) {
          controller.winGame();
        
        } else {
          // Load the next question and display it
          currentQuestion = game.getNextQuestion();
          loadNextQuestion();
        }
      }
    
    // Else set the submit colour to light grey to show it can't be clicked yet
    } else {
      submit.setCustomBackground(Color.LIGHT_GRAY);
      submit.setEnabled(false);
    }

  }
}