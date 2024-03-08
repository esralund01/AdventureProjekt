public class Item {

    // Attributes
    private final String longName;
    private final String shortName;

    // Constructor
    public Item(String longName, String shortName) {
        this.longName = longName;
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
