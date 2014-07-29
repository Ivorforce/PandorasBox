/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effectcreators;

import ivorius.pandorasbox.PandorasBoxHelper;
import ivorius.pandorasbox.effects.PBEffect;
import ivorius.pandorasbox.effects.PBEffectSpawnEntities;
import ivorius.pandorasbox.effects.PBEffectSpawnEntityIDList;
import ivorius.pandorasbox.random.*;
import ivorius.pandorasbox.weighted.WeightedEntity;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBECSpawnEntities implements PBEffectCreator
{
    public IValue time;
    public IValue number;
    public IValue entitiesPerTower;
    public IValue nameEntities;
    public IValue equipLevel;
    public IValue buffLevel;

    public Collection<WeightedEntity> entityIDs;

    public ValueThrow valueThrow;
    public ValueSpawn valueSpawn;

    public PBECSpawnEntities(IValue time, IValue number, IValue entitiesPerTower, IValue nameEntities, IValue equipLevel, IValue buffLevel, Collection<WeightedEntity> entityIDs, ValueThrow valueThrow, ValueSpawn valueSpawn)
    {
        this.time = time;
        this.number = number;
        this.entitiesPerTower = entitiesPerTower;
        this.nameEntities = nameEntities;
        this.equipLevel = equipLevel;
        this.buffLevel = buffLevel;
        this.entityIDs = entityIDs;
        this.valueThrow = valueThrow;
        this.valueSpawn = valueSpawn;
    }

    public PBECSpawnEntities(IValue time, IValue number, IValue entitiesPerTower, IValue equipLevel, IValue buffLevel, IValue nameEntities, Collection<WeightedEntity> entityIDs)
    {
        this(time, number, entitiesPerTower, nameEntities, equipLevel, buffLevel, entityIDs, defaultThrow(), defaultSpawn());
    }

    public static ValueThrow defaultThrow()
    {
        return new ValueThrow(new DLinear(0.1, 0.4), new DLinear(0.2, 1.0));
    }

    public static ValueSpawn defaultSpawn()
    {
        return new ValueSpawn(new DLinear(8.0, 30.0), new DConstant(0.0));
    }

    @Override
    public PBEffect constructEffect(World world, double x, double y, double z, Random random)
    {
        int time = this.time.getValue(random);
        int number = this.number.getValue(random);
        int[] towerSize = ValueHelper.getValueRange(entitiesPerTower, random);

        int nameEntities = this.nameEntities.getValue(random);
        int equipLevel = this.equipLevel.getValue(random);
        int buffLevel = this.buffLevel.getValue(random);

        WeightedEntity[] entitySelection = PandorasBoxHelper.getRandomEntityList(random, entityIDs);

        String[][] entitiesToSpawn = new String[number][];
        for (int i = 0; i < number; i++)
        {
            entitiesToSpawn[i] = new String[towerSize[0] + random.nextInt(towerSize[1] - towerSize[0] + 1)];

            for (int j = 0; j < entitiesToSpawn[i].length; j++)
            {
                entitiesToSpawn[i][j] = entitySelection[random.nextInt(entitySelection.length)].entityID;
            }
        }

        return constructEffect(random, entitiesToSpawn, time, nameEntities, equipLevel, buffLevel, valueThrow, valueSpawn);
    }

    public static PBEffect constructEffect(Random random, String[][] entitiesToSpawn, int time, ValueThrow valueThrow, ValueSpawn valueSpawn)
    {
        return constructEffect(random, entitiesToSpawn, time, 0, 0, 0, valueThrow, valueSpawn);
    }

    public static PBEffect constructEffect(Random random, String[][] entitiesToSpawn, int time, int nameEntities, int equipLevel, int buffLevel, ValueThrow valueThrow, ValueSpawn valueSpawn)
    {
        boolean canSpawn = valueSpawn != null;
        boolean canThrow = valueThrow != null;

        if (canThrow && (!canSpawn || random.nextBoolean()))
        {
            PBEffectSpawnEntityIDList effect = new PBEffectSpawnEntityIDList(time, entitiesToSpawn, nameEntities, equipLevel, buffLevel);
            setEffectThrow(effect, random, valueThrow);
            return effect;
        }
        else if (canSpawn)
        {
            PBEffectSpawnEntityIDList effect = new PBEffectSpawnEntityIDList(time, entitiesToSpawn, nameEntities, equipLevel, buffLevel);
            setEffectSpawn(effect, random, valueSpawn);
            return effect;
        }

        throw new RuntimeException("Both spawnRange and throwStrength are null!");
    }

    public static void setEffectThrow(PBEffectSpawnEntities effect, Random random, ValueThrow valueThrow)
    {
        double[] throwX = ValueHelper.getValueRange(valueThrow.throwStrengthSide, random);
        double[] throwY = ValueHelper.getValueRange(valueThrow.throwStrengthY, random);
        effect.setDoesSpawnFromBox(throwX[0], throwX[1], throwY[0], throwY[1]);
    }

    public static void setEffectSpawn(PBEffectSpawnEntities effect, Random random, ValueSpawn valueSpawn)
    {
        double range = valueSpawn.spawnRange.getValue(random);
        double spawnShift = valueSpawn.spawnShift.getValue(random);
        effect.setDoesNotSpawnFromBox(range, spawnShift);
    }

    @Override
    public float chanceForMoreEffects(World world, double x, double y, double z, Random random)
    {
        return 0.1f;
    }
}
