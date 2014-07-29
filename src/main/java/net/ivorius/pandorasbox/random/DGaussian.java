/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package net.ivorius.pandorasbox.random;

import java.util.Random;

/**
 * Created by lukas on 04.04.14.
 */
public class DGaussian implements DValue
{
    public double min;
    public double max;

    public DGaussian(double min, double max)
    {
        this.min = min;
        this.max = max;
    }

    @Override
    public double getValue(Random random)
    {
        return (min + max * 0.5) + (random.nextDouble() - random.nextDouble()) * (max - min) * 0.5;
    }
}
