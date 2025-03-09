package login1;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.AbstractTableModel;

public class Information {

    private JPanel contentPanel;
    private InformationTableModel model;
    private JTable infoTable;

    public JPanel getContentPanel() {
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        String[] columnNames = {"FullName", "Email", "Phone", "Age", "Gender", "Address", "Username"};
        
        model = new InformationTableModel(columnNames);
        infoTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(infoTable);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        fetchInformationData();

        return contentPanel;
    }

    private void fetchInformationData() {
        String url = "jdbc:mysql://localhost:3306/event";
        String username = "root";
        String password = "";

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(url, username, password);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM login";
            rs = stmt.executeQuery(sql);

            model.clearData();

            while (rs.next()) {
                String fullName = rs.getString("FullName");
                String email = rs.getString("Email");
                String phone = rs.getString("Phone");
                int age = rs.getInt("Age");
                String gender = rs.getString("Gender");
                String address = rs.getString("Address");
                String usernameFromDb = rs.getString("Username");

                model.addRow(fullName, email, phone, age, gender, address, usernameFromDb);
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

    class InformationTableModel extends AbstractTableModel {
        private String[] columnNames;
        private Object[][] data = new Object[0][0];

        public InformationTableModel(String[] columnNames) {
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

        public void addRow(String fullName, String email, String phone, int age, String gender, String address, String username) {
            Object[][] newData = new Object[data.length + 1][columnNames.length];
            System.arraycopy(data, 0, newData, 0, data.length);
            newData[data.length] = new Object[]{fullName, email, phone, age, gender, address, username};
            data = newData;
            fireTableRowsInserted(data.length - 1, data.length - 1);
        }

        public void clearData() {
            data = new Object[0][0];
            fireTableDataChanged();
        }
    }
}
