/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effectcreators;

import ivorius.pandorasbox.effects.PBEffect;
import ivorius.pandorasbox.effects.PBEffectEntitiesThrowItems;
import ivorius.pandorasbox.random.DValue;
import ivorius.pandorasbox.random.IValue;
import ivorius.pandorasbox.utils.RandomizedItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBECThrowItems implements PBEffectCreator
{
    public IValue time;
    public DValue range;
    public DValue throwChancePerItem;
    public DValue deletionChancePerThrow;
    public IValue smuggledInItems;

    public List<RandomizedItemStack> items;

    public PBECThrowItems(IValue time, DValue range, DValue throwChancePerItem, DValue deletionChancePerThrow, IValue smuggledInItems, List<RandomizedItemStack> items)
    {
        this.time = time;
        this.range = range;
        this.throwChancePerItem = throwChancePerItem;
        this.deletionChancePerThrow = deletionChancePerThrow;
        this.smuggledInItems = smuggledInItems;
        this.items = items;
    }

    @Override
    public PBEffect constructEffect(World world, double x, double y, double z, Random random)
    {
        int time = this.time.getValue(random);
        double range = this.range.getValue(random);
        double chancePerItem = this.throwChancePerItem.getValue(random);
        double deletionChance = this.deletionChancePerThrow.getValue(random);
        int smuggledIn = this.smuggledInItems.getValue(random);

        ItemStack[] stacks = PBECSpawnItems.getItemStacks(random, items, smuggledIn, random.nextInt(3) != 0, true, 0, false);

        PBEffectEntitiesThrowItems effect = new PBEffectEntitiesThrowItems(time, range, chancePerItem, deletionChance, stacks);
        return effect;
    }

    @Override
    public float chanceForMoreEffects(World world, double x, double y, double z, Random random)
    {
        return 0.15f;
    }
}
