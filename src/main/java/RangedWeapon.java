public class RangedWeapon extends Weapon {

    // Attribute
    private int numberOfProjectiles;

    public RangedWeapon(String longName, int hitPoints, int numberOfProjectiles) {
        super(longName, hitPoints);
        this.numberOfProjectiles = numberOfProjectiles;
    }



    // Weapon methods
    @Override
    public boolean canUse() {
        return numberOfProjectiles > 0;
    }

    @Override
    public void use(){
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
        newLongName = newLongName.replace("$", TextStyle.number(numberOfProjectiles));
        return newLongName;
    }
}