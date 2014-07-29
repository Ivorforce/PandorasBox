/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package net.ivorius.pandorasbox.random;

import java.util.Random;

/**
 * Created by lukas on 04.04.14.
 */
public class ILinear implements IValue
{
    public int min;
    public int max;

    public ILinear(int min, int max)
    {
        this.min = min;
        this.max = max;
    }

    @Override
    public int getValue(Random random)
    {
        return min + random.nextInt(max - min + 1);
    }
}
