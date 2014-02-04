package com.dinnerbone.bukkit.moon;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitMoon extends JavaPlugin {
    private final static String WORLD_NAME = "BukkitMoon";
    private static World moon = null;
    
    @Override
    public void onEnable() {
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
            WorldCreator moonCreator = new MoonCreator(WORLD_NAME).environment(World.Environment.NORMAL).generator(new MoonChunkGenerator());
            moon = Bukkit.getServer().createWorld(moonCreator);
            moon.setTime(14000L);
        }

        return moon;
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return new MoonChunkGenerator();
    }
}