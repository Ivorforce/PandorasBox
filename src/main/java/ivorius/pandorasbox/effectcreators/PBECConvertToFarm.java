/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effectcreators;

import ivorius.pandorasbox.PandorasBoxHelper;
import ivorius.pandorasbox.effects.PBEffect;
import ivorius.pandorasbox.effects.PBEffectGenConvertToFarm;
import ivorius.pandorasbox.random.DValue;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBECConvertToFarm implements PBEffectCreator
{
    public DValue range;
    public DValue cropChance;

    public PBECConvertToFarm(DValue range, DValue cropChance)
    {
        this.range = range;
        this.cropChance = cropChance;
    }

    @Override
    public PBEffect constructEffect(World world, double x, double y, double z, Random random)
    {
        double range = this.range.getValue(random);
        double cropChance = this.cropChance.getValue(random);
        int time = MathHelper.floor_double((random.nextDouble() * 7.0 + 3.0) * range);

        PBEffectGenConvertToFarm effect = new PBEffectGenConvertToFarm(time, range, PandorasBoxHelper.getRandomUnifiedSeed(random), cropChance);
        return effect;
    }

    @Override
    public float chanceForMoreEffects(World world, double x, double y, double z, Random random)
    {
        return 0.1f;
    }
}
