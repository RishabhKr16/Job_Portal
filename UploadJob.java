package jobportal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UploadJob extends JFrame implements ActionListener {
    private JTextField jobTitleField;
    private JTextArea jobDescriptionArea;
    private JButton uploadButton;
    private JPanel panel;
    private String username;

    public UploadJob(String username) {
        this.username = username;
        setTitle("Upload Job");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        
        // Set layout and panel
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(0x001F3F)); // Navy Blue
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Job Title Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 5, 10); // Padding
        JLabel jobTitleLabel = new JLabel("Job Title:");
        jobTitleLabel.setForeground(Color.WHITE); // White text for contrast
        panel.add(jobTitleLabel, gbc);
        
        // Job Title Text Field
        jobTitleField = new JTextField(20);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(jobTitleField, gbc);
        
        // Job Description Label
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel jobDescriptionLabel = new JLabel("Job Description:");
        jobDescriptionLabel.setForeground(Color.WHITE); // White text for contrast
        panel.add(jobDescriptionLabel, gbc);
        
        // Job Description Text Area
        jobDescriptionArea = new JTextArea(5, 20);
        jobDescriptionArea.setLineWrap(true);
        jobDescriptionArea.setWrapStyleWord(true);
        jobDescriptionArea.setBackground(new Color(0xB3E5FC)); // Light Blue
        jobDescriptionArea.setForeground(Color.BLACK); // Black text for readability
        JScrollPane scrollPane = new JScrollPane(jobDescriptionArea);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(scrollPane, gbc);
        
        // Upload Button
        uploadButton = new JButton("Upload");
        uploadButton.setBackground(new Color(0x007BFF)); // Bright Blue
        uploadButton.setForeground(Color.WHITE);
        uploadButton.setFocusPainted(false);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 0, 10, 10);
        panel.add(uploadButton, gbc);
        
        uploadButton.addActionListener(this);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == uploadButton) {
            String jobTitle = jobTitleField.getText();
            String jobDescription = jobDescriptionArea.getText();

            if (!jobTitle.isEmpty() && !jobDescription.isEmpty()) {
                insertJobIntoDatabase(jobTitle, jobDescription);
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void insertJobIntoDatabase(String jobTitle, String jobDescription) {
        String url = "jdbc:mysql://localhost:3306/job_portal";
        String user = "root";
        String password = "MRIKAL1409";

        String sql = "INSERT INTO job (job_title, company_name, qualification) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, jobTitle);
            pstmt.setString(2, username);
            pstmt.setString(3, jobDescription);
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Job uploaded successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error uploading job.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
