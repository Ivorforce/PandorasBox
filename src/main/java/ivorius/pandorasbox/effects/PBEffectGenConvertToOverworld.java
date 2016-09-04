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
public class PBEffectGenConvertToOverworld extends PBEffectGenerate
{
    public PBEffectGenConvertToOverworld()
    {
    }

    public PBEffectGenConvertToOverworld(int time, double range, int unifiedSeed)
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
            if (isBlockAnyOf(block, Blocks.SNOW, Blocks.SNOW_LAYER))
            {
                setBlockToAirSafe(world, pos);
            }
            else if (isBlockAnyOf(block, Blocks.STONE, Blocks.END_STONE, Blocks.NETHERRACK, Blocks.SOUL_SAND, Blocks.SAND, Blocks.MYCELIUM, Blocks.DIRT))
            {
                BlockPos posUp = pos.up();
                if (world.getBlockState(posUp).getBlock() == Blocks.AIR)
                {
                    setBlockSafe(world, pos, Blocks.GRASS.getDefaultState());

                    if (!world.isRemote)
                    {
                        if (random.nextInt(2 * 2) == 0)
                        {
                            setBlockSafe(world, posUp, Blocks.TALLGRASS.getStateFromMeta(random.nextInt(1) + 1));
                        }
                        else if (random.nextInt(5 * 5) == 0)
                        {
                            setBlockSafe(world, posUp, (world.rand.nextBoolean() ? Blocks.RED_FLOWER.getDefaultState() : Blocks.YELLOW_FLOWER.getDefaultState()));
                        }
                    }
                }
                else
                {
                    setBlockSafe(world, pos, Blocks.DIRT.getDefaultState());
                }
            }
            else if (isBlockAnyOf(block, Blocks.FIRE, Blocks.BROWN_MUSHROOM, Blocks.RED_MUSHROOM, Blocks.BROWN_MUSHROOM_BLOCK, Blocks.RED_MUSHROOM_BLOCK))
            {
                setBlockSafe(world, pos, Blocks.AIR.getDefaultState());
            }

            if (isBlockAnyOf(block, Blocks.FLOWING_LAVA, Blocks.LAVA))
            {
                setBlockSafe(world, pos, Blocks.WATER.getDefaultState());
            }
            if (isBlockAnyOf(block, Blocks.OBSIDIAN, Blocks.ICE))
            {
                setBlockSafe(world, pos, Blocks.WATER.getDefaultState());
            }
        }
        else
        {
            if (canSpawnEntity(world, blockState, pos))
            {
                lazilySpawnEntity(world, entity, random, "Pig", 1.0f / (30 * 30), pos);
                lazilySpawnEntity(world, entity, random, "Sheep", 1.0f / (30 * 30), pos);
                lazilySpawnEntity(world, entity, random, "Cow", 1.0f / (30 * 30), pos);
                lazilySpawnEntity(world, entity, random, "Chicken", 1.0f / (30 * 30), pos);
            }
        }
    }
}
