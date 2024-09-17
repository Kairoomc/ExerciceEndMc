package me.kairomc.architecture.guice;

import com.google.inject.AbstractModule;
import lombok.RequiredArgsConstructor;
import me.kairomc.architecture.ExercisePlugin;
import me.kairomc.architecture.service.DatabaseService;
import me.kairomc.architecture.service.PlayerService;

@RequiredArgsConstructor
public class ExerciseModule extends AbstractModule {
    private final ExercisePlugin exercisePlugin;

    public ExerciseModule(ExercisePlugin exercisePlugin) {
        this.exercisePlugin = exercisePlugin;
    }

    @Override
    protected void configure() {
        bind(ExercisePlugin.class).toInstance(exercisePlugin);

        bind(PlayerService.class);
        bind(DatabaseService.class);
    }
}
