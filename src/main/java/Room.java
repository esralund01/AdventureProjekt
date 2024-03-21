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
            return "a place filled with darkness, except in the direction you came from" + (enemies.isEmpty() ? "" : ", and a sense of some presence");
        }
        isAlreadyVisited = true;
        String s = description;
        for (Enemy enemy : enemies) {
            s += ", " + enemy.getLongName();
        }
        for (Item item : items) {
            s += ", " + item.getLongName();
        }
        int lastComma = s.lastIndexOf(",") + 1;
        s = s.substring(0, lastComma) + " and" + s.substring(lastComma);
        return s;
    }

    public boolean getIsAlreadyVisited() {
        return isAlreadyVisited;
    }

    public boolean getIsDark() {
        return isDark;
    }

    // Methods
    public boolean turnLight(boolean on) {
        if (hasLights) {
            isDark = !on;
            return true;
        }
        return false;
    }

    public void add(Item item) {
        items.add(item);
    }

    public void add(Enemy enemy) {
        enemies.add(enemy);
    }

    public Item findItem(String itemWord) {
        for (Item item : items) {
            if (item.getShortName().equalsIgnoreCase(itemWord)) {
                return item;
            }
        }
        return null;
    }

    public Enemy findEnemy(String name) {
        for (Enemy enemy : enemies) {
            if (enemy.getShortName().equalsIgnoreCase(name)) {
                return enemy;
            }
        }
        return null;
    }

    public Item findEnemyItem(String itemWord) {
        for (Enemy enemy : enemies) {
            Item item = enemy.getEquipped();
            if (item.getShortName().equalsIgnoreCase(itemWord)) {
                return item;
            }
        }
        return null;
    }

    public void remove(Item item) {
        items.remove(item);
    }

    public void remove(Enemy enemy) {
        enemies.remove(enemy);
    }
}
