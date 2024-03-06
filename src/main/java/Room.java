
public class Room {

    // Attributes
    private final String name;
    private final String description;
    private Room north;
    private Room east;
    private Room west;
    private Room south;
    private boolean visited;
    private boolean dark;


    // Constructor
    public Room(String name, String description) {
        this.name = name;
        this.description = description;
        visited = false;
        dark = true;
    }


    // Setters
    public void setNorth(Room north) {
        this.north = north;
    }

    public void setEast(Room east) {
        this.east = east;
    }

    public void setWest(Room west) {
        this.west = west;
    }

    public void setSouth(Room south) {
        this.south = south;
    }

    // Getters
    public Room getNorth() {
        return north;
    }

    public Room getEast() {
        return east;
    }

    public Room getWest() {
        return west;
    }

    public Room getSouth() {
        return south;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        if (dark) {
          return "the room is filled with darkness. to turn on the lights, write 'turn on lights'";
        } else {
            return description;
        }
    }
    public boolean isVisited() {
        return visited;
    }

    // Method
    public void visit() {
        visited = true;
    }

    public boolean isDark(){
        return dark;
    }

    public void turnOnLight(){
        dark = false;
    }
}