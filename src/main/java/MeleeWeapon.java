public class MeleeWeapon extends Weapon {

    public MeleeWeapon(String longName, int hitPoints) {
        super(longName, hitPoints);
    }

    // Weapon method
    @Override
    public boolean canUse() {
        return true;
    }
    @Override
    public void use(){}
}