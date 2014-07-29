/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package net.ivorius.pandorasbox.effects;

import net.ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public abstract class PBEffectGenerate2D extends PBEffectRangeBased
{
    public int unifiedSeed;

    public PBEffectGenerate2D()
    {
    }

    public PBEffectGenerate2D(int time, double range, int passes, int unifiedSeed)
    {
        super(time, range, passes);

        this.unifiedSeed = unifiedSeed;
    }

    @Override
    public void generateInRange(World world, EntityPandorasBox entity, Random random, double newRange, double prevRange, int pass)
    {
        int requiredRange = MathHelper.floor_double(newRange);

        int baseX = MathHelper.floor_double(entity.posX);
        int baseY = MathHelper.floor_double(entity.posY);
        int baseZ = MathHelper.floor_double(entity.posZ);

        for (int x = -requiredRange; x <= requiredRange; x++)
        {
            for (int z = -requiredRange; z <= requiredRange; z++)
            {
                double dist = MathHelper.sqrt_double(x * x + z * z);

                if (dist <= newRange)
                {
                    if (dist > prevRange)
                    {
                        generateOnSurface(world, entity, random, pass, x + baseX, baseY, z + baseZ, dist);
                    }
                    else
                    {
                        z = -z; // We can skip all blocks in between
                    }
                }
            }
        }
    }

    public abstract void generateOnSurface(World world, EntityPandorasBox entity, Random random, int pass, int x, int baseY, int z, double distance);

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("unifiedSeed", unifiedSeed);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        unifiedSeed = compound.getInteger("unifiedSeed");
    }
}
