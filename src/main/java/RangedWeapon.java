public class RangedWeapon extends Weapon {

    // Attribute
    private int numberOfProjectiles;

    // Constructor
    public RangedWeapon(String longName, int numberOfProjectiles) {
        super(longName);
        this.numberOfProjectiles = numberOfProjectiles;
    }

    // Weapon methods
    @Override
    public boolean canUse() {
        return numberOfProjectiles > 0;
    }

    @Override
    public void attack(){
        numberOfProjectiles -= 1;
    }

    // Item method
    @Override
    public String getLongName() {
        String newLongName = super.getLongName();
        if (numberOfProjectiles == 1) {
            newLongName = newLongName.replace("(s)", "");
        } else {
            newLongName = newLongName.replace("(s)", "s");
        }
        newLongName = newLongName.replace("$", String.valueOf(numberOfProjectiles));
        return newLongName;
    }
}