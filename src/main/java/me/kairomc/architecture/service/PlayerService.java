package me.kairomc.architecture.service;

import com.google.inject.Inject;
import me.kairomc.architecture.model.PlayerData;
import me.kairomc.architecture.repository.CachingPlayerRepository;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerService {

    private final CachingPlayerRepository cachingPlayerRepository;

    @Inject
    public PlayerService(CachingPlayerRepository cachingPlayerRepository) {
        this.cachingPlayerRepository = cachingPlayerRepository;
    }

    public PlayerData getPlayer(Player player) throws Exception {
        return cachingPlayerRepository.getPlayer(player);
    }

    public int getPlayerMoney(Player player) throws Exception {
        return getPlayer(player).getMoney();
    }

    public void setPlayerMoney(Player player, int money) throws Exception {
        PlayerData playerData = getPlayer(player);
        playerData.setMoney(money);
        cachingPlayerRepository.setPlayerMoney(player, money);
    }

    public void createPlayerProfile(Player player) throws Exception {
        cachingPlayerRepository.createPlayerProfile(player);
    }
}
