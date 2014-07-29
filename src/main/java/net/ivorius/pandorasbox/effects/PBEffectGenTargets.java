/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package net.ivorius.pandorasbox.effects;

import net.ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

/**
 * Created by lukas on 30.03.14.
 */
public class PBEffectGenTargets extends PBEffectGenerateByStructure
{
    public String entityToSpawn;
    public double range;
    public double targetSize;
    public double entityDensity;

    public PBEffectGenTargets()
    {
    }

    public PBEffectGenTargets(int maxTicksAlive, String entityToSpawn, double range, double targetSize, double entityDensity)
    {
        super(maxTicksAlive);
        this.entityToSpawn = entityToSpawn;
        this.range = range;
        this.targetSize = targetSize;
        this.entityDensity = entityDensity;
    }

    public void createTargets(World world, double x, double y, double z, Random random)
    {
        List<EntityPlayer> players = world.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getAABBPool().getAABB(x - range, y - range, z - range, x + range, y + range, z + range));
        this.structures = new Structure[players.size()];

        for (int i = 0; i < players.size(); i++)
        {
            EntityPlayer player = players.get(i);
            StructureTarget structureTarget = new StructureTarget();
            structureTarget.x = MathHelper.floor_double(player.posX);
            structureTarget.y = MathHelper.floor_double(player.posY - 0.5);
            structureTarget.z = MathHelper.floor_double(player.posZ);
            structureTarget.structureStart = random.nextFloat() * 0.3f;
            structureTarget.structureLength = 0.5f + random.nextFloat() * 0.2f;

            structureTarget.colors = new int[MathHelper.ceiling_double_int(targetSize)];
            for (int j = 0; j < structureTarget.colors.length; j++)
            {
                structureTarget.colors[j] = random.nextInt(16);
            }

            structures[i] = structureTarget;
        }
    }

    @Override
    public void generateStructure(World world, EntityPandorasBox entity, Random random, Structure structure, int x, int y, int z, float newRatio, float prevRatio)
    {
        if (!world.isRemote)
        {
            StructureTarget structureTarget = (StructureTarget) structure;

            double newRange = newRatio * targetSize;
            double prevRange = prevRatio * targetSize;

            int requiredRange = MathHelper.ceiling_double_int(newRange);

            for (int xP = -requiredRange; xP <= requiredRange; xP++)
            {
                for (int zP = -requiredRange; zP <= requiredRange; zP++)
                {
                    double dist = MathHelper.sqrt_double(xP * xP + zP * zP);

                    if (dist < newRange)
                    {
                        if (dist >= prevRange)
                        {
                            setBlockAndMetaSafe(world, structureTarget.x + xP, structureTarget.y, structureTarget.z + zP, Blocks.stained_hardened_clay, structureTarget.colors[MathHelper.floor_double(dist)]);

                            double nextDist = MathHelper.sqrt_double((xP * xP + 3 * 3) + (zP * zP + 3 * 3));

                            if (nextDist >= targetSize && random.nextDouble() < entityDensity)
                            {
                                Entity newEntity = PBEffectSpawnEntityIDList.createEntity(world, entity, random, entityToSpawn, structureTarget.x + xP + 0.5, structureTarget.y + 1.5, structureTarget.z + zP + 0.5);
                                world.spawnEntityInWorld(newEntity);
                            }
                        }
                    }

                    for (int yP = 1; yP <= requiredRange; yP++)
                    {
                        double dist3D = MathHelper.sqrt_double(xP * xP + zP * zP + yP * yP);

                        if (dist3D < newRange && dist3D >= prevRange) // -3 so we have a bit of a height bonus
                        {
                            setBlockSafe(world, structureTarget.x + xP, structureTarget.y + yP, structureTarget.z + zP, Blocks.air);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setString("entityToSpawn", entityToSpawn);
        compound.setDouble("range", range);
        compound.setDouble("targetSize", targetSize);
        compound.setDouble("entityDensity", entityDensity);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        entityToSpawn = compound.getString("entityToSpawn");
        range = compound.getDouble("range");
        targetSize = compound.getDouble("targetSize");
        entityDensity = compound.getDouble("entityDensity");
    }

    @Override
    public StructureTarget createStructure()
    {
        return new StructureTarget();
    }

    public static class StructureTarget extends Structure
    {
        public int[] colors;

        public StructureTarget()
        {
        }

        @Override
        public void writeToNBT(NBTTagCompound compound)
        {
            super.writeToNBT(compound);

            compound.setIntArray("colors", colors);
        }

        @Override
        public void readFromNBT(NBTTagCompound compound)
        {
            super.readFromNBT(compound);

            colors = compound.getIntArray("colors");
        }
    }
}
