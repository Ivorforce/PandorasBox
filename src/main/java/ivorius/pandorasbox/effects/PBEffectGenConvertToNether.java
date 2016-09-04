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
public class PBEffectGenConvertToNether extends PBEffectGenerate
{
    public PBEffectGenConvertToNether()
    {
    }

    public PBEffectGenConvertToNether(int time, double range, int unifiedSeed)
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
            if (isBlockAnyOf(block, Blocks.COBBLESTONE, Blocks.FLOWING_WATER, Blocks.ICE, Blocks.WATER, Blocks.OBSIDIAN))
            {
                setBlockSafe(world, pos, Blocks.LAVA.getDefaultState());
            }
            else if (isBlockAnyOf(block, Blocks.SNOW, Blocks.SNOW_LAYER, Blocks.RED_FLOWER, Blocks.YELLOW_FLOWER, Blocks.VINE, Blocks.BROWN_MUSHROOM_BLOCK, Blocks.RED_MUSHROOM_BLOCK, Blocks.TALLGRASS, Blocks.RED_MUSHROOM, Blocks.BROWN_MUSHROOM, Blocks.LOG, Blocks.LOG2, Blocks.LEAVES, Blocks.LEAVES2))
            {
                setBlockToAirSafe(world, pos);
            }
            else if (isBlockAnyOf(block, Blocks.SAND))
            {
                setBlockSafe(world, pos, Blocks.SOUL_SAND.getDefaultState());
            }
            else if (isBlockAnyOf(block, Blocks.GRASS, Blocks.DIRT, Blocks.STONE, Blocks.END_STONE, Blocks.MYCELIUM, Blocks.HARDENED_CLAY, Blocks.STAINED_HARDENED_CLAY))
            {
                setBlockSafe(world, pos, Blocks.NETHERRACK.getDefaultState());
            }
            else if (world.getBlockState(pos).getBlock() == Blocks.AIR && Blocks.FIRE.canPlaceBlockAt(world, pos))
            {
                if (!world.isRemote && random.nextInt(15) == 0)
                {
                    if (world.rand.nextFloat() < 0.9f)
                    {
                        setBlockSafe(world, pos, Blocks.FIRE.getDefaultState());
                    }
                    else
                    {
                        setBlockSafe(world, pos, Blocks.GLOWSTONE.getDefaultState());
                    }
                }
            }
        }
        else
        {
            if (canSpawnEntity(world, blockState, pos))
            {
                lazilySpawnEntity(world, entity, random, "PigZombie", 1.0f / (15 * 15), pos);
                lazilySpawnEntity(world, entity, random, "LavaSlime", 1.0f / (15 * 15), pos);
            }

            if (canSpawnFlyingEntity(world, blockState, pos))
            {
                lazilySpawnEntity(world, entity, random, "Ghast", 1.0f / (50 * 50 * 50), pos);
                lazilySpawnEntity(world, entity, random, "Blaze", 1.0f / (50 * 50 * 50), pos);
            }
        }
    }
}
