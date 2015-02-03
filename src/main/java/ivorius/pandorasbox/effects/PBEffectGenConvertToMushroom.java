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
    public void generateOnBlock(World world, EntityPandorasBox entity, Vec3 effectCenter, Random random, int pass, BlockPos pos, double range)
    {
        Block block = world.getBlockState(pos).getBlock();

        if (pass == 0)
        {
            if (isBlockAnyOf(block, Blocks.snow, Blocks.snow_layer, Blocks.fire, Blocks.red_flower, Blocks.yellow_flower, Blocks.tallgrass, Blocks.log, Blocks.log2, Blocks.leaves, Blocks.leaves2))
            {
                setBlockToAirSafe(world, pos);
            }
            else if (isBlockAnyOf(block, Blocks.stone, Blocks.end_stone, Blocks.netherrack, Blocks.soul_sand, Blocks.sand, Blocks.dirt, Blocks.grass))
            {
                BlockPos posUp = pos.up();

                if (world.getBlockState(posUp) == Blocks.air)
                {
                    setBlockSafe(world, pos, Blocks.mycelium.getDefaultState());

                    if (!world.isRemote)
                    {
                        if (world.rand.nextInt(6 * 6) == 0)
                        {
                            setBlockSafe(world, posUp, (world.rand.nextBoolean() ? Blocks.brown_mushroom.getDefaultState() : Blocks.red_mushroom.getDefaultState()));
                        }
                        else if (world.rand.nextInt(8 * 8) == 0)
                        {
                            WorldGenBigMushroom mushroomGen = new WorldGenBigMushroom(random.nextInt(2));
                            mushroomGen.generate(world, world.rand, posUp);
                        }
                    }
                }
                else
                {
                    setBlockSafe(world, pos, Blocks.dirt.getDefaultState());
                }
            }
            else if (isBlockAnyOf(block, Blocks.obsidian, Blocks.lava, Blocks.ice))
            {
                setBlockSafe(world, pos, Blocks.flowing_water.getDefaultState());
            }

            if (isBlockAnyOf(block, Blocks.flowing_lava, Blocks.lava))
            {
                setBlockSafe(world, pos, Blocks.water.getDefaultState());
            }
            if (isBlockAnyOf(block, Blocks.obsidian, Blocks.ice))
            {
                setBlockSafe(world, pos, Blocks.water.getDefaultState());
            }
        }
        else
        {
            if (canSpawnEntity(world, block, pos))
            {
                lazilySpawnEntity(world, entity, random, "MushroomCow", 1.0f / (20 * 20), pos);
            }
        }
    }
}
