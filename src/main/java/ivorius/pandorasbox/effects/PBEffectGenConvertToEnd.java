/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
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
    public void generateOnBlock(World world, EntityPandorasBox entity, Vec3d effectCenter, Random random, int pass, BlockPos pos, double range)
    {
        IBlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();

        if (pass == 0)
        {
            if (isBlockAnyOf(block, Blocks.OBSIDIAN))
            {

            }
            else if (isBlockAnyOf(block, Blocks.LOG, Blocks.LOG2, Blocks.BROWN_MUSHROOM_BLOCK, Blocks.RED_MUSHROOM_BLOCK))
            {
                setBlockSafe(world, pos, Blocks.OBSIDIAN.getDefaultState());
            }
            else if (isBlockAnyOf(block, Blocks.ICE, Blocks.FLOWING_WATER, Blocks.WATER, Blocks.SNOW, Blocks.SNOW_LAYER, Blocks.LEAVES, Blocks.LEAVES2, Blocks.RED_FLOWER, Blocks.YELLOW_FLOWER, Blocks.VINE, Blocks.TALLGRASS, Blocks.BROWN_MUSHROOM, Blocks.RED_MUSHROOM))
            {
                setBlockToAirSafe(world, pos);
            }
            else if (world.isBlockNormalCube(pos, false))
            {
                setBlockSafe(world, pos, Blocks.END_STONE.getDefaultState());
            }
        }
        else
        {
            if (canSpawnEntity(world, blockState, pos))
            {
                lazilySpawnEntity(world, entity, random, "Enderman", 1.0f / (20 * 20), pos);
            }
        }
    }
}
