package me.kairomc.architecture.service;

import com.google.inject.Inject;
import me.kairomc.architecture.utils.PlayerUtils;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import java.sql.SQLException;


public class PlayerService {

    private final DatabaseService databaseService;
    private final Map<UUID, PlayerUtils> playerCache = new HashMap<>();

    @Inject
    public PlayerService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public PlayerUtils getPlayer(Player player) throws SQLException {
        UUID playerUUID = player.getUniqueId();
        if (playerCache.containsKey(playerUUID)) {
            return playerCache.get(playerUUID);
        } else {
            PlayerUtils playerModel = loadPlayerFromDatabase(playerUUID);
            if (playerModel == null) {
                playerModel = new PlayerUtils(playerUUID, player.getName(), 0);
                savePlayerToDatabase(playerModel);
            }
            playerCache.put(playerUUID, playerModel);
            return playerModel;
        }
    }

    public int getPlayerMoney(Player player) throws SQLException {
        return getPlayer(player).getMoney();
    }

    public void setPlayerMoney(Player player, int money) throws SQLException {
        PlayerUtils playerModel = getPlayer(player);
        playerModel.setMoney(money);
        playerCache.put(playerModel.getUuid(), playerModel);
        savePlayerToDatabase(playerModel);
    }

    public void createPlayerProfile(Player player) throws SQLException {
        UUID playerUUID = player.getUniqueId();
        if (!playerCache.containsKey(playerUUID)) {
            PlayerUtils playerModel = new PlayerUtils(playerUUID, player.getName(), 0);
            playerCache.put(playerUUID, playerModel);
            savePlayerToDatabase(playerModel);
        }
    }

    private PlayerUtils loadPlayerFromDatabase(UUID playerUUID) throws SQLException {
        String query = "SELECT name, money FROM players WHERE uuid = ?";
        try (PreparedStatement stmt = databaseService.getConnection().prepareStatement(query)) {
            stmt.setString(1, playerUUID.toString());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                int money = rs.getInt("money");
                return new PlayerUtils(playerUUID, name, money);
            }
        }
        return null;
    }

    private void savePlayerToDatabase(PlayerUtils playerModel) throws SQLException {
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