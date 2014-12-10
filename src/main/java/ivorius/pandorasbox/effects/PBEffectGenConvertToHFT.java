/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import ivorius.pandorasbox.worldgen.WorldGenColorfulTree;
import ivorius.pandorasbox.worldgen.WorldGenLollipop;
import ivorius.pandorasbox.worldgen.WorldGenRainbow;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBEffectGenConvertToHFT extends PBEffectGenerate
{
    public int[] groundMetas;

    public PBEffectGenConvertToHFT()
    {
    }

    public PBEffectGenConvertToHFT(int time, double range, int unifiedSeed, int[] groundMetas)
    {
        super(time, range, 2, unifiedSeed);

        this.groundMetas = groundMetas;
    }

    @Override
    public void generateOnBlock(World world, EntityPandorasBox entity, Vec3 effectCenter, Random random, int pass, int x, int y, int z, double range)
    {
        Block block = world.getBlock(x, y, z);
        Block placeBlock = Blocks.stained_hardened_clay;

        if (pass == 0)
        {
            if (isBlockAnyOf(block, Blocks.log, Blocks.log2, Blocks.leaves, Blocks.leaves2, Blocks.lava, Blocks.flowing_lava))
            {
                setBlockSafe(world, x, y, z, Blocks.air);
            }
            else if (block == Blocks.wool || block == Blocks.stained_hardened_clay)
            {

            }
            else if (block.isNormalCube(world, x, y, z))
            {
                if (!world.isRemote)
                {
                    setBlockAndMetaSafe(world, x, y, z, placeBlock, groundMetas[world.rand.nextInt(groundMetas.length)]);
                }
            }
        }
        else if (pass == 1)
        {
            if (!world.isRemote)
            {
                if (world.rand.nextInt(10 * 10) == 0)
                {
                    int[] lolliColors = new int[world.rand.nextInt(4) + 1];
                    for (int i = 0; i < lolliColors.length; i++)
                    {
                        lolliColors[i] = world.rand.nextInt(16);
                    }

                    WorldGenerator treeGen;

                    if (world.rand.nextBoolean())
                    {
                        treeGen = new WorldGenLollipop(true, 20, Blocks.wool, lolliColors, placeBlock);
                    }
                    else if (world.rand.nextInt(6) > 0)
                    {
                        treeGen = new WorldGenColorfulTree(true, 20, Blocks.wool, lolliColors, placeBlock);
                    }
                    else
                    {
                        treeGen = new WorldGenRainbow(true, Blocks.wool, 20, placeBlock);
                    }

                    treeGen.generate(world, world.rand, x, y, z);
                }
                else if (world.rand.nextInt(5 * 5) == 0)
                {
                    if (world.getBlock(x, y - 1, z) == placeBlock && world.getBlock(x, y, z) == Blocks.air)
                    {
                        if (world.rand.nextBoolean())
                        {
                            setBlockSafe(world, x, y, z, Blocks.cake);
                        }
                        else
                        {
                            EntityItem entityItem = new EntityItem(world, x + 0.5f, y + 0.5f, z + 0.5f, new ItemStack(Items.cookie));
                            entityItem.delayBeforeCanPickup = 20;
                            world.spawnEntityInWorld(entityItem);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setIntArray("groundMetas", groundMetas);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        groundMetas = compound.getIntArray("groundMetas");
    }
}
