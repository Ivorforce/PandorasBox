/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 03.04.14.
 */
public class PBEffectEntitiesCrush extends PBEffectEntityBased
{
    public int cycles;
    public double speed;

    public PBEffectEntitiesCrush()
    {
    }

    public PBEffectEntitiesCrush(int maxTicksAlive, double range, int cycles, double speed)
    {
        super(maxTicksAlive, range);
        this.cycles = cycles;
        this.speed = speed;
    }

    @Override
    public void affectEntity(World world, EntityPandorasBox box, Random random, EntityLivingBase entity, double newRatio, double prevRatio, double strength)
    {
        boolean lift = ((newRatio * cycles) % 1.000001) < 0.7; // We want 1.0 inclusive

        if (lift)
        {
            entity.motionY = entity.motionY * (1.0f - strength) + strength * speed;
        }
        else
        {
            entity.motionY = entity.motionY - strength * speed;
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("cycles", cycles);
        compound.setDouble("speed", speed);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        cycles = compound.getInteger("cycles");
        speed = compound.getDouble("speed");
    }
}
