import java.util.Scanner;

public class UserInterface {

    private Scanner scanner;
    private Adventure adventure;

    public UserInterface() {
        scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");
        adventure = new Adventure();
    }

    public void startProgram() {

        System.out.println("Welcome to Adventure game, you have begun the game. ");

        boolean gameIsRunning = true;

        while (gameIsRunning) {
            System.out.println("which direction do you want to go?");
            String input = scanner.next();

            switch (input) {
                case "go north", "north", "n" -> {
                    if (adventure.goNorth()) {
                        System.out.println("going north. " + adventure.look());
                    } else {
                        System.out.println("you cannot go that way");
                    }
                }
                case "go west", "west", "w" -> {
                    if (adventure.goWest()) {
                        System.out.println("going west. " + adventure.look());
                    } else {
                        System.out.println("you cannot go that way");
                    }
                }
                case "go south", "south", "s" -> {
                    if (adventure.goSouth()) {
                        System.out.println("going south. " + adventure.look());
                    } else {
                        System.out.println("you cannot go that way");
                    }
                }
                case "go east", "east", "e" -> {
                    if (adventure.goEast()) {
                        System.out.println("going east. " + adventure.look());
                    } else {
                        System.out.println("you cannot go that way");
                    }
                }
                case "look" -> System.out.println("looking around. you are in room: " + adventure.look());
                case "exit" -> {
                    gameIsRunning = false;
                    System.out.println("the game has ended.");
                }
                case "help" -> helpProgram();
                default -> System.out.println("this input doesnt work. type 'help' to get help");
            }
        }
    }

    private void helpProgram() {
        System.out.println("help is on its way.");
        System.out.println("typing 'go north' will make you go north");
        System.out.println("typing 'go west' will make you go west");
        System.out.println("typing 'go east' will make you go east");
        System.out.println("typing 'go south' will make you go south");
        System.out.println("typing 'exit' will make you exit the game");
        System.out.println("typing 'look' will give you a description of the room you are in");
    }
}

