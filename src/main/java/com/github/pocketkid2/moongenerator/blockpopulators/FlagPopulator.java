package com.github.pocketkid2.moongenerator.blockpopulators;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.generator.BlockPopulator;

import com.github.pocketkid2.moongenerator.MoonGenerator;

public class FlagPopulator extends BlockPopulator {

	private MoonGenerator plugin;

	public FlagPopulator(MoonGenerator p) {
		plugin = p;
	}

	@Override
	public void populate(World world, Random random, Chunk source) {
		if (random.nextInt(plugin.getFlagPercentageScale()) <= plugin.getFlagChance()) {
			// Create random X/Y in world coords
			var startX = (source.getX() << 4) + random.nextInt(MoonGenerator.CHUNK_SIZE);
			var startZ = (source.getZ() << 4) + random.nextInt(MoonGenerator.CHUNK_SIZE);
			var startY = world.getHighestBlockYAt(startX, startZ) + 1;
			BlockFace woolDirection;
			BlockFace signPlaceDirection;
			BlockFace signFaceDirection;
			Block top = null;

			// Choose random direction
			signPlaceDirection = switch (random.nextInt(4)) {
			case 0 -> {
				woolDirection = BlockFace.NORTH;
				signFaceDirection = BlockFace.WEST;
				yield BlockFace.EAST;
			}
			case 1 -> {
				woolDirection = BlockFace.EAST;
				signFaceDirection = BlockFace.NORTH;
				yield BlockFace.SOUTH;
			}
			case 2 -> {
				woolDirection = BlockFace.SOUTH;
				signFaceDirection = BlockFace.EAST;
				yield BlockFace.WEST;
			}
			case 3 -> {
				woolDirection = BlockFace.WEST;
				signFaceDirection = BlockFace.SOUTH;
				yield BlockFace.NORTH;
			}
			default -> throw new IllegalArgumentException("Unexpected value: " + random.nextInt(4));
			};

			// Create the fence post
			for (var y = startY; y < startY + plugin.getFlagHeight(); y++) {
				top = world.getBlockAt(startX, y, startZ);
				top.setType(Material.OAK_FENCE);
			}

			// Create a sign in the direction
			var woolBlock = top.getRelative(woolDirection);
			woolBlock.setType(plugin.getFlagMaterial());

			var signBlock = woolBlock.getRelative(signPlaceDirection);
			signBlock.setType(Material.OAK_WALL_SIGN);

			var state = signBlock.getState();
			var data = signBlock.getBlockData();

			// Set the sign data
			if (state instanceof Sign sign) {
				if (data instanceof WallSign ws)
					ws.setFacing(signFaceDirection);
				if (plugin.getFlagText().size() >= MoonGenerator.SIGN_LINES)
					for (var i = 0; i < MoonGenerator.SIGN_LINES; i++)
						sign.setLine(i, plugin.getFlagText().get(i));
				sign.update(true);
			}
		}
	}
}
