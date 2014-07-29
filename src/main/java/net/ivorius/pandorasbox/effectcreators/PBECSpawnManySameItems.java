/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package net.ivorius.pandorasbox.effectcreators;

import net.ivorius.pandorasbox.effects.PBEffect;
import net.ivorius.pandorasbox.random.IValue;
import net.ivorius.pandorasbox.random.ValueSpawn;
import net.ivorius.pandorasbox.random.ValueThrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBECSpawnManySameItems implements PBEffectCreator
{
    public IValue ticksPerStack;
    public List<WeightedRandomChestContent> items;
    public ValueThrow valueThrow;
    public ValueSpawn valueSpawn;

    public PBECSpawnManySameItems(IValue ticksPerStack, List<WeightedRandomChestContent> items, ValueThrow valueThrow, ValueSpawn valueSpawn)
    {
        this.ticksPerStack = ticksPerStack;
        this.items = items;
        this.valueThrow = valueThrow;
        this.valueSpawn = valueSpawn;
    }

    public PBECSpawnManySameItems(IValue ticksPerStack, List<WeightedRandomChestContent> items)
    {
        this(ticksPerStack, items, PBECSpawnItems.defaultThrow(), null);
    }

    @Override
    public PBEffect constructEffect(World world, double x, double y, double z, Random random)
    {
        int ticksPerStack = this.ticksPerStack.getValue(random);
        int number = random.nextInt(5) + 5;

        ItemStack[] stacks = PBECSpawnItems.getItemStacks(random, items, number, true, true, 0, false);
        return PBECSpawnItems.constructEffect(random, stacks, number * ticksPerStack + 1, valueThrow, valueSpawn);
    }

    @Override
    public float chanceForMoreEffects(World world, double x, double y, double z, Random random)
    {
        return 0.1f;
    }
}
