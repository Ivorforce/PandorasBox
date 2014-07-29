/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package net.ivorius.pandorasbox.effects;

import net.ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public abstract class PBEffectNormal extends PBEffect
{
    public int maxTicksAlive;

    public PBEffectNormal()
    {
    }

    public PBEffectNormal(int maxTicksAlive)
    {
        this.maxTicksAlive = maxTicksAlive;
    }

    public float getRatioDone(int ticks)
    {
        if (ticks == maxTicksAlive) // Make sure value is exact
        {
            return 1.0f;
        }

        return (float) ticks / (float) maxTicksAlive;
    }

    public abstract void doEffect(World world, EntityPandorasBox entity, Random random, float newRatio, float prevRatio);

    public void setUpEffect(World world, EntityPandorasBox entity, Random random)
    {
    }

    public void finalizeEffect(World world, EntityPandorasBox entity, Random random)
    {
    }

    @Override
    public void doTick(EntityPandorasBox entity, int ticksAlive)
    {
        float prevRatio = getRatioDone(ticksAlive);
        float newRatio = getRatioDone(ticksAlive + 1);

        if (ticksAlive == 0)
        {
            setUpEffect(entity.worldObj, entity, entity.getRandom());
        }

        if (prevRatio >= 0.0f && newRatio <= 1.0f && newRatio > prevRatio)
        {
            doEffect(entity.worldObj, entity, entity.getRandom(), newRatio, prevRatio);
        }

        if (ticksAlive == maxTicksAlive)
        {
            finalizeEffect(entity.worldObj, entity, entity.getRandom());
        }
    }

    @Override
    public boolean isDone(EntityPandorasBox entity, int ticksAlive)
    {
        return ticksAlive >= maxTicksAlive;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        compound.setInteger("maxTicksAlive", maxTicksAlive);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        maxTicksAlive = compound.getInteger("maxTicksAlive");
    }

    @Override
    public boolean canGenerateMoreEffectsAfterwards(EntityPandorasBox entity)
    {
        return true;
    }
}
