public class MeleeWeapon extends Weapon{

    public MeleeWeapon(String longName) {
        super(longName);
    }

    @Override
    public int remainingUses() {
        return Integer.MAX_VALUE;
    }
}
