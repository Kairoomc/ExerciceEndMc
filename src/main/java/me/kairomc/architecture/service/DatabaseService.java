package me.kairomc.architecture.service;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DatabaseService {

    private final Map<UUID, Integer> playerMoneyDatabase = new HashMap<>();

    public DatabaseService() {
    }

    public int getPlayerMoney(Player player) {
        return playerMoneyDatabase.getOrDefault(player.getUniqueId(), 0);
    }
}
