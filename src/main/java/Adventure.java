public class Adventure {

    private Room currentRoom;


    public Adventure() {
        Room room1 = new Room("Room 1","room with no distinct features, except two doors");
        Room room2 = new Room("Room 2","room with no distinct features, except two doors");
        Room room3 = new Room("Room 3","room with no distinct features, except two doors");
        Room room4 = new Room("Room 4","room with no distinct features, except two doors");
        Room room5 = new Room("Room 5","room with no distinct features, except one door");
        Room room6 = new Room("Room 6","room with no distinct features, except two doors");
        Room room7 = new Room("Room 7","room with no distinct features, except two doors");
        Room room8 = new Room("Room 8","room with no distinct features, except three doors");
        Room room9 = new Room("Room 9","room with no distinct features, except two doors");
        currentRoom = room1;
        room1.setEast(room2);
        room1.setSouth(room4);
        room2.setWest(room1);
        room2.setEast(room3);
        room3.setWest(room2);
        room3.setSouth(room6);
        room4.setNorth(room1);
        room4.setSouth(room7);
        room5.setSouth(room8);
        room6.setNorth(room3);
        room6.setNorth(room9);
        room7.setNorth(room4);
        room7.setEast(room8);
        room8.setWest(room7);
        room8.setNorth(room5);
        room8.setEast(room9);
        room9.setWest(room8);
        room9.setNorth(room6);
    }

    public boolean goNorth(){
        if (currentRoom.getNorth() == null) {
            return false;
        }else{
            currentRoom = currentRoom.getNorth();
            return true;
        }
    }
    public boolean goWest() {
        if (currentRoom.getWest() == null) {
            return false;
        } else {
            currentRoom = currentRoom.getWest();
            return true;
        }
    }

    public boolean goEast() {
        if (currentRoom.getEast() == null) {
            return false;
        } else {
            currentRoom = currentRoom.getEast();
            return true;
        }
    }
    public boolean goSouth() {
        if (currentRoom.getSouth() == null) {
            return false;
        } else {
            currentRoom = currentRoom.getSouth();
            return true;
        }
    }
    public String look() {
        return currentRoom.getDescription();
    }
}