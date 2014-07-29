/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.worldgen;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

/**
 * Created by lukas on 14.02.14.
 */
public class WorldGenRainbow extends WorldGenAbstractTree
{
    public final Block block;
    public final int addition;
    public final Block soil;

    public WorldGenRainbow(boolean notify, Block block, int addition, Block soil)
    {
        super(notify);

        this.block = block;
        this.addition = addition;
        this.soil = soil;
    }

    @Override
    public boolean generate(World par1World, Random var2, int par3, int par4, int par5)
    {
        if (par1World.getBlock(par3, par4, par5) == soil && par1World.getBlock(par3, par4 + 1, par5) == Blocks.air)
        {
            boolean rotated = var2.nextBoolean();
            int l = var2.nextInt(addition) + 5;

            for (int shift = -1; shift <= 1; shift++)
            {
                for (int s = -l / 2; s <= l / 2; s++)
                {
                    for (int y = -l / 2; y <= l / 2; y++)
                    {
                        int distance = MathHelper.floor_float(MathHelper.sqrt_float(s * s + y * y));

                        if (distance <= (l / 2 - MathHelper.floor_float(MathHelper.sqrt_float(shift * shift)) * 2) && distance > l / 4)
                        {
                            int x = (!rotated ? s : shift) + par3;
                            int z = (rotated ? s : shift) + par5;
                            int rY = y + par4;

                            Block block1 = par1World.getBlock(x, rY, z);

                            if (block1.isAir(par1World, x, rY, z) || block1.isLeaves(par1World, x, rY, z))
                            {
                                int meta = distance;
                                if (meta < 0)
                                {
                                    meta = 0;
                                }
                                if (meta > 15)
                                {
                                    meta = 15;
                                }

                                this.setBlockAndNotifyAdequately(par1World, x, rY, z, block, meta);
                            }
                        }
                    }
                }
            }

            return true;
        }

        return false;
    }
}
