public class Enemy {

    //Attributes
    private String name;
    private String description;
    private int life;
    private Weapon weapon;

    public Enemy(String name, String description, int life, Weapon weapon) {
        this.name = name;
        this.description = description;
        this.life = life;
        this.weapon = weapon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    //attack player
    private void attackPlayer (Player player) {
        Weapon enemyWeapon = getWeapon();
        if (enemyWeapon != null) {

        }
    }

}

