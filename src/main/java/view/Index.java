package view;

import javax.swing.*;
import java.awt.*;

public class Index extends JFrame {

    public static void main(String[] args) {
        new Index();
    }
    public Index(){
        // Set the title of the frame
        setTitle("Welcome");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the size of the frame
        setSize(400, 200);

        // Center the frame on the screen
        setLocationRelativeTo(null);

        // Disable the ability to resize the frame
        setResizable(false);

        // Create a label for the welcome text
        JPanel welcomePanel = new JPanel();
        JLabel welcomeLabel = new JLabel("Welcome to my application!");
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        welcomePanel.add(welcomeLabel);

        // Create the buttons
        JButton button1 = new JButton("Stock management");
        JButton button2 = new JButton("Staff management");
        JButton button3 = new JButton("Customer management");
        JButton button4 = new JButton("sale");

        button2.addActionListener(actionEvent -> {
            new StaffView();
            dispose();
        });
        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();

        // Set the layout of the button panel to a grid with 2 rows and 2 columns
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Add the buttons to the button panel
        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button3);
        buttonPanel.add(button4);

        // Add the welcome label to the frame
        add(welcomePanel, BorderLayout.NORTH);

        // Add the button panel to the frame
        add(buttonPanel, BorderLayout.CENTER);

        // Set the frame to be visible
        setVisible(true);
    }

}
