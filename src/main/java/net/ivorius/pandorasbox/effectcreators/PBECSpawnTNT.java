/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package net.ivorius.pandorasbox.effectcreators;

import net.ivorius.pandorasbox.effects.PBEffect;
import net.ivorius.pandorasbox.random.IValue;
import net.ivorius.pandorasbox.random.ValueSpawn;
import net.ivorius.pandorasbox.random.ValueThrow;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBECSpawnTNT implements PBEffectCreator
{
    public IValue time;
    public IValue number;
    public IValue fuseTime;

    public ValueThrow valueThrow;
    public ValueSpawn valueSpawn;

    public PBECSpawnTNT(IValue time, IValue number, IValue fuseTime, ValueThrow valueThrow, ValueSpawn valueSpawn)
    {
        this.time = time;
        this.number = number;
        this.fuseTime = fuseTime;
        this.valueThrow = valueThrow;
        this.valueSpawn = valueSpawn;
    }

    @Override
    public PBEffect constructEffect(World world, double x, double y, double z, Random random)
    {
        int time = this.time.getValue(random);
        int number = this.number.getValue(random);

        String[][] entitiesToSpawn = new String[number][];
        for (int i = 0; i < number; i++)
        {
            entitiesToSpawn[i] = new String[]{"pbspecial_tnt" + this.fuseTime.getValue(random)};
        }

        return PBECSpawnEntities.constructEffect(random, entitiesToSpawn, time, valueThrow, valueSpawn);
    }

    @Override
    public float chanceForMoreEffects(World world, double x, double y, double z, Random random)
    {
        return 0.15f;
    }
}
