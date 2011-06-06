
package com.dinnerbone.bukkit.moon;

import java.util.Random;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

public class MoonCraterPopulator implements BlockPopulator {
    private static final int CRATER_CHANCE = 45; // Out of 100
    private static final int MIN_CRATER_SIZE = 3;
    private static final int SMALL_CRATER_SIZE = 8;
    private static final int BIG_CRATER_SIZE = 16;
    private static final int BIG_CRATER_CHANCE = 10; // Out of 100

    public void populate(World world, Random random, Chunk source) {
        if (random.nextInt(100) <= CRATER_CHANCE) {
            int centerX = (source.getX() << 4) + random.nextInt(16);
            int centerZ = (source.getZ() << 4) + random.nextInt(16);
            int centerY = world.getHighestBlockYAt(centerX, centerZ);
            Vector center = new BlockVector(centerX, centerY, centerZ);
            int radius = 0;

            if (random.nextInt(100) <= BIG_CRATER_CHANCE) {
                radius = random.nextInt(BIG_CRATER_SIZE - MIN_CRATER_SIZE + 1) + MIN_CRATER_SIZE;
            } else {
                radius = random.nextInt(SMALL_CRATER_SIZE - MIN_CRATER_SIZE + 1) + MIN_CRATER_SIZE;
            }

            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        Vector position = center.clone().add(new Vector(x, y, z));

                        if (center.distance(position) <= radius + 0.5) {
                            world.getBlockAt(position.toLocation(world)).setType(Material.AIR);
                        }
                    }
                }
            }
        }
    }
}
