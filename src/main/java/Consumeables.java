public abstract class Consumeables extends Item{

    // Attribute
    private final int healthPoints;

    public Consumeables(String longName, int healthPoints) {
        super(longName);
        this.healthPoints = healthPoints;
    }

    // Getter
    public int getHealthPoints() {
        return healthPoints;
    }

}
