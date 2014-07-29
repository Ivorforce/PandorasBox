/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package net.ivorius.pandorasbox.effectcreators;

import net.ivorius.pandorasbox.effects.PBEffect;
import net.ivorius.pandorasbox.random.IValue;
import net.ivorius.pandorasbox.random.ValueSpawn;
import net.ivorius.pandorasbox.random.ValueThrow;
import net.ivorius.pandorasbox.weighted.WeightedSet;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBECSpawnItemSet implements PBEffectCreator
{
    public IValue ticksPerItem;
    public List<WeightedSet> items;
    public ValueThrow valueThrow;
    public ValueSpawn valueSpawn;

    public PBECSpawnItemSet(IValue ticksPerItem, List<WeightedSet> items, ValueThrow valueThrow, ValueSpawn valueSpawn)
    {
        this.ticksPerItem = ticksPerItem;
        this.items = items;
        this.valueThrow = valueThrow;
        this.valueSpawn = valueSpawn;
    }

    public PBECSpawnItemSet(IValue ticksPerItem, List<WeightedSet> items)
    {
        this(ticksPerItem, items, PBECSpawnItems.defaultThrow(), null);
    }

    @Override
    public PBEffect constructEffect(World world, double x, double y, double z, Random random)
    {
        int ticksPerItem = this.ticksPerItem.getValue(random);

        ItemStack[] itemSet = ((WeightedSet) WeightedRandom.getRandomItem(random, items)).set;
        ItemStack[] stacks = new ItemStack[itemSet.length];
        for (int i = 0; i < itemSet.length; i++)
        {
            stacks[i] = itemSet[i].copy();
        }

        return PBECSpawnItems.constructEffect(random, stacks, stacks.length * ticksPerItem + 1, valueThrow, valueSpawn);
    }

    @Override
    public float chanceForMoreEffects(World world, double x, double y, double z, Random random)
    {
        return 0.1f;
    }
}
