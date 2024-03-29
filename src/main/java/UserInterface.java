import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {

    // Attributes
    private final Scanner scanner;
    private final Adventure adventure;
    private boolean gameIsRunning;
    private String command;
    private final Window window;

    // Constructor
    public UserInterface() {
        scanner = new Scanner(System.in);
        scanner.useDelimiter("\n"); // Forhindrer bøvl med mellemrum.
        adventure = new Adventure();
        gameIsRunning = false;
        window = new Window(this, adventure);
        System.out.printf("\n%s          Welcome to          \n%s          ADVENTURE           %s\n%s           the game           %s\n\n", TextStyle.YELLOW_FG + TextStyle.FRAMED, TextStyle.RESET_FRAMED + TextStyle.INVERT + TextStyle.BOLD, TextStyle.RESET_BOLD + TextStyle.RESET_INVERT, TextStyle.FRAMED, TextStyle.RESET);
        look();
        System.out.println();
    }

    // Start methods
    public void startProgram() {
        gameIsRunning = true;
        while (gameIsRunning) {
            System.out.printf("%sEnter command:%s ", TextStyle.BRIGHT_BLACK_FG, TextStyle.RESET);
            command = scanner.next();
            System.out.println();
            switch (command.toLowerCase()) {
                case "control" -> startWindow();
                case "n", "e", "w", "s" -> go(command);
                case "exit" -> exit();
                case "look" -> look();
                case "turn on light" -> turnLight(true);
                case "turn off light" -> turnLight(false);
                case "xyzzy" -> xyzzy();
                case "inventory", "invent", "inv" -> inventory();
                case "health" -> health();
                case "attack", "a" -> attack("");
                // Kommandoer med variable: Pattern matching kræver Java 21.
                case String s when s.startsWith("go ") -> go(s.substring(3));
                case String s when s.startsWith("take ") -> take(s.substring(5));
                case String s when s.startsWith("drop ") -> drop(s.substring(5));
                case String s when s.startsWith("eat ") -> consume(true, s.substring(4));
                case String s when s.startsWith("drink ") -> consume(false, s.substring(6));
                case String s when s.startsWith("equip ") -> equip(s.substring(6));
                case String s when s.startsWith("attack ") -> attack(s.substring(7));
                case String s when s.contains("help") -> help(); // Så er vi i hvert fald helt sikre på, at man kan få hjælp.
                default -> System.out.printf("Could not recognize '%s'. Enter %shelp%s to view available commands.\n", command, TextStyle.GREEN_FG, TextStyle.RESET);
            }
            if (!command.equals("control")) {
                System.out.println();
            }
        }
    }

    private void startWindow() {
        gameIsRunning = false;
        window.showWindow();
    }

    // Auxiliary methods
    private String nOrNot(String s) {
        // Denne metode kan bruges, når man har et ubestemt artikel foran en variabel,
        // fx "a horse" eller "an apple", men ikke ved om det er "horse" eller "apple",
        // og derfor ikke ved, som det skal være "a" eller "an".
        // Metoden tager variablen som parameter og returnerer "n " + variabel foran a, e, i og o, "(n) " + variabel foran u og ellers " " + variabel.
        // Man kan derfor skrive "A" + nOrNot(variabel) eller "a" + nOrNot(variabel), så kommer der måske et n samt et mellemrum mellem a/A o variablen.
        return (s.startsWith("a") || s.startsWith("e") || s.startsWith("i") || s.startsWith("o") ? "n " : s.startsWith("u") ? "(n) " : " ") + s;
    }

    public String aToTheUpperCase(String s) {
        if (s.startsWith("a ")) {
            s = "The" + s.substring(1);
        } else if (s.startsWith("an ")) {
            s = "The" + s.substring(2);
        } else if (s.startsWith("the ")) {
            s = "T" + s.substring(1);
        }
        return s;
    }

    private String theIfNotName(String s) {
        if (!java.lang.Character.isUpperCase(s.charAt(0))) {
            s = "the " + s;
        }
        return s;
    }

    private void deathCheck() {
        if (adventure.getHealth() == 0) {
            System.out.println("You died.");
            exit();
        }
    }

    // Command methods
    public void exit() {
        window.killWindow();
        gameIsRunning = false;
        System.out.println("Game over.");
        System.exit(0);
    }

    public void help() {
        System.out.println("Available commands:");
        ArrayList<String> commands = new ArrayList<>();
        commands.add("[exit] quits the game.");
        commands.add("[look] gives you a description of the place you are in.");
        commands.add("[inventory] shows your inventory.");
        commands.add("[health] shows your health.");
        commands.add("[go] <_> takes you in the selected direction if possible. Available directions are <north>, <east>, <west>, and <south>.");
        commands.add("[take] <_> moves the selected item to your inventory.");
        commands.add("[drop] <_> removes the selected item from your inventory and drops it in the place you're in.");
        commands.add("[eat] <_> lets you eat the selected food to restore health.");
        commands.add("[drink] <_> lets you drink the selected liquid to restore health.");
        commands.add("[equip] <_> lets you equip the selected weapon for combat.");
        commands.add("[attack] <_> lets you attack the selected enemy.");
        commands.add("[attack] lets you attack a previously selected enemy if any.");
        commands.add("[turn] <_> [light] turns the light <on> or <off> if possible.");
        for (String command : commands) {
            command = TextStyle.color(command);
            System.out.println("  " + command);
        }
        System.out.println("Commands are shown in green and selectables are shown in yellow.");
    }

    public void look() {
        System.out.printf("You are in %s, %s.\n", adventure.getCurrentRoom().getName(), adventure.getCurrentRoom().getDescription());
    }

    public void turnLight(boolean on) {
        boolean wasDark = adventure.getCurrentRoom().getIsDark();
        if (adventure.getCurrentRoom().turnLight(on)) {
            if (wasDark == on) {
                System.out.printf("You're turning %s the light...\nYou are in %s.\n", on ? "on" : "off", adventure.getCurrentRoom().getDescription()); // Ternary operator.
            } else {
                System.out.printf("The light was already %s.\n", on ? "on" : "off"); // Ternary operator.
            }
        } else {
            System.out.printf("%s doesn't have that feature.\n", aToTheUpperCase(adventure.getCurrentRoom().getName()));
        }
    }

    public void xyzzy() {
        System.out.printf("A magic portal opens in %s and you're going through it...\n", adventure.getCurrentRoom().getName());
        adventure.teleport();
        System.out.printf("You are in %s again.\n", adventure.getCurrentRoom().getName());
    }

    public void inventory() {
        ArrayList<Item> inventory = adventure.getInventory();
        if (inventory.isEmpty()) {
            System.out.println("Your inventory is empty.");
        } else {
            System.out.println("Your inventory contains:");
            for (Item item : inventory) {
                System.out.println("  " + item.getLongName() + (item == adventure.getEquipped() ? ", equipped and ready for combat" : ""));
            }
        }
    }

    public void health() {
        System.out.printf("Your health is %d/%d hit points. You are in ", adventure.getHealth(), adventure.getMaxHealth());
        int healthPercentage = adventure.getHealth() * 100 / adventure.getMaxHealth();
        if (healthPercentage > 75) {
            System.out.print("good health and ready to fight");
        } else if (healthPercentage > 50) {
            System.out.print("good health but avoid fighting right now");
        } else if (healthPercentage > 25) {
            System.out.print("poor health so avoid fighting right now");
        } else {
            System.out.print("poor health and close to death");
        }
        System.out.println(".");
    }

    public void go(String directionWord) {
        String dw = switch (directionWord.toLowerCase()) {
            case "n" -> "north";
            case "e" -> "east";
            case "w" -> "west";
            case "s" -> "south";
            default -> directionWord;
        };
        switch (adventure.go(dw)) {
            case NOT_FOUND -> System.out.printf("Could not recognize '%s' as a cardinal direction.\n", command.substring(3)); // Pas på med den her substring.
            case NO_ACCESS -> System.out.printf("You're in %s.\n", adventure.getCurrentRoom().getDescription());
            case NULL -> System.out.printf("You can't go %s.\n", dw);
            case SUCCESS -> {
                System.out.printf("You're leaving %s and going %s...\n", adventure.getPreviousRoom().getName(), dw);
                if (adventure.getCurrentRoom().getIsAlreadyVisited()) {
                    System.out.printf("You are in %s again.\n", adventure.getCurrentRoom().getName());
                } else {
                    look();
                }
            }
        }
    }

    public void take(String itemWord) {
        switch (adventure.take(itemWord)) {
            case NOT_FOUND -> System.out.printf("Could not find '%s' in %s", command.substring(5), adventure.getCurrentRoom().getName());
            case NO_ACCESS -> System.out.printf("Oh, that %s? Good luck trying to steal that", itemWord);
            case SUCCESS -> System.out.printf("You're taking the %s...\nThe %s has been moved to your inventory", itemWord, itemWord);
        }
        System.out.println(".");
    }

    public void drop(String itemWord) {
        switch (adventure.drop(itemWord)) {
            case NOT_FOUND -> System.out.printf("Could not find '%s' in your inventory", command.substring(5));
            case SUCCESS -> System.out.printf("You're dropping the %s in %s...\nThe %s has been removed from your inventory and dropped in %s", itemWord, adventure.getCurrentRoom().getName(), itemWord, adventure.getCurrentRoom().getName());
        }
        System.out.println(".");
    }

    public void consume(boolean food, String itemWord) {
        switch (adventure.consume(food, itemWord)) {
            case NOT_FOUND -> System.out.printf("Could not find '%s' in %s or your inventory.\n", food ? command.substring(4) : command.substring(6), adventure.getCurrentRoom().getName());
            case WRONG_TYPE -> System.out.printf("A%s isn't %s.\n", nOrNot(itemWord), food ? "edible" : "drinkable");
            case SUCCESS -> {
                System.out.printf("You're %s the %s...\n", food ? "eating" : "drinking", itemWord);
                int healthDifference = adventure.getHealth() - adventure.getOldHealth();
                if (healthDifference > 0) {
                    System.out.printf("You gained %d hit points.\n", healthDifference);
                } else if (healthDifference < 0) {
                    System.out.printf("That wasn't good for you. You lost %d hit points.\n", healthDifference * -1);
                    deathCheck();
                } else {
                    System.out.printf("Your health is unchanged, but your %s.\n", food ? "stomach is fuller" : "thirst is quenched");
                }
            }
        }
    }

    public void equip(String itemWord) {
        switch (adventure.equip(itemWord)) {
            case NOT_FOUND -> System.out.printf("Could not find '%s' in your inventory", command.substring(6));
            case WRONG_TYPE -> System.out.printf("A%s is not a weapon", nOrNot(itemWord));
            case SUCCESS -> System.out.printf("You're equipping yourself with the %s...\nYour %s is now ready for combat", itemWord, itemWord);
        }
        System.out.println(".");
    }

    public void attack(String name) {
        switch (adventure.attack(name)) {
            case NULL -> System.out.println("You don't have a weapon ready.");
            case NO_ACCESS -> System.out.printf("Your %s has no more ammunition.\n", adventure.getEquipped().getShortName());
            case NOT_FOUND -> {
                if (name.isEmpty()) {
                    System.out.printf("You're attacking...\nYou used your weapon in vain, because you haven't selected an enemy in %s.\n", adventure.getCurrentRoom().getName());
                } else {
                    System.out.printf("You're attacking '%s'...\nYou used your weapon in vain, because '%s' could not be found in %s.\n", command.substring(7), command.substring(7), adventure.getCurrentRoom().getName());
                }
            }
            case SUCCESS -> {
                System.out.printf("You're attacking %s...\n", theIfNotName(adventure.getSelected().getShortName()));
                int opponentHealthDifference =  adventure.getSelected().getOldHealth() - adventure.getSelected().getHealth();
                System.out.printf("You wield your %s against %s, who then loses %d hit points.\n", adventure.getEquipped().getShortName().toLowerCase(), theIfNotName(adventure.getSelected().getShortName()), opponentHealthDifference);
                if (adventure.getSelected().getHealth() == 0) {
                    System.out.printf("%s died in combat and left %s.\n", aToTheUpperCase(adventure.getSelected().getLongName()), adventure.getSelected().getEquipped().getLongName());
                }
                else {
                    int playerHealthDifference = adventure.getOldHealth() - adventure.getHealth();
                    System.out.printf("%s wields the %s against you. You lose %d hit points.\n", aToTheUpperCase(theIfNotName(adventure.getSelected().getShortName())), adventure.getSelected().getEquipped().getShortName().toLowerCase(), playerHealthDifference);
                    deathCheck();
                }
            }
        }
    }
}