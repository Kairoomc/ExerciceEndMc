package me.kairomc.architecture.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DatabaseService {

    private final Connection connection;

    public DatabaseService(Connection connection) {
        this.connection = connection;
    }

    public int getPlayerMoney(UUID playerUUID) throws SQLException {
        String query = "SELECT money FROM players WHERE uuid = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, playerUUID.toString());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("money");
            } else {
                return 0;
            }
        }
    }

    public void setPlayerMoney(UUID playerUUID, int money) throws SQLException {
        String query = "INSERT INTO players (uuid, money) VALUES (?, ?) ON DUPLICATE KEY UPDATE money = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, playerUUID.toString());
            stmt.setInt(2, money);
            stmt.setInt(3, money);
            stmt.executeUpdate();
        }
    }
}
