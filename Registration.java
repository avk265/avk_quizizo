package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Registration {

    public Registration(Runnable onClose) {
        JFrame frame = new JFrame("ONLINE EXAMINATION SYSTEM");
        frame.setSize(1000, 650);
        frame.getContentPane().setBackground(new Color(190, 187, 180));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);

        // Adding Image
        ImageIcon icon = new ImageIcon("C:\\Users\\Kiran\\Desktop\\myjdbc\\jdbc project\\src\\app\\icon.png");
        Image scaledImage = icon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        ImageIcon ico = new ImageIcon(scaledImage);
        JLabel img = new JLabel(ico);
        img.setBounds(10, 10, 400, 400);
        frame.add(img);

        // Header
        JLabel head = new JLabel("ADVISOR REGISTRATION FORM");
        head.setBounds(550, 10, 300, 40);
        head.setFont(new Font("Times New Roman", Font.BOLD, 13));
        frame.add(head);

        // Form Labels
        String[] labels = {"USERNAME", "FULL NAME", "GENDER", "MOBILE NUMBER", "EMAIL", "PASSWORD", "DESIGNATION"};
        int yPosition = 100;

        for (String label : labels) {
            JLabel lbl = new JLabel(label);
            lbl.setBounds(350, yPosition, 200, 40);
            lbl.setFont(new Font("Times New Roman", Font.PLAIN, 13));
            frame.add(lbl);
            yPosition += 50;
        }

        // Form Fields
        JTextField usernameField = new JTextField();
        usernameField.setBounds(600, 110, 200, 25);
        frame.add(usernameField);

        JTextField fullNameField = new JTextField();
        fullNameField.setBounds(600, 160, 200, 25);
        frame.add(fullNameField);

        // Gender Radio Buttons
        JRadioButton male = new JRadioButton("Male");
        male.setBounds(600, 210, 70, 25);
        male.setFont(new Font("Times New Roman", Font.PLAIN, 13));
        male.setBackground(new Color(190, 187, 180));
        frame.add(male);

        JRadioButton female = new JRadioButton("Female");
        female.setBounds(670, 210, 80, 25);
        female.setFont(new Font("Times New Roman", Font.PLAIN, 13));
        female.setBackground(new Color(190, 187, 180));
        frame.add(female);

        JRadioButton other = new JRadioButton("Other");
        other.setBounds(750, 210, 70, 25);
        other.setFont(new Font("Times New Roman", Font.PLAIN, 13));
        other.setBackground(new Color(190, 187, 180));
        frame.add(other);

        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(male);
        genderGroup.add(female);
        genderGroup.add(other);

        JTextField mobileField = new JTextField();
        mobileField.setBounds(600, 260, 200, 25);
        frame.add(mobileField);

        JTextField emailField = new JTextField();
        emailField.setBounds(600, 310, 200, 25);
        frame.add(emailField);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(600, 360, 200, 25);
        frame.add(passwordField);

        JTextField designationField = new JTextField();
        designationField.setBounds(600, 410, 200, 25);
        frame.add(designationField);

        // Checkbox for confirmation
        Checkbox c = new Checkbox("All the details given are correct.");
        c.setBounds(500, 480, 500, 30);
        c.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        frame.add(c);

        // Submit Button
        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(650, 510, 100, 30);
        submitButton.setBackground(new Color(76, 175, 80));
        submitButton.setBorder(null);
        submitButton.setEnabled(false); // Initially disabled
        frame.add(submitButton);

        // Enable submit button only when all fields are filled and checkbox is selected
        KeyAdapter formKeyListener = new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                boolean allFieldsFilled = !usernameField.getText().isEmpty() &&
                        !fullNameField.getText().isEmpty() &&
                        (male.isSelected() || female.isSelected() || other.isSelected()) &&
                        !mobileField.getText().isEmpty() &&
                        !emailField.getText().isEmpty() &&
                        passwordField.getPassword().length > 0 &&
                        !designationField.getText().isEmpty();

                submitButton.setEnabled(allFieldsFilled && c.getState());
            }
        };

        usernameField.addKeyListener(formKeyListener);
        fullNameField.addKeyListener(formKeyListener);
        male.addActionListener(e -> submitButton.setEnabled(c.getState()));
        female.addActionListener(e -> submitButton.setEnabled(c.getState()));
        other.addActionListener(e -> submitButton.setEnabled(c.getState()));
        mobileField.addKeyListener(formKeyListener);
        emailField.addKeyListener(formKeyListener);
        passwordField.addKeyListener(formKeyListener);
        designationField.addKeyListener(formKeyListener);
        c.addItemListener(e -> submitButton.setEnabled(c.getState() && submitButton.isEnabled()));

        // Submit Button Action Listener
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String fullName = fullNameField.getText();
                String gender = male.isSelected() ? "Male" : female.isSelected() ? "Female" : "Other";
                String mobileNumber = mobileField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                String designation = designationField.getText();

                // Validate input
                if (checkUsernameExists(username)) {
                    JOptionPane.showMessageDialog(frame, "Username already exists. Please choose a different username.", "Username Taken", JOptionPane.WARNING_MESSAGE);
                } else if (!isValidMobileNumber(mobileNumber)) {
                    JOptionPane.showMessageDialog(frame, "Mobile number must be exactly 10 digits.", "Invalid Mobile Number", JOptionPane.WARNING_MESSAGE);
                } else if (!isValidEmail(email)) {
                    JOptionPane.showMessageDialog(frame, "Email must end with @gmail.com, @tkmce.ac.in, or @outlook.com", "Invalid Email", JOptionPane.WARNING_MESSAGE);
                } else {
                    // Save to database
                    saveToDatabase(username, fullName, gender, mobileNumber, email, password, designation);
                }
            }
        });

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                onClose.run();  // Reset the flag
            }
        });

        frame.setVisible(true);
    }

    private static boolean checkUsernameExists(String username) {
        String url = "jdbc:mysql://localhost:3306/mydb";
        String dbUsername = "root";
        String dbPassword = "kiran";

        String query = "SELECT COUNT(*) FROM advisor_records WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // returns true if username exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean isValidMobileNumber(String mobileNumber) {
        return mobileNumber.matches("\\d{10}"); // Check if mobile number is exactly 10 digits
    }

    private static boolean isValidEmail(String email) {
        return email.endsWith("@gmail.com") || email.endsWith("@tkmce.ac.in") || email.endsWith("@outlook.com");
    }

    private static void saveToDatabase(String username, String fullName, String gender, String mobileNumber, String email, String password, String designation) {
        String url = "jdbc:mysql://localhost:3306/mydb";
        String dbUsername = "root";
        String dbPassword = "kiran";

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
             Statement stmt = conn.createStatement()) {

            // Create table if not exists
            String createTableQuery = "CREATE TABLE IF NOT EXISTS advisor_records ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "username VARCHAR(50) UNIQUE, "
                    + "full_name VARCHAR(100), "
                    + "gender VARCHAR(10), "
                    + "mobile_number VARCHAR(15), "
                    + "email VARCHAR(100), "
                    + "password VARCHAR(100), "
                    + "designation VARCHAR(50)"
                    + ")";
            stmt.execute(createTableQuery);

            // Insert advisor details into the database
            String query = "INSERT INTO advisor_records (username, full_name, gender, mobile_number, email, password, designation) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, username);
                pstmt.setString(2, fullName);
                pstmt.setString(3, gender);
                pstmt.setString(4, mobileNumber);
                pstmt.setString(5, email);
                pstmt.setString(6, password);
                pstmt.setString(7, designation);

                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(null, "Registration successful!");
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred while saving the data.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
