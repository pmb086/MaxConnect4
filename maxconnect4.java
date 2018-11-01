import java.util.Scanner;

/**
 *
 * @author balaji
 * 
 */

public class maxconnect4 {

    public static Scanner in = null;
    public static GameBoard currentPlay = null;
    public static AiPlayer aiPlayer = null;

    public static final int PLAYER1 = 1;
    public static final int PLAYER2 = 2;
    public static int HUMAN_PIECE;
    public static int COMPUTER_PIECE;
    public static int INVALID = 99;
    public static final String FILEPATH_PREFIX = "../";
    public static final String COMPUTER_OPT_FILE = "computer.txt";
    public static final String HUMAN_OPT_FILE = "human.txt";

    public enum MODE {
        INTERACTIVE,
        ONE_MOVE
    };

    public enum PLAYER_TYPE {
        HUMAN,
        COMPUTER
    };

    public static void main(String[] args) throws CloneNotSupportedException {
        // check for the correct number of arguments
        if (args.length != 4) {
            System.out.println("Four command-line arguments are needed:\n"
                + "Usage: java [program name] interactive [input_file] [computer-next / human-next] [depth]\n"
                + " or:  java [program name] one-move [input_file] [output_file] [depth]\n");

            exit_function(0);
        }

        // parse the input arguments
        String game_mode = args[0].toString(); // the game mode
        String inputFilePath = args[1].toString(); // the input game file
        int depthLevel = Integer.parseInt(args[3]); // the depth level of the ai search

        // create and initialize the game board
        currentPlay = new GameBoard(inputFilePath);

        // create the Ai Player
        aiPlayer = new AiPlayer(depthLevel, currentPlay);

        if (game_mode.equalsIgnoreCase("interactive")) {
            currentPlay.setGameMode(MODE.INTERACTIVE);
            if (args[2].toString().equalsIgnoreCase("computer-next") || args[2].toString().equalsIgnoreCase("C")) {
                // if it is computer next, make the computer make a move
                currentPlay.setFirstTurn(PLAYER_TYPE.COMPUTER);
                InteractivePlayByComputer();
            } else if (args[2].toString().equalsIgnoreCase("human-next") || args[2].toString().equalsIgnoreCase("H")){
                currentPlay.setFirstTurn(PLAYER_TYPE.HUMAN);
                HumanMove();
            } else {
                System.out.println("\n" + "value for 'next turn' doesn't recognized.  \n try again. \n");
                exit_function(0);
            }

            if (currentPlay.isBoardFull()) {
                System.out.println("\nI can't play.\nThe Board is Full\n\nGame Over.");
                exit_function(0);
            }

        } else if (!game_mode.equalsIgnoreCase("one-move")) {
            System.out.println("\n" + game_mode + " is an unrecognized game mode \n try again. \n");
            exit_function(0);
        } else {
            // /////////// one-move mode ///////////
            currentPlay.setGameMode(MODE.ONE_MOVE);
            String outputFileName = args[2].toString(); // the output game file
            OneMovePlayByComputer(outputFileName);
        }
    } // end of main()
    
    /**
     * This method handles computer's move for one-move mode
     */
    private static void OneMovePlayByComputer(String outputFileName) throws CloneNotSupportedException {
        // TODO Auto-generated method stub

        // variables to keep up with the game
        int playColumn = 99; // the players choice of column to play
        boolean playMade = false; // set to true once a play has been made

        System.out.print("\nmaxconnect-4 game:\n");

        System.out.print("Game state before move:\n");

        // print the current game board
        currentPlay.printGameBoard();

        // print the current scores
        System.out.println("Score: Player1 = " + currentPlay.getScore(PLAYER1) + ",\n Player2 = " + currentPlay.getScore(PLAYER2)
            + "\n ");

        if (currentPlay.isBoardFull()) {
            System.out.println("\nI can't play.\nThe Board is Full\n\nGame Over.");
            return;
        }

        // ****************** this chunk of code makes the computer play

        int current_player = currentPlay.getCurrentTurn();

        // AI play - random play
        playColumn = aiPlayer.findBestPlay(currentPlay);

        if (playColumn == INVALID) {
            System.out.println("\nI can't play.\nThe Board is Full\n\nGame Over.");
            return;
        }

        // play the piece
        currentPlay.playPiece(playColumn);

        // display the current game board
        System.out.println("move " + currentPlay.getPieceCount() + ": Player " + current_player + ", column "
            + (playColumn + 1));

        System.out.print("Game state after move:\n");

        // print the current game board
        currentPlay.printGameBoard();

        // print the current scores
        System.out.println("Score: Player-1 = " + currentPlay.getScore(PLAYER1) + ",\n Player-2 = " + currentPlay.getScore(PLAYER2)
            + "\n ");

        currentPlay.printGameBoardToFile(outputFileName);

        // ************************** end computer play

    }

    /**
     * This method handles computer's move for interactive mode
     * 
     */
    private static void InteractivePlayByComputer() throws CloneNotSupportedException {

        display_Board_Score();

        System.out.println("\n Computer's turn:\n");

        int playColumn = INVALID; // the players choice of column to play

        // AI play - random play
        playColumn = aiPlayer.findBestPlay(currentPlay);

        if (playColumn == INVALID) {
            System.out.println("\nI can't play.\nThe Board is Full\n\nGame Over.");
            return;
        }

        // play the piece
        currentPlay.playPiece(playColumn);

        System.out.println("move: " + currentPlay.getPieceCount() + " , Player: Computer , Column: " + (playColumn + 1));

        currentPlay.printGameBoardToFile(COMPUTER_OPT_FILE);

        if (currentPlay.isBoardFull()) {
            display_Board_Score();
            displayResult();
        } else {
            HumanMove();
        }
    }

    /**
     * This method prints final result for the game.
     * 
     */
    
    private static void displayResult() {
        int human_score = currentPlay.getScore(maxconnect4.HUMAN_PIECE);
        int comp_score = currentPlay.getScore(maxconnect4.COMPUTER_PIECE);
        
        System.out.println("\n Final Result:");
        if(human_score > comp_score){
            System.out.println("\n Congratulations!! You won this game."); 
        } else if (human_score < comp_score) {
            System.out.println("\n You lost!! Good luck for next game.");
        } else {
            System.out.println("\n Game is tie!!");
        }
    }

    /**
     * This method handles human's move for interactive mode.
     * 
     */
    private static void HumanMove() throws CloneNotSupportedException {
        // TODO Auto-generated method stub
        display_Board_Score();

        System.out.println("\n Human's turn:\nKindly play your move here(1-7):");

        in = new Scanner(System.in);

        int playColumn = INVALID;

        do {
            playColumn = in.nextInt();
        } while (!isLegal(playColumn));

        // play the piece
        currentPlay.playPiece(playColumn - 1);

        System.out.println("move: " + currentPlay.getPieceCount() + " , Player: Human , Column: " + playColumn);
        
        currentPlay.printGameBoardToFile(HUMAN_OPT_FILE);

        if (currentPlay.isBoardFull()) {
            display_Board_Score();
            displayResult();
        } else {
            InteractivePlayByComputer();
        }
    }

    private static boolean isLegal(int playColumn) {
        if (currentPlay.isLegal(playColumn - 1)) {
            return true;
        }
        System.out.println("Oops!!...Invalid column , Kindly enter column value between 1 to 7.");
        return false;
    }

    /**
     * This method displays current board state and score of each player.
     * 
     */
    public static void display_Board_Score() {
        System.out.print("Game state :\n");

        // print the current game board
        currentPlay.printGameBoard();

        // print the current scores
        System.out.println("Score: Player-1 = " + currentPlay.getScore(PLAYER1) + ", Player-2 = " + currentPlay.getScore(PLAYER2)
            + "\n ");
    }

    /**
     * This method is used when to exit the program prematurly.
     * 
     * @param value an integer that is returned to the system when the program exits.
     */
    private static void exit_function(int value) {
        System.out.println("exiting from MaxConnectFour.java!\n\n");
        System.exit(value);
    }
} // end of class connectFou
