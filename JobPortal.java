package jobportal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

class AddObject {
    Employer e = new Employer();
    Employer ep = new Employer();
    Employer emp = new Employer();
    Jobseeker job = new Jobseeker();
    Jobseeker job1 = new Jobseeker();
    Jobseeker job2 = new Jobseeker();
    static ArrayList<Employer> al = new ArrayList<Employer>();
    static ArrayList<Jobseeker> alj = new ArrayList<Jobseeker>();

    public void Adde(String name, String user, String phone, char[] pwd, String email) {
        ep.setName(name);
        ep.setUserName(user);
        ep.setPhone(phone);
        ep.setPassword(pwd);
        ep.setEmail(email);
        printArrayList();
        al.add(ep);
        printArrayList();
    }

    public void Addj(String name, String user, String phone, char[] pwd, String email, String qual) {
        job2.setName(name);
        job2.setUserName(user);
        job2.setPhone(phone);
        job2.setPassword(pwd);
        job2.setEmail(email);
        job2.setQual(qual);
        alj.add(job2);
    }

    void printArrayList() {
        Iterator<Employer> itr = al.iterator();
        while (itr.hasNext()) {
            e = itr.next();
        }
    }

    void printArrayListjs() {
        Iterator<Jobseeker> itr1 = alj.iterator();
        while (itr1.hasNext()) {
            job = itr1.next();
        }
    }

    public void setArrayList() {
        try {
            File in = new File("Employer.ser");
            int size = (int) in.length();
            if (size != 0) {
                FileInputStream fileIn = new FileInputStream("Employer.ser");
                BufferedInputStream bf = new BufferedInputStream(fileIn);
                ObjectInputStream i = new ObjectInputStream(bf);
                al = (ArrayList) i.readObject();
                i.close();
                bf.close();
                fileIn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            File in1 = new File("Jobseeker.ser");
            int size = (int) in1.length();
            if (size != 0) {
                FileInputStream fileIn = new FileInputStream("Jobseeker.ser");
                BufferedInputStream bf = new BufferedInputStream(fileIn);
                ObjectInputStream i = new ObjectInputStream(bf);
                alj = (ArrayList) i.readObject();
                i.close();
                bf.close();
                fileIn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setfile() {
        try {
            FileOutputStream fileout = new FileOutputStream("Employer.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileout);
            out.writeObject(al);
            out.close();
            fileout.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try {
            FileOutputStream fileout1 = new FileOutputStream("Jobseeker.ser");
            ObjectOutputStream out1 = new ObjectOutputStream(fileout1);
            out1.writeObject(alj);
            out1.close();
            fileout1.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.exit(0);
    }

    boolean existUser(String username) {
        Iterator<Employer> itr = al.iterator();
        while (itr.hasNext()) {
            e = itr.next();
            if (e.getUserName().equals(username)) {
                JOptionPane.showMessageDialog(null, "Username already exists");
                return false;
            }
        }
        return true;
    }

    boolean checkUser(String user, char[] pwd) {
        char[] passwd;
        Iterator<Employer> itr = al.iterator();
        while (itr.hasNext()) {
            emp = itr.next();
            passwd = emp.getPassword();
            if (emp.getUserName().equals(user)) {
                if (Arrays.equals(pwd, passwd)) {
                    return true;
                }
            }
        }
        JOptionPane.showMessageDialog(null, "Invalid Username or Password");
        return false;
    }

    boolean existJobSeekerUser(String username) {
        Iterator<Jobseeker> itr2 = alj.iterator();
        while (itr2.hasNext()) {
            job1 = itr2.next();
            if (job1.getUserName().equals(username)) {
                JOptionPane.showMessageDialog(null, "Username already exists");
                return false;
            }
        }
        return true;
    }

    boolean checkJobSeekerUser(String user, char[] pwd) {
        char[] passwd;
        Iterator<Jobseeker> itr = alj.iterator();
        while (itr.hasNext()) {
            job = itr.next();
            passwd = job.getPassword();
            if (job.getUserName().equals(user)) {
                if (Arrays.equals(pwd, passwd)) {
                    return true;
                }
            }
        }
        JOptionPane.showMessageDialog(null, "Invalid Username or Password");
        return false;
    }

    String[] str = new String[20];
    int i = 0;

    public String[] getList() {
        Iterator<Employer> itr5 = al.iterator();
        Employer emp = new Employer();
        while (itr5.hasNext()) {
            emp = itr5.next();
            str[i++] = emp.getName();
        }
        return str;
    }
}

class WelcomeFrame extends JFrame implements ActionListener {
    private final JLabel label1, imglabel, infolabel, extral;
    private final JButton JregisterButton, closeButton;
    private final JButton JloginButton, Eregister, Elogin;
    private final JLabel label2, label3;
    private JPanel panel, panel1, panel2, panel3, panel4, panel5, panel6, imgpanel, infopanel, extra;
    ImageIcon pic;
    AddObject ao;

    public WelcomeFrame() {
        // Set frame properties
        setTitle("Job Recruitment Portal");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Initialize components
        label1 = new JLabel("Jomble : JOB RECRUITMENT PORTAL", SwingConstants.CENTER);
        JregisterButton = new JButton("Register");
        JloginButton = new JButton("Login");
        label2 = new JLabel("Are You a Jobseeker?", SwingConstants.CENTER);
        label3 = new JLabel("Employer", SwingConstants.CENTER);
        Eregister = new JButton("Register");
        Elogin = new JButton("Login");
        closeButton = new JButton("Close");
        extral = new JLabel("\n", SwingConstants.CENTER);
        infolabel = new JLabel("If you are looking for a Job or an Employee, then you've come to the right place!\nRegister Below, IT'S FREE!", SwingConstants.CENTER);

        // Set fonts and colors
        Font titleFont = new Font("Times New Roman", Font.BOLD, 36); // Increased font size
        Font buttonFont = new Font("Arial", Font.BOLD, 20); // Increased button font size
        label1.setFont(titleFont);
        label1.setForeground(Color.YELLOW);
        label1.setOpaque(true);
        label1.setBackground(Color.BLACK);
        label2.setFont(buttonFont);
        label2.setForeground(Color.YELLOW);
        label3.setFont(buttonFont);
        label3.setForeground(Color.YELLOW);
        infolabel.setForeground(Color.WHITE);
        infolabel.setFont(new Font("Calibri", Font.ITALIC, 18));

        // Create panels with black background
        panel = new JPanel(new GridLayout(9, 1));
        panel.setBackground(Color.BLACK);
        panel1 = new JPanel(new FlowLayout());
        panel1.setBackground(Color.BLACK);
        panel2 = new JPanel(new FlowLayout());
        panel2.setBackground(Color.BLACK);
        panel3 = new JPanel(new FlowLayout());
        panel3.setBackground(Color.BLACK);
        panel4 = new JPanel(new FlowLayout());
        panel4.setBackground(Color.BLACK);
        panel5 = new JPanel();
        panel5.setBackground(Color.BLACK);
        panel6 = new JPanel();
        panel6.setBackground(Color.BLACK);
        extra = new JPanel();
        extra.setBackground(Color.BLACK);
        infopanel = new JPanel();
        infopanel.setBackground(Color.BLACK);
        imgpanel = new JPanel();
        imgpanel.setBackground(Color.BLACK);

        // Load image
        pic = new ImageIcon(getClass().getResource("logo_1.jpg"));
        imglabel = new JLabel(pic);
        Dimension size = new Dimension(400, 120); // Increased logo size
        imglabel.setPreferredSize(size);
        imgpanel.add(imglabel);

        // Add components to panels
        panel1.add(label1);
        panel.add(panel1);
        panel.add(imgpanel);
        extra.add(extral);
        panel.add(extra);
        infopanel.add(infolabel);
        panel.add(infopanel);
        panel3.add(label2);
        panel.add(panel2);
        panel4.add(JregisterButton);
        panel4.add(JloginButton);
        panel5.add(label3);
        panel6.add(Eregister);
        panel6.add(Elogin);
        panel6.add(closeButton);
        panel.add(panel3);
        panel.add(panel4);
        panel.add(panel5);
        panel.add(panel6);

        // Add panel to frame
        add(panel, BorderLayout.CENTER);
        
        // Add action listeners
        JregisterButton.addActionListener(this);
        JloginButton.addActionListener(this);
        Eregister.addActionListener(this);
        Elogin.addActionListener(this);
        closeButton.addActionListener(this);

        setVisible(true);
        ao = new AddObject();
        ao.setArrayList();
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == JregisterButton) {
            new RegisterJobSeeker();
        } else if (ae.getSource() == JloginButton) {
            new JobseekerLogin();
        } else if (ae.getSource() == Eregister) {
            RegisterEmployer remp = new RegisterEmployer();
            remp.setTitle("Registration");
            remp.setSize(600, 600);
            remp.setLocationRelativeTo(null);
            remp.setVisible(true);
            remp.setDefaultCloseOperation(EXIT_ON_CLOSE);
        } else if (ae.getSource() == Elogin) {
            new LoginEmployer();
        } else if (ae.getSource() == closeButton) {
            ao.setfile();
        }
    }
}

public class JobPortal {
    public static void main(String[] args) {
        new WelcomeFrame();
    }
}
