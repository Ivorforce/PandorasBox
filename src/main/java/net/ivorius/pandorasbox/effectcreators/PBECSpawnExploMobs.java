/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package net.ivorius.pandorasbox.effectcreators;

import net.ivorius.pandorasbox.PandorasBoxHelper;
import net.ivorius.pandorasbox.effects.PBEffect;
import net.ivorius.pandorasbox.random.IValue;
import net.ivorius.pandorasbox.random.ValueSpawn;
import net.ivorius.pandorasbox.random.ValueThrow;
import net.ivorius.pandorasbox.weighted.WeightedEntity;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBECSpawnExploMobs implements PBEffectCreator
{
    public IValue time;
    public IValue number;
    public IValue fuseTime;
    public IValue nameEntities;
    public Collection<WeightedEntity> entityIDs;

    public ValueThrow valueThrow;
    public ValueSpawn valueSpawn;

    public PBECSpawnExploMobs(IValue time, IValue number, IValue fuseTime, IValue nameEntities, Collection<WeightedEntity> entityIDs, ValueThrow valueThrow, ValueSpawn valueSpawn)
    {
        this.time = time;
        this.number = number;
        this.fuseTime = fuseTime;
        this.nameEntities = nameEntities;
        this.entityIDs = entityIDs;
        this.valueThrow = valueThrow;
        this.valueSpawn = valueSpawn;
    }

    public PBECSpawnExploMobs(IValue time, IValue number, IValue fuseTime, IValue nameEntities, Collection<WeightedEntity> entityIDs)
    {
        this(time, number, fuseTime, nameEntities, entityIDs, PBECSpawnEntities.defaultThrow(), PBECSpawnEntities.defaultSpawn());
    }

    @Override
    public PBEffect constructEffect(World world, double x, double y, double z, Random random)
    {
        int time = this.time.getValue(random);
        int number = this.number.getValue(random);
        WeightedEntity entity = PandorasBoxHelper.getRandomEntityFromList(random, entityIDs);
        boolean invisible = random.nextBoolean();

        String[][] entitiesToSpawn = new String[number][];
        for (int i = 0; i < number; i++)
        {
            entitiesToSpawn[i] = new String[2];
            entitiesToSpawn[i][0] = (invisible ? "pbspecial_invisibleTnt" : "pbspecial_tnt") + this.fuseTime.getValue(random);
            entitiesToSpawn[i][1] = entity.entityID;
        }

        int nameEntities = this.nameEntities.getValue(random);

        return PBECSpawnEntities.constructEffect(random, entitiesToSpawn, time, nameEntities, 0, 0, valueThrow, valueSpawn);
    }

    @Override
    public float chanceForMoreEffects(World world, double x, double y, double z, Random random)
    {
        return 0.1f;
    }
}
