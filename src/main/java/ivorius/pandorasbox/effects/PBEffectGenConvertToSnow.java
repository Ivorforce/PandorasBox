/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBEffectGenConvertToSnow extends PBEffectGenerate
{
    public PBEffectGenConvertToSnow()
    {
    }

    public PBEffectGenConvertToSnow(int time, double range, int unifiedSeed)
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
            if (isBlockAnyOf(block, Blocks.FLOWING_WATER, Blocks.WATER))
            {
                setBlockSafe(world, pos, Blocks.ICE.getDefaultState());
            }
            else if (blockState.getMaterial() == Material.AIR && Blocks.SNOW_LAYER.canPlaceBlockAt(world, pos))
            {
                setBlockSafe(world, pos, Blocks.SNOW_LAYER.getDefaultState());
            }
            else if (block == Blocks.FIRE)
            {
                setBlockSafe(world, pos, Blocks.AIR.getDefaultState());
            }
            else if (block == Blocks.FLOWING_LAVA)
            {
                setBlockSafe(world, pos, Blocks.COBBLESTONE.getDefaultState());
            }
            else if (block == Blocks.LAVA)
            {
                setBlockSafe(world, pos, Blocks.OBSIDIAN.getDefaultState());
            }
        }
        else
        {
            if (canSpawnEntity(world, blockState, pos))
            {
                lazilySpawnEntity(world, entity, random, "SnowMan", 1.0f / (20 * 20), pos);
            }
        }
    }
}
