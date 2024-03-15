import java.util.ArrayList;

public class Adventure {

    // Attribute
    private final Player player;

    // Constructor
    public Adventure() {
        player = new Player(new Map().getFirstRoom());
    }

    // Methods
    public void addToInventory(Item item) {
        player.addToInventory(item);
    }

    public void removeFromInventory(Item item) {
        player.removeFromInventory(item);
    }

    public Item findInInventory(String itemWord) {
        return player.findInInventory(itemWord);

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

    public void eat(Food food) {
        player.eat(food);
    }

    public void equip(Weapon weapon){
        player.equip(weapon);
    }

    public Weapon getEquipped() {
        return player.getEquipped();
    }
}