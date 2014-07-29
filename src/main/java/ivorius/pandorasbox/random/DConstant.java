/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.random;

import java.util.Random;

/**
 * Created by lukas on 04.04.14.
 */
public class DConstant implements DValue
{
    public double constant;

    public DConstant(double constant)
    {
        this.constant = constant;
    }

    @Override
    public double getValue(Random random)
    {
        return constant;
    }
}
