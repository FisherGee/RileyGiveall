package me.fishergee.rileygiveall;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.List;

public class GiveallCmd implements CommandExecutor {

    Plugin plugin = RileyGiveall.plugin;
    FileConfiguration config = plugin.getConfig();
    File configFile = new File(plugin.getDataFolder(), "config.yml");

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if(!(sender.hasPermission("giveall.use"))){
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cYou do not have permission to use this command."));
            return true;
        }
        if(!(cmd.getName().equalsIgnoreCase("giveall"))) return true;
        if(args.length > 0){
            if(args[0].equalsIgnoreCase("reload")){
                config = YamlConfiguration.loadConfiguration(configFile);
                sender.sendMessage(ChatColor.GREEN + "Reloaded config.");
                return true;
            }
        }
        if(!(sender instanceof Player)) return true;
        Player giver = (Player) sender;
        ItemStack giveItem = new ItemStack(giver.getItemInHand());
        if(giveItem.getType().equals(Material.AIR)){
            giver.sendMessage(ChatColor.RED + "You cannot give nothing!");
            return true;
        }

        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("giver-message")));

        List<String> broadcast = config.getStringList("giveall-message");
        for(Player player : Bukkit.getOnlinePlayers()){
            if(player.equals(giver)) continue; //stop the giver from receiving rewards
            player.getInventory().addItem(giveItem);
            for(String line : broadcast){
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
            }
            player.playSound(player.getLocation(), config.getString("sound.type"), config.getInt("sound.volume"), config.getInt("sound.pitch"));
        }
        return true;
    }
}
