import java.util.ArrayList;

public class Adventure {

    // Attribute
    public final Player player;

    // Constructor
    public Adventure() {
        player = new Player(new Map().getFirstRoom());
    }

    // Methods
    public void teleport() {
        player.teleport();
    }
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

    public boolean eat(Item item) {
        return player.eat(item);
    }

    public boolean drink(Item item) {
        return player.drink(item);
    }

    public boolean equip(Item item) {
        return player.equip(item);
    }

    public Weapon getEquipped() {
        return player.getEquipped();
    }
    public void attack(Character c1, Character c2){
        c1.attack(c2);
    }
}