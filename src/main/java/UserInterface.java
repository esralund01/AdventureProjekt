import java.util.Scanner;

public class UserInterface {

    private Scanner scanner;
    private Adventure adventure;
    private String[] input;

    public UserInterface() {
        scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");
        adventure = new Adventure();
    }

    public void startProgram() {

        System.out.println("Welcome to Adventure game, you have begun the game. ");
        System.out.println(adventure.look());
        boolean gameIsRunning = true;

        while (gameIsRunning) {
            System.out.println("which direction do you want to go?");
            input = scanner.next().split(" ");

            switch (input[0]) {
                case "go" -> {
                    if (adventure.go(input[1])){
                        System.out.println("going " + input[1] + ". " + adventure.enter());
                    } else {
                        System.out.println("you cannot go that way");
                    }
                }
               case "take" -> {
                    if(adventure.take(input[1])) {
                        System.out.println(input[1] + " added to inventory");
                    } else {
                        System.out.println(input[1] + " not found in this room");
                    }
               }
                case "drop" -> {
                    if(adventure.drop(input[1])) {
                        System.out.println(input[1] + " was removed from inventory");
                    } else {
                        System.out.println(input[1] + " not found i inventory");
                    }
                }
                case "look" -> System.out.println("looking around. you are in room: " + adventure.look());
               /* case "turn on light" -> {
                    adventure.turnOnLight();
                    System.out.println("light is now on");
                }

                */
                case "exit" -> {
                    gameIsRunning = false;
                    System.out.println("the game has ended.");
                }
                case "inventory", "inv", "invent" -> {
                    System.out.println(adventure.showInventory());
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

