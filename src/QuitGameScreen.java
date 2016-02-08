import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;

public class QuitGameScreen extends ImagePanel implements ActionListener {

  // Default serialVersionUID
  private static final long serialVersionUID = 1L;
  
  // Controller to change screens
  private Controller controller;
  
  // Buttons
  private Button quit;
  private Button cancel;
  
  public QuitGameScreen(Controller controller) {    
    // Call parent constructor to set image as background
    super(new File("resources/start_bg.jpg"));
    setLayout(new GridBagLayout());
    
    // Assign variables
    this.controller = controller;
    
    // Create JPanel to serve as background
    JPanel backing = new JPanel();
    backing.setPreferredSize(new Dimension(400, 200));
    backing.setBackground(Color.WHITE);
    backing.setBorder(new LineBorder(Color.BLACK, 5));
    backing.setLayout(new GridBagLayout());
    
    // Create JPanel to hold GUI elements
    JPanel holder = new JPanel();
    holder.setPreferredSize(new Dimension(400, 150));
    holder.setLayout(new GridLayout(0, 1, 3, 3));
    holder.setOpaque(false);

    // Text with instructions
    JTextPane quitGameText = new JTextPane();
    quitGameText.setEditable(false);
    quitGameText.setFont(new Font("Century Gothic", Font.BOLD, 22));
    
    // Set text of instructions
    quitGameText.setText("Are you sure you want to quit the game?");
    
    // Add text to text panel
    holder.add(quitGameText);
    
    // Button holder panel
    JPanel buttonHolder = new JPanel();
    buttonHolder.setOpaque(false);
    buttonHolder.setPreferredSize(new Dimension(400, 150));
    buttonHolder.setLayout(new GridLayout(0, 2, 5, 5));
    
    // Save game button
    quit = new Button("Quit Game");
    quit.addActionListener(this);
    buttonHolder.add(quit);
    
    // Cancel save button
    cancel = new Button("Cancel");
    cancel.addActionListener(this);
    buttonHolder.add(cancel);
    
    // Add button panel to GUI panel
    holder.add(buttonHolder);
    // Add GUI panel to backing panel
    backing.add(holder);
    // Add contents panel to screen
    add(backing);
  }
  
  // Button action handling
  public  void actionPerformed(ActionEvent event) {
    // If a new game is started
    if (event.getSource() == quit) {
      controller.completeQuit();
    
    } else if (event.getSource() == cancel) {
      controller.cancelQuit();
    }
  }
}