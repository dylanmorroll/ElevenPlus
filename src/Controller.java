import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Controller extends JFrame {
  
  // Default serialVersionUID
  private static final long serialVersionUID = 1L;
  
  // Path where files are saved
  String saveDirectory = "resources" + File.separator
      + "saves" + File.separator;
  
  // Each of the screens, represented as JPanels
  private StartScreen startScreen;
  private ChooseCounter chooseCounter;
  private MainScreen mainScreen;
  private WinGameScreen winGameScreen;
  private SaveScreen saveScreen;
  private LoadScreen loadScreen;
  private CreateQuestionScreen createQuestionScreen;
  private QuitGameScreen quitGameScreen;
  
  // Character images
  private Image[] charImages = new Image[4];
  
  // Content pane of screen
  private Container contain;
  
  /* Size of the windows, size calculated by getting a resolution for the
   * main game board that was a multiple of 32 x 32 for a tileset
   * Original JFrame resolution was 1280 x 720, which resulted in a board of size
   * 964 x 603, 954 x 593 without the border, so by working backwards a grid of
   * size 30 x 18 would be the closest resolution to what I already had
   * 30 x 32 = 960, 18 x 32 = 576, 960 x 576
   * So I added 6 to the width and subtracted 17 from the height
   * To give the game background the right size
   * 960 x 576 = 552960 pixels
   *     60 x 36 = 2160 tiles at (16 x 16)
   *     30 x 18 540 tiles at (32 x 32)
   */
  private Dimension size = new Dimension(1286, 703);
  
  // RunGame instance to pass to classes, holds state of game
  private RunGame game = new RunGame();
  
  public Controller() {
        
    // Set title of window
    super("ElevenPlus");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(size);
    setVisible(true);
    
    // Load character images
    try {
      charImages[0] = ImageIO.read(new File("resources/char1.png"));
      charImages[1] = ImageIO.read(new File("resources/char2.png"));
      charImages[2] = ImageIO.read(new File("resources/char3.png"));
      charImages[3] = ImageIO.read(new File("resources/char4.png"));
      
    } catch (IOException ex) {
      System.out.println("Character image files not found.");
    }
    
    // Get and set container reference
    contain = getContentPane();
    
    // Create start screen JPanel
    startScreen = new StartScreen(this);
    startScreen.setSize(size);

    // Add JPanel to current container
    contain.add(startScreen);

    // Switch views
    startScreen.setVisible(true);
  }
  
  public void startGame() {
    
    // Create JPanel for choosing counter
    chooseCounter = new ChooseCounter(this, game);
    chooseCounter.setSize(size);

    // Add JPanel to current container
    contain.add(chooseCounter);

    // Switch views
    startScreen.setVisible(false);
    chooseCounter.setVisible(true);
    
  }
  
  public void loadGame() {
    
    // Create JPanel for choosing counter
    loadScreen = new LoadScreen(this, game);
    loadScreen.setSize(size);

    // Add JPanel to current container
    contain.add(loadScreen);

    // Switch views
    startScreen.setVisible(false);
    loadScreen.setVisible(true);
    
  }
  
  public void cancelLoad() {
    
    // Switch views
    loadScreen.removeAll();
    loadScreen.setVisible(false);
    startScreen.setVisible(true);
    
  }  
  
  public void playGame() {

    // Create JPanel for choosing counter
    mainScreen = new MainScreen(this, game);
    mainScreen.setSize(size);
    
    // Create JPanel to save game, so it can be switched to without being redrawn
    saveScreen = new SaveScreen(this, game);
    saveScreen.setSize(size);
    
    // Add JPanels to current container
    contain.add(mainScreen);
    
    // Switch views
    chooseCounter.removeAll();
    chooseCounter.setVisible(false);
    saveScreen.setVisible(false);
    mainScreen.setVisible(true);
    
  }
  
  public void playGameFromLoad() {

    // Create JPanel for choosing counter
    mainScreen = new MainScreen(this, game);
    mainScreen.setSize(size);
    
    // Create JPanel to save game, so it can be switched to without being redrawn
    saveScreen = new SaveScreen(this, game);
    saveScreen.setSize(size);
    
    // Add JPanels to current container
    contain.add(mainScreen);
    
    // Switch views
    loadScreen.removeAll();
    loadScreen.setVisible(false);
    saveScreen.setVisible(false);
    mainScreen.setVisible(true);
    
  }
  
  public void winGame() {

    // Create JPanel for choosing counter
    winGameScreen = new WinGameScreen(this, game);
    winGameScreen.setSize(size);
    
    // Add JPanel to current container
    contain.add(winGameScreen);
    
    // Switch views
    mainScreen.removeAll();
    saveScreen.removeAll();
    mainScreen.setVisible(false);
    winGameScreen.setVisible(true);
    
  }

  public void endGame() {

    // Reset game state
    game = new RunGame();
    
    // Switch views
    winGameScreen.removeAll();
    winGameScreen.setVisible(false);
    startScreen.setVisible(true);
    
  }
  
  public void saveGame() {

    // Add JPanel to current container
    contain.add(saveScreen);
    
    // Switch views
    mainScreen.setVisible(false);
    saveScreen.setVisible(true);
    
  }
  
  public void cancelSave() {
    
    // Switch views
    saveScreen.setVisible(false);
    mainScreen.setVisible(true);
    
  }  
  
  public void completeSave() {
    
    // Reset game state
    game = new RunGame();
    
    // Switch views
    mainScreen.removeAll();
    saveScreen.removeAll();
    saveScreen.setVisible(false);
    startScreen.setVisible(true);
    
  }
  
  public void quitGame() {
    
    // Create JPanel for choosing counter
    quitGameScreen = new QuitGameScreen(this);
    quitGameScreen.setSize(size);
    
    // Add JPanel to current container
    contain.add(quitGameScreen);
    
    // Switch views
    mainScreen.setVisible(false);
    quitGameScreen.setVisible(true);
    
  }
  
  public void cancelQuit() {
    
    // Switch views
    quitGameScreen.removeAll();
    quitGameScreen.setVisible(false);
    mainScreen.setVisible(true);
    
  }
  
  public void completeQuit() {
    
    // Reset game state
    game = new RunGame();
    
    // Switch views
    mainScreen.removeAll();
    quitGameScreen.removeAll();
    quitGameScreen.setVisible(false);
    startScreen.setVisible(true);
    
  }
  
  public void createQuestion() {
    
  }
  
  public Image[] getCharImages() {
    return charImages;
  }
  
  public String getSaveDirectory() {
    return saveDirectory;
  }
  
  public static void main(String[] args) {
    
    //Schedule a job for the event-dispatching thread:
    //creating and showing this application's GUI.
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        new Controller();
      }
    }); 
  }
}
