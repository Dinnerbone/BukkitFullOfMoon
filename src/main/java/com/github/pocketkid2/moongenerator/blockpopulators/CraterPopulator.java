package com.github.pocketkid2.moongenerator.blockpopulators;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

import com.github.pocketkid2.moongenerator.MoonGenerator;

public class CraterPopulator extends BlockPopulator {

	private MoonGenerator plugin;

	public CraterPopulator(MoonGenerator p) {
		plugin = p;
	}

	@Override
	public void populate(World world, Random random, Chunk source) {
		if (random.nextInt(plugin.getCraterPercentageScale()) <= plugin.getCraterChance()) {
			// Create random X/Y position in world coords
			var centerX = (source.getX() << 4) + random.nextInt(MoonGenerator.CHUNK_SIZE);
			var centerZ = (source.getZ() << 4) + random.nextInt(MoonGenerator.CHUNK_SIZE);
			var highest = world.getHighestBlockYAt(centerX, centerZ);
			// Create random radius
			var radius = MoonGenerator.randomRange(random, plugin.getMinCraterRadius(), plugin.getMaxCraterRadius());
			// Move the center up by half the radius
			var centerY = highest + Math.ceil(radius / 2.0D);
			Vector center = new BlockVector(centerX, centerY, centerZ);
			// Loop through all the X and Z and Y (local)
			for (var x = -radius; x <= radius; x++)
				for (var y = -radius; y <= radius; y++)
					for (var z = -radius; z <= radius; z++) {
						// Create the block position to modify in chunk coordinates
						var position = center.clone().add(new Vector(x, y, z));
						// Check radius
						if (center.distance(position) <= radius + 0.5)
							world.getBlockAt(position.toLocation(world)).setType(Material.AIR);
					}
		}
	}
}
