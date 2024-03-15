public class MeleeWeapon extends Weapon {

    // Constructor
    public MeleeWeapon(String longName) {
        super(longName);
    }

    // Weapon method
    @Override
    public boolean canUse() {
        return true;
    }
}