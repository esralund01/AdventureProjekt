public class Item {

    // Attributes
    private final String longName;
    private final String shortName;

    // Constructor
    public Item(String longName) {
        String shortName = "";
        try {
            // Gør <tekst> i longName til shortName.
            shortName = longName.substring(longName.indexOf('<') + 1, longName.indexOf('>'));
        }
        catch (StringIndexOutOfBoundsException ignored) {
            System.out.printf("\n%s Fejl %s\n%s-objekt ved navn '%s' har ikke fået sit <shortName> angivet korrekt.\n", TextStyle.FRAMED, TextStyle.RESET, this.getClass().toString().substring(6), longName);
        }
        this.shortName = shortName;
        this.longName = TextStyle.format(longName); // Ændrer <tekst> til farvet tekst, se TextStyle-klassen.
    }

    // Getters
    public String getLongName() {
        return longName;
    }

    public String getShortName() {
        return shortName;
    }
}
