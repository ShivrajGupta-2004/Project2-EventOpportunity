package login1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Register extends JFrame {

    JLabel l1, l2, l3, l4, l5, l6, l7, l9, successMessage;
    JTextField t1, t2, t3, tUsername, tAge, tAddress;
    JPasswordField t5;
    JButton b1;
    JRadioButton c1, c2, c3;
    ButtonGroup genderGroup;

    public Register() {
        // Labels
        l1 = new JLabel("Full Name: ");
        l2 = new JLabel("Email: ");
        l3 = new JLabel("Phone: ");
        l4 = new JLabel("Age: ");
        l5 = new JLabel("Gender: ");
        l6 = new JLabel("Address: ");
        l9 = new JLabel("Username: ");
        l7 = new JLabel("Password: ");
        successMessage = new JLabel("");

        // Text fields
        t1 = new JTextField(20);
        t2 = new JTextField(20);
        t3 = new JTextField(20);
        tUsername = new JTextField(20);
        tAge = new JTextField(5);
        tAddress = new JTextField(20);
        t5 = new JPasswordField(20);

        // Gender radio buttons
        c1 = new JRadioButton("Male");
        c2 = new JRadioButton("Female");
        c3 = new JRadioButton("Other");

        genderGroup = new ButtonGroup();
        genderGroup.add(c1);
        genderGroup.add(c2);
        genderGroup.add(c3);

        b1 = new JButton("Submit");

        // Layout setup
        Container cp = getContentPane();
        cp.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Adding components to the frame
        gbc.gridx = 0;
        gbc.gridy = 0;
        cp.add(l1, gbc);
        gbc.gridx = 1;
        cp.add(t1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        cp.add(l2, gbc);
        gbc.gridx = 1;
        cp.add(t2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        cp.add(l3, gbc);
        gbc.gridx = 1;
        cp.add(t3, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        cp.add(l4, gbc);
        gbc.gridx = 1;
        cp.add(tAge, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        cp.add(l5, gbc);
        gbc.gridx = 1;
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        genderPanel.add(c1);
        genderPanel.add(c2);
        genderPanel.add(c3);
        cp.add(genderPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        cp.add(l6, gbc);
        gbc.gridx = 1;
        cp.add(tAddress, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        cp.add(l9, gbc);
        gbc.gridx = 1;
        cp.add(tUsername, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        cp.add(l7, gbc);
        gbc.gridx = 1;
        cp.add(t5, gbc);

        gbc.gridx = 1;
        gbc.gridy = 8;
        cp.add(b1, gbc);

        gbc.gridx = 1;
        gbc.gridy = 9;
        successMessage.setHorizontalAlignment(SwingConstants.CENTER);
        cp.add(successMessage, gbc);

        setTitle("Register Page");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Submit button action
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String fullName = t1.getText();
                String email = t2.getText();
                String phone = t3.getText();
                String ageText = tAge.getText();
                String gender = c1.isSelected() ? "Male" : c2.isSelected() ? "Female" : "Other";
                String address = tAddress.getText();
                String username = tUsername.getText();
                String password = new String(t5.getPassword());

                try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/event", "root", "");
                     PreparedStatement pst = con.prepareStatement(
                             "INSERT INTO login (FullName, Email, Phone, Age, Gender, Address, Username, Password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");) {

                    pst.setString(1, fullName);
                    pst.setString(2, email);
                    pst.setString(3, phone);
                    pst.setInt(4, Integer.parseInt(ageText));
                    pst.setString(5, gender);
                    pst.setString(6, address);
                    pst.setString(7, username);
                    pst.setString(8, password);

                    int rowsAffected = pst.executeUpdate();

                    if (rowsAffected > 0) {
                        successMessage.setForeground(Color.GREEN);
                        successMessage.setText("Registration Successful!");
                        // Open Login1 page
                        dispose();
                        new Login1();
                    } else {
                        successMessage.setForeground(Color.RED);
                        successMessage.setText("Registration failed!");
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    successMessage.setForeground(Color.RED);
                    successMessage.setText("Error: " + ex.getMessage());
                }
            }
        });
    }

    public static void main(String[] args) {
        new Register();
    }
}
