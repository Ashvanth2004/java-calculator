import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame implements ActionListener {

    private JTextField display;
    private StringBuilder currentInput;
    private double num1 = 0, num2 = 0, result = 0;
    private char operator;

    public Calculator() {
        // Window (Frame) setup
        setTitle("Calculator");
        setSize(350, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        currentInput = new StringBuilder();

        // Display field (top)
        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 32));
        display.setEditable(false);
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        add(display, BorderLayout.NORTH);

        // Button panel (center)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 5, 5));

        String[] buttons = {
            "C", "DEL", "%", "/",
            "7", "8", "9", "*",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", ".", "=", 
        };

        // Add all buttons
        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 24));
            button.addActionListener(this);
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.matches("[0-9]") || command.equals(".")) {
            currentInput.append(command);
            display.setText(currentInput.toString());
        } 
        else if (command.equals("C")) {
            currentInput.setLength(0);
            display.setText("");
            num1 = num2 = result = 0;
        } 
        else if (command.equals("DEL")) {
            if (currentInput.length() > 0) {
                currentInput.deleteCharAt(currentInput.length() - 1);
                display.setText(currentInput.toString());
            }
        } 
        else if (command.equals("=")) {
            try {
                num2 = Double.parseDouble(currentInput.toString());
                switch (operator) {
                    case '+': result = num1 + num2; break;
                    case '-': result = num1 - num2; break;
                    case '*': result = num1 * num2; break;
                    case '/': 
                        if (num2 == 0) throw new ArithmeticException("Division by zero");
                        result = num1 / num2; 
                        break;
                    case '%': result = num1 % num2; break;
                }
                display.setText(String.valueOf(result));
                currentInput.setLength(0);
                currentInput.append(result);
            } catch (Exception ex) {
                display.setText("Error");
                currentInput.setLength(0);
            }
        } 
        else { 
            // Operator (+, -, *, /, %)
            try {
                num1 = Double.parseDouble(currentInput.toString());
                operator = command.charAt(0);
                currentInput.setLength(0);
            } catch (Exception ex) {
                display.setText("Error");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Calculator::new);
    }
}
