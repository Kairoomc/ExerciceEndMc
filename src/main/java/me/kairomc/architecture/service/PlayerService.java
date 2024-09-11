package me.kairomc.architecture.service;

import org.bukkit.entity.Player;

import javax.inject.Inject;

public class PlayerService {

    private final DatabaseService databaseService;

    @Inject
    public PlayerService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public String getPlayerInfo(Player player) {
        return "Joueur: " + player.getName() + " a " + databaseService.getPlayerMoney(player) + " pièces d'or.";
    }
}
