public class TextStyle {

    // ANSI escape codes:
    public static final String RESET = "\u001B[0m";
    public static final String RESET_BOLD = "\u001B[22m";
    public static final String RESET_INVERT = "\u001B[27m";
    public static final String RESET_FRAMED = "\u001B[54m";
    public static final String YELLOW_FG = "\u001B[33m";
    public static final String GREEN_FG = "\u001B[32m";
    public static final String LIGHT_BLACK_FG = "\u001B[90m";
    public static final String FRAMED = "\u001B[51m";
    public static final String INVERT = "\u001B[7m";
    public static final String BOLD = "\u001B[1m";

    // Metoden tager en string ind og returnerer en string magen til men i farver.
    public static String format(String s) {

        // <tekst> bliver udskiftet med gul tekst.
        s = s.replace('<', 'Æ');
        s = s.replace('>', 'Ø');

        // [tekst] bliver udskiftet med grøn tekst.
        s = s.replace('[', 'Å');
        s = s.replace(']', 'Ø');

        // {tekst} bliver udskiftet med tekst med ramme om sig.
        s = s.replace('{', 'å');
        s = s.replace('}', 'ø');

        // Konvertering. Teknisk: Pas på med regex. Jeg har valgt danske bogstaver, så der ikke kan gå noget galt.
        s = s.replaceAll("Ø", RESET);
        s = s.replaceAll("ø", " " + RESET);
        s = s.replaceAll("Æ", YELLOW_FG);
        s = s.replaceAll("Å", GREEN_FG);
        s = s.replaceAll("å", FRAMED + " ");

        return s;
    }
}