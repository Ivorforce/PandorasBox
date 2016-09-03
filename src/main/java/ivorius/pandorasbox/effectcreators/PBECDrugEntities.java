/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effectcreators;

import ivorius.pandorasbox.effects.PBEffect;
import ivorius.pandorasbox.effects.PBEffectEntitiesDrug;
import ivorius.pandorasbox.mods.PsychedelicraftModule;
import ivorius.pandorasbox.random.DValue;
import ivorius.pandorasbox.random.IValue;
import ivorius.ivtoolkit.random.WeightedSelector;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBECDrugEntities implements PBEffectCreator
{
    public IValue time;
    public IValue number;
    public DValue range;

    public float chanceForMoreEffects;

    public Collection<PsychedelicraftModule.WeightedDrug> applicableDrugs;

    public PBECDrugEntities(IValue time, IValue number, DValue range, float chanceForMoreEffects, Collection<PsychedelicraftModule.WeightedDrug> applicableDrugs)
    {
        this.time = time;
        this.number = number;
        this.range = range;
        this.chanceForMoreEffects = chanceForMoreEffects;
        this.applicableDrugs = applicableDrugs;
    }

    @Override
    public PBEffect constructEffect(World world, double x, double y, double z, Random random)
    {
        int number = this.number.getValue(random);
        int time = this.time.getValue(random);
        double range = this.range.getValue(random);

        List<Pair<String, Float>> effects = new ArrayList<>(number);
        for (int i = 0; i < number; i++)
        {
            PsychedelicraftModule.WeightedDrug weightedDrug = WeightedSelector.selectItem(random, applicableDrugs);
            float value = random.nextFloat() * (weightedDrug.maxAddValue - weightedDrug.minAddValue) + weightedDrug.minAddValue;

            effects.add(new ImmutablePair<>(weightedDrug.drugName, value));
        }

        PBEffectEntitiesDrug effect = new PBEffectEntitiesDrug(time, range, effects);
        return effect;
    }

    @Override
    public float chanceForMoreEffects(World world, double x, double y, double z, Random random)
    {
        return chanceForMoreEffects;
    }
}
