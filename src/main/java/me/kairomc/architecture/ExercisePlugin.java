package me.kairomc.architecture;


import com.google.inject.Guice;
import com.google.inject.Injector;
import com.sun.xml.internal.ws.developer.SchemaValidation;
import me.kairomc.architecture.guice.CommandInitializer;
import me.kairomc.architecture.guice.ExerciseModule;
import org.bukkit.plugin.java.JavaPlugin;

public class ExercisePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        ExerciseModule exerciseModule = new ExerciseModule(this);
        Injector injector = Guice.createInjector(exerciseModule);

        injector.getInstance(CommandInitializer.class).registerCommands();



    }

}
