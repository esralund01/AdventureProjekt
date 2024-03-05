public class Room {
    //attributter
    private String name;
    private String description;
private Room north;
private Room south;
private Room west;
private Room east;

    //constructor
public Room (String name, String description) {
    this.name = name;
    this.description = description;
}

    public void setNorth(Room north) {
        this.north = north;
    }

    public void setSouth(Room south) {
        this.south = south;
    }

    public void setWest(Room west) {
        this.west = west;
    }

    public void setEast(Room east) {
        this.east = east;
    }

}
