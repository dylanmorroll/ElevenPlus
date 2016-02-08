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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class ChooseCounter extends ImagePanel implements ActionListener {

  // Default serialVersionUID
  private static final long serialVersionUID = 1L;
  
  // Controller to change screens
  private Controller controller;
  
  // Game instance to execute functions
  private RunGame game;
  
  // JButton for each character
  private JButton char1;
  private JButton char2;
  private JButton char3;
  private JButton char4;

  // Button to start game
  private Button playGame;
  
  // Instructions text
  JTextPane chooseCharText;
  
  // Array to represent which character has been chose, index is player number
  List<Integer> charactersPicked = new ArrayList<Integer>();
  
  public ChooseCounter(Controller controller, RunGame game) {    
    // Call parent constructor to set image as background
    super(new File("resources/start_bg.jpg"));
    setLayout(new GridBagLayout());
    
    // Assign variables
    this.controller = controller;
    this.game = game;
    
    // Create JPanel to hold GUI elements
    JPanel contentsPanel = new JPanel();
    contentsPanel.setPreferredSize(new Dimension(300,400));
    contentsPanel.setOpaque(false);
    contentsPanel.setLayout(new BorderLayout(5, 5));

    // Text both with instructions
    chooseCharText = new JTextPane();
    chooseCharText.setBorder(new LineBorder(Color.BLACK, 2));
    chooseCharText.setEditable(false);
    chooseCharText.setFont(new Font("Century Gothic", Font.BOLD, 22));
    chooseCharText.setPreferredSize(new Dimension(300, 100));
    
    StyledDocument doc = chooseCharText.getStyledDocument();
    SimpleAttributeSet centre = new SimpleAttributeSet();
    StyleConstants.setAlignment(centre, StyleConstants.ALIGN_CENTER);
    doc.setParagraphAttributes(0, doc.getLength(), centre, false);
    
    contentsPanel.add(chooseCharText, BorderLayout.NORTH);
    chooseCharText.setText("Player 1 choose your character:");
    
    // JPanel to hold character buttons
    JPanel characterChoice = new JPanel();
    characterChoice.setOpaque(false);
    characterChoice.setLayout(new GridLayout(2, 2, 0, 0));
    
    // Select character buttons
    char1 = new JButton();
    char1.setBackground(Color.WHITE);
    char1.setBorder(new LineBorder(Color.BLACK, 2));
    char1.setIcon(new ImageIcon(controller.getCharImages()[0]));
    char1.addActionListener(this);
    characterChoice.add(char1);
    
    char2 = new JButton();
    char2.setBackground(Color.WHITE);
    char2.setBorder(new LineBorder(Color.BLACK, 2));
    char2.setIcon(new ImageIcon(controller.getCharImages()[1]));
    char2.addActionListener(this);
    characterChoice.add(char2);
    
    char3 = new JButton();
    char3.setBackground(Color.WHITE);
    char3.setBorder(new LineBorder(Color.BLACK, 2));
    char3.setIcon(new ImageIcon(controller.getCharImages()[2]));
    char3.addActionListener(this);
    characterChoice.add(char3);
    
    char4 = new JButton();
    char4.setBackground(Color.WHITE);
    char4.setBorder(new LineBorder(Color.BLACK, 2));
    char4.setIcon(new ImageIcon(controller.getCharImages()[3]));
    char4.addActionListener(this);
    characterChoice.add(char4);
    
    contentsPanel.add(characterChoice, BorderLayout.CENTER);
    
    // Start game button
    playGame = new Button("Play game!");
    playGame.setBorder(new LineBorder(Color.BLACK, 3));
    playGame.setBackground(Color.LIGHT_GRAY);
    playGame.setEnabled(false);
    playGame.setPreferredSize(new Dimension(300, 75));
    playGame.addActionListener(this);
    contentsPanel.add(playGame, BorderLayout.SOUTH);
    
    // Add contents panel to screen
    add(contentsPanel);
  }
  
  private void nextPlayerChooseCounter() {
    playGame.setBackground(Color.WHITE);
    playGame.setEnabled(true);
    
    if (charactersPicked.size() == 4) {
      chooseCharText.setText("You have reached the max number of players, start the game!");
    
    } else {
      chooseCharText.setText("You can now start the game! Or player "
          + (charactersPicked.size() + 1) + " can choose their character");
    }
  }
  
  // Button action handling
  public  void actionPerformed(ActionEvent event) {
    
    // If button is a character, assign character to that player and start game
    if (event.getSource() == char1) {
      charactersPicked.add(1);
      char1.setVisible(false);
      nextPlayerChooseCounter();
    
    } else if (event.getSource() == char2) {
      charactersPicked.add(2);
      char2.setVisible(false);
      nextPlayerChooseCounter();
    
    } else if (event.getSource() == char3) {
      charactersPicked.add(3);
      char3.setVisible(false);
      nextPlayerChooseCounter();
    
    } else if (event.getSource() == char4) {
      charactersPicked.add(4);
      char4.setVisible(false);
      nextPlayerChooseCounter();
    
    // Run controller method to start game if there's at least one player
    } else if (event.getSource() == playGame && charactersPicked.size() > 0) {
      game.setUpPlayers(charactersPicked);
      controller.playGame();
    }
    
    
  }
  
}