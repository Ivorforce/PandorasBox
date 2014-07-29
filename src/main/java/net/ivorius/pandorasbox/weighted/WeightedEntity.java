/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package net.ivorius.pandorasbox.weighted;

import net.minecraft.util.WeightedRandom;

/**
 * Created by lukas on 31.03.14.
 */
public class WeightedEntity extends WeightedRandom.Item
{
    public String entityID;
    public int minNumber;
    public int maxNumber;

    public WeightedEntity(int par1, String entityID, int minNumber, int maxNumber)
    {
        super(par1);
        this.entityID = entityID;
        this.minNumber = minNumber;
        this.maxNumber = maxNumber;
    }
}
