package jobportal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class SearchJob extends JFrame implements ActionListener {
    JTable table;
    DefaultTableModel model;
    JButton applyButton;
    String[] columns = {"Job Title", "User Name", "Job Description"};
    Connection conn;

    public SearchJob() {
        setTitle("Search Jobs");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLayout(new BorderLayout());

        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        table.setBackground(new Color(0xB3E5FC)); // Baby Blue
        table.setForeground(Color.BLACK); // Text Color
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        fetchJobs();

        applyButton = new JButton("Apply");
        applyButton.setBackground(new Color(0xF8BBD0)); // Soft Pink
        applyButton.setForeground(Color.BLACK); // Text Color
        applyButton.setFont(new Font("Arial", Font.BOLD, 16));
        applyButton.addActionListener(this);
        add(applyButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void fetchJobs() {
        String url = "jdbc:mysql://localhost:3306/job_portal";
        String user = "root";
        String password = "MRIKAL1409";

        try {
            conn = DriverManager.getConnection(url, user, password);
            String query = "SELECT job_title, company_name, qualification FROM job"; // Company Name will now contain the username
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String jobTitle = rs.getString("job_title");
                String companyName = rs.getString("company_name");
                String qualification = rs.getString("qualification");
                model.addRow(new Object[]{jobTitle, companyName, qualification});
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String jobTitle = (String) model.getValueAt(selectedRow, 0);
            String companyName = (String) model.getValueAt(selectedRow, 1);
            JOptionPane.showMessageDialog(this, "You've successfully applied for the job: " + jobTitle + " posted by " + companyName);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a job to apply.");
        }
    }

    public static void main(String[] args) {
        new SearchJob();
    }
}
