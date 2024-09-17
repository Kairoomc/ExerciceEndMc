package me.kairomc.architecture.repository;

import com.google.inject.Inject;
import me.kairomc.architecture.model.PlayerData;
import me.kairomc.architecture.service.DatabaseService;

import java.sql.*;
import java.util.UUID;

public class PlayerRepository {

    private final DatabaseService databaseService;

    @Inject
    public PlayerRepository(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public PlayerData loadPlayerFromDatabase(UUID playerUUID) throws SQLException {
        String query = "SELECT name, money FROM players WHERE uuid = ?";
        try (PreparedStatement stmt = databaseService.getConnection().prepareStatement(query)) {
            stmt.setString(1, playerUUID.toString());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                int money = rs.getInt("money");
                return new PlayerData(playerUUID, name, money);
            }
        }
        return null;
    }

    public void savePlayerToDatabase(PlayerData playerModel) throws SQLException {
        String query = "INSERT INTO players (uuid, name, money) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE name = ?, money = ?";
        try (PreparedStatement stmt = databaseService.getConnection().prepareStatement(query)) {
            stmt.setString(1, playerModel.getUuid().toString());
            stmt.setString(2, playerModel.getName());
            stmt.setInt(3, playerModel.getMoney());
            stmt.setString(4, playerModel.getName());
            stmt.setInt(5, playerModel.getMoney());
            stmt.executeUpdate();
        }
    }
}

