/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effectcreators;

import ivorius.pandorasbox.PandorasBoxHelper;
import ivorius.pandorasbox.effects.PBEffect;
import ivorius.pandorasbox.effects.PBEffectGenTreesOdd;
import ivorius.pandorasbox.random.DValue;
import ivorius.pandorasbox.random.IValue;
import ivorius.pandorasbox.random.ZValue;
import ivorius.pandorasbox.weighted.WeightedBlock;
import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBECGenTreesOdd implements PBEffectCreator
{
    public DValue range;
    public DValue chancePerBlock;

    public ZValue requiresSolidGround;
    public IValue possibleTreeFlags;

    public Collection<WeightedBlock> trunkBlocks;
    public Collection<WeightedBlock> leafBlocks;

    public PBECGenTreesOdd(DValue range, DValue chancePerBlock, ZValue requiresSolidGround, IValue possibleTreeFlags, Collection<WeightedBlock> trunkBlocks, Collection<WeightedBlock> leafBlocks)
    {
        this.range = range;
        this.chancePerBlock = chancePerBlock;
        this.requiresSolidGround = requiresSolidGround;
        this.possibleTreeFlags = possibleTreeFlags;
        this.trunkBlocks = trunkBlocks;
        this.leafBlocks = leafBlocks;
    }

    @Override
    public PBEffect constructEffect(World world, double x, double y, double z, Random random)
    {
        double range = this.range.getValue(random);
        int time = MathHelper.floor_double((random.nextDouble() * 7.0 + 3.0) * range);
        double chancePerBlock = this.chancePerBlock.getValue(random);
        boolean requiresSolidGround = this.requiresSolidGround.getValue(random);
        int possibleTreeFlags = this.possibleTreeFlags.getValue(random);

        Block trunkBlock = PandorasBoxHelper.getRandomBlock(random, trunkBlocks);
        Block leafBlock = PandorasBoxHelper.getRandomBlock(random, leafBlocks);

        PBEffectGenTreesOdd genTrees = new PBEffectGenTreesOdd(time, range, PandorasBoxHelper.getRandomUnifiedSeed(random), requiresSolidGround, chancePerBlock, possibleTreeFlags, trunkBlock, leafBlock);
        return genTrees;
    }

    @Override
    public float chanceForMoreEffects(World world, double x, double y, double z, Random random)
    {
        return 0.15f;
    }
}
