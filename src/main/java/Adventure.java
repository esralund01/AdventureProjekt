import java.util.ArrayList;

public class Adventure {

    // Attribute
    private final Player player;

    // Constructor
    public Adventure() {
        player = new Player(new Map().getFirstRoom());
    }

    // Methods
    public void teleport() {
        player.teleport();
    }

    public ArrayList<Item> getInventory() {
        return player.getInventory();
    }

    public Room getCurrentRoom() {
        return player.getCurrentRoom();
    }

    public State go(String directionWord) {
        return player.go(directionWord);
    }

    public int getMaxHealth() {
        return player.getMaxHealth();
    }

    public int getHealth() {
        return player.getHealth();
    }

    public State take(String itemWord) {
        return player.take(itemWord);
    }

    public State drop(String itemWord) {
        return player.drop(itemWord);
    }

    public State consume(boolean food, String itemWord) {
        return player.consume(food, itemWord);
    }

    public State equip(String itemWord) {
        return player.equip(itemWord);
    }

    public Weapon getEquipped() {
        return player.getEquipped();
    }

    public State attack() {
        return player.attack();
    }
}