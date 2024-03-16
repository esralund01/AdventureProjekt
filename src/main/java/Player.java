import java.util.ArrayList;

public class Player {

    // Attributes
    private final ArrayList<Item> inventory;
    private Room currentRoom;
    private Room previousRoom;
    private Room portalRoom;
    private final int maxHealth;
    private int health;
    private Weapon equipped;

    // Constructor
    public Player(Room firstRoom) {
        currentRoom = firstRoom;
        portalRoom = currentRoom;
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

    public Weapon getEquipped() {
        return equipped;
    }

    // Methods
    public void teleport() {
        Room teleportedFrom = currentRoom;
        currentRoom = portalRoom;
        portalRoom = teleportedFrom;
    }

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
        if (item == equipped) {
            equipped = null;
        }
    }

    public Item findInInventory(String itemWord) {
        for (Item item : inventory) {
            if (item.getShortName().equals(itemWord)) {
                return item;
            }
        }
        return null;
    }

    public boolean eat(Item item) {
        if (item instanceof Food) {
            health += ((Food) item).getHealthPoints();
            if (health > maxHealth) {
                health = maxHealth;
            }
            return true;
        }
        return false;
    }

    public boolean equip(Item item){
        if (item instanceof Weapon) {
            equipped = (Weapon) item;
            return true;
        }
        return false;
    }
}