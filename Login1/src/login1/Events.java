package login1;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.AbstractTableModel;

public class Events {

    private JPanel contentPanel;
    private EventTableModel model;
    private JTable eventTable;

    public JPanel getContentPanel() {
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        String[] columnNames = {"EventId", "TypeOfEvent", "Date", "Location", "Payment", "Timing", "Participants", "DressCode", "Notice"};
        
        model = new EventTableModel(columnNames);
        eventTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(eventTable);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> refreshEventData());
        contentPanel.add(refreshButton, BorderLayout.SOUTH);

        fetchEventData();

        return contentPanel;
    }

    private void fetchEventData() {
        String url = "jdbc:mysql://localhost:3306/event";
        String username = "root";
        String password = "";

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(url, username, password);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM events";
            rs = stmt.executeQuery(sql);

            model.clearData();

            while (rs.next()) {
                int eventId = rs.getInt("EventId");
                String typeOfEvent = rs.getString("TypeOfEvent");
                Date date = rs.getDate("Date");
                String location = rs.getString("Location");
                double payment = rs.getDouble("Payment");
                String timing = rs.getString("Timing");
                String participants = rs.getString("Participants");
                String dressCode = rs.getString("DressCode");
                String notice = rs.getString("Notice");

                model.addRow(eventId, typeOfEvent, date, location, payment, timing, participants, dressCode, notice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void refreshEventData() {
        fetchEventData();
        eventTable.repaint();
    }

    class EventTableModel extends AbstractTableModel {
        private String[] columnNames;
        private Object[][] data = new Object[0][0];

        public EventTableModel(String[] columnNames) {
            this.columnNames = columnNames;
        }

        @Override
        public int getRowCount() {
            return data.length;
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int columnIndex) {
            return columnNames[columnIndex];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data[rowIndex][columnIndex];
        }

        public void addRow(int eventId, String typeOfEvent, Date date, String location, double payment,
                           String timing, String participants, String dressCode, String notice) {
            Object[][] newData = new Object[data.length + 1][columnNames.length];
            System.arraycopy(data, 0, newData, 0, data.length);
            newData[data.length] = new Object[]{eventId, typeOfEvent, date, location, payment, timing, participants, dressCode, notice};
            data = newData;
            fireTableRowsInserted(data.length - 1, data.length - 1);
        }

        public void clearData() {
            data = new Object[0][0];
            fireTableDataChanged();
        }
    }
}
