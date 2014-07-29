/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBEffectGenConvertToIce extends PBEffectGenerate
{
    public PBEffectGenConvertToIce()
    {
    }

    public PBEffectGenConvertToIce(int time, double range, int unifiedSeed)
    {
        super(time, range, 2, unifiedSeed);
    }

    @Override
    public void generateOnBlock(World world, EntityPandorasBox entity, Random random, int pass, int x, int y, int z, double range)
    {
        Block block = world.getBlock(x, y, z);

        if (pass == 0)
        {
            if (isBlockAnyOf(block, Blocks.flowing_water, Blocks.water))
            {
                setBlockSafe(world, x, y, z, Blocks.ice);
            }
            else if (block.getMaterial() == Material.air && Blocks.snow_layer.canPlaceBlockAt(world, x, y, z))
            {
                setBlockSafe(world, x, y, z, Blocks.snow_layer);
            }
            else if (block == Blocks.fire)
            {
                setBlockSafe(world, x, y, z, Blocks.air);
            }
            else if (block == Blocks.flowing_lava)
            {
                setBlockSafe(world, x, y, z, Blocks.cobblestone);
            }
            else if (block == Blocks.lava)
            {
                setBlockSafe(world, x, y, z, Blocks.obsidian);
            }
        }
        else
        {
            if (canSpawnEntity(world, block, x, y, z))
            {
                lazilySpawnEntity(world, entity, random, "SnowMan", 1.0f / (20 * 20), x, y, z);
            }
        }
    }
}
