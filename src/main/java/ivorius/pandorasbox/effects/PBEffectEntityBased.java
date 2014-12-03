/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

/**
 * Created by lukas on 31.03.14.
 */
public abstract class PBEffectEntityBased extends PBEffectNormal
{
    public double range;

    public PBEffectEntityBased()
    {
    }

    public PBEffectEntityBased(int maxTicksAlive, double range)
    {
        super(maxTicksAlive);
        this.range = range;
    }

    @Override
    public void doEffect(World world, EntityPandorasBox entity, Vec3 effectCenter, Random random, float prevRatio, float newRatio)
    {
        AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(effectCenter.xCoord - range, effectCenter.yCoord - range, effectCenter.zCoord - range, effectCenter.xCoord + range, effectCenter.yCoord + range, effectCenter.zCoord + range);
        List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, bb);

        for (EntityLivingBase entityLivingBase : entities)
        {
            double dist = entityLivingBase.getDistanceToEntity(entity);
            double strength = (range - dist) / range;

            if (strength > 0.0)
            {
                affectEntity(world, entity, random, entityLivingBase, newRatio, prevRatio, strength);
            }
        }
    }

    public abstract void affectEntity(World world, EntityPandorasBox box, Random random, EntityLivingBase entity, double newRatio, double prevRatio, double strength);

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setDouble("range", range);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        range = compound.getDouble("range");
    }
}
