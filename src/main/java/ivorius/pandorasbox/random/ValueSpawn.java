/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.random;

/**
 * Created by lukas on 04.04.14.
 */
public class ValueSpawn
{
    public DValue spawnRange;
    public DValue spawnShift;

    public ValueSpawn(DValue spawnRange, DValue spawnShift)
    {
        this.spawnRange = spawnRange;
        this.spawnShift = spawnShift;
    }
}
