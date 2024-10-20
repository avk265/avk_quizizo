package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class main_gui {

    private JFrame mainFrame;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(main_gui::new);
    }

    public main_gui() {
        // Set up the main frame
        mainFrame = new JFrame("Welcome to My Application");
        mainFrame.setSize(400, 600);
        mainFrame.getContentPane().setBackground(new Color(190, 187, 180));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BoxLayout(mainFrame.getContentPane(), BoxLayout.Y_AXIS)); // Use BoxLayout

        // Set application icon
        ImageIcon icon = new ImageIcon("C:\\Users\\Kiran\\Desktop\\myjdbc\\jdbc project\\src\\app\\icon.png");
        Image scaledImage = icon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        ImageIcon ico = new ImageIcon(scaledImage);
        JLabel img = new JLabel(ico);
        img.setAlignmentX(Component.CENTER_ALIGNMENT); // Center alignment

        // Space before image for centering
        mainFrame.add(Box.createRigidArea(new Dimension(0, 50))); // Adjust vertical spacing as needed
        mainFrame.add(img); // Add the image label
        mainFrame.add(Box.createRigidArea(new Dimension(0, 20))); // Space after image

        // Welcome Label

        // Start Button
        JButton startButton = new JButton("START");
        startButton.setFont(new Font("Verdana", Font.BOLD, 16));
        startButton.setForeground(Color.black);
        startButton.setBackground(new Color(190, 187, 180));
        startButton.setFocusPainted(false);
        startButton.setBorderPainted(false);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center alignment

        // Add action listener to the button
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 // Close the main frame
                new Login(); // Open the login page
            }
        });

        // Add the start button to the panel
        mainFrame.add(startButton);

        // Space after the button
        mainFrame.add(Box.createRigidArea(new Dimension(0, 50))); // Adjust as needed for spacing

        // Display the main frame
        mainFrame.setVisible(true);
    }
}
