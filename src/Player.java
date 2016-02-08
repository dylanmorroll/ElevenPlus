
public class Player {
  
  //ATTRIBUTES
  private int position;
  private int playerNumber;
  private int character;
  
  //CONSTRUCTOR
  public Player(int playerNumber, int character) {
    this.playerNumber = playerNumber;
    this.character = character;
    this.position = 0;
  }
  
  // Constructor to set position as well, for loading games
  public Player(int playerNumber, int character, int position) {
    this.playerNumber = playerNumber;
    this.character = character;
    this.position = position;
  }
  
  //GETTERS & SETTERS
  public int getPosition() {
    return position;
  }
  
  public void setPosition(int position) {
    this.position = position;
  }
  
  public int getPlayerNumber() {
    return playerNumber;
  }
  
  public void setPlayerNumber(int playerNumber) {
    this.playerNumber = playerNumber;
  }
  
  public int getCharacter() {
    return character;
  }
  
  public void setCharacter(int character) {
    this.character = character;
  }
  
}
