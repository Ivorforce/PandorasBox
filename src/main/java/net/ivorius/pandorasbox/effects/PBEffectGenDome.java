/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package net.ivorius.pandorasbox.effects;

import net.ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBEffectGenDome extends PBEffectGenerate2D
{
    public Block block;
    public Block fillBlock;

    public PBEffectGenDome()
    {
    }

    public PBEffectGenDome(int time, double range, int unifiedSeed, Block block, Block fillBlock)
    {
        super(time, range, 2, unifiedSeed);
        this.block = block;
        this.fillBlock = fillBlock;
    }

    @Override
    public void generateOnSurface(World world, EntityPandorasBox entity, Random random, int pass, int x, int baseY, int z, double dist)
    {
        int domeHeightY = MathHelper.ceiling_double_int(range);

        for (int y = -domeHeightY; y <= domeHeightY; y++)
        {
            if (pass == 0)
            {
                if (isSpherePart(x + 0.5, baseY + y + 0.5, z + 0.5, entity.posX, entity.posY, entity.posZ, range - 1.5, range))
                {
                    Block block = world.getBlock(x, baseY + y, z);

                    if (block.isReplaceable(world, x, baseY + y, z))
                    {
                        setBlockVarying(world, x, baseY + y, z, this.block, unifiedSeed);
                    }
                }
            }
            else if (pass == 1 && fillBlock != null)
            {
                if (isSpherePart(x + 0.5, baseY + y + 0.5, z + 0.5, entity.posX, entity.posY, entity.posZ, 0.0, range - 1.5))
                {
                    Block block = world.getBlock(x, baseY + y, z);

                    if (block.isReplaceable(world, x, baseY + y, z))
                    {
                        setBlockVarying(world, x, baseY + y, z, this.fillBlock, unifiedSeed);
                    }
                }
            }
        }
    }

    public static boolean isSpherePart(double x, double y, double z, double centerX, double centerY, double centerZ, double distStart, double distEnd)
    {
        double xDist = centerX - x;
        double yDist = centerY - y;
        double zDist = centerZ - z;
        double rangeSQ = xDist * xDist + yDist * yDist + zDist * zDist;

        return rangeSQ >= distStart * distStart && rangeSQ < distEnd * distEnd;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setString("block", Block.blockRegistry.getNameForObject(block));

        if (fillBlock != null)
        {
            compound.setString("fillBlock", Block.blockRegistry.getNameForObject(fillBlock));
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        block = (Block) Block.blockRegistry.getObject(compound.getString("block"));
        fillBlock = (Block) Block.blockRegistry.getObject(compound.getString("fillBlock"));
    }
}
