/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
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
    public void generateOnBlock(World world, EntityPandorasBox entity, Vec3 effectCenter, Random random, int pass, int x, int y, int z, double range)
    {
        Block block = world.getBlock(x, y, z);

        if (pass == 0)
        {
            if (isBlockAnyOf(block, Blocks.cobblestone, Blocks.flowing_water, Blocks.ice, Blocks.water, Blocks.obsidian))
            {
                setBlockSafe(world, x, y, z, Blocks.lava);
            }
            else if (isBlockAnyOf(block, Blocks.snow, Blocks.snow_layer, Blocks.red_flower, Blocks.yellow_flower, Blocks.vine, Blocks.brown_mushroom_block, Blocks.red_mushroom_block, Blocks.tallgrass, Blocks.red_mushroom, Blocks.brown_mushroom, Blocks.log, Blocks.log2, Blocks.leaves, Blocks.leaves2))
            {
                setBlockSafe(world, x, y, z, Blocks.air);
            }
            else if (isBlockAnyOf(block, Blocks.sand))
            {
                setBlockSafe(world, x, y, z, Blocks.soul_sand);
            }
            else if (isBlockAnyOf(block, Blocks.grass, Blocks.dirt, Blocks.stone, Blocks.end_stone, Blocks.mycelium, Blocks.hardened_clay, Blocks.stained_hardened_clay))
            {
                setBlockSafe(world, x, y, z, Blocks.netherrack);
            }
            else if (world.getBlock(x, y, z) == Blocks.air && Blocks.fire.canPlaceBlockAt(world, x, y, z))
            {
                if (!world.isRemote && random.nextInt(15) == 0)
                {
                    if (world.rand.nextFloat() < 0.9f)
                    {
                        setBlockSafe(world, x, y, z, Blocks.fire);
                    }
                    else
                    {
                        setBlockSafe(world, x, y, z, Blocks.glowstone);
                    }
                }
            }
        }
        else
        {
            if (canSpawnEntity(world, block, x, y, z))
            {
                lazilySpawnEntity(world, entity, random, "PigZombie", 1.0f / (15 * 15), x, y, z);
                lazilySpawnEntity(world, entity, random, "LavaSlime", 1.0f / (15 * 15), x, y, z);
            }

            if (canSpawnFlyingEntity(world, block, x, y, z))
            {
                lazilySpawnEntity(world, entity, random, "Ghast", 1.0f / (50 * 50 * 50), x, y, z);
                lazilySpawnEntity(world, entity, random, "Blaze", 1.0f / (50 * 50 * 50), x, y, z);
            }
        }
    }
}
