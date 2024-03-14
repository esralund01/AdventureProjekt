import java.util.ArrayList;

public class Adventure {

    // Attribute
    private final Player player;

    // Constructor
    public Adventure() {
        player = new Player(new Map().getFirstRoom());
    }

    // Methods
    public boolean take(String itemWord) {
       return player.take(itemWord);

    }

    public boolean drop(String itemWord) {
        return player.drop(itemWord);

    }

    public ArrayList<Item> getInventory() {
        return player.getInventory();
    }

    public Room getCurrentRoom() {
        return player.getCurrentRoom();
    }

    public boolean go(String direction) {
        return player.go(direction);
    }

    public int getMaxHealth() {
        return player.getMaxHealth();
    }

    public int getHealth() {
        return player.getHealth();
    }

    public boolean eat(String itemWord) {
        return player.eat(itemWord);
    }
}