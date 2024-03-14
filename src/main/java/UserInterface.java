import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {

    // Attributes
    private final Scanner scanner;
    private final Adventure adventure;

    // Constructor
    public UserInterface() {
        scanner = new Scanner(System.in);
        scanner.useDelimiter("\n"); // Forhindrer bøvl med mellemrum.
        adventure = new Adventure();
    }

    // Method
    public void startProgram() {
        boolean gameIsRunning = true;
        System.out.println("Welcome to AdventureGame!");
        look();
        adventure.getCurrentRoom().visit();
        while (gameIsRunning) {
            System.out.print("Enter command: ");
            String command = scanner.next().toLowerCase();
            switch (command) {
                // Kommandoer uden variable.
                case "n", "e", "w", "s" -> go(command);
                case "exit" -> {
                    gameIsRunning = false;
                    System.out.println("Quitting game.");
                }
                case "help" -> help();
                case "look" -> look();
                case "turn on light" -> {
                    adventure.turnOnLight();
                    System.out.println("Turning on light...");
                    look();
                }
                case "inventory", "invent", "inv" -> {
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
                // Kommandoer med variable: Pattern matching kræver Java 21.
                case String s when s.startsWith("go ") -> go(command.substring(3));
                case String s when s.startsWith("take ") -> {
                    String item = s.substring(5);
                    if (adventure.take(item)) {
                        System.out.printf("%s is added to your inventory.\n", item);
                    } else {
                        System.out.printf("Could not find %s in this room.\n", item);
                    }
                }
                case String s when s.startsWith("drop ") -> {
                    String item = s.substring(5);
                    if (adventure.drop(item)) {
                        System.out.printf("%s is removed from inventory.\n", item);
                    } else {
                        System.out.printf("Could not find %s in your inventory.\n", item);
                    }
                }
                // Kommando, der ikke kunne genkendes.
                default -> System.out.println("Could no recognize command. Enter 'help' to view available commands.");
            }
        }
    }

    // Auxiliary methods
    private void go(String direction) {
        if (adventure.go(direction)) {
            System.out.printf("Going %s...\n", direction);
            if (adventure.getCurrentRoom().isVisited()) {
                System.out.printf("You are in %s again.\n", adventure.getCurrentRoom().getName());
            } else {
                look();
                adventure.getCurrentRoom().visit();
            }
        } else {
            System.out.println("You cannot go that way.");
        }
    }

    private void help() {
        System.out.println("Available commands:");
        System.out.println("- 'go north', 'go n' or 'n' takes you north. This is also available for south, west, and east.");
        System.out.println("- 'exit' quits the game.");
        System.out.println("- 'look' gives you a description of the place you are in.");
        //System.out.println("- 'turn on light' turns on the light.");
        System.out.println("- 'inventory', 'invent' or 'inv' shows your inventory.");
        System.out.println("- 'take <item>' puts an item in your inventory.");
        System.out.println("- 'drop <item>' removes an item from your inventory.");

    }

    private void look() {
        System.out.printf("You are in %s, a ", adventure.getCurrentRoom().getName());
        if (adventure.getCurrentRoom().isDark()) {
            System.out.println("place filled with darkness, except in the direction you came from.");
        } else {
            System.out.print(adventure.getCurrentRoom().getDescription());
            for (Item item : adventure.getCurrentRoom().getItems()) {
                System.out.printf(", and %s", item.getLongName());
            }
            System.out.println(".");
        }
    }
}