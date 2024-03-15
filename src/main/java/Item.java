public class Item {

    // Attributes
    private final String longName;
    private final String shortName;

    // Constructor
    public Item(String longName) {
        this.longName = longName;
        String shortName;
        try {
            shortName = longName.substring(longName.indexOf('<') + 1, longName.indexOf('>'));
        }
        catch (IndexOutOfBoundsException ioobe) {
            shortName = longName;
        }
        this.shortName = shortName;
    }

    // Getters
    public String getLongName() {
        return longName;
    }

    public String getShortName() {
        return shortName;
    }
}
