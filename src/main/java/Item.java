public class Item {

    // Attributes
    private final String longName;
    private final String shortName;

    // Constructor
    public Item(String longName) {
        this.longName = longName;
        if (longName.contains("<") && longName.contains(">")) {
            shortName = longName.substring(longName.indexOf('<') + 1, longName.indexOf('>'));
        } else {
            shortName = longName;
        }
    }

    // Getters
    public String getLongName() {
        return longName;
    }

    public String getShortName() {
        return shortName;
    }
}
