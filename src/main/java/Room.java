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
    private final ArrayList<Enemy> enemies;

    // Constructor 1: Rum uden lys/mørke-funktion.
    public Room(String name, String description) {
        this.name = name;
        this.description = description;
        hasLights = false; // Slået fra.
        isDark = false;
        isAlreadyVisited = false;
        items = new ArrayList<>();
        enemies = new ArrayList<>();
    }

    // Constructor 2: Rum med lys/mørke-funktion. Her vælger man om rummet er mørkt eller ej fra starten.
    public Room(String name, String description, boolean isDark) {
        this.name = name;
        this.description = description;
        hasLights = true; // Slået til.
        this.isDark = isDark;
        isAlreadyVisited = false;
        items = new ArrayList<>();
        enemies = new ArrayList<>();
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
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
        isAlreadyVisited = true; // Når man beder om navnet på et rum, så er det jo fordi man besøger det.
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
        for (Enemy enemy : enemies) {
            s += ", and " + enemy.getName();
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
    public boolean turnLight(boolean on) { // skal converteres til state
        if (hasLights) {
            isDark = !on;
            return true;
        }
        return false;
    }

    public void addToRoom(Item item) {
        items.add(item);
    }

    public Item findInRoom(String itemWord) { // skal måske også fjerne item
        for (Item item : items) {
            if (item.getShortName().equals(itemWord)) {
                items.remove(item);
                return item;
            }
        }
        return null;
    }

    public void removeFromRoom(Item item) {
        items.remove(item);
    }
}
