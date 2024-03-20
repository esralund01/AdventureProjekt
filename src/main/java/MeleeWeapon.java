public class MeleeWeapon extends Weapon {

    // Constructor
    public MeleeWeapon(String longName, int hitPoints) {
        super(longName, hitPoints);
    }

    // Weapon methods
    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    public void use(){}
}