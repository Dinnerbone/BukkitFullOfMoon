
package com.dinnerbone.bukkit.moon;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.Material;
import org.bukkit.World;

public class MoonChunkGenerator implements ChunkGenerator {
    public byte[] generate(World world, Random random, int cx, int cz) {
        byte[] result = new byte[32768];

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < 50; y++) {
                    result[(x * 16 + z) * 128 + y] = (byte)Material.SPONGE.getId();
                }
            }
        }

        return result;
    }

    public boolean canSpawn(World world, int x, int z) {
        return true;
    }

    public List<BlockPopulator> getDefaultPopulators(World world) {
        return Arrays.asList((BlockPopulator)new MoonCraterPopulator(), new FlagPopulator());
    }
}
