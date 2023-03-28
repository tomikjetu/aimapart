package tomikjetu.AiMapArt;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import tomikjetu.AiMapArt.AiMapArt;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

/***
 *
 * @author CodedRed
 *
 * CustomFile code provided by CodedRed.
 */
class CustomFile {

    private final AiMapArt plugin = AiMapArt.getPlugin(AiMapArt.class);
    private FileConfiguration dataConfig = null;
    private File dataConfigFile = null;
    private final String name;

    public CustomFile(String name) {
        this.name = name;
        saveDefaultConfig();
    }

    public void reloadConfig() {
        if (dataConfigFile == null)
            dataConfigFile = new File(plugin.getDataFolder(),name);

        this.dataConfig = YamlConfiguration
                .loadConfiguration(dataConfigFile);

        InputStream defConfigStream = plugin.getResource(name);
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration
                    .loadConfiguration(new InputStreamReader(defConfigStream));
            this.dataConfig.setDefaults(defConfig);
        }
    }

    public FileConfiguration getConfig() {
        if (this.dataConfig == null)
            reloadConfig();
        return this.dataConfig;
    }

    public void saveConfig() {
        if ((dataConfig == null) || (dataConfigFile == null))
            return;
        try {
            getConfig().save(dataConfigFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to "
                    + dataConfigFile, e);
        }
    }

    public void saveDefaultConfig() {
        if (dataConfigFile == null)
            dataConfigFile = new File(plugin.getDataFolder(), name);
        if (!dataConfigFile.exists())
            plugin.saveResource(name, false);
    }

}
