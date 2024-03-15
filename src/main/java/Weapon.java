public abstract class Weapon extends Item{

    public Weapon(String longName) {
        super(longName);
    }

    public abstract int remainingUses(); //abstract har ingen "krop"

}
