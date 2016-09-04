/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBEffectGenConvertToLifeless extends PBEffectGenerate
{
    public PBEffectGenConvertToLifeless()
    {
    }

    public PBEffectGenConvertToLifeless(int time, double range, int unifiedSeed)
    {
        super(time, range, 1, unifiedSeed);
    }

    @Override
    public void generateOnBlock(World world, EntityPandorasBox entity, Vec3d effectCenter, Random random, int pass, BlockPos pos, double range)
    {
        Block block = world.getBlockState(pos).getBlock();

        if (pass == 0)
        {
            if (isBlockAnyOf(block, Blocks.FLOWING_WATER, Blocks.ICE, Blocks.WATER, Blocks.LAVA, Blocks.FLOWING_LAVA, Blocks.SNOW, Blocks.SNOW_LAYER))
            {
                setBlockToAirSafe(world, pos);
            }
            else if (isBlockAnyOf(block, Blocks.VINE, Blocks.BROWN_MUSHROOM_BLOCK, Blocks.RED_MUSHROOM_BLOCK, Blocks.RED_MUSHROOM, Blocks.BROWN_MUSHROOM, Blocks.LOG, Blocks.LOG2, Blocks.LEAVES, Blocks.LEAVES2, Blocks.FIRE))
            {
                setBlockToAirSafe(world, pos);
            }
            else if (isBlockAnyOf(block, Blocks.RED_FLOWER, Blocks.YELLOW_FLOWER, Blocks.TALLGRASS))
            {
                setBlockSafe(world, pos, Blocks.DEADBUSH.getDefaultState());
            }
            else if (isBlockAnyOf(block, Blocks.MYCELIUM, Blocks.GRASS, Blocks.WOOL, Blocks.CAKE))
            {
                setBlockSafe(world, pos, Blocks.DIRT.getDefaultState());
            }
            else if (isBlockAnyOf(block, Blocks.NETHERRACK, Blocks.STAINED_HARDENED_CLAY, Blocks.END_STONE))
            {
                setBlockSafe(world, pos, Blocks.STONE.getDefaultState());
            }
            else if (isBlockAnyOf(block, Blocks.SOUL_SAND))
            {
                setBlockSafe(world, pos, Blocks.SAND.getDefaultState());
            }
        }
    }
}
