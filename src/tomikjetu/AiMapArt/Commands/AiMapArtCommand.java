package tomikjetu.AiMapArt.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import tomikjetu.AiMapArt.ComputerRenderer;
import tomikjetu.AiMapArt.CustomMapRenderer;
import tomikjetu.AiMapArt.ImageManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
public class AiMapArtCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Use /aimapart help");
            return false;
        }

        if (args[0].equals("help")) {
            if (!sender.hasPermission("aimapart.help")) {
                sender.sendMessage(ChatColor.DARK_RED + "Missing aimapart.help permission");
                return false;
            }
            sender.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "===== AI Map Art =====");
            sender.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "AI generated art in minecraft!");
            sender.sendMessage(ChatColor.GREEN + "Author: tomikjetu");
            sender.sendMessage(ChatColor.AQUA + "Support me https://paypal.me/tomikjetu");
            sender.sendMessage(ChatColor.GRAY + "" + ChatColor.BOLD + "Commands:");
            sender.sendMessage(ChatColor.BLUE + "/aimapart help " + ChatColor.GRAY + "displays this message");
            sender.sendMessage(ChatColor.BLUE + "/aimapart [prompt] " + ChatColor.GRAY + "gives you a map with the ai generated image");
            sender.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Prompt has to be in english");
            sender.sendMessage(ChatColor.GRAY + "Aliases: /ai, /aimap, /mapart, /prompt");
            return false;
        }

        if (!sender.hasPermission("aimapart.prompt")) {
            sender.sendMessage(ChatColor.DARK_RED + "Missing aimapart.prompt permission");
            return false;
        }

        String prompt = String.join(" ", args);
        Image img = ComputerRenderer.generate(prompt);

        if (img == null) {
            sender.sendMessage(ChatColor.RED + "I'm Sorry. An Error occurred");
            return false;
        }

        BufferedImage smallImage = new BufferedImage(128, 128, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = smallImage.createGraphics();
        graphics2D.drawImage(img, 0, 0, 128, 128, null);
        graphics2D.dispose();

        ItemStack Map = new ItemStack(Material.FILLED_MAP);
        MapView MapView = Bukkit.createMap(((Player)sender).getWorld());
        MapView.getRenderers().clear();
        MapView.addRenderer(new CustomMapRenderer(smallImage));
        MapMeta meta = (MapMeta) Map.getItemMeta();
        meta.setMapView(MapView);
        meta.setDisplayName(ChatColor.YELLOW + prompt);
        Map.setItemMeta(meta);

        ImageManager ImageManager = tomikjetu.AiMapArt.ImageManager.getInstance();
        ImageManager.saveImage(MapView.getId(), smallImage);

        ((Player) sender).getInventory().addItem(Map);
        sender.sendMessage(ChatColor.YELLOW + "MapArt added to inventory");
        
        Random paypalAd = new Random();
        if(paypalAd.nextInt(10) == 4){
            sender.sendMessage(ChatColor.AQUA + "--------AIMapArt--------");
            sender.sendMessage(ChatColor.AQUA + "Support the author https://paypal.me/tomikjetu");
            sender.sendMessage(ChatColor.AQUA + "------------------------");
        }

        return false;
    }
}
