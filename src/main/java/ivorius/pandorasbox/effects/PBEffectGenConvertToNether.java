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
    public void generateOnBlock(World world, EntityPandorasBox entity, Vec3 effectCenter, Random random, int pass, BlockPos pos, double range)
    {
        Block block = world.getBlockState(pos).getBlock();

        if (pass == 0)
        {
            if (isBlockAnyOf(block, Blocks.cobblestone, Blocks.flowing_water, Blocks.ice, Blocks.water, Blocks.obsidian))
            {
                setBlockSafe(world, pos, Blocks.lava.getDefaultState());
            }
            else if (isBlockAnyOf(block, Blocks.snow, Blocks.snow_layer, Blocks.red_flower, Blocks.yellow_flower, Blocks.vine, Blocks.brown_mushroom_block, Blocks.red_mushroom_block, Blocks.tallgrass, Blocks.red_mushroom, Blocks.brown_mushroom, Blocks.log, Blocks.log2, Blocks.leaves, Blocks.leaves2))
            {
                setBlockToAirSafe(world, pos);
            }
            else if (isBlockAnyOf(block, Blocks.sand))
            {
                setBlockSafe(world, pos, Blocks.soul_sand.getDefaultState());
            }
            else if (isBlockAnyOf(block, Blocks.grass, Blocks.dirt, Blocks.stone, Blocks.end_stone, Blocks.mycelium, Blocks.hardened_clay, Blocks.stained_hardened_clay))
            {
                setBlockSafe(world, pos, Blocks.netherrack.getDefaultState());
            }
            else if (world.getBlockState(pos).getBlock() == Blocks.air && Blocks.fire.canPlaceBlockAt(world, pos))
            {
                if (!world.isRemote && random.nextInt(15) == 0)
                {
                    if (world.rand.nextFloat() < 0.9f)
                    {
                        setBlockSafe(world, pos, Blocks.fire.getDefaultState());
                    }
                    else
                    {
                        setBlockSafe(world, pos, Blocks.glowstone.getDefaultState());
                    }
                }
            }
        }
        else
        {
            if (canSpawnEntity(world, block, pos))
            {
                lazilySpawnEntity(world, entity, random, "PigZombie", 1.0f / (15 * 15), pos);
                lazilySpawnEntity(world, entity, random, "LavaSlime", 1.0f / (15 * 15), pos);
            }

            if (canSpawnFlyingEntity(world, block, pos))
            {
                lazilySpawnEntity(world, entity, random, "Ghast", 1.0f / (50 * 50 * 50), pos);
                lazilySpawnEntity(world, entity, random, "Blaze", 1.0f / (50 * 50 * 50), pos);
            }
        }
    }
}
