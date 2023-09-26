public class Minimax {
    int player_type;
    public Minimax(int player_type){
        this.player_type = player_type;
    }
    Move best_move;
    int state_count = 0;
    //full minimax for 4x4 and 5x5 games
    public int minimax(State state, boolean max_player,int current_player) {
        state_count++;
        if (state.is_terminal()) {
            return state.evaluate(current_player);
        }
        int best_value;
        Move firstBestMove = null;
        if(max_player){
            best_value = Integer.MIN_VALUE;
            for(Move move: state.generate_all_legal_moves(current_player)){
                State next_state = state.clone();
                next_state.current_board.move_player(current_player, move.from, move.to);
                if(next_state.isGoalState(current_player)){
                    best_move = move;
                    return Integer.MAX_VALUE;
                }
                int value = minimax(next_state, !max_player, current_player +1);
                if(value > best_value){
                    best_value = Math.max(value, best_value);
                    firstBestMove = new Move(move.from, move.to);
                }
            }
            best_move = firstBestMove;
        }else{
            best_value = Integer.MAX_VALUE;
            for(Move move: state.generate_all_legal_moves(current_player)){
                State next_state = state.clone();
                next_state.current_board.move_player(current_player, move.from, move.to);
                int value = minimax(next_state, max_player, current_player+1);
                best_value = Math.min(best_value, value);
            }
        }
        return best_value;
    }
    //minimax with alpha/beta pruning and cutoff depth for 8x8 games
    public int minimax_8x8(State state, boolean max_player,int current_player, int a, int b, int cutoff_depth, int current_depth) {
        state_count++;
        if (state.is_terminal() || current_depth == cutoff_depth) {
            return state.evaluate(current_player); // Evaluate the current game state
        }
        int best_value;
        Move firstBestMove = null;
        if(max_player){
            best_value = Integer.MIN_VALUE;
            for(Move move: state.generate_all_legal_moves(current_player)){
                State next_state = state.clone();
                next_state.current_board.move_player(current_player, move.from, move.to);
                if(next_state.isGoalState(current_player)){
                    best_move = move;
                    return Integer.MAX_VALUE;
                }
                int value = minimax_8x8(next_state, !max_player, current_player +1, a, b, cutoff_depth, current_depth+1);
                if(value > best_value){
                    best_value = Math.max(value, best_value);
                    a = Math.max(a, best_value);
                    firstBestMove = new Move(move.from, move.to);
                    if(b <= a){
                        break;
                    }
                }
            }
            best_move = firstBestMove;
        }else{
            best_value = Integer.MAX_VALUE;
            for(Move move: state.generate_all_legal_moves(current_player)){
                State next_state = state.clone();
                next_state.current_board.move_player(current_player, move.from, move.to);
                int value = minimax_8x8(next_state, max_player, current_player+1, a, b, cutoff_depth, current_depth + 1);
                best_value = Math.min(best_value, value);
                b = Math.min(b, best_value);
                if(b <= a){
                    break;
                }
            }
        }
        return best_value;
    }
    
}