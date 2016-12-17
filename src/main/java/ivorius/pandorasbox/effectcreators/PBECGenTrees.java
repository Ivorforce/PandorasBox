/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effectcreators;

import ivorius.pandorasbox.PandorasBoxHelper;
import ivorius.pandorasbox.effects.PBEffect;
import ivorius.pandorasbox.effects.PBEffectGenTrees;
import ivorius.pandorasbox.random.DValue;
import ivorius.pandorasbox.random.IValue;
import ivorius.pandorasbox.random.ZValue;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBECGenTrees implements PBEffectCreator
{
    public DValue range;
    public DValue chancePerBlock;

    public ZValue requiresSolidGround;
    public IValue possibleTreeFlags;

    public PBECGenTrees(DValue range, DValue chancePerBlock, ZValue requiresSolidGround, IValue possibleTreeFlags)
    {
        this.range = range;
        this.chancePerBlock = chancePerBlock;
        this.requiresSolidGround = requiresSolidGround;
        this.possibleTreeFlags = possibleTreeFlags;
    }

    @Override
    public PBEffect constructEffect(World world, double x, double y, double z, Random random)
    {
        double range = this.range.getValue(random);
        int time = MathHelper.floor((random.nextDouble() * 7.0 + 3.0) * range);
        double chancePerBlock = this.chancePerBlock.getValue(random);
        boolean requiresSolidGround = this.requiresSolidGround.getValue(random);
        int possibleTreeFlags = this.possibleTreeFlags.getValue(random);

        PBEffectGenTrees genTrees = new PBEffectGenTrees(time, range, PandorasBoxHelper.getRandomUnifiedSeed(random), requiresSolidGround, chancePerBlock, possibleTreeFlags);
        return genTrees;
    }

    @Override
    public float chanceForMoreEffects(World world, double x, double y, double z, Random random)
    {
        return 0.15f;
    }
}
