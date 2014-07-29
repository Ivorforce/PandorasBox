/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.weighted;

import net.minecraft.potion.Potion;
import net.minecraft.util.WeightedRandom;

/**
 * Created by lukas on 31.03.14.
 */
public class WeightedPotion extends WeightedRandom.Item
{
    public Potion potion;

    public int minStrength;
    public int maxStrength;

    public int minDuration;
    public int maxDuration;

    public WeightedPotion(int weight, Potion potion, int minStrength, int maxStrength, int minDuration, int maxDuration)
    {
        super(weight);
        this.potion = potion;
        this.minStrength = minStrength;
        this.maxStrength = maxStrength;
        this.minDuration = minDuration;
        this.maxDuration = maxDuration;
    }
}
