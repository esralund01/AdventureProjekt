import java.util.ArrayList;

public class Player extends Character{

    // Attributes
    private final ArrayList<Item> inventory;
    private Room currentRoom;
    private Room previousRoom;
    private Room portalRoom;
    private final int maxHealth;

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

    // Methods
    public void teleport() {
        Room teleportedFrom = currentRoom;
        currentRoom = portalRoom;
        portalRoom = teleportedFrom;
    }

    public State go(String direction) {
        // go er en kopi af move fra Signes PowerPoint på Fronter men med "Darkness, imprison me!" tilføjet.
        Room desiredRoom;
        switch (direction) {
            case "north" -> desiredRoom = currentRoom.getNorth();
            case "east" -> desiredRoom = currentRoom.getEast();
            case "west" -> desiredRoom = currentRoom.getWest();
            case "south" -> desiredRoom = currentRoom.getSouth();
            default -> {return State.NOT_FOUND;}}
        if (!currentRoom.getIsDark() || desiredRoom == previousRoom) {
            previousRoom = currentRoom;
            currentRoom = desiredRoom;
            return State.SUCCESS;
        } else {
            return State.FAILURE; // upræcis
        }
    }

    private Item findInInventory(String itemWord) {
        for (Item item : inventory) {
            if (item.getShortName().equals(itemWord)) {
                return item;
            }
        }
        return null;
    }

    public State take(String itemWord) {
        Item found = currentRoom.findInRoom(itemWord);
        if (found == null) {
            return State.NOT_FOUND;
        }
        currentRoom.removeFromRoom(found);
        inventory.add(found);
        return State.SUCCESS;
    }

    public State drop(String itemWord) {
        Item found = findInInventory(itemWord);
        if (found == null) {
            return State.NOT_FOUND;
        }
        inventory.remove(found);
        getCurrentRoom().addToRoom(found);
        return State.SUCCESS;
    }

    public State consume(boolean food, String itemWord) {
        Item foundInRoom = currentRoom.findInRoom(itemWord);
        Item foundInInventory = findInInventory(itemWord);
        if (foundInRoom == null && foundInInventory == null) {
            return State.NOT_FOUND;
        }
        if ((food && foundInRoom instanceof Food) || (!food && foundInRoom instanceof Liquid)) {
            currentRoom.removeFromRoom(foundInRoom);
            incrementHealth(((Food) foundInRoom).getHealthPoints());
            return State.SUCCESS;
        } else if ((food && foundInInventory instanceof Food) || (!food && foundInInventory instanceof Liquid)) {
            inventory.remove(foundInInventory);
            incrementHealth(((Food) foundInInventory).getHealthPoints());
            return State.SUCCESS;
        }
        return State.WRONG_TYPE;
    }

    public State equip(String itemWord) {
        Item found = findInInventory(itemWord);
        if (found == null) {
            return State.NOT_FOUND;
        }
        if (found instanceof Weapon) {
            equipped = (Weapon) found;
            return State.SUCCESS;
        }
        return State.WRONG_TYPE;
    }

    // Auxiliary method
    private void incrementHealth(int i) {
        health += i;
        if (health > maxHealth) {
            health = maxHealth;
        }
    }
}