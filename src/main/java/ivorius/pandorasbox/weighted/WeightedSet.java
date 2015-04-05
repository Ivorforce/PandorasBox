/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.weighted;

import ivorius.pandorasbox.utils.WeightedSelector;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;

/**
 * Created by lukas on 31.03.14.
 */
public class WeightedSet implements WeightedSelector.Item
{
    public double weight;

    public ItemStack[] set;

    public WeightedSet(double weight, ItemStack[] set)
    {
        this.weight = weight;
        this.set = set;
    }

    @Override
    public double getWeight()
    {
        return weight;
    }
}
