package me.kairomc.architecture.commands;

import me.kairomc.architecture.service.PlayerService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import java.sql.SQLException;

public class PlayerCommand implements CommandExecutor {

    private final PlayerService playerService;

    @Inject
    public PlayerCommand(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            try {
                playerService.createPlayerProfile(player);
                String playerInfo = player.getName() + " a " + playerService.getPlayerMoney(player) + " pièces d'or.";
                player.sendMessage(playerInfo);
            } catch (SQLException e) {
                player.sendMessage("Erreur lors de la récupération des données du joueur !");
                e.printStackTrace();
            }
        }
        return true;
    }
}