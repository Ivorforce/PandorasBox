/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effectcreators;

import ivorius.pandorasbox.PandorasBoxHelper;
import ivorius.pandorasbox.effects.PBEffect;
import ivorius.pandorasbox.effects.PBEffectGenLavaCages;
import ivorius.pandorasbox.random.DValue;
import ivorius.pandorasbox.weighted.WeightedBlock;
import net.minecraft.block.Block;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBECLavaCage implements PBEffectCreator
{
    public DValue range;

    public Block lavaBlock;
    public Block fillBlock;
    public Collection<WeightedBlock> cageBlocks;

    public PBECLavaCage(DValue range, Block lavaBlock, Block fillBlock, Collection<WeightedBlock> cageBlocks)
    {
        this.range = range;
        this.lavaBlock = lavaBlock;
        this.fillBlock = fillBlock;
        this.cageBlocks = cageBlocks;
    }

    @Override
    public PBEffect constructEffect(World world, double x, double y, double z, Random random)
    {
        double range = this.range.getValue(random);
        int time = MathHelper.floor((random.nextDouble() * 7.0 + 3.0) * range);

        Block cageBlock = PandorasBoxHelper.getRandomBlock(random, cageBlocks);

        PBEffectGenLavaCages genLavaCages = new PBEffectGenLavaCages(time, range, PandorasBoxHelper.getRandomUnifiedSeed(random), lavaBlock, cageBlock, fillBlock);
        return genLavaCages;
    }

    @Override
    public float chanceForMoreEffects(World world, double x, double y, double z, Random random)
    {
        return 0.1f;
    }
}
