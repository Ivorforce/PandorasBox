/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
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
    public void generateOnBlock(World world, EntityPandorasBox entity, Vec3 effectCenter, Random random, int pass, BlockPos pos, double range)
    {
        Block block = world.getBlockState(pos).getBlock();

        if (pass == 0)
        {
            if (isBlockAnyOf(block, Blocks.obsidian))
            {

            }
            else if (isBlockAnyOf(block, Blocks.log, Blocks.log2, Blocks.brown_mushroom_block, Blocks.red_mushroom_block))
            {
                setBlockSafe(world, pos, Blocks.obsidian.getDefaultState());
            }
            else if (isBlockAnyOf(block, Blocks.ice, Blocks.flowing_water, Blocks.water, Blocks.snow, Blocks.snow_layer, Blocks.leaves, Blocks.leaves2, Blocks.red_flower, Blocks.yellow_flower, Blocks.vine, Blocks.tallgrass, Blocks.brown_mushroom, Blocks.red_mushroom))
            {
                setBlockToAirSafe(world, pos);
            }
            else if (world.isBlockNormalCube(pos, false))
            {
                setBlockSafe(world, pos, Blocks.end_stone.getDefaultState());
            }
        }
        else
        {
            if (canSpawnEntity(world, block, pos))
            {
                lazilySpawnEntity(world, entity, random, "Enderman", 1.0f / (20 * 20), pos);
            }
        }
    }
}
