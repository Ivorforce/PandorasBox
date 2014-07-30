/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBEffectGenConvertToEnd extends PBEffectGenerate
{
    public PBEffectGenConvertToEnd()
    {
    }

    public PBEffectGenConvertToEnd(int time, double range, int unifiedSeed)
    {
        super(time, range, 2, unifiedSeed);
    }

    @Override
    public void generateOnBlock(World world, EntityPandorasBox entity, Random random, int pass, int x, int y, int z, double range)
    {
        Block block = world.getBlock(x, y, z);

        if (pass == 0)
        {
            if (isBlockAnyOf(block, Blocks.obsidian))
            {

            }
            else if (isBlockAnyOf(block, Blocks.log, Blocks.log2, Blocks.brown_mushroom_block, Blocks.red_mushroom_block))
            {
                setBlockSafe(world, x, y, z, Blocks.obsidian);
            }
            else if (isBlockAnyOf(block, Blocks.ice, Blocks.flowing_water, Blocks.water, Blocks.snow, Blocks.snow_layer, Blocks.leaves, Blocks.leaves2, Blocks.red_flower, Blocks.yellow_flower, Blocks.vine, Blocks.tallgrass, Blocks.brown_mushroom, Blocks.red_mushroom))
            {
                setBlockSafe(world, x, y, z, Blocks.air);
            }
            else if (world.isBlockNormalCubeDefault(x, y, z, false))
            {
                setBlockSafe(world, x, y, z, Blocks.end_stone);
            }
        }
        else
        {
            if (canSpawnEntity(world, block, x, y, z))
            {
                lazilySpawnEntity(world, entity, random, "Enderman", 1.0f / (20 * 20), x, y, z);
            }
        }
    }
}
