import java.util.ArrayList;

public class Adventure {

    // Attribute
    private final Player player;

    // Constructor
    public Adventure() {
        Map map = new Map();
        player = new Player(map.getFirstRoom());
    }

    // Methods
    public void turnOnLight(){
        player.turnOnLight();
    }

    public boolean take(String itemWord){
       return player.take(itemWord);

    }

    public boolean drop(String itemWord){
        return player.drop(itemWord);

    }

    public ArrayList<Item> getInventory(){
        return player.getInventory();
    }

    public Room getCurrentRoom(){
        return player.getCurrentRoom();
    }

    public boolean go(String direction){
        return player.go(direction);
    }
}