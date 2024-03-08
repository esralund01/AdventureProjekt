import java.util.ArrayList;

public class Player {

    private Room currentRoom;
    private Room previousRoom;
    private final ArrayList<Item> inventory;


    public Player(Room firstRoom) {
        currentRoom = firstRoom;
        inventory = new ArrayList<>();
    }

    // Go kopieret fra sigens powerpoint.
    public boolean go(String direction){
        Room desiredRoom = switch (direction){
            case "north", "n" -> currentRoom.getNorth();
            case "east", "e" -> currentRoom.getEast();
            case "west", "w" -> currentRoom.getWest();
            case "south", "s" -> currentRoom.getSouth();
            default -> null;
        };

        if (desiredRoom != null){
            currentRoom = desiredRoom;
            return true;
        } else {
            return false;
        }
    }
    public boolean goNorth() {
        if (currentRoom.getNorth() == null || currentRoom.isDark() && currentRoom.getNorth()!=previousRoom) {
            return false;
        } else {
            previousRoom = currentRoom;
            currentRoom = currentRoom.getNorth();
            return true;
        }
    }

    public boolean goWest() {
        if (currentRoom.getWest() == null || currentRoom.isDark() && currentRoom.getWest()!=previousRoom) {
            return false;
        } else {
            previousRoom = currentRoom;
            currentRoom = currentRoom.getWest();
            return true;
        }
    }

    public boolean goEast() {
        if (currentRoom.getEast() == null || currentRoom.isDark()&& currentRoom.getEast()!=previousRoom) {
            return false;
        } else {
            previousRoom = currentRoom;
            currentRoom = currentRoom.getEast();
            return true;
        }
    }

    public boolean goSouth() {
        if (currentRoom.getSouth() == null || currentRoom.isDark()&& currentRoom.getSouth()!=previousRoom) {
            return false;
        } else {
            previousRoom = currentRoom;
            currentRoom = currentRoom.getSouth();
            return true;
        }
    }

    public String look() {
            return currentRoom.getName() + ": " + currentRoom.getDescription() + currentRoom.showItems();
        }
    public String enter() {
        if (currentRoom.isVisited()) {
            return currentRoom.getName();
        }
        else {
            currentRoom.visit();
            return currentRoom.getName() + ": " + currentRoom.getDescription() + currentRoom.showItems();
        }
    }

    public void turnOnLight(){
        currentRoom.turnOnLight();
    }

    public boolean take(String itemWord){
        Item found = currentRoom.findItem(itemWord);
        if(found==null){
            return false;
        } else{
            inventory.add(found);
            return true;
        }
    }
    public boolean drop(String itemWord){
        for (Item item : inventory) {
            if (item.getShortName().equals(itemWord)) {
                inventory.remove(item);
                currentRoom.addItem(item);
                return true;
            }
        }
        return false;
    }
    public String showInvetory() {
        if(inventory.isEmpty()){
            return "Your inventory is empty";
        }
        String result = "Your inventory contains ";
        for (Item item : inventory) {
            result +=  item.getLongName() + " and ";
        }
        return result.substring(0, result.length() - 5);
    }

}
