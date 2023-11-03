import java.io.*;

class Board {
  private String[] phrases;

  private String phrase;
  private String state;
  
  public Board() {
    loadPhrases();
    setRandomPhrase();
  }

  // Helper method to load all phrases from the phrases.txt file.
  private String[] loadPhrases() {
    String contents = "";
    try {
      BufferedReader input = new BufferedReader(new FileReader("phrases.txt"));

      String line;
      while ((line = input.readLine()) != null) {
          contents += line + "\n";
      }

      input.close();
    } catch (Exception e) {
      System.out.println("Unable to locate or read phrases.txt");
    }

    phrases = contents.split("\n");
    return phrases;
  }

  // Helper method to return a random phrase.
  private String generateRandomPhrase() {    
    int len = phrases.length; 
    int rand = (int)(Math.random() * len);
    
    return phrases[rand];
  }

  // Creates the inital state, with _ to signify any non-space character.
  private String generateInitialState() {
    StringBuilder builder = new StringBuilder();

    for (char c : this.phrase.toCharArray()) {
      if (c == ' ') builder.append(' ');
      else          builder.append('_');
    }

    return builder.toString();
  }

  public String getPhrase() {
     return phrase;
  }
  
  public void setPhrase(String phrase) {
     this.phrase = phrase;
     this.state = generateInitialState(); 
  }

  public void setRandomPhrase() {
    setPhrase(generateRandomPhrase());
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }
}