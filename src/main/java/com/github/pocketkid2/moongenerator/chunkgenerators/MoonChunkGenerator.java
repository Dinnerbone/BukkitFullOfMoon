package com.github.pocketkid2.moongenerator.chunkgenerators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.NoiseGenerator;
import org.bukkit.util.noise.SimplexNoiseGenerator;

import com.github.pocketkid2.moongenerator.MoonGenerator;
import com.github.pocketkid2.moongenerator.blockpopulators.CraterPopulator;
import com.github.pocketkid2.moongenerator.blockpopulators.FlagPopulator;

public class MoonChunkGenerator extends ChunkGenerator {

	private static final int CHUNK_SIZE = 16;

	private NoiseGenerator generator;

	private MoonGenerator plugin;

	public MoonChunkGenerator(MoonGenerator p) {
		plugin = p;
	}

	private NoiseGenerator getGenerator(World world) {
		if (generator == null)
			generator = new SimplexNoiseGenerator(world);
		return generator;
	}

	private int getHeight(World world, double x, double y) {
		var gen = getGenerator(world);
		var result = plugin.getHeightVariance() * gen.noise(x * plugin.getNoiseScale(), y * plugin.getNoiseScale());
		return plugin.getTerrainDepth() + NoiseGenerator.floor(result);
	}

	@Override
	public ChunkGenerator.ChunkData generateChunkData(World world, Random random, int cx, int cz, ChunkGenerator.BiomeGrid biome) {
		var data = createChunkData(world);
		// Loop through each X and Z value
		for (var x = 0; x < CHUNK_SIZE; x++)
			for (var z = 0; z < CHUNK_SIZE; z++) {
				var height = getHeight(world, cx * CHUNK_SIZE + x, cz * CHUNK_SIZE + z);
				for (var y = 0; y < height; y++)
					if (y == 0)
						data.setBlock(x, y, z, Material.BEDROCK);
					else if (y == height - 1)
						data.setBlock(x, y, z, Material.GRAVEL);
					else
						data.setBlock(x, y, z, Material.STONE);
			}
		return data;
	}

	@Override
	public List<BlockPopulator> getDefaultPopulators(World world) {
		List<BlockPopulator> populators = new ArrayList<>();
		if (plugin.areCratersOn())
			populators.add(new CraterPopulator());
		if (plugin.areFlagsOn())
			populators.add(new FlagPopulator());
		return populators;
	}

	@Override
	public Location getFixedSpawnLocation(World world, Random random) {
		// Create random position within 100 blocks of 0,0
		var x = random.nextInt(200) - 100;
		var z = random.nextInt(200) - 100;
		var y = world.getHighestBlockYAt(x, z);
		return new Location(world, x, y, z);
	}
}
