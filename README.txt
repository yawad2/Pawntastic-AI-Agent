Summary:
This project was done in Java. 3 different games can be played:
- 4x4 game, using full minimax
- 5x5 game, using full minimax
- 8x8 game, using minimax with alpha/beta pruning and cutoff depth as specified by the user
There were no issues with the AI agent during our testing. At the beginning, the user is asked to provide a size 
for the board (i.e. 4, 5, or 8). If the size is 8, the user is asked to provide a cutoff depth for the minimax 
algorithm. Then, the user is asked to choose a team (0 -> white, 1 -> black) and the game starts. The user is asked to
provide valid moves only and the program will ask for a new move if the entered move is invalid. The program 
prints useful information about the AI agent (the move it performed, time taken to generate the move, the number of states
visited). The board is printed after each move from either players. If the game reaches a terminal state, the program 
prints who the winner is (if there is one) or if it's a tie/draw


How to run the code:
1. In your terminal, navigate to the project folder and compile the file named "Main.java" by typing "javac Main.java"
2. Run the java file by typing: "java Main"
3. Enter input as prompted by the program to start the game


Credits:
Yousra Awad, email: yawad2@u.rochester.edu
Hesham Elshafey, email: helshafe@u.rochester.edu
