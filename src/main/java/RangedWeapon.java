public class RangedWeapon extends Weapon {

    // Attribute
    private int numberOfProjectiles;

    // Constructor
    public RangedWeapon(String longName, int numberOfProjectiles) {
        super(longName);
        this.numberOfProjectiles = numberOfProjectiles;
    }

    // Weapon method
    @Override
    public boolean canUse() {
        return numberOfProjectiles > 0;
    }
}