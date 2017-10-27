package com.dinnerbone.bukkit.moon;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitMoon extends JavaPlugin {
	private final static String WORLD_NAME = "world_moon";
	private static World moon = null;

	@Override
	public void onEnable() {
		// Create command executor
		getCommand("moon").setExecutor(new MoonCommandExec());
		// Log status
		this.getLogger().info("Enabled!");
	}

	// Creates and returns a world
	public static World getMoon() {
		if (moon == null) {
			WorldCreator creator = new WorldCreator(WORLD_NAME);
			creator.environment(World.Environment.NORMAL);
			creator.generator(new MoonChunkGenerator());
			moon = Bukkit.getServer().createWorld(creator);
		}
		return moon;
	}

	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		return new MoonChunkGenerator();
	}
}