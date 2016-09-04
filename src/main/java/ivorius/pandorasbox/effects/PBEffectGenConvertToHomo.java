/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import ivorius.pandorasbox.worldgen.WorldGenRainbow;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
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
    public void generateOnBlock(World world, EntityPandorasBox entity, Vec3d effectCenter, Random random, int pass, BlockPos pos, double range)
    {
        IBlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();

        if (pass == 0)
        {
            if (isBlockAnyOf(block, Blocks.SNOW, Blocks.SNOW_LAYER))
            {
                setBlockToAirSafe(world, pos);
            }
            else if (isBlockAnyOf(block, Blocks.STONE, Blocks.END_STONE, Blocks.NETHERRACK, Blocks.SOUL_SAND, Blocks.SAND, Blocks.MYCELIUM))
            {
                if (world.getBlockState(pos.up()) == Blocks.AIR)
                {
                    setBlockSafe(world, pos, Blocks.GRASS.getDefaultState());
                }
                else
                {
                    setBlockSafe(world, pos, Blocks.DIRT.getDefaultState());
                }
            }
            else if (isBlockAnyOf(block, Blocks.FIRE, Blocks.BROWN_MUSHROOM, Blocks.RED_MUSHROOM, Blocks.BROWN_MUSHROOM_BLOCK, Blocks.RED_MUSHROOM_BLOCK))
            {
                setBlockToAirSafe(world, pos);
            }

            if (isBlockAnyOf(block, Blocks.FLOWING_LAVA, Blocks.LAVA))
            {
                setBlockSafe(world, pos, Blocks.WATER.getDefaultState());
            }
            if (isBlockAnyOf(block, Blocks.OBSIDIAN, Blocks.ICE))
            {
                setBlockSafe(world, pos, Blocks.WATER.getDefaultState());
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

                    treeGen = new WorldGenRainbow(true, Blocks.WOOL, 20, Blocks.GRASS);

                    treeGen.generate(world, world.rand, pos);
                }
                else if (blockState.getMaterial() == Material.AIR && Blocks.SNOW_LAYER.canPlaceBlockAt(world, pos))
                {
                    if (random.nextInt(3 * 3) == 0)
                    {
                        Block flowerBlock;
                        int meta;

                        if (random.nextInt(10) == 0)
                        {
                            flowerBlock = Blocks.YELLOW_FLOWER;
                            meta = 0;
                        }
                        else
                        {
                            flowerBlock = Blocks.RED_FLOWER;
                            meta = random.nextInt(9);
                        }

                        setBlockSafe(world, pos, flowerBlock.getStateFromMeta(meta));
                    }
                }
            }
        }
        else
        {
            if (canSpawnEntity(world, blockState, pos))
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
