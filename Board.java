import java.io.*;

class Board {
  private String[] phrases;

  private String phrase;
  private String state;
  
  public Board() {
    loadPhrases();
    setRandomPhrase();
  }

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
  
  private String generateRandomPhrase() {
    String[] arr = loadPhrases();
    int len = arr.length; 
    int rand = (int)(Math.random() * len);
    return arr[rand];
  }

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