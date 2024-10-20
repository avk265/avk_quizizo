package app;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.io.IOException;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class Login {

    private final JFrame frame;
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JComboBox<String> roleComboBox;
    private final JLabel messageLabel;
    private boolean isStudentRegistrationOpen = false;
    private boolean isTeacherRegistrationOpen = false;
    private boolean isAdvisorRegistrationOpen = false;


    public static void main(String[] args) {
        SwingUtilities.invokeLater(Login::new);
    }

    public Login() {
        // Frame setup
        frame = new JFrame("Member Login");
        frame.setSize(400, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);

        // Gradient background
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(35, 95, 115);
                Color color2 = new Color(175, 25, 235);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setBounds(0, 0, 400, 600);
        panel.setLayout(null);
        frame.add(panel);

        // Title Label
        JLabel titleLabel = new JLabel("MEMBER LOGIN", SwingConstants.CENTER);
        titleLabel.setBounds(100, 30, 200, 40);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(Color.BLACK);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        panel.add(titleLabel);

        // Role selection Label and ComboBox
        JLabel roleLabel = new JLabel("Select Role:");
        roleLabel.setForeground(Color.WHITE);
        roleLabel.setBounds(100, 90, 200, 20);
        roleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(roleLabel);

        roleComboBox = new JComboBox<>(new String[]{" ","Student", "Teacher", "Advisor"});
        roleComboBox.setBounds(100, 120, 200, 30);
        roleComboBox.setBackground(new Color(100, 255, 237, 157));
        roleComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(roleComboBox);

        // Username Label and Field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setBounds(100, 170, 200, 20);
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(usernameLabel);

        usernameField = new JTextField("");
        usernameField.setBounds(100, 200, 200, 30);
        addPlaceholderText(usernameField,"enter your username");
        usernameField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setBackground(new Color(240, 248, 255));
        panel.add(usernameField);

        // Password Label and Field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setBounds(100, 250, 200, 20);
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(100, 280, 200, 30);
        addPlaceholderText(passwordField,"");
        passwordField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBackground(new Color(240, 248, 255));
        panel.add(passwordField);

        // Login Button
        JButton loginButton = createButton("LOGIN", 100, 340, 200, 40);
        panel.add(loginButton);

        // Forgot Password link
        JLabel forgotPasswordLabel = new JLabel("Forgot Password?");
        forgotPasswordLabel.setForeground(Color.WHITE);
        forgotPasswordLabel.setBounds(100, 390, 200, 30);
        forgotPasswordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        forgotPasswordLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel.add(forgotPasswordLabel);

        // Register Button
        JButton registerButton = createButton("REGISTER", 100, 440, 200, 40);
        panel.add(registerButton);

        // Message Label for errors
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setBounds(50, 500, 300, 30);
        messageLabel.setForeground(Color.RED);
        panel.add(messageLabel);

        // Add action listeners
        loginButton.addActionListener(e -> handleLogin());
        forgotPasswordLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                handlePasswordReset();
            }
        });

        // Register Button Action Listener
        registerButton.addActionListener(e -> handleRegister());


        // Display frame
        frame.setVisible(true);
    }
    private void addPlaceholderText(JTextField field, String placeholder) {
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(Color.GRAY);
                }
            }
        });

        // Initially set the placeholder text
        field.setText(placeholder);
        field.setForeground(Color.GRAY);
    }
    private JButton createButton(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(34, 139, 34));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        return button;
    }

    private void handleLogin() {
        String selectedRole = (String) roleComboBox.getSelectedItem();
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (checkUsernameExists(username, selectedRole)) {
            if (validateCredentials(username, password, selectedRole)) {
                JOptionPane.showMessageDialog(frame, "Login successful as " + selectedRole + "!");
            } else {
                messageLabel.setText("Invalid credentials. Please try again.");
            }
        } else {
            messageLabel.setText("Username not found.");
        }
    }

    private void handlePasswordReset() {
        String username = JOptionPane.showInputDialog(frame, "Enter your username:");

        if (username == null || username.trim().isEmpty()) {
            return; // user cancelled or did not input username
        }

        String selectedRole = (String) roleComboBox.getSelectedItem();

        if (checkUsernameExists(username, selectedRole)) {
            String newPassword = JOptionPane.showInputDialog(frame, "Enter your new password:");

            if (newPassword != null && !newPassword.trim().isEmpty()) {
                if (resetPassword(username, newPassword, selectedRole)) {
                    JOptionPane.showMessageDialog(frame, "Password reset successful!");
                } else {
                    messageLabel.setText("Error resetting password. Please try again.");
                }
            }
        } else {
            messageLabel.setText("Username not found.");
        }
    }

    // Register Button Action: Open respective registration frames
    private void handleRegister() {
        String selectedRole = (String) roleComboBox.getSelectedItem();

        if (selectedRole.equals("Student")) {
            if (!isStudentRegistrationOpen) {
                isStudentRegistrationOpen = true;  // Mark as open
                new StudentRegistration(() -> isStudentRegistrationOpen = false);  // Pass a callback to reset the flag
            } else {
                JOptionPane.showMessageDialog(frame, "Student Registration form is already open.");
            }

        } else if (selectedRole.equals("Teacher")) {
            if (!isTeacherRegistrationOpen) {
                isTeacherRegistrationOpen = true;  // Mark as open
                new TeacherRegistration(() -> isTeacherRegistrationOpen = false);  // Pass a callback to reset the flag
            } else {
                JOptionPane.showMessageDialog(frame, "Teacher Registration form is already open.");
            }

        } else if (selectedRole.equals("Advisor")) {
            if (!isAdvisorRegistrationOpen) {
                isAdvisorRegistrationOpen = true;  // Mark as open
                new Registration(() -> isAdvisorRegistrationOpen = false);  // Pass a callback to reset the flag
            } else {
                JOptionPane.showMessageDialog(frame, "Advisor Registration form is already open.");
            }
        }
    }


    private boolean checkUsernameExists(String username, String role) {
        String url = "jdbc:mysql://localhost:3306/mydb";
        String dbUsername = "root";
        String dbPassword = "kiran";
        String query = "SELECT COUNT(*) FROM " + role.toLowerCase() + "_records WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean resetPassword(String username, String newPassword, String role) {
        String url = "jdbc:mysql://localhost:3306/mydb";
        String dbUsername = "root";
        String dbPassword = "kiran";
        String query = "UPDATE " + role.toLowerCase() + "_records SET password = ? WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, newPassword);
            pstmt.setString(2, username);
            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean validateCredentials(String username, String password, String role) {
        String url = "jdbc:mysql://localhost:3306/mydb";
        String dbUsername = "root";
        String dbPassword = "kiran";
        String query = "SELECT COUNT(*) FROM " + role.toLowerCase() + "_records WHERE username = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
