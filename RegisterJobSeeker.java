package jobportal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterJobSeeker extends JFrame implements ActionListener {
    private JLabel nameLabel, usernameLabel, passwordLabel, phoneLabel, emailLabel, qualificationLabel;
    private JTextField nameField, usernameField, phoneField, emailField, qualificationField;
    private JPasswordField passwordField;
    private JButton createButton, cancelButton;
    private JPanel panel;

    public RegisterJobSeeker() {
        setTitle("Job Seeker Registration");
        setSize(400, 400);
        setLocationRelativeTo(null);

        nameLabel = new JLabel("Name:");
        usernameLabel = new JLabel("Choose Username:");
        passwordLabel = new JLabel("Choose Password:");
        phoneLabel = new JLabel("Phone Number:");
        emailLabel = new JLabel("Email:");
        qualificationLabel = new JLabel("Qualification:");

        nameField = new JTextField(30);
        usernameField = new JTextField(30);
        passwordField = new JPasswordField(30);
        phoneField = new JTextField(30);
        emailField = new JTextField(30);
        qualificationField = new JTextField(30);

        createButton = new JButton("Create");
        cancelButton = new JButton("Cancel");

        createButton.addActionListener(this);
        cancelButton.addActionListener(e -> setVisible(false));

        panel = new JPanel(new GridLayout(7, 2));
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
        panel.add(qualificationLabel);
        panel.add(qualificationField);
        panel.add(createButton);
        panel.add(cancelButton);

        add(panel);
        setVisible(true);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createButton) {
            if (validateInput()) {
                registerJobSeeker();
            }
        }
    }

    private boolean validateInput() {
        if (!checkPhone()) {
            JOptionPane.showMessageDialog(null, "Invalid Phone Number", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!checkEmail()) {
            JOptionPane.showMessageDialog(null, "Invalid Email", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean checkPhone() {
        return phoneField.getText().length() == 10;
    }

    private boolean checkEmail() {
        return emailField.getText().contains(".com");
    }

    private void registerJobSeeker() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/job_portal", "root", "MRIKAL1409")) {
            String sql = "INSERT INTO job_seekers (name, username, password, phone_number, email, qualification) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nameField.getText());
            pstmt.setString(2, usernameField.getText());
            pstmt.setString(3, new String(passwordField.getPassword()));
            pstmt.setString(4, phoneField.getText());
            pstmt.setString(5, emailField.getText());
            pstmt.setString(6, qualificationField.getText());
            pstmt.executeUpdate();
            pstmt.close();
            JOptionPane.showMessageDialog(this, "Job Seeker registered successfully.");
            setVisible(false);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new RegisterJobSeeker();
    }
}
