import java.util.ArrayList;

public class Player extends Character {

    // Attributes
    private final ArrayList<Item> inventory;
    private Room currentRoom;
    private Room previousRoom;
    private Room portalRoom;
    private final int maxHealth;
    private Enemy opponent;

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

    public Enemy getOpponent() {
        return opponent;
    }

    // Methods
    public void teleport() {
        Room teleportedFrom = currentRoom;
        currentRoom = portalRoom;
        portalRoom = teleportedFrom;
    }

    public State go(String directionWord) {
        Room desiredRoom;
        switch (directionWord) {
            case "north" -> desiredRoom = currentRoom.getNorth();
            case "east" -> desiredRoom = currentRoom.getEast();
            case "west" -> desiredRoom = currentRoom.getWest();
            case "south" -> desiredRoom = currentRoom.getSouth();
            default -> {
                return State.NOT_FOUND; // Failure
            }
        }
        if (currentRoom.getIsDark() && desiredRoom != previousRoom) {
            return State.NO_LIGHT; // Failure
        }
        if (desiredRoom == null) {
            return State.NO_DOOR; // Failure
        }
        previousRoom = currentRoom;
        currentRoom = desiredRoom;
        return State.SUCCESS;
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
        Consumable found;
        if ((food && foundInRoom instanceof Food) || (!food && foundInRoom instanceof Liquid)) {
            currentRoom.removeFromRoom(foundInRoom);
            found = (Consumable) foundInRoom;
        } else if ((food && foundInInventory instanceof Food) || (!food && foundInInventory instanceof Liquid)) {
            inventory.remove(foundInInventory);
            found = (Consumable) foundInInventory;

        } else {
            return State.WRONG_TYPE;
        }
        health += found.getHealthPoints();
        if (health > maxHealth) {
            health = maxHealth;
        }
        return State.SUCCESS;
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
    public void chooseOpponent(){
        if (getCurrentRoom().getEnemies().isEmpty()) {
            opponent = null;
        }
       else {
            opponent = currentRoom.getEnemies().getFirst();
        }
    }
    public State attack() {
        if (getEquipped() == null) {
            return State.FAILURE; // no weapon
        }
        if (!getEquipped().canUse()) {
            return State.NO_AMMO;
        }
        if (opponent == null) {
            return State.NOT_FOUND;
        }
        opponent.hit(equipped.getHitPoints());
        if (opponent.getHealth()<=0){
            currentRoom.getEnemies().remove(opponent);
            currentRoom.addToRoom(opponent.getEquipped());
            opponent = null;
            return State.SUCCESS;
        }
        opponent.attack(this);
        return State.SUCCESS;
    }

    // Auxiliary method
    private Item findInInventory(String itemWord) {
        for (Item item : inventory) {
            if (item.getShortName().equals(itemWord)) {
                return item;
            }
        }
        return null;
    }
}