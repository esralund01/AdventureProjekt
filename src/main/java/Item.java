public class Item {

    // Attributes
    private String longName;
    private final String shortName;

    // Constructor
    public Item(String longName) {
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
    public String getLongName() {
        return longName;
    }

    public String getShortName() {
        return shortName;
    }

    // Setter
    public void setLongName(String longName) {
        this.longName = longName;
    }
}
