package lu.uni.ejb;

public class LetterColor {

  private String letter;
  private Color color;

  public LetterColor(String letter, Color color) {
    this.letter = letter;
    this.color = color;
  }

  public String getLetter() {
    return letter;
  }

  public Color getColor() {
    return color;
  }

  public void setLetter(String letter) {
    this.letter = letter;
  }

  public void setColor(Color color) {
    this.color = color;
  }
}
