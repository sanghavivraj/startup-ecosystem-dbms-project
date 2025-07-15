import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InvestorGUI extends JFrame {
    private JTextField investorIDField, financeField, marketOpportunitiesField;
    private JButton addButton, updateButton, deleteButton, loadButton;
    private JTable investorTable;
    private DefaultTableModel tableModel;
    private DatabaseManager dbManager;

    public InvestorGUI() {
        dbManager = new DatabaseManager();
        setTitle("Investor Management");
        setLayout(new BorderLayout());

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("InvestorID:"));
        investorIDField = new JTextField();
        inputPanel.add(investorIDField);
        inputPanel.add(new JLabel("Finance:"));
        financeField = new JTextField();
        inputPanel.add(financeField);
        inputPanel.add(new JLabel("Market Opportunities:"));
        marketOpportunitiesField = new JTextField();
        inputPanel.add(marketOpportunitiesField);

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
        tableModel = new DefaultTableModel(new String[]{"InvestorID", "Finance", "Market Opportunities"}, 0);
        investorTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(investorTable);

        // Add components to the frame
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int investorID = Integer.parseInt(investorIDField.getText());
                double finance = Double.parseDouble(financeField.getText());
                String marketOpportunities = marketOpportunitiesField.getText();
                dbManager.insertInvestor(investorID, finance, marketOpportunities);
                loadInvestor();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int investorID = Integer.parseInt(investorIDField.getText());
                double finance = Double.parseDouble(financeField.getText());
                String marketOpportunities = marketOpportunitiesField.getText();
                dbManager.updateInvestor(investorID, finance, marketOpportunities);
                loadInvestor();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int investorID = Integer.parseInt(investorIDField.getText());
                dbManager.deleteInvestor(investorID);
                loadInvestor();
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadInvestor();
            }
        });

        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void loadInvestor() {
        try {
            ResultSet resultSet = dbManager.readInvestor();
            tableModel.setRowCount(0); // Clear existing data
            while (resultSet != null && resultSet.next()) {
                int investorID = resultSet.getInt("InvestorID");
                double finance = resultSet.getDouble("Finance");
                String marketOpportunities = resultSet.getString("MarketOpportunities");
                tableModel.addRow(new Object[]{investorID, finance, marketOpportunities});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new InvestorGUI();
    }
}
