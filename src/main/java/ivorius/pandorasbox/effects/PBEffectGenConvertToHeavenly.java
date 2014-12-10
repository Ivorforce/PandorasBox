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
    public void generateOnBlock(World world, EntityPandorasBox entity, Vec3 effectCenter, Random random, int pass, int x, int y, int z, double range)
    {
        Block block = world.getBlock(x, y, z);

        if (pass == 0)
        {
            if (isBlockAnyOf(block, Blocks.snow, Blocks.snow_layer, Blocks.fire, Blocks.red_flower, Blocks.yellow_flower, Blocks.tallgrass))
            {
                setBlockSafe(world, x, y, z, Blocks.air);
            }
            else if (isBlockAnyOf(block, Blocks.stone, Blocks.end_stone, Blocks.netherrack, Blocks.soul_sand, Blocks.sand, Blocks.dirt, Blocks.grass))
            {
                if (world.getBlock(x, y + 1, z) == Blocks.air)
                {
                    if (!world.isRemote && world.rand.nextInt(6 * 6) == 0)
                    {
                        setBlockSafe(world, x, y, z, Blocks.dirt);
                        setBlockSafe(world, x, y + 1, z, Blocks.log);
                        setBlockSafe(world, x, y + 2, z, Blocks.leaves);
                    }
                    else if (!world.isRemote && world.rand.nextInt(6 * 6) == 0)
                    {
                        int pHeight = random.nextInt(5) + 3;
                        for (int yp = 0; yp < pHeight; yp++)
                            setBlockSafe(world, x, y + yp, z, Blocks.quartz_block);
                    }
                    else if (!world.isRemote && world.rand.nextInt(2 * 2) == 0)
                    {
                        setBlockSafe(world, x, y, z, Blocks.glass);
                    }
                    else if (!world.isRemote && world.rand.nextInt(8 * 8) == 0)
                    {
                        setBlockSafe(world, x, y, z, Blocks.glass);
                        setBlockSafe(world, x, y - 1, z, Blocks.redstone_lamp);
                        setBlockSafe(world, x, y - 2, z, Blocks.redstone_block);
                    }
                    else
                        setBlockSafe(world, x, y, z, Blocks.stonebrick);
                }
                else
                {
                    setBlockSafe(world, x, y, z, Blocks.stonebrick);
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
                lazilySpawnEntity(world, entity, random, "Sheep", 1.0f / (20 * 20), x, y, z);
            }
        }
    }
}
