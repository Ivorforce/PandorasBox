/*
 * Copyright (c) 2014, Lukas Tenbrink.
 * http://lukas.axxim.net
 */

package ivorius.pandorasbox.effects;

import ivorius.pandorasbox.entitites.EntityPandorasBox;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by lukas on 03.04.14.
 */
public class PBEffectEntitiesTeleport extends PBEffectEntityBased
{
    private double teleportRange;
    private int teleports;

    public PBEffectEntitiesTeleport()
    {
    }

    public PBEffectEntitiesTeleport(int maxTicksAlive, double range, double teleportRange, int teleports)
    {
        super(maxTicksAlive, range);
        this.teleportRange = teleportRange;
        this.teleports = teleports;
    }

    @Override
    public void affectEntity(World world, EntityPandorasBox box, Random random, EntityLivingBase entity, double newRatio, double prevRatio, double strength)
    {
        if (!world.isRemote)
        {
            Random entityRandom = new Random(entity.getEntityId());

            for (int i = 0; i < teleports; i++)
            {
                double expectedTeleport = entityRandom.nextDouble();
                if (newRatio >= expectedTeleport && prevRatio < expectedTeleport)
                {
                    double newX = entity.posX + (random.nextDouble() - random.nextDouble()) * teleportRange;
                    double newZ = entity.posZ + (random.nextDouble() - random.nextDouble()) * teleportRange;
                    double newY = world.getHeight(new BlockPos(newX, 0.0, newZ)).getY() + 0.2;
                    float newYaw = random.nextFloat() * 360.0f;

                    entity.setPositionAndUpdate(newX, newY, newZ);

                    if (entity instanceof EntityPlayerMP)
                    {
                        ((EntityPlayerMP) entity).playerNetServerHandler.setPlayerLocation(newX, newY, newZ, newYaw, ((EntityPlayerMP) entity).rotationPitch);
                    }
                }
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setDouble("teleportRange", teleportRange);
        compound.setInteger("teleports", teleports);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        teleportRange = compound.getDouble("teleportRange");
        teleports = compound.getInteger("teleports");
    }
}
