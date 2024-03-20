public abstract class Character {

    protected int health;
    protected Weapon equipped;



   /* public void attack (Character character) {
        character.hit();
    }

    */

    public void hit(int hitPoints) {
        health -= hitPoints;
    }

    // public abstract void drop();

    public int getHealth() {
        return health;
    }

    public Weapon getEquipped() {
        return equipped;
    }
}
