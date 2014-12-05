/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effectcreators;

import ivorius.pandorasbox.effects.PBEffect;
import ivorius.pandorasbox.effects.PBEffectEntitiesBombpack;
import ivorius.pandorasbox.effects.PBEffectExplode;
import ivorius.pandorasbox.random.DValue;
import ivorius.pandorasbox.random.IValue;
import ivorius.pandorasbox.random.ZValue;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBECExplosion implements PBEffectCreator
{
    public IValue time;
    public DValue explosionRadius;
    public ZValue burning;

    public PBECExplosion(IValue time, DValue explosionRadius, ZValue burning)
    {
        this.time = time;
        this.explosionRadius = explosionRadius;
        this.burning = burning;
    }

    @Override
    public PBEffect constructEffect(World world, double x, double y, double z, Random random)
    {
        int time = this.time.getValue(random);
        double explosionRadius = this.explosionRadius.getValue(random);
        boolean burning = this.burning.getValue(random);

        PBEffectExplode effect = new PBEffectExplode(time, (float) explosionRadius, burning);
        return effect;
    }

    @Override
    public float chanceForMoreEffects(World world, double x, double y, double z, Random random)
    {
        return 0.5f;
    }
}
