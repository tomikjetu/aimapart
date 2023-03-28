package tomikjetu.AiMapArt;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

public class ComputerRenderer {
    public static Image generate(String prompt) {
        try {
            Random SeedGenerator = new Random();
            int Seed = SeedGenerator.nextInt(10000);
            URL url = new URL("https://api.computerender.com/generate/" + prompt.replaceAll(" ", "-") + ".jpg?seed=" + Seed);
            return ImageIO.read(url);
        } catch (IOException e) {
            return null;
        }
    }
}
