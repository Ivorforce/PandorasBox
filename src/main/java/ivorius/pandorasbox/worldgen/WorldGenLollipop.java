/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.worldgen;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class WorldGenLollipop extends WorldGenAbstractTree
{
    public final Block block;
    public final int[] metas;
    public final int addition;
    public final Block soil;

    public WorldGenLollipop(boolean p_i45449_1_, int addition, Block block, int[] metas, Block soil)
    {
        super(p_i45449_1_);

        this.block = block;
        this.metas = metas;
        this.addition = addition;
        this.soil = soil;
    }

    public boolean generate(World world, Random par2Random, BlockPos genPos)
    {
        int l = par2Random.nextInt(addition) + 5;
        int meta = metas[par2Random.nextInt(metas.length)];

        boolean flag = true;

        int par3 = genPos.getX();
        int par4 = genPos.getY();
        int par5 = genPos.getZ();

        if (par4 >= 1 && par4 + l + 1 <= 256)
        {
            int j1;
            int k1;

            for (int i1 = par4; i1 <= par4 + 1 + l; ++i1)
            {
                byte b0 = 1;

                if (i1 == par4)
                {
                    b0 = 0;
                }

                if (i1 >= par4 + 1 + l - 2)
                {
                    b0 = 2;
                }

                for (j1 = par3 - b0; j1 <= par3 + b0 && flag; ++j1)
                {
                    for (k1 = par5 - b0; k1 <= par5 + b0 && flag; ++k1)
                    {
                        if (i1 >= 0 && i1 < 256)
                        {
                            BlockPos pos = new BlockPos(j1, i1, k1);
                            Block block = world.getBlockState(pos).getBlock();

                            if (!this.isReplaceable(world, pos))
                            {
                                flag = false;
                            }
                        }
                        else
                        {
                            flag = false;
                        }
                    }
                }
            }

            if (!flag)
            {
                return false;
            }
            else
            {
                BlockPos pos = new BlockPos(par3, par4 - 1, par5);
                IBlockState block2State = world.getBlockState(pos);
                Block block2 = block2State.getBlock();
                boolean rotated = par2Random.nextBoolean();

                boolean isSoil = block2 == soil;
                if (isSoil && par4 < 256 - l - 1)
                {
                    block2.onPlantGrow(block2State, world, pos, new BlockPos(par3, par4, par5));
                    int k2;

                    for (int shift = -1; shift <= 1; shift++)
                    {
                        for (int s = -l / 2; s <= l / 2; s++)
                        {
                            for (int y = -l / 2; y <= l / 2; y++)
                            {
                                if (s * s + y * y <= (l * l / 4 - shift * shift * 4))
                                {
                                    int x = (!rotated ? s : shift) + par3;
                                    int z = (rotated ? s : shift) + par5;
                                    int rY = y + par4 + l;

                                    BlockPos pos1 = new BlockPos(x, rY, z);
                                    IBlockState block1State = world.getBlockState(pos1);
                                    Block block1 = block1State.getBlock();

                                    if (block1.isAir(block1State, world, pos1) || block1.isLeaves(block1State, world, pos1))
                                    {
                                        this.setBlockAndNotifyAdequately(world, pos1, block.getStateFromMeta(metas[par2Random.nextInt(metas.length)]));
                                    }
                                }
                            }
                        }
                    }

                    for (k2 = 0; k2 < l; ++k2)
                    {
                        BlockPos pos1 = new BlockPos(par3, par4 + k2, par5);
                        IBlockState block3State = world.getBlockState(pos1);
                        Block block3 = block3State.getBlock();

                        if (block3.isAir(block3State, world, pos1) || block3.isLeaves(block3State, world, pos1))
                        {
                            this.setBlockAndNotifyAdequately(world, pos1, block.getStateFromMeta(metas[0]));
                        }
                    }

                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
        else
        {
            return false;
        }
    }
}