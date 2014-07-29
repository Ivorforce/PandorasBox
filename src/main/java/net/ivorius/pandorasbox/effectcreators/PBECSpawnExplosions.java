/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package net.ivorius.pandorasbox.effectcreators;

import net.ivorius.pandorasbox.effects.PBEffect;
import net.ivorius.pandorasbox.effects.PBEffectRandomExplosions;
import net.ivorius.pandorasbox.random.DValue;
import net.ivorius.pandorasbox.random.IValue;
import net.ivorius.pandorasbox.random.ValueHelper;
import net.ivorius.pandorasbox.random.ZValue;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBECSpawnExplosions implements PBEffectCreator
{
    public IValue time;
    public IValue number;
    public DValue range;

    public DValue explosionStrength;

    public ZValue isFlaming;
    public ZValue isSmoking;

    public PBECSpawnExplosions(IValue time, IValue number, DValue range, DValue explosionStrength, ZValue isFlaming, ZValue isSmoking)
    {
        this.time = time;
        this.number = number;
        this.range = range;
        this.explosionStrength = explosionStrength;
        this.isFlaming = isFlaming;
        this.isSmoking = isSmoking;
    }

    @Override
    public PBEffect constructEffect(World world, double x, double y, double z, Random random)
    {
        int time = this.time.getValue(random);
        int number = this.number.getValue(random);
        double range = this.range.getValue(random);
        double[] strength = ValueHelper.getValueRange(explosionStrength, random);
        boolean isFlaming = this.isFlaming.getValue(random);
        boolean isSmoking = this.isSmoking.getValue(random);

        PBEffectRandomExplosions effect = new PBEffectRandomExplosions(time, number, range, (float) strength[0], (float) strength[1], isFlaming, isSmoking);
        return effect;
    }

    @Override
    public float chanceForMoreEffects(World world, double x, double y, double z, Random random)
    {
        return 0.7f;
    }
}
