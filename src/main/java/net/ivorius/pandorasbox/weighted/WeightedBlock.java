/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package net.ivorius.pandorasbox.weighted;

import net.minecraft.block.Block;
import net.minecraft.util.WeightedRandom;

/**
 * Created by lukas on 31.03.14.
 */
public class WeightedBlock extends WeightedRandom.Item
{
    public Block block;

    public WeightedBlock(int weight, Block block)
    {
        super(weight);

        this.block = block;
    }
}
