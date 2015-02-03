/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 03.04.14.
 */
public class PBEffectEntitiesBuff extends PBEffectEntityBased
{
    public PotionEffect[] effects;

    public PBEffectEntitiesBuff()
    {
    }

    public PBEffectEntitiesBuff(int maxTicksAlive, double range, PotionEffect[] effects)
    {
        super(maxTicksAlive, range);

        this.effects = effects;
    }

    @Override
    public void affectEntity(World world, EntityPandorasBox box, Random random, EntityLivingBase entity, double newRatio, double prevRatio, double strength)
    {
        for (PotionEffect effect : effects)
        {
            int prevDuration = MathHelper.floor_double(prevRatio * strength * effect.getDuration());
            int newDuration = MathHelper.floor_double(newRatio * strength * effect.getDuration());
            int duration = newDuration - prevDuration;

            if (duration > 0)
            {
                PotionEffect curEffect = new PotionEffect(effect.getPotionID(), duration, effect.getAmplifier(), effect.getIsAmbient(), effect.getIsShowParticles());
                addPotionEffectDuration(entity, curEffect);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        setNBTPotions("potions", effects, compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        effects = getNBTPotions("potions", compound);
    }
}
