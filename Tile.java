class Tile{
    boolean tile_status; // 0 = empty, 1 = occupied.
    String tile_location_String; //using standard files and ranks notation
    int tile_location_x; //x index of the tile
    int tile_location_y; //y index of the tile
    String player_type; //define which team the player occupying the tile belongs to.

    public Tile (String tile_location_String, int tile_location_x, int tile_location_y)
    {
        this.tile_location_String = tile_location_String;
        this.tile_location_x = tile_location_x;
        this.tile_location_y = tile_location_y;
    }

    boolean get_tile_status()
    {
        return tile_status;
    }
    void set_tile_status(Boolean tile_status)
    {
        this.tile_status = tile_status;
    }
    String get_tile_location()
    {
        return tile_location_String;
    }
    void set_tile_location_string(String tile_location_String)
    {
        this.tile_location_String = tile_location_String;
    }
    int get_tile_location_x()
    {
        return tile_location_x;
    }
    void set_tile_location_x(int tile_location_x)
    {
        this.tile_location_x = tile_location_x;
    }
    int get_tile_location_y()
    {
        return tile_location_y;
    }
    void set_tile_location_y(int tile_location_y)
    {
        this.tile_location_y = tile_location_y;
    }
    void set_player_type(String player_type)
    {
        this.player_type = player_type;
    }
    String get_player_type()
    {
        return player_type;
    }
    Tile deepClone(){
        Tile copy = new Tile(this.player_type, this.tile_location_x, this.tile_location_y);
        copy.tile_status = this.tile_status;
        copy.player_type = this.player_type;
        return copy;
    }
}