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
public class PBEffectGenConvertToHeavenly extends PBEffectGenerate
{
    public PBEffectGenConvertToHeavenly()
    {
    }

    public PBEffectGenConvertToHeavenly(int time, double range, int unifiedSeed)
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
            if (isBlockAnyOf(block, Blocks.SNOW, Blocks.SNOW_LAYER, Blocks.FIRE, Blocks.RED_FLOWER, Blocks.YELLOW_FLOWER, Blocks.TALLGRASS))
            {
                setBlockToAirSafe(world, pos);
            }
            else if (isBlockAnyOf(block, Blocks.STONE, Blocks.END_STONE, Blocks.NETHERRACK, Blocks.SOUL_SAND, Blocks.SAND, Blocks.DIRT, Blocks.GRASS))
            {
                if (world.getBlockState(pos.up()).getBlock() == Blocks.AIR)
                {
                    if (!world.isRemote && world.rand.nextInt(6 * 6) == 0)
                    {
                        setBlockSafe(world, pos, Blocks.DIRT.getDefaultState());
                        setBlockSafe(world, pos.up(), Blocks.LOG.getDefaultState());
                        setBlockSafe(world, pos.up(2), Blocks.LEAVES.getDefaultState());
                    }
                    else if (!world.isRemote && world.rand.nextInt(6 * 6) == 0)
                    {
                        int pHeight = random.nextInt(5) + 3;
                        for (int yp = 0; yp < pHeight; yp++)
                            setBlockSafe(world, pos.up(yp), Blocks.QUARTZ_BLOCK.getDefaultState());
                    }
                    else if (!world.isRemote && world.rand.nextInt(2 * 2) == 0)
                    {
                        setBlockSafe(world, pos, Blocks.GLASS.getDefaultState());
                    }
                    else if (!world.isRemote && world.rand.nextInt(8 * 8) == 0)
                    {
                        setBlockSafe(world, pos, Blocks.GLASS.getDefaultState());
                        setBlockSafe(world, pos.down(), Blocks.REDSTONE_LAMP.getDefaultState());
                        setBlockSafe(world, pos.down(2), Blocks.REDSTONE_BLOCK.getDefaultState());
                    }
                    else
                        setBlockSafe(world, pos, Blocks.STONEBRICK.getDefaultState());
                }
                else
                {
                    setBlockSafe(world, pos, Blocks.STONEBRICK.getDefaultState());
                }
            }
            else if (isBlockAnyOf(block, Blocks.OBSIDIAN, Blocks.LAVA, Blocks.ICE))
            {
                setBlockSafe(world, pos, Blocks.FLOWING_WATER.getDefaultState());
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
                lazilySpawnEntity(world, entity, random, "Sheep", 1.0f / (20 * 20), pos);
            }
        }
    }
}
