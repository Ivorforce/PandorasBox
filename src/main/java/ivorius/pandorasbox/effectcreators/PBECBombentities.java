/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effectcreators;

import ivorius.pandorasbox.effects.PBEffect;
import ivorius.pandorasbox.effects.PBEffectEntitiesBomberman;
import ivorius.pandorasbox.random.DValue;
import ivorius.pandorasbox.random.IValue;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBECBombentities implements PBEffectCreator
{
    public IValue time;
    public IValue number;
    public DValue range;

    public PBECBombentities(IValue time, IValue number, DValue range)
    {
        this.time = time;
        this.number = number;
        this.range = range;
    }

    @Override
    public PBEffect constructEffect(World world, double x, double y, double z, Random random)
    {
        int number = this.number.getValue(random);
        int time = this.time.getValue(random);
        double range = this.range.getValue(random);

        PBEffectEntitiesBomberman effect = new PBEffectEntitiesBomberman(time, range, number);
        return effect;
    }

    @Override
    public float chanceForMoreEffects(World world, double x, double y, double z, Random random)
    {
        return 0.2f;
    }
}
