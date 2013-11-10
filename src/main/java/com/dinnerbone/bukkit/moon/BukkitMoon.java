
package com.dinnerbone.bukkit.moon;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitMoon extends JavaPlugin {
    
    private final static String WORLD_NAME = "BukkitMoon";
    private static World moon = null;
    
    @Override
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
            WorldCreator world = new WorldCreator(WORLD_NAME);
            world.environment(World.Environment.NORMAL);
            world.generator(new MoonChunkGenerator());
            moon = Bukkit.getServer().createWorld(world);
        }

        return moon;
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return new MoonChunkGenerator();
    }
}