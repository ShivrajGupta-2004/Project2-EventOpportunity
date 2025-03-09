package login1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Update {
    private JPanel contentPanel;
    private JLabel successMessage;
    private JTextField eventIdField, t1, t2, t3, t4, t5, t6, t7, noticeField;
    private JButton updateButton, deleteButton;

    public Update() {
        contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel l1 = new JLabel("Update/Delete Event");
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

        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
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

        gbc.gridx = 0;
        gbc.gridy = 10;
        contentPanel.add(updateButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 10;
        contentPanel.add(deleteButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 11;
        successMessage.setHorizontalAlignment(SwingConstants.LEFT);
        contentPanel.add(successMessage, gbc);

        // Action for Update Button
        updateButton.addActionListener(e -> {
            String eventId = eventIdField.getText();
            String typeOfEvent = t1.getText();
            String date = t2.getText();
            String location = t3.getText();
            String payment = t5.getText();
            String timing = t7.getText();
            String participants = t4.getText();
            String dressCode = t6.getText();
            String notice = noticeField.getText();

            // Update the event in the database
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/event", "root", "");
                 PreparedStatement pst = con.prepareStatement(
                         "UPDATE events SET TypeOfEvent = ?, Date = ?, Location = ?, Payment = ?, Timing = ?, Participants = ?, DressCode = ?, Notice = ? WHERE EventId = ?")) {

                pst.setString(1, typeOfEvent);
                pst.setString(2, date);
                pst.setString(3, location);
                pst.setString(4, payment);
                pst.setString(5, timing);
                pst.setString(6, participants);
                pst.setString(7, dressCode);
                pst.setString(8, notice);
                pst.setString(9, eventId);

                int rowsAffected = pst.executeUpdate();

                if (rowsAffected > 0) {
                    successMessage.setForeground(Color.GREEN);
                    successMessage.setText("Event updated successfully!");
                } else {
                    successMessage.setForeground(Color.RED);
                    successMessage.setText("Failed to update event.");
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                successMessage.setForeground(Color.RED);
                successMessage.setText("Error: " + ex.getMessage());
            }
        });

        // Action for Delete Button
        deleteButton.addActionListener(e -> {
            String eventId = eventIdField.getText();

            // Delete the event from the database
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/event", "root", "");
                 PreparedStatement pst = con.prepareStatement("DELETE FROM events WHERE EventId = ?")) {

                pst.setString(1, eventId);

                int rowsAffected = pst.executeUpdate();

                if (rowsAffected > 0) {
                    successMessage.setForeground(Color.RED);
                    successMessage.setText("Event deleted successfully!");
                    // Clear the fields after delete
                    clearFields();
                } else {
                    successMessage.setForeground(Color.RED);
                    successMessage.setText("Failed to delete event.");
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                successMessage.setForeground(Color.RED);
                successMessage.setText("Error: " + ex.getMessage());
            }
        });

        // Add KeyListener to eventIdField to fetch data when EventId is entered
        eventIdField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String eventId = eventIdField.getText();
                if (!eventId.isEmpty()) {
                    fetchEventData(eventId);
                } else {
                    clearFields();
                }
            }
        });
    }

    // Method to fetch event data by EventId and display it in the text fields
    public void fetchEventData(String eventId) {
    try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/event", "root", "");
         PreparedStatement pst = con.prepareStatement("SELECT * FROM events WHERE EventId = ?")) {

        System.out.println("Executing query for EventId: " + eventId);  // Debug statement

        pst.setString(1, eventId);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            System.out.println("Event found: " + rs.getString("EventId"));  // Debug statement
            t1.setText(rs.getString("TypeOfEvent"));
            t2.setText(rs.getString("Date"));
            t3.setText(rs.getString("Location"));
            t5.setText(rs.getString("Payment"));
            t7.setText(rs.getString("Timing"));
            t4.setText(rs.getString("Participants"));
            t6.setText(rs.getString("DressCode"));
            noticeField.setText(rs.getString("Notice"));
        } else {
            successMessage.setForeground(Color.RED);
            successMessage.setText("");
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
        successMessage.setForeground(Color.RED);
        successMessage.setText("Error: " + ex.getMessage());
    }
}


    // Method to clear the fields after delete or failure
    public void clearFields() {
        t1.setText("");
        t2.setText("");
        t3.setText("");
        t4.setText("");
        t5.setText("");
        t6.setText("");
        t7.setText("");
        noticeField.setText("");
        eventIdField.setText("");
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }
}
