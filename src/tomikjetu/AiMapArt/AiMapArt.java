package tomikjetu.AiMapArt;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import tomikjetu.AiMapArt.Commands.AiMapArtCommand;

public class AiMapArt extends JavaPlugin implements Listener {

    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        this.getCommand("aimapart").setExecutor(new AiMapArtCommand());
        ImageManager ImageManager = tomikjetu.AiMapArt.ImageManager.getInstance();
        ImageManager.init();
    }

    public void onDisable() {

    }
}
