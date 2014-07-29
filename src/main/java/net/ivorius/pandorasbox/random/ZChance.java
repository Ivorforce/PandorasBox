/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package net.ivorius.pandorasbox.random;

import java.util.Random;

/**
 * Created by lukas on 04.04.14.
 */
public class ZChance implements ZValue
{
    public double chance;

    public ZChance(double chance)
    {
        this.chance = chance;
    }

    @Override
    public boolean getValue(Random random)
    {
        return random.nextDouble() < chance;
    }
}
