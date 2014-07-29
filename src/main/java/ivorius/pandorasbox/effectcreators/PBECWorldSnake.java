/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effectcreators;

import ivorius.pandorasbox.PandorasBoxHelper;
import ivorius.pandorasbox.effects.PBEffect;
import ivorius.pandorasbox.effects.PBEffectGenWorldSnake;
import ivorius.pandorasbox.random.DValue;
import ivorius.pandorasbox.random.IValue;
import ivorius.pandorasbox.weighted.WeightedBlock;
import net.minecraft.block.Block;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBECWorldSnake implements PBEffectCreator
{
    public IValue time;
    public DValue startRange;
    public DValue speed;
    public DValue size;

    public Collection<WeightedBlock> blocks;

    public PBECWorldSnake(IValue time, DValue startRange, DValue speed, DValue size, Collection<WeightedBlock> blocks)
    {
        this.time = time;
        this.startRange = startRange;
        this.speed = speed;
        this.size = size;
        this.blocks = blocks;
    }

    @Override
    public PBEffect constructEffect(World world, double x, double y, double z, Random random)
    {
        double speed = this.speed.getValue(random);
        double size = this.size.getValue(random);
        int time = this.time.getValue(random);

        double distX = this.startRange.getValue(random);
        double distY = this.startRange.getValue(random);
        double distZ = this.startRange.getValue(random);

        Block[] selection = PandorasBoxHelper.getRandomBlockList(random, blocks);

        PBEffectGenWorldSnake gen = new PBEffectGenWorldSnake(time, selection, PandorasBoxHelper.getRandomUnifiedSeed(random), x + distX, y + distY, z + distZ, size, speed, random.nextFloat() * 360.0f, random.nextFloat() * 360.0f);
        return gen;
    }

    @Override
    public float chanceForMoreEffects(World world, double x, double y, double z, Random random)
    {
        return 0.2f;
    }
}
