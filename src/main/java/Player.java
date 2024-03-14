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

    public boolean take(String itemWord) {
        Item found = currentRoom.findItem(itemWord);
        if (found == null) {
            return false;
        } else {
            inventory.add(found);
            currentRoom.removeItem(found);
            return true;
        }
    }

    public boolean drop(String itemWord) {
        for (Item item : inventory) {
            if (item.getShortName().equals(itemWord)) {
                inventory.remove(item);
                currentRoom.addItem(item);
                return true;
            }
        }
        return false;
    }

    public boolean eat(String itemWord) {
        // Erklærer (declares) variablen 'food'.
        Food food = null;
        // Leder efter søgeordet i rummet.
        Item itemFromRoom = currentRoom.findItem(itemWord);
        // Hvis mad bliver fundet i rummet, så bliver det tildelt (assigned) til 'food'. Her er null-tjek inkluderet i instanceof.
        if (itemFromRoom instanceof Food) {
            food = (Food) itemFromRoom;
            currentRoom.removeItem(itemFromRoom);
        }
        // Hvis ikke, ledes efter søgeordet i inventory.
        else {
            for (Item itemFromInventory : inventory) {
                if (itemFromInventory.getShortName().equals(itemWord) && itemFromInventory instanceof Food) {
                    food = (Food) itemFromInventory;
                    inventory.remove(itemFromInventory);
                    break; // Stopper for-loopet.
                }
            }
        }
        // Hvis der blev fundet mad.
        if (food != null) {
            health += food.getHealthPoints();
            if (health > maxHealth) {
                health = maxHealth;
            }
            return true;
        }
        return false;
    }
}
