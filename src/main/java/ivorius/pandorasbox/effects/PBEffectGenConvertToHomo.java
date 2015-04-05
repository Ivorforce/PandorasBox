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
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
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
    public void generateOnBlock(World world, EntityPandorasBox entity, Vec3 effectCenter, Random random, int pass, BlockPos pos, double range)
    {
        Block block = world.getBlockState(pos).getBlock();

        if (pass == 0)
        {
            if (isBlockAnyOf(block, Blocks.snow, Blocks.snow_layer))
            {
                setBlockToAirSafe(world, pos);
            }
            else if (isBlockAnyOf(block, Blocks.stone, Blocks.end_stone, Blocks.netherrack, Blocks.soul_sand, Blocks.sand, Blocks.mycelium))
            {
                if (world.getBlockState(pos.up()) == Blocks.air)
                {
                    setBlockSafe(world, pos, Blocks.grass.getDefaultState());
                }
                else
                {
                    setBlockSafe(world, pos, Blocks.dirt.getDefaultState());
                }
            }
            else if (isBlockAnyOf(block, Blocks.fire, Blocks.brown_mushroom, Blocks.red_mushroom, Blocks.brown_mushroom_block, Blocks.red_mushroom_block))
            {
                setBlockToAirSafe(world, pos);
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

                    treeGen.generate(world, world.rand, pos);
                }
                else if (block.getMaterial() == Material.air && Blocks.snow_layer.canPlaceBlockAt(world, pos))
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

                        setBlockSafe(world, pos, flowerBlock.getStateFromMeta(meta));
                    }
                }
            }
        }
        else
        {
            if (canSpawnEntity(world, block, pos))
            {
                EntitySheep sheep = (EntitySheep) lazilySpawnEntity(world, entity, random, "Sheep", 1.0f / (10 * 10), pos);
                if (sheep != null)
                {
                    sheep.setFleeceColor(EnumDyeColor.byMetadata(random.nextInt(16)));
                }
            }
        }
    }
}
