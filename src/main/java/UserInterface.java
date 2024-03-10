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
            String[] command = scanner.next().split(" ");
            switch (command[0]) {
                case "go" -> go(command[1]);
                case "n", "e", "w", "s" -> go(command[0]);
                case "look" -> look();
                case "turn" -> {
                    adventure.turnOnLight();
                    System.out.println("Turning on light...");
                    look();
                }
                case "inventory", "inv" -> {
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
                case "take" -> {
                    if (adventure.take(command[1])) {
                        System.out.printf("%s is added to your inventory.\n", command[1]);
                    } else {
                        System.out.printf("Could not find %s in this room.\n", command[1]);
                    }
                }
                case "drop" -> {
                    if (adventure.drop(command[1])) {
                        System.out.printf("%s is removed from inventory.\n", command[1]);
                    } else {
                        System.out.printf("Could not find %s in your inventory.\n", command[1]);
                    }
                }
                case "exit" -> {
                    gameIsRunning = false;
                    System.out.println("Quitting game.");
                }
                case "help" -> help();
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

    private void look() {
        System.out.printf("You are in %s", adventure.getCurrentRoom().getName());
        if (adventure.getCurrentRoom().isDark()) {
            System.out.println(", a place filled with darkness, except in the direction you came from.");
        } else {
            System.out.printf(", a %s", adventure.getCurrentRoom().getDescription());
            for (Item item : adventure.getCurrentRoom().getItems()) {
                System.out.printf(", and %s", item.getLongName());
            }
            System.out.println(".");
        }
    }

    private void help() {
        System.out.println("Available commands:");
        System.out.println("- 'go north' or 'go n' takes you north. This is also available for south, west, and east.");
        System.out.println("- 'look' gives you a description of the place you are in.");
        //System.out.println("- 'turn on light' turns on the light.");
        System.out.println("- 'inventory' or 'inv' shows your inventory.");
        System.out.println("- 'take <item>' puts an item in your inventory.");
        System.out.println("- 'drop <item>' removes an item from your inventory.");
        System.out.println("- 'exit' quits the game.");
    }
}