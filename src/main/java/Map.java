public class Map {

    // Attribute
    private final Room firstRoom;

    // Constructor
    public Map() {

        // Creating rooms
        Room room1 = new Room("Room 1", "room with no distinct features, except two doors");
        Room room2 = new Room("Room 2", "room with no distinct features, except two doors");
        Room room3 = new Room("Room 3", "room with no distinct features, except two doors");
        Room room4 = new Room("Room 4", "room with no distinct features, except two doors");
        Room room5 = new Room("Room 5", "room with no distinct features, except one door", true);
        Room room6 = new Room("Room 6", "room with no distinct features, except two doors", true);
        Room room7 = new Room("Room 7", "room with no distinct features, except two doors");
        Room room8 = new Room("Room 8", "room with no distinct features, except three doors", true);
        Room room9 = new Room("Room 9", "room with no distinct features, except two doors", true);

        // Setting first room
        firstRoom = room1;

        // Connecting rooms
        connectWestEast(room1, room2);
        connectWestEast(room2, room3);
        connectNorthSouth(room3, room6);
        connectNorthSouth(room6, room9);
        connectNorthSouth(room1, room4);
        connectNorthSouth(room4, room7);
        connectWestEast(room7, room8);
        connectWestEast(room8, room9);
        connectNorthSouth(room5, room8);

        // Putting items into rooms
        room1.addToRoom(new Item("a shiny brass <lamp>"));
        room2.addToRoom(new MeleeWeapon("a gold <knife>"));
        room3.addToRoom(new MeleeWeapon("a diamond <pickaxe>"));
        room1.addToRoom(new Food("a red <apple>", 10));
        room2.addToRoom(new Food("a <black apple>", -20));
        room3.addToRoom(new Food("a cup of freshly brewed <coffee>", 25));
        room1.addToRoom(new RangedWeapon("a black <pistol>", 6));
        room2.addToRoom(new RangedWeapon("a <bow> and arrows",10));
    }

    // Getter
    public Room getFirstRoom() {
        return firstRoom;
    }

    // Auxiliary methods
    private void connectWestEast(Room western, Room eastern) {
        western.setEast(eastern);
        eastern.setWest(western);
    }

    private void connectNorthSouth(Room northern, Room southern) {
        northern.setSouth(southern);
        southern.setNorth(northern);
    }
}
