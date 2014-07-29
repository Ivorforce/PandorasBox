/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
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
    public void generateOnBlock(World world, EntityPandorasBox entity, Random random, int pass, int x, int y, int z, double range)
    {
        Block block = world.getBlock(x, y, z);

        if (pass == 0)
        {
            if (isBlockAnyOf(block, Blocks.snow, Blocks.snow_layer, Blocks.fire, Blocks.red_flower, Blocks.yellow_flower, Blocks.tallgrass, Blocks.log, Blocks.log2, Blocks.leaves, Blocks.leaves2))
            {
                setBlockSafe(world, x, y, z, Blocks.air);
            }
            else if (isBlockAnyOf(block, Blocks.stone, Blocks.end_stone, Blocks.netherrack, Blocks.soul_sand, Blocks.sand, Blocks.dirt, Blocks.grass))
            {
                if (world.getBlock(x, y + 1, z) == Blocks.air)
                {
                    setBlockSafe(world, x, y, z, Blocks.mycelium);

                    if (!world.isRemote)
                    {
                        if (world.rand.nextInt(6 * 6) == 0)
                        {
                            setBlockSafe(world, x, y + 1, z, (world.rand.nextBoolean() ? Blocks.brown_mushroom : Blocks.red_mushroom));
                        }
                        else if (world.rand.nextInt(8 * 8) == 0)
                        {
                            WorldGenBigMushroom mushroomGen = new WorldGenBigMushroom(random.nextInt(2));
                            mushroomGen.generate(world, world.rand, x, y + 1, z);
                        }
                    }
                }
                else
                {
                    setBlockSafe(world, x, y, z, Blocks.dirt);
                }
            }
            else if (isBlockAnyOf(block, Blocks.obsidian, Blocks.lava, Blocks.ice))
            {
                setBlockSafe(world, x, y, z, Blocks.flowing_water);
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
                lazilySpawnEntity(world, entity, random, "MushroomCow", 1.0f / (20 * 20), x, y, z);
            }
        }
    }
}
