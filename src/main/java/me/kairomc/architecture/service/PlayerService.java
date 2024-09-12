package me.kairomc.architecture.service;

import com.google.inject.Inject;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerService {

    private final DatabaseService databaseService;
    private final Map<UUID, Integer> playerCache = new HashMap<>();

    @Inject
    public PlayerService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public int getPlayerMoney(Player player) throws SQLException {
        UUID playerUUID = player.getUniqueId();
        if (playerCache.containsKey(playerUUID)) {
            return playerCache.get(playerUUID);
        } else {
            int money = databaseService.getPlayerMoney(playerUUID);
            playerCache.put(playerUUID, money);
            return money;
        }
    }

    public void setPlayerMoney(Player player, int money) throws SQLException {
        UUID playerUUID = player.getUniqueId();
        playerCache.put(playerUUID, money);
        databaseService.setPlayerMoney(playerUUID, money);
    }

    public void createPlayerProfile(Player player) throws SQLException {
        UUID playerUUID = player.getUniqueId();
        if (!playerCache.containsKey(playerUUID)) {
            int money = databaseService.getPlayerMoney(playerUUID);
            playerCache.put(playerUUID, money);
        }
    }
}