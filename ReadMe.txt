NAME: BALAJI PAIYUR MOHAN
UTA ID: 1001576836
PROGRAMMING LANGUAGE USED: JAVA
STRUCTURE OF CODE:

1. AiPlayer class
a. ComputeMinUtilValue() computes the minimum utility value
b. ComputeMaxUtilValue() computes the maximum utility value
c. findBestPlay() makes the best move based on the above two functions

2. GameBoard class
a. getscore gets the current score of the board and send it to Maxconnect4 to print the score
b. getCurrentTurn  gets the current turn
c. getGameBoard takes the current state of the board 
d. isLegal takes the current column value and checks if it is valid move
e. printGameBoard prints the current state 

3. maxconnect4 class
a. HumanMove function takes the input and makes the human player to make the next move
b. OneMovePlayByComputer computer make move for 1 move game
c. display_Board_Score prints current board state and the score
d. InteractivePlayByComputer here computer makes a move in interactive game mode
e. displayResult displays the final score and the result

Steps for Compilation:
Step 1: javac maxconnect4.java AiPlayer.java GameBoard.java
      
		
Step 2: Interactive mode :
        syntax: java maxconnect4 interactive [input_file] [computer-next/human-next] [depth]  
        sample: java maxconnect4 interactive input.txt computer-next 8
		
		
Step 3: One-Mode mode:
        syntax: java maxconnect4 one-move [input_file] [output_file] [depth]
		sample: java maxconnect4 one-move input.txt output.txt 0
		
		
Step 4: Getting the  execution time:
         time java maxconnect4 one-move input1.txt output.txt 0
EXECUTION TIME:

depth 0:real    0m0.088s
        user    0m0.037s
        sys     0m0.007s

depth 1: real    0m0.087s
         user    0m0.035s
         sys     0m0.005s

depth 2: real    0m0.103s
         user    0m0.082s
         sys     0m0.014s

depth 3: real    0m0.113s
         user    0m0.095s
         sys     0m0.013s
		 
depth 4: real    0m0.126s
         user    0m0.105s
         sys     0m0.012s

depth 5: real    0m0.0104s
         user    0m0.080s
         sys     0m0.011s

depth 6: real    0m0.312s
         user    0m0.279s
         sys     0m0.022s

depth 7: real    0m0.483s
         user    0m0.506s
         sys     0m0.026s

depth 8: real    0m0.453s
         user    0m0.472s
         sys     0m0.046s

depth 9: real    0m0.472s
         user    0m0.675s
         sys     0m0.038s

depth 10: real    0m1.598s
          user    0m1.687s
          sys     0m0.128s