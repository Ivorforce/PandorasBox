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
    public void generateOnBlock(World world, EntityPandorasBox entity, Vec3 effectCenter, Random random, int pass, BlockPos pos, double range)
    {
        Block block = world.getBlockState(pos).getBlock();

        if (pass == 0)
        {
            if (isBlockAnyOf(block, Blocks.snow, Blocks.snow_layer))
            {
                setBlockToAirSafe(world, pos);
            }
            else if (isBlockAnyOf(block, Blocks.stone, Blocks.end_stone, Blocks.netherrack, Blocks.soul_sand, Blocks.sand, Blocks.mycelium, Blocks.dirt))
            {
                BlockPos posUp = pos.up();
                if (world.getBlockState(posUp).getBlock() == Blocks.air)
                {
                    setBlockSafe(world, pos, Blocks.grass.getDefaultState());

                    if (!world.isRemote)
                    {
                        if (random.nextInt(2 * 2) == 0)
                        {
                            setBlockSafe(world, posUp, Blocks.tallgrass.getStateFromMeta(random.nextInt(1) + 1));
                        }
                        else if (random.nextInt(5 * 5) == 0)
                        {
                            setBlockSafe(world, posUp, (world.rand.nextBoolean() ? Blocks.red_flower.getDefaultState() : Blocks.yellow_flower.getDefaultState()));
                        }
                    }
                }
                else
                {
                    setBlockSafe(world, pos, Blocks.dirt.getDefaultState());
                }
            }
            else if (isBlockAnyOf(block, Blocks.fire, Blocks.brown_mushroom, Blocks.red_mushroom, Blocks.brown_mushroom_block, Blocks.red_mushroom_block))
            {
                setBlockSafe(world, pos, Blocks.air.getDefaultState());
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
                lazilySpawnEntity(world, entity, random, "Pig", 1.0f / (30 * 30), pos);
                lazilySpawnEntity(world, entity, random, "Sheep", 1.0f / (30 * 30), pos);
                lazilySpawnEntity(world, entity, random, "Cow", 1.0f / (30 * 30), pos);
                lazilySpawnEntity(world, entity, random, "Chicken", 1.0f / (30 * 30), pos);
            }
        }
    }
}
