/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effectcreators;

import ivorius.pandorasbox.effects.PBEffect;
import ivorius.pandorasbox.effects.PBEffectSetWeather;
import ivorius.pandorasbox.random.IValue;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBECSetWeather implements PBEffectCreator
{
    public IValue weather;
    public IValue rainTime;
    public IValue delay;

    public PBECSetWeather(IValue weather, IValue rainTime, IValue delay)
    {
        this.weather = weather;
        this.rainTime = rainTime;
        this.delay = delay;
    }

    @Override
    public PBEffect constructEffect(World world, double x, double y, double z, Random random)
    {
        int time = this.delay.getValue(random);
        int weather = this.weather.getValue(random);
        int rainTime = this.rainTime.getValue(random);

        PBEffectSetWeather effect = new PBEffectSetWeather(time, weather > 0, weather > 1, rainTime);
        return effect;
    }

    @Override
    public float chanceForMoreEffects(World world, double x, double y, double z, Random random)
    {
        return 0.8f;
    }
}
