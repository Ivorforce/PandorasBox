/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.random;

import java.util.Random;

/**
 * Created by lukas on 04.04.14.
 */
public class IExp implements IValue
{
    public int min;
    public int max;

    public double exp;

    public IExp(int min, int max, double exp)
    {
        this.min = min;
        this.max = max;
        this.exp = exp;
    }

    @Override
    public int getValue(Random random)
    {
        return (int) Math.round(min + ((Math.pow(exp, random.nextDouble()) - 1.0) / (exp - 1.0)) * (max - min));
    }
}
