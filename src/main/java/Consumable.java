public abstract class Consumable extends Item {

    // Attribute
    private final int healthPoints;

    // Constructor
    public Consumable(String longName, int healthPoints) {
        super(longName);
        this.healthPoints = healthPoints;
    }

    // Getter
    public int getHealthPoints() {
        return healthPoints;
    }
}
