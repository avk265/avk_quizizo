package app;

import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class main_gui {

    public static void main(String[] args) {
        // Only proceed if a network connection is found
        if (isNetworkAvailable()) {
            SwingUtilities.invokeLater(main_gui::new);  // Launch GUI
        } else {
            JOptionPane.showMessageDialog(null, "No network connection found. Please check your connection and try again.",
                    "Network Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0); // Exit the application if no network
        }
    }

    public main_gui() {
        // Set up the main frame
        JFrame mainFrame = new JFrame("Welcome to My Application");
        mainFrame.setSize(800, 800);
        mainFrame.getContentPane().setBackground(new Color(190, 187, 180));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BoxLayout(mainFrame.getContentPane(), BoxLayout.Y_AXIS)); // Use BoxLayout

        // Set application icon
        ImageIcon icon = new ImageIcon("C:\\Users\\Kiran\\Desktop\\myjdbc\\java_app\\src\\app\\app\\icon.png");
        Image scaledImage = icon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        ImageIcon ico = new ImageIcon(scaledImage);
        JLabel img = new JLabel(ico);
        img.setAlignmentX(Component.CENTER_ALIGNMENT); // Center alignment

        // Space before image for centering
        mainFrame.add(Box.createRigidArea(new Dimension(0, 50))); // Adjust vertical spacing as needed
        mainFrame.add(img); // Add the image label
        mainFrame.add(Box.createRigidArea(new Dimension(0, 20))); // Space after image

        // Start Button
        JButton startButton = new JButton("START");
        startButton.setFont(new Font("Verdana", Font.BOLD, 16));
        startButton.setForeground(Color.black);
        startButton.setBackground(new Color(190, 187, 180));
        startButton.setFocusPainted(false);
        startButton.setBorderPainted(false);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center alignment

        // Add action listener to the button
        startButton.addActionListener(e -> {
            // Close the main frame and open the login page
            new Login();
        });

        // Add the start button to the panel
        mainFrame.add(startButton);

        // Space after the button
        mainFrame.add(Box.createRigidArea(new Dimension(0, 50))); // Adjust as needed for spacing

        // Display the main frame
        mainFrame.setVisible(true);
    }

    // Method to check if the network connection is available
    public static boolean isNetworkAvailable() {
        try {
            // Create an HTTP client
            HttpClient client = HttpClient.newHttpClient();

            // Create an HTTP GET request to check network availability
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://www.google.com"))
                    .timeout(java.time.Duration.ofSeconds(5)) // Set a 5-second timeout
                    .GET()
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Check if the response status code is 200 (OK)
            return response.statusCode() == 200;
        } catch (Exception e) {
            // If there's an error (e.g., no internet connection, timeout, etc.), return false
            return false;
        }
    }
}
