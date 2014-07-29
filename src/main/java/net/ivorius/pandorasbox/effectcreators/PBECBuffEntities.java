/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package net.ivorius.pandorasbox.effectcreators;

import net.ivorius.pandorasbox.effects.PBEffect;
import net.ivorius.pandorasbox.effects.PBEffectEntitiesBuff;
import net.ivorius.pandorasbox.random.DValue;
import net.ivorius.pandorasbox.random.IValue;
import net.ivorius.pandorasbox.weighted.WeightedPotion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBECBuffEntities implements PBEffectCreator
{
    public IValue time;
    public IValue number;
    public DValue range;

    public float chanceForMoreEffects;

    public Collection<WeightedPotion> applicablePotions;

    public PBECBuffEntities(IValue time, IValue number, DValue range, float chanceForMoreEffects, Collection<WeightedPotion> applicablePotions)
    {
        this.time = time;
        this.number = number;
        this.range = range;
        this.chanceForMoreEffects = chanceForMoreEffects;
        this.applicablePotions = applicablePotions;
    }

    @Override
    public PBEffect constructEffect(World world, double x, double y, double z, Random random)
    {
        int number = this.number.getValue(random);
        int time = this.time.getValue(random);
        double range = this.range.getValue(random);

        PotionEffect[] effects = new PotionEffect[number];
        for (int i = 0; i < effects.length; i++)
        {
            WeightedPotion weightedPotion = (WeightedPotion) WeightedRandom.getRandomItem(random, applicablePotions);

            int duration = random.nextInt(weightedPotion.maxDuration - weightedPotion.minDuration + 1) + weightedPotion
                    .minDuration;
            int strength = random.nextInt(weightedPotion.maxStrength - weightedPotion.minStrength + 1) + weightedPotion
                    .minStrength;
            effects[i] = new PotionEffect(weightedPotion.potion.getId(), duration, strength, false);
        }

        PBEffectEntitiesBuff effect = new PBEffectEntitiesBuff(time, range, effects);
        return effect;
    }

    @Override
    public float chanceForMoreEffects(World world, double x, double y, double z, Random random)
    {
        return chanceForMoreEffects;
    }
}
