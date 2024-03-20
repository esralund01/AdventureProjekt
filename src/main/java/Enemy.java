public class Enemy extends Character{

    //Attributes
    private String name;
    private String description;

    public Enemy(String name, String description, int health, Weapon weapon) {
        this.name = name;
        this.description = description;
        super.health = health;
        super.equipped = weapon;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void attack(Character player){
        player.hit(equipped.getHitPoints());
    }


}

