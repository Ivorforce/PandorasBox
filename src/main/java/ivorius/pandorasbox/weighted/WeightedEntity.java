/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.weighted;

import ivorius.pandorasbox.utils.WeightedSelector;

/**
 * Created by lukas on 31.03.14.
 */
public class WeightedEntity implements WeightedSelector.Item
{
    public double weight;

    public String entityID;

    public int minNumber;
    public int maxNumber;

    public WeightedEntity(double weight, String entityID, int minNumber, int maxNumber)
    {
        this.weight = weight;
        this.entityID = entityID;
        this.minNumber = minNumber;
        this.maxNumber = maxNumber;
    }

    @Override
    public double getWeight()
    {
        return weight;
    }
}
