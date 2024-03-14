import java.util.ArrayList;

public class Room {

    // Attributes
    private final String name;
    private final String description;
    private Room north;
    private Room east;
    private Room west;
    private Room south;
    private boolean isAlreadyVisited;
    private final boolean hasLights;
    private boolean isDark;
    private final ArrayList<Item> items;

    // Constructor
    public Room(String name, String description) {
        this.name = name;
        this.description = description;
        hasLights = false;
        isDark = false;
        isAlreadyVisited = false;
        items = new ArrayList<>();
    }

    public Room(String name, String description, boolean isDark) {
        this.name = name;
        this.description = description;
        hasLights = true;
        this.isDark = isDark;
        isAlreadyVisited = false;
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
        if (isDark) {
            return "place filled with darkness, except in the direction you came from";
        }
        String s = description;
        for (Item item : items) {
            s += ", and " + item.getLongName();
        }
        return s;
    }

    public boolean getIsAlreadyVisited() {
        return isAlreadyVisited;
    }

    public boolean getIsDark() {
        return isDark;
    }

    // Methods
    public void visit() {
        isAlreadyVisited = true;
    }

    public boolean turnLight(boolean on) {
        if (hasLights) {
            isDark = !on;
            return true;
        }
        return false;
    }

    public void addItem(Item item) {
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

    public void removeItem (Item item) {
        items.remove(item);
    }

}
