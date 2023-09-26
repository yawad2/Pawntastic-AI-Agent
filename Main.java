import java.util.*;
public class Main {
    public static void main(String [] args)
    {
        System.out.println("Please enter a board size");
        Scanner sc = new Scanner(System.in);
        int size = sc.nextInt();
        int depth = 0; //cutoff depth specified by the user for 8x8 games
        if(size == 8){
            System.out.println("Please enter a cutoff depth for the search");
            depth = sc.nextInt();
        }
        System.out.println("To play white, enter 0. To play black, enter 1");
        int user_team = sc.nextInt();
        int AI_team = (user_team == 0) ? 1: 0;
        Minimax AI_agent = new Minimax(AI_team);
        int current_player = 0; //white playes first
        Board game = new Board(size);
        game.intializeBoard();
        State current_state = new State(game);
        current_state.current_board.printBoard();
        while(!current_state.is_terminal()){
            //user's turn
            if(user_team == current_player % 2){
                System.out.println("Enter a move (e.g. 'a2 a3'):");
                Scanner stringScanner = new Scanner(System.in);
                String move1 = stringScanner.nextLine();
                String from = move1.substring(0, 2);
                String to = move1.substring(3);
                //if user types an invalid move, ask them to try again
                while(!current_state.current_board.is_valid_move(current_player, from, to)){
                    System.out.println("Invalid move, try entering another move: ");
                    move1 = stringScanner.nextLine();
                    from = move1.substring(0, 2);
                    to = move1.substring(3);
                }
                //perform the user's move if it's valid and print board
                current_state.current_board.move_player(current_player, from, to);
                current_state.current_board.printBoard();
            }
            //AI's turn
            else{
                System.out.println("I'm thinking...");
                long startTime;
                long endTime;
                //if it's an 8x8 game, we call minimax with alpha/beta pruning and cutoff depth
                if(size == 8){
                    startTime = System.currentTimeMillis();
                    AI_agent.minimax_8x8(current_state, true, AI_team, Integer.MIN_VALUE, Integer.MAX_VALUE, depth, 0);
                    endTime = System.currentTimeMillis();
                }
                //we call full minimax for 4x4 and 5x5 games
                else{
                    startTime = System.currentTimeMillis();
                    AI_agent.minimax(current_state, true, AI_team);
                    endTime = System.currentTimeMillis();
                }
                //calculates time taken by the AI agent to make a move
                double timeTaken = (double) (endTime - startTime) / 1000.0;
                System.out.println("Time taken : " + timeTaken + " sec");
                Move move = AI_agent.best_move;
                String from1 = move.from;
                String to1 = move.to;
                System.out.println("AI agent: " + from1 + " " + to1);
                System.out.println("Visited states: " + AI_agent.state_count);
                AI_agent.state_count = 0;
                current_state.current_board.move_player(current_player, from1, to1);
                current_state.current_board.printBoard();
            }
            //switch player
            current_player += 1;
        }
        //is it a goal state for the player who made the last move?
        if(current_state.isGoalState(current_player - 1)){
            String winner = ((current_player - 1) % 2 == 0) ? "White" : "Black";
            System.out.println(winner + " won!");
        }
        //if it's a terminal state but not a goal for the player, then it's a tie
        else{
            System.out.println("It's a tie!");
        }
    }
}
