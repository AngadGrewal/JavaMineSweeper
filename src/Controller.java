import java.util.InputMismatchException;
import java.util.Scanner;

public class Controller {

    public static int selectDifficulty() {
        System.out.println("Please enter what difficulty you'd like\n" +
                "Easy, Medium, or Hard\n");
        Scanner scanner = new Scanner(System.in);
        String difficulty = scanner.nextLine().toLowerCase();

        int size = 0;

        switch (difficulty) {
            case "easy":
                size = 5;
                break;
            case "medium":
                size = 7;
                break;
            case "hard":
                size = 10;
                break;
        }

        if (size == 0) {
            System.out.println("Incorrect Input\n");
            selectDifficulty();
        }
        return size;
    }

    @SuppressWarnings("SuspiciousNameCombination")
    public static boolean operation(Grid game, int command) {
        int xCoord, yCoord;
        try {
            System.out.println("please enter the x coordinate");
            Scanner xCoordinate = new Scanner(System.in);
            xCoord = xCoordinate.nextInt();

            System.out.println("please enter the y coordinate");
            Scanner yCoordinate = new Scanner(System.in);
            yCoord = yCoordinate.nextInt();

            if (xCoord >= game.getSize() || yCoord >= game.getSize()) {
                System.out.println("Input larger than grid");
                operation(game, command);
            }

            if (command == 0) {
                if (game.reveal(yCoord, xCoord)) {
                    game.printGrid();
                    System.out.println("\nYOU LOSE!");
                    return true;
                }
            } else {
                if (game.flag(yCoord, xCoord)) {
                    game.gridReveal();
                    game.printGrid();
                    System.out.println("\nYOU WIN!");
                    return true;
                }
            }

        } catch (InputMismatchException e) {
            System.out.println("not a valid input");
            operation(game, command);
        }
        return false;
    }

    public static void run() {
        System.out.println("Welcome to Minesweeper by Angad Grewal\n");
        Grid game = new Grid(selectDifficulty());
        game.printGrid();
        label:
        while (true) {
            System.out.println("Would you like to 'reveal, (r)', 'flag, (f)', 'restart' or 'exit'");
            Scanner choiceInput = new Scanner(System.in);
            String choice = choiceInput.nextLine();

            switch (choice) {
                case "exit":
                    break label;
                case "reveal":
                case "r":
                    if (operation(game, 0)) {
                        System.out.println("Would you like to replay (r)? or exit?");
                        Scanner lose = new Scanner(System.in);
                        String loseChoice = lose.nextLine();
                        switch (loseChoice) {
                            case "replay":
                            case "restart":
                            case "r":
                                run();
                            case "exit":
                                break label;
                        }
                        break label;
                    }
                    break;
                case "flag":
                case "f":
                    if (operation(game, 1)) {
                        System.out.println("Would you like to replay (r)? or exit?");
                        Scanner win = new Scanner(System.in);
                        String winChoice = win.nextLine();
                        switch (winChoice) {
                            case "replay":
                            case "restart":
                            case "r":
                                run();
                            case "exit":
                                break label;
                        }
                        break label;
                    }
                    break;
                case "restart":
                    run();
            }
        }
    }

    public static void main(String[] args) {
        run();
    }
}