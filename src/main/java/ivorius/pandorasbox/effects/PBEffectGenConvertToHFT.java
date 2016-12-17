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
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
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
    public void generateOnBlock(World world, EntityPandorasBox entity, Vec3d effectCenter, Random random, int pass, BlockPos pos, double range)
    {
        IBlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();
        Block placeBlock = Blocks.STAINED_HARDENED_CLAY;

        if (pass == 0)
        {
            if (isBlockAnyOf(block, Blocks.LOG, Blocks.LOG2, Blocks.LEAVES, Blocks.LEAVES2, Blocks.LAVA, Blocks.FLOWING_LAVA))
            {
                setBlockToAirSafe(world, pos);
            }
            else if (block == Blocks.WOOL || block == Blocks.STAINED_HARDENED_CLAY)
            {

            }
            else if (block.isNormalCube(blockState, world, pos))
            {
                if (!world.isRemote)
                {
                    setBlockSafe(world, pos, placeBlock.getStateFromMeta(groundMetas[world.rand.nextInt(groundMetas.length)]));
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
                        treeGen = new WorldGenLollipop(true, 20, Blocks.WOOL, lolliColors, placeBlock);
                    }
                    else if (world.rand.nextInt(6) > 0)
                    {
                        treeGen = new WorldGenColorfulTree(true, 20, Blocks.WOOL, lolliColors, placeBlock);
                    }
                    else
                    {
                        treeGen = new WorldGenRainbow(true, Blocks.WOOL, 20, placeBlock);
                    }

                    treeGen.generate(world, world.rand, pos);
                }
                else if (world.rand.nextInt(5 * 5) == 0)
                {
                    if (world.getBlockState(pos.down()) == placeBlock && world.getBlockState(pos).getBlock() == Blocks.AIR)
                    {
                        if (world.rand.nextBoolean())
                        {
                            setBlockSafe(world, pos, Blocks.CAKE.getDefaultState());
                        }
                        else
                        {
                            EntityItem entityItem = new EntityItem(world, pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, new ItemStack(Items.COOKIE));
                            entityItem.setPickupDelay(20);
                            world.spawnEntity(entityItem);
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
