/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effectcreators;

import ivorius.pandorasbox.effects.PBEffect;
import ivorius.pandorasbox.effects.PBEffectSetTime;
import ivorius.pandorasbox.random.IValue;
import ivorius.pandorasbox.random.ZValue;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBECSetTime implements PBEffectCreator
{
    public IValue time;
    public IValue worldTime;
    public ZValue add;

    public PBECSetTime(IValue time, IValue worldTime, ZValue add)
    {
        this.time = time;
        this.worldTime = worldTime;
        this.add = add;
    }

    @Override
    public PBEffect constructEffect(World world, double x, double y, double z, Random random)
    {
        int time = this.time.getValue(random);
        int worldTime = this.worldTime.getValue(random);
        boolean add = this.add.getValue(random);

        if (!add)
        {
            int currentTime = (int) (world.getWorldTime() % 24000);
            PBEffectSetTime effect = new PBEffectSetTime(time, worldTime - currentTime);
            return effect;
        }
        else
        {
            PBEffectSetTime effect = new PBEffectSetTime(time, worldTime);
            return effect;
        }
    }

    @Override
    public float chanceForMoreEffects(World world, double x, double y, double z, Random random)
    {
        return 0.7f;
    }
}
