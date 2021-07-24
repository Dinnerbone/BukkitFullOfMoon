package com.github.pocketkid2.moongenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.NoiseGenerator;
import org.bukkit.util.noise.SimplexNoiseGenerator;

public class MoonChunkGenerator extends ChunkGenerator {
	private NoiseGenerator generator;

	private NoiseGenerator getGenerator(World world) {
		if (generator == null) {
			generator = new SimplexNoiseGenerator(world);
		}
		return generator;
	}

	private int getHeight(World world, double x, double y, int variance) {
		NoiseGenerator gen = getGenerator(world);
		double result = gen.noise(x, y);
		result *= variance;
		return NoiseGenerator.floor(result);
	}

	@Override
	public ChunkGenerator.ChunkData generateChunkData(World world, Random random, int cx, int cz, ChunkGenerator.BiomeGrid biome) {
		// Create the chunk data from method
		ChunkGenerator.ChunkData data = this.createChunkData(world);
		// Loop through each X and Z value
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				int height = this.getHeight(world, cx + (x * 0.0625), cz + (z * 0.625), 2) + 60;
				// Loop through each Y value in the column
				for (int y = 0; y < height; y++) {
					// Set the block to be stone if more than 5 blocks down from the surface
					if (y < (height - 5)) {
						data.setBlock(x, y, z, Material.STONE);
					} else {
						data.setBlock(x, y, z, Material.GRAVEL);
					}
				}
			}
		}
		return data;
	}

	@Override
	public List<BlockPopulator> getDefaultPopulators(World world) {
		return Arrays.asList((BlockPopulator)new MoonCraterPopulator(), new FlagPopulator());
	}

	@Override
	public Location getFixedSpawnLocation(World world, Random random) {
		// Create random position within 100 blocks of 0,0
		int x = random.nextInt(200) - 100;
		int z = random.nextInt(200) - 100;
		int y = world.getHighestBlockYAt(x, z);
		return new Location(world, x, y, z);
	}
}
