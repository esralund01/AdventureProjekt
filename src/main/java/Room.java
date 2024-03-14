import java.util.ArrayList;

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
    private final ArrayList<Item> items;

    // Constructor
    public Room(String name, String description) {
        this.name = name;
        this.description = description;
        visited = false;
        dark = false; // Slå "Darkness, imprison me!" til her.
        items = new ArrayList<>();
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
        return description;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public boolean isVisited() {
        return visited;
    }

    public boolean isDark() {
        return dark;
    }

    // Methods
    public void visit() {
        visited = true;
    }

    public void turnOnLight() {
        dark = false;
    }

    public void addItem(Item item){
        items.add(item);
    }

    public Item findItem(String itemWord) {
        for (Item item : items) {
            if (item.getShortName().equals(itemWord)) {
                return item;
            }
        }
        return null;
    }

    public void removeItem (Item item){
        items.remove(item);
    }

}
