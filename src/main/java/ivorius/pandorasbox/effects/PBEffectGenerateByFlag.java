/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public abstract class PBEffectGenerateByFlag extends PBEffectRangeBased
{
    public int unifiedSeed;

    public int[] flags;

    public PBEffectGenerateByFlag()
    {
    }

    public PBEffectGenerateByFlag(int time, double range, int passes, int unifiedSeed)
    {
        super(time, range, passes);

        this.unifiedSeed = unifiedSeed;
        flags = new int[31 * 31];
    }

    @Override
    public void setUpEffect(World world, EntityPandorasBox entity, Vec3d effectCenter, Random random)
    {
        super.setUpEffect(world, entity, effectCenter, random);

        byte requiredRange = (byte) MathHelper.ceil(range);

        int baseX = MathHelper.floor(effectCenter.xCoord);
        int baseY = MathHelper.floor(effectCenter.yCoord);
        int baseZ = MathHelper.floor(effectCenter.zCoord);

        boolean[] flags = new boolean[31];

        for (byte x = (byte) -requiredRange; x <= requiredRange; x++)
        {
            for (byte z = (byte) -requiredRange; z <= requiredRange; z++)
            {
                for (byte y = (byte) -requiredRange; y <= requiredRange; y++)
                {
                    double dist = MathHelper.sqrt(x * x + y * y + z * z);

                    if (dist <= range)
                        flags[y + 15] = hasFlag(world, entity, random, new BlockPos(baseX + x, baseY + y, baseZ + z));
                }

                setAllFlags(x, z, flags);
            }
        }
    }

    public abstract boolean hasFlag(World world, EntityPandorasBox entity, Random random, BlockPos pos);

    @Override
    public void generateInRange(World world, EntityPandorasBox entity, Random random, Vec3d effectCenter, double prevRange, double newRange, int pass)
    {
        byte requiredRange = (byte) MathHelper.ceil(newRange);

        int baseX = MathHelper.floor(effectCenter.xCoord);
        int baseY = MathHelper.floor(effectCenter.yCoord);
        int baseZ = MathHelper.floor(effectCenter.zCoord);

        for (byte x = (byte) -requiredRange; x <= requiredRange; x++)
        {
            for (byte y = (byte) -requiredRange; y <= requiredRange; y++)
            {
                for (byte z = (byte) -requiredRange; z <= requiredRange; z++)
                {
                    double dist = MathHelper.sqrt(x * x + y * y + z * z);

                    if (dist <= newRange)
                    {
                        if (dist > prevRange)
                        {
                            generateOnBlock(world, entity, random, pass, new BlockPos(baseX + x, baseY + y, baseZ + z), dist, getFlag(x, y, z));
                        }
                        else
                        {
                            z = (byte) -z; // We can skip all blocks in between
                        }
                    }
                }
            }
        }
    }

    public abstract void generateOnBlock(World world, EntityPandorasBox entity, Random random, int pass, BlockPos pos, double dist, boolean flag);

    public void setAllFlags(byte x, byte z, boolean... flags)
    {
        int flagInt = 0;
        for (int i = flags.length - 1; i >= 0; i--)
        {
            boolean flag = flags[i];
            if (flag)
            {
                flagInt = (flagInt << 1) + 1;
            }
            else
            {
                flagInt = (flagInt << 1);
            }
        }

        this.flags[getFlagIndex(x, z)] = flagInt;
    }

    public void setFlag(byte x, byte y, byte z, boolean flag)
    {
        int index = getFlagIndex(x, z);
        int bit = getBitOfFlag(y);

        if (flag)
        {
            flags[index] = flags[index] | bit;
        }
        else
        {
            flags[index] = flags[index] & (~bit);
        }
    }

    public boolean getFlag(byte x, byte y, byte z)
    {
        int index = getFlagIndex(x, z);
        int bit = getBitOfFlag(y);

        return (flags[index] & bit) > 0;
    }

    public int getBitOfFlag(byte y)
    {
        return 1 << (y + 15);
    }

    public int getFlagIndex(byte x, byte z)
    {
        return (x + 15) * 31 + (z + 15);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("unifiedSeed", unifiedSeed);
        compound.setIntArray("flags", flags);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        unifiedSeed = compound.getInteger("unifiedSeed");
        flags = compound.getIntArray("flags");
    }
}
