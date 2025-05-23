
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Calculator extends JFrame {

    @Serial
    private static final long serialVersionUID = -9075562467166618473L;
    private final JTextField display;
    private double result = 0;
    private String lastCommand = "=";
    private boolean start = true;

    /**
     * Create the frame.
     */
    public Calculator() {
        setTitle("\u8BA1\u7B97\u5668");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationByPlatform(true);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JPanel displayPanel = new JPanel();
        contentPane.add(displayPanel, BorderLayout.NORTH);

        display = new JTextField();
        display.setText("0");
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setEditable(false);
        display.setFont(new Font("΢���ź�", Font.PLAIN, 15));
        displayPanel.add(display);
        display.setColumns(13);

        JPanel buttonPanel = new JPanel();
        contentPane.add(buttonPanel, BorderLayout.CENTER);
        buttonPanel.setLayout(new GridLayout(4, 4, 5, 5));

        JButton number7Button = new JButton("7");
        ActionListener insert = new InsertAction();
        number7Button.addActionListener(insert);
        number7Button.setFont(new Font("΢���ź�", Font.PLAIN, 15));
        buttonPanel.add(number7Button);

        JButton number8Button = new JButton("8");
        number8Button.addActionListener(insert);
        number8Button.setFont(new Font("΢���ź�", Font.PLAIN, 15));
        buttonPanel.add(number8Button);

        JButton number9Button = new JButton("9");
        number9Button.addActionListener(insert);
        number9Button.setFont(new Font("΢���ź�", Font.PLAIN, 15));
        buttonPanel.add(number9Button);

        JButton divideButton = new JButton("/");
        ActionListener command = new CommandAction();
        divideButton.addActionListener(command);
        divideButton.setFont(new Font("΢���ź�", Font.PLAIN, 15));
        buttonPanel.add(divideButton);

        JButton number4Button = new JButton("4");
        number4Button.addActionListener(insert);
        number4Button.setFont(new Font("΢���ź�", Font.PLAIN, 15));
        buttonPanel.add(number4Button);

        JButton number5Button = new JButton("5");
        number5Button.addActionListener(insert);
        number5Button.setFont(new Font("΢���ź�", Font.PLAIN, 15));
        buttonPanel.add(number5Button);

        JButton number6Button = new JButton("6");
        number6Button.addActionListener(insert);
        number6Button.setFont(new Font("΢���ź�", Font.PLAIN, 15));
        buttonPanel.add(number6Button);

        JButton multiplyButton = new JButton("*");
        multiplyButton.addActionListener(command);
        multiplyButton.setFont(new Font("΢���ź�", Font.PLAIN, 15));
        buttonPanel.add(multiplyButton);

        JButton number3Button = new JButton("1");
        number3Button.addActionListener(insert);
        number3Button.setFont(new Font("΢���ź�", Font.PLAIN, 15));
        buttonPanel.add(number3Button);

        JButton number2Button = new JButton("2");
        number2Button.addActionListener(insert);
        number2Button.setFont(new Font("΢���ź�", Font.PLAIN, 15));
        buttonPanel.add(number2Button);

        JButton number1Button = new JButton("3");
        number1Button.addActionListener(insert);
        number1Button.setFont(new Font("΢���ź�", Font.PLAIN, 15));
        buttonPanel.add(number1Button);

        JButton subtractButton = new JButton("-");
        subtractButton.addActionListener(command);
        subtractButton.setFont(new Font("΢���ź�", Font.PLAIN, 15));
        buttonPanel.add(subtractButton);

        JButton number0Button = new JButton("0");
        number0Button.addActionListener(insert);
        number0Button.setFont(new Font("΢���ź�", Font.PLAIN, 15));
        buttonPanel.add(number0Button);

        JButton dotButton = new JButton(".");
        dotButton.setFont(new Font("΢���ź�", Font.PLAIN, 15));
        buttonPanel.add(dotButton);

        JButton equalButton = new JButton("=");
        equalButton.addActionListener(command);
        equalButton.setFont(new Font("΢���ź�", Font.PLAIN, 15));
        buttonPanel.add(equalButton);

        JButton addButton = new JButton("+");
        addButton.addActionListener(command);
        addButton.setFont(new Font("΢���ź�", Font.PLAIN, 15));
        buttonPanel.add(addButton);
        pack();
    }

    private class InsertAction implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String input = e.getActionCommand();
            String text = display.getText();
            if (start) {
                display.setText("");
                start = false;
            }
            if (text.startsWith(".")) {
                display.setText("0" + display.getText() + input);
            } else if (text.startsWith("-0.") || text.startsWith("0.")) {
                display.setText(display.getText() + input);
            } else if (text.startsWith("-0")) {
                display.setText("-" + input);
            } else if (text.startsWith("0")) {
                display.setText(input);
            } else {
                display.setText(display.getText() + input);
            }
        }
    }

    private class CommandAction implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (start) {
                if (command.equals("-")) {
                    display.setText(command);
                    start = false;
                } else {
                    lastCommand = command;
                }
            } else {
                calculate(Double.parseDouble(display.getText()));
                lastCommand = command;
                start = true;
            }
        }
    }

    public void calculate(double x) {
        char operator = lastCommand.charAt(0);
        switch (operator) {
            case '+' -> result += x;
            case '-' -> result -= x;
            case '*' -> result *= x;
            case '/' -> result /= x;
            case '=' -> result = x;
        }
        display.setText("" + result);
    }
}
