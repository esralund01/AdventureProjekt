import java.util.ArrayList;

public class Player extends Character {

    // Attributes
    private final ArrayList<Item> inventory;
    private Room currentRoom;
    private Room previousRoom;
    private Room portalRoom;

    // Constructor
    public Player(Room firstRoom) {
        super(100);
        currentRoom = firstRoom;
        portalRoom = currentRoom;
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
    public void teleport() {
        Room teleportedFrom = currentRoom;
        currentRoom = portalRoom;
        portalRoom = teleportedFrom;
    }

    public State go(String directionWord) {
        boolean invalidDW = false;
        Room desiredRoom = switch (directionWord) {
            case "north" -> currentRoom.getNorth();
            case "east" -> currentRoom.getEast();
            case "west" -> currentRoom.getWest();
            case "south" -> currentRoom.getSouth();
            default -> {
                invalidDW = true;
                yield null;
            }
        };
        if (invalidDW) {
            return State.NOT_FOUND; // Der var søgt på noget andet end de fire valgmuligheder.
        }
        if (currentRoom.getIsDark() && desiredRoom != previousRoom) {
            return State.NO_ACCESS; // Rummet var mørkt, og der var valgt en retning, som ikke var den, man kom fra.
        }
        if (desiredRoom == null) {
            return State.NULL; // Der var ikke noget rum forbundet i den retning.
        }
        previousRoom = currentRoom;
        currentRoom = desiredRoom;
        return State.SUCCESS;
    }

    public State take(String itemWord) {
        Item found = currentRoom.findItem(itemWord);
        if (found == null) {
            Item maybeYouMeant = currentRoom.findEnemyItem(itemWord);
            if (maybeYouMeant != null) {
                return State.NO_ACCESS;
            }
            return State.NOT_FOUND;
        }
        currentRoom.remove(found);
        inventory.add(found);
        return State.SUCCESS;
    }

    public State drop(String itemWord) {
        Item found = findInInventory(itemWord);
        if (found == null) {
            return State.NOT_FOUND;
        }
        inventory.remove(found);
        getCurrentRoom().add(found);
        return State.SUCCESS;
    }

    public State consume(boolean food, String itemWord) {
        // Variablen food skal være true, hvis man vil spise, og false hvis man vil drikke.
        Item foundInRoom = currentRoom.findItem(itemWord);
        Item foundInInventory = findInInventory(itemWord);
        if (foundInRoom == null && foundInInventory == null) {
            return State.NOT_FOUND;
        }
        Consumable found;
        if ((food && foundInRoom instanceof Food) || (!food && foundInRoom instanceof Liquid)) {
            currentRoom.remove(foundInRoom);
            found = (Consumable) foundInRoom;
        } else if ((food && foundInInventory instanceof Food) || (!food && foundInInventory instanceof Liquid)) {
            inventory.remove(foundInInventory);
            found = (Consumable) foundInInventory;
        } else {
            return State.WRONG_TYPE;
        }
        heal(found.getHitPoints()); // Her får man mere health. Se heal-metoden i Character-klassen.
        return State.SUCCESS;
    }

    public State equip(String itemWord) {
        Item found = findInInventory(itemWord);
        if (found == null) {
            return State.NOT_FOUND;
        }
        if (found instanceof Weapon) {
            setEquipped((Weapon) found);
            return State.SUCCESS;
        }
        return State.WRONG_TYPE;
    }

    public State attack(Enemy opponent) {
        if (getEquipped() == null) { // Er der et våben equipped?
            return State.NULL;
        }
        if (!getEquipped().canUse()) { // Har det flere skud?
            return State.NO_ACCESS;
        }
        this.hit(opponent); // Her bruger vi et skud, om der er en opponent eller ej. Se hit-metoden i Character-klassen.
        if (opponent == null) { // Er der en opponent?
            return State.NOT_FOUND;
        }
        if (opponent.getHealth() == 0) { // Er opponent død?
            currentRoom.remove(opponent);
            currentRoom.add(opponent.getEquipped());
        }
        opponent.hit(this); // Opponent angriber tilbage og bruger derfor også et skud.
        return State.SUCCESS;
    }

    // Auxiliary method
    private Item findInInventory(String itemWord) {
        for (Item item : inventory) {
            if (item.getShortName().equalsIgnoreCase(itemWord)) {
                return item;
            }
        }
        return null;
    }
}