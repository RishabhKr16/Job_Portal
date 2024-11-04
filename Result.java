package jobportal;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class Result extends JFrame {
    Connection conn;
    JTable table;

    public Result() {
        String url = "jdbc:mysql://localhost:3306/job_portal";
        String user = "root";
        String password = "MRIKAL1409";
        
        String[] columns = {"Candidate Name", "Qualification", "Company Name", "Status"};
        
        ArrayList<String[]> dataList = new ArrayList<>();

        try {
            conn = DriverManager.getConnection(url, user, password);
            // Update the SQL query to fetch the status column
            String query = "SELECT candidate_name, qualification, company_name, status FROM candidates";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                String candidateName = rs.getString("candidate_name");
                String qualification = rs.getString("qualification");
                String companyName = rs.getString("company_name");
                String status = rs.getString("status"); // Fetching the status column
                
                // Add the status to the data list
                dataList.add(new String[]{candidateName, qualification, companyName, status});
            }
            
            String[][] data = new String[dataList.size()][4];
            for (int i = 0; i < dataList.size(); i++) {
                data[i] = dataList.get(i);
            }

            rs.close();
            stmt.close();
            conn.close();
            
            table = new JTable(data, columns);
            table.setPreferredScrollableViewportSize(new Dimension(500, 300));
            table.setFillsViewportHeight(true);
            table.setEnabled(false);
            
            JScrollPane scrollPane = new JScrollPane(table);
            add(scrollPane);

            setSize(600, 400);
            setLocationRelativeTo(null);
            setTitle("Result Table");
            setVisible(true);
            setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Result();
    }
}
