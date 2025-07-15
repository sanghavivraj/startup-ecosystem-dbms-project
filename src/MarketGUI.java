import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MarketGUI extends JFrame {
    private JTextField marketIDField, startupIDField, investorIDField, demandField, opportunitiesField, trendsField, competitionField;
    private JButton addButton, updateButton, deleteButton, loadButton;
    private JTable marketTable;
    private DefaultTableModel tableModel;
    private DatabaseManager dbManager;

    public MarketGUI() {
        dbManager = new DatabaseManager();
        setTitle("Market Management");
        setLayout(new BorderLayout());

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(8, 2));
        inputPanel.add(new JLabel("MarketID:"));
        marketIDField = new JTextField();
        inputPanel.add(marketIDField);
        inputPanel.add(new JLabel("StartupID:"));
        startupIDField = new JTextField();
        inputPanel.add(startupIDField);
        inputPanel.add(new JLabel("InvestorID:"));
        investorIDField = new JTextField();
        inputPanel.add(investorIDField);
        inputPanel.add(new JLabel("Demand:"));
        demandField = new JTextField();
        inputPanel.add(demandField);
        inputPanel.add(new JLabel("Opportunities:"));
        opportunitiesField = new JTextField();
        inputPanel.add(opportunitiesField);
        inputPanel.add(new JLabel("Trends:"));
        trendsField = new JTextField();
        inputPanel.add(trendsField);
        inputPanel.add(new JLabel("Competition:"));
        competitionField = new JTextField();
        inputPanel.add(competitionField);

        // Button panel
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        loadButton = new JButton("Load");
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(loadButton);

        // Table
        tableModel = new DefaultTableModel(new String[]{"MarketID", "StartupID", "InvestorID", "Demand", "Opportunities", "Trends", "Competition"}, 0);
        marketTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(marketTable);

        // Add components to the frame
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Get input values from the text fields
                    int startupID = Integer.parseInt(startupIDField.getText());
                    int investorID = Integer.parseInt(investorIDField.getText());
                    String demand = demandField.getText();
                    String opportunities = opportunitiesField.getText();
                    String trends = trendsField.getText();
                    String competition = competitionField.getText();
        
                    // Call the insertMarket method to add the data
                    dbManager.insertMarket(startupID, investorID, demand, opportunities, trends, competition);
        
                    // After adding, reload the data to show the new record
                    loadMarket();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(MarketGUI.this, "Please enter valid numeric values for StartupID and InvestorID.");
                }
            }
        });
        

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int marketID = Integer.parseInt(marketIDField.getText());
                int startupID = Integer.parseInt(startupIDField.getText());
                int investorID = Integer.parseInt(investorIDField.getText());
                String demand = demandField.getText();
                String opportunities = opportunitiesField.getText();
                String trends = trendsField.getText();
                String competition = competitionField.getText();
                dbManager.updateMarket(marketID, startupID, investorID, demand, opportunities, trends, competition);
                loadMarket();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int marketID = Integer.parseInt(marketIDField.getText());
                dbManager.deleteMarket(marketID);
                loadMarket();
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadMarket();
            }
        });

        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void loadMarket() {
        try {
            ResultSet resultSet = dbManager.readMarket();
            tableModel.setRowCount(0); // Clear existing data
            while (resultSet != null && resultSet.next()) {
                int marketID = resultSet.getInt("MarketID");
                int startupID = resultSet.getInt("StartupID");
                int investorID = resultSet.getInt("InvestorID");
                String demand = resultSet.getString("Demand");
                String opportunities = resultSet.getString("Opportunities");
                String trends = resultSet.getString("Trends");
                String competition = resultSet.getString("Competition");
                tableModel.addRow(new Object[]{marketID, startupID, investorID, demand, opportunities, trends, competition});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new MarketGUI();
    }
}
