import java.util.ArrayList;

public class Player {

    // Attributes
    private final ArrayList<Item> inventory;
    private Room currentRoom;
    private Room previousRoom;
    private final int maxHealth;
    private int health;

    // Constructor
    public Player(Room firstRoom) {
        currentRoom = firstRoom;
        inventory = new ArrayList<>();
        maxHealth = 100;
        health = maxHealth;
    }

    // Getters
    public Room getCurrentRoom() {
        return currentRoom;
    }
    public ArrayList<Item> getInventory() {
        return inventory;
    }
    public int getMaxHealth() {
        return maxHealth;
    }
    public int getHealth() {
        return health;
    }

    // Methods
    public boolean go(String direction) {
        // go er en kopi af move fra Signes PowerPoint på Fronter men med "Darkness, imprison me!" tilføjet.
        Room desiredRoom = switch (direction) {
            case "north" -> currentRoom.getNorth();
            case "east" -> currentRoom.getEast();
            case "west" -> currentRoom.getWest();
            case "south" -> currentRoom.getSouth();
            default -> null;
        };
        if (desiredRoom != null && (!currentRoom.getIsDark() || desiredRoom == previousRoom)) {
            previousRoom = currentRoom;
            currentRoom = desiredRoom;
            return true;
        } else {
            return false;
        }
    }

    public void addToInventory(Item item) {
        inventory.add(item);
    }

    public void removeFromInventory(Item item) {
        inventory.remove(item);
    }

    public Item findInInventory(String itemWord) {
        for (Item item : inventory) {
            if (item.getShortName().equals(itemWord)) {
                return item;
            }
        }
        return null;
    }

    public void eat(Food food) {
        health += food.getHealthPoints();
        if (health > maxHealth) {
            health = maxHealth;
        }
    }
}
