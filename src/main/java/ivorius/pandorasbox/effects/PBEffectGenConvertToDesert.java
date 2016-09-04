/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBEffectGenConvertToDesert extends PBEffectGenerate
{
    public PBEffectGenConvertToDesert()
    {
    }

    public PBEffectGenConvertToDesert(int time, double range, int unifiedSeed)
    {
        super(time, range, 1, unifiedSeed);
    }

    @Override
    public void generateOnBlock(World world, EntityPandorasBox entity, Vec3d effectCenter, Random random, int pass, BlockPos pos, double range)
    {
        Block block = world.getBlockState(pos).getBlock();

        if (pass == 0)
        {
            if (isBlockAnyOf(block, Blocks.ICE, Blocks.FLOWING_WATER, Blocks.WATER, Blocks.SNOW, Blocks.SNOW_LAYER, Blocks.LOG, Blocks.LOG2, Blocks.LEAVES, Blocks.LEAVES2, Blocks.RED_FLOWER, Blocks.YELLOW_FLOWER, Blocks.VINE, Blocks.TALLGRASS, Blocks.BROWN_MUSHROOM, Blocks.BROWN_MUSHROOM_BLOCK, Blocks.RED_MUSHROOM, Blocks.RED_MUSHROOM_BLOCK))
            {
                setBlockToAirSafe(world, pos);
            }
            else if (isBlockAnyOf(block, Blocks.SOUL_SAND, Blocks.STONE, Blocks.END_STONE, Blocks.NETHERRACK, Blocks.GRASS, Blocks.DIRT, Blocks.MYCELIUM, Blocks.HARDENED_CLAY, Blocks.STAINED_HARDENED_CLAY))
            {
                setBlockSafe(world, pos, Blocks.SAND.getDefaultState());

                if (world.getBlockState(pos.up()).getMaterial() == Material.AIR)
                {
                    if (!world.isRemote && random.nextInt(20 * 20) == 0)
                    {
                        setBlockSafe(world, pos.up(1), Blocks.CACTUS.getDefaultState());
                        setBlockSafe(world, pos.up(2), Blocks.CACTUS.getDefaultState());
                        setBlockSafe(world, pos.up(3), Blocks.CACTUS.getDefaultState());
                    }
                }
            }
        }
    }
}
