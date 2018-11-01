import java.util.*;

/**
 * This is the AiPlayer class. It simulates a minimax player for the max connect four game. 
 *
 * @author balaji
 * 
 */

public class AiPlayer {

    public int depth_level;
    public GameBoard gameBoardShallow;

    /**
     * The constructor essentially does nothing except instantiate an AiPlayer object.
     */
    public AiPlayer(int depth, GameBoard currentPlay) {
        this.depth_level = depth;
        this.gameBoardShallow = currentPlay;
    }

    /**
     * This method makes the decision to make a move for the computer using 
     * the min and max value from the below given two functions.
     */
    public int findBestPlay(GameBoard currentPlay) throws CloneNotSupportedException {
        int playChoice = maxconnect4.INVALID;
        if (currentPlay.getCurrentTurn() == maxconnect4.PLAYER1) {
            int v = Integer.MAX_VALUE;
            for (int i = 0; i < GameBoard.Columns; i++) {
                if (currentPlay.isLegal(i)) {
                    GameBoard nextMoveBoard = new GameBoard(currentPlay.getGameBoard());
                    nextMoveBoard.playPiece(i);
                    int value = ComputeMaxUtilValue(nextMoveBoard, depth_level, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    if (v > value) {
                        playChoice = i;
                        v = value;
                    }
                }
            }
        } else {
            int v = Integer.MIN_VALUE;
            for (int i = 0; i < GameBoard.Columns; i++) {
                if (currentPlay.isLegal(i)) {
                    GameBoard nextMoveBoard = new GameBoard(currentPlay.getGameBoard());
                    nextMoveBoard.playPiece(i);
                    int value = ComputeMinUtilValue(nextMoveBoard, depth_level, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    if (v < value) {
                        playChoice = i;
                        v = value;
                    }
                }
            }
        }
        return playChoice;
    }

    /**
     * This method calculates the min value.
     */

    private int ComputeMinUtilValue(GameBoard gameBoard, int depth_level, int alpha_value, int beta_value)
        throws CloneNotSupportedException {
        // TODO Auto-generated method stub
        if (!gameBoard.isBoardFull() && depth_level > 0) {
            int v = Integer.MAX_VALUE;
            for (int i = 0; i < GameBoard.Columns; i++) {
                if (gameBoard.isLegal(i)) {
                    GameBoard board4NextMove = new GameBoard(gameBoard.getGameBoard());
                    board4NextMove.playPiece(i);
                    int value = ComputeMaxUtilValue(board4NextMove, depth_level - 1, alpha_value, beta_value);
                    if (v > value) {
                        v = value;
                    }
                    if (v <= alpha_value) {
                        return v;
                    }
                    if (beta_value > v) {
                        beta_value = v;
                    }
                }
            }
            return v;
        } else {
            // this is a terminal state
            return gameBoard.getScore(maxconnect4.PLAYER2) - gameBoard.getScore(maxconnect4.PLAYER1);
        }
    }

    /**
     * This method calculates the max value.
     */

    private int ComputeMaxUtilValue(GameBoard gameBoard, int depth_level, int alpha_value, int beta_value)
        throws CloneNotSupportedException {
        // TODO Auto-generated method stub
        if (!gameBoard.isBoardFull() && depth_level > 0) {
            int v = Integer.MIN_VALUE;
            for (int i = 0; i < GameBoard.Columns; i++) {
                if (gameBoard.isLegal(i)) {
                    GameBoard board4NextMove = new GameBoard(gameBoard.getGameBoard());
                    board4NextMove.playPiece(i);
                    int value = ComputeMinUtilValue(board4NextMove, depth_level - 1, alpha_value, beta_value);
                    if (v < value) {
                        v = value;
                    }
                    if (v >= beta_value) {
                        return v;
                    }
                    if (alpha_value < v) {
                        alpha_value = v;
                    }
                }
            }
            return v;
        } else {
            // this is a terminal state
            return gameBoard.getScore(maxconnect4.PLAYER2) - gameBoard.getScore(maxconnect4.PLAYER1);
        }
    }

}
