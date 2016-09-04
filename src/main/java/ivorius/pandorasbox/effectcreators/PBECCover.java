/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effectcreators;

import ivorius.pandorasbox.PandorasBoxHelper;
import ivorius.pandorasbox.effects.PBEffect;
import ivorius.pandorasbox.effects.PBEffectGenCover;
import ivorius.pandorasbox.random.DValue;
import ivorius.pandorasbox.random.ZValue;
import ivorius.pandorasbox.weighted.WeightedBlock;
import net.minecraft.block.Block;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBECCover implements PBEffectCreator
{
    public DValue range;
    public ZValue overSurface;

    public Collection<WeightedBlock> blocks;

    public PBECCover(DValue range, ZValue overSurface, Collection<WeightedBlock> blocks)
    {
        this.range = range;
        this.overSurface = overSurface;
        this.blocks = blocks;
    }

    @Override
    public PBEffect constructEffect(World world, double x, double y, double z, Random random)
    {
        double range = this.range.getValue(random);
        boolean overSurface = this.overSurface.getValue(random);
        int time = MathHelper.floor_double((random.nextDouble() * 7.0 + 3.0) * range);

        Block[] selection = PandorasBoxHelper.getRandomBlockList(random, blocks);

        PBEffectGenCover gen = new PBEffectGenCover(time, range, PandorasBoxHelper.getRandomUnifiedSeed(random), overSurface, selection);
        return gen;
    }

    @Override
    public float chanceForMoreEffects(World world, double x, double y, double z, Random random)
    {
        return 0.1f;
    }
}
