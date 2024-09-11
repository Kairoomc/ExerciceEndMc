package me.kairomc.architecture.guice;

import me.kairomc.architecture.ExercisePlugin;
import me.kairomc.architecture.commands.PlayerCommand;

import javax.inject.Inject;

public class CommandInitializer {

    private final ExercisePlugin exercisePlugin;
    private final PlayerCommand playerCommand;

    @Inject
    public CommandInitializer(ExercisePlugin exercisePlugin, PlayerCommand playerCommand) {
        this.exercisePlugin = exercisePlugin;
        this.playerCommand = playerCommand;
    }

    public void registerCommands() {
        exercisePlugin.getCommand("player").setExecutor(playerCommand);
    }
}
