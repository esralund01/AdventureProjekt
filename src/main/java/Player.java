public class Player {

    private Room currentRoom;
    private Room previousRoom;



    public Player(Room firstRoom) {
        currentRoom = firstRoom;
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
        if (currentRoom.isVisited()) {
            return currentRoom.getName();
        }
        else {
            currentRoom.visit();
            return currentRoom.getName() + ": " + currentRoom.getDescription();
        }
    }

    public void turnOnLight(){
        currentRoom.turnOnLight();
    }
}
