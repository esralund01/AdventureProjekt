public abstract class Consumable extends Item {

    // Attribute
    private final int hitPoints;

    // Constructor
    public Consumable(String longName, int hitPoints) {
        super(longName);
        this.hitPoints = hitPoints;
    }

    // Getter
    public int getHitPoints() {
        return hitPoints;
    }
}