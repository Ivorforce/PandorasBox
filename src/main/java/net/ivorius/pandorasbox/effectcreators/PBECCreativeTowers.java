/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package net.ivorius.pandorasbox.effectcreators;

import net.ivorius.pandorasbox.PandorasBoxHelper;
import net.ivorius.pandorasbox.effects.PBEffect;
import net.ivorius.pandorasbox.effects.PBEffectGenCreativeTowers;
import net.ivorius.pandorasbox.random.DValue;
import net.ivorius.pandorasbox.random.IValue;
import net.ivorius.pandorasbox.weighted.WeightedBlock;
import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBECCreativeTowers implements PBEffectCreator
{
    public DValue range;
    public IValue number;

    public Collection<WeightedBlock> blocks;

    public PBECCreativeTowers(DValue range, IValue number, Collection<WeightedBlock> blocks)
    {
        this.range = range;
        this.number = number;
        this.blocks = blocks;
    }

    @Override
    public PBEffect constructEffect(World world, double x, double y, double z, Random random)
    {
        double range = this.range.getValue(random);
        int number = this.number.getValue(random);
        int time = MathHelper.floor_double((random.nextDouble() * 4.0 + 1.0) * number * 10.0);

        Block[] selection = PandorasBoxHelper.getRandomBlockList(random, blocks);

        PBEffectGenCreativeTowers genCreativeTowers = new PBEffectGenCreativeTowers(time);
        genCreativeTowers.createRandomStructures(random, number, range, blocks);
        return genCreativeTowers;
    }

    @Override
    public float chanceForMoreEffects(World world, double x, double y, double z, Random random)
    {
        return 0.15f;
    }
}
