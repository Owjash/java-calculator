import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CalculatorGUI extends JFrame {

    private final JTextField display = new JTextField("0");
    private final ArrayList<String> history = new ArrayList<>();

    private double firstNumber = 0;
    private String operator = "";
    private boolean startNewNumber = true;

    public CalculatorGUI() {
        setTitle("Java Calculator");
        setSize(350, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Calculator display
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(new Font("Arial", Font.BOLD, 32));
        display.setBorder(
                BorderFactory.createEmptyBorder(20, 10, 20, 10)
        );

        add(display, BorderLayout.NORTH);

        // Calculator buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 8, 8));
        buttonPanel.setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        String[] buttons = {
                "C", "√", "%", "÷",
                "7", "8", "9", "×",
                "4", "5", "6", "-",
                "1", "2", "3", "+",
                "0", ".", "⌫", "="
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 20));

            button.addActionListener(event -> handleButton(text));

            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);

        // History button
        JButton historyButton = new JButton("View History");
        historyButton.setFont(new Font("Arial", Font.BOLD, 18));
        historyButton.addActionListener(event -> showHistory());

        add(historyButton, BorderLayout.SOUTH);
    }

    private void handleButton(String button) {
        if (button.matches("[0-9]")) {
            enterNumber(button);
        } else {
            switch (button) {
                case "." -> enterDecimal();

                case "+", "-", "×", "÷" ->
                        selectOperator(button);

                case "=" -> calculateResult();

                case "C" -> clearCalculator();

                case "√" -> calculateSquareRoot();

                case "%" -> calculatePercentage();

                case "⌫" -> deleteLastCharacter();
            }
        }
    }

    private void enterNumber(String number) {
        if (startNewNumber || display.getText().equals("0")) {
            display.setText(number);
            startNewNumber = false;
        } else {
            display.setText(display.getText() + number);
        }
    }

    private void enterDecimal() {
        if (startNewNumber) {
            display.setText("0.");
            startNewNumber = false;
        } else if (!display.getText().contains(".")) {
            display.setText(display.getText() + ".");
        }
    }

    private void selectOperator(String selectedOperator) {
        firstNumber = Double.parseDouble(display.getText());
        operator = selectedOperator;
        startNewNumber = true;
    }

    private void calculateResult() {
        if (operator.isEmpty()) {
            return;
        }

        double secondNumber =
                Double.parseDouble(display.getText());

        double result;

        switch (operator) {
            case "+" -> result = firstNumber + secondNumber;

            case "-" -> result = firstNumber - secondNumber;

            case "×" -> result = firstNumber * secondNumber;

            case "÷" -> {
                if (secondNumber == 0) {
                    display.setText("Cannot divide by zero");
                    operator = "";
                    startNewNumber = true;
                    return;
                }

                result = firstNumber / secondNumber;
            }

            default -> {
                return;
            }
        }

        String calculation =
                formatNumber(firstNumber)
                        + " " + operator + " "
                        + formatNumber(secondNumber)
                        + " = "
                        + formatNumber(result);

        history.add(calculation);

        display.setText(formatNumber(result));

        operator = "";
        startNewNumber = true;
    }

    private void calculateSquareRoot() {
        double number =
                Double.parseDouble(display.getText());

        if (number < 0) {
            display.setText("Invalid input");
        } else {
            double result = Math.sqrt(number);

            history.add(
                    "√" + formatNumber(number)
                            + " = "
                            + formatNumber(result)
            );

            display.setText(formatNumber(result));
        }

        startNewNumber = true;
    }

    private void calculatePercentage() {
        double number =
                Double.parseDouble(display.getText());

        double result = number / 100;

        history.add(
                formatNumber(number)
                        + "% = "
                        + formatNumber(result)
        );

        display.setText(formatNumber(result));
        startNewNumber = true;
    }

    private void deleteLastCharacter() {
        String currentText = display.getText();

        if (currentText.length() > 1) {
            display.setText(
                    currentText.substring(
                            0,
                            currentText.length() - 1
                    )
            );
        } else {
            display.setText("0");
            startNewNumber = true;
        }
    }

    private void clearCalculator() {
        display.setText("0");
        firstNumber = 0;
        operator = "";
        startNewNumber = true;
    }

    private void showHistory() {
        if (history.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "No calculations yet.",
                    "Calculation History",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }

        String historyText = String.join("\n", history);

        JOptionPane.showMessageDialog(
                this,
                historyText,
                "Calculation History",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private String formatNumber(double number) {
        if (number == (long) number) {
            return String.valueOf((long) number);
        }

        return String.valueOf(number);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculatorGUI calculator =
                    new CalculatorGUI();

            calculator.setVisible(true);
        });
    }
}