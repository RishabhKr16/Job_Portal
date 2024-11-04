package jobportal;

import javax.swing.*;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployerHome extends JFrame implements ActionListener {
    private JLabel label1;
    public static JLabel name;
    public JPanel panel, panel1, panel2;
    private JButton button1, button2, button3;
    private Font fnt;
    private String username; // Add this field

    EmployerHome(String username) {
        this.username = username; // Initialize username
        fnt = new Font("Times New Roman", Font.BOLD, 18);
        
        // Initialize labels
        label1 = new JLabel("WELCOME   ");
        label1.setFont(fnt);
        label1.setForeground(Color.YELLOW); // Vibrant Yellow
        name = new JLabel(username); // Display the username
        name.setFont(fnt);
        name.setForeground(Color.YELLOW); // Vibrant Yellow

        // Initialize buttons
        button1 = new JButton("SELECT EMPLOYEE");
        button2 = new JButton("VIEW CVs");
        button3 = new JButton("UPLOAD JOB");

        // Set button colors
        button1.setBackground(Color.GREEN); // Lime Green
        button1.setForeground(Color.WHITE);
        button2.setBackground(Color.MAGENTA); // Hot Pink
        button2.setForeground(Color.WHITE);
        button3.setBackground(Color.BLUE); // Electric Blue
        button3.setForeground(Color.WHITE);

        // Create panels
        panel = new JPanel(new GridLayout(4, 1));
        panel1 = new JPanel();
        panel2 = new JPanel();

        // Add components to panels
        panel1.add(label1);
        panel1.add(name);
        panel2.add(button1);
        panel2.add(button2);
        panel2.add(button3); // Add the upload button

        // Add panels to the main panel
        panel.add(panel1);
        panel.add(panel2); 
        add(panel);
        
        // Set action listeners
        button1.addActionListener(this);
        button2.addActionListener(this);
        button3.addActionListener(this); // Add ActionListener for button3
        
        // Set frame properties
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setBackground(Color.BLACK); // Set background color to black for contrast
        panel.setBackground(Color.BLACK); // Set panel background to black
        panel1.setBackground(Color.BLACK); // Set panel1 background to black
        panel2.setBackground(Color.BLACK); // Set panel2 background to black
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == button1) {
            new JobSelect();
        } else if (ae.getSource() == button2) {
            new ViewCVs();
        } else if (ae.getSource() == button3) {
            new UploadJob(username); // Pass the username to UploadJob
        }
    }
}
