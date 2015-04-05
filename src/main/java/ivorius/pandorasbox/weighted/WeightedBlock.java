/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.weighted;

import ivorius.pandorasbox.utils.WeightedSelector;
import net.minecraft.block.Block;
import net.minecraft.util.WeightedRandom;

/**
 * Created by lukas on 31.03.14.
 */
public class WeightedBlock implements WeightedSelector.Item
{
    public double weight;

    public Block block;

    public WeightedBlock(double weight, Block block)
    {
        this.weight = weight;
        this.block = block;
    }

    @Override
    public double getWeight()
    {
        return weight;
    }
}
