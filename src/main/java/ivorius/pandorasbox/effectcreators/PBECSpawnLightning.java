/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effectcreators;

import ivorius.pandorasbox.effects.PBEffect;
import ivorius.pandorasbox.effects.PBEffectRandomLightnings;
import ivorius.pandorasbox.random.DValue;
import ivorius.pandorasbox.random.IValue;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBECSpawnLightning implements PBEffectCreator
{
    public IValue time;
    public IValue number;
    public DValue range;

    public PBECSpawnLightning(IValue time, IValue number, DValue range)
    {
        this.time = time;
        this.number = number;
        this.range = range;
    }

    @Override
    public PBEffect constructEffect(World world, double x, double y, double z, Random random)
    {
        int time = this.time.getValue(random);
        int number = this.number.getValue(random);
        double range = this.range.getValue(random);

        PBEffectRandomLightnings effect = new PBEffectRandomLightnings(time, number, range);
        return effect;
    }

    @Override
    public float chanceForMoreEffects(World world, double x, double y, double z, Random random)
    {
        return 0.7f;
    }
}
