package tomikjetu.AiMapArt;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import java.awt.image.BufferedImage;
public class CustomMapRenderer extends MapRenderer {

    BufferedImage Image;
    boolean done = false;

    public CustomMapRenderer(BufferedImage image){
        Image = image;
    }

    @Override
    public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
        if(done) return;
        mapCanvas.drawImage(0, 0, Image);
        done = true;
    }
}
