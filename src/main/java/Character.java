public abstract class Character {

    // Attributes
    private int health;
    private final int maxHealth;
    private Weapon equipped;

    // Constructor
    public Character(int health) {
        this.health = health;
        this.maxHealth = health;
    }

    // Getters
    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public Weapon getEquipped() {
        return equipped;
    }

    // Setter (subclasses only)
    protected void setEquipped(Weapon equipped) {
        this.equipped = equipped;
    }

    // Subclass methods for player
    protected void hit(Character character) {
        if (equipped != null) {
            equipped.use();
            if (character != null) {
                character.damage(equipped.getHitPoints());
            }
        }
    }

    protected void heal(int hitPoints) {
        health += hitPoints;
        cap();
    }

    // Auxiliary methods
    private void damage(int hitPoints) {
        health -= hitPoints;
        cap();
    }

    private void cap() {
        if (health > maxHealth) {
            health = maxHealth;
        } else if (health < 0) {
            health = 0;
        }
    }
}
