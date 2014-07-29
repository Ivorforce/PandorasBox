/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package net.ivorius.pandorasbox.worldgen;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenHugeTrees;

import java.util.Random;

/**
 * Created by lukas on 14.02.14.
 */
public class WorldGenMegaJungleCustom extends WorldGenHugeTrees
{
    public final Block trunkBlock;
    public final Block leavesBlock;

    public WorldGenMegaJungleCustom(boolean p_i45456_1_, int p_i45456_2_, int p_i45456_3_, int p_i45456_4_, int p_i45456_5_, Block trunkBlock, Block leavesBlock)
    {
        super(p_i45456_1_, p_i45456_2_, p_i45456_3_, p_i45456_4_, p_i45456_5_);

        this.trunkBlock = trunkBlock;
        this.leavesBlock = leavesBlock;
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        int l = this.func_150533_a(par2Random);

        if (!this.func_150537_a(par1World, par2Random, par3, par4, par5, l))
        {
            return false;
        }
        else
        {
            this.func_150543_c(par1World, par3, par5, par4 + l, 2, par2Random);

            for (int i1 = par4 + l - 2 - par2Random.nextInt(4); i1 > par4 + l / 2; i1 -= 2 + par2Random.nextInt(4))
            {
                float f = par2Random.nextFloat() * (float) Math.PI * 2.0F;
                int j1 = par3 + (int) (0.5F + MathHelper.cos(f) * 4.0F);
                int k1 = par5 + (int) (0.5F + MathHelper.sin(f) * 4.0F);
                int l1;

                for (l1 = 0; l1 < 5; ++l1)
                {
                    j1 = par3 + (int) (1.5F + MathHelper.cos(f) * (float) l1);
                    k1 = par5 + (int) (1.5F + MathHelper.sin(f) * (float) l1);
                    this.setBlockAndNotifyAdequately(par1World, j1, i1 - 3 + l1 / 2, k1, trunkBlock, this.woodMetadata);
                }

                l1 = 1 + par2Random.nextInt(2);
                int i2 = i1;

                for (int j2 = i1 - l1; j2 <= i2; ++j2)
                {
                    int k2 = j2 - i2;
                    this.func_150534_b(par1World, j1, j2, k1, 1 - k2, par2Random);
                }
            }

            for (int l2 = 0; l2 < l; ++l2)
            {
                Block block = par1World.getBlock(par3, par4 + l2, par5);

                if (block.isAir(par1World, par3, par4 + l2, par5) || block.isLeaves(par1World, par3, par4 + l2, par5))
                {
                    this.setBlockAndNotifyAdequately(par1World, par3, par4 + l2, par5, trunkBlock, this.woodMetadata);

                    if (l2 > 0)
                    {
                        if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3 - 1, par4 + l2, par5))
                        {
                            this.setBlockAndNotifyAdequately(par1World, par3 - 1, par4 + l2, par5, Blocks.vine, 8);
                        }

                        if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3, par4 + l2, par5 - 1))
                        {
                            this.setBlockAndNotifyAdequately(par1World, par3, par4 + l2, par5 - 1, Blocks.vine, 1);
                        }
                    }
                }

                if (l2 < l - 1)
                {
                    block = par1World.getBlock(par3 + 1, par4 + l2, par5);

                    if (block.isAir(par1World, par3 + 1, par4 + l2, par5) || block.isLeaves(par1World, par3 + 1, par4 + l2, par5))
                    {
                        this.setBlockAndNotifyAdequately(par1World, par3 + 1, par4 + l2, par5, trunkBlock, this.woodMetadata);

                        if (l2 > 0)
                        {
                            if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3 + 2, par4 + l2, par5))
                            {
                                this.setBlockAndNotifyAdequately(par1World, par3 + 2, par4 + l2, par5, Blocks.vine, 2);
                            }

                            if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3 + 1, par4 + l2, par5 - 1))
                            {
                                this.setBlockAndNotifyAdequately(par1World, par3 + 1, par4 + l2, par5 - 1, Blocks.vine, 1);
                            }
                        }
                    }

                    block = par1World.getBlock(par3 + 1, par4 + l2, par5 + 1);

                    if (block.isAir(par1World, par3 + 1, par4 + l2, par5 + 1) || block.isLeaves(par1World, par3 + 1, par4 + l2, par5 + 1))
                    {
                        this.setBlockAndNotifyAdequately(par1World, par3 + 1, par4 + l2, par5 + 1, trunkBlock, this.woodMetadata);

                        if (l2 > 0)
                        {
                            if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3 + 2, par4 + l2, par5 + 1))
                            {
                                this.setBlockAndNotifyAdequately(par1World, par3 + 2, par4 + l2, par5 + 1, Blocks.vine, 2);
                            }

                            if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3 + 1, par4 + l2, par5 + 2))
                            {
                                this.setBlockAndNotifyAdequately(par1World, par3 + 1, par4 + l2, par5 + 2, Blocks.vine, 4);
                            }
                        }
                    }

                    block = par1World.getBlock(par3, par4 + l2, par5 + 1);

                    if (block.isAir(par1World, par3, par4 + l2, par5 + 1) || block.isLeaves(par1World, par3, par4 + l2, par5 + 1))
                    {
                        this.setBlockAndNotifyAdequately(par1World, par3, par4 + l2, par5 + 1, trunkBlock, this.woodMetadata);

                        if (l2 > 0)
                        {
                            if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3 - 1, par4 + l2, par5 + 1))
                            {
                                this.setBlockAndNotifyAdequately(par1World, par3 - 1, par4 + l2, par5 + 1, Blocks.vine, 8);
                            }

                            if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3, par4 + l2, par5 + 2))
                            {
                                this.setBlockAndNotifyAdequately(par1World, par3, par4 + l2, par5 + 2, Blocks.vine, 4);
                            }
                        }
                    }
                }
            }

            return true;
        }
    }

    private void func_150543_c(World p_150543_1_, int p_150543_2_, int p_150543_3_, int p_150543_4_, int p_150543_5_, Random p_150543_6_)
    {
        byte b0 = 2;

        for (int i1 = p_150543_4_ - b0; i1 <= p_150543_4_; ++i1)
        {
            int j1 = i1 - p_150543_4_;
            this.func_150535_a(p_150543_1_, p_150543_2_, i1, p_150543_3_, p_150543_5_ + 1 - j1, p_150543_6_);
        }
    }

    @Override
    protected void func_150535_a(World p_150535_1_, int p_150535_2_, int p_150535_3_, int p_150535_4_, int p_150535_5_, Random p_150535_6_)
    {
        int i1 = p_150535_5_ * p_150535_5_;

        for (int j1 = p_150535_2_ - p_150535_5_; j1 <= p_150535_2_ + p_150535_5_ + 1; ++j1)
        {
            int k1 = j1 - p_150535_2_;

            for (int l1 = p_150535_4_ - p_150535_5_; l1 <= p_150535_4_ + p_150535_5_ + 1; ++l1)
            {
                int i2 = l1 - p_150535_4_;
                int j2 = k1 - 1;
                int k2 = i2 - 1;

                if (k1 * k1 + i2 * i2 <= i1 || j2 * j2 + k2 * k2 <= i1 || k1 * k1 + k2 * k2 <= i1 || j2 * j2 + i2 * i2 <= i1)
                {
                    Block block = p_150535_1_.getBlock(j1, p_150535_3_, l1);

                    if (block.isAir(p_150535_1_, j1, p_150535_3_, l1) || block.isLeaves(p_150535_1_, j1, p_150535_3_, l1))
                    {
                        this.setBlockAndNotifyAdequately(p_150535_1_, j1, p_150535_3_, l1, leavesBlock, this.leavesMetadata);
                    }
                }
            }
        }
    }

    @Override
    protected void func_150534_b(World p_150534_1_, int p_150534_2_, int p_150534_3_, int p_150534_4_, int p_150534_5_, Random p_150534_6_)
    {
        int i1 = p_150534_5_ * p_150534_5_;

        for (int j1 = p_150534_2_ - p_150534_5_; j1 <= p_150534_2_ + p_150534_5_; ++j1)
        {
            int k1 = j1 - p_150534_2_;

            for (int l1 = p_150534_4_ - p_150534_5_; l1 <= p_150534_4_ + p_150534_5_; ++l1)
            {
                int i2 = l1 - p_150534_4_;

                if (k1 * k1 + i2 * i2 <= i1)
                {
                    Block block = p_150534_1_.getBlock(j1, p_150534_3_, l1);

                    if (block.isAir(p_150534_1_, j1, p_150534_3_, l1) || block.isLeaves(p_150534_1_, j1, p_150534_3_, l1))
                    {
                        this.setBlockAndNotifyAdequately(p_150534_1_, j1, p_150534_3_, l1, leavesBlock, this.leavesMetadata);
                    }
                }
            }
        }
    }
}
