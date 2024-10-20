import javax.swing.*;
import java.awt.*;

public class AdvisorDashboard extends JFrame {

    public AdvisorDashboard() {
        // Set the title of the window
        setTitle("Advisor's Dashboard");

        // Set default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set layout manager for the frame
        setLayout(new BorderLayout());

        // Create a panel for icons and buttons
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 20, 0));

        // Panel for Teacher Records
        JPanel teacherPanel = new JPanel(new BorderLayout());
        JLabel teacherIcon = new JLabel(new ImageIcon("path/to/teacher_icon.png")); // Update with your own image path
        JButton teacherButton = new JButton("TEACHER RECORDS");
        teacherPanel.add(teacherIcon, BorderLayout.CENTER);
        teacherPanel.add(teacherButton, BorderLayout.SOUTH);

        // Panel for Student Records
        JPanel studentPanel = new JPanel(new BorderLayout());
        JLabel studentIcon = new JLabel(new ImageIcon("path/to/student_icon.png")); // Update with your own image path
        JButton studentButton = new JButton("STUDENT RECORDS");
        studentPanel.add(studentIcon, BorderLayout.CENTER);
        studentPanel.add(studentButton, BorderLayout.SOUTH);

        // Add panels to main panel
        mainPanel.add(teacherPanel);
        mainPanel.add(studentPanel);

        // Add main panel to the frame
        add(mainPanel, BorderLayout.CENTER);

        // Set the size of the frame
        setSize(500, 300);
        setVisible(true);
    }

    public static void main(String[] args) {
        // Run the application
        SwingUtilities.invokeLater(() -> new AdvisorDashboard());
    }
}