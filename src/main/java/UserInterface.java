import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {

    // Attributes
    private final Scanner scanner;
    private final Adventure adventure;
    private boolean gameIsRunning;

    // Constructor
    public UserInterface() {
        scanner = new Scanner(System.in);
        scanner.useDelimiter("\n"); // Forhindrer bøvl med mellemrum.
        adventure = new Adventure();
        gameIsRunning = false;
    }

    // Method
    public void startProgram() {
        gameIsRunning = true;
        System.out.printf("\n%s          Welcome to          \n%s          ADVENTURE           %s\n%s           the game           %s\n\n", TextStyle.YELLOW_FG + TextStyle.FRAMED, TextStyle.RESET_FRAMED + TextStyle.INVERT + TextStyle.BOLD, TextStyle.RESET_BOLD + TextStyle.RESET_INVERT, TextStyle.FRAMED, TextStyle.RESET);
        look();
        adventure.getCurrentRoom().visit();
        while (gameIsRunning) {
            System.out.printf("\n%sEnter command:%s ", TextStyle.BRIGHT_BLACK_FG, TextStyle.RESET);
            String command = scanner.next().toLowerCase();
            System.out.println();
            switch (command) {
                case "n", "e", "w", "s" -> go(command);
                case "exit" -> exit();
                case "look" -> look();
                case "turn on light" -> turnLight(true);
                case "turn off light" -> turnLight(false);
                case "xyzzy" -> xyzzy();
                case "inventory", "invent", "inv" -> inventory();
                case "health" -> health();
                case "attack" -> attack();
                // Kommandoer med variable: Pattern matching kræver Java 21.
                case String s when s.startsWith("go ") -> go(s.substring(3));
                case String s when s.startsWith("take ") -> take(s.substring(5));
                case String s when s.startsWith("drop ") -> drop(s.substring(5));
                case String s when s.startsWith("eat ") -> consume(true, s.substring(4));
                case String s when s.startsWith("drink ") -> consume(false, s.substring(6));
                case String s when s.startsWith("equip ") -> equip(s.substring(6));
                case String s when s.contains("help") -> help(); // Så er vi i hvert fald helt sikre på, at man kan få hjælp.
                default -> System.out.printf("Could not recognize '%s'. Enter %shelp%s to view available commands.\n", command, TextStyle.GREEN_FG, TextStyle.RESET);
            }
        }
    }

    // Auxiliary methods
    private String nOrNot(String s) {
        // Denne metode kan bruges, når man har et ubestemt artikel foran en variabel,
        // fx "a horse" eller "an apple", men ikke ved om det er "horse" eller "apple",
        // og derfor ikke ved, som det skal være "a" eller "an".
        // Metoden tager variablen som parameter og returnerer "n " + variabel foran a, e, i og o, "(n) " + variabel foran u og ellers " " + variabel.
        // Man kan derfor skrive A + nOrNot(variabel) eller a + nOrNot(variabel), så kommer der måske et n samt et mellemrum.
        return (s.startsWith("a") || s.startsWith("e") || s.startsWith("i") || s.startsWith("o") ? "n " : s.startsWith("u") ? "(n) " : " ") + s;
    }

    private void exit() {
        gameIsRunning = false;
        System.out.println("Quitting game...");
    }

    private void help() {
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
        commands.add("[turn] <_> [light] turns the light <on> or <off> if possible.");
        for (String command : commands) {
            command = TextStyle.color(command);
            System.out.println("  " + command);
        }
        System.out.println("Commands are shown in green and selectables are shown in yellow.");
    }

    private void look() {
        System.out.printf("You are in %s, a%s.\n", adventure.getCurrentRoom().getName(), nOrNot(adventure.getCurrentRoom().getDescription()));
    }

    private void turnLight(boolean on) {
        boolean wasDark = adventure.getCurrentRoom().getIsDark();
        if (adventure.getCurrentRoom().turnLight(on)) {
            if (wasDark == on) {
                System.out.printf("Turning %s the light...\n", on ? "on" : "off"); // Ternary operator.
                look();
            } else {
                System.out.printf("The light was already %s.\n", on ? "on" : "off"); // Ternary operator.
            }
        } else {
            System.out.printf("%s doesn't have that feature.\n", adventure.getCurrentRoom().getName());
        }
    }

    private void xyzzy() {
        adventure.teleport();
        System.out.println("A magic portal opens and you go through it...");
        System.out.printf("You are in %s again.\n", adventure.getCurrentRoom().getName());
    }

    private void inventory() {
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

    private void health() {
        System.out.printf("Your health is %d out of %d. You are in ", adventure.getHealth(), adventure.getMaxHealth());
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

    private void go(String input) { // mange flere enum muligheder
        String direction = switch (input) {
            case "n" -> "north";
            case "e" -> "east";
            case "w" -> "west";
            case "s" -> "south";
            default -> "";
        };
        if (direction.isEmpty()) { //FORKER
            System.out.printf("Could not recognize '%s' as a cardinal direction.\n", input);
        } else {
            System.out.printf("Going %s...\n", direction);
            if (adventure.go(direction) == State.SUCCESS) {
                if (adventure.getCurrentRoom().getIsAlreadyVisited()) {
                    System.out.printf("You are in %s again.\n", adventure.getCurrentRoom().getName());
                } else {
                    look();
                    adventure.getCurrentRoom().visit();
                }
            } else {
                System.out.println("You cannot go that way.");
            }
        }
    }

    private void take(String itemWord) {
        switch (adventure.take(itemWord)) {
            case NOT_FOUND -> System.out.printf("Could not find '%s' in %s", itemWord, adventure.getCurrentRoom().getName());
            case SUCCESS -> System.out.printf("The %s has been moved to your inventory", itemWord);
        }
        System.out.println(".");
    }

    private void drop(String itemWord) {
        switch (adventure.take(itemWord)) {
            case NOT_FOUND -> System.out.printf("Could not find '%s' in your inventory", itemWord);
            case SUCCESS -> System.out.printf("The %s has been removed from your inventory and dropped in %s", itemWord, adventure.getCurrentRoom().getName());
        }
        System.out.println(".");
    }

    private void consume(boolean food, String itemWord) {
        int oldHealth = adventure.getHealth();
        switch (adventure.consume(food, itemWord)) {
            case NOT_FOUND -> System.out.printf("Could not find '%s' in %s or your inventory.\n", itemWord, adventure.getCurrentRoom().getName());
            case WRONG_TYPE -> System.out.printf("A%s isn't %s.\n", nOrNot(itemWord), food ? "edible" : "drinkable");
            case SUCCESS -> {
                System.out.printf("%s the %s...\n", food ? "Eating" : "Drinking", itemWord);
                int healthPoints = adventure.getHealth() - oldHealth;
                if (healthPoints > 0) {
                    System.out.printf("Your health has increased by %d", healthPoints);
                } else if (healthPoints < 0) {
                    System.out.printf("That wasn't good for you. Your health has decreased by %d", healthPoints * -1);
                } else {
                    System.out.printf("Your health is unchanged, but your %s", food ? "stomach is fuller" : "thirst is quenched");
                }
            }
        }
        System.out.println(".");
    }

    private void equip(String itemWord) {
        switch (adventure.equip(itemWord)) {
            case NOT_FOUND -> System.out.printf("Could not find '%s' in your inventory", itemWord);
            case WRONG_TYPE -> System.out.printf("A%s is not a weapon", nOrNot(itemWord));
            case SUCCESS -> System.out.printf("Your %s is equipped and ready for combat", itemWord);
        }
        System.out.println(".");
    }

    private void attack (){
        Enemy enemy = adventure.getCurrentRoom().getEnemies();
        System.out.println("enemy  health before attack: " + enemy.getHealth());
        System.out.println("player health before enemy strikes back: " + adventure.getHealth());
        switch (adventure.attack()){
           case State.FAILURE ->  System.out.println("No weapon is equipped, you cannot attack");
            case State.SUCCESS -> {
                adventure.getEquipped().use();

              //  Enemy enemy = adventure.getCurrentRoom().getEnemies().getFirst();
                System.out.println("Attack!!");
                System.out.println("enemy health after attack: " + enemy.getHealth());


                System.out.println(enemy.getName() + " attacks back");
                System.out.println("player health after enemy attack: " + adventure.getHealth());
            }
            case State.NO_AMMO ->  System.out.println("No projectiles left in this weapon...");
            case State.DEATH -> System.out.println("You killed the enemy. Their body is gone, but their weapon is still in the room");
        }
    }
}