public class RangedWeapon extends Weapon{

    private int i;

    public RangedWeapon(String longName, int i) {
        super(longName);
        this.i=i;
    }


    @Override
    public int remainingUses() {
        return i;
    }
}
