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
        System.out.println("\nWelcome to AdventureGame!");
        look();
        adventure.getCurrentRoom().visit();
        while (gameIsRunning) {
            System.out.print("\nEnter command: ");
            String command = scanner.next().toLowerCase();
            System.out.println();
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
                case String s when s.startsWith("equip ") -> equip(s.substring(6));
                default -> System.out.printf("Could not recognize '%s'. Enter 'help' to view available commands.\n", command);
            }
        }
    }

    // Auxiliary methods
    private String nOrNot(String s) {
        // Denne metode kan bruges, når man har et ubestemt artikel foran en variabel,
        // fx "a horse" eller "an apple", men ikke ved om det er "horse" eller "apple",
        // og derfor ikke ved, som det skal være "a" eller "an".
        // Metoden tager variablen som parameter og returnerer "n" eller "" (tom string).
        return s.startsWith("a") || s.startsWith("e") || s.startsWith("i") || s.startsWith("o") ? "n" : "";
    }

    private void exit() {
        gameIsRunning = false;
        System.out.println("Quitting game...");
    }

    private void help() {
        System.out.println("Available commands:");
        System.out.println("  'go <direction>' takes you in that direction if possible. This is available for north, east, west, and south.");
        System.out.println("  'exit' quits the game.");
        System.out.println("  'look' gives you a description of the place you are in.");
        System.out.println("  'turn <on/off> light' turns the light on or off if possible.");
        System.out.println("  'inventory', 'invent' or 'inv' shows your inventory.");
        System.out.println("  'take <item>' takes an item from the room into your inventory.");
        System.out.println("  'drop <item>' removes an item from your inventory and drops it in the place you're in.");
        System.out.println("  'health' shows your health.");
        System.out.println("  'eat <food>' lets you eat a piece of food to restore health. Be weary though.");
        System.out.println("  'equip <weapon>' lets you equip a weapon from your inventory, so you can use it.");
        System.out.println("Don't include ' or < or > or / in your command.");
    }

    private void look() {
        System.out.printf("You are in %s, a%s %s.\n", adventure.getCurrentRoom().getName(), nOrNot(adventure.getCurrentRoom().getDescription()), adventure.getCurrentRoom().getDescription());
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
                System.out.printf("  %s%s\n", item.getLongName(), item == adventure.getEquipped() ? "  <--- equipped and ready to use" : "");
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
            System.out.println("You cannot go that way.");
        }
    }

    private void take(String itemWord) {
        Item found = adventure.getCurrentRoom().findInRoom(itemWord);
        if (found != null) {
            adventure.getCurrentRoom().removeFromRoom(found);
            adventure.addToInventory(found);
            System.out.printf("The %s has been moved to your inventory", itemWord);
        } else {
            System.out.printf("Could not find '%s' in %s", itemWord, adventure.getCurrentRoom().getName());
        }
        System.out.println(".");
    }

    private void drop(String itemWord) {
        Item found = adventure.findInInventory(itemWord);
        if (found != null) {
            adventure.removeFromInventory(found);
            adventure.getCurrentRoom().addToRoom(found);
            System.out.printf("The %s has been removed from your inventory and dropped in %s", itemWord, adventure.getCurrentRoom().getName());
        } else {
            System.out.printf("Could not find '%s' in your inventory", itemWord);
        }
        System.out.println(".");
    }

    private void eat(String itemWord) {
        Item foundInRoom = adventure.getCurrentRoom().findInRoom(itemWord);
        Item foundInInventory = adventure.findInInventory(itemWord);
        if (foundInRoom == null && foundInInventory == null) {
            System.out.printf("Could not find '%s' in %s or your inventory", itemWord, adventure.getCurrentRoom().getName());
        } else {
            Food food = null;
            if (foundInRoom instanceof Food) {
                food = (Food) foundInRoom;
                adventure.getCurrentRoom().removeFromRoom(foundInRoom);
            } else if (foundInInventory instanceof Food) {
                food = (Food) foundInInventory;
                adventure.removeFromInventory(foundInInventory);
            }
            if (food == null) {
                System.out.printf("A%s %s isn't edible", nOrNot(itemWord), itemWord);
            } else {
                int oldHealth = adventure.getHealth();
                adventure.eat(food);
                System.out.printf("Eating %s...\n", itemWord);
                int healthPoints = adventure.getHealth() - oldHealth;
                if (healthPoints > 0) {
                    System.out.printf("Your health has increased by %d", healthPoints);
                } else if (healthPoints < 0) {
                    System.out.printf("That wasn't good for you. Your health has decreased by %d", healthPoints * -1);
                } else {
                    System.out.print("Your health is unchanged, but your stomach is fuller");
                }
            }
        }
        System.out.println(".");
    }
    private void equip(String itemWord){
        Item item = adventure.findInInventory(itemWord);
        if (item == null) {
            System.out.printf("Could not find '%s' in your inventory", itemWord);
        } else {
            if (item instanceof Weapon) {
                adventure.equip((Weapon) item);
                System.out.printf("Your %s is equipped and ready to use", itemWord);
            } else {
                System.out.printf("A%s %s is not a weapon", nOrNot(itemWord), itemWord);
            }
        }
        System.out.println(".");
    }
}