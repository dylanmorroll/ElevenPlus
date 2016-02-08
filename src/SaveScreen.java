import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class SaveScreen extends ImagePanel implements ActionListener {

  // Default serialVersionUID
  private static final long serialVersionUID = 1L;
  
  // Controller to change screens
  private Controller controller;
  
  // Game instance to execute functions
  private RunGame game;
  
  // Name of save game
  private JTextField saveGameName;
  
  // Text pane displaying error message
  private JTextPane fileExists;
  
  private Button save;
  private Button cancel;
  
  public SaveScreen(Controller controller, RunGame game) {    
    // Call parent constructor to set image as background
    super(new File("resources/start_bg.jpg"));
    setLayout(new GridBagLayout());
    
    // Assign variables
    this.controller = controller;
    this.game = game;
    
    // Create JPanel to serve as background
    JPanel backing = new JPanel();
    backing.setPreferredSize(new Dimension(400, 300));
    backing.setBackground(Color.WHITE);
    backing.setBorder(new LineBorder(Color.BLACK, 5));
    backing.setLayout(new GridBagLayout());
    
    // Create JPanel to hold GUI elements
    JPanel holder = new JPanel();
    holder.setPreferredSize(new Dimension(400, 100));
    holder.setLayout(new GridLayout(0, 1, 3, 3));
    holder.setOpaque(false);

    // Text with instructions
    JTextPane saveGameText = new JTextPane();
    saveGameText.setEditable(false);
    saveGameText.setFont(new Font("Century Gothic", Font.BOLD, 22));
    
    // Text if file already exists
    fileExists = new JTextPane();
    fileExists.setEditable(false);
    fileExists.setFont(new Font("Century Gothic", Font.PLAIN, 16));
    
    // Centring text
    StyledDocument doc = saveGameText.getStyledDocument();
    StyledDocument doc2 = fileExists.getStyledDocument();
    SimpleAttributeSet centre = new SimpleAttributeSet();
    StyleConstants.setAlignment(centre, StyleConstants.ALIGN_CENTER);
    doc.setParagraphAttributes(0, doc.getLength(), centre, false);
    doc2.setParagraphAttributes(0, doc2.getLength(), centre, false);
    
    // Set text of save game
    saveGameText.setText("Enter your save game name.");
    
    // Add text to text panel
    holder.add(saveGameText);
    holder.add(fileExists);
    
    // Input file name
    saveGameName = new JTextField(20);
    saveGameName.setDocument(new JTextFieldLimit(20));
    holder.add(saveGameName);
    
    // Button holder panel
    JPanel buttonHolder = new JPanel();
    buttonHolder.setOpaque(false);
    buttonHolder.setPreferredSize(new Dimension(400, 50));
    buttonHolder.setLayout(new GridLayout(0, 2, 5, 5));
    
    // Save game button
    save = new Button("Save Game");
    save.addActionListener(this);
    buttonHolder.add(save);
    
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
    if (event.getSource() == save) {
      
      // Create a reference to save file
      File saveFile = new File(controller.getSaveDirectory() + saveGameName.getText() + ".txt");
      
      // If the already exists, display the error message
      if (saveFile.isFile()) {
        fileExists.setText("Sorry, that save already exists. Please choose another.");
      
      } else {
        game.saveGame(saveFile);
        controller.completeSave();
      }
      
    
    } else if (event.getSource() == cancel) {
      controller.cancelSave();
    }
  }
}