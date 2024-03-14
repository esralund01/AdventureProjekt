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
        System.out.println("Welcome to AdventureGame!");
        look();
        adventure.getCurrentRoom().visit();
        while (gameIsRunning) {
            System.out.print("Enter command: ");
            String command = scanner.next().toLowerCase();
            switch (command) {
                case "n", "e", "w", "s" -> go(command);
                case "exit" -> exit();
                case "help" -> help();
                case "look" -> look();
                case "turn on light" -> turnLight(true);
                case "turn off light" -> turnLight(false);
                case "inventory", "invent", "inv" -> inventory();
                case "health" -> health();
                // Kommandoer med variable: Pattern matching kræver Java 21.
                case String s when s.startsWith("go ") -> go(s.substring(3));
                case String s when s.startsWith("take ") -> take(s.substring(5));
                case String s when s.startsWith("drop ") -> drop(s.substring(5));
                case String s when s.startsWith("eat ") -> eat(s.substring(4));
                default -> System.out.printf("Could not recognize '%s'. Enter 'help' to view available commands.\n", command);
            }
        }
    }

    // Auxiliary methods
    private void exit() {
        gameIsRunning = false;
        System.out.println("Quitting game.");
    }

    private void help() {
        System.out.println("Available commands:");
        System.out.println("- 'go north', 'go n' or 'n' takes you north. This is also available for south, west, and east.");
        System.out.println("- 'exit' quits the game.");
        System.out.println("- 'look' gives you a description of the place you are in.");
        System.out.println("- 'turn on/off light' turns on/off the light.");
        System.out.println("- 'inventory', 'invent' or 'inv' shows your inventory.");
        System.out.println("- 'take <item>' puts an item in your inventory.");
        System.out.println("- 'drop <item>' removes an item from your inventory.");
        System.out.println("- 'health' shows your health.");
        System.out.println("- 'eat <food>' lets you eat a piece of food to restore health.");
    }

    private void look() {
        System.out.printf("You are in %s, a %s.\n", adventure.getCurrentRoom().getName(), adventure.getCurrentRoom().getDescription());
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

    private void inventory() {
        ArrayList<Item> inventory = adventure.getInventory();
        if (inventory.isEmpty()) {
            System.out.println("Your inventory is empty.");
        } else {
            System.out.println("Your inventory contains:");
            for (Item item : inventory) {
                System.out.printf("- %s.\n", item.getLongName());
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

    private void go(String input) {
        String direction = switch (input) {
            case "n" -> "north";
            case "e" -> "east";
            case "w" -> "west";
            case "s" -> "south";
            default -> input;
        };
        if (adventure.go(direction)) {
            System.out.printf("Going %s...\n", direction);
            if (adventure.getCurrentRoom().getIsAlreadyVisited()) {
                System.out.printf("You are in %s again.\n", adventure.getCurrentRoom().getName());
            } else {
                look();
                adventure.getCurrentRoom().visit();
            }
        } else {
            System.out.println("Cannot go that way.");
        }
    }

    private void take(String itemWord) {
        if (adventure.take(itemWord)) {
            System.out.printf("The %s has been moved to your inventory.\n", itemWord);
        } else {
            System.out.printf("Could not find '%s' in %s.\n", itemWord, adventure.getCurrentRoom().getName());
        }
    }

    private void drop(String itemWord) {
        if (adventure.drop(itemWord)) {
            System.out.printf("The %s has been removed from your inventory and dropped in %s.\n", itemWord, adventure.getCurrentRoom().getName());
        } else {
            System.out.printf("Could not find '%s' in your inventory.\n", itemWord);
        }
    }

    private void eat(String itemWord) {
        int oldHealth = adventure.getHealth();
        if (adventure.eat(itemWord)) {
            System.out.printf("Eats %s...\n", itemWord);
            int healthPoints = adventure.getHealth() - oldHealth;
            if (healthPoints > 0) {
                System.out.printf("Your health has increased by %d", healthPoints);
            } else if (healthPoints < 0) {
                System.out.printf("That wasn't good for you. Your health has decreased by %d", healthPoints * -1);
            } else {
                System.out.print("Your health is unchanged, but your stomach is fuller");
            }
            System.out.println(".");
        } else {
            System.out.printf("'%s' wasn't edible or could not be found in %s or your inventory.\n", itemWord, adventure.getCurrentRoom().getName());
        }
    }
}