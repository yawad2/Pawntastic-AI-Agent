import java.util.*;
public class State {
    Board current_board;
    public State(Board current_board){
        this.current_board = current_board;
    }
    //check if state is a goal state for the given player
    boolean isGoalState(int current_player){
        return (current_player % 2 == 0) ? isGoalForWhite() : isGoalForBlack();
    }
    //checks if state is goal for black team
    boolean isGoalForBlack(){ 
        for(Player p: current_board.black_team.values()){
            for(int i=0; i < current_board.size;i++){
                StringBuilder s = new StringBuilder();
                char c = (char) ('a' + i);
                s.append(c);
                s.append('1');
                if(p.player_location.equals(s.toString())){
                    return true;
                }
            }
        }
        return false;
    }
    //checks if state is goal for white
    boolean isGoalForWhite(){
        for(Player p : current_board.white_team.values()){
            for(int i=0; i < current_board.size;i++){
                StringBuilder s = new StringBuilder();
                char c = (char) ('a' + i);
                s.append(c);
                s.append(current_board.size);
                if(p.player_location.equals(s.toString())){
                    return true;
                }
            }
        }
        return false;
    }
    //checks if state is a tie (i.e either one of the players runs out of moves/pawns)
    boolean is_tie(){
        boolean white_out_of_moves = true;
        boolean black_out_of_moves = true;
        //out of pawns
        if(current_board.white_team.size() == 0 || current_board.black_team.size() == 0){
            return true;
        }
        for(Player p : current_board.white_team.values()){
            //there's a player in white team that can make a move
            if(current_board.generate_legal_moves(p).size() > 0){
                white_out_of_moves = false;
            }
        }
        for(Player p : current_board.black_team.values()){
            //there's a player in black team that can make a move
            if(current_board.generate_legal_moves(p).size() > 0){
                black_out_of_moves = false;
            }
        }
        //either teams is out of moves, hence the game ends
        return (white_out_of_moves || black_out_of_moves) ? true : false;
    }
    //checks if state is terminal(either it's a goal state for one of the players or it's a tie)
    boolean is_terminal(){
        if(isGoalForWhite()|| isGoalForBlack() || is_tie()){
            return true;
        }
        return false;
    }
    //evaluates the state based on the sum of the players' distances from a goal state
    int evaluate(int curr_player){
        //greater distance (score) is worse
        int score = 0;
        //if white is playing
        if(curr_player % 2 == 0){
            for(Player p: current_board.white_team.values()){
                //score is sum of distance of all players from goal locations
                score += p.player_location_x;
                if(isGoalForWhite()){
                    score -= 500;
                }
            }
        }else{
            //if black is playing
            for(Player p: current_board.black_team.values()){
                //score is sum of distance of all players from goal locations
                score += (current_board.size - p.player_location_x - 1);
                if(isGoalForBlack()){
                    score -= 500;
                }
            }
        }
        //multiplied by -1 for clarity (this way the higher the score of a state, the more desirable it is)
        return -1 * score;
    }
    //makes a deep copy of the state
    public State clone(){
        State copy = new State(current_board.deepClone());
        return copy;
    }
    //generates all legal moves for a given player
    List<Move> generate_all_legal_moves(int curr_player){
        List<Move> result = new ArrayList<Move>();
        //white is playing
        if(curr_player % 2 == 0){
            for(Player p: current_board.white_team.values()){
                result.addAll(current_board.generate_legal_moves(p));
            }
        }
        //black is playing
        else{ 
            for(Player p: current_board.black_team.values()){
                result.addAll(current_board.generate_legal_moves(p));
            }
        }
        return result;
    }

}
