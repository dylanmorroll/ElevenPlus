import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JPanel;

public class StartScreen extends ImagePanel implements ActionListener {

  // Default serialVersionUID
  private static final long serialVersionUID = 1L;
  
  // Controller to change screens
  private Controller controller;
  
  // Buttons for game options
  Button startGame;
  Button loadGame;
  //TODO JButton createQuestions;
  
  public StartScreen(Controller controller) {    
    // Call parent constructor to set image as background
    super(new File("resources/start_bg.jpg"));
    setLayout(new GridBagLayout());
    
    // Assign variables
    this.controller = controller;
    
    // Create JPanel to hold GUI elements
    JPanel contentsPanel = new JPanel();
    contentsPanel.setPreferredSize(new Dimension(300,400));
    contentsPanel.setOpaque(false);
    contentsPanel.setLayout(new GridLayout(2, 1, 10, 10)); //TODO 3 rows for creating questions

    // Start game button
    startGame = new Button("Start A New Game");
    startGame.setPreferredSize(new Dimension(300, 75));
    startGame.addActionListener(this);
    contentsPanel.add(startGame);
    
    // Load game button
    loadGame = new Button("Load Game");
    loadGame.setPreferredSize(new Dimension(300, 75));
    loadGame.addActionListener(this);
    contentsPanel.add(loadGame);
    
    // Add contents panel to screen
    add(contentsPanel);
  }
  
  // Button action handling
  public  void actionPerformed(ActionEvent event) {
    
    // If a new game is started
    if (event.getSource() == startGame) {
      controller.startGame();
    
    // If a game is loaded
    } else if (event.getSource() == loadGame) {
      controller.loadGame();
    }
  }
}