/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package net.ivorius.pandorasbox.effects;

import net.ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBEffectRandomLightnings extends PBEffectPositionBased
{
    protected PBEffectRandomLightnings()
    {
    }

    public PBEffectRandomLightnings(int time, int number, double range)
    {
        super(time, number, range);
    }

    @Override
    public void doEffect(World world, EntityPandorasBox entity, Random random, float newRatio, float prevRatio, double x, double y, double z)
    {
        if (!world.isRemote)
        {
            EntityLightningBolt lightningBolt = new EntityLightningBolt(world, x, world.getHeightValue(MathHelper.floor_double(x), MathHelper.floor_double(z)), z);
            world.addWeatherEffect(lightningBolt);
        }
    }
}
