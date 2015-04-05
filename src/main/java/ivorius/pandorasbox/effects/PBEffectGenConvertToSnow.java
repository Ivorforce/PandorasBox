/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
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
    public void generateOnBlock(World world, EntityPandorasBox entity, Vec3 effectCenter, Random random, int pass, BlockPos pos, double range)
    {
        Block block = world.getBlockState(pos).getBlock();

        if (pass == 0)
        {
            if (isBlockAnyOf(block, Blocks.flowing_water, Blocks.water))
            {
                setBlockSafe(world, pos, Blocks.ice.getDefaultState());
            }
            else if (block.getMaterial() == Material.air && Blocks.snow_layer.canPlaceBlockAt(world, pos))
            {
                setBlockSafe(world, pos, Blocks.snow_layer.getDefaultState());
            }
            else if (block == Blocks.fire)
            {
                setBlockSafe(world, pos, Blocks.air.getDefaultState());
            }
            else if (block == Blocks.flowing_lava)
            {
                setBlockSafe(world, pos, Blocks.cobblestone.getDefaultState());
            }
            else if (block == Blocks.lava)
            {
                setBlockSafe(world, pos, Blocks.obsidian.getDefaultState());
            }
        }
        else
        {
            if (canSpawnEntity(world, block, pos))
            {
                lazilySpawnEntity(world, entity, random, "SnowMan", 1.0f / (20 * 20), pos);
            }
        }
    }
}
