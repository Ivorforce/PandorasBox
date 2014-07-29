/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package net.ivorius.pandorasbox.effectcreators;

import net.ivorius.pandorasbox.PandorasBoxHelper;
import net.ivorius.pandorasbox.effects.PBEffect;
import net.ivorius.pandorasbox.effects.PBEffectMulti;
import net.ivorius.pandorasbox.random.ILinear;
import net.ivorius.pandorasbox.random.IValue;
import net.ivorius.pandorasbox.random.ValueSpawn;
import net.ivorius.pandorasbox.random.ValueThrow;
import net.ivorius.pandorasbox.weighted.WeightedEntity;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBECSpawnArmy implements PBEffectCreator
{
    public IValue groups;
    public IValue equipLevel;

    public Collection<WeightedEntity> entityIDs;

    public ValueThrow valueThrow;
    public ValueSpawn valueSpawn;

    public PBECSpawnArmy(IValue groups, IValue equipLevel, Collection<WeightedEntity> entityIDs, ValueThrow valueThrow, ValueSpawn valueSpawn)
    {
        this.groups = groups;
        this.equipLevel = equipLevel;
        this.entityIDs = entityIDs;
        this.valueThrow = valueThrow;
        this.valueSpawn = valueSpawn;
    }

    public PBECSpawnArmy(IValue groups, IValue equipLevel, Collection<WeightedEntity> entityIDs)
    {
        this(groups, equipLevel, entityIDs, PBECSpawnEntities.defaultThrow(), PBECSpawnEntities.defaultSpawn());
    }

    @Override
    public PBEffect constructEffect(World world, double x, double y, double z, Random random)
    {
        int groups = this.groups.getValue(random);

        WeightedEntity[] entitySelection = PandorasBoxHelper.getRandomEntityList(random, entityIDs);

        PBEffect[] effects = new PBEffect[groups * 2];
        int[] delays = new int[effects.length];

        for (int i = 0; i < groups; i++)
        {
            WeightedEntity soldierType = entitySelection[random.nextInt(entitySelection.length)];
            String[][] soldiers = new String[new ILinear(soldierType.minNumber, soldierType.maxNumber).getValue(random)][];
            Arrays.fill(soldiers, new String[]{soldierType.entityID});

            int equipLevel = this.equipLevel.getValue(random);
            int equipLevelCaptain = 5 + equipLevel;
            int buffLevelCaptain = random.nextInt(5);

            effects[i * 2] = PBECSpawnEntities.constructEffect(random, soldiers, 50, 0, equipLevel, 0, valueThrow, valueSpawn);
            effects[i * 2 + 1] = PBECSpawnEntities.constructEffect(random, new String[][]{new String[]{soldierType.entityID}}, 25, 1, equipLevelCaptain, buffLevelCaptain, valueThrow, valueSpawn);
            delays[i * 2] = i * 50;
            delays[i * 2 + 1] = i * 50 + 25;
        }

        return new PBEffectMulti(effects, delays);
    }

    @Override
    public float chanceForMoreEffects(World world, double x, double y, double z, Random random)
    {
        return 0.1f;
    }
}
