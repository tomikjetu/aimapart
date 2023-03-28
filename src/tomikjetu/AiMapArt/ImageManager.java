package tomikjetu.AiMapArt;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.map.MapView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageManager implements Listener {

    public static ImageManager instance = null;

    public static ImageManager getInstance() {
        if (instance == null) instance = new ImageManager();
        return instance;
    }

    AiMapArt plugin = AiMapArt.getPlugin(AiMapArt.class);

    public void init() {
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
        File f = new File(plugin.getDataFolder() + File.separator + "images");
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
            f.mkdir();
        }
    }

    public void saveImage(Integer id, BufferedImage image) {
        try {
            File f = new File(plugin.getDataFolder() + "/images/" + id + ".png");
            ImageIO.write(image, "png", f);
        } catch (IOException ignored) {
        }
    }


    @EventHandler
    public void onMapInitEvent(MapInitializeEvent event) {
        if (hasImage(event.getMap().getId())) {
            MapView view = event.getMap();
            view.getRenderers().clear();
            view.addRenderer(new CustomMapRenderer(getImage(view.getId())));
            view.setScale(MapView.Scale.FARTHEST);
            view.setTrackingPosition(false);
        }
    }

    public boolean hasImage(int id) {
        File f = new File(plugin.getDataFolder() + "/images/" + id + ".png");
        return f.exists();
    }

    public BufferedImage getImage(int id) {
        try {
            File f = new File(plugin.getDataFolder() + "/images/" + id + ".png");
            return ImageIO.read(f);
        } catch (IOException ignored) {
        }
        return null;
    }
}