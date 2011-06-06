
package com.dinnerbone.bukkit.moon;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitMoon extends JavaPlugin {
    private final static String WORLD_NAME = "BukkitMoon";
    private static World moon = null;

    public void onDisable() {
    }
    
    public void onEnable() {
        PluginDescriptionFile desc = this.getDescription();

        System.out.println( desc.getName() + " version " + desc.getVersion() + " is enabled!" );

        getCommand("moon").setExecutor(new MoonCommandExec());
    }
    
    public boolean anonymousCheck(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Cannot execute that command, I don't know who you are!");
            return true;
        } else {
            return false;
        }
    }

    public static World getMoon() {
        if (moon == null) {
            moon = Bukkit.getServer().createWorld(WORLD_NAME, World.Environment.NORMAL, new MoonChunkGenerator());
        }

        return moon;
    }
}