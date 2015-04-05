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
    public void generateOnBlock(World world, EntityPandorasBox entity, Vec3 effectCenter, Random random, int pass, BlockPos pos, double range)
    {
        Block block = world.getBlockState(pos).getBlock();

        if (pass == 0)
        {
            if (isBlockAnyOf(block, Blocks.snow, Blocks.snow_layer, Blocks.fire, Blocks.red_flower, Blocks.yellow_flower, Blocks.tallgrass))
            {
                setBlockToAirSafe(world, pos);
            }
            else if (isBlockAnyOf(block, Blocks.stone, Blocks.end_stone, Blocks.netherrack, Blocks.soul_sand, Blocks.sand, Blocks.dirt, Blocks.grass))
            {
                if (world.getBlockState(pos.up()).getBlock() == Blocks.air)
                {
                    if (!world.isRemote && world.rand.nextInt(6 * 6) == 0)
                    {
                        setBlockSafe(world, pos, Blocks.dirt.getDefaultState());
                        setBlockSafe(world, pos.up(), Blocks.log.getDefaultState());
                        setBlockSafe(world, pos.up(2), Blocks.leaves.getDefaultState());
                    }
                    else if (!world.isRemote && world.rand.nextInt(6 * 6) == 0)
                    {
                        int pHeight = random.nextInt(5) + 3;
                        for (int yp = 0; yp < pHeight; yp++)
                            setBlockSafe(world, pos.up(yp), Blocks.quartz_block.getDefaultState());
                    }
                    else if (!world.isRemote && world.rand.nextInt(2 * 2) == 0)
                    {
                        setBlockSafe(world, pos, Blocks.glass.getDefaultState());
                    }
                    else if (!world.isRemote && world.rand.nextInt(8 * 8) == 0)
                    {
                        setBlockSafe(world, pos, Blocks.glass.getDefaultState());
                        setBlockSafe(world, pos.down(), Blocks.redstone_lamp.getDefaultState());
                        setBlockSafe(world, pos.down(2), Blocks.redstone_block.getDefaultState());
                    }
                    else
                        setBlockSafe(world, pos, Blocks.stonebrick.getDefaultState());
                }
                else
                {
                    setBlockSafe(world, pos, Blocks.stonebrick.getDefaultState());
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
                lazilySpawnEntity(world, entity, random, "Sheep", 1.0f / (20 * 20), pos);
            }
        }
    }
}
