package lu.uni.ejb;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@SessionScoped
@Named("wordleGame")
public class WordleGame implements Serializable {

  private String secretWord;
  private List<String> guesses = new ArrayList<>();
  private List<String> answers = new ArrayList<>();
  private List<String> letters = new ArrayList<>(Collections.nCopies(5, null));
  private List<List<LetterColor>> guessedLetters = new ArrayList<>();
  private boolean isWin = false;
  private boolean isLost = false;
  private boolean isGameOver = false;

  public WordleGame() {
    generateSecretWord();
    this.loadGuesses("guesses.txt");
    System.out.println("Number of total guesses is " + this.guesses.size());
    System.out.println("Secret word is :" + this.secretWord);
  }

  public boolean getIsGameOver() {
    return isGameOver;
  }

  public boolean getIsWin() {
    return isWin;
  }

  public boolean getIsLost() {
    return isLost;
  }

  public List<String> getLetters() {
    return letters;
  }

  public void setLetters(List<String> letters) {
    this.letters = letters;
  }

  public String getSecretWord() {
    return this.secretWord;
  }

  public void setSecretWord(String secretWord) {
    this.secretWord = secretWord;
  }

  public List<List<LetterColor>> getGuessedLetters() {
    return guessedLetters;
  }

  public String chooseAction() {
    System.out.println("Submit");
    if (validateForm(letters)) {
      compareWithSecretWord(letters);
    }

    return "";
  }

  public String restartGame() {
    System.out.println("Restart Game");
    generateSecretWord();
    resetForm();
    return "";
  }

  public void resetForm() {
    this.guessedLetters = new ArrayList<>();
    this.letters = new ArrayList<>(Collections.nCopies(5, null));
    this.isGameOver = false;
    this.isWin = false;
    this.isLost = false;
  }

  public void compareWithSecretWord(List<String> letters) {
    List<LetterColor> letterColors = new ArrayList<>();
    for (int i = 0; i < letters.size(); i++) {
      if (letters.get(i).equals(String.valueOf(secretWord.charAt(i)))) {
        letterColors.add(new LetterColor(letters.get(i), Color.green));
      } else if (secretWord.contains(letters.get(i))) {
        letterColors.add(new LetterColor(letters.get(i), Color.yellow));
      } else {
        letterColors.add(new LetterColor(letters.get(i), Color.grey));
      }
    }
    guessedLetters.add(letterColors);
    if (guessedLetters.size() > 5) {
      isLost = true;
      isGameOver = true;
    }
    if (String.join("", letters).equals(secretWord)) {
      System.out.println("Secret Word found");
      isWin = true;
      isGameOver = true;
    } else {
      this.letters = new ArrayList<>(Collections.nCopies(5, ""));
    }
  }

  public boolean validateForm(List<String> letters) {
    return validateLetter(letters) && validateWord(letters);
  }

  public boolean validateLetter(List<String> letters) {
    for (String letter : letters) {
      if (letter.matches("[0-9]")) {
        FacesContext
          .getCurrentInstance()
          .addMessage("", new FacesMessage("Please only use letters!"));
        return false;
      } else if (letter.isEmpty()) {
        FacesContext
          .getCurrentInstance()
          .addMessage("", new FacesMessage("Empty letters are not accepted!"));
        return false;
      }
    }
    return true;
  }

  public boolean validateWord(List<String> letters) {
    String word = String.join("", letters);
    if (guesses.contains(word)) {
      return true;
    } else {
      FacesContext
        .getCurrentInstance()
        .addMessage("", new FacesMessage("Word not found."));
      return false;
    }
  }

  private void loadGuesses(String fileName) {
    ClassLoader classLoader = getClass().getClassLoader();
    InputStream inputStream = classLoader.getResourceAsStream(fileName);

    if (inputStream == null) {
      throw new IllegalArgumentException("file not found! " + fileName);
    } else {
      try (
        BufferedReader reader = new BufferedReader(
          new InputStreamReader(inputStream, StandardCharsets.UTF_8)
        )
      ) {
        String line;
        while ((line = reader.readLine()) != null) {
          guesses.add(line);
        }
      } catch (IOException e) {
        e.printStackTrace();
        throw new RuntimeException("Error while reading file " + fileName, e);
      }
    }
  }

  public void generateSecretWord() {
    this.secretWord = this.getRandomWordFromResource("answers.txt");
    System.out.println("Secret word is " + this.secretWord);
  }

  private String getRandomWordFromResource(String fileName) {
    ClassLoader classLoader = getClass().getClassLoader();
    InputStream inputStream = classLoader.getResourceAsStream(fileName);

    if (inputStream == null) {
      throw new IllegalArgumentException("file not found! " + fileName);
    } else {
      try (
        BufferedReader reader = new BufferedReader(
          new InputStreamReader(inputStream, StandardCharsets.UTF_8)
        )
      ) {
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
          lines.add(line);
        }
        Random random = new Random();
        return lines.get(random.nextInt(lines.size()));
      } catch (IOException e) {
        e.printStackTrace();
        throw new RuntimeException("Error while reading file " + fileName, e);
      }
    }
  }

  private static void printInputStream(InputStream is) {
    try (
      InputStreamReader streamReader = new InputStreamReader(
        is,
        StandardCharsets.UTF_8
      );
      BufferedReader reader = new BufferedReader(streamReader)
    ) {
      String line;
      while ((line = reader.readLine()) != null) {
        System.out.println(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
