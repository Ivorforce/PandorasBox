/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package net.ivorius.pandorasbox.weighted;

import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;

/**
 * Created by lukas on 31.03.14.
 */
public class WeightedSet extends WeightedRandom.Item
{
    public ItemStack[] set;

    public WeightedSet(int par1, ItemStack[] set)
    {
        super(par1);
        this.set = set;
    }
}
