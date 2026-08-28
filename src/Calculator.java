import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A menu-driven calculator demonstrating Java methods, loops, collections,
 * exception handling, and user input validation.
 */
public class Calculator {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final List<String> HISTORY = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("===========================");
        System.out.println("      JAVA CALCULATOR");
        System.out.println("===========================");

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 1 -> calculate("+");
                case 2 -> calculate("-");
                case 3 -> calculate("*");
                case 4 -> calculate("/");
                case 5 -> calculate("^");
                case 6 -> squareRoot();
                case 7 -> showHistory();
                case 8 -> clearHistory();
                case 9 -> percentage();
                case 0 -> running = false;
                default -> System.out.println("Please choose a number from 0 to 8.");
            }
        }

        System.out.println("Thanks for using the calculator!");
        SCANNER.close();
    }

    private static void printMenu() {
        System.out.println("\n1. Add");
        System.out.println("2. Subtract");
        System.out.println("3. Multiply");
        System.out.println("4. Divide");
        System.out.println("5. Power");
        System.out.println("6. Square root");
        System.out.println("7. View history");
        System.out.println("8. Clear history");
        System.out.println("9. Percentage");
        System.out.println("0. Exit");
    }

    private static void calculate(String operator) {
        double first = readDouble("Enter the first number: ");
        double second = readDouble("Enter the second number: ");
        double result;

        switch (operator) {
            case "+" -> result = first + second;
            case "-" -> result = first - second;
            case "*" -> result = first * second;
            case "/" -> {
                if (second == 0) {
                    System.out.println("Error: Division by zero is not allowed.");
                    return;
                }
                result = first / second;
            }
            case "^" -> result = Math.pow(first, second);
            default -> throw new IllegalArgumentException("Unknown operator");
        }

        saveAndDisplay(first + " " + operator + " " + second + " = " + result);
    }

    private static void squareRoot() {
        double number = readDouble("Enter a non-negative number: ");
        if (number < 0) {
            System.out.println("Error: A real square root cannot use a negative number.");
            return;
        }

        saveAndDisplay("sqrt(" + number + ") = " + Math.sqrt(number));
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = SCANNER.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException error) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = SCANNER.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException error) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }

    private static void saveAndDisplay(String calculation) {
        HISTORY.add(calculation);
        System.out.println("Result: " + calculation);
    }

    private static void showHistory() {
        if (HISTORY.isEmpty()) {
            System.out.println("No calculations yet.");
            return;
        }

        System.out.println("\nCalculation History");
        for (int i = 0; i < HISTORY.size(); i++) {
            System.out.println((i + 1) + ". " + HISTORY.get(i));
        }
    }

    private static void clearHistory() {
        HISTORY.clear();
        System.out.println("History cleared.");
    }
    private static void percentage() {
    double number = readDouble("Enter the number: ");
    double percentage = readDouble("Enter the percentage: ");

    double result = number * percentage / 100;

    saveAndDisplay(percentage + "% of " + number + " = " + result);
    System.out.println("9. Percentage");
   }
}
