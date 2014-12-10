/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public abstract class PBEffectSpawnEntities extends PBEffectNormal
{
    public int number;

    public boolean spawnFromBox;
    public double range;
    public double shiftY;
    public double throwStrengthSideMin;
    public double throwStrengthSideMax;
    public double throwStrengthYMin;
    public double throwStrengthYMax;

    protected PBEffectSpawnEntities()
    {
    }

    public PBEffectSpawnEntities(int time, int number)
    {
        super(time);

        this.number = number;
    }

    public void setDoesNotSpawnFromBox(double range, double shiftY)
    {
        this.spawnFromBox = false;
        this.range = range;
        this.shiftY = shiftY;
    }

    public void setDoesSpawnFromBox(double throwStrengthSideMin, double throwStrengthSideMax, double throwStrengthYMin, double throwStrengthYMax)
    {
        this.spawnFromBox = true;
        this.throwStrengthSideMin = throwStrengthSideMin;
        this.throwStrengthSideMax = throwStrengthSideMax;
        this.throwStrengthYMin = throwStrengthYMin;
        this.throwStrengthYMax = throwStrengthYMax;
    }

    @Override
    public void doEffect(World world, EntityPandorasBox box, Vec3 effectCenter, Random random, float prevRatio, float newRatio)
    {
        if (!world.isRemote)
        {
            int prev = getSpawnNumber(prevRatio);
            int toSpawn = getSpawnNumber(newRatio) - prev;

            for (int i = 0; i < toSpawn; i++)
            {
                double eX;
                double eY;
                double eZ;

                if (spawnFromBox)
                {
                    eX = box.posX;
                    eY = box.posY;
                    eZ = box.posZ;
                }
                else
                {
                    eX = box.posX + (random.nextDouble() - random.nextDouble()) * range;
                    eY = box.posY + (random.nextDouble() - random.nextDouble()) * 3.0 + shiftY;
                    eZ = box.posZ + (random.nextDouble() - random.nextDouble()) * range;
                }

                Entity newEntity = spawnEntity(world, box, random, prev + i, eX, eY, eZ);
                if (newEntity != null)
                {
                    if (spawnFromBox)
                    {
                        // FIXME Disabled because it causes mobs to sink in the ground on clients (async) >.>
//                        float dirSide = random.nextFloat() * 2.0f * 3.1415926f;
//                        double throwStrengthSide = throwStrengthSideMin + random.nextDouble() * (throwStrengthSideMax - throwStrengthSideMin);
//
//                        newEntity.addVelocity(MathHelper.sin(dirSide) * throwStrengthSide,
//                                throwStrengthYMin + random.nextDouble() * (throwStrengthYMax - throwStrengthYMin),
//                                MathHelper.cos(dirSide) * throwStrengthSide);
//                        newEntity.velocityChanged = true;
                    }
                }
            }
        }
    }

    private int getSpawnNumber(float ratio)
    {
        return MathHelper.floor_float(ratio * number);
    }

    public abstract Entity spawnEntity(World world, EntityPandorasBox pbEntity, Random random, int number, double x, double y, double z);

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("number", number);
        compound.setBoolean("spawnFromBox", spawnFromBox);
        compound.setDouble("range", range);
        compound.setDouble("shiftY", shiftY);
        compound.setDouble("throwStrengthSideMin", throwStrengthSideMin);
        compound.setDouble("throwStrengthSideMax", throwStrengthSideMax);
        compound.setDouble("throwStrengthYMin", throwStrengthYMin);
        compound.setDouble("throwStrengthYMax", throwStrengthYMax);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        number = compound.getInteger("number");
        spawnFromBox = compound.getBoolean("spawnFromBox");
        range = compound.getDouble("range");
        shiftY = compound.getDouble("shiftY");
        throwStrengthSideMin = compound.getDouble("throwStrengthSideMin");
        throwStrengthSideMax = compound.getDouble("throwStrengthSideMax");
        throwStrengthYMin = compound.getDouble("throwStrengthYMin");
        throwStrengthYMax = compound.getDouble("throwStrengthYMax");
    }
}
