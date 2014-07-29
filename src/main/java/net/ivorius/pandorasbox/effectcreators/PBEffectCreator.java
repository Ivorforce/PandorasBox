/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package net.ivorius.pandorasbox.effectcreators;

import net.ivorius.pandorasbox.effects.PBEffect;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public interface PBEffectCreator
{
    public PBEffect constructEffect(World world, double x, double y, double z, Random random);

    public float chanceForMoreEffects(World world, double x, double y, double z, Random random);
}
