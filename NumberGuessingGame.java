import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class NumberGuessingGame {
    private static final int MIN = 1;
    private static final int MAX = 100;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(NumberGuessingGame::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        // Create the main frame
        JFrame frame = new JFrame("Number Guessing Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 300);
        frame.setLayout(new BorderLayout());

        // Random number generation
        Random random = new Random();
        final int secretNumber = random.nextInt(MAX - MIN + 1) + MIN;
        int attempts = 0;

        // GUI components
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        JLabel instructionLabel = new JLabel("Guess a number between " + MIN + " and " + MAX + ":");
        JTextField guessField = new JTextField();
        JLabel attemptLabel = new JLabel("Attempts: 0");

        inputPanel.add(instructionLabel);
        inputPanel.add(guessField);
        inputPanel.add(new JLabel()); // Empty space for alignment
        inputPanel.add(attemptLabel);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton guessButton = new JButton("Guess");
        JButton restartButton = new JButton("Restart");
        JLabel resultLabel = new JLabel(" ", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 16));

        buttonPanel.add(guessButton);
        buttonPanel.add(restartButton);

        // Action listener for guessing
        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = guessField.getText().trim();
                if (input.isEmpty()) {
                    resultLabel.setText("<html><font color='red'>Please enter a number!</font></html>");
                    return;
                }

                try {
                    int guess = Integer.parseInt(input);
                    if (guess < MIN || guess > MAX) {
                        resultLabel.setText("<html><font color='red'>Please enter a number between " + MIN + " and " + MAX + "!</font></html>");
                    } else {
                        attempts++;
                        attemptLabel.setText("Attempts: " + attempts);

                        if (guess == secretNumber) {
                            resultLabel.setText("<html><font color='green'>Congratulations! You guessed the number in " + attempts + " attempts.</font></html>");
                            guessButton.setEnabled(false);
                        } else if (guess < secretNumber) {
                            resultLabel.setText("<html><font color='blue'>Too low! Try again.</font></html>");
                        } else {
                            resultLabel.setText("<html><font color='blue'>Too high! Try again.</font></html>");
                        }
                    }
                } catch (NumberFormatException ex) {
                    resultLabel.setText("<html><font color='red'>Invalid input! Please enter a valid number.</font></html>");
                }

                guessField.setText(""); // Clear the input field
                guessField.requestFocus(); // Focus back on the input field
            }
        });

        // Action listener for restarting the game
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(frame, "Are you sure you want to restart?", "Restart Game", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    int newSecretNumber = random.nextInt(MAX - MIN + 1) + MIN;
                    attempts = 0;
                    resultLabel.setText(" ");
                    guessField.setText("");
                    attemptLabel.setText("Attempts: 0");
                    guessButton.setEnabled(true);
                }
            }
        });

        // Add panels to the frame
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.add(resultLabel, BorderLayout.SOUTH);

        // Display the window
        frame.setVisible(true);
    }
}