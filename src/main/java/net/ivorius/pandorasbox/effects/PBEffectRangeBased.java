/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package net.ivorius.pandorasbox.effects;

import net.ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 31.03.14.
 */
public abstract class PBEffectRangeBased extends PBEffectNormal
{
    public double range;
    public int passes;

    public PBEffectRangeBased()
    {
    }

    public PBEffectRangeBased(int maxTicksAlive, double range, int passes)
    {
        super(maxTicksAlive);
        this.range = range;
        this.passes = passes;
    }

    @Override
    public void doEffect(World world, EntityPandorasBox entity, Random random, float newRatio, float prevRatio)
    {
        for (int i = 0; i < passes; i++)
        {
            double prevRange = getRange(prevRatio, i);
            double newRange = getRange(newRatio, i);

            generateInRange(world, entity, random, newRange, prevRange, i);
        }
    }

    private double getRange(float ratio, int pass)
    {
        double fullRange = range + (passes - 1) * 5.0;
        double tempRange = ratio * fullRange - pass * 5.0;

        return MathHelper.clamp_double(tempRange, 0.0, range);
    }

    public abstract void generateInRange(World world, EntityPandorasBox entity, Random random, double newRange, double prevRange, int pass);

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setDouble("range", range);
        compound.setInteger("passes", passes);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        range = compound.getDouble("range");
        passes = compound.getInteger("passes");
    }
}
