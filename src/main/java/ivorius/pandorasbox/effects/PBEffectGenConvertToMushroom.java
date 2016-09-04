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
import net.minecraft.world.gen.feature.WorldGenBigMushroom;

import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBEffectGenConvertToMushroom extends PBEffectGenerate
{
    public PBEffectGenConvertToMushroom()
    {
    }

    public PBEffectGenConvertToMushroom(int time, double range, int unifiedSeed)
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
            if (isBlockAnyOf(block, Blocks.SNOW, Blocks.SNOW_LAYER, Blocks.FIRE, Blocks.RED_FLOWER, Blocks.YELLOW_FLOWER, Blocks.TALLGRASS, Blocks.LOG, Blocks.LOG2, Blocks.LEAVES, Blocks.LEAVES2))
            {
                setBlockToAirSafe(world, pos);
            }
            else if (isBlockAnyOf(block, Blocks.STONE, Blocks.END_STONE, Blocks.NETHERRACK, Blocks.SOUL_SAND, Blocks.SAND, Blocks.DIRT, Blocks.GRASS))
            {
                BlockPos posUp = pos.up();

                if (world.getBlockState(posUp) == Blocks.AIR)
                {
                    setBlockSafe(world, pos, Blocks.MYCELIUM.getDefaultState());

                    if (!world.isRemote)
                    {
                        if (world.rand.nextInt(6 * 6) == 0)
                        {
                            setBlockSafe(world, posUp, (world.rand.nextBoolean() ? Blocks.BROWN_MUSHROOM.getDefaultState() : Blocks.RED_MUSHROOM.getDefaultState()));
                        }
                        else if (world.rand.nextInt(8 * 8) == 0)
                        {
                            WorldGenBigMushroom mushroomGen = new WorldGenBigMushroom(random.nextBoolean() ? Blocks.BROWN_MUSHROOM : Blocks.RED_MUSHROOM);
                            mushroomGen.generate(world, world.rand, posUp);
                        }
                    }
                }
                else
                {
                    setBlockSafe(world, pos, Blocks.DIRT.getDefaultState());
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
                lazilySpawnEntity(world, entity, random, "MushroomCow", 1.0f / (20 * 20), pos);
            }
        }
    }
}
