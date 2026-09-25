package com.mycompany.test;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//import static org.junit.Assert.assertEquals;
//NEU (JUnit 5)
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
class AccountRepositoryTest {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpassword");

    @Test
    void shouldConnectToDatabase() throws SQLException {
        assertTrue(postgres.isRunning());

        try (Connection connection = DriverManager.getConnection(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword())) {
            
            assertTrue(connection.isValid(2));
        }
    }
    
    @Test
    void shouldSaveAndRetrieveAccount() throws SQLException {
        try (Connection conn = DriverManager.getConnection(postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword())) {
            // Tabellenstruktur anlegen
            conn.createStatement().execute("CREATE TABLE accounts (id VARCHAR(50) PRIMARY KEY, balance DOUBLE PRECISION)");

            // Account speichern
            var stmt = conn.prepareStatement("INSERT INTO accounts (id, balance) VALUES (?, ?)");
            stmt.setString(1, "ACC-123");
            stmt.setDouble(2, 250.0);
            stmt.executeUpdate();

            // Account auslesen
            var rs = conn.createStatement().executeQuery("SELECT balance FROM accounts WHERE id = 'ACC-123'");
            assertTrue(rs.next());
            assertEquals(250.0, rs.getDouble("balance"));
        }
    }
}
