/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;

import java.util.Random;

/**
 * Created by lukas on 03.04.14.
 */
public class PBEffectSetWeather extends PBEffectNormal
{
    public boolean rain;
    public boolean thunder;
    public int rainTime;

    public PBEffectSetWeather()
    {
    }

    public PBEffectSetWeather(int maxTicksAlive, boolean rain, boolean thunder, int rainTime)
    {
        super(maxTicksAlive);
        this.rain = rain;
        this.thunder = thunder;
        this.rainTime = rainTime;
    }

    @Override
    public void doEffect(World world, EntityPandorasBox entity, Vec3d effectCenter, Random random, float prevRatio, float newRatio)
    {
    }

    @Override
    public void finalizeEffect(World world, EntityPandorasBox entity, Vec3d effectCenter, Random random)
    {
        WorldInfo worldInfo = world.getWorldInfo();
        worldInfo.setRainTime(rainTime);
        worldInfo.setThunderTime(rainTime);
        worldInfo.setRaining(rain);
        worldInfo.setThundering(rain && thunder);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("rainTime", rainTime);
        compound.setBoolean("rain", rain);
        compound.setBoolean("thunder", thunder);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        rainTime = compound.getInteger("rainTime");
        rain = compound.getBoolean("rain");
        thunder = compound.getBoolean("thunder");
    }
}
