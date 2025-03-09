package login1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddEvent {
    private JPanel contentPanel;
    private JLabel successMessage;
    private JTextField eventIdField, t1, t2, t3, t4, t5, t6, t7, noticeField;
    private JButton addButton;

    public AddEvent() {
        contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel l1 = new JLabel("Add New Event");
        l1.setFont(new Font("Arial", Font.BOLD, 24));
        l1.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel l2 = new JLabel("Event ID: ");
        JLabel l3 = new JLabel("Type of Event: ");
        JLabel l4 = new JLabel("Date: ");
        JLabel l5 = new JLabel("Location: ");
        JLabel l6 = new JLabel("Payment: ");
        JLabel l7 = new JLabel("Timing: ");
        JLabel l8 = new JLabel("Participants: ");
        JLabel l9 = new JLabel("Dress Code: ");
        JLabel l10 = new JLabel("Notice: ");

        eventIdField = new JTextField(20);
        t1 = new JTextField(20);
        t2 = new JTextField(20);
        t3 = new JTextField(20);
        t4 = new JTextField(5);
        t5 = new JTextField(20);
        t6 = new JTextField(20);
        t7 = new JTextField(20);
        noticeField = new JTextField(20);

        addButton = new JButton("Add");
        successMessage = new JLabel("");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        contentPanel.add(l1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        contentPanel.add(l2, gbc);
        gbc.gridx = 1;
        contentPanel.add(eventIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        contentPanel.add(l3, gbc);
        gbc.gridx = 1;
        contentPanel.add(t1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        contentPanel.add(l4, gbc);
        gbc.gridx = 1;
        contentPanel.add(t2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        contentPanel.add(l5, gbc);
        gbc.gridx = 1;
        contentPanel.add(t3, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        contentPanel.add(l6, gbc);
        gbc.gridx = 1;
        contentPanel.add(t5, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        contentPanel.add(l7, gbc);
        gbc.gridx = 1;
        contentPanel.add(t7, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        contentPanel.add(l8, gbc);
        gbc.gridx = 1;
        contentPanel.add(t4, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        contentPanel.add(l9, gbc);
        gbc.gridx = 1;
        contentPanel.add(t6, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        contentPanel.add(l10, gbc);
        gbc.gridx = 1;
        contentPanel.add(noticeField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 10;
        contentPanel.add(addButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 11;
        successMessage.setHorizontalAlignment(SwingConstants.LEFT);
        contentPanel.add(successMessage, gbc);

        // Add action listener for the button
        addButton.addActionListener(e -> {
            String eventId = eventIdField.getText();
            String typeOfEvent = t1.getText();
            String date = t2.getText();
            String location = t3.getText();
            String payment = t5.getText();
            String timing = t7.getText();
            String participants = t4.getText();
            String dressCode = t6.getText();
            String notice = noticeField.getText();

            // Insert the data into the database
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/event", "root", "");
                 PreparedStatement pst = con.prepareStatement(
                         "INSERT INTO events (EventId, TypeOfEvent, Date, Location, Payment, Timing, Participants, DressCode, Notice) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

                pst.setString(1, eventId);
                pst.setString(2, typeOfEvent);
                pst.setString(3, date);
                pst.setString(4, location);
                pst.setString(5, payment);
                pst.setString(6, timing);
                pst.setString(7, participants);
                pst.setString(8, dressCode);
                pst.setString(9, notice);

                int rowsAffected = pst.executeUpdate();

                // Show success or error message
                if (rowsAffected > 0) {
                    successMessage.setForeground(Color.GREEN);
                    successMessage.setText("Event added successfully!");
                } else {
                    successMessage.setForeground(Color.RED);
                    successMessage.setText("Failed to add event.");
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                successMessage.setForeground(Color.RED);
                successMessage.setText("Database error: " + ex.getMessage());
            }
        });
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }
}
