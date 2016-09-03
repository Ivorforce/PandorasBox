/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.weighted;

import ivorius.ivtoolkit.random.WeightedSelector;
import net.minecraft.potion.Potion;
import net.minecraft.util.WeightedRandom;

/**
 * Created by lukas on 31.03.14.
 */
public class WeightedPotion implements WeightedSelector.Item
{
    public double weight;

    public Potion potion;

    public int minStrength;
    public int maxStrength;

    public int minDuration;
    public int maxDuration;

    public WeightedPotion(double weight, Potion potion, int minStrength, int maxStrength, int minDuration, int maxDuration)
    {
        this.weight = weight;
        this.potion = potion;
        this.minStrength = minStrength;
        this.maxStrength = maxStrength;
        this.minDuration = minDuration;
        this.maxDuration = maxDuration;
    }

    @Override
    public double getWeight()
    {
        return weight;
    }
}
