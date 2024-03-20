public abstract class Weapon extends Item {

    // Attribute
    private final int hitPoints;

    // Constructor
    public Weapon(String longName, int hitPoints) {
        super(longName);
        this.hitPoints = hitPoints;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    // Abstract methods
    public abstract boolean canUse(); // Abstrakte metoder har ingen {krop}.

    public abstract void use();
}