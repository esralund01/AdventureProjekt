public class Adventure {

    private final Player player;

    public Adventure() {
        Map map = new Map();
        player = new Player(map.getFirstRoom());
    }

    public boolean goNorth(){
        return player.goNorth();
    }
    public boolean goWest() {
       return player.goWest();

    }

    public boolean goEast() {
       return player.goEast();
    }
    public boolean goSouth() {
      return player.goSouth();

    }
    public String look() {
        return player.look();
    }

    public void turnOnLight(){
        player.turnOnLight();
    }

}