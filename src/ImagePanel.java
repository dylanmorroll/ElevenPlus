import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {
  
  private static final long serialVersionUID = 1L;
  Image img;
  int borderThickness = 0;
  
  public ImagePanel(File imageFile) {
    // Create image file from background image
    try {
      img = ImageIO.read(imageFile);
    } catch (IOException e) {
      System.out.println("Image file for ImagePanel location is incorrect. Terminating program.");
    }
  }
  
  public ImagePanel(Image img) {
    this.img = img;
    setOpaque(false);
  }
  
  public void setBorderThickness(int borderThickness) {
    this.borderThickness = borderThickness;
  }

  @Override
  protected void paintComponent(Graphics graphics) {
    // Paint background image onto JPanel
    super.paintComponent(graphics);
    graphics.drawImage(img, 0, 0, getWidth() - (2 * borderThickness),
        getHeight() - (2 * borderThickness), this);
  }
}
