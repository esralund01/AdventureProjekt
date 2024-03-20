public class TextStyle {

    // ANSI escape codes:
    public static final String RESET = "\u001B[0m";
    public static final String RESET_BOLD = "\u001B[22m";
    public static final String RESET_INVERT = "\u001B[27m";
    public static final String RESET_FRAMED = "\u001B[54m";
    public static final String YELLOW_FG = "\u001B[33m";
    public static final String GREEN_FG = "\u001B[32m";
    public static final String BRIGHT_RED_FG = "\u001B[91m";
    public static final String BRIGHT_BLACK_FG = "\u001B[90m";
    public static final String FRAMED = "\u001B[51m";
    public static final String INVERT = "\u001B[7m";
    public static final String BOLD = "\u001B[1m";

    // Metoden tager en string ind og returnerer en string magen til men i farver.
    public static String color(String str) {
        String s = str;

        // <tekst> bliver udskiftet med gul tekst.
        s = s.replace('<', 'Æ');
        s = s.replace('>', 'Ø');

        // [tekst] bliver udskiftet med grøn tekst.
        s = s.replace('[', 'Å');
        s = s.replace(']', 'Ø');

        // Konvertering. Teknisk: Pas på med regex. Jeg har valgt danske bogstaver, så der ikke kan gå noget galt.
        s = s.replaceAll("Ø", RESET);
        s = s.replaceAll("Æ", YELLOW_FG);
        s = s.replaceAll("Å", GREEN_FG);

        return s;
    }

    public static String number(int i) {
        return switch (i) {
            case 0 -> "no more";
            case 1 -> "a single";
            case 2 -> "a couple of";
            case 3 -> "three";
            case 4 -> "four";
            case 5 -> "five";
            case 6 -> "half a dozen";
            case 7 -> "seven";
            case 8 -> "eight";
            case 9 -> "nine";
            case 10 -> "ten";
            case 11 -> "eleven";
            case 12 -> "a dozen";
            // Fortsæt evt. med "score" (20), "two dozen" (24), "two score" (40) osv.
            default -> String.valueOf(i);
        };
    }
}