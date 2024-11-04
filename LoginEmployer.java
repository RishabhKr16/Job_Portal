package jobportal;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

class LoginEmployer extends JFrame {

    JPanel p, p1, p2, p3, p4;
    JButton b1, b2;
    JLabel l1, l2;
    public JTextField t;
    JPasswordField pwd;
    Font f;

    public LoginEmployer() {
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
        
        event e = new event();
        b1.addActionListener(e);
        event1 e1 = new event1();
        b2.addActionListener(e1);
        
        p.add(p3);
        add(p);
        setVisible(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    public class event implements ActionListener {
        boolean flag;

        @Override
        public void actionPerformed(ActionEvent e) {
            char[] passwd = pwd.getPassword();
            flag = checkUser(t.getText().trim(), passwd);
            if (flag) {
                String username = t.getText().trim(); // Get the username
                EmployerHome eh = new EmployerHome(username); // Pass the username
                EmployerHome.name.setText(username);
                eh.setVisible(true);
                setVisible(false);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid login credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        public boolean checkUser(String username, char[] password) {
            boolean isValid = false;
            String query = "SELECT * FROM employers WHERE username = ? AND password = ?";
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/job_portal", "root", "MRIKAL1409");
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, username);
                pstmt.setString(2, new String(password).trim());

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

    public class event1 implements ActionListener {
        public void actionPerformed(ActionEvent e1) {
            setVisible(false);
        }
    }
}
