import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class WinGameScreen extends ImagePanel implements ActionListener {

  // Default serialVersionUID
  private static final long serialVersionUID = 1L;
  
  // Controller to change screens
  private Controller controller;
  
  // Button to return to the main screen
  Button returnToStart;
  
  public WinGameScreen(Controller controller, RunGame game) {    
    // Call parent constructor to set image as background
    super(new File("resources/start_bg.jpg"));
    setLayout(new GridBagLayout());
    
    // Assign variables
    this.controller = controller;
    
    // Create JPanel to hold GUI elements
    JPanel backing = new JPanel();
    backing.setPreferredSize(new Dimension(400, 200));
    backing.setBackground(Color.WHITE);
    backing.setBorder(new LineBorder(Color.BLACK, 5));
    backing.setLayout(new GridBagLayout());
    
    JPanel holder = new JPanel();
    holder.setOpaque(false);
    holder.setPreferredSize(new Dimension(400, 150));
    holder.setLayout(new BorderLayout(10, 10));

    // Text both with instructions
    JTextPane wonGameText = new JTextPane();
    wonGameText.setEditable(false);
    wonGameText.setFont(new Font("Century Gothic", Font.BOLD, 22));
    
    StyledDocument doc = wonGameText.getStyledDocument();
    SimpleAttributeSet centre = new SimpleAttributeSet();
    StyleConstants.setAlignment(centre, StyleConstants.ALIGN_CENTER);
    doc.setParagraphAttributes(0, doc.getLength(), centre, false);
    
    wonGameText.setText("Congratulations Player "
        + game.getLastPlayer().getPlayerNumber() + 1 + " you have won the game!");
    holder.add(wonGameText, BorderLayout.CENTER);
    
    // Return to main menu button
    returnToStart = new Button("Main Menu");
    returnToStart.addActionListener(this);
    holder.add(returnToStart, BorderLayout.SOUTH);
    
    // Add text and buttons to the backing panel
    backing.add(holder);
    // Add contents panel to screen
    add(backing);
  }
  
  // Button action handling
  public  void actionPerformed(ActionEvent event) {
    
    // If a new game is started
    if (event.getSource() == returnToStart) {
      controller.endGame();
    }
  }
}