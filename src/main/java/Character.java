public abstract class Character {

    protected int health;
    protected Weapon equipped;



    public void attack (Character character) {
        character.hit();
    }

    private void hit(){
         health -= 10;
    }

   // public abstract void drop();

    public int getHealth() {
        return health;
    }
    public Weapon getEquipped() {
        return equipped;
    }
}
