/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.Vec3d;
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

    public abstract void doEffect(World world, EntityPandorasBox entity, Vec3d effectCenter, Random random, float prevRatio, float newRatio);

    public void setUpEffect(World world, EntityPandorasBox entity, Vec3d effectCenter, Random random)
    {
    }

    public void finalizeEffect(World world, EntityPandorasBox entity, Vec3d effectCenter, Random random)
    {
    }

    @Override
    public void doTick(EntityPandorasBox entity, Vec3d effectCenter, int ticksAlive)
    {
        float prevRatio = getRatioDone(ticksAlive);
        float newRatio = getRatioDone(ticksAlive + 1);

        if (ticksAlive == 0)
            setUpEffect(entity.worldObj, entity, effectCenter, entity.getRandom());

        if (prevRatio >= 0.0f && newRatio <= 1.0f && newRatio > prevRatio)
            doEffect(entity.worldObj, entity, effectCenter, entity.getRandom(), prevRatio, newRatio);

        if (ticksAlive == maxTicksAlive - 1)
            finalizeEffect(entity.worldObj, entity, effectCenter, entity.getRandom());
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
