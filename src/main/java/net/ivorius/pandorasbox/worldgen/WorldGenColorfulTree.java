/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package net.ivorius.pandorasbox.worldgen;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class WorldGenColorfulTree extends WorldGenAbstractTree
{
    public final Block block;
    public final int[] metas;
    public final int addition;
    public final Block soil;

    public WorldGenColorfulTree(boolean p_i45449_1_, int addition, Block block, int[] metas, Block soil)
    {
        super(p_i45449_1_);

        this.block = block;
        this.metas = metas;
        this.addition = addition;
        this.soil = soil;
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        int l = par2Random.nextInt(addition) + 5;
        int meta = metas[par2Random.nextInt(metas.length)];

        boolean flag = true;

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
                            Block block = par1World.getBlock(j1, i1, k1);

                            if (!this.isReplaceable(par1World, j1, i1, k1))
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
                Block block2 = par1World.getBlock(par3, par4 - 1, par5);

                boolean isSoil = block2 == soil;
                if (isSoil && par4 < 256 - l - 1)
                {
                    block2.onPlantGrow(par1World, par3, par4 - 1, par5, par3, par4, par5);
                    int k2;

                    for (k2 = par4 - 3 + l; k2 <= par4 + l; ++k2)
                    {
                        j1 = k2 - (par4 + l);
                        k1 = 1 - j1 / 2;

                        for (int l2 = par3 - k1; l2 <= par3 + k1; ++l2)
                        {
                            int l1 = l2 - par3;

                            for (int i2 = par5 - k1; i2 <= par5 + k1; ++i2)
                            {
                                int j2 = i2 - par5;

                                if (Math.abs(l1) != k1 || Math.abs(j2) != k1 || par2Random.nextInt(2) != 0 && j1 != 0)
                                {
                                    Block block1 = par1World.getBlock(l2, k2, i2);

                                    if (block1.isAir(par1World, l2, k2, i2) || block1.isLeaves(par1World, l2, k2, i2))
                                    {
                                        this.setBlockAndNotifyAdequately(par1World, l2, k2, i2, block, metas[par2Random.nextInt(metas.length)]);
                                    }
                                }
                            }
                        }
                    }

                    for (k2 = 0; k2 < l; ++k2)
                    {
                        Block block3 = par1World.getBlock(par3, par4 + k2, par5);

                        if (block3.isAir(par1World, par3, par4 + k2, par5) || block3.isLeaves(par1World, par3, par4 + k2, par5))
                        {
                            this.setBlockAndNotifyAdequately(par1World, par3, par4 + k2, par5, block, metas[0]);
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