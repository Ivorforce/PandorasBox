/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import ivorius.pandorasbox.math.IvMathHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 31.03.14.
 */
public abstract class PBEffectRangeBased extends PBEffectNormal
{
    public double range;
    public int passes;

    public boolean spreadSquared = true;
    public boolean easeInOut = true;

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
    public void doEffect(World world, EntityPandorasBox entity, Vec3d effectCenter, Random random, float prevRatio, float newRatio)
    {
        for (int i = 0; i < passes; i++)
        {
            double prevRange = getRange(prevRatio, i);
            double newRange = getRange(newRatio, i);

            generateInRange(world, entity, random, effectCenter, prevRange, newRange, i);
        }
    }

    private double getRange(double ratio, int pass)
    {
        if (spreadSquared)
            ratio = Math.sqrt(ratio);
        if (easeInOut)
            ratio = IvMathHelper.mixEaseInOut(0.0, 1.0, ratio);

        double fullRange = range + (passes - 1) * 5.0;
        double tempRange = ratio * fullRange - pass * 5.0;

        return MathHelper.clamp_double(tempRange, 0.0, range);
    }

    public abstract void generateInRange(World world, EntityPandorasBox entity, Random random, Vec3d effectCenter, double prevRange, double newRange, int pass);

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setDouble("range", range);
        compound.setInteger("passes", passes);
        compound.setBoolean("spreadSquared", spreadSquared);
        compound.setBoolean("easeInOut", easeInOut);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        range = compound.getDouble("range");
        passes = compound.getInteger("passes");
        spreadSquared = compound.getBoolean("spreadSquared");
        easeInOut = compound.getBoolean("easeInOut");
    }
}
