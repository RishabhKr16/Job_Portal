package jobportal;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class JobSelect extends JFrame implements ActionListener {
    JPanel panel;
    JButton selectButton;
    JTable table;
    String[] columnNames = {"Candidate Name", "Qualification"};
    Object[][] data;
    
    public JobSelect() {
        loadDataFromDatabase();
        panel = new JPanel(new BorderLayout());
        table = new JTable(data, columnNames);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        selectButton = new JButton("Select Candidate");
        selectButton.addActionListener(this);
        panel.add(selectButton, BorderLayout.SOUTH);

        add(panel);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setTitle("Select Candidates");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadDataFromDatabase() {
        ArrayList<Object[]> candidates = new ArrayList<>();
        String url = "jdbc:mysql://localhost:3306/job_portal"; // Change database name as needed
        String user = "root"; // Your MySQL username
        String password = "MRIKAL1409"; // Your MySQL password

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT name, qualification FROM candidates")) {
             
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String qualification = resultSet.getString("qualification");
                candidates.add(new Object[]{name, qualification});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        data = new Object[candidates.size()][2];
        for (int i = 0; i < candidates.size(); i++) {
            data[i] = candidates.get(i);
        }
    }

    public void actionPerformed(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String candidateName = (String) table.getValueAt(selectedRow, 0);
            String qualification = (String) table.getValueAt(selectedRow, 1);
            JOptionPane.showMessageDialog(this, "You've selected " + candidateName + " with qualification: " + qualification);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a candidate.");
        }
    }
}
