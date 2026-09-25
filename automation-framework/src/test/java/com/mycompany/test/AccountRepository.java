package com.mycompany.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRepository {

    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;

    public AccountRepository(String dbUrl, String dbUser, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    /**
     * Speichert ein Account-Objekt in der Datenbank oder aktualisiert den Kontostand, falls die ID existiert.
     */
    public void save(String id, Account account) throws SQLException {
        String sql = "INSERT INTO accounts (id, balance) VALUES (?, ?) " +
                     "ON CONFLICT (id) DO UPDATE SET balance = EXCLUDED.balance";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.setDouble(2, account.getBalance());
            stmt.executeUpdate();
        }
    }

    /**
     * Liest einen Account anhand der ID aus der Datenbank aus.
     */
    public Account findById(String id) throws SQLException {
        String sql = "SELECT balance FROM accounts WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Account account = new Account();
                    account.deposit(rs.getDouble("balance"));
                    return account;
                }
            }
        }
        return null;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }
}