/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package net.ivorius.pandorasbox.effectcreators;

import net.ivorius.pandorasbox.PandorasBoxHelper;
import net.ivorius.pandorasbox.effects.PBEffect;
import net.ivorius.pandorasbox.effects.PBEffectGenShapes;
import net.ivorius.pandorasbox.random.DValue;
import net.ivorius.pandorasbox.random.IValue;
import net.ivorius.pandorasbox.random.ValueHelper;
import net.ivorius.pandorasbox.random.ZValue;
import net.ivorius.pandorasbox.weighted.WeightedBlock;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBECRandomShapes implements PBEffectCreator
{
    public DValue range;
    public DValue size;
    public IValue number;

    public Collection<WeightedBlock> blocks;

    public ZValue sameBlockSetup;

    public PBECRandomShapes(DValue range, DValue size, IValue number, Collection<WeightedBlock> blocks, ZValue sameBlockSetup)
    {
        this.range = range;
        this.size = size;
        this.number = number;
        this.blocks = blocks;
        this.sameBlockSetup = sameBlockSetup;
    }

    @Override
    public PBEffect constructEffect(World world, double x, double y, double z, Random random)
    {
        double range = this.range.getValue(random);
        int number = this.number.getValue(random);
        int shape = random.nextInt(4) - 1;

        double[] size = ValueHelper.getValueRange(this.size, random);

        int time = MathHelper.floor_double((random.nextDouble() * 4.0 + 1.0) * size[1] * 8.0);
        boolean sameBlockSetup = this.sameBlockSetup.getValue(random);

        PBEffectGenShapes genTransform = new PBEffectGenShapes(time);
        if (sameBlockSetup)
        {
            genTransform.setShapes(random, PandorasBoxHelper.getRandomBlockList(random, blocks), range, size[0], size[1], number, shape, PandorasBoxHelper.getRandomUnifiedSeed(random));
        }
        else
        {
            genTransform.setRandomShapes(random, blocks, range, size[0], size[1], number, shape);
        }
        return genTransform;
    }

    @Override
    public float chanceForMoreEffects(World world, double x, double y, double z, Random random)
    {
        return 0.2f;
    }
}
