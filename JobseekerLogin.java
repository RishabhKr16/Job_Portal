package jobportal;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

public class JobseekerLogin extends JFrame {
    JPanel p, p1, p2, p3, p4;
    JButton b1, b2;
    JLabel l1, l2;
    JTextField t;
    JPasswordField pwd;
    Font f;

    public JobseekerLogin() {
        setSize(400, 300);
        setLocationRelativeTo(null);
        setTitle("Login Portal");
        f = new Font("Arial", Font.BOLD, 15);
        p = new JPanel(new GridLayout(7, 1));
        p1 = new JPanel();
        p2 = new JPanel();
        p3 = new JPanel();
        p4 = new JPanel();
        p.add(p4);
        
        l1 = new JLabel("Username :");
        l1.setFont(f);
        p1.add(l1);
        t = new JTextField(15);
        p1.add(t);
        
        l2 = new JLabel("Password :");
        l2.setFont(f);
        p2.add(l2);
        pwd = new JPasswordField(15);
        p2.add(pwd);
        
        p.add(p1);
        p.add(p2);
        
        b1 = new JButton("Login");
        p3.add(b1);
        b2 = new JButton("Cancel");
        p3.add(b2);
        
        b1.addActionListener(new LoginEvent());
        b2.addActionListener(e -> setVisible(false));
        
        p.add(p3);
        add(p);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setVisible(true);
    }

    public class LoginEvent implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            char[] passwd = pwd.getPassword();
            boolean flag = checkJobSeekerUser(t.getText().trim(), passwd);
            if (flag) {
                JobSeekerHomePage h = new JobSeekerHomePage();
                JobSeekerHomePage.name.setText(t.getText());
                h.setVisible(true);
                setVisible(false);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid login credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        public boolean checkJobSeekerUser(String username, char[] password) {
            boolean isValid = false;
            String query = "SELECT * FROM job_seekers WHERE username = ? AND password = ?";
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/job_portal", "root", "MRIKAL1409");
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, username);
                pstmt.setString(2, new String(password).trim());

                // Debugging output
                System.out.println("Username: " + username);
                System.out.println("Password: " + new String(password).trim());

                ResultSet rs = pstmt.executeQuery();
                isValid = rs.next();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return isValid;
        }
    }
}
