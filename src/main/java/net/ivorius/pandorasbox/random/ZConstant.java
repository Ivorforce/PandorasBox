/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package net.ivorius.pandorasbox.random;

import java.util.Random;

/**
 * Created by lukas on 04.04.14.
 */
public class ZConstant implements ZValue
{
    public boolean value;

    public ZConstant(boolean value)
    {
        this.value = value;
    }

    @Override
    public boolean getValue(Random random)
    {
        return value;
    }
}
