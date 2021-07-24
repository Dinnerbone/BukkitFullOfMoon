package com.github.pocketkid2.moongenerator;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.pocketkid2.moongenerator.chunkgenerators.MoonChunkGenerator;

public class MoonGenerator extends JavaPlugin {

	private int terrain_height_variance = 5;
	private double terrain_noise_scale = 0.015625;
	private int terrain_depth = 60;

	private boolean craters;
	private boolean flags;

	@Override
	public void onEnable() {
		saveDefaultConfig();
		var cfg = getConfig();
		terrain_height_variance = cfg.getInt("height-variance", 5);
		terrain_noise_scale = 1.0D / cfg.getInt("noise-scale", 64);
		terrain_depth = cfg.getInt("depth", 60);
		craters = cfg.getBoolean("craters", true);
		flags = cfg.getBoolean("flags", true);
		getLogger().info("Enabled!");
	}

	@Override
	public void onDisable() {
		getLogger().info("Disabled!");

	}

	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		return new MoonChunkGenerator(this);
	}

	public int getHeightVariance() {
		return terrain_height_variance;
	}

	public double getNoiseScale() {
		return terrain_noise_scale;
	}

	public int getTerrainDepth() {
		return terrain_depth;
	}

	public boolean areCratersOn() {
		return craters;
	}

	public boolean areFlagsOn() {
		return flags;
	}
}