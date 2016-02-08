import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class CreateQuestionScreen extends ImagePanel implements ActionListener {

  // Default serialVersionUID
  private static final long serialVersionUID = 1L;
  
  // Controller to change screens
  private Controller controller;
  
  // Game instance to execute functions
  private RunGame game;
  
  private Button create;
  private Button cancel;
  
  // Question information storage
  private JTextField questionInput;
  private JTextField answerInput;
  private JTextField correctAnswerInput;
  private JTextField moveForwardInput;
  private JTextField moveBackwardInput;
  
  //TODO JButton createQuestions;
  
  public CreateQuestionScreen(Controller controller, RunGame game) {    
    // Call parent constructor to set image as background
    super(new File("resources/start_bg.jpg"));
    setLayout(new GridBagLayout());
    
    // Assign variables
    this.controller = controller;
    this.game = game;
    
    // Create JPanel to hold all other components
    JPanel backing = new JPanel();
    backing.setPreferredSize(new Dimension(400, 600));
    backing.setBackground(Color.WHITE);
    backing.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(Color.BLACK, 3),
        BorderFactory.createLineBorder(Color.WHITE, 10)));
    backing.setLayout(new BorderLayout(30, 30));

    // Text with instructions
    JTextPane enterInfoText = new JTextPane();
    enterInfoText.setEditable(false);
    enterInfoText.setFont(new Font("Century Gothic", Font.BOLD, 22));
    
    // Set text of save game
    enterInfoText.setText("Enter infromation to create a new question.");
    
    // Add text to north of holder panel
    backing.add(enterInfoText, BorderLayout.NORTH);
    
    // Create a JPanel to hold all the text
    JPanel questionInfoPane = new JPanel();
    questionInfoPane.setOpaque(false);
    questionInfoPane.setLayout(new GridLayout(0, 1, 5, 5));
    
    // START OF QUESTION INFORMATION
    
    // Text with instructions
    JTextPane questionInformation = new JTextPane();
    questionInformation.setEditable(false);
    questionInformation.setFont(new Font("Century Gothic", Font.PLAIN, 16));
    questionInformation.setText("Question text:");
    
    // Input file name
    questionInput = new JTextField(20);
    questionInput.setDocument(new JTextFieldLimit(100));
    questionInfoPane.add(questionInput);
    
    // Text with instructions
    JTextPane answerInformation = new JTextPane();
    answerInformation.setEditable(false);
    answerInformation.setFont(new Font("Century Gothic", Font.PLAIN, 16));
    answerInformation.setText("Enter each answer on a new line:");
    
    // Input file name
    answerInput = new JTextField(20);
    answerInput.setDocument(new JTextFieldLimit(100));
    questionInfoPane.add(answerInput);
    
    // Text with instructions
    JTextPane correctAnswerInformation = new JTextPane();
    correctAnswerInformation.setEditable(false);
    correctAnswerInformation.setFont(new Font("Century Gothic", Font.PLAIN, 16));
    correctAnswerInformation.setText("Enter the line number of each correct answer, "
        + "separated by a comma (,) :");
    
    // Input file name
    correctAnswerInput = new JTextField(20);
    correctAnswerInput.setDocument(new JTextFieldLimit(100));
    questionInfoPane.add(correctAnswerInput);
    
    // Text with instructions
    JTextPane moveForwardInformation = new JTextPane();
    moveForwardInformation.setEditable(false);
    moveForwardInformation.setFont(new Font("Century Gothic", Font.PLAIN, 16));
    moveForwardInformation.setText("Question text:");
    
    // Input file name
    moveForwardInput = new JTextField(20);
    moveForwardInput.setDocument(new JTextFieldLimit(100));
    questionInfoPane.add(moveForwardInput);
    
    // Text with instructions
    JTextPane moveBackwardInformation = new JTextPane();
    moveBackwardInformation.setEditable(false);
    moveBackwardInformation.setFont(new Font("Century Gothic", Font.PLAIN, 16));
    moveBackwardInformation.setText("Question text:");
    
    // Input file name
    moveBackwardInput = new JTextField(20);
    moveBackwardInput.setDocument(new JTextFieldLimit(100));
    questionInfoPane.add(moveBackwardInput);
    
    
    // END OF QUESTION INFORMATION
    
    // Turn the games panel into a scrollable panel
    JScrollPane scrollPane = new JScrollPane(questionInfoPane);
    scrollPane.setBackground(Color.WHITE);
    
    // Add scroll panel to holder panel
    backing.add(scrollPane, BorderLayout.CENTER);
    
    // Button holder panel
    JPanel buttonHolder = new JPanel();
    buttonHolder.setOpaque(false);
    buttonHolder.setLayout(new GridLayout(1, 3, 5, 5));
    
    // Load game button
    create = new Button("Create");
    create.setCustomBackground(Color.LIGHT_GRAY);
    create.addActionListener(this);
    buttonHolder.add(create);
    
    // Delete save button
    cancel = new Button("Cancel");
    cancel.setCustomBackground(Color.LIGHT_GRAY);
    cancel.addActionListener(this);
    buttonHolder.add(cancel);
    
    // Add button panel to GUI panel
    backing.add(buttonHolder, BorderLayout.SOUTH);
    
    // Add contents panel to screen
    add(backing);
  }
  
  // Button action handling
  public  void actionPerformed(ActionEvent event) {
    
    // Once we find the right button
    if (event.getSource().equals(create)) {
      
    }
  }
}