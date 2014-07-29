/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effectcreators;

import ivorius.pandorasbox.PandorasBoxHelper;
import ivorius.pandorasbox.effects.PBEffect;
import ivorius.pandorasbox.effects.PBEffectGenHeightNoise;
import ivorius.pandorasbox.random.DValue;
import ivorius.pandorasbox.random.IValue;
import ivorius.pandorasbox.random.ValueHelper;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBECHeightNoise implements PBEffectCreator
{
    public DValue range;
    public IValue shift;
    public IValue towerSize;
    public IValue blockSize;

    public PBECHeightNoise(DValue range, IValue shift, IValue towerSize, IValue blockSize)
    {
        this.range = range;
        this.shift = shift;
        this.towerSize = towerSize;
        this.blockSize = blockSize;
    }

    @Override
    public PBEffect constructEffect(World world, double x, double y, double z, Random random)
    {
        double range = this.range.getValue(random);
        int time = MathHelper.floor_double((random.nextDouble() * 7.0 + 3.0) * range);

        int blockSize = this.blockSize.getValue(random);

        int[] shift = ValueHelper.getValueRange(this.shift, random);
        int[] towerSize = ValueHelper.getValueRange(this.towerSize, random);

        PBEffectGenHeightNoise genHeightNoise = new PBEffectGenHeightNoise(time, range, PandorasBoxHelper.getRandomUnifiedSeed(random), shift[0], shift[1], towerSize[0], towerSize[1], blockSize);
        return genHeightNoise;
    }

    @Override
    public float chanceForMoreEffects(World world, double x, double y, double z, Random random)
    {
        return 0.15f;
    }
}
