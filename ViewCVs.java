package jobportal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewCVs extends JFrame {
    private JTable table;
    private JButton viewButton;
    private JButton acceptButton;
    private JButton rejectButton;
    private File selectedFile;

    public ViewCVs() {
        setTitle("View Uploaded CVs");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set up the table
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Create button panel and style buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(0x001F3F)); // Navy Blue for button panel
        viewButton = new JButton("View Selected CV");
        acceptButton = new JButton("Accept CV");
        rejectButton = new JButton("Reject CV");
        
        // Button styling
        styleButton(viewButton);
        styleButton(acceptButton);
        styleButton(rejectButton);
        
        viewButton.addActionListener(new ViewCVAction());
        acceptButton.addActionListener(new AcceptRejectCVAction("Accepted"));
        rejectButton.addActionListener(new AcceptRejectCVAction("Rejected"));

        buttonPanel.add(viewButton);
        buttonPanel.add(acceptButton);
        buttonPanel.add(rejectButton);
        add(buttonPanel, BorderLayout.SOUTH);

        loadCVs();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(0x007BFF)); // Bright Blue for buttons
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(120, 40));
    }

    private void loadCVs() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Seeker Name");
        model.addColumn("Qualification");
        model.addColumn("CV File");
        model.addColumn("Status");

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/job_portal", "root", "MRIKAL1409");
            ResultSet rs = conn.createStatement().executeQuery("SELECT seeker_name, qualification, cv_file_path, status FROM applied_jobs");

            while (rs.next()) {
                String seekerName = rs.getString("seeker_name");
                String qualification = rs.getString("qualification");
                String cvFilePath = rs.getString("cv_file_path");
                String status = rs.getString("status");
                model.addRow(new Object[]{seekerName, qualification, cvFilePath, status});
            }

            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        table.setModel(model);
        table.setFillsViewportHeight(true);
        table.setBackground(Color.WHITE); // White background for table
        table.setForeground(Color.BLACK); // Black text for readability
    }

    private class ViewCVAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String cvFilePath = (String) table.getValueAt(selectedRow, 2);
                selectedFile = new File(cvFilePath);
                openCV();
            } else {
                JOptionPane.showMessageDialog(null, "Please select a CV to view.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }

        private void openCV() {
            if (selectedFile.exists()) {
                try {
                    Desktop.getDesktop().open(selectedFile);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Unable to open the CV file.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "The selected CV file does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class AcceptRejectCVAction implements ActionListener {
        private String status;

        public AcceptRejectCVAction(String status) {
            this.status = status;
        }

        public void actionPerformed(ActionEvent e) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String seekerName = (String) table.getValueAt(selectedRow, 0);
                updateCVStatus(seekerName, status);
            } else {
                JOptionPane.showMessageDialog(null, "Please select a CV to " + status.toLowerCase() + ".", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }

        private void updateCVStatus(String seekerName, String status) {
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/job_portal", "root", "MRIKAL1409");
                String query = "UPDATE applied_jobs SET status = ? WHERE seeker_name = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                    pstmt.setString(1, status);
                    pstmt.setString(2, seekerName);
                    pstmt.executeUpdate();
                }
                conn.close();
                loadCVs();
                JOptionPane.showMessageDialog(null, "CV " + status + " successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error updating CV status.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
