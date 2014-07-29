/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.random;

import java.util.Random;

/**
 * Created by lukas on 04.04.14.
 */
public class DLinear implements DValue
{
    public double min;
    public double max;

    public DLinear(double min, double max)
    {
        this.min = min;
        this.max = max;
    }

    @Override
    public double getValue(Random random)
    {
        return min + random.nextDouble() * (max - min);
    }
}
