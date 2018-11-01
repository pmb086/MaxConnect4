import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 * @author balaji
 * 
 */

public class GameBoard implements Cloneable {
    // class fields
    private int[][] boardPosition;
    private int pieceCount;
    private int currentTurn;
    private maxconnect4.PLAYER_TYPE first_turn;
    private maxconnect4.MODE game_mode;

    public static final int Columns = 7;
    public static final int Rows = 6;
    public static final int TOTAL_PIECES = 42;

    /**
     * This constructor creates a GameBoard object based on the input file given as an argument. It reads data from the input
     * file and provides lines that, when uncommented, will display exactly what has been read in from the input file. You can
     * find these lines by looking for
     */
    public GameBoard(String inputFile) {
        this.boardPosition = new int[Rows][Columns];
        this.pieceCount = 0;
        int counter = 0;
        BufferedReader input = null;
        String gameData = null;

        // open the input file
        try {
            input = new BufferedReader(new FileReader(inputFile));
        } catch (IOException e) {
            System.out.println("\nInput file is not found!\nPlease enter a valid input file" + "\n");
            e.printStackTrace();
        }

        // read the game data from the input file
        for (int i = 0; i < Rows; i++) {
            try {
                gameData = input.readLine();

                // read each piece from the input file
                for (int j = 0; j < Columns; j++) {

                    this.boardPosition[i][j] = gameData.charAt(counter++) - 48;

                    // sanity check
                    if (!((this.boardPosition[i][j] == 0) || (this.boardPosition[i][j] == maxconnect4.PLAYER1) || (this.boardPosition[i][j] == maxconnect4.PLAYER2))) {
                        System.out.println("\nThere is a problem encountered! Only 0 or 1 or 2 should be in the board\n");
                        this.exit_function(0);
                    }

                    if (this.boardPosition[i][j] > 0) {
                        this.pieceCount++;
                    }
                }
            } catch (Exception e) {
                System.out.println("\nThe input file could not be read!\n");
                e.printStackTrace();
                this.exit_function(0);
            }

            // reset the counter
            counter = 0;

        } // end for loop

        // read one more line to get the next players turn
        try {
            gameData = input.readLine();
        } catch (Exception e) {
            System.out.println("\nThe next turn cannot be read!\n");
            e.printStackTrace();
        }

        this.currentTurn = gameData.charAt(0) - 48;

        // make sure the turn corresponds to the number of pcs played already
        if (!((this.currentTurn == maxconnect4.PLAYER1) || (this.currentTurn == maxconnect4.PLAYER2))) {
            System.out.println("Problems!\n The current turn play should be " + "1 or a 2!");
            this.exit_function(0);
        } else if (this.getCurrentTurn() != this.currentTurn) {
            System.out.println("\n the current turn read does not " + "correspond to the number of pieces played");
            this.exit_function(0);
        }
    } // end GameBoard( String )

    /**
     * This method set piece value for both human & computer(1 or 2). 
     * 
     */
    public void setPieceValue() {
        if ((this.currentTurn == maxconnect4.PLAYER1 && first_turn == maxconnect4.PLAYER_TYPE.COMPUTER)
            || (this.currentTurn == maxconnect4.PLAYER2 && first_turn == maxconnect4.PLAYER_TYPE.HUMAN)) {
            maxconnect4.COMPUTER_PIECE = maxconnect4.PLAYER1;
            maxconnect4.HUMAN_PIECE = maxconnect4.PLAYER2;
        } else {
            maxconnect4.HUMAN_PIECE = maxconnect4.PLAYER1;
            maxconnect4.COMPUTER_PIECE = maxconnect4.PLAYER2;
        }
        
        System.out.println("Human plays as : " + maxconnect4.HUMAN_PIECE + " , Computer plays as : " + maxconnect4.COMPUTER_PIECE);
        
    }
    
    
    /**
     * This mathod creats clone (Shallow copy) for object of GameBoard class. (It's recommended to use clone() method instead of
     * copy constructor as it's process faster)
     * 
     */
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * This constructor creates a GameBoard object from another double indexed array.
     */
    public GameBoard(int masterGame[][]) {

        this.boardPosition = new int[Rows][Columns];
        this.pieceCount = 0;

        for (int i = 0; i < Rows; i++) {
            for (int j = 0; j < Columns; j++) {
                this.boardPosition[i][j] = masterGame[i][j];

                if (this.boardPosition[i][j] > 0) {
                    this.pieceCount++;
                }
            }
        }
    } // end GameBoard( int[][] )

    /**
     * this method returns the score for the player given as an argument. it checks horizontally, vertically, and each direction
     * diagonally. currently, it uses for loops, but i'm sure that it can be made more efficient.
     */
    public int getScore(int player) {
        // reset the scores
        int playerScore = 0;

        // check horizontally
        for (int i = 0; i < Rows; i++) {
            for (int j = 0; j < 4; j++) {
                if ((this.boardPosition[i][j] == player) && (this.boardPosition[i][j + 1] == player)
                    && (this.boardPosition[i][j + 2] == player) && (this.boardPosition[i][j + 3] == player)) {
                    playerScore++;
                }
            }
        } // end horizontal

        // check vertically
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < Columns; j++) {
                if ((this.boardPosition[i][j] == player) && (this.boardPosition[i + 1][j] == player)
                    && (this.boardPosition[i + 2][j] == player) && (this.boardPosition[i + 3][j] == player)) {
                    playerScore++;
                }
            }
        } // end verticle

        // check diagonally - backs lash
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if ((this.boardPosition[i][j] == player) && (this.boardPosition[i + 1][j + 1] == player)
                    && (this.boardPosition[i + 2][j + 2] == player) && (this.boardPosition[i + 3][j + 3] == player)) {
                    playerScore++;
                }
            }
        }

        // check diagonally - forward slash
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if ((this.boardPosition[i + 3][j] == player) && (this.boardPosition[i + 2][j + 1] == player)
                    && (this.boardPosition[i + 1][j + 2] == player) && (this.boardPosition[i][j + 3] == player)) {
                    playerScore++;
                }
            }
        }// end player score check

        return playerScore;
    } // end getScore

    public int getUnBlockedThrees(int player) {
        /**
         * similar to getScore but only requires three in a row and the last one to be open
         */
        // reset the scores
        int playerScore = 0;

        // check horizontally
        for (int i = 0; i < Rows; i++) {
            for (int j = 0; j < 4; j++) {
                if ((this.boardPosition[i][j] == player) && (this.boardPosition[i][j + 1] == player)
                    && (this.boardPosition[i][j + 2] == player)
                    && (this.boardPosition[i][j + 3] == player || this.boardPosition[i][j + 3] == 0)) {
                    playerScore++;
                }
            }
        } // end horizontal

        // check vertically
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < Columns; j++) {
                if ((this.boardPosition[i][j] == player) && (this.boardPosition[i + 1][j] == player)
                    && (this.boardPosition[i + 2][j] == player)
                    && (this.boardPosition[i + 3][j] == player || this.boardPosition[i + 3][j] == 0)) {
                    playerScore++;
                }
            }
        } // end verticle

        // check diagonally - backs lash 
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if ((this.boardPosition[i][j] == player) && (this.boardPosition[i + 1][j + 1] == player)
                    && (this.boardPosition[i + 2][j + 2] == player)
                    && (this.boardPosition[i + 3][j + 3] == player || this.boardPosition[i + 3][j + 3] == 0)) {
                    playerScore++;
                }
            }
        }

        // check diagonally - forward slash 
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if ((this.boardPosition[i + 3][j] == player) && (this.boardPosition[i + 2][j + 1] == player)
                    && (this.boardPosition[i + 1][j + 2] == player)
                    && (this.boardPosition[i][j + 3] == player || this.boardPosition[i][j + 3] == 0)) {
                    playerScore++;
                }
            }
        }// end player score check

        return playerScore;
    }

    /**
     * the method gets the current turn
     */
    public int getCurrentTurn() {
        return (this.pieceCount % 2) + 1;
    } // end getCurrentTurn

    /**
     * this method returns the number of pieces that have been played on the board
     */
    public int getPieceCount() {
        return this.pieceCount;
    }

    /**
     * this method returns the whole gameboard as a dual indexed array
     */
    public int[][] getGameBoard() {
        return this.boardPosition;
    }

    /**
     * a method that determines if a play is valid or not. It checks to see if the column is within bounds. If the column is
     * within bounds, and the column is not full, then the play is valid.
     */
    public boolean isLegal(int column) {

        if (!(column >= 0 && column < 7)) {
            // check the column bounds
            return false;
        } else if (this.boardPosition[0][column] > 0) {
            // check if column is full
            return false;
        } else {
            // column is NOT full and the column is within bounds
            return true;
        }
    }

    /**
     * This method checks whether Gameboard is full or not 
     * 
     */

    boolean isBoardFull() {
        return (this.getPieceCount() >= GameBoard.TOTAL_PIECES);
    }

    /**
     * This method plays a piece on the game board.
     */
    public boolean playPiece(int column) {

        // check if the column choice is a valid play
        if (!this.isLegal(column)) {
            return false;
        } else {

            // starting at the bottom of the board,
            // place the piece into the first empty spot
            for (int i = 5; i >= 0; i--) {
                if (this.boardPosition[i][column] == 0) {
                    this.boardPosition[i][column] = getCurrentTurn();
                    this.pieceCount++;
                    return true;
                }
            }
            // the pgm shouldn't get here
            System.out.println("Something went wrong with playPiece()");

            return false;
        }
    } // end playPiece

    /**
     * this method removes the top piece from the game board
     */
    public void removePiece(int column) {

        // starting looking at the top of the game board,
        // and remove the top piece
        for (int i = 0; i < Rows; i++) {
            if (this.boardPosition[i][column] > 0) {
                this.boardPosition[i][column] = 0;
                this.pieceCount--;

                break;
            }
        }
    } // end remove piece

    /**
     * this method prints the GameBoard to the screen in a nice, pretty, readable format
     */
    public void printGameBoard() {
        System.out.println(" -----------------");

        for (int i = 0; i < Rows; i++) {
            System.out.print(" | ");
            for (int j = 0; j < Columns; j++) {
                System.out.print(this.boardPosition[i][j] + " ");
            }

            System.out.println("| ");
        }

        System.out.println(" -----------------");
    } // end printGameBoard

    /**
     * this method prints the GameBoard to an output file to be used for inspection or by another running of the application
     */
    public void printGameBoardToFile(String outputFile) {
        try {
            BufferedWriter output = new BufferedWriter(new FileWriter(outputFile));

            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) {
                    output.write(this.boardPosition[i][j] + 48);
                }
                output.write("\r\n");
            }

            // write the current turn
            output.write(this.getCurrentTurn() + "\r\n");
            output.close();

        } catch (IOException e) {
            System.out.println("\nProblem writing to the output file!\n" + "Try again.");
            e.printStackTrace();
        }
    } // end printGameBoardToFile()

    private void exit_function(int value) {
        System.out.println("exiting from GameBoard.java!\n\n");
        System.exit(value);
    }
 
    //set first turn
    public void setFirstTurn(maxconnect4.PLAYER_TYPE turn) {
        // TODO Auto-generated method stub
        first_turn = turn;
        setPieceValue();
    }

    public maxconnect4.PLAYER_TYPE getFirstTurn() {
        // TODO Auto-generated method stub
        return first_turn;
    }
    //set game mode (interactive or one-move)
    public void setGameMode(maxconnect4.MODE mode) {
        // TODO Auto-generated method stub
        game_mode = mode;
    }

    public maxconnect4.MODE getGameMode() {
        // TODO Auto-generated method stub
        return game_mode;
    }

} // end GameBoard class
