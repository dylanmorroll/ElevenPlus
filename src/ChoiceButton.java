import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.CompoundBorder;

public class ChoiceButton extends JButton {

  // Default serialVersionUID
  private static final long serialVersionUID = 1L;
  
  // Boolean to show choice has been selected
  private boolean selected = false;
  
  // Integer to show which choice the button represents
  int choiceNum;
  
  // Border instance with custom border
  CompoundBorder customBorder = BorderFactory.createCompoundBorder(
      BorderFactory.createLineBorder(Color.BLACK, 5),
      BorderFactory.createLineBorder(Color.WHITE, 3));
  
  // Defer constructors to JButton
  public ChoiceButton(int choiceNum) {
    super();
    this.choiceNum = choiceNum;
    setBackground();
  }
  
  public ChoiceButton(String temp, int choiceNum) {
    super(temp);
    this.choiceNum = choiceNum;
    setBackground();
  }
  
  // Set custom background and font of button
  private void setBackground() {
    setBackground(Color.WHITE);
    setBorder(customBorder);
    setFont(new Font("Century Gothic",1,16));
  }
  
  // Change the outer border of the button
  public void changeBackground(Color colour) {
    customBorder = BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(colour, 5),
        BorderFactory.createLineBorder(Color.WHITE, 3));
    setBorder(customBorder);
  }
  
  // Get boolean of whether or not the button has been selected
  public boolean getSelected() {
    return selected;
  }
  
  public int getChoiceNum() {
    return choiceNum;
  }
  
  // Set border to red and assign selected to true
  public void selectChoice() {
    changeBackground(Color.RED);
    selected = true;
  }
  
  // Set border to black and assign selected to false
  public void deselectChoice() {
    changeBackground(Color.BLACK);
    selected = false;
  }
}
