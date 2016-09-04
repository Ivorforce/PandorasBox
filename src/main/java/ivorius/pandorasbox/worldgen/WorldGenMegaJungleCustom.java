/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.worldgen;

import net.minecraft.block.Block;
import net.minecraft.block.BlockVine;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenHugeTrees;

import java.util.Random;

/**
 * Created by lukas on 14.02.14.
 */
public class WorldGenMegaJungleCustom extends WorldGenHugeTrees
{
    public WorldGenMegaJungleCustom(boolean p_i45458_1_, int p_i45458_2_, int p_i45458_3_, IBlockState p_i45458_4_, IBlockState p_i45458_5_)
    {
        super(p_i45458_1_, p_i45458_2_, p_i45458_3_, p_i45458_4_, p_i45458_5_);
    }

    public boolean generate(World worldIn, Random p_180709_2_, BlockPos p_180709_3_)
    {
        int i = this.getHeight(p_180709_2_);

        if (!this.ensureGrowable(worldIn, p_180709_2_, p_180709_3_, i))
        {
            return false;
        }
        else
        {
            this.func_175930_c(worldIn, p_180709_3_.up(i), 2);

            for (int j = p_180709_3_.getY() + i - 2 - p_180709_2_.nextInt(4); j > p_180709_3_.getY() + i / 2; j -= 2 + p_180709_2_.nextInt(4))
            {
                float f = p_180709_2_.nextFloat() * (float) Math.PI * 2.0F;
                int k = p_180709_3_.getX() + (int) (0.5F + MathHelper.cos(f) * 4.0F);
                int l = p_180709_3_.getZ() + (int) (0.5F + MathHelper.sin(f) * 4.0F);
                int i1;

                for (i1 = 0; i1 < 5; ++i1)
                {
                    k = p_180709_3_.getX() + (int) (1.5F + MathHelper.cos(f) * (float) i1);
                    l = p_180709_3_.getZ() + (int) (1.5F + MathHelper.sin(f) * (float) i1);
                    this.setBlockAndNotifyAdequately(worldIn, new BlockPos(k, j - 3 + i1 / 2, l), this.woodMetadata);
                }

                i1 = 1 + p_180709_2_.nextInt(2);
                int j1 = j;

                for (int k1 = j - i1; k1 <= j1; ++k1)
                {
                    int l1 = k1 - j1;
                    this.func_175928_b(worldIn, new BlockPos(k, k1, l), 1 - l1);
                }
            }

            for (int i2 = 0; i2 < i; ++i2)
            {
                BlockPos blockpos1 = p_180709_3_.up(i2);

                if (this.isAirLeaves(worldIn, blockpos1))
                {
                    this.setBlockAndNotifyAdequately(worldIn, blockpos1, this.woodMetadata);

                    if (i2 > 0)
                    {
                        this.func_175932_b(worldIn, p_180709_2_, blockpos1.west(), Blocks.VINE.getDefaultState().withProperty(BlockVine.EAST, true));
                        this.func_175932_b(worldIn, p_180709_2_, blockpos1.north(), Blocks.VINE.getDefaultState().withProperty(BlockVine.SOUTH, true));
                    }
                }

                if (i2 < i - 1)
                {
                    BlockPos blockpos2 = blockpos1.east();

                    if (this.isAirLeaves(worldIn, blockpos2))
                    {
                        this.setBlockAndNotifyAdequately(worldIn, blockpos2, this.woodMetadata);

                        if (i2 > 0)
                        {
                            this.func_175932_b(worldIn, p_180709_2_, blockpos2.east(), Blocks.VINE.getDefaultState().withProperty(BlockVine.WEST, true));
                            this.func_175932_b(worldIn, p_180709_2_, blockpos2.north(), Blocks.VINE.getDefaultState().withProperty(BlockVine.SOUTH, true));
                        }
                    }

                    BlockPos blockpos3 = blockpos1.south().east();

                    if (this.isAirLeaves(worldIn, blockpos3))
                    {
                        this.setBlockAndNotifyAdequately(worldIn, blockpos3, this.woodMetadata);

                        if (i2 > 0)
                        {
                            this.func_175932_b(worldIn, p_180709_2_, blockpos3.east(), Blocks.VINE.getDefaultState().withProperty(BlockVine.WEST, true));
                            this.func_175932_b(worldIn, p_180709_2_, blockpos3.south(), Blocks.VINE.getDefaultState().withProperty(BlockVine.NORTH, true));
                        }
                    }

                    BlockPos blockpos4 = blockpos1.south();

                    if (this.isAirLeaves(worldIn, blockpos4))
                    {
                        this.setBlockAndNotifyAdequately(worldIn, blockpos4, this.woodMetadata);

                        if (i2 > 0)
                        {
                            this.func_175932_b(worldIn, p_180709_2_, blockpos4.west(), Blocks.VINE.getDefaultState().withProperty(BlockVine.EAST, true));
                            this.func_175932_b(worldIn, p_180709_2_, blockpos4.south(), Blocks.VINE.getDefaultState().withProperty(BlockVine.NORTH, true));
                        }
                    }
                }
            }

            return true;
        }
    }

    private void func_175932_b(World worldIn, Random p_175932_2_, BlockPos p_175932_3_, IBlockState p_175932_4_)
    {
        if (p_175932_2_.nextInt(3) > 0 && worldIn.isAirBlock(p_175932_3_))
        {
            this.setBlockAndNotifyAdequately(worldIn, p_175932_3_, p_175932_4_);
        }
    }

    private void func_175930_c(World worldIn, BlockPos p_175930_2_, int p_175930_3_)
    {
        byte b0 = 2;

        for (int j = -b0; j <= 0; ++j)
        {
            this.func_175925_a(worldIn, p_175930_2_.up(j), p_175930_3_ + 1 - j);
        }
    }

    //Helper macro
    private boolean isAirLeaves(World world, BlockPos pos)
    {
        IBlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();
        return block.isAir(blockState, world, pos) || block.isLeaves(blockState, world, pos);
    }


    // Overwritten
    protected void func_175925_a(World worldIn, BlockPos p_175925_2_, int p_175925_3_)
    {
        int j = p_175925_3_ * p_175925_3_;

        for (int k = -p_175925_3_; k <= p_175925_3_ + 1; ++k)
        {
            for (int l = -p_175925_3_; l <= p_175925_3_ + 1; ++l)
            {
                int i1 = k - 1;
                int j1 = l - 1;

                if (k * k + l * l <= j || i1 * i1 + j1 * j1 <= j || k * k + j1 * j1 <= j || i1 * i1 + l * l <= j)
                {
                    BlockPos blockpos1 = p_175925_2_.add(k, 0, l);
                    IBlockState state = worldIn.getBlockState(blockpos1);

                    if (state.getBlock().isAir(state, worldIn, blockpos1) || state.getBlock().isLeaves(state, worldIn, blockpos1))
                    {
                        this.setBlockAndNotifyAdequately(worldIn, blockpos1, this.leavesMetadata);
                    }
                }
            }
        }
    }

    protected void func_175928_b(World worldIn, BlockPos p_175928_2_, int p_175928_3_)
    {
        int j = p_175928_3_ * p_175928_3_;

        for (int k = -p_175928_3_; k <= p_175928_3_; ++k)
        {
            for (int l = -p_175928_3_; l <= p_175928_3_; ++l)
            {
                if (k * k + l * l <= j)
                {
                    BlockPos blockpos1 = p_175928_2_.add(k, 0, l);
                    IBlockState blockState = worldIn.getBlockState(blockpos1);
                    Block block = blockState.getBlock();

                    if (block.isAir(blockState, worldIn, blockpos1) || block.isLeaves(blockState, worldIn, blockpos1))
                    {
                        this.setBlockAndNotifyAdequately(worldIn, blockpos1, leavesMetadata);
                    }
                }
            }
        }
    }
}
