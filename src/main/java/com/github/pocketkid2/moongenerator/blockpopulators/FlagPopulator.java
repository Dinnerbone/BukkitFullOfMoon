package com.github.pocketkid2.moongenerator.blockpopulators;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.generator.BlockPopulator;

public class FlagPopulator extends BlockPopulator {
	// TODO: Put these in a config
	private static final int FLAG_CHANCE = 1; 	// Out of 200
	private static final int FLAG_HEIGHT = 3; 	// Fence post height

	@Override
	public void populate(World world, Random random, Chunk source) {
		if (random.nextInt(200) <= FLAG_CHANCE) {
			// Create random X/Y in world coords
			int centerX = (source.getX() << 4) + random.nextInt(16);
			int centerZ = (source.getZ() << 4) + random.nextInt(16);
			int centerY = world.getHighestBlockYAt(centerX, centerZ);
			BlockFace direction = null;
			Block top = null;

			// Choose random direction
			int dir = random.nextInt(4);
			switch (dir) {
			case 0:
				direction = BlockFace.NORTH;
				break;
			case 1:
				direction = BlockFace.EAST;
				break;
			case 2:
				direction = BlockFace.SOUTH;
				break;
			case 3:
				direction = BlockFace.WEST;
			}

			// Create the fence post
			for (int y = centerY; y < centerY + FLAG_HEIGHT; y++) {
				top = world.getBlockAt(centerX, y, centerZ);
				top.setType(Material.FENCE);
			}

			// Create a sign in the direction
			Block signBlock = top.getRelative(direction);
			signBlock.setType(Material.WALL_SIGN);
			BlockState state = signBlock.getState();

			// Set the sign data
			if (state instanceof Sign) {
				Sign sign = (Sign)state;
				org.bukkit.material.Sign data = (org.bukkit.material.Sign)state.getData();

				data.setFacingDirection(direction);
				sign.setLine(0, "---------|*****");
				sign.setLine(1, "---------|*****");
				sign.setLine(2, "-------------");
				sign.setLine(3, "-------------");
				sign.update(true);
			}
		}
	}
}
