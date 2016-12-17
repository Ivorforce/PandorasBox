/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effectcreators;

import ivorius.pandorasbox.PandorasBoxHelper;
import ivorius.pandorasbox.effects.PBEffect;
import ivorius.pandorasbox.effects.PBEffectGenShapes;
import ivorius.pandorasbox.random.DValue;
import ivorius.pandorasbox.random.IValue;
import ivorius.pandorasbox.random.ValueHelper;
import ivorius.pandorasbox.random.ZValue;
import ivorius.pandorasbox.weighted.WeightedBlock;
import net.minecraft.util.math.MathHelper;
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

        int time = MathHelper.floor((random.nextDouble() * 4.0 + 1.0) * size[1] * 8.0);
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
