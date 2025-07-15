import java.sql.*;

public class DatabaseManager {
    private static final String URL = "jdbc:postgresql://localhost:5432/Startup_master"; // Change to your database name
    private static final String USER = "postgres"; // Change to your username
    private static final String PASSWORD = "admin"; // Change to your password

    // Connect to the database
    public Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Insert Market record
    public void insertMarket(int startupID, int investorID, String demand, String opportunities, String trends, String competition) {
        String insertSQL = "INSERT INTO Market (StartupID, InvestorID, Demand, Opportunities, Trends, Competition) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setInt(1, startupID);
            pstmt.setInt(2, investorID);
            pstmt.setString(3, demand);
            pstmt.setString(4, opportunities);
            pstmt.setString(5, trends);
            pstmt.setString(6, competition);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    

    // Insert Investor record
    public void insertInvestor(int investorID, double finance, String marketOpportunities) {
        String insertSQL = "INSERT INTO Investor (InvestorID, Finance, MarketOpportunities) VALUES (?, ?, ?)";
        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setInt(1, investorID);
            pstmt.setDouble(2, finance);
            pstmt.setString(3, marketOpportunities);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Read Market records
    public ResultSet readMarket() {
        String selectSQL = "SELECT * FROM Market";
        try {
            Connection connection = connect();
            PreparedStatement pstmt = connection.prepareStatement(selectSQL);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Read Investor records
    public ResultSet readInvestor() {
        String selectSQL = "SELECT * FROM Investor";
        try {
            Connection connection = connect();
            PreparedStatement pstmt = connection.prepareStatement(selectSQL);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update Market record
    public void updateMarket(int marketID, int startupID, int investorID, String demand, String opportunities, String trends, String competition) {
        String updateSQL = "UPDATE Market SET StartupID = ?, InvestorID = ?, Demand = ?, Opportunities = ?, Trends = ?, Competition = ? WHERE MarketID = ?";
        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
            pstmt.setInt(1, startupID);
            pstmt.setInt(2, investorID);
            pstmt.setString(3, demand);
            pstmt.setString(4, opportunities);
            pstmt.setString(5, trends);
            pstmt.setString(6, competition);
            pstmt.setInt(7, marketID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update Investor record
    public void updateInvestor(int investorID, double finance, String marketOpportunities) {
        String updateSQL = "UPDATE Investor SET Finance = ?, MarketOpportunities = ? WHERE InvestorID = ?";
        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
            pstmt.setDouble(1, finance);
            pstmt.setString(2, marketOpportunities);
            pstmt.setInt(3, investorID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete Market record
    public void deleteMarket(int marketID) {
        String deleteSQL = "DELETE FROM Market WHERE MarketID = ?";
        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(deleteSQL)) {
            pstmt.setInt(1, marketID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete Investor record
    public void deleteInvestor(int investorID) {
        String deleteSQL = "DELETE FROM Investor WHERE InvestorID = ?";
        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(deleteSQL)) {
            pstmt.setInt(1, investorID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
