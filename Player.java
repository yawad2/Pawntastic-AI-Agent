public class Player {
    boolean moved_two_tiles; // false = can move two tiles, true = can't move two tiles
    int player_location_x; //x index of player location
    int player_location_y; //y index of player location
    String team; //to define if player is from white or black team.
    String player_location; //the string location of the player on the board

    public Player(int player_location_x, int player_location_y, String team, String player_location)
    {
        this.moved_two_tiles = false;
        this.player_location_x = player_location_x;
        this.player_location_y = player_location_y;
        this.team = team;
        this.player_location = player_location;
    }
    void set_moved_two_tiles(boolean moved_two_tiles)
    {
        this.moved_two_tiles = moved_two_tiles;
    }
    boolean get_moved_two_tiles()
    {
        return moved_two_tiles;
    }
    void set_player_location (String location){
        this.player_location = location;
    }
    String get_player_location (){
        return player_location;
    }
    void set_player_location_x(int player_location_x)
    {
        this.player_location_x = player_location_x;
    }
    int get_player_location_x()
    {
        return player_location_x;
    }
    void set_player_location_y(int player_location_y)
    {
        this.player_location_y = player_location_y;
    }
    int get_player_location_y()
    {
        return player_location_y;
    }
    void set_team(String team)
    {
        this.team = team;
    }
    String get_team()
    {
        return team;
    }
    Player deepClone(){
        Player copy = new Player(this.player_location_x, this.player_location_y, this.team, this.player_location);
        copy.moved_two_tiles = this.moved_two_tiles;
        return copy;
    }

}
