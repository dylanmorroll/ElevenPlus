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
import javax.swing.JTextPane;

public class LoadScreen extends ImagePanel implements ActionListener {

  // Default serialVersionUID
  private static final long serialVersionUID = 1L;
  
  // Controller to change screens
  private Controller controller;
  
  // Game instance to execute functions
  private RunGame game;
  
  private Button load;
  private Button delete;
  private Button back;
  
  // Is there a game selected
  private boolean isGameSelected;
  // The button selected
  private ChooseGameButton gameSelected;
  // Name of the file
  private String selectedGameName;
  
  // List holding the button for each game
  private List<ChooseGameButton> gamesButtonList;
  
  public LoadScreen(Controller controller, RunGame game) {    
    // Call parent constructor to set image as background
    super(new File("resources/start_bg.jpg"));
    setLayout(new GridBagLayout());
    
    // Assign variables
    this.controller = controller;
    this.game = game;
    
    // Create JPanel to hold all other components
    JPanel backing = new JPanel();
    backing.setPreferredSize(new Dimension(400, 500));
    backing.setBackground(Color.WHITE);
    backing.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(Color.BLACK, 3),
        BorderFactory.createLineBorder(Color.WHITE, 10)));
    backing.setLayout(new BorderLayout(30, 30));

    // Text with instructions
    JTextPane loadGameText = new JTextPane();
    loadGameText.setEditable(false);
    loadGameText.setFont(new Font("Century Gothic", Font.BOLD, 22));
    
    // Set information text
    loadGameText.setText("Select your game.");
    
    // Add text to north of holder panel
    backing.add(loadGameText, BorderLayout.NORTH);
    
    // Create a JPanel to hold all the games
    JPanel gamesHolder = new JPanel();
    gamesHolder.setOpaque(false);
    gamesHolder.setLayout(new GridLayout(0, 1, 5, 5));
    
    // For each text file create a button
    File directory = new File(controller.getSaveDirectory());
    
    // Create list to hold all game buttons
    gamesButtonList = new ArrayList<ChooseGameButton>();
    
    // For each file in the saves directory
    for (String currentfile : directory.list()) {
      
      // If the file is a save
      if (new File(controller.getSaveDirectory() + currentfile).isFile()) {
        
        // Create a button for it
        String name = currentfile.substring(0, currentfile.length() - 4);
        ChooseGameButton gameButton = new ChooseGameButton(name);
        gameButton.addActionListener(this);
        
        // Add it to list of buttons and panel
        gamesButtonList.add(gameButton);
        gamesHolder.add(gameButton);
      }
    }
        
    // Turn the games panel into a scrollable panel
    JScrollPane scrollPane = new JScrollPane(gamesHolder);
    scrollPane.setBackground(Color.WHITE);
    
    // Add scroll panel to holder panel
    backing.add(scrollPane, BorderLayout.CENTER);
    
    // Button holder panel
    JPanel buttonHolder = new JPanel();
    buttonHolder.setOpaque(false);
    buttonHolder.setLayout(new GridLayout(1, 3, 5, 5));
    
    // Load game button
    load = new Button("Load");
    load.setCustomBackground(Color.LIGHT_GRAY);
    load.addActionListener(this);
    buttonHolder.add(load);
    
    // Delete save button
    delete = new Button("Delete");
    delete.setCustomBackground(Color.LIGHT_GRAY);
    delete.addActionListener(this);
    buttonHolder.add(delete);
    
    // Return to main menu button
    back = new Button("Back");
    back.addActionListener(this);
    buttonHolder.add(back);
    
    // Add button panel to GUI panel
    backing.add(buttonHolder, BorderLayout.SOUTH);
    
    // Add contents panel to screen
    add(backing);
  }
  
  // Button action handling
  public  void actionPerformed(ActionEvent event) {
    
    // Loop through buttons representing each game
    for (ChooseGameButton button : gamesButtonList) {
      
      // Once we find the right button
      if (event.getSource().equals(button)) {
        
        // If the button has not been selected
        if (button.getSelected() == false) {
          
          // If there isn't a game selected
          if (isGameSelected == false) {
                      
            // Select it (turn border red) and get the save game name
            button.selectChoice();
            isGameSelected = true;
            gameSelected = button;
            selectedGameName = ((ChooseGameButton) event.getSource()).getText();
          }
        
        // Else if the button has been selected
        } else {
          
          // Deselect it
          button.deselectChoice();
          isGameSelected = false;
        }
      }
    }
    
    
    
    // Go back to main menu
    if (event.getSource() == back) {
      controller.cancelLoad();
      
    
    // If a game is selected
    } else if (isGameSelected) {
      
      // Set the load and delete buttons to white, to show they can be selected
      load.setCustomBackground(Color.WHITE);
      delete.setCustomBackground(Color.WHITE);
      
      // Load the game
      if (event.getSource() == load) {
        game.loadGame(controller.getSaveDirectory() + selectedGameName + ".txt");
        controller.playGameFromLoad();
        
      // Delete the file and hide the button
      } else if (event.getSource() == delete) {
        game.deleteGame(controller.getSaveDirectory() + selectedGameName + ".txt");
        
        isGameSelected = false;
        gameSelected.deselectChoice();
        gameSelected.setVisible(false);
      }
    
    // Set the load and delete buttons to grey, to show they can be selected
    } else {
      load.setCustomBackground(Color.LIGHT_GRAY);
      delete.setCustomBackground(Color.LIGHT_GRAY);
    }
  }
}