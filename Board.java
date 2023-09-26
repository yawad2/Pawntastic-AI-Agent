import java.util.*;
public class Board {
    int size; //size of the board (i.e 4 for 4x4, etc)
    HashMap<String,Tile> tiles; //mapping tiles to the corresponding string locations on the board
    HashMap<String,Player> white_team; //mapping white players to the corresponding string locations on the board
    HashMap<String,Player> black_team; //mapping black players to the corresponding string locations on the board
    public Board(int size){
        this.size = size;
        tiles = new HashMap<>();
        white_team = new HashMap<String,Player>();
        black_team = new HashMap<String,Player>();
    }
    //initial setup of the game
    public void intializeBoard(){
        setup_player();
        setup_tile();
    }
    //setting up players to their starting positions
    public void setup_player()
    {
        String player_location = "a2";
        StringBuilder temp = new StringBuilder(player_location);
        int temp_x = 1;
        int temp_y = 0;
        for(int x = 0; x < 2; x++)
        {
            for(int y = 0; y < size; y ++)
            {
            player_location = temp.toString();
                if(x == 0)
                {
                    Player temp_obj = new Player(temp_x, temp_y,"white",player_location);
                    white_team.put(player_location,temp_obj);
                }
                else
                {
                    Player temp_obj = new Player(temp_x, temp_y,"black",player_location);
                    black_team.put(player_location,temp_obj);
                }
                temp.setCharAt(0, (char)(temp.charAt(0) + 1));
                temp_y ++;
            }
            temp.setCharAt(0,'a');
            temp.setCharAt(1, Character.forDigit(size - 1,10));
            temp_y = 0;
            temp_x = size - 2;
        }
        for(Player p: white_team.values()){
            p.set_moved_two_tiles(false);
        }
        for(Player p: black_team.values()){
            p.set_moved_two_tiles(false);
        }
    }
    //setting up tiles to the corresponding board
    public void setup_tile()
    {
        String tile_location = "a1";
        StringBuilder temp = new StringBuilder(tile_location);
        int temp_x = 0;
        int temp_y = 0;
        for(int x = 0 ; x < size; x ++)
        {
            for(int y = 0; y < size; y ++)
            {
                tile_location = temp.toString();
                Tile temp_obj = new Tile(tile_location, temp_x, temp_y);
                if(x == 1)
                {
                    temp_obj.set_tile_status(true);
                    temp_obj.set_player_type("white");
                }
                else if(x == size - 2)
                {
                    temp_obj.set_tile_status(true);
                    temp_obj.set_player_type("black");                   
                }
                else
                {
                    temp_obj.set_tile_status(false);
                }
                tiles.put(tile_location,temp_obj);
                temp.setCharAt(0, (char)(temp.charAt(0) + 1));
                temp_y ++;
            }
            temp_y = 0;
            temp_x ++; 
            temp.setCharAt(0,'a');
            temp.setCharAt(1, (char)(temp.charAt(1) + 1));
        }
    }
    //print the board
    public void printBoard() {
        String [][] temp = new String[size][size];
        for(Tile t: tiles.values()){
            if(t.tile_status == true){
                temp[t.tile_location_x][t.tile_location_y] = t.player_type;
            }
        }
        //stores letter labels of the first row (i.e. a b c d) 
        char[] columns = new char[size]; 
        for (int col = 0; col < size; col++) {
            columns[col] = (char) ('a' + col);
        }
        //print letter labels
        System.out.print("   ");
        for (char column : columns) {
            System.out.print(column + " ");
        }
        System.out.println();
        for (int row = size; row >= 1; row--) {
            //print the row separator before each row
            System.out.println("   " + "- ".repeat(size));
            //print row number
            System.out.print(row + " ");

            //print the row content
            for (int col = 0; col < size; col++) {
                System.out.print('|');
                //place black pieces on the second row
                if(temp[row-1][col] != null){
                    if (temp[row-1][col].equals("black")) {
                        System.out.print("" + '\u2659'); // "b" represents black soldiers u2659
                    }
                    //place white pieces on the second-to-last row
                    else if (temp[row-1][col].equals("white")) {
                        System.out.print("" + '\u265F'); // "w" represents white soldiers u265F
                    } 
                }
                else {
                    System.out.print(" "); // Empty square
                }
            }
            System.out.print('|');
            System.out.println(" " + row);
        }
        //print the last row separator after the final row
        System.out.println("   " + "- ".repeat(size));
        //print letter labels again
        System.out.print("   ");
        for (char column : columns) {
            System.out.print(column + " ");
        }
        System.out.println();
    }
    //move the player at curr_location to final_location, given it's current_player's turn to play    
    void move_player(int current_player, String curr_location, String final_location)
    { 
        if(is_valid_move(current_player, curr_location, final_location)){
            //if white is playing
            if(current_player % 2 == 0)
            {   
                //if it's an attack, the player of the opposing team dies
                if(is_valid_attack(current_player, curr_location, final_location)){
                    black_team.remove(final_location);
                }
                int new_x_location = tiles.get(final_location).get_tile_location_x();
                int new_y_location = tiles.get(final_location).get_tile_location_y();
                Player curr_player = white_team.get(curr_location);
                curr_player.set_moved_two_tiles(true);
                curr_player.set_player_location_x(new_x_location);
                curr_player.set_player_location_y(new_y_location);
                curr_player.set_player_location(final_location);
                white_team.remove(curr_location);
                white_team.put(final_location, curr_player);
                tiles.get(curr_location).set_tile_status(false);
                tiles.get(final_location).set_tile_status(true);
                tiles.get(curr_location).set_player_type(null);
                tiles.get(final_location).set_player_type("white");
            }
            //black is playing
            else if(current_player % 2 == 1){
                //if it's an attack, the player of the opposing team dies
                if(is_valid_attack(current_player, curr_location, final_location)){
                    white_team.remove(final_location);
                }
                int new_x_location = tiles.get(final_location).get_tile_location_x();
                int new_y_location = tiles.get(final_location).get_tile_location_y();
                Player curr_player = black_team.get(curr_location);
                curr_player.set_moved_two_tiles(true);
                curr_player.set_player_location_x(new_x_location);
                curr_player.set_player_location_y(new_y_location);
                curr_player.set_player_location(final_location);
                black_team.remove(curr_location);
                black_team.put(final_location, curr_player);
                tiles.get(curr_location).set_tile_status(false);
                tiles.get(final_location).set_tile_status(true);
                tiles.get(curr_location).set_player_type(null);
                tiles.get(final_location).set_player_type("black");
            }
        }
    }

    boolean is_valid_move(int curr_player, String curr_location, String final_location)
    {   //curr_player is even -> white, curr_player is odd -> black
        String team = (curr_player % 2 == 0) ? "white" : "black";
        //if user input is less than 2 characters
        if(curr_location.length() < 2 || final_location.length() < 2){
            return false;
        }
        //checks if move is out of bound 
        int[] indices = location_to_indices(final_location);
        if(indices[0] < 0 || indices[1] >= size){
            return false;
        }
        //checks if the locations exist in the board
        if(!tiles.containsKey(curr_location) || !tiles.containsKey(final_location)){
            return false;
        }
        //if the from location isn't empty, team matches the piece to be moved, the final location exists in the board
        if(tiles.get(curr_location).tile_status == true && team.equals(tiles.get(curr_location).player_type) && tiles.containsKey(final_location)){
            //if the final location is empty and first char of the location is the same, it's a forward move
            if(curr_location.charAt(0) == final_location.charAt(0) && tiles.get(final_location).tile_status == false)
            {   
                //players can't go backwards
                if(Math.abs(final_location.charAt(1) - curr_location.charAt(1)) == 1){
                    if((team.equals("black") && (final_location.charAt(1)- curr_location.charAt(1) != -1)) || (team.equals("white") && (final_location.charAt(1) - curr_location.charAt(1) != 1))){
                        return false;
                    }
                }
                //the distance from curr to final is 2
                if(Math.abs((final_location.charAt(1) - curr_location.charAt(1))) == 2) 
                {
                     //location of the next tile forward for black
                    StringBuilder one_forward_black = new StringBuilder();
                    one_forward_black.append(curr_location.charAt(0));
                    one_forward_black.append(Character.getNumericValue(curr_location.charAt(1)) - 1);
                    
                    //location of the next tile forward for white
                    StringBuilder one_forward_white = new StringBuilder(); 
                    one_forward_white.append(curr_location.charAt(0));
                    one_forward_white.append(Character.getNumericValue(curr_location.charAt(1)) + 1);

                    //extra check that both tiles are empty for a 2-step forward move
                    if((team.equals("black") && tiles.get(one_forward_black.toString()).tile_status == true) || 
                    (team.equals("white") && tiles.get(one_forward_white.toString()).tile_status == true)){
                        return false;
                    }
                    //if player already moved, it can't move two tiles
                    if((team.equals("white") && white_team.containsKey(curr_location) && white_team.get(curr_location).get_moved_two_tiles()) ||
                    (team.equals("black") && black_team.containsKey(curr_location) && black_team.get(curr_location).get_moved_two_tiles()))
                    {
                        return false;
                    }
                }
            }
            else
            {
                //the only case the final location won't be empty is if it's an attack, if it's not a valid attack, return false
                if(is_valid_attack(curr_player, curr_location, final_location) == false){
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    //checks if move is a valid attack by the curr_player
    boolean is_valid_attack(int curr_player, String curr_location, String final_location)
    {   
        String team = (curr_player % 2 == 0) ? "white" : "black";
        //if user input is less than 2 characters
        if(curr_location == null || curr_location.length() < 2 || final_location == null || final_location.length() < 2){
            return false;
        }
        // //checks if move is out of bound 
        int[] indices = location_to_indices(final_location);
        if(indices[0] < 0 || indices[1] >= size){
            return false;
        }
        //black is attacking
        if(team.equals("black")){
            StringBuilder attack1_black = new StringBuilder();
            attack1_black.append((char)(curr_location.charAt(0) - 1));
            attack1_black.append(Character.getNumericValue(curr_location.charAt(1)) - 1);
            StringBuilder attack2_black = new StringBuilder();
            attack2_black.append((char)(curr_location.charAt(0) + 1));
            attack2_black.append(Character.getNumericValue(curr_location.charAt(1)) - 1);
            if(!(final_location.equals(attack1_black.toString())) && !(final_location.equals(attack2_black.toString()))){
                return false;
            }
        }
        //white is attacking
        else if(team.equals("white")){
            StringBuilder attack1_white = new StringBuilder();
            attack1_white.append((char)(curr_location.charAt(0) - 1));
            attack1_white.append(Character.getNumericValue(curr_location.charAt(1)) + 1);

            StringBuilder attack2_white = new StringBuilder();
            attack2_white.append((char)(curr_location.charAt(0) + 1));
            attack2_white.append(Character.getNumericValue(curr_location.charAt(1)) + 1);
            if(!(final_location.equals(attack1_white.toString())) && !(final_location.equals(attack2_white.toString()))){
                return false;
            } 
        }
        //if team matches the piece to be moved, the from_location isn't empty, and the final_location is on the board & has a piece of the opposite team
        if(team.equals(tiles.get(curr_location).player_type) && tiles.get(curr_location).tile_status == true && tiles.containsKey(final_location)
        && tiles.get(final_location).tile_status == true && !(team.equals(tiles.get(final_location).player_type))){
            return true;
        }
        return false;
    }
    //generate all legal moves for a player
    List<Move> generate_legal_moves(Player p){
        List<Move> result = new ArrayList<>();
            //generate the 2-step forward move and add to list
            if(!p.moved_two_tiles){
                if(p.team.equals("white")){
                    StringBuilder new_move = new StringBuilder();
                    new_move.append(p.player_location.charAt(0));
                    new_move.append(Character.getNumericValue(p.player_location.charAt(1)) + 2);//when white moves frwd, x decrements by 2
                    if(is_valid_move(0, p.player_location, new_move.toString())){
                        result.add(new Move(p.player_location, new_move.toString()));
                    }
                }else{
                    StringBuilder new_move = new StringBuilder();
                    new_move.append(p.player_location.charAt(0));
                    new_move.append(Character.getNumericValue(p.player_location.charAt(1)) - 2);//when black moves frwd, x increments by 2
                    if(is_valid_move(1, p.player_location, new_move.toString())){
                        result.add(new Move(p.player_location, new_move.toString()));
                    }
                }
            }
            //player is from black team
            if(p.team.equals("black")){
                //generate one_step forward moves and add to list
                StringBuilder new_move = new StringBuilder();
                new_move.append(p.player_location.charAt(0));
                new_move.append(Character.getNumericValue(p.player_location.charAt(1)) - 1);
                if(is_valid_move(1, p.player_location, new_move.toString())){
                    result.add(new Move(p.player_location, new_move.toString()));
                }
                //generate attack moves for black
                StringBuilder attack_move1 = new StringBuilder();
                attack_move1.append((char)(p.player_location.charAt(0) - 1));
                attack_move1.append(Character.getNumericValue(p.player_location.charAt(1)) - 1);
                if(is_valid_attack(1, p.player_location, attack_move1.toString())){
                    result.add(new Move(p.player_location,attack_move1.toString()));
                }
                StringBuilder attack_move2 = new StringBuilder();
                attack_move2.append((char)(p.player_location.charAt(0) + 1));
                attack_move2.append(Character.getNumericValue(p.player_location.charAt(1)) - 1);
                if(is_valid_attack(1, p.player_location, attack_move2.toString())){
                    result.add(new Move(p.player_location,attack_move2.toString()));
                }
            }
            //player is from white team
            else{
                //generate one_step forward moves and add to list
                StringBuilder new_move = new StringBuilder();
                new_move.append(p.player_location.charAt(0));
                new_move.append(Character.getNumericValue(p.player_location.charAt(1)) + 1);
                if(is_valid_move(0, p.player_location, new_move.toString())){
                    result.add(new Move(p.player_location,new_move.toString()));
                }
                //generate attack moves for white
                StringBuilder attack_move1 = new StringBuilder();
                attack_move1.append((char)(p.player_location.charAt(0) - 1));
                attack_move1.append(Character.getNumericValue(p.player_location.charAt(1)) + 1);
                if(is_valid_attack(0, p.player_location, attack_move1.toString())){
                    result.add(new Move(p.player_location,attack_move1.toString()));
                }
                StringBuilder attack_move2 = new StringBuilder();
                attack_move2.append((char)(p.player_location.charAt(0) + 1));
                attack_move2.append(Character.getNumericValue(p.player_location.charAt(1)) + 1);
                if(is_valid_attack(0, p.player_location, attack_move2.toString())){
                    result.add(new Move(p.player_location, attack_move2.toString()));
                }

            }
        return result;
    }
    //takes a string location (i.e. a1) and returns an array of correspoding indices (i.e. {x, y})
    int[] location_to_indices(String location){
        char letter = location.charAt(0);
        char num = location.charAt(1);
        int x = size - Character.getNumericValue(num);
        int y = letter - 'a';
        return new int[] {x, y};
    }
    //create a deep copy of the current board
    Board deepClone(){
        Board copy = new Board(this.size);
        for(String key: this.tiles.keySet()){
            Tile t = this.tiles.get(key);
            Tile tile_copy = t.deepClone();
            copy.tiles.put(key, tile_copy);
        }
        for(String key: this.white_team.keySet()){
            Player p = this.white_team.get(key);
            Player p_copy = p.deepClone();
            copy.white_team.put(key, p_copy);
        }
        for(String key: this.black_team.keySet()){
            Player p = this.black_team.get(key);
            Player p_copy = p.deepClone();
            copy.black_team.put(key, p_copy);
        }
        return copy;
    }
}
