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
    public void generateOnBlock(World world, EntityPandorasBox entity, Vec3 effectCenter, Random random, int pass, int x, int y, int z, double range)
    {
        Block block = world.getBlock(x, y, z);

        if (pass == 0)
        {
            if (isBlockAnyOf(block, Blocks.snow, Blocks.snow_layer))
            {
                setBlockSafe(world, x, y, z, Blocks.air);
            }
            else if (isBlockAnyOf(block, Blocks.stone, Blocks.end_stone, Blocks.netherrack, Blocks.soul_sand, Blocks.sand, Blocks.mycelium, Blocks.dirt))
            {
                if (world.getBlock(x, y + 1, z) == Blocks.air)
                {
                    setBlockSafe(world, x, y, z, Blocks.grass);

                    if (!world.isRemote)
                    {
                        if (random.nextInt(2 * 2) == 0)
                        {
                            setBlockAndMetaSafe(world, x, y + 1, z, Blocks.tallgrass, random.nextInt(1) + 1);
                        }
                        else if (random.nextInt(5 * 5) == 0)
                        {
                            setBlockSafe(world, x, y + 1, z, (world.rand.nextBoolean() ? Blocks.red_flower : Blocks.yellow_flower));
                        }
                    }
                }
                else
                {
                    setBlockSafe(world, x, y, z, Blocks.dirt);
                }
            }
            else if (isBlockAnyOf(block, Blocks.fire, Blocks.brown_mushroom, Blocks.red_mushroom, Blocks.brown_mushroom_block, Blocks.red_mushroom_block))
            {
                setBlockSafe(world, x, y, z, Blocks.air);
            }

            if (isBlockAnyOf(block, Blocks.flowing_lava, Blocks.lava))
            {
                setBlockSafe(world, x, y, z, Blocks.water);
            }
            if (isBlockAnyOf(block, Blocks.obsidian, Blocks.ice))
            {
                setBlockSafe(world, x, y, z, Blocks.water);
            }
        }
        else
        {
            if (canSpawnEntity(world, block, x, y, z))
            {
                lazilySpawnEntity(world, entity, random, "Pig", 1.0f / (30 * 30), x, y, z);
                lazilySpawnEntity(world, entity, random, "Sheep", 1.0f / (30 * 30), x, y, z);
                lazilySpawnEntity(world, entity, random, "Cow", 1.0f / (30 * 30), x, y, z);
                lazilySpawnEntity(world, entity, random, "Chicken", 1.0f / (30 * 30), x, y, z);
            }
        }
    }
}
