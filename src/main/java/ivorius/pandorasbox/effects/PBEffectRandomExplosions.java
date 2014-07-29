/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBEffectRandomExplosions extends PBEffectPositionBased
{
    public float minExplosionStrength;
    public float maxExplosionStrength;
    public boolean isFlaming;
    public boolean isSmoking;

    protected PBEffectRandomExplosions()
    {
    }

    public PBEffectRandomExplosions(int time, int number, double range, float minExplosionStrength, float maxExplosionStrength, boolean isFlaming, boolean isSmoking)
    {
        super(time, number, range);
        this.minExplosionStrength = minExplosionStrength;
        this.maxExplosionStrength = maxExplosionStrength;
        this.isFlaming = isFlaming;
        this.isSmoking = isSmoking;
    }

    @Override
    public void doEffect(World world, EntityPandorasBox entity, Random random, float newRatio, float prevRatio, double x, double y, double z)
    {
        if (!world.isRemote)
        {
            world.newExplosion(entity, x, y, z, minExplosionStrength + random.nextFloat() * (maxExplosionStrength - minExplosionStrength), isFlaming, isSmoking);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setFloat("minExplosionStrength", minExplosionStrength);
        compound.setFloat("maxExplosionStrength", maxExplosionStrength);

        compound.setBoolean("isFlaming", isFlaming);
        compound.setBoolean("isSmoking", isSmoking);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        minExplosionStrength = compound.getFloat("minExplosionStrength");
        maxExplosionStrength = compound.getFloat("maxExplosionStrength");

        isFlaming = compound.getBoolean("isFlaming");
        isSmoking = compound.getBoolean("isSmoking");
    }
}
