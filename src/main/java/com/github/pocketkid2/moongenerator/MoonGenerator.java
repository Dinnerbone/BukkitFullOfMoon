package com.github.pocketkid2.moongenerator;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.pocketkid2.moongenerator.chunkgenerators.MoonChunkGenerator;

public class MoonGenerator extends JavaPlugin {

	@Override
	public void onEnable() {
		getLogger().info("Enabled!");
	}

	@Override
	public void onDisable() {
		getLogger().info("Disabled!");

	}

	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		return new MoonChunkGenerator();
	}
}