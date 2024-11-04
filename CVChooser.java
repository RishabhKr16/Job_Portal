package jobportal;

import javax.accessibility.Accessible;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CVChooser extends JComponent implements Accessible {
    private JTextField nameField;
    private JTextField qualificationField;

    public CVChooser() {
        JFrame frame = new JFrame("Upload CV");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(5, 1)); // Adjusted for better spacing

        nameField = new JTextField();
        qualificationField = new JTextField();

        // Labels with navy blue foreground
        JLabel nameLabel = new JLabel("Enter your name:");
        nameLabel.setForeground(new Color(0x001F3F)); // Navy Blue
        JLabel qualificationLabel = new JLabel("Enter your qualification:");
        qualificationLabel.setForeground(new Color(0x001F3F)); // Navy Blue

        frame.add(nameLabel);
        frame.add(nameField);
        frame.add(qualificationLabel);
        frame.add(qualificationField);

        JButton uploadButton = new JButton("Upload CV");
        styleButton(uploadButton);
        uploadButton.addActionListener(new UploadCVAction(frame));
        frame.add(uploadButton);

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(0x007BFF)); // Bright Blue for button
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
    }

    private class UploadCVAction implements ActionListener {
        private JFrame frame;

        public UploadCVAction(JFrame frame) {
            this.frame = frame;
        }

        public void actionPerformed(ActionEvent e) {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF and DOC Files", "pdf", "doc");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();
                String seekerName = nameField.getText();
                String qualification = qualificationField.getText();
                String cvFilePath = selectedFile.getAbsolutePath();

                if (!seekerName.isEmpty() && !qualification.isEmpty()) {
                    uploadCV(seekerName, qualification, cvFilePath);
                    JOptionPane.showMessageDialog(null, "Your CV has been uploaded.");
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter both your name and qualification.");
                }
            }
        }
    }

    private void uploadCV(String seekerName, String qualification, String cvFilePath) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/job_portal", "root", "MRIKAL1409");
            String sql = "INSERT INTO applied_jobs (qualification, seeker_name, cv_file_path) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, qualification);
            pstmt.setString(2, seekerName);
            pstmt.setString(3, cvFilePath);
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error uploading CV to the database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

