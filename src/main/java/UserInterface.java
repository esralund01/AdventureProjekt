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
                case "health" -> {
                    String message;
                    int healthPercentage = adventure.getHealth() * 100 / adventure.getMaxHealth();
                    if (healthPercentage > 75) {
                        message = "good health and ready to fight";
                    } else if (healthPercentage > 50) {
                        message = "good health but avoid fighting right now";
                    } else if (healthPercentage > 25) {
                        message = "poor health so avoid fighting right now";
                    } else {
                        message = "poor health and close to death";
                    }
                    System.out.printf("Your health is %d out of %d. You are in %s.\n", adventure.getHealth(), adventure.getMaxHealth(), message);
                }
                // Kommandoer med variable: Pattern matching kræver Java 21.
                case String s when s.startsWith("go ") -> go(s.substring(3));
                case String s when s.startsWith("take ") -> {
                    String item = s.substring(5);
                    if (adventure.take(item)) {
                        System.out.printf("The %s is added to your inventory.\n", item);
                    } else {
                        System.out.printf("Could not find %s in this room.\n", item);
                    }
                }
                case String s when s.startsWith("drop ") -> {
                    String item = s.substring(5);
                    if (adventure.drop(item)) {
                        System.out.printf("The %s is removed from your inventory.\n", item);
                    } else {
                        System.out.printf("Could not find %s in your inventory.\n", item);
                    }
                }
                case String s when s.startsWith("eat ") -> {
                    String food = s.substring(4);

                    if (adventure.eat(food)) {
                        System.out.printf("%s has been eaten.\n", food);
                    } else {
                        System.out.printf("you cannot eat %s. \n", food);
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
        System.out.println("- 'health' shows your health.");

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