import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.CompoundBorder;

public class Button extends JButton {

  // Default serialVersionUID
  private static final long serialVersionUID = 1L;
  
  // Border instance with custom border
  // Two borders to create outer black border, then inner white border
  // to force a minimum 3 pixel gap between text and black border
  Color innerColour = Color.WHITE;
  int innerSize = 3;
  Color outerColour = Color.BLACK;
  int outerSize = 3;
  
  // Generate buttons with custom background and border
  public Button() {
    super();
    setBorder();
    setCustomBackground(Color.WHITE);
    setFont(new Font("Century Gothic",1,20));
  }
  
  public Button(String temp) {
    super(temp);
    setBorder();
    setCustomBackground(Color.WHITE);
    setFont(new Font("Century Gothic",1,20));
  }
  
  private void setBorder() {
    CompoundBorder customBorder = BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(outerColour, outerSize),
        BorderFactory.createLineBorder(innerColour, innerSize));
    setBorder(customBorder);
  }
  
  public void setCustomBackground(Color colour) {
    setBackground(colour);
    changeInnerBorder(colour);
  }
  
  public void changeOuterBorder(Color colour) {
    outerColour = colour;
    setBorder();
  }
  
  public void changeOuterBorder(Color colour, int size) {
    outerColour = colour;
    outerSize = size;
    setBorder();
  }
  
  public void changeInnerBorder(Color colour) {
    innerColour = colour;
    setBorder();
  }
  
  public void changeInnerBorder(Color colour, int size) {
    innerColour = colour;
    innerSize = size;
    setBorder();
  }
}
