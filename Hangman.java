import java.util.Scanner;

class Hangman {  
  // Initialized attributes
  private Player player1;
  private Player player2;

  private Board board;
  private boolean ended;

  public Hangman() {
    restart();
  }
  // Restart function (also used for initialization)
  public void restart() {
    this.player1 = new Player(getInput("Player 1's Name > "));
    this.player2 = new Player(getInput("Player 2's Name > "));

    this.board = new Board();
    this.ended = false;
  }

  // Helper method to ask the user a prompt and read in the next line.
  private String getInput(String prompt) {
    Scanner scanner = new Scanner(System.in);
    System.out.print(prompt);
    return scanner.nextLine();
  }


  // Main loop for the game; switches turns and calls the doTurn function to execute the actual game.
  public void play() {
    int turn = 0;
    while (true) {
      boolean guessCorrectly = doTurn(turn);
      if (ended) break;
      if (!guessCorrectly)
        turn = (turn + 1) % 2;
    }

    System.out.println(player1.getName() + " had score: " + player1.getScore());
    System.out.println(player2.getName() + " had score: " + player2.getScore());
  }

  // Runs the game for the given player, where 0 is player1, and any other value is player2.
  public boolean doTurn(int turn) {
    // Get player
    Player player;
    if (turn == 0) player = player1;
    else           player = player2;

    System.out.println();
    System.out.println(player.getPlayer() + "'s Turn.");
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
      char guess = (getInput("Guess your letter > ") + " ").charAt(0);
      int score = guessLetter(guess);
      
      player.addScore(score);

      // Check if word has been completed
      if (board.getPhrase().equals(board.getState())) {
        System.out.println("Correct! You won.");
        ended = true;
      }
      
      return score > 0;
    }

    // Guess phrase!
    String guess = getInput("Guess the phrase > ");
    boolean isCorrect = guessPhrase(guess);

    // Display whether they were correct or not and end game if needed.
    if (isCorrect) {
      System.out.println("Correct! You won.");
      player.addScore(10);
      ended = true;
      return true;
    }

    System.out.println("Incorrect guess!");

    return false;
  }

  // Attempts to guess a letter in the phrase. Returns 0 if the letter is not in the phrase or if the letter was already guessed; otherwise, returns the number of letters in the phrase that was that letter.
  public int guessLetter(char letter) {
    StringBuilder state = new StringBuilder(board.getState());
    int numLettersGot = 0;
    
    for (int i = 0; i < state.length(); i++) {
      if (board.getPhrase().charAt(i) == letter && board.getState().charAt(i) == '_') {
        state.setCharAt(i, letter);
        numLettersGot++;
      } 
    }

    board.setState(state.toString());

    return numLettersGot;
  }

  // Attempts to guess the entire phrase. Returns true if the guess was correct, and false otherwise.
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