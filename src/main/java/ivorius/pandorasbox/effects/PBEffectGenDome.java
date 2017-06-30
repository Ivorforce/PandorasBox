/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import ivorius.pandorasbox.utils.PBNBTHelper;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
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
    public void generateOnSurface(World world, EntityPandorasBox box, Vec3d effectCenter, Random random, BlockPos pos, double dist, int pass)
    {
        int domeHeightY = MathHelper.ceil(range);

        for (int y = -domeHeightY; y <= domeHeightY; y++)
        {
            BlockPos shiftedPos = pos.up(y);

            if (pass == 0)
            {
                if (isSpherePart(shiftedPos.getX() + 0.5, shiftedPos.getY() + 0.5, shiftedPos.getZ() + 0.5, effectCenter.x, effectCenter.y, effectCenter.z, range - 1.5, range))
                {
                    Block block = world.getBlockState(shiftedPos).getBlock();

                    if (block.isReplaceable(world, shiftedPos))
                    {
                        setBlockVarying(world, shiftedPos, this.block, unifiedSeed);
                    }
                }
            }
            else if (pass == 1 && fillBlock != null)
            {
                if (isSpherePart(shiftedPos.getX() + 0.5, shiftedPos.getY() + 0.5, shiftedPos.getZ() + 0.5, effectCenter.x, effectCenter.y, effectCenter.z, 0.0, range - 1.5))
                {
                    Block block = world.getBlockState(shiftedPos).getBlock();

                    if (block.isReplaceable(world, shiftedPos))
                    {
                        setBlockVarying(world, shiftedPos, this.fillBlock, unifiedSeed);
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

        compound.setString("block", PBNBTHelper.storeBlockString(block));

        if (fillBlock != null)
            compound.setString("fillBlock", PBNBTHelper.storeBlockString(fillBlock));
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        block = PBNBTHelper.getBlock(compound.getString("block"));
        fillBlock = PBNBTHelper.getBlock(compound.getString("fillBlock"));
    }
}
