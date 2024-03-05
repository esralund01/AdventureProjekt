public class Adventure {
public void createRoom() {
    Room room1 = new Room("Room 1", "a totally empty room and dark room. There's 4 doors, but only 2 can open. But which?");
    Room room2 = new Room("Room 2", "a totally empty room and dark room. There's 4 doors, but only 2 can open. But which?");
    Room room3 = new Room("Room 3", "a totally empty room and dark room. There's 4 doors, but only 2 can open. But which?");
    Room room4 = new Room("Room 4", "a totally empty room and dark room. There's 4 doors, but only 2 can open. But which?");
    Room room5 = new Room("Room 5", "a totally empty room and dark room. There's 4 doors, but only 1 can open. But which?");
    Room room6 = new Room("Room 6", "a totally empty room and dark room. There's 4 doors, but only 2 can open. But which?");
    Room room7 = new Room("Room 7", "a totally empty room and dark room. There's 4 doors, but only 2 can open. But which?");
    Room room8 = new Room("Room 8", "a totally empty room and dark room. There's 4 doors, but only 3 can open. But which?");
    Room room9 = new Room("Room 9", "a totally empty room and dark room. There's 4 doors, but only 2 can open. But which?");

  //forbind rummene
    room1.setEast(room2);
    room1.setSouth(room4);

    room2.setEast(room3);
    room2.setWest(room1);

    room3.setSouth(room6);
    room3.setWest(room2);

    room4.setNorth(room1);
    room4.setSouth(room7);

    room5.setSouth(room8); //dette skal være finishline

    room6.setNorth(room3);
    room6.setSouth(room9);

    room7.setNorth(room4);
    room7.setEast(room8);

    room8.setWest(room7);
    room8.setNorth(room5);
    room8.setEast(room9);

    room9.setNorth(room6);
    room9.setWest(room8);
}

}

