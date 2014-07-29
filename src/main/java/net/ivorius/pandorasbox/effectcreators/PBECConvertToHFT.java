/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package net.ivorius.pandorasbox.effectcreators;

import net.ivorius.pandorasbox.PandorasBoxHelper;
import net.ivorius.pandorasbox.effects.PBEffect;
import net.ivorius.pandorasbox.effects.PBEffectGenConvertToHFT;
import net.ivorius.pandorasbox.random.DValue;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBECConvertToHFT implements PBEffectCreator
{
    public DValue range;

    public PBECConvertToHFT(DValue range)
    {
        this.range = range;
    }

    @Override
    public PBEffect constructEffect(World world, double x, double y, double z, Random random)
    {
        double range = this.range.getValue(random);
        int time = MathHelper.floor_double((random.nextDouble() * 7.0 + 3.0) * range);

        int[] metaTypes = new int[random.nextInt(3) + 2];
        for (int i = 0; i < metaTypes.length; i++)
        {
            metaTypes[i] = random.nextInt(16);
        }

        PBEffectGenConvertToHFT effect = new PBEffectGenConvertToHFT(time, range, PandorasBoxHelper.getRandomUnifiedSeed(random), metaTypes);
        return effect;
    }

    @Override
    public float chanceForMoreEffects(World world, double x, double y, double z, Random random)
    {
        return 0.1f;
    }
}
