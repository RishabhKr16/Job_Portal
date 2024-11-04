package jobportal;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class JobSeekerHomePage extends JFrame implements ActionListener {
    JPanel mainPanel, welcomePanel, buttonPanel1, buttonPanel2, buttonPanel3;
    JButton searchButton, viewResultButton, uploadCVButton;
    JLabel welcomeLabel, nameLabel;
    Font titleFont, buttonFont;
    public static JLabel name;

    public JobSeekerHomePage() {
        // Frame settings
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Initialize fonts
        titleFont = new Font("Times New Roman", Font.BOLD, 24);
        buttonFont = new Font("Arial", Font.BOLD, 18);

        // Welcome label
        welcomeLabel = new JLabel("WELCOME");
        welcomeLabel.setFont(titleFont);
        welcomeLabel.setForeground(new Color(0x9C27B0)); // Rich Burgundy
        name = new JLabel("");
        name.setFont(titleFont);
        name.setForeground(new Color(0x9C27B0)); // Rich Burgundy

        // Welcome panel
        welcomePanel = new JPanel();
        welcomePanel.setBackground(new Color(0x424242)); // Charcoal Gray
        welcomePanel.add(welcomeLabel);
        welcomePanel.add(name);
        
        // Buttons
        searchButton = createButton("Search for a job");
        viewResultButton = createButton("View result");
        uploadCVButton = createButton("Upload CV");

        // Button panels
        buttonPanel1 = createButtonPanel(searchButton);
        buttonPanel2 = createButtonPanel(viewResultButton);
        buttonPanel3 = createButtonPanel(uploadCVButton);

        // Main panel
        mainPanel = new JPanel(new GridLayout(4, 1));
        mainPanel.setBackground(new Color(0x424242)); // Charcoal Gray
        mainPanel.add(welcomePanel);
        mainPanel.add(buttonPanel1);
        mainPanel.add(buttonPanel2);
        mainPanel.add(buttonPanel3);

        // Add main panel to frame
        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);

        // Action listeners
        searchButton.addActionListener(this);
        viewResultButton.addActionListener(this);
        uploadCVButton.addActionListener(this);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(buttonFont);
        button.setBackground(new Color(0xFFD700)); // Gold Accents
        button.setForeground(Color.BLACK);
        return button;
    }

    private JPanel createButtonPanel(JButton button) {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0x424242)); // Charcoal Gray
        panel.add(button);
        return panel;
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == searchButton) {
            new SearchJob();
        } else if (ae.getSource() == viewResultButton) {
            new Result();
        } else if (ae.getSource() == uploadCVButton) {
            new CVChooser();
            JOptionPane.showMessageDialog(null, "Your CV has been Uploaded.");
        }
    }

    public static void main(String[] args) {
        new JobSeekerHomePage();
    }
}