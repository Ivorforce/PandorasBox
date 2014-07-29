/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package net.ivorius.pandorasbox.effectcreators;

import net.ivorius.pandorasbox.PandorasBoxHelper;
import net.ivorius.pandorasbox.effects.PBEffect;
import net.ivorius.pandorasbox.effects.PBEffectGenDome;
import net.ivorius.pandorasbox.random.DValue;
import net.ivorius.pandorasbox.random.IValue;
import net.ivorius.pandorasbox.weighted.WeightedBlock;
import net.minecraft.block.Block;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBECDome implements PBEffectCreator
{
    public IValue time;
    public DValue range;

    public Collection<WeightedBlock> domeBlocks;
    public Block fillBlock;

    public PBECDome(IValue time, DValue range, Collection<WeightedBlock> domeBlocks, Block fillBlock)
    {
        this.time = time;
        this.range = range;
        this.domeBlocks = domeBlocks;
        this.fillBlock = fillBlock;
    }

    @Override
    public PBEffect constructEffect(World world, double x, double y, double z, Random random)
    {
        double range = this.range.getValue(random);
        int time = this.time.getValue(random);

        Block domeBlock = PandorasBoxHelper.getRandomBlock(random, domeBlocks);

        PBEffectGenDome gen = new PBEffectGenDome(time, range, PandorasBoxHelper.getRandomUnifiedSeed(random), domeBlock, fillBlock);
        return gen;
    }

    @Override
    public float chanceForMoreEffects(World world, double x, double y, double z, Random random)
    {
        return 0.15f;
    }
}
