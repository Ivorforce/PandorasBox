/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package net.ivorius.pandorasbox.effectcreators;

import net.ivorius.pandorasbox.PandorasBoxHelper;
import net.ivorius.pandorasbox.effects.PBEffect;
import net.ivorius.pandorasbox.effects.PBEffectGenTargets;
import net.ivorius.pandorasbox.random.DValue;
import net.ivorius.pandorasbox.random.IValue;
import net.ivorius.pandorasbox.weighted.WeightedEntity;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBECTargets implements PBEffectCreator
{
    public IValue time;
    public DValue range;
    public DValue targetSize;
    public DValue entityDensity;

    public Collection<WeightedEntity> entities;

    public PBECTargets(IValue time, DValue range, DValue targetSize, DValue entityDensity, Collection<WeightedEntity> entities)
    {
        this.time = time;
        this.range = range;
        this.targetSize = targetSize;
        this.entityDensity = entityDensity;
        this.entities = entities;
    }

    @Override
    public PBEffect constructEffect(World world, double x, double y, double z, Random random)
    {
        double range = this.range.getValue(random);
        double targetSize = this.targetSize.getValue(random);
        double entityDensity = this.entityDensity.getValue(random);
        int time = this.time.getValue(random);

        WeightedEntity entity = PandorasBoxHelper.getRandomEntityFromList(random, entities);

        PBEffectGenTargets gen = new PBEffectGenTargets(time, entity.entityID, range, targetSize, entityDensity);
        gen.createTargets(world, x, y, z, random);
        return gen;
    }

    @Override
    public float chanceForMoreEffects(World world, double x, double y, double z, Random random)
    {
        return 0.15f;
    }
}
