package com.github.pocketkid2.moongenerator;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

public class MoonCraterPopulator extends BlockPopulator {
	// TODO: Put these in a config
	private static final int CRATER_CHANCE = 45; 		// Out of 100
	private static final int BIG_CRATER_CHANCE = 10; 	// Out of 100
	private static final int MIN_CRATER_SIZE = 3;
	private static final int SMALL_CRATER_SIZE = 8;
	private static final int BIG_CRATER_SIZE = 16;

	// Returns a random integer within the bounds (inclusive)
	private int randInt(Random random, int hi, int lo) {
		return random.nextInt(hi - lo + 1) + lo;
	}

	@Override
	public void populate(World world, Random random, Chunk source) {
		if (random.nextInt(100) <= CRATER_CHANCE) {
			// Create random X/Y position in world coords
			int centerX = (source.getX() << 4) + random.nextInt(16);
			int centerZ = (source.getZ() << 4) + random.nextInt(16);
			int highest = world.getHighestBlockYAt(centerX, centerZ);
			// Create random radius
			int radius = 0;
			if (random.nextInt(100) <= BIG_CRATER_CHANCE) {
				radius = this.randInt(random, BIG_CRATER_SIZE, MIN_CRATER_SIZE);
			} else {
				radius = this.randInt(random, SMALL_CRATER_SIZE, MIN_CRATER_SIZE);
			}
			// Move the center up by half the radius
			int centerY = highest + (radius / 2);
			Vector center = new BlockVector(centerX, centerY, centerZ);
			// Loop through all the X and Z and Y (local)
			for (int x = -radius; x <= radius; x++) {
				for (int y = -radius; y <= radius; y++) {
					for (int z = -radius; z <= radius; z++) {
						// Skip blocks where the chunk's highest block is already air
						if (world.getHighestBlockYAt(centerX, centerZ) < (centerY + y)) {
							continue;
						}
						// Create the block position to modify in chunk coordinates
						Vector position = center.clone().add(new Vector(x, y, z));
						// Check radius
						if (center.distance(position) <= (radius + 0.5)) {
							world.getBlockAt(position.toLocation(world)).setType(Material.AIR);
						}
					}
				}
			}
		}
	}
}
