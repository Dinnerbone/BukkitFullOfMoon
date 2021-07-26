package com.github.pocketkid2.moongenerator;

import java.util.Random;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.pocketkid2.moongenerator.chunkgenerators.MoonChunkGenerator;

public class MoonGenerator extends JavaPlugin {

	public static final int CHUNK_SIZE = 16;
	public static final int SIGN_LINES = 4;

	// TERRAIN SETTINGS
	private int terrain_height_variance = 5;
	private double terrain_noise_scale = 0.015625;
	private int terrain_depth = 60;

	// CRATER SETTINGS
	private boolean craters_enabled;
	private int crater_chance;
	private int crater_percentage_scale;
	private int min_crater_radius;
	private int max_crater_radius;

	@Override
	public void onEnable() {
		saveDefaultConfig();
		var cfg = getConfig();

		// TERRAIN SETTINGS
		terrain_height_variance = cfg.getInt("height-variance", 5);
		terrain_noise_scale = 1.0D / cfg.getInt("noise-scale", 64);
		terrain_depth = cfg.getInt("depth", 60);

		// CRATER SETTINGS
		craters_enabled = cfg.getBoolean("craters.enabled", true);
		crater_chance = cfg.getInt("craters.chance", 2);
		crater_percentage_scale = cfg.getInt("craters.percentage", 100);
		min_crater_radius = cfg.getInt("craters.min-radius", 8);
		max_crater_radius = cfg.getInt("craters.max-radius", 24);

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

	//
	// TERRAIN GENERATION OPTIONS
	//

	public int getHeightVariance() {
		return terrain_height_variance;
	}

	public double getNoiseScale() {
		return terrain_noise_scale;
	}

	public int getTerrainDepth() {
		return terrain_depth;
	}

	//
	// CRATER GENERATION OPTIONS
	//

	public boolean areCratersEnabled() {
		return craters_enabled;
	}

	public int getCraterChance() {
		return crater_chance;
	}

	public int getCraterPercentageScale() {
		return crater_percentage_scale;
	}

	public int getMinCraterRadius() {
		return min_crater_radius;
	}

	public int getMaxCraterRadius() {
		return max_crater_radius;
	}

	//
	// Inclusive-range random integer generator
	//
	public static int randomRange(Random random, int min, int max) {
		return random.nextInt(max - min + 1) + min;
	}
}