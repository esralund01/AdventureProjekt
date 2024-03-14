public class Food extends Item{
    private int healthPoints;
    public Food(String longName, String shortName, int healthPoints) {
        super(longName, shortName);
        this.healthPoints = healthPoints;
    }

    public int getHealthPoints() {
        return healthPoints;
    }
}
