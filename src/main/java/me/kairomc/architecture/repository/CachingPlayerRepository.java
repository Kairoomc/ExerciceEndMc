package me.kairomc.architecture.repository;
import com.google.inject.Inject;
import me.kairomc.architecture.model.PlayerData;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CachingPlayerRepository {

    private final PlayerRepository playerRepository;
    private final Map<UUID, PlayerData> playerCache = new HashMap<>();

    @Inject
    public CachingPlayerRepository(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public PlayerData getPlayer(Player player) throws SQLException {
        UUID playerUUID = player.getUniqueId();
        if (playerCache.containsKey(playerUUID)) {
            return playerCache.get(playerUUID);
        } else {
            PlayerData playerData = playerRepository.loadPlayerFromDatabase(playerUUID);
            if (playerData == null) {
                playerData = new PlayerData(playerUUID, player.getName(), 0);
                playerRepository.savePlayerToDatabase(playerData);
            }
            playerCache.put(playerUUID, playerData);
            return playerData;
        }
    }

    public void setPlayerMoney(Player player, int money) throws SQLException {
        PlayerData playerData = getPlayer(player);
        playerData.setMoney(money);
        playerCache.put(playerData.getUuid(), playerData);
        playerRepository.savePlayerToDatabase(playerData);
    }

    public int getPlayerMoney(Player player) throws SQLException {
        return getPlayer(player).getMoney();
    }

    public void createPlayerProfile(Player player) throws SQLException {
        UUID playerUUID = player.getUniqueId();
        if (!playerCache.containsKey(playerUUID)) {
            PlayerData playerData = new PlayerData(playerUUID, player.getName(), 0);
            playerCache.put(playerUUID, playerData);
            playerRepository.savePlayerToDatabase(playerData);
        }
    }
}

