public abstract class Weapon extends Item {


    private int hitPoints;

    // Constructor
    public Weapon(String longName, int hitPoints) {
        super(longName);
        this.hitPoints = hitPoints;
    }
    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    // Abstract method
    public abstract boolean canUse(); // Abstract metoder har ingen {krop}.

    public abstract void use();
}