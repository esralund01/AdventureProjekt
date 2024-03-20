public class Enemy extends Character {

    //Attributes
    private final String shortName;
    private final String longName;

    // Constructor
    public Enemy(String longName, int health, Weapon weapon) {
        super(health);
        setEquipped(weapon);
        String shortName = "";
        try {
            // Gør <tekst> i longName til shortName.
            shortName = longName.substring(longName.indexOf('<') + 1, longName.indexOf('>'));
        }
        catch (StringIndexOutOfBoundsException ignored) {
            System.out.printf("\n%sFejl:%s %s-objekt ved navn '%s' har ikke fået sit <shortName> angivet korrekt.\n", TextStyle.BRIGHT_RED_FG, TextStyle.RESET, this.getClass().toString().substring(6), longName);
        }
        this.shortName = shortName;
        this.longName = TextStyle.color(longName); // Ændrer <tekst> i longName til farvet tekst, se TextStyle-klassen.
    }

    // Getters
    public String getShortName() {
        return shortName;
    }

    public String getLongName() {
        if (getHealth() == 0) {
            return longName;
        }
        return longName + " carrying " + getEquipped().getLongName();
    }
}