package jobportal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class RegisterEmployer extends JFrame implements ActionListener {
    private JLabel nameLabel, usernameLabel, passwordLabel, phoneLabel, emailLabel;
    private JTextField nameField, usernameField, phoneField, emailField;
    private JPasswordField passwordField;
    private JButton createButton, cancelButton;
    private JPanel panel;

    public RegisterEmployer() {
        setTitle("Register Employer");
        setSize(400, 400);
        setLocationRelativeTo(null);

        nameLabel = new JLabel("Name:");
        usernameLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");
        phoneLabel = new JLabel("Phone Number:");
        emailLabel = new JLabel("Email:");

        nameField = new JTextField(30);
        usernameField = new JTextField(30);
        passwordField = new JPasswordField(30);
        phoneField = new JTextField(30);
        emailField = new JTextField(30);

        createButton = new JButton("Create");
        cancelButton = new JButton("Cancel");

        createButton.addActionListener(this);
        cancelButton.addActionListener(this);

        panel = new JPanel(new GridLayout(6, 2));
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(phoneLabel);
        panel.add(phoneField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(createButton);
        panel.add(cancelButton);

        add(panel);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createButton) {
            String name = nameField.getText();
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String phone = phoneField.getText();
            String email = emailField.getText();

            // MySQL connection
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/job_portal", "root", "MRIKAL1409");
                String sql = "INSERT INTO employers (name, username, password, phone_number, email) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, name);
                pstmt.setString(2, username);
                pstmt.setString(3, password);
                pstmt.setString(4, phone);
                pstmt.setString(5, email);
                pstmt.executeUpdate();
                pstmt.close();
                conn.close();
                JOptionPane.showMessageDialog(this, "Employer registered successfully.");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == cancelButton) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new RegisterEmployer();
    }
}

