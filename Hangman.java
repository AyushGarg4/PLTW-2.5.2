import java.util.Scanner;

class Hangman {  
  private Player player1;
  private Player player2;

  private Board board;
  
  private boolean ended;

  public Hangman() {
    restart();
  }

  public void restart() {
    this.player1 = new Player(getInput("Player 1's Name > "));
    this.player2 = new Player(getInput("Player 2's Name > "));

    this.board = new Board();
    this.ended = false;
  }

  private String getInput(String prompt) {
    Scanner scanner = new Scanner(System.in);
    System.out.print(prompt);
    return scanner.nextLine();
  }


  public void play() {
    int turn = 0;
    while (true) {
      doTurn(turn);
      if (ended) break;
      turn = (turn + 1) % 2;
    }

    System.out.println("Player 1 had score: " + player1.getScore());
    System.out.println("Player 2 had score: " + player2.getScore());
  }

  public void doTurn(int turn) {
    // Get player
    Player player;
    if (turn == 0) player = player1;
    else           player = player2;

    System.out.println();
    System.out.println("Player " + (turn + 1) + "'s Turn.");
    System.out.println("Current state: " + board.getState());

    // Guess letter or guess phrase?
    boolean isLetter;
    while (true) {
      String typeGuess = getInput("Guess letter or whole phrase? (l/p) > ").toLowerCase();
      if (typeGuess.equals("l") || typeGuess.equals("p")) {
        isLetter = typeGuess.equals("l");
        break;
      }
    }
    
    if (isLetter) {
      // Guess letter!
      char guess = getInput("Guess your letter > ").charAt(0);
      int score = guessLetter(guess);
      
      player.addScore(score);

      // Check if word has been completed
      if (board.getPhrase().equals(board.getState())) {
        System.out.println("Correct! You won.");
        ended = true;
      }
      
      return;
    }

    // Guess phrase!
    String guess = getInput("Guess the phrase > ");
    boolean isCorrect = guessPhrase(guess);

    // Display whether they were correct or not and end game if needed.
    if (isCorrect) {
      System.out.println("Correct! You won.");
      player.addScore(10);
      ended = true;
      return;
    }

    System.out.println("Incorrect guess!");
  }

  public int guessLetter(char letter) {
    StringBuilder state = new StringBuilder(board.getState());
    int numLettersGot = 0;
    
    for (int i = 0; i < state.length(); i++) {
      if (board.getPhrase().charAt(i) == letter) {
        state.setCharAt(i, letter);
        numLettersGot++;
      } 
    }

    board.setState(state.toString());

    return numLettersGot;
  }

  public boolean guessPhrase(String text) {
    if (board.getPhrase().equals(text)) {
      board.setState(text);
      return true;
    }
    
    return false;
  }

  public Board getBoard() {
    return board;
  }

  public boolean hasEnded() {
    return ended; 
  }

  public Player getPlayer1() {
     return player1;
  }
  
  public Player getPlayer2() {
     return player2;
  }
}