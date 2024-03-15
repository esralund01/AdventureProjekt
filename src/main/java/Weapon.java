public abstract class Weapon extends Item {

    // Constructor
    public Weapon(String longName) {
        super(longName);
    }

    // Abstract method
    public abstract boolean canUse(); // Abstract metoder har ingen {krop}.
}