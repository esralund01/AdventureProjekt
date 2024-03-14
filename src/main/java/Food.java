public class Food extends Item {

    // Attribute
    private final int healthPoints;

    // Constructor
    public Food(String longName, int healthPoints) {
        super(longName);
        this.healthPoints = healthPoints;
    }

    // Getter
    public int getHealthPoints() {
        return healthPoints;
    }
}
