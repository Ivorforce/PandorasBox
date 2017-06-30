/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public abstract class PBEffectPositionBased extends PBEffectNormal
{
    public int number;

    public double range;

    protected PBEffectPositionBased()
    {
    }

    public PBEffectPositionBased(int time, int number, double range)
    {
        super(time);

        this.number = number;
        this.range = range;
    }

    @Override
    public void doEffect(World world, EntityPandorasBox entity, Vec3d effectCenter, Random random, float prevRatio, float newRatio)
    {
        int prev = getSpawnNumber(prevRatio);
        int toSpawn = getSpawnNumber(newRatio) - prev;

        for (int i = 0; i < toSpawn; i++)
        {
            double eX = effectCenter.x + (random.nextDouble() - random.nextDouble()) * range;
            double eY = effectCenter.y + (random.nextDouble() - random.nextDouble()) * 3.0 * 2.0;
            double eZ = effectCenter.z + (random.nextDouble() - random.nextDouble()) * range;

            doEffect(world, entity, random, newRatio, prevRatio, eX, eY, eZ);
        }
    }

    public abstract void doEffect(World world, EntityPandorasBox entity, Random random, float newRatio, float prevRatio, double x, double y, double z);

    private int getSpawnNumber(float ratio)
    {
        return MathHelper.floor(ratio * number);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("number", number);
        compound.setDouble("range", range);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        number = compound.getInteger("number");
        range = compound.getDouble("range");
    }
}
