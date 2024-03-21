public class Map {

    // Attribute
    private final Room firstRoom;

    // Constructor
    public Map() {

        // Creating rooms
        Room room1 = new Room("Entrance hall", "Large hall with no distinct features, except two doors");
        Room room2 = new Room("Library", "everything looks like a huge mess, looking like something went trough, there are two doors in this room");
        Room room3 = new Room("kea", "kea with no distinct features, except two doors");
        Room room4 = new Room("Dungeon", "You sense af chilling presence in this room, but you can see two doors");
        Room room5 = new Room("Treasure vault", "the hidden room you've with only one entrance, and filled with all sorts of treasures,", true);
        Room room6 = new Room("The kitchen", "a room filled with delicious consumables, and two doors", true);
        Room room7 = new Room("Gardens", "Nothing to exciting a garden which hasn't been looked after, but there are two doors");
        Room room8 = new Room("Royal chambers", "you sense you are nearing your objective in this room, there are three doors", true);
        Room room9 = new Room("Armory", "room with a lot of different weapons from any time, and two doors", true);

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

        // Putting items and enemies into rooms
        room1.add(new Item("a shiny brass <lamp>"));
        room5.add(new Item("a diamond <bracelet>"));
        room5.add(new Item("a dripping <cloak>"));
        room5.add(new Item("a new pair of <air max>"));
        room2.add(new MeleeWeapon("a gold <knife>", 15));
        room3.add(new MeleeWeapon("a diamond <pickaxe>", 7));
        room9.add(new MeleeWeapon("a steel <katana>", 35));
        room9.add(new RangedWeapon("a golden <ak-47>", 30, 50));
        room1.add(new Food("a red <apple>", 10));
        room6.add(new Food("a bigmac <burger>", 30));
        room6.add(new Liquid("an iskold <faxe>", 50));
        room8.add(new Liquid("a can of sparkling <water>", -10));
        room2.add(new Food("a <black apple>", -20));
        room3.add(new Liquid("a cup of freshly brewed <coffee>", 25));
        room1.add(new RangedWeapon("a black <revolver> with $ bullet(s)", 6, 25));
        room2.add(new RangedWeapon("a <bow> and quiver with $ arrow(s)", 12, 20));
        room5.add(new Enemy("the Dark Lord <Voldemort>", 150, new MeleeWeapon("the Elder <Wand>", 25)));
        room4.add(new Enemy("Visible <Ghost>", 20, new MeleeWeapon("Ghost <Weapon>", 10)));
        room3.add(new Enemy("a REALLY large <Spider>", 15, new MeleeWeapon("spider <web>", 10)));
        room3.add(new Enemy("a REALLY large <Wasp>", 15, new MeleeWeapon("wasp <stinger>", 10)));
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
