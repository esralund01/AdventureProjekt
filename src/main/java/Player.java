import java.util.ArrayList;

public class Player {

    // Attributes
    private final ArrayList<Item> inventory;
    private Room currentRoom;
    private Room previousRoom;

    // Constructor
    public Player(Room firstRoom) {
        currentRoom = firstRoom;
        inventory = new ArrayList<>();
    }

    // Getters
    public Room getCurrentRoom() {
        return currentRoom;
    }
    public ArrayList<Item> getInventory() {
        return inventory;
    }

    // Methods
    public boolean go(String direction) {
        // go er en kopi af move fra Signes PowerPoint på Fronter men med "Darkness, imprison me!" tilføjet.
        Room desiredRoom = switch (direction) {
            case "north", "n" -> currentRoom.getNorth();
            case "east", "e" -> currentRoom.getEast();
            case "west", "w" -> currentRoom.getWest();
            case "south", "s" -> currentRoom.getSouth();
            default -> null;
        };
        if (desiredRoom != null && !currentRoom.isDark() || currentRoom.getNorth() != previousRoom) {
            previousRoom = currentRoom;
            currentRoom = desiredRoom;
            return true;
        } else {
            return false;
        }
    }

    public void turnOnLight() {
        currentRoom.turnOnLight();
    }

    public boolean take(String itemWord) {
        Item found = currentRoom.findItem(itemWord);
        if (found == null) {
            return false;
        } else {
            inventory.add(found);
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
}
