package me.kairomc.architecture;


import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.sun.xml.internal.ws.developer.SchemaValidation;
import me.kairomc.architecture.guice.CommandInitializer;
import me.kairomc.architecture.guice.ExerciseModule;
import me.kairomc.architecture.repository.CachingPlayerRepository;
import me.kairomc.architecture.service.PlayerService;
import org.bukkit.plugin.java.JavaPlugin;

public class ExercisePlugin extends JavaPlugin {

    @Inject
    private CommandInitializer commandInitializer;

    @Inject
    private PlayerService playerService;

    @Override
    public void onEnable() {
        ExerciseModule exerciseModule = new ExerciseModule(this);
        Injector injector = Guice.createInjector(exerciseModule);
        injector.injectMembers(this);
        commandInitializer.registerCommands();
    }

}
