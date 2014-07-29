/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import ivorius.pandorasbox.worldgen.WorldGenRainbow;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBEffectGenConvertToHomo extends PBEffectGenerate
{
    public PBEffectGenConvertToHomo()
    {
    }

    public PBEffectGenConvertToHomo(int time, double range, int unifiedSeed)
    {
        super(time, range, 3, unifiedSeed);
    }

    @Override
    public void generateOnBlock(World world, EntityPandorasBox entity, Random random, int pass, int x, int y, int z, double range)
    {
        Block block = world.getBlock(x, y, z);

        if (pass == 0)
        {
            if (isBlockAnyOf(block, Blocks.snow, Blocks.snow_layer))
            {
                setBlockSafe(world, x, y, z, Blocks.air);
            }
            else if (isBlockAnyOf(block, Blocks.stone, Blocks.end_stone, Blocks.netherrack, Blocks.soul_sand, Blocks.sand, Blocks.mycelium))
            {
                if (world.getBlock(x, y + 1, z) == Blocks.air)
                {
                    setBlockSafe(world, x, y, z, Blocks.grass);
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
        else if (pass == 1)
        {
            if (!world.isRemote)
            {
                if (random.nextInt(15 * 15) == 0)
                {
                    int[] lolliColors = new int[world.rand.nextInt(4) + 1];
                    for (int i = 0; i < lolliColors.length; i++)
                    {
                        lolliColors[i] = world.rand.nextInt(16);
                    }

                    WorldGenerator treeGen;

                    treeGen = new WorldGenRainbow(true, Blocks.wool, 20, Blocks.grass);

                    treeGen.generate(world, world.rand, x, y, z);
                }
                else if (block.getMaterial() == Material.air && Blocks.snow_layer.canPlaceBlockAt(world, x, y, z))
                {
                    if (random.nextInt(3 * 3) == 0)
                    {
                        Block flowerBlock;
                        int meta;

                        if (random.nextInt(10) == 0)
                        {
                            flowerBlock = Blocks.yellow_flower;
                            meta = 0;
                        }
                        else
                        {
                            flowerBlock = Blocks.red_flower;
                            meta = random.nextInt(9);
                        }

                        setBlockAndMetaSafe(world, x, y, z, flowerBlock, meta);
                    }
                }
            }
        }
        else
        {
            if (canSpawnEntity(world, block, x, y, z))
            {
                EntitySheep sheep = (EntitySheep) lazilySpawnEntity(world, entity, random, "Sheep", 1.0f / (10 * 10), x, y, z);
                if (sheep != null)
                {
                    sheep.setFleeceColor(random.nextInt(16));
                }
            }
        }
    }
}
